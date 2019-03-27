define(['knockout', 'store', 'mUpload', 'util'], function (ko, Store, MUpload, Util) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        var groupStore = new Store({
            model: 'mGroup',
            pageSize: 0,
            id: 'groupStore',
            proxy:{url:'/groups'},
            onLoadComplete:function () {
                contactStore.setProxy({url:''});
                contactStore.empty();
            }
        });
        scope.leftCard = {
            title: '群组',
            titleButtons: [],
            cardBody: {
                name: 'cmp:List',
                params: {
                    displayField: 'name',
                    store: groupStore,
                    onItemSelected: function (item) {
                        contactStore.setProxy({url:Util.formatTpl(contactStore.getProxy().originalUrl,item.id())});
                        contactStore.load({firstPage:true});
                    },
                    onNoneItemSelected: function () {
                        contactStore.setProxy({url:''});
                        contactStore.empty();
                    }
                }
            }
        };

        var contactStore = new Store({
            model: 'mContact',
            id: 'contactStore',
            autoLoad: false
        });
        scope.contactCard = {
            title: '联系人',
            titleButtons: [],
            preOperation:function(operation){
                switch (operation.name) {
                    case 'add':
                    case 'upload':
                        if(contactStore.getProxy().url == ''){
                            Util.showAlert('请选择群组','warning');
                            return false;
                        }
                        else{
                            return true;
                        }
                        break;
                }

                return true;
            },
            cardBody: {
                name: 'cmp:Grid',
                params: {
                    operations: [],
                    store: contactStore,
                    displayFields: ['rowId', 'name','wxhm','email','tel']
                }
            },
            onOperation: function (operation) {
                if (operation.name == 'upload') {
                    var upload = new MUpload({isKo: true});

                    upload.setProxy({url:contactStore.getProxy().url.replace('contacts','upload')});
                    ko.root.openModal({
                        title: '上传', record: upload, operation: 'upload',
                        commit: function (btn, record, callback) {
                            record.save(callback);
                        }
                    });
                }
                else if(operation.name == 'download'){
                    window.open(Util.getRestUrl('/import/template'));
                }
            }
        };

        var menuItems = Util.findSubItemsByParentField(ko.root.menus,Util.getLocationHash(1),'href','parentId')

        menuItems.forEach(function (menuItem) {
           switch (menuItem.name) {
               case '查看':
                   scope.contactCard.cardBody.params.operations.push('view');
                   break;
               case '编辑':
                   scope.leftCard.titleButtons.push('add');
                   scope.contactCard.titleButtons = ['add', 'upload','download'];
                   scope.contactCard.cardBody.params.operations.push('edit');
                   scope.contactCard.cardBody.params.operations.push('delete');
                   break;
           }
        });

        ko.root.secondCmp({name:'cmp:Card',params:scope.contactCard});
    }

    return ViewModel;
});

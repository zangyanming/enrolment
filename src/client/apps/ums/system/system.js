define(['knockout', 'store', 'util'], function (ko, Store, Util) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        var systemStore = new Store({
            model: 'mSystem',
            id: 'groupStore',
        });

        scope.card = {
            title: '系统列表',
            titleButtons: [],
            cardBody: {
                name: 'cmp:Grid',
                params: {
                    store: systemStore,
                    operations:[],
                    displayFields: ['rowId', 'name','sysCode','sysKey','applicationUnit','applicant','auditStatusName']
                }
            }
        };

        var menuItems = Util.findSubItemsByParentField(ko.root.menus,Util.getLocationHash(1),'href','parentId')

        menuItems.forEach(function (menuItem) {
            switch (menuItem.name) {
                case '查看':
                    scope.card.cardBody.params.operations.push('view');
                    break;
                case '编辑':
                    scope.card.titleButtons.push('add');
                    break;
            }
        });
    }

    return ViewModel;
});

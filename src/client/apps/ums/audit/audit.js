define(['knockout', 'store','util'], function (ko, Store,Util) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;
        var menuStore = new Store({
            autoLoad: false, model: 'model', onInitComplete: function () {
                menuStore.initRecords(Util.findSubItemsByParentField(ko.root.menus,Util.getLocationHash(1),'href','parentId'));
            }
        });

        scope.leftCard = {
            cardBody: {
                name: 'cmp:List',
                params: {
                    displayField:'name',
                    store: menuStore,
                    onItemSelected: function (item) {},
                    onNoneItemSelected: function () {}
                }
            }
        };
    }

    return ViewModel;
});

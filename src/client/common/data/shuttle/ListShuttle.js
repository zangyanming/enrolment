define(['knockout', 'store', 'util'], function (ko, Store, Util) {
    function ViewModel(params) {
        var scope = this;
        var listStore = new Store({
            model: params.record.params.list.model,
            pageSize: 0
        });
        var shuttleStore = new Store({
            model: params.record.params.shuttle.model,
            autoLoad: false,
            pageSize: 0
        });
        scope.list = {
            showOperation: false,
            displayField: params.record.params.list.displayName,
            store: listStore,
            onItemSelected: function (item) {
                shuttleStore.setProxy({url: Util.formatTpl(shuttleStore.getProxy().originalUrl, item.id())});
                shuttleStore.load();
            },
            onNoneItemSelected: function () {
                shuttleStore.empty();
            }
        };
        scope.shuttle = {
            leftItems: shuttleStore.records,
            rightItems: params.record.params._items,
            displayName: params.record.params.shuttle.displayName
        };
    }


    return ViewModel;
});

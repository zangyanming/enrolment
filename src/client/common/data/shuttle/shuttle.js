define(['knockout','store'], function (ko,Store) {
    function ViewModel(params) {
        $.extend(this,params);

        var scope = this;

        if(scope.record&&scope.record.params.model){
            var store = new Store({
                model: scope.record.params.model,
                pageSize:0
            });

            scope.leftItems = store.records;
            scope.rightItems = scope.record.params._items;
            scope.displayName = scope.record.params.displayName;
        }

        if(!scope.displayName) scope.displayName = 'text';
        if(!scope.leftItems) scope.leftItems = ko.observableArray();
        if(!scope.rightItems) scope.rightItems = ko.observableArray();

        scope.leftChosenItems = ko.observableArray();
        scope.rightChosenItems = ko.observableArray();
        scope.onShuttle = function (op) {
            switch (op) {
                case 'ar':
                    pushItems(scope.leftItems(),scope.rightItems);
                    scope.leftItems.removeAll();
                    break;
                case 'r':
                    pushItems(scope.leftChosenItems(),scope.rightItems);
                    spliceItems(scope.leftChosenItems(),scope.leftItems);
                    break;
                case 'l':
                    pushItems(scope.rightChosenItems(),scope.leftItems);
                    spliceItems(scope.rightChosenItems(),scope.rightItems);
                    break;
                case 'al':
                    pushItems(scope.rightItems(),scope.leftItems);
                    scope.rightItems.removeAll();
                    break;
            }
        };

        function pushItems(items,container) {
            if(items.length>0){
                items.forEach(function (item) {
                    container.push(item);
                });
            }
        }

        function spliceItems(items,container) {
            if(items.length>0){
                items.forEach(function (item) {
                    container.remove(item);
                });
            }
        }
    }

    return ViewModel;
});

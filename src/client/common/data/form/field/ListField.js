define(['knockout'], function (ko) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        scope.field.cmp.params._items = ko.observableArray();
        scope.field.cmp.params._lastItems = ko.observableArray();
        scope.onFieldClick = function () {
            scope.field.cmp.params._items.removeAll();
            scope.field.cmp.params._lastItems().forEach(function(item){
                scope.field.cmp.params._items.push(item);
            });
            ko.root.openModal({
                title: '请选择', size: 'lg', record: {bodyCmp:scope.field.cmp.name,params:scope.field.cmp.params}, commit:
                    function (btn,record,callback) {
                        scope.field.cmp.params._lastItems.removeAll();
                        scope.field.cmp.params._items().forEach(function (item) {
                            scope.field.cmp.params._lastItems.push(item);
                        });
                        var ids = scope.field.cmp.params._lastItems().map(function (item) {
                            return item.id();
                        });
                        scope.record[scope.field.name](ids.join(','));
                        callback({success:true});
                    }
            });
        };
    }

    return ViewModel;
});

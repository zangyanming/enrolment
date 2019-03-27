define(['knockout', 'util'], function (ko, Util) {
    function ViewModel(params) {
        $.extend(this,{
            showOperation:true,
            displayField:'title',
            preOperation:function () {
                return true
            }
        });
        $.extend(this, params);

        var scope = this;

        scope._operations = Util.generateOperations(scope.operations);
        scope._onOperation = function (operation, record) {
            if (scope.preOperation(operation)) {
                if (scope.store) {
                    Util.operationSwitch(operation,record);
                }

                if (scope.onOperation) {
                    scope.onOperation(operation);
                }
            }
        }
        scope._lastItemIndex = -1;
        scope.store.addLoadListener('complete', function () {
            scope._lastItemIndex = -1;
        });

        scope._onItemClick = function (item) {
            if (scope.itemClick) {
                scope.itemClick(item);
            }

            var tempIndex = scope.store.records.indexOf(item)

            if (tempIndex == scope._lastItemIndex) {
                item._cssActive(false);
                scope._lastItemIndex = -1;
                if (scope.onNoneItemSelected) scope.onNoneItemSelected();
            }
            else {
                Util.changeCssActive(item, scope.store.records());
                if (scope._lastItemIndex > -1 && scope.onItemUnSelected) scope.onItemUnSelected(scope.store.records()[scope._lastItemIndex]);
                if (scope.onItemSelected) scope.onItemSelected(item);
                scope._lastItemIndex = tempIndex;
            }

            if(item._href&&item._href!='#'){
                return true;
            }
        }
    }

    return ViewModel;
});

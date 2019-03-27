define(['knockout', 'store'], function (ko, Store) {
    function ViewModel(params) {
        $.extend(this,{
            model:'model',
            operations:[],
            touchObj:{},
            _fixedQuery:this.fixedQuery
        });
        $.extend(this, params);

        var scope = this;

        scope._id = scope.model + '_list_' + new Date().getTime();
        scope._jqId = '#' + scope._id;

        if (scope.fixedQuery) $.extend(scope.fixedQuery, scope._fixedQuery);

        if(!scope.store){
            scope.store = new Store({
                model: scope.model,
                cache: true,
                id: scope.model + 'Store',
                fixedQuery: scope.fixedQuery,
                onInitComplete: function () {
                    if (scope.operations && scope.operations.length > 0) {
                        $(scope._jqId)[0].addEventListener('touchstart', function (event) {
                            if (event.touches.length == 1) {
                                for (var i = 0; i < event.path.length; ++i) {
                                    if (event.path[i].id) {
                                        scope.touchObj.id = event.path[i].id;
                                        scope.touchObj.x = event.touches[0].screenX;
                                        break;
                                    }
                                }
                            }
                        });

                        $(scope._jqId)[0].addEventListener('touchend', function (event) {
                            if (event.changedTouches.length == 1) {
                                var distance = scope.touchObj.x - event.changedTouches[0].screenX;

                                if (Math.abs(distance) > 30) {
                                    if (distance > 0) {
                                        if (scope.touchObj.lastShow) {
                                            $(scope.touchObj.lastShow)[0].classList.remove('show');
                                        }

                                        scope.touchObj.lastShow = '#' + scope.touchObj.id + '_operation';
                                        $(scope.touchObj.lastShow)[0].classList.add('show');
                                    }
                                    else {
                                        $('#' + scope.touchObj.id + '_operation')[0].classList.remove('show');
                                    }
                                }
                            }
                        });
                    }

                    if ($(scope._jqId).parent()[0].className == 'modal-body p-0') {
                        $(scope._jqId).parent().scroll(function () {
                            var scrollPos = this.scrollTop / (this.scrollHeight - this.clientHeight)

                            if (!isNaN(scrollPos) && scrollPos >= 1) {
                                scope.store.next();
                            }
                        });
                    }
                }
            });
        }

        scope._onItemClick = function (item) {
            hideOperation();

            if (scope.onItemClick) {
                scope.onItemClick(item);
            }
        };
        scope._onOperationClick = function (item, event, operation) {
            event.stopPropagation();
            hideOperation();

            switch (operation.cmp) {
                case 'delete':
                    ko.root.openModal({cmp: 'cmp:MobileConfirmModal', params: {level:'danger',operation: 'delete', record: item}});
                    break;
                default:
                    ko.root.openModal(
                        {title: operation.name, operation: operation.cmp, record: item}
                    );
            }
        };

        function hideOperation() {
            if (scope.touchObj.lastShow) {
                $(scope.touchObj.lastShow)[0].classList.remove('show');
                scope.touchObj.lastShow = '';
            }
        }
    }

    return ViewModel;
});

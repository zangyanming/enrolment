define(['store', 'mAudit','util'], function (Store, MAudit,Util) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        scope.auditCallback = function () {
            scope.store.load();
        };
        scope.onOperation = function(operation, record){
            var model = new MAudit({isKo: true});

            model.setProxy({url:Util.formatTpl(scope.url,record.id())});

            switch (operation.name) {
                case 'pass':
                    model.auditStatus(1);
                    break;
                case 'notPass':
                    model.auditStatus(2);
                    break;
            }

            model.save(scope.auditCallback, true);
        };

        scope.card = {
            title:scope.title,
            cardBody: {
                name: 'cmp:Grid',
                params: {
                    store: scope.store,
                    displayFields: scope.displayFields,
                    onOperation: function (operation, record) {
                        scope.onOperation(operation,record);
                    }
                }
            }
        };
    }

    return ViewModel;
});

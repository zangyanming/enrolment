define(['knockout','util'], function (ko,Util) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        if(scope.showOperation==undefined) scope.showOperation = true;
        scope._displayFields = ko.observableArray();

        if(scope.displayFields&&scope.displayFields.length>0){
            var tempFields = [];

            require([scope.store.model.name],function (model) {
                var tempModel = new model();

                scope.displayFields.forEach(function(field) {
                    //name:field,alias:tempModel.getField(field).alias
                    var tempField = {
                        _css:{},
                        _event:{}
                    };

                    if(field instanceof Object) {
                        tempField.name = field.name;
                        tempField.alias = tempModel.getField(field.name).alias;

                        if(field.click){
                            tempField._css['xgx-grid-cell-click'] = true;
                        }

                        if(scope.onCellClick){
                            tempField._event.click = function(data) {scope.onCellClick(data.withRecord,data.withField);};
                        }
                    }
                    else {
                        tempField.name = field;
                        tempField.alias = tempModel.getField(field).alias;
                    }

                    tempFields.push(tempField);
                });

                scope._displayFields(tempFields);
            });
        }

        if(!scope.preOperation) scope.preOperation = function(){return true};
        if(!scope.onOperation) scope.onOperation = function(){};

        scope._operations = Util.generateOperations(scope.operations);
        scope._onOperation = function (operation,record) {
            if(scope.preOperation(operation)) {
                if (scope.store) {
                    Util.operationSwitch(operation,record);
                }

                scope.onOperation(operation, record);
            }
        }
    }

    return ViewModel;
});

define(['knockout', 'util','store','config','knockout-file-bindings'], function (ko,Util,Store,Config) {
    function ViewModel(params) {
        $.extend(this,{
            onFieldBlur:function () {}
        });
        $.extend(this, params);
        var scope = this;

        scope._id = 'form_' + Date.parse(new Date());
        //store the form id for clear was-validate css if necessary
        scope.record._formId = scope._id;
        //the update operation will get a new record from server
        //we need hide the form until the ajax request is finish
        scope._showForm = ko.observable(false);
        scope._fieldSets = [];
        //the _buttons will show at the bottom of the form if the showButtons is true
        scope._showButtons = scope.showButtons?true:false;
        scope._buttons = ko.observableArray();
        scope._onButtonClick = function () {console.log('AForm:click callback not assign in child class')};

        //the buttons can show under a form when not in a modal
        if(scope._showButtons&&scope.buttons){
            scope._buttons(scope.buttons);

            if(scope.onButtonClick) scope._onButtonClick = scope.onButtonClick;
        }
        //the button can add to a modal parent's bottom
        if(scope.buttons&&scope._parent&&scope._parent._name == 'modal') {
            scope._parent._buttons(scope.buttons);
            scope._parent.commit = scope.onButtonClick;
        }

        if(scope.record.id() == Config.modelDefaultId){
            initFields();
            scope._showForm(true);
        }
        else{
            scope._showForm(false);
            Util.ajaxGet(Util.generateUrl(scope.record.getProxy())+'/'+scope.record.id(),{},function (data) {
               if(data.success){
                   scope.record._fullRecord = ko.storeCache[scope.record._storeId].createModelFormOBJ(data.data);
                   scope.record = scope.record._fullRecord;
                   initFields();
                   scope._showForm(true);
               }
               else{
                   Util.showAlert(data.message,'error');
               }
            });
        }

        function initFields() {
            if(scope.fields){
                scope.fieldSets = [{name:'', fields:scope.fields}];
            }

            if(scope.fieldSets&&scope.fieldSets.length>0){
                scope.fieldSets.forEach(function (fieldSet) {
                    scope._fieldSets.push({name:fieldSet.name,_fields:[]});

                    if(fieldSet.fields){
                        fieldSet.fields.forEach(function (field) {
                            var modelField = {};

                            if(field instanceof Object){
                                modelField = scope.record.getField(field.name);
                                $.extend(modelField,field);
                            }
                            else {
                                modelField = scope.record.getField(field);
                            }

                            if(modelField){
                                modelField.isFormField = true;
                                scope._fieldSets[scope._fieldSets.length-1]._fields.push(modelField);

                                if(modelField.type == 'select'&&modelField.model){
                                    var store = new Store({
                                        model:modelField.model,
                                        pageSize:0,
                                        onLoadComplete:function () {
                                            modelField.options(store.records());
                                        }
                                    });
                                }

                                addFileListener(modelField);
                            }
                        });
                    }
                });
            }
        }

        function addFileListener(field){
            if(field.preview&&field.type == 'file'){
                if(field._mimeType == 'img'){
                    ko.computed(function () {
                        var file = scope.record[field.name].file==undefined?null:scope.record[field.name].file();

                        if(file){
                            var reader = new FileReader();

                            reader.onload = function (e) {
                                scope.record['_preview_' + field.name](e.target.result);
                            }

                            reader.readAsDataURL(file)
                        }
                        else{
                            scope.record['_preview_' + field.name](scope.record[field.name.substr(0,field.name.length-1)]());
                        }
                    });
                }
            }
        }
    }

    ViewModel.prototype.isView = function(fieldName) {
        if(this.operation != 'view'){
            var field = this.record.getField(fieldName);

            if(field&&(field.readonly||field.disabled)){
               return true;
            }
            else{
                return false;
            }
        }

        return true;
    }

    return ViewModel;
});

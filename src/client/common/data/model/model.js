define(['knockout', 'util', 'config'], function (ko, Util, Config) {
    function Model(params) {
        if (!params) params = {};
        //the params contains the config
        var scope = this;
        //the store need get url form a model instance when the store url not assigned
        if (scope.proxy) {
            scope.proxy.type = scope.proxy.type ? scope.proxy.type : 'rest';
        }
        else {
            scope.proxy = {type: 'rest', url: ''};
        }

        if (!scope.fields) scope.fields = [];
        //skip init logic when requestUrl is true
        if (!params.requestUrl) {
            scope.extraData = {};

            if (!scope.preSave) scope.preSave = function () {
                return true
            };

            scope.fields.forEach(function (field) {
                field.serverCheck = {success: true, message: ''};

                if (field.type == undefined) {
                    field.type = 'text';
                }

                if (field.defaultValue == undefined) {
                    switch (field.type) {
                        case 'number':
                            field.defaultValue = NaN;
                            break;
                        case 'select':
                            if (!field.options) field.options = ko.observableArray();
                            if (!field.optionsText) field.optionsText = 'text';
                            if (!field.optionsValue) field.optionsValue = 'value'

                            field.defaultValue = undefined;
                            break;
                        case 'file':
                            field.defaultValue = {fileArray: [], file: null};
                            break;
                        case 'checkboxs':
                            field.defaultValue = [];
                            break;
                        case 'date':
                            field.defaultValue = null;
                            break;
                        case 'checkbox':
                            field.defaultValue = false;
                            break;
                        default:
                            field.defaultValue = '';
                    }
                }

                field._attr = {};

                if (field.type == 'text' || field.type == 'password' || field.type == 'date' || field.type == 'number') {
                    field._attr['type'] = field.type;

                    // if(field.type == 'number'){
                    //     field._attr['min'] = field.min = (field.min==undefined?0:field.min);
                    //     field._attr['max'] = field.max = (field.max==undefined?1000000:field.max);
                    // }
                }

                if (field.type == 'file') {
                    field._attr.placeholder = '未选择文件';

                    if (field.accept) {
                        field._attr['accept'] = field.accept;

                        if (field.accept.indexOf('image') > -1) {
                            field._mimeType = 'img';
                        }
                    }

                    if (field.preview) {
                        scope['_preview_' + field.name] = ko.observable('');
                    }
                    else {
                        field.preview = false;
                    }
                }
                else {
                    field._attr['placeholder'] = field.alias;
                }

                // if(field.readonly){
                //     field._attr['readonly'] = true;
                // }

                field._attr['id'] = field.type + '_' + field.name;
                field.validateMessage = ko.observable();

                if (field.validator && field.validator.length > 0) {
                    field._validator = [];
                    field.validator.forEach(function (v) {
                        switch (v) {
                            case 'required':
                                field._validator.push({name: 'required', message: '必须填写'});
                                field._attr[v] = true;
                                break;
                            case 'number':
                                field._validator.push({name: 'number', message: '数值错误'});
                                field._attr['min'] = field.min = (field.min == undefined ? 0 : field.min);
                                field._attr['max'] = field.max = (field.max == undefined ? 1000000 : field.max);
                                break;
                        }
                    });

                    field.validateMessage(field._validator[0].message);
                }
            });
        }

        scope.fields.push({name: 'id', alias: '编号', defaultValue: Config.modelDefaultId});
        scope.fields.push({name: 'rowId', alias: '行号'});

        if (params && params.isKo) {
            var obj = {};

            scope.fields.forEach(function (field) {
                if (field.name != 'rowId') {
                    obj[field.name] = field.defaultValue;
                }
            });

            if (params.default && params.default instanceof Object) {
                for (var p in params.default) {
                    obj[p] = params.default[p];
                }
            }

            $.extend(scope, ko.mapping.fromJS(obj));

            scope._original = JSON.stringify(obj);
        }
    }

    //callback(data) when ajax is success
    //forceSave  to save the model when the record is not edit
    Model.prototype.save = function (callback, forceSave) {
        var scope = this;

        if (scope.isDirty() || forceSave) {
            if (scope.checkValidity()) {
                if (scope.preSave()) {
                    var url = Util.generateUrl(scope.proxy);

                    if (scope.isFormSubmit()) {
                        Util.ajaxForm(url, this, function (data) {
                            scope._ajaxCallback(data, callback);
                        });
                    }
                    else {
                        var jsonObj = ko.mapping.toJS(scope);

                        $.extend(jsonObj, scope.extraData);
                        scope.extraData = {};
                        //add operation or confirm
                        if (scope.id() == Config.modelDefaultId||scope.id() == 'confirm') {
                            Util.ajaxPost(url, jsonObj, function (data) {
                                scope._ajaxCallback(data, callback);
                            });
                        }
                        else {//update operation
                            var tempUrl = url + '/' + scope.id();

                            if (this.proxy.type == 'ajax') {
                                tempUrl = url;
                            }

                            Util.ajaxPut(tempUrl, jsonObj, function (data) {
                                scope._ajaxCallback(data, callback);
                            });
                        }
                    }
                }
            }
        }
        else {
            Util.showAlert('请修改表单项', 'warning');
        }
    }

    Model.prototype.delete = function (callback) {
        var scope = this;
        var url = Util.generateUrl(scope.proxy);
        var tempUrl = url + '/' + scope.id();
        var jsonObj = {};

        if (this.proxy.type == 'ajax') {
            tempUrl = url;
            jsonObj = ko.mapping.toJS(scope);
        }

        Util.ajaxDelete(tempUrl, jsonObj, function (data) {
            scope._ajaxCallback(data, callback);
        });
    }

    Model.prototype._ajaxCallback = function (data,callback) {
        Util.ajaxCallback(data,callback,this._storeId);
    }

    Model.prototype.isDirty = function () {
        if (this._original) {
            if (this._original === ko.mapping.toJSON(this)) {
                return false;
            }
            else {
                return true;
            }
        }

        return false;
    }

    Model.prototype.revert = function () {
        var scope = this;

        if (scope._original) {
            var original = ko.mapping.fromJSON(scope._original);

            scope.fields.forEach(function (field) {
                if (field.name != 'rowId') {
                    switch (field.type) {
                        case 'file':
                            scope[field.name].clear();
                            scope[field.name].fileArray.removeAll();
                            break;
                        case 'list':
                            field.cmp.params._lastItems.removeAll();
                        default:
                            if (original[field.name]) {
                                scope[field.name](original[field.name]());
                            }
                            else {
                                if (scope[field.name]) {
                                    scope[field.name](undefined);
                                }
                            }
                    }
                }
            });
        }
    }

    Model.prototype.getField = function (name) {
        var rtn = undefined;

        if (this.fields && this.fields.length > 0) {
            for (var fIndex = 0; fIndex < this.fields.length; ++fIndex) {
                if (name == this.fields[fIndex].name) {
                    rtn = this.fields[fIndex];
                    break;
                }
            }
        }

        return rtn;
    }

    Model.prototype.isFormSubmit = function () {
        if (this.fields && this.fields.length > 0) {
            for (var fIndex = 0; fIndex < this.fields.length; ++fIndex) {
                if ('file' == this.fields[fIndex].type) {
                    return true;
                }
            }
        }

        return false;
    }

    Model.prototype.checkValidity = function () {
        var scope = this;
        var forms = $('.needs-validation');

        if (forms.length > 0 && !forms[forms.length - 1].checkValidity()) {
            if (scope.fields && scope.fields.length > 0) {
                scope.fields.forEach(function (field) {
                    if (field.isFormField && field._validator && field._validator.length > 0) {
                        var fieldValue;

                        if (field.type == 'file') {
                            fieldValue = scope[field.name].file();
                        }
                        else {
                            fieldValue = scope[field.name] instanceof Function ? scope[field.name]() : undefined;
                        }

                        for (var vIndex = 0; vIndex < field._validator.length; vIndex++) {
                            if (field._validator[vIndex].name == 'required') {
                                if (fieldValue == undefined || isNaN(fieldValue) || (typeof fieldValue == 'string' && fieldValue.trim() == '')) {
                                    field.validateMessage(field._validator[vIndex].message);
                                    break;
                                }
                            }

                            if (field._validator[vIndex].name == 'number') {
                                if (isNaN(Number(fieldValue)) || fieldValue < field.min || fieldValue > field.max) {
                                    field.validateMessage(field._validator[vIndex].message);
                                    break;
                                }
                            }

                            if (field._validator[vIndex].name == 'idcard') {
                                if (!RegExp(field._attr.pattern).test(fieldValue)) {
                                    field.validateMessage(field._validator[vIndex].message);
                                    break;
                                }
                            }
                        }
                    }
                })
            }

            if (!forms[forms.length - 1].classList.contains('was-validated')) {
                forms[forms.length - 1].classList.add('was-validated');
            }

            Util.showAlert('表单验证失败', 'error');

            return false;
        }

        return true;
    }

    Model.prototype.getFormData = function () {
        var scope = this;
        var formData = {};

        if (scope.fields && scope.fields.length > 0) {
            scope.fields.forEach(function (field) {
                if (field.isFormField) {
                    formData[field.name] = scope[field.name]();
                }
            })
        }

        return formData;
    }
    //check the fields empty
    Model.prototype.allFieldsAssigned = function (fieldNames) {
        var scope = this;

        function rtn(state, fieldAlias) {
            return {success: state, message: fieldAlias + ' 必须填写'};
        }

        for (var fIndex = 0; fIndex < fieldNames.length; ++fIndex) {
            var fieldName = fieldNames[fIndex] instanceof Object ? fieldNames[fIndex].name : fieldNames[fIndex];
            var fieldValue = null;
            var field = scope.getField(fieldName);

            switch (field.type) {
                case 'text':
                case 'date':
                case 'password':
                case 'number':
                case 'textarea':
                    fieldValue = scope[fieldName]();
                    if (!fieldValue) return rtn(false, field.alias);
                    if (fieldValue.length == 0) return rtn(false, field.alias);
                    break;
                case 'checkboxs':
                    fieldValue = scope[fieldName]();
                    if (fieldValue.length == 0) return rtn(false, field.alias);
                    break;
                case 'select':
                    fieldValue = scope[fieldName]();
                    if (!fieldValue) return rtn(false, field.alias);
                    if (fieldValue <= 0) return rtn(false, field.alias);
                    break;
                case 'file':
                    if (scope[fieldName.substr(0, fieldName.length - 1)] &&
                        scope[fieldName.substr(0, fieldName.length - 1)]() &&
                        scope[fieldName.substr(0, fieldName.length - 1)]().length > 0) {
                    }
                    else {
                        fieldValue = scope[fieldName].file();
                        if (!fieldValue) return rtn(false, field.alias);
                    }

                    break;
            }
        }

        return rtn(true);
    };

    Model.prototype.setProxy = function (proxy) {
        $.extend(this.proxy, proxy);
    };

    Model.prototype.getProxy = function () {
        return this.proxy;
    };

    Model.prototype.getStoreId = function () {
        return this._storeId;
    };

    return Model;
});

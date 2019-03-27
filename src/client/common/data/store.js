define(['knockout', 'util', 'knockout-mapping'], function (ko, Util) {
    function Store(params) {
        //the params contains the config
        $.extend(this, {
            autoLoad: true,
            pageSize: 10,
            pageNo: 1,
            cache: false,//cache the store for mobile
            proxy: {
                type: 'rest',
                url: '',
                originalUrl:''
            },
            //use the _model in the callback
            onInitComplete: undefined,
            query: {},//the form query
            fixedQuery: {},
            model:'model',
            initWith:undefined
        });
        $.extend(this, params);

        if(!this.proxy.type) this.proxy.type = 'rest';

        var scope = this;

        scope._loadListeners ={
            'complete':[]
        };
        scope.totalCount = ko.observable();
        scope.records = ko.observableArray();
        scope.pageCount = 0;

        if (scope.id) {
            ko.storeCache[scope.id] = scope;
        }

        if (scope.model) {
            //change the model to a object for add other property dynamically
            scope.model = {name: scope.model};

            //dynamic load model class and invoke the finish callback if necessary
            require([scope.model.name], function (model) {
                scope._model = model;
                scope._modelInstance = new model({requestUrl:true});

                if (!scope.proxy.url) {
                    scope.setProxy(scope._modelInstance.getProxy())
                }

                scope.proxy.originalUrl = scope.proxy.url;

                if(scope.initWith){
                    scope.initRecords(scope.initWith);
                }
                else if(scope.autoLoad) {
                    scope.load();
                }

                if (scope.onInitComplete) {
                    scope.onInitComplete();
                }
            });
        }
    }

    Store.prototype.first = function() {
        if(this.pageNo!=1){
            this.pageNo = 1;
            this.load();

            return true;
        }

        return false;
    };

    Store.prototype.next = function () {
        if (this.pageNo < this.pageCount) {
            ++this.pageNo;
            this.load();

            return true;
        }

        return false;
    };

    Store.prototype.pre = function () {
        if (this.pageNo > 1) {
            --this.pageNo;
            this.load();

            return true;
        }

        return false;
    };

    Store.prototype.last = function(){
        if(this.pageNo != this.pageCount) {
            this.pageNo = this.pageCount;
            this.load();

            return true;
        }

        return false;
    };

    Store.prototype.createModel = function () {
        var scope = this;
        var model = new scope._model({isKo: true, default: this.model.default});

        model.setProxy(scope.getProxy());


        if (scope.id) {
            model._storeId = scope.id;
        }

        return model;
    };
    //callback invoked after load finish
    //firstPage is true will go to page 1
    Store.prototype.load = function (params) {
        if(!params) params = {};

        var scope = this;
        var callback,firstPage;

        if(params){
            callback = params.callback;
            firstPage = params.firstPage?true:false;
        }

        if (scope.proxy.url) {
            var url = Util.generateUrl(scope.proxy);
            var queryParams = {};

            $.extend(queryParams, scope.fixedQuery);
            $.extend(queryParams, scope.query);

            if(firstPage) {
                scope.totalCount(0);
                scope.pageNo = 1;
            }

            if (scope.pageNo > 0 && scope.pageSize > 0) {
                $.extend(queryParams, {pageNo: scope.pageNo, pageSize: scope.pageSize});
            }

            Util.ajaxGet(url, queryParams, function (data) {
                if (data.success) {
                    if (!scope.cache) scope.records.removeAll();
                    scope.totalCount(data.totalCount);

                    if (scope.pageSize > 0) {
                        scope.pageCount = Math.ceil(scope.totalCount() / scope.pageSize);
                    }

                    if(data.data&&data.data instanceof Array){
                        for (var rIndex = 0; rIndex < data.data.length; ++rIndex) {
                            var model = scope.createModelFormOBJ(data.data[rIndex]);

                            if (scope.pageNo > 0 && scope.pageSize > 0) {
                                model.rowId = ko.observable((scope.pageNo - 1) * scope.pageSize + rIndex + 1);
                            }
                            else {
                                model.rowId = rIndex + 1;
                            }

                            scope.records.push(model);
                        }
                    }

                    if (callback) callback();
                    if (scope.onLoadComplete) scope.onLoadComplete();

                    scope._loadListeners['complete'].forEach(function (listener) {
                        listener();
                    });
                }
            });
        }
    };

    Store.prototype.initRecords = function (data) {
        var scope = this;

        data.forEach(function (record) {
            scope.records.push(scope.createModelFormOBJ(record));
        });
    };

    Store.prototype.createModelFormOBJ = function (obj) {
        var scope = this;
        var parentModel = new scope._model();

        parentModel.fields.forEach(function (field) {
            //skip the id and rowId because the field's type is undefined
            if (field.type) {
                switch (field.type) {
                    case 'file':
                        obj[field.name] = field.defaultValue;
                        break;
                    default:
                        if (obj[field.name]==undefined) {
                            obj[field.name] = field.defaultValue;
                        }
                }
            }
        });

        var record = ko.mapping.fromJS(obj);
        var model = $.extend(parentModel, record);

        model._original = JSON.stringify(obj);//for model revert
        model._cssActive = ko.observable(false);//add the ko field for ui active
        model._icon = {};//add icon css field for ui bind
        model._operations = [];//add record level operation，
        model._href = '#' + (obj.href?obj.href:'');//for router

        if (obj.icon) {
            model._icon = Util.zico(obj.icon);
        }

        if(obj.operations&&obj.operations.length>0){
            model._operations = Util.generateOperations(obj.operations);
        }

        if (scope.id) {
            model._storeId = scope.id;
        }

        model.setProxy(scope.getProxy());

        return model;
    };

    Store.prototype.empty = function () {
        this.records.removeAll();
        this.totalCount(0);
        this.pageNo = 1;
    };

    Store.prototype.setProxy = function(proxy){
      $.extend(this.proxy,proxy);
    };

    Store.prototype.getProxy = function(){
        return this.proxy;
    };

    Store.prototype.addLoadListener = function (key,callback) {
        if(this._loadListeners[key]){
            this._loadListeners[key].push(callback);
        }
    };
    //find a record with id from the store cache
    Store.prototype.getRecordById = function (id) {
        var record = undefined;
        var records = this.records();

        if(records&&records.length>0){
            for(rIndex=0;rIndex<records.length;++rIndex){
                if(records[rIndex].id()==id){
                    record = records[rIndex];

                    break;
                }
            }
        }

        return record;
    };

    Store.prototype.getModelFields = function () {
        if(this._modelInstance){
            return this._modelInstance.fields;
        }
    };

    Store.prototype.getModelField = function (name) {
        if(this._modelInstance){
            return this._modelInstance.getField(name);
        }
    };

    return Store;
});

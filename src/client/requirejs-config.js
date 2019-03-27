var requirejs = function (config) {
    return config;
};

var r_config = requirejs({
    baseUrl: './',
    paths: {
        //node modules js
        //==require js
        'text': 'node_modules/requirejs-text/text',
        'css': 'node_modules/require-css/css',
        'css-builder': 'node_modules/require-css/css-builder',
        'normalize': 'node_modules/require-css/normalize',
        //knockout
        'knockout': 'node_modules/knockout/build/output/knockout-latest',
        'knockout-mapping': 'node_modules/knockout-mapping/dist/knockout.mapping',
        'knockout-file-bindings': 'node_modules/knockout-file-bindings-xgx/knockout-file-bindings',
        //jquery
        'jquery': 'node_modules/jquery/dist/jquery',
        'jquery.cookie': 'node_modules/jquery.cookie/jquery.cookie',
        //bootstrap
        'bootstrap': 'node_modules/bootstrap/dist/js/bootstrap.bundle',
        'bootstrap-treeview': 'node_modules/bootstrap-treeview-min/dist/bootstrap-treeview.min',
        //tip
        'toastr': 'node_modules/toastr-min/build/toastr',
        //jqx
        'jqxcore': 'node_modules/jqwidgets-scripts/jqwidgets/jqxcore',
        'jqxdata': 'node_modules/jqwidgets-scripts/jqwidgets/jqxdata',
        'jqxbuttons': 'node_modules/jqwidgets-scripts/jqwidgets/jqxbuttons',
        'jqxscrollbar': 'node_modules/jqwidgets-scripts/jqwidgets/jqxscrollbar',
        'jqxdatatable': 'node_modules/jqwidgets-scripts/jqwidgets/jqxdatatable',
        'jqxtreegrid': 'node_modules/jqwidgets-scripts/jqwidgets/jqxtreegrid',
        'jqxchart':'node_modules/jqwidgets-scripts/jqwidgets/jqxchart',
        'jqxcheckbox':'node_modules/jqwidgets-scripts/jqwidgets/jqxcheckbox',
        'jqxknockout':'node_modules/jqwidgets-scripts/jqwidgets/jqxknockout',
        //director
        'director':'node_modules/director/build/director',
        //node modules css
        'css-toastr': 'node_modules/toastr-min/build/toastr',
        'css-jqx-base': 'node_modules/jqwidgets-scripts/jqwidgets/styles/jqx.base',
        'css-jqx-bootstrap': 'node_modules/jqwidgets-scripts/jqwidgets/styles/jqx.bootstrap',
        //common modules
        'config': 'common/config',
        'registerCommon': 'common/RegisterCommon',
        'root': 'common/KoRoot',
        'util': 'common/util',
        'store': 'common/data/store',
        'model': 'common/data/model/model',
        'mPassword': 'common/data/model/MPassword',
        'mLogin': 'common/data/model/MLogin',
        'aForm': 'common/data/form/AForm',
        'aFormCard': 'common/card/AFormCard',
        'mobileTabMain': 'common/main/MobileTabMain',
        'aMobileList': 'common/data/list/AMobileList',
        'mUpload': 'common/data/model/MUpload',
        'mConfirm': 'common/data/model/MConfirm',
        'mAjax': 'common/data/model/MAjax',
        'mAudit': 'common/data/model/MAudit',
        //common css
        'css-jqxcustom':'common/assets/css/jqxcustom',
        'css-treeview': 'common/assets/css/treeview'
    },
    shim: {
        'knockout-mapping': ['knockout'],
        'knockout-file-bindings': ['knockout'],
        'bootstrap': ['jquery'],
        'bootstrap-treeview': ['jquery', 'bootstrap', 'css!css-treeview'],
        'toastr': ['css!css-toastr'],
        'jquery.cookie': ['jquery'],
        'director':{exports:'Router'},
        'root': ['text', 'bootstrap', 'knockout-mapping'],
        'css!css-jqx-bootstrap': ['css!css-jqx-base'],
        'css!css-jqxcustom':['css!css-jqx-bootstrap'],
        'jqxcore': ['css!css-jqxcustom'],
        'jqxdata':  ['jqxcore'],
        'jqxbuttons': ['jqxcore'],
        'jqxscrollbar':['jqxcore'],
        'jqxdatatable': ['jqxcore'],
        'jqxtreegrid':  ['jqxcore','jqxdatatable'],
        'jqxchart':['jqxcore'],
        'jqxcheckbox':['jqxcore'],
        'jqxknockout':['jqxcore']
    }
});


requirejs = undefined;

//the class extend function
Function.prototype.defineMethod = function (methodName, methodBody) {
    this.prototype[methodName] = methodBody;
    methodBody.$name = methodName;
    this.$owner = this;
};

Function.prototype.extend = function (baseType) {
    var tempType = function () {
    };
    tempType.prototype = baseType.prototype;

    this.prototype = new tempType();
    this.prototype.constructor = this;
    this.prototype.callParent = function () {
        var method = arguments.callee.caller;

        return method.$owner.$baseType.prototype[method.$name].apply(this, arguments);
    };

    this.$baseType = baseType;
    this.defineMethod('constructor', this.prototype.constructor);
};

Array.prototype.remove = function (val) {
    var index = this.indexOf(val);

    if (index > -1) {
        this.splice(index, 1);
    }
};

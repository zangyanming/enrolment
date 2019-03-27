define(['knockout', 'jqxknockout', 'jqxcore', 'jqxdata', 'jqxchart', 'jqxbuttons', 'jqxcheckbox'], function () {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        scope._id = 'chart' + Date.parse(new Date());
        scope._jqId = '#' + scope._id;
        scope._css = {};
        scope._style = {
            height: scope.height?scope.height+'px':'300px',
            minHeight: scope.minHeight?scope.minHeight+'px':'300px'
        };
        scope.jqxChart = scope.settings;
    }

    return ViewModel;
});

define([], function () {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        scope.confirmInfo = '请选择  ‘确定’ 或  ‘取消’';

        switch (params.operation) {
            case 'delete':
                scope.confirmInfo = '是否删除?';
                break;
            default:
                if(params.record.confirmInfo)
                    scope.confirmInfo = params.record.confirmInfo;
                break;
        }
    }

    return ViewModel;
});

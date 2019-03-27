define(['aForm'], function (Form) {
    function ViewModel(params) {
        var scope = this;

        scope.buttons = [{name:'send',title:'发送'}];
        scope.showButtons = true;
        scope.onButtonClick = function(button){
            scope.record.save(function (data) {
                if(data.success){
                    scope.record.revert();
                }
            });
        };
        scope.callParent(params);
    }

    ViewModel.extend(Form);

    return ViewModel;
});

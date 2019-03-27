define(['aMessageForm'], function (Form) {
    function ViewModel(params) {
        var scope = this;

        scope.fields = ['sysCode','sysKey','receiverIds','content','audit'];
        scope.callParent(params);
    }

    ViewModel.extend(Form);

    return ViewModel;
});

define(['aForm'], function (Form) {
    function ViewModel(params) {
        var scope = this;

        scope.fields = [ 'name','sysCode','applicationUnit','applicant','email','mobile'];
        scope.callParent(params);
    }

    ViewModel.extend(Form);

    return ViewModel;
});

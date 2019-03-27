define(['aForm'], function (Form) {
    function ViewModel(params) {
        var scope = this;

        scope.fields = ['name','tel','email','wxhm'];
        scope.callParent(params);
    }

    ViewModel.extend(Form);

    return ViewModel;
});

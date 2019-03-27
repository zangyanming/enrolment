define(['aForm'], function (Form) {
    function ViewModel(params) {
        var scope = this;

        scope.fields = ['name'];

        scope.callParent(params);
    }

    ViewModel.extend(Form);

    return ViewModel;
});

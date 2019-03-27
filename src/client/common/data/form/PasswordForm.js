define(['form'], function (Form) {
    function ViewModel(params) {
        $.extend(this, params);

        this.fields = ['oldPassword', 'newPassword','confirmNewPassword'];

        this.callParent(params);
    }

    ViewModel.extend(Form);

    return ViewModel;
});
define(['aForm'], function (Form) {
    function ViewModel(params) {
        $.extend(this, params);

        this.fields = ['upload'];

        this.callParent(params);
    }

    ViewModel.extend(Form);

    return ViewModel;
});

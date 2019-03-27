define(['aForm'], function (Form) {
    function ViewModel(params) {
        $.extend(this, params);

        this.fields = ['content'];
        this.buttons = [
            {
                name: 'pass', title: '通过'
            },
            {
                name: 'notPass', title: '不通过'
            }
        ];
        this.callParent(params);
    }

    ViewModel.extend(Form);

    return ViewModel;
});

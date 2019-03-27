define(['aMessageForm'], function (Form) {
    function ViewModel(params) {
        var scope = this;

        scope.fields = ['receiverIds','subject','content','upload','audit'];
        scope.callParent(params);
    }

    ViewModel.extend(Form);

    return ViewModel;
});

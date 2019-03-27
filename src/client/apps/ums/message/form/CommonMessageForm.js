define(['aMessageForm'], function (Form) {
    function ViewModel(params) {
        var scope = this;

        scope.fields = ['receiverIds','content','audit'];
        scope.callParent(params);
    }

    ViewModel.extend(Form);

    return ViewModel;
});

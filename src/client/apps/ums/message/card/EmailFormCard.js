define(['aFormCard'],function (AFormCard) {
    function ViewModel() {
        this.callParent({title:'电子邮件',model:'mEmail',form:'cmp:EmailForm'});
    }

    ViewModel.extend(AFormCard)

    return ViewModel;
});

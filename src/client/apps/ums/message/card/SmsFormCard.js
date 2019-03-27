define(['aFormCard'],function (AFormCard) {
    function ViewModel() {
        this.callParent({title:'短信息',model:'mSms',form:'cmp:CommonMessageForm'});
    }

    ViewModel.extend(AFormCard)

    return ViewModel;
});

define(['aFormCard'],function (AFormCard) {
    function ViewModel() {
        this.callParent({title:'微信消息',model:'mWeiChat',form:'cmp:CommonMessageForm'});
    }

    ViewModel.extend(AFormCard)

    return ViewModel;
});

define(['aFormCard'],function (AFormCard) {
    function ViewModel() {
        this.callParent({title:'系统消息',model:'mSystemMessage',form:'cmp:SystemMessageForm'});
    }

    ViewModel.extend(AFormCard)

    return ViewModel;
});

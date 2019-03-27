define(['store', 'mAudit','aCommonAuditCard'], function (Store, MAudit,ACommonAuditCard) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        scope.url = '/rest/wxMessageAudits/{0}/audit';
        scope.title = '微信消息';
        scope.displayFields = ['rowId', 'content', 'auditStatus'];

        scope.store = new Store({
            model: 'mWeiChatAudit',
            id: 'weiChatAuditStore'
        });

        this.callParent(params);
    }

    ViewModel.extend(ACommonAuditCard);

    return ViewModel;
});

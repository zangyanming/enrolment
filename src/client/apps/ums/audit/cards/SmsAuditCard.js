define(['store', 'mAudit','aCommonAuditCard'], function (Store, MAudit,ACommonAuditCard) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        scope.url = '/rest/smsMessageAudits/{0}/audit';
        scope.title = '短信息';
        scope.displayFields = ['rowId', 'content', 'auditStatus'];

        scope.store = new Store({
            model: 'mSmsAudit',
            id: 'smsAuditStore'
        });

        this.callParent(params);
    }

    ViewModel.extend(ACommonAuditCard);

    return ViewModel;
});

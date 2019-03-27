define(['store', 'mAudit','aCommonAuditCard'], function (Store, MAudit,ACommonAuditCard) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        scope.url = '/rest/systemMessageAudits/{0}/audit';
        scope.title = '系统消息';
        scope.displayFields = ['rowId','sendName', 'content', 'auditStatus'];

        scope.store = new Store({
            model: 'mSystemAudit',
            id: 'systemAuditStore'
        });

        this.callParent(params);
    }

    ViewModel.extend(ACommonAuditCard);

    return ViewModel;
});

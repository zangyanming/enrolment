define(['store', 'mAudit','util','aCommonAuditCard'], function (Store, MAudit,Util,ACommonAuditCard) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        scope.url = '/rest/emailMessageAudits/{0}/audit';
        scope.title = '电子邮件';
        scope.displayFields = ['rowId', 'content', 'auditStatus'];

        scope.store = new Store({
            model: 'mEmailAudit',
            id: 'emailAuditStore'
        });

        this.callParent(params);
    }

    ViewModel.extend(ACommonAuditCard);

    return ViewModel;
});

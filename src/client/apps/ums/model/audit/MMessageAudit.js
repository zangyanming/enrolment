define(['model'], function (Model) {
    function MessageAudit(params) {
        if (!this.fields) this.fields = [];

        this.fields = this.fields.concat([
            {name: 'receiverIds', alias: '接收者ids'},
            {name: 'content', alias: '内容'},
            {name: 'auditBy', alias: '审核者'},
            {name: 'auditDate', alias: '审核时间'},
            {name: 'auditStatus', alias: '审核状态ID'},
            {name: 'auditReason', alias: ''},
            {name: 'auditStatusName',alias:'审核状态'}
        ]);

        this.callParent(params);
    }

    MessageAudit.extend(Model);

    return MessageAudit;
});

define(['mMessageAudit'], function (MMessageAudit) {
    function EmailAudit(params) {
        this.fields = [
            {name: 'sendBy', alias: '发送者'},
            {name: 'receiverEmails', alias: '接收者'},
            {name: 'subject', alias: '主题'},
            {name:'attachName',alias:'附件'}
        ];

        this.proxy = {
            url: '/emailMessageAudits'
        };
        this.callParent(params);
    }

    EmailAudit.extend(MMessageAudit);

    return EmailAudit;
});

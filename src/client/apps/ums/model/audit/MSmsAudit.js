define(['mMessageAudit'], function (MMessageAudit) {
    function SmsAudit(params) {
        this.fields = [
            {name: 'senderOfficeId', alias: '发送部门id'},
            {name: 'senderId', alias: '发送者id'},
            {name: 'receiverNames', alias: '接收者'},
            {name: 'receiverPhones', alias: '接收者号码'},
        ];

        this.proxy = {
            url: '/smsMessageAudits'
        };
        this.callParent(params);
    }

    SmsAudit.extend(MMessageAudit);

    return SmsAudit;
});

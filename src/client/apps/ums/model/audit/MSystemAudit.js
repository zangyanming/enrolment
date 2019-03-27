define(['mMessageAudit'], function (MMessageAudit) {
    function SystemAudit(params) {
        this.fields = [
            {name: 'sendName', alias: '系统名称'},
            {name: 'sendCode', alias: '系统编码'},
            {name: 'sendKey', alias: '系统秘钥'},
            {name: 'receiverNames', alias: '接收者'},
            {name: 'receiverCodes', alias: '接收者编码'}
        ];

        this.proxy = {
            url: '/systemMessageAudits'
        };
        this.callParent(params);
    }

    SystemAudit.extend(MMessageAudit);

    return SystemAudit;
});

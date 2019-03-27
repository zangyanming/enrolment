define(['mMessageAudit'], function (MMessageAudit) {
    function WeiChatAudit(params) {
        this.fields = [
            {name: 'senderOfficeId', alias: '发送部门id'},
            {name: 'senderId', alias: '发送者id'},
            {name: 'receiverNames', alias: '接收者'},
            {name: 'receiverOpenids', alias: '接收者号码'},
        ];

        this.proxy = {
            url: '/wxMessageAudits'
        };
        this.callParent(params);
    }

    WeiChatAudit.extend(MMessageAudit);

    return WeiChatAudit;
});

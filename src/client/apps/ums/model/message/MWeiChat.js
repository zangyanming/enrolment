define([ 'mMessage'], function (MMessage) {
    function WeiChat(params) {
        this.proxy = {
            url:  '/wx'
        };
        this.callParent(params);
    }

    WeiChat.extend(MMessage);

    return WeiChat;
});

define([ 'mMessage'], function (MMessage) {
    function Sms(params) {
        this.proxy = {
            url:  '/sms'
        };
        this.callParent(params);
    }

    Sms.extend(MMessage);

    return Sms;
});

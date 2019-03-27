define(['mMessage'], function (MMessage) {
    function Email(params) {
        this.fields = [
            {name: 'subject', alias: '主题',validator: ['required']},
            {name: 'upload', alias: '附件', type: 'file'}
        ];
        this.proxy = {
            url:'/email'
        };
        this.callParent(params);
    }

    Email.extend(MMessage);

    return Email;
});

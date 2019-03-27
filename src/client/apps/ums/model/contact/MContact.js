define(['model'], function (Model) {
    function Contact(params) {
        this.fields=[
            {name:'name',alias:'姓名',validator: ['required']},
            {name:'wxhm',alias:'微信'},
            {name:'email',alias:'邮箱',validator: ['required']},
            {name:'tel',alias:'手机',validator: ['required']}
        ];

        this.proxy = {
            url: '/groups/{0}/contacts',
        };
        this.form ='ContactForm';
        this.callParent(params);
    }

    Contact.extend(Model);

    return Contact;
});

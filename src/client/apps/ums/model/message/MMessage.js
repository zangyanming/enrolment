define(['model'], function (Model) {
    function Message(params) {
        if (!this.fields) this.fields = [];
        this.fields.push(
            {
                name: 'receiverIds', alias: '接收者', type: 'list', cmp: {
                    name: 'cmp:ListShuttle',
                    params: {
                        list: {model: 'mGroup', displayName: 'name'},
                        shuttle: {model: 'mContact', displayName: 'name'}
                    }
                },validator: ['required']
            });
        this.fields.push(
            {name: 'content', alias: '内容', type: 'textarea',validator: ['required']});
        this.fields.push({name: 'audit', alias: '',label:'审核', type: 'checkbox'});

        this.callParent(params);
    }

    Message.extend(Model);

    return Message;
});

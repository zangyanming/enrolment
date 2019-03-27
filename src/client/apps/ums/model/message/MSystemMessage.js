define([ 'model'], function (Model) {
    function SystemMessage(params) {
        this.fields = [
            {name:'sysCode',alias:'系统编码',validator: ['required']},
            {name:'sysKey',alias:'秘钥',validator: ['required']},
        ];
        this.fields.push(
            {
                name: 'receiverIds', alias: '接收者', type: 'list', cmp: {
                    name: 'cmp:Shuttle',
                    params: {model: 'mSystemPass', displayName: 'name'}
                },validator: ['required']
            });
        this.fields.push(
            {name: 'content', alias: '内容', type: 'textarea',validator: ['required']});
        this.fields.push({name: 'audit', alias: '',label:'审核', type: 'checkbox'});
        this.proxy = {
            url:  '/systemMessage'
        };
        this.callParent(params);
    }

    SystemMessage.extend(Model);

    return SystemMessage;
});

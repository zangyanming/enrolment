define(['model'], function (Model) {
    function SystemPass(params) {
        this.fields = [
            {name: 'name', alias: '系统名称'}
        ];

        this.proxy = {
            url: '/systems/audit/pass'
        };
        this.callParent(params);
    }

    SystemPass.extend(Model);

    return SystemPass;
});

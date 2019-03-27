define(['model'], function (Model) {
    function Login(params) {
        this.fields=[
            {name:'name',alias:'姓名',validator:['required']},
            {name:'password',alias:'密码',type:'password',validator:['required']}
        ];
        this.callParent(params);
    }

    Login.extend(Model);

    return Login;
});

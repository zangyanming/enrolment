define(['knockout','mAjax'], function (ko,MAjax) {
    function Password(params) {
        this.fields=[
            {name:'loginName',alias:'用户名'},
            {name:'name',alias:'姓名'},
            {name:'oldPassword',alias:'原密码',type:'password',validator:['required']},
            {name:'newPassword',alias:'新密码',type:'password',validator:['required']},
            {name:'confirmNewPassword',alias:'原密码',type:'password',validator:['required']}
        ];

        this.form = 'PasswordForm';
        this.callParent(params);
    }

    Password.extend(MAjax);

    return Password;
});

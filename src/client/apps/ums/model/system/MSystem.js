define(['model'], function (Model) {
    function System(params) {
        this.fields=[
            {name:'name',alias:'系统名称',validator: ['required']},
            {name:'sysCode',alias:'系统编码',validator: ['required']},
            {name:'sysKey',alias:'秘钥'},
            {name:'applicationUnit',alias:'申请单位',validator: ['required']},
            {name:'applicant',alias:'申请人',validator: ['required']},
            {name:'email',alias:'邮箱',validator: ['required']},
            {name:'mobile',alias:'手机',validator: ['required']},
            {name:'auditStatusName',alias:'审核状态'}
        ];

        this.proxy = {
            url: '/systems'
        };
        this.form ='SystemForm';
        this.callParent(params);
    }

    System.extend(Model);

    return System;
});

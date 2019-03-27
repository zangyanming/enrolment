define(['knockout','mAjax'], function (ko,MAjax) {
    function Audit(params) {
        this.fields=[
            {name: 'content', type: 'textarea', alias: '审核意见', validator: ['required']},
            {name:'auditStatus',alias: '审核状态'}
        ];

        this.form = 'AuditForm';
        this.callParent(params);
    }

    Audit.extend(MAjax);

    return Audit;
});

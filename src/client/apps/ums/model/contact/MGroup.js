define(['model'], function (Model) {
    function Group(params) {
        this.fields=[
            {name:'name',alias:'群组名'}
        ];
        this.form = 'GroupForm';
        this.proxy = {
            url:'/groups'
        }
        this.callParent(params);
    }

    Group.extend(Model);

    return Group;
});

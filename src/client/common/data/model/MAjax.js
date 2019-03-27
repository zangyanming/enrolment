define(['model'], function (Model) {
    function MAjax(params) {
        this.proxy = {
            url : '',//this url should be assigned when in use
            type: 'ajax'
        };

        this.callParent(params);
    }

    MAjax.extend(Model);

    return MAjax;
});

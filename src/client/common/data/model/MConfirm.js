define(['mAjax'], function (MAjax) {
    function Confirm(params) {
        this.id = function () {return 'confirm'};
        this.confirmInfo = params;
        this.callParent(params);
    }

    Confirm.extend(MAjax);

    return Confirm;
});

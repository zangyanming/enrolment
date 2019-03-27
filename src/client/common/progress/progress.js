define([], function () {
    function ViewModel(params) {
        $.extend(this,{
           title:''
        });
        $.extend(this, params);

        var scope = this;
    }

    return ViewModel;
});

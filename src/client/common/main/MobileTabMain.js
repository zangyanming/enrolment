define([], function () {
    function ViewModel(params) {
        scope = this;
        scope.items = params.items;
        if(!scope.tabCmp) scope.tabCmp = 'cmp:Empty';
    }

    return ViewModel;
});

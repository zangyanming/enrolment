define(['knockout','util'], function (ko,Util) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        if(scope.items && scope.items.length>0) {
            Util.addCssActive(scope.items,true);

            for(var index=0;index<scope.items.length;++index) {
                if(!scope.items[index].cmp) {
                    scope.items[index].cmp = 'cmp:Empty';
                }

                if(scope.items[index].icon){
                    scope.items[index].css = Util.zico(scope.items[index].icon);
                }
            }

            ko.root.mainCmp({name:scope.items[0].cmp,params:scope.items[0]});
        }

        scope.itemClick = function (item) {
            if(Util.changeCssActive(item,scope.items)) {
                scope.items.forEach(function (itm) {
                    itm._cssActive(false);
                });

                item._cssActive(true);
                ko.root.mainCmp({name:item.cmp,params:item});
            }
        }
    }

    return ViewModel;
});

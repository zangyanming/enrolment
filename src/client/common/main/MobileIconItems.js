define(['knockout','util','store'], function (ko,Util,Store) {
    function ViewModel(params) {
        this.items = params.items;

        this.items.forEach(function(item){
            if(item.icon){
                item.css = Util.zico(item.icon);
            }
            else{
                item.css = {};
            }

            if(!item.iconColor){
                item.iconColor = 'black';
            }
        });

        this.onItemClick = function (index,item) {
            var cmpSplit = item.cmp.split('_');

            if(cmpSplit[0] == 'cmp:MobileModal'){
                if(cmpSplit.length == 3){
                    if(cmpSplit[2].indexOf('cmp:')==-1){
                        switch (cmpSplit[2]) {
                            case 'add':
                                var store = new Store({
                                    model:cmpSplit[1],
                                    autoLoad:false,
                                    onInitComplete: function () {
                                        //show add page
                                        ko.root.openModal({title:item.name,operation:cmpSplit[2],record:store.createModel()});
                                    }
                                });
                                break;
                        }
                    }
                    else{
                        //show other page
                        ko.root.openModal({title:item.name,titleOperations:['query'],record:{bodyCmp:cmpSplit[2],model:cmpSplit[1],operations:item.items}});
                    }
                }
            }
        };
    }

    return ViewModel;
});

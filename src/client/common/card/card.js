define(['knockout','util'], function (ko,Util) {
    function ViewModel(params) {
        $.extend(this,{
            cardBody:{name:'cmp:Empty',params:{}},
            title:'',
            collapseAble:false,
            preOperation:function(){return true}
        });
        $.extend(this, params);

        var scope = this;

        if(typeof scope.cardBody == 'function'){
            scope._cardBody = scope.cardBody;

            if(scope.cardBody()==undefined){
                scope._cardBody({name:'cmp:Empty',params:{}});
            }
        }
        else{
            scope._cardBody = ko.observable(scope.cardBody);
        }

        if(typeof scope.title == 'string'){
            scope.title = ko.observable(scope.title);
        }

        if(scope.height) {
            scope.height = scope.height+'px';
        }
        else{
            scope.height = 'inherit';
        }

        scope.cardBodyId = 'card_body_' + (scope.title?scope.title() : Date.parse(new Date()));

        if(scope.titleButtons && scope.titleButtons.length>0) {
            scope.titleButtons = Util.generateOperations(scope.titleButtons);//tempButtons;
        }
        else{
            scope.titleButtons = [];
        }

        scope._onOperation = function (operation) {
            if(scope.preOperation(operation)){
                if(scope._cardBody().params.store)
                {
                    switch (operation.name) {
                        case 'refresh':
                            scope._cardBody().params.store.load();
                            break;
                        case 'add':
                            ko.root.openModal({operation:'add',record:scope._cardBody().params.store.createModel()});
                            break;
                        default:
                    }
                }

                if(scope.onOperation){
                    scope.onOperation(operation);
                }
            }
        }
    }

    return ViewModel;
});

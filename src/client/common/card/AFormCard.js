define(['knockout', 'store','util'], function (ko, Store) {
    //params:{model:,form:,title:}
    function ViewModel(params) {
        var scope = this;
        var messageStore = new Store({
            autoLoad: false, model: params.model, onInitComplete: function () {
                scope.record = messageStore.createModel();
                scope.card.cardBody({name:params.form,params:{record:scope.record}});
            }
        });

        scope.card = {
            title: params.title,
            cardBody: ko.observable({name:'cmp:Empty'})
        };
    }

    return ViewModel;
});

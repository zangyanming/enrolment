define(['knockout', 'registerCommon', 'director', 'util'], function (ko, RegisterCommon, Router, Util) {
    RegisterCommon.registerCmp();

    function ViewModel(params) {
        $.extend(this,{
            mobile:false
        });
        $.extend(this, params);

        var scope = this;

        if (!scope.fluid) {
            scope.fluid = scope.mobile;
        }

        scope.fluidCss = {container: !scope.fluid, 'container-fluid': scope.fluid};
        ['topCmp', 'mainCmp', 'bottomCmp'].forEach(function (item) {
            if (scope[item]) {//the field is config in app
                if (scope[item] instanceof Object) {
                    scope[item] = ko.observable(scope[item]);
                }
                else {
                    scope[item] = ko.observable({name: scope[item], params: {}})
                }
            }
            else {
                scope[item] = ko.observable();
            }
        });
        //main cmp contain the second cmp
        scope.secondCmp = ko.observable({name: 'cmp:Empty', params: {}});

        scope.router = Router().configure({
            notfound: function () {
                var maxLevel = Util.getHashMaxLevel();

                if (maxLevel > 0) {
                    var cmpL1 = Util.getCmpFromHash(1)

                    if (!ko.root.mainCmp() || ko.root.mainCmp().name != cmpL1.name) {
                        ko.root.mainCmp(cmpL1);
                        ko.root.secondCmp({name: 'cmp:Empty'});
                    }
                }

                if (maxLevel > 1) {
                    var cmpL2 = Util.getCmpFromHash(2);

                    if (!ko.root.secondCmp() || ko.root.secondCmp().name != cmpL2.name)
                        ko.root.secondCmp(cmpL2);
                }
            }
        });

        scope.showProgress = ko.observable(false);
        scope.modalStack = ko.observableArray();
        scope.openModal = function (modalConfig) {
            var params = {};
            var cmp = 'cmp:Modal';

            if (modalConfig) {
                if (modalConfig.cmp) {
                    cmp = modalConfig.cmp;
                    params = modalConfig.params;
                }
                else {
                    params = modalConfig;
                }
            }
            else {
                params = {record: {bodyCmp: 'cmp:Empty'}};
            }

            params['_id'] = 'modal_' + scope.modalStack().length;
            scope.modalStack.push({cmp: cmp, params: params});
        }

        scope.hideModal = function () {
            scope.modalStack.pop();
        }

        ko.computed(function () {
            scope.mainCmp();
            ko.storeCache = {};//cache the store which has the id
        });
    }

    return ViewModel;
});

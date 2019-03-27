define(['knockout', 'util'], function (ko, Util) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        scope.maxBtn = 5;
        scope.pageCount = ko.observable();
        scope.pageBtns = ko.observableArray([]);
        scope.lastPage = false;

        scope._lastTotalcount = 0;

        ko.computed(function () {
            var totalCount = scope.store.totalCount();

            if (scope._lastTotalcount == totalCount) {

            }
            else {
                scope._lastTotalcount = totalCount;
                var firstBtnIndex = scope.pageBtns().length > 0 ? scope.pageBtns()[0].text() : 1;
                scope.pageBtns.removeAll();

                if (totalCount > 0 && scope.store.pageSize > 0) {
                    var btnSize = 0;
                    var btns = [];
                    scope.pageCount(Math.ceil(totalCount / scope.store.pageSize));

                    if (scope.pageCount() < scope.maxBtn) {
                        btnSize = scope.pageCount();
                    }
                    else {
                        btnSize = scope.maxBtn;
                    }

                    if ((firstBtnIndex + btnSize - 1) > scope.pageCount()) {
                        firstBtnIndex = firstBtnIndex - (firstBtnIndex + btnSize - 1 - scope.pageCount());

                        if (firstBtnIndex < 1) firstBtnIndex = 1;
                    }

                    if (scope.lastPage) {
                        firstBtnIndex = scope.pageCount() - btnSize + 1;
                        scope.lastPage = false;
                    }

                    for (var bIndex = firstBtnIndex; bIndex <= btnSize + firstBtnIndex - 1; ++bIndex) {
                        btns.push({text: ko.observable(bIndex)});
                    }

                    Util.addCssActive(btns);
                    scope.pageBtns(btns);

                    if (scope.pageBtns()[scope.store.pageNo - firstBtnIndex])
                        scope.pageBtns()[scope.store.pageNo - firstBtnIndex]._cssActive(true);
                }
                else {
                    scope.pageCount(0);
                    scope.pageBtns([]);
                }
            }
        });

        scope.preDisabled = ko.observable(false);
        scope.nextDisabled = ko.observable(false);

        scope.firstBtnClick = function () {
            if (scope.store.pageNo > 1) {
                scope.store.totalCount(0);
                scope.store.first();
            }
        };

        scope.preBtnClick = function () {
            if (scope.store.pre()) {
                scope._changePageBtns(false);
            }
        };

        scope.nextBtnClick = function () {
            if (scope.store.next()) {
                scope._changePageBtns(true);
            }
        };

        scope.lastBtnClick = function () {
            if(scope.pageCount()>scope.store.pageNo){
                scope.store.totalCount(0);
                scope.lastPage = true;
                scope.store.last();
            }
        };

        scope._changePageBtns = function (direction) {
            var firstBtn = scope.pageBtns()[0];
            var lastBtn = scope.pageBtns()[scope.pageBtns().length - 1];

            if (direction) {//next
                if (lastBtn._cssActive()) {
                    scope.pageBtns().forEach(function (btn) {
                        btn.text(btn.text() + 1)
                    });
                }
            }
            else {//pre
                if (firstBtn._cssActive()) {
                    scope.pageBtns().forEach(function (btn) {
                        btn.text(btn.text() - 1);
                    });
                }
            }

            scope.pageBtns().forEach(function (btn) {
                if (btn.text() == scope.store.pageNo) {
                    Util.changeCssActive(btn, scope.pageBtns());
                }
            });
        }

        scope.pageBtnClick = function (btn) {
            if (!btn._cssActive()) {
                Util.changeCssActive(btn, scope.pageBtns());
                scope.store.pageNo = btn.text();
                scope.store.load();
            }
        }
    }

    return ViewModel;
});

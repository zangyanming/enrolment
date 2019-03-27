define(['knockout', 'pdfjs-dist/build/pdf'], function (ko, PdfjsLib) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;
        scope.canvasStyle = {};

        if(!scope.pageGutter){
            scope.pageGutter = 8;
        }

        scope.canvasStyle['margin-top'] = scope.pageGutter + 'px';
        PdfjsLib.GlobalWorkerOptions.workerSrc = '../node_modules/pdfjs-dist/build/pdf.worker.min.js';

        var pdfDoc = null, scale = 0, pdfContainer = $('.xgx-pdf-container')[0];

        scope.pageNum = ko.observable(1);
        scope.maxPage = ko.observable();
        scope.pages = [];
        scope.scaleOptions = [
            {text: '自动', value: -1},
            {text: '100%', value: 1},
            {text: '150%', value: 1.5},
            {text: '175%', value: 1.75},
            {text: '200%', value: 2},
            {text: '225%', value: 2.25}];
        scope.scaleValue = ko.observable(-1);
        scope.scaleChange = function () {
            scope.pages.forEach(function (page) {
               page.loaded=false;
            });
            calculateViewPort(function () {
                renderPage(Number(scope.pageNum()));
            });
        };
        scope.canvasWidth = ko.observable(0);
        scope.canvasHeight = ko.observable(0);

        function calculateViewPort(callback) {
            pdfDoc.getPage(1).then(function (page) {
                var desiredWidth = 1000;
                var viewport = page.getViewport(1);
                scale = (scope.scaleValue() == -1 ? (desiredWidth / viewport.width) : scope.scaleValue());
                var scaledViewport = page.getViewport(scale);
                var scaledHeight = Math.floor(scaledViewport.height);
                var scaledWidth = Math.floor(scaledViewport.width);

                if (scope.canvasHeight() != scaledHeight) {
                    scope.canvasHeight(scaledHeight);
                }

                if (scope.canvasWidth() != scaledWidth) {
                    scope.canvasWidth(scaledWidth);
                }

                if (callback) {
                    callback();
                }
            });
        }

        function renderPage(num) {
            if(num<1||num>scope.maxPage()){
                return;
            }

            pdfDoc.getPage(num).then(function (page) {
                if(!scope.pages[num-1].loaded){
                    scope.pages[num-1].loaded=true;
                    var scaledViewport = page.getViewport(scale);
                    var canvas = $('#pdf-page-' + num)[0];

                    ctx = canvas.getContext('2d');
                    // Render PDF page into canvas context
                    var renderContext = {
                        canvasContext: canvas.getContext('2d'),
                        viewport: scaledViewport
                    };

                    page.render(renderContext);

                }
                else{
                }
            });
        }


        if (scope.url) {
            PdfjsLib.getDocument(scope.url).then(function (pdfDoc_) {
                pdfDoc = pdfDoc_;
                scope.maxPage(pdfDoc.numPages);
                for (var i = 1; i <= pdfDoc.numPages; ++i) {
                    scope.pages.push({page:i,loaded:false});
                }

                calculateViewPort(function () {
                    renderPage(1);
                    renderPage(2);
                    renderPage(3);
                });
            });
        }

        scope.onPrevPage = function () {
            var num = Number(scope.pageNum());

            if (num <= 1) {
                return;
            }
            scope.pageNum(num - 1);
            gotoPage();
        };

        scope.onNextPage = function () {
            var num = Number(scope.pageNum());

            if (num>= pdfDoc.numPages) {
                return;
            }

            scope.pageNum(num + 1);
            gotoPage();
        };

        var lastPage = 0;

        function gotoPage(){
            var num =Number(scope.pageNum());

            pdfContainer.scrollTo(0,(scope.canvasHeight()+scope.pageGutter+2)*(num-1));
            renderPage(num);
            renderPage(num+1);
        }

        scope.scroll = function (data, event) {
            var tempPage = (pdfContainer.scrollTop+1) / (scope.canvasHeight() + scope.pageGutter +2);
            var num =Number(scope.pageNum());

            if (Math.ceil(tempPage) !=num ) {

                renderPage(num - 1);
                renderPage(num);
                renderPage(num + 1);
                renderPage(num + 2);

                scope.pageNum(Math.ceil(tempPage));
            }

            lastPage = tempPage;
        };

        $("[title='当前页']").keyup(function (event) {
            if(event.keyCode==13){
                gotoPage();
            }
        });
        $("[title='当前页']").blur(function () {
           gotoPage();
        });
    }

    return ViewModel;
});
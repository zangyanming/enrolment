define([], function () {
    var koTemplate = {
        helloWorld: '<div class="container bg-light" style="height: 1000px;"></div>',
        empty: '<div class="w-100 bg-primary border"></div>',
        deleteConfirm: '<div class="p-3"></span><span class="ml-2">是否删除？</span></div>',
    };

    return {
        crossDomain: false,
        baseUrl: '.',
        koTemplate: koTemplate,
        modelDefaultId: 0
    }
});

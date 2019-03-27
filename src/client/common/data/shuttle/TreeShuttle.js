define(['knockout'], function (ko) {
    function ViewModel(params) {
        var scope = this;

        scope.tree = {
            url: './ums/rest/tree',
            small:true,
            onNodeSelected: function (data) {
                scope.shuttle.leftItems([
                    {id:1,text:'测试1'},
                    {id:2,text:'测试2'},
                    {id:3,text:'测试3'},
                    {id:4,text:'测试4'},
                    {id:5,text:'测试5'},
                    {id:6,text:'测试6'}
                ]);
            }
        };
        scope.shuttle = {
            leftItems:ko.observableArray()
        };
    }


    return ViewModel;
});

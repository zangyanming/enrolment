define(['knockout', 'util', 'bootstrap-treeview'], function (ko, Util) {
    function ViewModel(params) {
        this.small = false;

        $.extend(this, params);

        var scope = this;

        scope._css = {'xgx-tree-sm':scope.small};

        //if (!scope.id) {
        scope._id = 'tree_' + Date.parse(new Date());
        //}

        if (scope.url) {
            Util.ajaxGet(scope.url, {}, function (data) {
                if(scope.dataFormat){
                    Util.treeFormat(data.data,0,scope.dataFormat);
                }

                $('#' + scope._id).treeview({
                    data: data.data,
                    onNodeSelected: function (event, data) {
                        if(scope.onNodeSelected){
                            scope.onNodeSelected(data);
                        }
                    },
                    onNodeUnselected: function (event, data) {
                        if(scope.onNodeUnselected){
                            scope.onNodeUnselected(data);
                        }
                    },
                    expandIcon:Util.treeConfig.expandIcon,
                    collapseIcon:Util.treeConfig.collapseIcon,
                    color:Util.treeConfig.color,
                    levels:1,
                    selectedBackColor:Util.treeConfig.selectedBackColor,
                    onhoverColor:Util.treeConfig.onhoverColor,
                });
            });
        }
    }

    return ViewModel;
});

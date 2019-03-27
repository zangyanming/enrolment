define(['knockout', 'util','bootstrap-treeview'], function (ko, Util) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        //if (!scope.id) {
        scope._id = 'tree_' + Date.parse(new Date());
        //}

        scope.jqId = '#'+scope._id;

        scope.displayValue = ko.observable();

        if (scope.field.url) {
            Util.ajaxGet(scope.field.url, {}, function (data) {
                if(scope.field.dataFormat){
                    scope.field.dataFormat(data.data,scope.field.selectLevel);
                }

                $(scope.jqId).treeview($.extend({
                    levels:1,
                    showCheckbox:true,
                    data: data.data,
                    onNodeChecked:function (event,node) {
                        scope.checkNode(node);
                        scope.changeValue();
                    },
                    onNodeUnchecked:function (event,node) {
                        scope.uncheckNode(node);
                        scope.changeValue();
                    }
                },Util.treeConfig));

                if(scope.record.id&&scope.record.id()>0){
                    if(scope.record[scope.field.name]&&scope.record[scope.field.name]()){
                        var codes = scope.record[scope.field.name]().split(',');

                        if(codes.length>0){
                           var allNodes = $(scope.jqId).treeview('getUnchecked');
                           var nodes = codes.map(function (code) {
                               for(var nIndex=0;nIndex<allNodes.length;++nIndex){
                                   if(code == allNodes[nIndex].id){
                                       return {nodeId:allNodes[nIndex].nodeId,text:allNodes[nIndex].text};
                                   }
                               }
                           });

                           scope.checkNodes(nodes);
                        }
                    }
                }
            });
        }

        scope.checkNodes = function(nodes){
            var tempTexts = [];

            nodes.forEach(function (node) {
                if(node!=undefined){
                    $(scope.jqId).treeview('checkNode',[node.nodeId,{silent:true}]);
                    tempTexts.push(node.text);
                }
            });

            scope.displayValue(tempTexts.join(','));
        }

        scope.changeValue = function(){
           var nodes = $(scope.jqId).treeview('getChecked');
           var ids = nodes.map(function (node) {
               return node.id;
           });
           var texts = nodes.map(function (node) {
               return node.text;
           });

           scope.displayValue(texts.join(','));
           scope.record[scope.field.name](ids.join(','));
        }

        scope.checkNode = function(node){
            scope.checkParentNode(node);
            scope.checkChildNode(node);
        }

        scope.checkParentNode = function(node){
            if(node.parentId!=undefined){
                var parentNode = $(scope.jqId).treeview('getNode',node.parentId);

                $(scope.jqId).treeview('checkNode',[parentNode.nodeId,{silent:true}]);

                scope.checkParentNode(parentNode);
            }
        }

        scope.checkChildNode = function (node) {
            $(scope.jqId).treeview('checkNode',[node.nodeId,{silent:true}]);

            if(node.nodes){
                node.nodes.forEach(function (childNode) {
                    scope.checkChildNode(childNode);
                });
            }
        }

        scope.uncheckNode = function (node) {
            $(scope.jqId).treeview('uncheckNode',[node.nodeId,{silent:true}]);

            if(node.nodes){
                node.nodes.forEach(function (childNode) {
                    scope.uncheckNode(childNode);
                });
            }
        }
    }

    return ViewModel;
});

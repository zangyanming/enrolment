define(['knockout', 'util'], function (ko, Util) {
    function ViewModel(params) {
        $.extend(this, {
            brand: '',
            title: '',
            light: false,
            pills: false,
            shortcutItems:[]
        });
        $.extend(this, params);

        var scope = this;

        if (scope.menuItems && scope.menuItems.length > 0) {
            Util.addCssActive(scope.menuItems, true);

            for (var index = 0; index < scope.menuItems.length; ++index) {
                if (!scope.menuItems[index].cmp) {
                    scope.menuItems[index].cmp = 'cmp:Empty';
                }

                scope.menuItems[index]._attr = {};

                if (scope.menuItems[index].href) {
                    scope.menuItems[index]._attr.href = '#' + scope.menuItems[index].href;
                }
            }

            if (location.hash == '' || location.hash == '#') {
                location.hash = '#' + scope.menuItems[0].href;
            }
            //ko.root.mainCmp({name:scope.menuItems[0].cmp});
        }
        if (scope.shortcutItems && scope.shortcutItems.length > 0) {
            for (var index = 0; index < scope.shortcutItems.length; ++index) {
                scope.shortcutItems[index].css = Util.zico(scope.shortcutItems[index].icon);

                if (!scope.shortcutItems[index].subItems) {
                    scope.shortcutItems[index].subItems = [];
                }
            }
        }

        scope.menuClick = function (item) {
            if (Util.changeCssActive(item, scope.menuItems)) {
                scope.menuItems.forEach(function (itm) {
                    itm._cssActive(false);
                });

                item._cssActive(true);
                //ko.root.mainCmp({name:item.cmp});
            }

            return true;
        };

        scope._shortcutClick = function (item) {
            if (scope.shortcutClick) {
                scope.shortcutClick(item);
            }
        };
    }

    return ViewModel;
});

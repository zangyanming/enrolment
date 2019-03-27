define(['knockout', 'util', 'jqxcore', 'jqxdata', 'jqxbuttons', 'jqxscrollbar', 'jqxdatatable', 'jqxtreegrid'], function (ko, Util) {
    function ViewModel(params) {
        $.extend(this,{
            preOperation:function () {
                return true
            }
        });
        $.extend(this, params);

        var scope = this;

        scope._id = 'treeGrid_' + Date.parse(new Date());
        scope._jqId = '#' + scope._id;
        scope._css = {};
        scope._selectedId = undefined;
        scope._operations = Util.generateOperations(scope.operations);
        scope._onOperation = function (operation, id) {
            var record = scope.store.getRecordById(id);

            if (scope.preOperation(operation) && record) {
                if (scope.store) {
                    Util.operationSwitch(operation, record);
                }

                if (scope.onOperation) {
                    scope.onOperation(operation);
                }
            }
        };

        var source =
            {
                dataType: "array",
                dataFields: scope.store.getModelFields(),
                hierarchy:
                    {
                        keyDataField: {name: scope.keyDataField},
                        parentDataField: {name: scope.parentDataField}
                    },
                id: scope.keyDataField,
                localData: []
            };
        var dataAdapter = new $.jqx.dataAdapter(source);
        var cellsrenderer = function (row, column, value, rowData) {
            return '<!-- ko foreach:_operations -->' +
                '<span class="mr-1" data-bind="css:css,click:function(){$parent._onOperation($data,' + rowData.id + ')},attr:{title:title}"></span>' +
                '<!-- /ko -->'
        };
        var columns = [];

        scope.displayFields.forEach(function (fieldName) {
            var field = scope.store.getModelField(fieldName);

            columns.push({text: field.alias, dataField: field.name});
        });

        if (scope.operations && scope.operations.length > 0) {
            columns.push({text: '操作', cellsrenderer: cellsrenderer});
        }

        //add the tree grid init method in a async function because id is ko bind
        require([], function () {
            $(scope._jqId).jqxTreeGrid(
                {
                    columnsHeight: 48,
                    width: '100%',
                    source: dataAdapter,
                    theme: "bootstrap",
                    rendered: function () {
                        ko.cleanNode($("#table" + scope._id)[0]);
                        ko.applyBindings(scope, $("#table" + scope._id)[0]);
                    },
                    columns: columns
                });
            $(scope._jqId).on('rowSelect',
                function (event) {
                    var row = event.args.row;

                    if (row[scope.keyDataField] instanceof Number) {
                        row[scope.keyDataField] = row[scope.keyDataField].toString();
                    }

                    if (row[scope.parentDataField] instanceof Number) {
                        row[scope.parentDataField] = row[scope.parentDataField].toString();
                    }

                    if (scope._selectedId == event.args.row[scope.keyDataField]) {
                        $(scope._jqId).jqxTreeGrid('unselectRow', scope._selectedId );
                        scope._selectedId = undefined;

                        if(scope.onNoneRowSelect) scope.onNoneRowSelect();
                    }
                    else {
                        if (scope.onRowSelect) scope.onRowSelect(event.args.row);

                        scope._selectedId = event.args.row[scope.keyDataField];
                    }
                }
            );

            scope.store.load();
        });


        scope.store.addLoadListener('complete', function () {
            source.localdata = ko.mapping.toJS(scope.store.records);
            dataAdapter.dataBind();
            //$("#" + scope.id).jqxTreeGrid('expandRow', '1');
            scope._selectedId = undefined;
            if (scope.onNoneRowSelect) scope.onNoneRowSelect();
        });
    }

    return ViewModel;
});

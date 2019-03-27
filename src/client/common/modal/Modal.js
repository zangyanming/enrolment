define(['knockout', 'model','mConfirm', 'util'], function (ko, Model,MConfirm, Util) {
    function ViewModel(params) {
        $.extend(this,{
           operation:''
        });
        $.extend(this, params);

        var scope = this;

        scope._name = 'modal';
        scope._jqId = '#' + scope._id;
        scope._title = ko.observable();
        scope._titleOperations = [];//mobile
        scope._mobile = ko.root.mobile;//mobile
        scope._bodyCmp = ko.observable();
        scope._buttons = ko.observableArray(//the button can init in child component
            (scope._mobile||scope.operation=='view')?[]:[
                {
                    name: 'cancel', title: '取消'
                },
                {
                    name: 'confirm', title: '确定'
                }
            ]
        );
        scope._large = scope.size=='lg'?true:false;
        scope._small = scope.size=='sm'?true:false;
        scope._modalCss = {'modal-lg':scope._large,'modal-sm':scope._small};
        scope._level = scope.level?scope.level:'xgx-default';
        scope._modalHeaderCss = {'text-light':true};
        scope._modalHeaderCss['bg-'+ scope._level] = true;

        if (scope.titleOperations) {//mobile
            scope._titleOperations = Util.generateOperations(scope.titleOperations);
        }

        if (scope.record instanceof Model && scope.operation) {
            if (scope.record.form) {
                if (scope.record.form instanceof Object) {
                    scope._bodyCmp('cmp:' + scope.record.form[scope.operation]);
                }
                else {
                    scope._bodyCmp('cmp:' + scope.record.form);
                }
            }
        }
        else {
            //record isn't a model instance
            //bodyCmp must init manually in the record object
            scope._bodyCmp(scope.record.bodyCmp?scope.record.bodyCmp:'cmp:Empty');
            delete scope.record.bodyCmp;
        }

        switch (scope.operation) {
            case 'view':
                scope._title(scope.title ? scope.title : '查看');
                break;
            case 'add':
                scope._title(scope.title ? scope.title : '新增');
                break;
            case 'edit':
                scope._title(scope.title ? scope.title : '修改')
                break;
            case 'delete':
                scope._title(scope.title ? scope.title : '删除');
                scope._bodyCmp('cmp:ConfirmBody');
                break;
            default:
                scope._title(scope.title ? scope.title : '');

                if(scope.record instanceof MConfirm){
                    scope._bodyCmp('cmp:ConfirmBody');
                }
        }

        scope._commit = function (btn) {
            switch (scope.operation) {
                case 'add':
                    scope.record.save(scope.commitCallback);
                    break;
                case 'edit':
                    scope.record._fullRecord.save(scope.commitCallback);
                    break;
                case 'delete':
                    scope.record.delete(scope.commitCallback);
                    break;
                default:
                    if (scope.commit) {
                        scope.commit(btn, scope.record, scope.commitCallback);
                    }
            }
        }
        scope._dismiss = function () {
            if (scope.record instanceof Model && scope.record.isDirty()) {
                if (ko.root.mobile) {
                    if (scope.operation == 'query') {
                        ko.storeCache[scope.record._storeId].query = scope.record.getFormData();
                        ko.storeCache[scope.record._storeId].empty();
                        ko.storeCache[scope.record._storeId].load();
                    }
                }

                $(scope._jqId).modal('hide');
                // else {
                //     $('.btn-primary').attr('disabled', true);
                //     $('.btn-secondary').attr('disabled', true);
                //     $('.btn-secondary').confirmation({
                //         onConfirm: function (value) {
                //             $('.btn-primary').attr('disabled', false);
                //             $('.btn-secondary').attr('disabled', false);
                //
                //             if (value) {
                //                 scope._commit();
                //             }
                //             else {
                //                 //scope.record.revert();
                //                 $(scope._jqId).modal('hide');
                //             }
                //         },
                //         title: '保存编辑？',
                //         btnOkLabel: '是',
                //         btnCancelLabel: '否',
                //         buttons: [
                //             {
                //                 iconClass: 'glyphicon glyphicon-remove',
                //                 class: 'btn btn-danger',
                //                 label: '否',
                //                 value: false
                //             },
                //             {
                //                 iconClass: 'glyphicon glyphicon-ok',
                //                 class: 'btn btn-success',
                //                 label: '是',
                //                 value: true
                //             }
                //         ]
                //     });
                //     $('.btn-secondary').confirmation('show');
                // }
            }
            else {
                $(scope._jqId).modal('hide');
            }

            if(scope.dismiss) scope.dismiss();
        }
        scope.commitCallback = function (data) {
            if (data.success) {
                $(scope._jqId).modal('hide');
            }
            else {
            }
        }
        scope._onTitleOP = function (op) {//mobile
            switch (op.name) {
                case 'query':
                    var modelName = scope.record.model.substring(1, scope.record.model.length);

                    if (ko.storeCache[scope.record.model + 'Store']) {
                        var queryModel = ko.storeCache[scope.record.model + 'Store'].createModel();

                        ko.root.openModal(
                            {title: '查询', operation: 'query', record: queryModel}
                        );
                    }
                    else {
                        console.log('please set the store id');
                    }

                    break;
            }
        }
        scope._onButtonClick = function (item) {
            scope._commit(item);
        }

        $(scope._jqId).modal({keyboard: false, backdrop: ko.root.mobile ? false : 'static', focus: false});
        $(scope._jqId).on('hidden.bs.modal', function (e) {
            ko.root.hideModal();
        });
    }

    return ViewModel;
});

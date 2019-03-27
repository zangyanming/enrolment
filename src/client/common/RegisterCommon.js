define(['knockout', 'toastr', 'config', 'bootstrap'], function (ko, toastr, Config) {
    //config the modules
    toastr.options.closeDuration = 300;
    toastr.options.timeOut = 5000;
    toastr.options.extendedTimeOut = 60;
    toastr.options.positionClass = 'toast-top-center';

    return {
        registerCmp: function () {
            ko.components.register('cmp:HelloWorld', {
                template: Config.koTemplate.helloWorld
            });
            ko.components.register('cmp:Empty', {
                template: Config.koTemplate.empty
            });
            ko.components.register('cmp:ConfirmBody', {
                viewModel: {require: 'common/modal/body/ConfirmBody'},
                template: {require: 'text!common/modal/body/ConfirmBody.html'}
            });
            //layout
            ko.components.register('cmp:Root', {
                viewModel: {require: 'common/vm/empty'},
                template: {require: 'text!common/layout/root.html'}
            });
            ko.components.register('cmp:NavigationBar', {
                viewModel: {require: 'common/layout/NavigationBar'},
                template: {require: 'text!common/layout/NavigationBar.html'}
            });
            ko.components.register('cmp:Bottom', {
                viewModel: {require: 'common/layout/bottom'},
                template: {require: 'text!common/layout/bottom.html'}
            });
            ko.components.register('cmp:TabBar', {
                viewModel: {require: 'common/layout/TabBar'},
                template: {require: 'text!common/layout/TabBar.html'}
            });
            //card
            ko.components.register('cmp:Card', {
                viewModel: {require: 'common/card/card'},
                template: {require: 'text!common/card/card.html'}
            });
            //modal
            ko.components.register('cmp:Modal', {
                viewModel: {require: 'common/modal/Modal'},
                template: {require: 'text!common/modal/Modal.html'}
            });
            ko.components.register('cmp:MobileModal', {
                viewModel: {require: 'common/modal/Modal'},
                template: {require: 'text!common/modal/Modal.html'}
            });
            ko.components.register('cmp:MobileConfirmModal', {
                viewModel: {require: 'common/modal/Modal'},
                template: {require: 'text!common/modal/MobileConfirmModal.html'}
            });
            //form
            ko.components.register('cmp:TreeField', {
                viewModel: {require: 'common/data/form/field/TreeField'},
                template: {require: 'text!common/data/form/field/TreeField.html'}
            });
            ko.components.register('cmp:ListField', {
                viewModel: {require: 'common/data/form/field/ListField'},
                template: {require: 'text!common/data/form/field/ListField.html'}
            });
            ko.components.register('cmp:LoginForm', {
                viewModel: {require: 'common/data/form/LoginForm'},
                template: {require: 'text!common/data/form/LoginForm.html'}
            });
            ko.components.register('cmp:PasswordForm', {
                viewModel: {require: 'common/data/form/PasswordForm'},
                template: {require: 'text!common/data/form/AForm.html'}
            });
            ko.components.register('cmp:UploadForm', {
                viewModel: {require: 'common/data/form/UploadForm'},
                template: {require: 'text!common/data/form/AForm.html'}
            });
            ko.components.register('cmp:AuditForm', {
                viewModel: {require: 'common/data/form/AuditForm'},
                template: {require: 'text!common/data/form/AForm.html'}
            });
            //data
            ko.components.register('cmp:List', {
                viewModel: {require: 'common/data/list/list'},
                template: {require: 'text!common/data/list/list.html'}
            });
            ko.components.register('cmp:Table', {
                viewModel: {require: 'common/data/grid/table'},
                template: {require: 'text!common/data/grid/table.html'}
            });
            ko.components.register('cmp:Pagination', {
                viewModel: {require: 'common/data/grid/pagination'},
                template: {require: 'text!common/data/grid/pagination.html'}
            });
            ko.components.register('cmp:Grid', {
                viewModel: {require: 'common/vm/empty'},
                template: {require: 'text!common/data/grid/grid.html'}
            });
            ko.components.register('cmp:TreeGrid', {
                viewModel: {require: 'common/data/grid/TreeGrid'},
                template: {require: 'text!common/template/TDiv.html'}
            });
            ko.components.register('cmp:Tree', {
                viewModel: {require: 'common/data/tree/tree'},
                template: {require: 'text!common/template/TDiv.html'}
            });
            ko.components.register('cmp:Chart', {
                viewModel: {require: 'common/data/chart/chart'},
                template: {require: 'text!common/data/chart/chart.html'}
            });
            ko.components.register('cmp:Pdf', {
                viewModel: {require: 'common/data/pdf/pdf'},
                template: {require: 'text!common/data/pdf/pdf.html'}
            });
            ko.components.register('cmp:Shuttle', {
                viewModel: {require: 'common/data/shuttle/shuttle'},
                template: {require: 'text!common/data/shuttle/shuttle.html'}
            });
            ko.components.register('cmp:TreeShuttle', {
                viewModel: {require: 'common/data/shuttle/TreeShuttle'},
                template: {require: 'text!common/data/shuttle/TreeShuttle.html'}
            });
            ko.components.register('cmp:ListShuttle', {
                viewModel: {require: 'common/data/shuttle/ListShuttle'},
                template: {require: 'text!common/data/shuttle/ListShuttle.html'}
            });
            //progress
            ko.components.register('cmp:Progress', {
                viewModel: {require: 'common/progress/progress'},
                template: {require: 'text!common/progress/progress.html'}
            });
            //main
            ko.components.register('cmp:LoginMain', {
                viewModel: {require: 'common/main/login'},
                template: {require: 'text!common/main/login.html'}
            });
            ko.components.register('cmp:MobileIconItems', {
                viewModel: {require: 'common/main/MobileIconItems'},
                template: {require: 'text!common/main/MobileIconItems.html'}
            });
        }
    }
});

define(['knockout','config'], function (ko,Config) {
    var reg = {
        widget: function () {
            //message
            ko.components.register('cmp:MessageManage', {
                viewModel: {require: 'apps/ums/message/message'},
                template: {require: 'text!common/template/TMainCol2Col10.html'}
            });
            // message form
            ko.components.register('cmp:CommonMessageForm', {
                viewModel: {require: 'apps/ums/message/form/CommonMessageForm'},
                template: {require: 'text!common/data/form/AForm.html'}
            });
            ko.components.register('cmp:EmailForm', {
                viewModel: {require: 'apps/ums/message/form/EmailForm'},
                template: {require: 'text!common/data/form/AForm.html'}
            });
            ko.components.register('cmp:SystemMessageForm', {
                viewModel: {require: 'apps/ums/message/form/SystemMessageForm'},
                template: {require: 'text!common/data/form/AForm.html'}
            });
            //message card
            ko.components.register('cmp:SmsFormCard', {
                viewModel: {require: 'apps/ums/message/card/SmsFormCard'},
                template: {require: 'text!common/template/TCard.html'}
            });
            ko.components.register('cmp:WeiChatFormCard', {
                viewModel: {require: 'apps/ums/message/card/WeiChatFormCard'},
                template: {require: 'text!common/template/TCard.html'}
            });
            ko.components.register('cmp:EmailFormCard', {
                viewModel: {require: 'apps/ums/message/card/EmailFormCard'},
                template: {require: 'text!common/template/TCard.html'}
            });
            ko.components.register('cmp:SystemFormCard', {
                viewModel: {require: 'apps/ums/message/card/SystemFormCard'},
                template: {require: 'text!common/template/TCard.html'}
            });
            //contact
            ko.components.register('cmp:ContactManage', {
                viewModel: {require: 'apps/ums/contact/contact'},
                template: {require: 'text!common/template/TMainCol2Col10.html'}
            });
            ko.components.register('cmp:GroupForm', {
                viewModel: {require: 'apps/ums/contact/GroupForm'},
                template: {require: 'text!common/data/form/AForm.html'}
            });
            ko.components.register('cmp:ContactForm', {
                viewModel: {require: 'apps/ums/contact/ContactForm'},
                template: {require: 'text!common/data/form/AForm.html'}
            });
            //system
            ko.components.register('cmp:SystemManage', {
                viewModel: {require: 'apps/ums/system/system'},
                template: {require: 'text!common/template/TMainCol.html'}
            });
            ko.components.register('cmp:SystemForm', {
                viewModel: {require: 'apps/ums/system/SystemForm'},
                template: {require: 'text!common/data/form/AForm.html'}
            });
            //audit
            ko.components.register('cmp:AuditManage', {
                viewModel: {require: 'apps/ums/audit/audit'},
                template: {require: 'text!common/template/TMainCol2Col10.html'}
            });
            ko.components.register('cmp:SmsAuditCard', {
                viewModel: {require: 'apps/ums/audit/cards/SmsAuditCard'},
                template: {require: 'text!common/template/TCard.html'}
            });
            ko.components.register('cmp:WeiChatAuditCard', {
                viewModel: {require: 'apps/ums/audit/cards/WeiChatAuditCard'},
                template: {require: 'text!common/template/TCard.html'}
            });
            ko.components.register('cmp:EmailAuditCard', {
                viewModel: {require: 'apps/ums/audit/cards/EmailAuditCard'},
                template: {require: 'text!common/template/TCard.html'}
            });
            ko.components.register('cmp:SystemAuditCard', {
                viewModel: {require: 'apps/ums/audit/cards/SystemAuditCard'},
                template: {require: 'text!common/template/TCard.html'}
            });
            //statistic
            ko.components.register('cmp:Statistic', {
                viewModel: {require: 'apps/ums/statistic/statistic'},
                template: {require: 'text!apps/ums/statistic/statistic.html'}
            });
            //sys setting
            ko.components.register('cmp:SystemSetting', {
                template:  '<div class="xgx-sticky-main" data-bind="css:$root.fluidCss">\n' +
                        '   <div class="row">\n' +
                        '      <div class="col pt-1">\n' +
                        '         <iframe width="100%" height="99%" frameborder="0" src="/ums/a/sysIndex"></iframe>\n' +
                        '      </div>\n' +
                        '   </div>\n' +
                        '</div>'
            });
        }
    };

    return {
        registerCmp: function () {
            reg.widget();
        }
    }
});

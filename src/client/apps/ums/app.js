r_config.paths['registerUms'] = 'apps/ums/register';
r_config.paths['css-ums'] = 'assets/css/ums';
r_config.paths['aMessageForm'] = 'apps/ums/message/form/AMessageForm';
r_config.paths['mMessage'] = 'apps/ums/model/message/MMessage';
r_config.paths['mSms'] = 'apps/ums/model/message/MSms';
r_config.paths['mWeiChat'] = 'apps/ums/model/message/MWeiChat';
r_config.paths['mEmail'] = 'apps/ums/model/message/MEmail';
r_config.paths['mSystemMessage'] = 'apps/ums/model/message/MSystemMessage';
r_config.paths['mSystemPass'] = 'apps/ums/model/system/MSystemPass';
r_config.paths['mSystem'] = 'apps/ums/model/system/MSystem';
r_config.paths['mGroup'] = 'apps/ums/model/contact/MGroup';
r_config.paths['mContact'] = 'apps/ums/model/contact/MContact';
r_config.paths['mMessageAudit'] = 'apps/ums/model/audit/MMessageAudit';
r_config.paths['mSmsAudit'] = 'apps/ums/model/audit/MSmsAudit';
r_config.paths['mWeiChatAudit'] = 'apps/ums/model/audit/MWeiChatAudit';
r_config.paths['mEmailAudit'] = 'apps/ums/model/audit/MEmailAudit'
r_config.paths['mSystemAudit'] = 'apps/ums/model/audit/MSystemAudit';
r_config.paths['aCommonAuditCard'] = 'apps/ums/audit/cards/ACommonAuditCard';

require.config(r_config);
require(['jquery', 'knockout', 'root','config'],
    function ($, ko, Root,Config) {
        Config.baseUrl = '/ums/a';
        Config.modelDefaultId = '';
        require(['registerUms','util', 'css!css-ums'], function (RegisterApp,Util) {
            $(document).ready(
                function () {
                    RegisterApp.registerCmp();
                    ko.root = new Root({
                        fluid:true,
                        topCmp: {
                            name: 'cmp:NavigationBar', params: {
                                title:'UMS 统一消息平台',
                                menuItems: [],
                                shortcutItems: [{name: '', icon: 'user', subItems: [ '退出']}],
                                shortcutClick: function (name) {
                                    switch (name) {
                                        case '退出':
                                            self.location = '/ums/a/logout';
                                            break;
                                        default:
                                    }
                                }
                            }
                        }
                    });

                    Util.ajaxGet(Util.getRestUrl('/menus'),{},function (data) {
                        if(data.success){
                            ko.root.menus = data.data;
                            ko.root.topCmp().params.menuItems = Util.findItemsByField(ko.root.menus,'1','parentId');

                            ko.applyBindings(ko.root);
                        }
                    });


                    ko.root.router.init();
                });
        });
    });



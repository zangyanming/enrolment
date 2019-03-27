define(['knockout','util','knockout-mapping'], function (ko,Util) {
    function ViewModel(params) {
        $.extend(this,{
            logo:'',
            title:''
        })
        $.extend(this, params);

        var scope = this;

        scope.css = {'xgx-login':!ko.root.mobile,'xgx-login-mobile':ko.root.mobile};
        scope.loginForm = {
            onLogin:function (formData,callback) {
                Util.ajaxPost(scope.url,ko.mapping.toJSON(formData),function (data) {
                    if(data.success){
                        if(data.data.logged==0){
                             callback();
                        }
                        else{
                            if(scope.onLoginSuccess){
                                scope.onLoginSuccess(data);
                            }
                        }
                    }
                    else{
                        Util.showAlert(data.message,'error');
                    }
                });
            },
            onModifyPassword:function () {

            }
        }
    }

    return ViewModel;
});

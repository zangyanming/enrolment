define(['knockout','mLogin'], function (ko,MLogin) {
    function ViewModel(params) {
        $.extend(this, params);

        var scope = this;

        scope.showAlert = ko.observable(false);
        scope.record = new MLogin({isKo:true});


        scope.record.getField('name').isFormField=true;
        scope.record.getField('password').isFormField=true;

        function keyup_login(event){
            if(event.keyCode==13){
                scope._onLogin();
            }
        }

        $("[name='login_form_name']").keyup(keyup_login);
        $("[name='login_form_password']").keyup(keyup_login);

        scope._onLogin = function () {
            if(scope.record.checkValidity()) {
                if (scope.onLogin) {
                    scope.onLogin(ko.mapping.toJS(scope.record), function () {
                        scope.showAlert(true);
                    });
                }
            }
        }

        scope._onModifyPassword = function () {
            if(scope.onModifyPassword){
                scope.onModifyPassword();
                scope.record.revert();
                var loginForm = $('#login_form')[0];

                if(loginForm.classList.contains('was-validated')){
                    loginForm.classList.remove('was-validated');
                }
            }

            scope.showAlert(false);
        }
    }

    return ViewModel;
});
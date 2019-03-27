define(['knockout', 'toastr', 'config'], function (ko, toastr, Config) {
    var util = {
        dicts: {
            personSex: [
                {text: "男", value: 1},
                {text: "女", value: 2}
            ]
        }
    };
    //css
    var cssUtil = {
        zico: function (icon, size, animate) {
            var css = {};

            if (icon) {
                css['zi'] = true;
                css['zi_' + icon] = true;
            }

            if (size) {
                css['zi_' + size + 'x'] = true;
            }

            if (animate) {
                css['zi_' + animate] = true;
            }

            return css;
        },
        addCssActive: function (items, firstActive) {//add the css active field to model for bootstrap ui active bind
            for (var index = 0; index < items.length; ++index) {
                if (0 == index && firstActive) {
                    items[0]._cssActive = ko.observable(true);
                }
                else {
                    items[index]._cssActive = ko.observable(false);
                }
            }
        },
        changeCssActive: function (item, items) {
            if (!item._cssActive()) {
                items.forEach(function (itm) {
                    if (itm._cssActive()) itm._cssActive(false);
                });

                item._cssActive(true);

                return true;
            }

            return false;
        }
    };
    //ajax
    $.ajaxSetup({
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        xhrFields: Config.crossDomain ? {withCredentials: true} : {},
        crossDomain: Config.crossDomain,
        beforeSend: function (jqXHR, request) {
            // if(request.type != 'GET'){
            ko.root.showProgress(true);
            // }

            if (ko.root.mobile) {
                jqXHR.setRequestHeader('device', 'mobile');
            }
        },
        complete: function () {
            ko.root.showProgress(false);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            var errorText = '';

            switch (jqXHR.status) {
                case 401:
                    errorText = '没有提供认证信息';
                    break;
                case 403:
                    errorText = '请求的资源不允许访问';
                    break;
                case 404:
                    errorText = '请求的内容不存在';
                    break;
                case 408:
                    errorText = '客户端请求超时';
                    break;
                case 500:
                    errorText = '服务器错误';
                    break;
                default:
                    errorText = '未知错误';
            }

            toastr.error('网络请求错误');
            console.log(jqXHR.status + ':' + errorText);
        },
        cache: false
    });
    var ajaxUtil = {
        ajaxGet: function (url, params, callback) {
            $.ajax
            ({
                type: "get",
                data: params,
                url: url,
                success: function (data) {
                    callback(data);
                }
            });
        },
        ajaxGetSync: function (url, params) {
            var result = null;

            $.ajax
            ({
                type: "get",
                data: params,
                url: url,
                async: false,
                success: function (data) {
                    result = data;
                }
            });

            return result;
        },
        ajaxPost: function (url, postData, callback) {
            $.ajax
            ({
                type: "post",
                data: (postData instanceof Object) ? JSON.stringify(postData) : postData,
                url: url,
                success: function (data) {
                    callback(data);
                }
            });
        },
        ajaxPostSync: function (url, postData) {
            var result = null;

            $.ajax
            ({
                type: "post",
                url: url,
                data: (postData instanceof Object) ? JSON.stringify(postData) : postData,
                async: false,
                success: function (data) {
                    result = data;
                }
            });

            return result;
        },
        ajaxPut: function (url, putData, callback) {
            $.ajax
            ({
                type: "put",
                data: (putData instanceof Object) ? JSON.stringify(putData) : putData,
                url: url,
                success: function (data) {
                    callback(data);
                }
            });
        },
        ajaxPutSync: function (url, putData) {
            var result = null;

            $.ajax
            ({
                type: "put",
                url: url,
                data: (putData instanceof Object) ? JSON.stringify(putData) : putData,
                async: false,
                success: function (data) {
                    result = data;
                }
            });

            return result;
        },
        ajaxDelete: function (url, delData, callback) {
            $.ajax
            ({
                type: "delete",
                data: delData,
                url: url,
                success: function (data) {
                    callback(data);
                }
            });
        },
        ajaxDeleteSync: function (url, delData) {
            var result = null;

            $.ajax
            ({
                type: "delete",
                url: url,
                data: delData,
                async: false,
                success: function (data) {
                    result = data;
                }
            });

            return result;
        },
        ajaxForm: function (url, model, callback) {
            var formData = new FormData();

            model.fields.forEach(function (field) {
                if (field.name.substr(0, 1) != '_' && model[field.name]) {
                    switch (field.type) {
                        case 'file':
                            if (model[field.name].file && model[field.name].file()) {
                                formData.append(field.name, model[field.name].file());
                            }
                            break;
                        default:
                            if (model[field.name] && model[field.name] instanceof Function) {
                                formData.append(field.name, model[field.name]());
                            }
                    }
                }
            });

            for (var key in model.extraData) {
                formData.append(key, model.extraData[key]);
            }

            $.ajax({
                url: url + (model.id() == 0 ? '' : '/' + model.id()),
                type: model.id() == 0 ? 'POST' : 'POST',
                data: formData,
                // 告诉jQuery不要去处理发送的数据
                processData: false,
                // 告诉jQuery不要去设置Content-Type请求头
                contentType: false,
                success: function (data) {
                    callback(data);
                }
            });
        },
        ajaxCallback:function (data,callback,storeId) {
            this.showAlert(data);

            if (callback) callback(data);

            if (storeId && data.success) {
                ko.storeCache[storeId].empty();
                ko.storeCache[storeId].load();
            }
        }
    };
    //component
    var componentUtil = {
        generateOperations: function (operations) {
            var tempOperations = [];

            if (operations && operations.length > 0) {
                operations.forEach(function (item, index) {
                    switch (item) {
                        case 'view':
                            tempOperations.push({name: item, css: cssUtil.zico('eye'), title: '查看'});
                            break;
                        case 'delete':
                            tempOperations.push({name: item, css: cssUtil.zico('trashalt'), title: '删除'});
                            tempOperations[tempOperations.length - 1].css['text-danger'] = true;
                            break;
                        case 'add':
                            tempOperations.push({name: item, css: cssUtil.zico('plus'), title: '添加'});
                            break;
                        case 'refresh':
                            tempOperations.push({name: item, css: cssUtil.zico('sync'), title: '刷新'});
                            break;
                        case 'edit':
                            tempOperations.push({name: item, css: cssUtil.zico('edit'), title: '编辑'});
                            break;
                        case 'query':
                            tempOperations.push({name: item, css: cssUtil.zico('search'), title: '查询'});
                            break;
                        case 'upload':
                            tempOperations.push({name: item, css: cssUtil.zico('upload'), title: '上传'});
                            break;
                        default:
                            if (item instanceof Object) {
                                item.css = item.icon ? cssUtil.zico(item.icon) : cssUtil.zico(item.name);
                                item.title = item.title ? item.title : '';
                                tempOperations.push(item);
                            }
                            else {
                                tempOperations.push({name: item, css: cssUtil.zico(item), title: ''});
                            }
                    }
                });
            }

            return tempOperations;
        },
        operationSwitch: function (operation, record) {
            switch (operation.name) {
                case 'view':
                case 'edit':
                    ko.root.openModal({operation: operation.name, record: record});
                    break;
                case 'delete':
                    ko.root.openModal({level: 'danger', operation: operation.name, record: record});
                    break;
                default:
            }
        },
        treeConfig: {
            checkedIcon: 'zi zi_squareCorrect',
            uncheckedIcon: 'zi zi_squareLine',
            expandIcon: 'zi zi_forRight',
            collapseIcon: 'zi zi_forDown',
            color: '#17a2b8',
            selectedBackColor: '#7bbfea',
            onhoverColor: '#eeeeff'
        },
        treeFormat:function(data,level,fun){
            var scope = this;
            if(data instanceof Array) {
                data.forEach(function (childData) {
                    scope.treeFormat(childData,level,fun);
                });
            }
            else{
                if(fun) fun(data);
                if(data.nodes){
                    scope.treeFormat(data.nodes,level,fun);
                }
            }
        }
    };
    //common
    var commonUtil = {
        showAlert: function (data, level) {
            if (level) {
                switch (level) {
                    case 'success':
                        toastr.success(data ? data : '成功');
                        break;
                    case 'warning':
                        toastr.warning(data ? data : '警告');
                        break;
                    case 'error':
                        toastr.error(data ? data : '失败');
                        break;
                }
            }
            else {
                if (data.success) {
                    toastr.success(data.message ? data.message : '成功');
                }
                else {
                    toastr.error(data.message ? data.message : '失败', '', {timeOut: 5000});
                }
            }
        },
        checkDevice: function () {
            var userAgentInfo = navigator.userAgent;
            var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");
            var flag = {device: 'PC', env: 'UNKNOWN'};

            if (!!window.ActiveXObject || "ActiveXObject" in window) {
                return {device: 'PC', env: 'IE'};
            }
            if (navigator.userAgent.indexOf("Android") != -1) {
                return {device: 'Mobile', env: 'Android'};
            }
            if (navigator.userAgent.indexOf("iPhone") != -1) {
                return {device: 'Mobile', env: 'iPhone'};
            }
            if (navigator.userAgent.indexOf("Edge") != -1) {
                return {device: 'PC', env: 'Edge'};
            }
            if (navigator.userAgent.indexOf("Chrome") != -1) {
                return {device: 'PC', env: 'Chrome'};
            }
            if (navigator.userAgent.indexOf("Firefox") != -1) {
                return {device: 'PC', env: 'Firefox'};
            }
            if (navigator.userAgent.indexOf("Safari") != -1) {
                return {device: 'PC', env: 'Safari'};
            }
            if (navigator.userAgent.indexOf("SymbianOS") != -1) {
                return {device: 'Mobile', env: 'SymbianOS'};
            }
            if (navigator.userAgent.indexOf("Windows Phone") != -1) {
                return {device: 'Mobile', env: 'Windows Phone'};
            }
            if (navigator.userAgent.indexOf("iPad") != -1) {
                return {device: 'Mobile', env: 'iPad'};
            }
            if (navigator.userAgent.indexOf("iPod") != -1) {
                return {device: 'Mobile', env: 'iPod'};
            }

            return flag;
        },
        identityCodeValid: function (code) {
            var city = {
                11: "北京",
                12: "天津",
                13: "河北",
                14: "山西",
                15: "内蒙古",
                21: "辽宁",
                22: "吉林",
                23: "黑龙江 ",
                31: "上海",
                32: "江苏",
                33: "浙江",
                34: "安徽",
                35: "福建",
                36: "江西",
                37: "山东",
                41: "河南",
                42: "湖北 ",
                43: "湖南",
                44: "广东",
                45: "广西",
                46: "海南",
                50: "重庆",
                51: "四川",
                52: "贵州",
                53: "云南",
                54: "西藏 ",
                61: "陕西",
                62: "甘肃",
                63: "青海",
                64: "宁夏",
                65: "新疆",
                71: "台湾",
                81: "香港",
                82: "澳门",
                91: "国外 "
            };
            var tip = "";
            var pass = true;

            if (!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)) {
                tip = "身份证号格式错误";
                pass = false;
            }
            else if (!city[code.substr(0, 2)]) {
                tip = "身份证地址编码错误";
                pass = false;
            }
            else {
                //18位身份证需要验证最后一位校验位
                if (code.length == 18) {
                    code = code.toUpperCase().split('');
                    //∑(ai×Wi)(mod 11)
                    //加权因子
                    var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
                    //校验位
                    var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
                    var sum = 0;
                    var ai = 0;
                    var wi = 0;
                    for (var i = 0; i < 17; i++) {
                        ai = code[i];
                        wi = factor[i];
                        sum += ai * wi;
                    }
                    var last = parity[sum % 11];
                    if (parity[sum % 11] != code[17]) {
                        tip = "身份证校验位错误";
                        pass = false;
                    }
                }
            }
            if (!pass) toastr.error(tip);
            return {success: pass, message: tip};
        },
        getRestUrl: function (urlRelative) {
            return Config.baseUrl + '/rest' + urlRelative;
        },
        getAjaxUrl: function (urlRelative) {
            return Config.baseUrl + urlRelative;
        },
        generateUrl: function (proxy) {
            var url = this.getRestUrl(proxy.url);

            if (proxy.type == 'ajax') {
                url = this.getAjaxUrl(proxy.url);
            }

            return url;
        },
        formatTpl: function (tpl) {
            //the tpl eg {0}:{1}:{2}
            if (tpl && arguments.length > 1) {
                for (var i = 1; i < arguments.length; ++i) {
                    tpl = tpl.replace('{' + (i - 1) + '}', arguments[i]);
                }
            }

            return tpl;
        },
        firstCharacterU: function (word) {
            if (word) {
                return word.charAt(0).toUpperCase() + word.substr(1, word.length - 1);
            }
            else {
                return "";
            }
        },
        findItemByField: function (items, fieldValue, fieldName) {
            var item;

            if (items) {
                for (var i = 0; i < items.length; ++i) {
                    if (items[i][fieldName] == fieldValue) {
                        item = items[i];

                        break;
                    }
                }

            }

            return item;
        },
        findItemsByField: function (items, fieldValue, fieldName) {
            var menus = [];

            if (items) {
                items.forEach(function (menu) {
                    if (menu[fieldName] == fieldValue) {
                        menus.push(menu);
                    }
                });
            }

            return menus;
        },
        findSubItemsByParentField: function (items, fieldValue, fieldName, pFieldName) {
            var item = this.findItemByField(items, fieldValue, fieldName);
            var rtn = [];

            if (item) {
                rtn = this.findItemsByField(items, item.id, pFieldName);
            }

            return rtn;
        },
        getLocationHash: function (level) {
            var hash = location.hash;

            if (hash == '' || hash == '#' || hash == '#/') {
                return '';
            }
            else {
                if (level == undefined || level == 0) {
                    return hash.split('#')[1];
                }
                else {
                    var hashSplit = hash.replace('#', '').split('/');

                    return hashSplit.splice(0, level + 1).join('/');
                }

            }
        },
        getHashMaxLevel:function() {
            var hash = location.hash;

            if (hash == '' || hash == '#' || hash == '#/') {
                return 0;
            }
            else{
                return hash.split('/').length - 1;
            }
        },
        getCmpFromHash: function (level) {
            var hash = this.getLocationHash(level);
            var cmp = {name: 'cmp:Empty', params: {}};

            if (hash) {
                var lastHashSeg = hash.substring(hash.lastIndexOf('/') + 1, hash.length).split('&');

                if (ko.components.isRegistered('cmp:' + lastHashSeg[0])) cmp.name = 'cmp:' + lastHashSeg[0];
                if (lastHashSeg[1]) cmp.params = this.URI2JS(lastHashSeg[1]);
            }

            console.log(cmp);
            return cmp;
        },
        JS2URI:function (obj) {
            return encodeURI(JSON.stringify(obj));
        },
        URI2JS:function (uri) {
            return JSON.parse(decodeURI(uri));
        }
    };

    $.extend(util, cssUtil);
    $.extend(util, ajaxUtil);
    $.extend(util, componentUtil);
    $.extend(util, commonUtil);

    return util;
});

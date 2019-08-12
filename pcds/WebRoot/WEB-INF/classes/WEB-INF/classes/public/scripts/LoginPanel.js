LoginPanel = function() {
    var win, f;

    var buildForm = function() {
        f = new Ext.form.FormPanel( {
            bodyStyle : 'padding-top:6px',
            defaultType : 'textfield',
            labelAlign : 'right',
            labelWidth : 55,
            labelPad : 0,
            frame : true,
			url : 'loginAjax.do?method=doLogin',

            defaults : {
                allowBlank : false,
                width : 158
            },
            items : [ {
                    cls : 'user',
                    name : 'userID',
                    value:'00000',
                    fieldLabel : '用户',
                    blankText : '用户不能为空'
                }, {
                    cls : 'key',
                    name : 'password',
                    value:'1',
                    fieldLabel : '密码',
                    blankText : '密码不能为空',
                    inputType : 'password'
                },
				{
					xtype:'hidden',
					name:'width',
					value:screen.width
				},
				{
					xtype:'hidden',
					name:'height',
					value:screen.height
				}]
        });
    };
    var buildWin = function() {
        win = new Ext.Window( {
            el : 'win',
            title : '登陆系统',
            width : 265,
            height : 140,
            layout : 'column',
            collapsible : true,
            defaults : {
                border : false
            },
            items : {
                columnWidth : 1,
                items : f
            },
            buttonAlign : 'center',
            buttons : [ {
                    text : '登陆',
                    handler : login
                }, {
                    text : '重置',
                    handler : reset
                }]
        });
    };
    var login = function() {
        f.form.submit({
                waitMsg : '正在登录，请稍候。。。。。。',
                
                method : 'POST',
				failure: function(form, action) {
					Ext.MessageBox.alert('错误', action.result.info);
				},
                success : function(form, action) {
                    doLogin();
                }
            });
    };
    var reset = function() {
        f.form.reset();
    };
    return {
        init : function() {
            buildForm();
            buildWin();
            win.show();
        }
    }
}();
// 当当前页面DOM加载完毕后,在LoginPanel作用域内执行LoginPanel.init.
Ext.onReady(LoginPanel.init, LoginPanel);
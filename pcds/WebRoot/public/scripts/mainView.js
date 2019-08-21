//修改密码窗体
Ext.form.Field.prototype.msgTarget = 'under';
MyModifyWindowUi = Ext.extend(Ext.Window, {
	id : 'modifyWindow',
	title : '修改密码',
	width : 300,
	height : 220,
	modal : true,
	resizable : false,
	buttonAlign : 'center',
	layout : 'fit',
	buttons : [ {
		text : '确定',
		id : 'confrim',
		handler : function() {
		    var obj_form=Ext.getCmp('modifyPasswordForm').getForm();
		    if(obj_form.isValid()){
		    	var oldPwd = Ext.getCmp('old_password').getValue();
		    	var newPwd = Ext.getCmp('password').getValue();
		    	if(checkPassword(oldPwd,newPwd)){
		    		 obj_form.submit({
			            url : pathUrl+'/userExtAjax.do?method=modifyPassword&login_num=0',
			            success:function(f,action){
			                Ext.getCmp('modifyWindow').close();
			                Ext.Msg.show({
			                	title : '提示',
			                	msg : '密码修改成功，系统需重新登录！',
			                	buttons : Ext.MessageBox.OK,
			                	fn : logout_,
			                	animEl : 'mb3'
			                });
			            },
			            failure:function(f,action){
			                Ext.Msg.alert('消息',action.result.info);
			            }
			        });
		    	}
		    }
		}
	}, {
		text : '重置',
		handler : function() {
			Ext.getCmp('modifyPasswordForm').getForm().reset();
		}
	}, {
		text : '取消',
		handler : function() {
			this.ownerCt.ownerCt.close();
		}
	} ],
	initComponent : function() {
		Ext.applyIf(this, {
			items : [{
			    xtype : 'form',
			    id : 'modifyPasswordForm',
			    labelWidth : 55,
			    frame : true,
	            bodyStyle: 'padding:5px 15px',
	            defaultType : 'textfield',
	            defaults : {inputType:'password',allowBlank:false,blankText:'不能为空'},
			    items: [{
			        id : 'old_password',
			        name : 'old_password',
			        fieldLabel : '旧密码',
			        allowBlank:false
			    },{
			        id : 'password',
			        name : 'password',
			        fieldLabel : '新密码'
			    },{
			        name : 'again',
			        fieldLabel : '再次输入',
			        vtype : 'password',  
                    initialPassField : 'password'
			    }/*,{
        		xtype : 'radiogroup',
        		fieldLabel : '更改周期',
        		id : 'ask_month',
        		items : [
        			{boxLabel : '一个月',name : 'ask_month',inputValue : 1, checked : true},
        			{boxLabel : '三个月',name : 'ask_month',inputValue : 3},
        			{boxLabel : '六个月',name : 'ask_month',inputValue : 6}
        		]
        	}*/]
			}]
		});
		MyModifyWindowUi.superclass.initComponent.call(this);
	}
});
Ext.apply(Ext.form.VTypes,{  
    password:function(val,field){  
        if(field.initialPassField){  
            var pwd = Ext.getCmp(field.initialPassField);  
            return (val == pwd.getValue());  
        }  
        return true;  
    },  
    passwordText:'两次密码不一致'
});  


function checkPassword(oldPwd,newPwd,verPwd){

	var reg =/^(?!^\d+$)(?!^[a-z]+$)(?!^[A-Z]+$)(?!^[a-zA-Z]+$)\S{8,20}/;

		if(oldPwd.length == 0){
	    	Ext.MessageBox.alert('提示',"旧密码不能为空.");
			return false;
	    }else if(newPwd.length == 0){
	    	Ext.MessageBox.alert('提示',"新密码不能为空.");
	    	return false;
	    }else if (!reg.test(newPwd)){
			Ext.MessageBox.alert('提示',"密码最少由数字,字母,特殊字符三类中的两种组成且长度在8位和20位之间（包含）！");
			return false;
		}else if(newPwd == oldPwd){
	    	Ext.MessageBox.alert('提示',"新密码与旧密码不能相等.");
	    	return false;
		}else{
			return true;
		}
	}


function logout_ (){

	Ext.Ajax.request({
			url : pathUrl + '/loginAjax.do?method=doLogout',
			method: 'POST',
				params: {},
				failure:function(response, options){
					Ext.MessageBox.alert('提示',response.responseText);
				}, 
				success:function(response, options){
					window.location.href= pathUrl + '/login.jsp';
				}
		});
	
}
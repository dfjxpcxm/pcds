
var is_expand = false;
var dhtml = '';
var special_user_id = '';
var special_user_name = '';

function addUser(store){
	if(addWindow != null || editWindow != null)
		return;
	
	//定义显示数据变量===========================================
	var userId = {
               xtype: 'textfield',
                  name: 'user_id',
                  id : 'add_user_id',
                  fieldLabel: '用户ID<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
                  allowBlank: false,
                  anchor: '91%'
              };
	var userName = {
          	xtype: 'textfield',
           name: 'user_name',
           id : 'add_user_name',
           fieldLabel: '用户名<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
           allowBlank: false,
           anchor: '91%'
   	};
   	var employeeId = {
           xtype: 'textfield',
           name: 'employee_id',
           id : 'add_employee_id',
           fieldLabel: '员工号',
           anchor: '91%'
   	};
	 
	var ownerOrgSelector = new OwnerOrgSelector('');
   	var  busiLineSelector = new BusiLineSelector({fieldLabel:'归属条线<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'});
   	var gender = new GenderSelector();
	var ethnicity = new EthnicitySelector();
   	var eduBack = new EduBackSelector();
   	var bornDate ={
		           xtype: 'datefield',
		           name: 'born_date',
		           id : 'add_born_date',
		           format: 'Y-m-d',
		           fieldLabel: '出生日期',
		           anchor: '91%'
   			};
   	
   	var userMobile = {
           xtype: 'textfield',
           name: 'telephone',
           id : 'add_telephone',
           fieldLabel: '手机',
           regex : /^(\d{11})$|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
           regexText :'匹配格式:11位手机号码 ,(3-4位区号)-(7-8位直播号码)-(1-4位分机号),7-8位直播号码',
           anchor: '91%'
   	};
	var userPost = {
           xtype: 'textfield',
           name: 'postal',
           id : 'add_user_post',
           regex : /^[0-9]{1}(\d{5})$/,
           regexText :'邮编格式不正确',
           fieldLabel: '邮编',
           anchor: '91%'
   	};
	var beginDate = {
   		xtype: 'datefield',
           name: 'begin_date',
           format: 'Y-m-d',
           id : 'add_begin_date',
           fieldLabel: '开始时间',
           anchor: '91%'
    	};
	
   	
   	//右侧列的定义
   	var password ={
           xtype: 'textfield',
           inputType: 'password',
           name: 'password',
           id : 'add_password',
           fieldLabel: '密码<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
           allowBlank: false,
           anchor: '91%'
       };
   	var certId = {
           xtype: 'textfield',
           name: 'id_card',
           id : 'add_id_card',
           fieldLabel: '身份证号<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
           regex : /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
           regexText :'身份证号格式不正确,请检查',
           allowBlank: false,
           anchor: '91%'
   	};
   	var jobSeqCode = new CustJobSeqSelector();
   	var manageOrgSelector = new ManageOrgSelector('');
   	var jobTypeCodeSelector = new JobTypeCodeSelector({fieldLabel:'职位类型<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'});
   	var postType = new PostTypeSelector();
   	
   	var joinDate = {
           xtype: 'datefield',
           format: 'Y-m-d',
           name: 'join_date',
           id : 'add_join_date',
           fieldLabel: '入行日期',
           anchor: '91%'
   	};
   	var leaveDate = {
           xtype: 'datefield',
           format: 'Y-m-d',
           name: 'leave_date',
           id : 'add_leave_date',
           fieldLabel: '离行日期',
           anchor: '91%'
   	};
   	
   	var statusSelector = new StatusSelector();
   	var userAddress = {
           xtype: 'textfield',
           name: 'address',
           id : 'add_address',
           fieldLabel: '地址',
           anchor: '91%'
   	};
   	var userEmail = {
           xtype: 'textfield',
           name: 'email',
           id : 'add_email',
           regex : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
           regexText :'Email格式不正确,请检查',
           fieldLabel: 'Email',
           anchor: '91%'
   	};
   	var endDate = {
           xtype: 'datefield',
           name: 'end_date',
           id : 'add_end_date',
           format: 'Y-m-d',
           fieldLabel: '结束时间',
           anchor: '91%'
	};
   	
   	//最下端  备注
    var remark = {
        xtype: 'textarea',
        name: 'remark',
        id : 'add_remark',
        fieldLabel: '备注',
        allowBlank: true,
        anchor: '96%'}
	
	//定义显示数据变量===========================================
	
	addWindow = new Ext.Window({
		layout: 'form',
		title: '添加用户',
		width: 640,
		height: 500,
		border: false,
		modal : true,
		buttonAlign: 'center',
		items: [{
		    xtype: 'form',
		    frame: true,
		    border: false,
		    method: 'POST',
		    url: pathUrl + '/user/addUser',
		    id: 'addFormPanel',
		    items:[{
		   		    xtype: 'panel',
		   		    frame: true,
		   		    bodyStyle: 'padding:5px',
		   		    border: false,
		   		    layout: 'column',
		   		    items: [{
		   		            xtype: 'panel',
		   		            border: false,
		   		            layout: 'form',
		   		            labelWidth: 70,
		   		            labelAlign: 'left',
		   		            columnWidth: 0.5,
		   		            items: [
		   		                    userId,				//用户id
		   		                    userName,			//用户名
		   		                    employeeId,			//员工号
		   		                    ownerOrgSelector,	//所属机构 
		   		                    busiLineSelector ,	//条线
		   		                    gender,				//性别
		   		                    ethnicity,			//民族
		   		                    eduBack,			//学历
		   		                    bornDate,			//出生日期
		   		                    userMobile,			//手机号
		   		                    userPost,			//邮编
		   		                    beginDate			//开始时间
		   	                	
		   	                	]
		   		        },{
		   		            xtype: 'panel',
		   		            border: false,
		   		            layout: 'form',
		   		            labelWidth: 70,
		   		            labelAlign: 'left',
		   		            columnWidth: 0.5,
		   		            items: [
		   		                    password,			//密码
		   		                    certId,				//身份证
		   		                    jobSeqCode,			// 序列
		   		                    manageOrgSelector,	//权限机构
		   		                    jobTypeCodeSelector,//职位类型
		   		                    postType,			//岗位
		   		                    joinDate,			//入行日期
		   		                    leaveDate,			//离行日期
		   		                    statusSelector ,	//状态
		   		                    userAddress,		//地址
		   		                    userEmail,			//邮箱
		   		                    endDate				//结束日期
		   	                	]
		   	        },{
	   		            xtype: 'panel',
	   		            border: false,
	   		            layout: 'form',
	   		            labelWidth: 70,
	   		            labelAlign: 'left',
	   		            columnWidth: 1,
	   		            items: [ 
	   		                     remark
	   		                    ]
		   	        }]
		   		  
		    }]
		
		}],
		buttons: [{
			text: '添加',
			handler: function(){
				var formPanel = Ext.getCmp("addFormPanel");
				if(formPanel.form.isValid()){
					formPanel.form.submit({      
			            waitMsg:'正在处理,请稍候......',         
			            timeout:30000,
			            failure: function(form, action) {
						    Ext.MessageBox.alert('错误', action.result.info);
						},
						success: function(form, action) {
							Ext.Msg.alert('提示',action.result.info);
						    addWindow.destroy();
							addWindow = null;
							store.reload();
						}
			        });					
				}else{
					Ext.Msg.alert("提示信息","请输入必填项");
				}
			}
		},{
			text: '取消',
			handler: function(){
				addWindow.destroy();
				addWindow = null;
			}
		}]
	});
	addWindow.on("close",function(){
		addWindow.destroy();
		addWindow = null;
	});
	addWindow.show();
	ownerOrgSelector.initUI();
	manageOrgSelector.initUI();	
}



// 修改用户信息
function modifyUser(ownerOrgId, bankOrgId, userId, store){
	var oldParam = "?oldOwerOrgId="+ownerOrgId+"&oldBankOrgId="+bankOrgId;
	if(addWindow != null || editWindow != null)
		return;
	//定义显示数据变量===========================================
	var userIdObj = {
               xtype: 'textfield',
                  name: 'user_id',
                  id : 'edit_user_id',
                  fieldLabel: '用户ID<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
                  allowBlank: false,
                  readOnly:true,
                  anchor: '91%'
              };
	var userName = {
          	xtype: 'textfield',
           name: 'user_name',
           id : 'edit_user_name',
           fieldLabel: '用户名<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
           allowBlank: false,
           anchor: '91%'
   	};
   	var employeeId = {
           xtype: 'textfield',
           name: 'employee_id',
           id : 'edit_employee_id',
           fieldLabel: '员工号',
           anchor: '91%'
   	};
	 
	var ownerOrgSelector = new OwnerOrgSelector('');
   	var  busiLineSelector = new BusiLineSelector({fieldLabel:'归属条线<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'});
   	var gender = new GenderSelector();
	var ethnicity = new EthnicitySelector();
   	var eduBack = new EduBackSelector();
   	var bornDate ={
		           xtype: 'datefield',
		           name: 'born_date',
		           id : 'edit_born_date',
		           format: 'Y-m-d',
		           fieldLabel: '出生日期',
		           anchor: '91%'
   			};
   	
   	var userMobile = {
           xtype: 'textfield',
           name: 'telephone',
           id : 'edit_telephone',
           fieldLabel: '手机',
           regex : /^(\d{11})$|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
           regexText :'匹配格式:11位手机号码 ,(3-4位区号)-(7-8位直播号码)-(1-4位分机号),7-8位直播号码',
           anchor: '91%'
   	};
	var userPost = {
           xtype: 'textfield',
           name: 'postal',
           id : 'edit_user_post',
           regex : /^[0-9]{1}(\d{5})$/,
           regexText :'邮编格式不正确',
           fieldLabel: '邮编',
           anchor: '91%'
   	};
	var beginDate = {
   		xtype: 'datefield',
           name: 'begin_date',
           format: 'Y-m-d',
           id : 'edit_begin_date',
           fieldLabel: '开始时间',
           anchor: '91%'
    	};
	
   	
   	//右侧列的定义
   	var password ={
           xtype: 'textfield',
           inputType: 'password',
           name: 'password',
           id : 'edit_password',
           fieldLabel: '密码<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
           allowBlank: false,
           anchor: '91%'
       };
   	var certId = {
           xtype: 'textfield',
           name: 'id_card',
           id : 'edit_id_card',
           fieldLabel: '身份证号<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
           regex : /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
           regexText :'身份证号格式不正确,请检查',
           allowBlank: false,
           anchor: '91%'
   	};
   	var jobSeqCode = new CustJobSeqSelector();
   	var manageOrgSelector = new ManageOrgSelector('');
   	var jobTypeCodeSelector = new JobTypeCodeSelector({fieldLabel:'职位类型<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'});
   	var postType = new PostTypeSelector();
   	
   	var joinDate = {
           xtype: 'datefield',
           format: 'Y-m-d',
           name: 'join_date',
           id : 'edit_join_date',
           fieldLabel: '入行日期',
           anchor: '91%'
   	};
   	var leaveDate = {
           xtype: 'datefield',
           format: 'Y-m-d',
           name: 'leave_date',
           id : 'edit_leave_date',
           fieldLabel: '离行日期',
           anchor: '91%'
   	};
   	
   	var statusSelector = new StatusSelector();
   	var userAddress = {
           xtype: 'textfield',
           name: 'address',
           id : 'edit_address',
           fieldLabel: '地址',
           anchor: '91%'
   	};
   	var userEmail = {
           xtype: 'textfield',
           name: 'email',
           id : 'edit_email',
           regex : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
           regexText :'Email格式不正确,请检查',
           fieldLabel: 'Email',
           anchor: '91%'
   	};
   	var endDate = {
           xtype: 'datefield',
           name: 'end_date',
           id : 'edit_end_date',
           format: 'Y-m-d',
           fieldLabel: '结束时间',
           anchor: '91%'
	};
   	
   	//最下端  备注
    var remark = {
        xtype: 'textarea',
        name: 'remark',
        id : 'edit_remark',
        fieldLabel: '备注',
        allowBlank: true,
        anchor: '96%'}
	
	//定义显示数据变量===========================================
	
	
	
	
	
	editWindow = new Ext.Window({
		layout: 'fit',
		title: '编辑用户',
		width: 640,
		height: 500,
		border: false,
		modal : true,
		buttonAlign: 'center',
		items: [{
		    xtype: 'form',
		    frame: true,
		    bodyStyle: 'padding:5px',
		    border: false,
		    method: 'POST',
		    url: pathUrl + '/user/editUser'+oldParam,
		    id: 'editFormPanel',
		    reader: new Ext.data.JsonReader({
					root: 'results'
				}, [
					{name:'user_id'},
					{name:'password'},
					{name:'user_name'},
					{name:'owner_org_id'},
					{name:'bank_org_id'},
					{name:'job_seq_code'},
					{name:'job_title_code'},
					{name:'post_type_code'},
					{name:'technical_title_code'},
					{name:'busi_line_id'},
					{name:'join_date'},
					{name:'employee_id'},
					{name:'leave_date'},
					{name:'id_card'},
					{name:'born_date'},
					{name:'gender_code'},
					{name:'nation_code'},
					{name:'education_code'},
					{name:'telephone'},
					{name:'address'},
					{name:'email'},
					{name:'postal'},
					{name:'begin_date'},
					{name:'end_date'},
					{name:'status_code'},
					{name:'remark'}
				]
	        ),
		    layout: 'column',
		    items: [{
		            xtype: 'panel',
		            border: false,
		            layout: 'form',
		            labelWidth: 70,
		            labelAlign: 'left',
		            columnWidth: 0.5,
		            items: [
							   userIdObj,				//用户id
							   userName,			//用户名
							   employeeId,			//员工号
							   ownerOrgSelector,	//所属机构 
							   busiLineSelector ,	//条线
							   gender,				//性别
							   ethnicity,			//民族
							   eduBack,				//学历
							   bornDate,			//出生日期
							   userMobile,			//手机号
							   userPost,			//邮编
							   beginDate			//开始时间
		                    ]
		        },{
		            xtype: 'panel',
		            border: false,
		            layout: 'form',
		            labelWidth: 70,
		            labelAlign: 'left',
		            columnWidth: 0.5,
		            items: [
   		                    password,			//密码
   		                    certId,				//身份证
   		                    jobSeqCode,			// 序列
   		                    manageOrgSelector,	//权限机构
   		                    jobTypeCodeSelector,//职位类型
   		                    postType,			//岗位
   		                    joinDate,			//入行日期
   		                    leaveDate,			//离行日期
   		                    statusSelector ,	//状态
   		                    userAddress,		//地址
   		                    userEmail,			//邮箱
   		                    endDate				//结束日期
   	                	]
	        },{
		            xtype: 'panel',
   		            border: false,
   		            layout: 'form',
   		            labelWidth: 70,
   		            labelAlign: 'left',
   		            columnWidth: 1,
   		            items: [ 
   		                     remark
   		                    ]
	   	        }]
		}],
		buttons: [{
			text: '确定',
			handler: function(){
				var formPanel = Ext.getCmp("editFormPanel");
				if(formPanel.form.isValid()){
					formPanel.form.submit({      
			            waitMsg:'正在处理,请稍候......',         
			            timeout:30000,
			            failure: function(form, action) {
						    Ext.MessageBox.alert('错误', action.result.info);
						},
						success: function(form, action) {
							Ext.MessageBox.alert('提示', action.result.info);
						    editWindow.destroy();
							editWindow = null;
							store.reload();
						}
			        });					
				}else{
					Ext.Msg.alert("提示信息","请输入必填项");
				}
			}
		},{
			text: '取消',
			handler: function(){
				editWindow.destroy();
				editWindow = null;
			}
		}]
	});
	var p = Ext.getCmp("editFormPanel");
	p.form.load({url: pathUrl + '/user/getUserById',params:{user_id:userId}});
	editWindow.on("close",function(){
		editWindow.destroy();
		editWindow = null;
	});
	editWindow.show();
	ownerOrgSelector.initUI();
	manageOrgSelector.initUI();	
}

function deleteUser(userID,store){
	if(editWindow != null || addWindow != null)
		return;
	Ext.Ajax.request({
		url: pathUrl + '/user/deleteUser',
		params: {user_id:userID},
		method: 'POST',
		callback: function (options, success, response) {
			var json=Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				store.reload();
			} else {
				Ext.MessageBox.alert('错误',json.info);
			}
		}
	});
		
}

var rolesSelectionModel = new Ext.grid.CheckboxSelectionModel({
	handleMouseDown : Ext.emptyFn,
	renderer:function(v,c,r){
		 return  '<div class="x-grid3-row-checker"></div>';// 显示checkbox
        /*if(r.get("bank_org_id") != bank_org_id){
            return " ";// 不显示checkbox
        }else{
           return  '<div class="x-grid3-row-checker"></div>';// 显示checkbox
        }*/
    }
});
	
var roleInfo_ds=new Ext.data.JsonStore({
			url : pathUrl + '/user/getUserRole',
			root : 'results',
			fields : ['role_id','role_name', 'checked']
		});
var roleInfo_cm=[rolesSelectionModel,
    {header:'角色ID',dataIndex:'role_id',hidden:true},
    {header:'角色名称',dataIndex:'role_name'}
];

var user_id;
var user_name;
function addUserRole(){
	var record=Ext.getCmp("gridPanel").getSelectionModel().getSelected();
	if(record==null){
			Ext.MessageBox.alert('提示信息', '请选择一个用户！');
			return ;
	}
	user_id=record.get('user_id');
	user_name=record.get('user_name');
	var rolewin=new UserRolesWindowUI();
	rolewin.setTitle('用户角色分配—正在为 <span style="color:red;"> '+user_name+' </span>分配角色');
	rolewin.on('close',function(){rolewin.destroy();});
	rolewin.show();
	roleInfo_ds.load({
		params : {
            user_id:user_id
		},
		callback : function(r,options,success){
			if(success){
				doSelect(r);
			}
		}
	});
}

// 选中已经分配的角色
function doSelect(records) {
		for (var i = 0; i < records.length; i++) {
			if(records[i].get('checked') == '1'){
				var index =roleInfo_ds.indexOf(records[i]); 
				Ext.getCmp('selectGridPanel').getSelectionModel().selectRow(index,true);
			}
		}
}

UserRolesWindowUI = Ext.extend(Ext.Window, {
    height: 380,
    width: 400,
    id:'userRolesWin',
    title: '用户角色分配',
    buttonAlign:'center',
    modal : true,
    initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'grid',
                    height: 367,
                    viewConfig : {
                         forceFit : true
                    },
                    id:'selectGridPanel',
                    sm:rolesSelectionModel,
                    columns:roleInfo_cm,
                    store:roleInfo_ds
                }
            ],
            buttons: [{
			text: '保存',
			handler: function(){
				var selections=Ext.getCmp('selectGridPanel').getSelectionModel().getSelections();
				var roleIds='';
				for(var i=0;i<selections.length;i++){
//					if(selections[i].get('bank_org_id') == bank_org_id){
						roleIds +=','+selections[i].get('role_id');
//					}
				}
				if(roleIds !=''){
					roleIds=roleIds.substring(1);
				}
				Ext.Ajax.request({
					url : pathUrl + '/user/saveUserRole',
					waitMsg:'正在处理,请稍候......',         
					timeout:30000,
					params: {role_id:roleIds,user_id:user_id},
					method: 'POST',
					callback: function (options, success, response) {
						var json=Ext.util.JSON.decode(response.responseText);
						if (json.success) {
							Ext.MessageBox.alert("提示信息","分配成功");
							Ext.getCmp("userRolesWin").destroy();
						} else {
							Ext.MessageBox.alert('错误',json.info);
						}
					}
				});
			}
            },
            {
			text: '取消',
			handler: function(){
				Ext.getCmp("userRolesWin").destroy();
			}
            }
            ]
        });

        UserRolesWindowUI.superclass.initComponent.call(this);
    }
});

// ********************************特殊授权***********************/

// 机构变化选择重新加载菜单树
var myComboChangeFun = function(bank_id){
	setResTree(special_user_id,bank_id);
}

// 权限机构panel
var formPanel = new Ext.form.FormPanel({
	frame : true,
	region : 'north',
	split : true,
	height : 50,
	bodyStyle : 'padding : 5px',
	layout: 'column',
	buttonAlign: 'center',
	items : [{
		xtype : 'panel',
		border : false,
		columnWidth : .4,
		labelWidth : 40,
		bodyStyle : 'padding : 0px 0px 0px 5px',
		layout : 'form',
		items : [bankSelector = new MyCombo(myComboChangeFun)]
	}]
});

// 菜单树
var resTreePanel = new Ext.Panel({
	id : 'card-1',
	region : 'center',
	split : true,
	title : '可选功能页面',
	autoScroll :true,
	layout : 'fit',
	items : [{
		xtype : 'panel',
		autoScroll :true,
		contentEl : 'res_tree',
		border : true,
		split : false
	}]
});

// 弹出窗口
var specialWindow = new Ext.Window({
	title : '',
	id : 'addWindow',
	width : 640,
	height : 500,
	layout : 'border',
	plain : true,
	modal : true,
	bodyStyle : 'padding:10px;',
	buttonAlign : 'center',
	listeners : {
		beforehide: function(){
			is_expand = false;
		},
		beforeclose : function(){
			specialWindow.hide();
			return false;
		}
	},
	items : [formPanel,resTreePanel],
	buttons : [{
		text : '保存',
		handler : function() {
			var bank_id = Ext.getCmp('myCombo').getStore().getAt(0).get('bank_org_id');
			var resource = "";
			var allNode = tree.getAllChecked();
			var nodes = allNode.split(",");
			// =======获得所有选择的功能菜单，包括处于半选状态的父节点菜单======Start==
			for(var i=0;i<nodes.length;i++){
				if(isExistNode(resource,nodes[i])){
					continue;
				}else{
					resource += nodes[i] + ";";
				}

				var Presource = getCheckResource("",nodes[i]).split(";");
				for(var j=0;j<Presource.length;j++){
					if(isExistNode(resource,Presource[j])){
						continue;
					}else{
						resource += Presource[j] + ";";
					}
				}
			}
			// =======获得所有选择的功能菜单，包括处于半选状态的父节点菜单=====End===
			// =======保存特殊授权=====Start===
			Ext.Ajax.request({
				url : pathUrl + '/user/saveSpeciallyAuthorize?user_id='+special_user_id+'&bank_id='+bank_id+'&resource='+resource,
				method : 'POST',
				timeout : 30000,
				callback : function(options, success, response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if (json.success){
						Ext.MessageBox.alert('提示信息', "保存成功!");
						specialWindow.hide();
					}else{
						Ext.MessageBox.alert('提示信息', "保存失败!");
					}
				}
			});
		}
	}, {
		text : '取消',
		handler : function() {
			specialWindow.hide();
		}
	}]
});

// 得到所有选择的菜单节点；如果选择的节点存在除root外的父节点则将其父节点一起保存
var getCheckResource = function(resource,id){
	var pId = tree.getParentId(id);
	if("root" != pId){
		resource += pId + ";";
		return getCheckResource(resource,pId);
	}else{
		return resource;
	}
}
// 判断id是否已经存在于resource中
var isExistNode = function(resource,id){
	var rs = resource.split(";");
	for(var i=0;i<rs.length;i++){
		if(id == rs[i]){
			return true;
		}
	}
	return false;
}

// 加载菜单树
var setResTree = function(user_id,bank_id){
	Ext.Ajax.request({
		url : pathUrl + '/user/getResourceTree?user_id='+user_id+'&bank_id='+bank_id,
		method : 'POST',
		timeout : 30000,
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success){
				$("#res_tree").empty();
				tree=new dhtmlXTreeObject("res_tree","100%","100%","");
				tree.setImagePath(pathUrl + "/public/scripts/dhtmlx/imgs/");
				tree.enableCheckBoxes(1);
				tree.attachEvent("onCheck",function(id,state){
					if(tree.hasChildren(id) > 0){
						checkChildren(tree,id,state)
					}
					checkParent(tree,id,state);
				});
				tree.loadXMLString(json.info);
				tree.disableCheckbox('root',true);
			}else{
				Ext.MessageBox.alert('提示信息', "加载用户菜单树失败!");
			}
		}
	});
}

// 如果选择节点为子节点则将父节点变成半选中状态
/**
 * 递归调用 选择的节点为子节点，分两种情况： 1、反选。判断该节点的直接父节点下所有孩子节点是否存在选中状态节点，
 * 如果存在则只修改父节点为半选样式（不改变父节点的选择状态）， 如果不存在则反选父节点 2、选中。判断该节点的直接父节点下所有孩子节点是否存在反选状态节点，
 * 如果存在则判断父节点是否已经处于选中状态，处于选中状态则不做修改，否则将父节点改成半选样式 如果不存在则将父节点置选中状态
 */
var checkParent = function(tree,id,state){
	var pId = tree.getParentId(id);
	var flag = true;
	if("root" != pId){
		if(state == 0){	// 反选
			for(var i=0;i<tree.hasChildren(pId);i++){
				if(1 == tree.isItemChecked(tree.getChildItemIdByIndex(pId,i))){
					var sNode = tree._globalIdStorageFind(pId, 0, 1);
					sNode.htmlNode.childNodes[0].childNodes[0].childNodes[1].childNodes[0].src = tree.imPath + 'iconInterCheck.gif';
					flag = false;
					break;					
				}
			}
			flag ? tree.setCheck(pId,state) : '';
		}else{// 选择
			for(var i=0;i<tree.hasChildren(pId);i++){
				if(0 == tree.isItemChecked(tree.getChildItemIdByIndex(pId,i))){
					if(0 == tree.isItemChecked(pId)){// 节点未选中则置半选状态，否则不变
						var sNode = tree._globalIdStorageFind(pId, 0, 1);
						sNode.htmlNode.childNodes[0].childNodes[0].childNodes[1].childNodes[0].src = tree.imPath + 'iconInterCheck.gif';
					}
					flag = false;
					break;					
				}
			}
			flag ? tree.setCheck(pId,state) : '';
		}
		checkParent(tree,pId,state);
	}else{
		return ;
	}
} 

// 如果选择节点为父节点则选择下面所有子节点
var checkChildren = function(tree,id,state){
	if(tree.hasChildren(id) > 0){
		for(var i=0;i<tree.hasChildren(id);i++){
				tree.setCheck(tree.getChildItemIdByIndex(id,i),state);
				checkChildren(tree,tree.getChildItemIdByIndex(id,i),state);
		}
	}else{
		return ;
	}
}
	
// 特殊授权
function speciallyAuthorize(){
	var record=Ext.getCmp("gridPanel").getSelectionModel().getSelected();
	if(record==null){
			Ext.MessageBox.alert('提示信息', '请选择一个用户！');
			return ;
	}
	special_user_id=record.get('user_id');
	special_user_name=record.get('user_name');
	
	// 弹出框之前机构定位总是当前用户机构
	var comboStore = Ext.getCmp('myCombo').getStore();
	comboStore.removeAll();
	comboStore.insert(0,new Ext.data.Record({bank_org_id:bank_org_id,bank_org_name:bank_org_name}));
	Ext.getCmp('myCombo').setValue("["+bank_org_id+"]"+bank_org_name);
	// 加载特殊授权的菜单树
	setResTree(special_user_id,bank_org_id);
	
	specialWindow.show();
	specialWindow.setTitle('为 <span style="color:red;"> '+special_user_name+' </span>分配特殊授权');
}
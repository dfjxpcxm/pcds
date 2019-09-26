var dataForm;
var queryForm;
var windows;
var dataGrid;
var dataStore;
var dataCm;
var url;
var records;
var record;
var templet_id;
var storeArray = [];

/**
 * 客户经理分配面板
 */
Ext.MyField=Ext.extend(Ext.form.TextField ,{
	xtype:"textfield",
	readOnly:true,
	id:Ext.id(),
	constructor: function(config) {
		config = config||{};
		this.rxcfg = {};
		this.rxcfg.id = config.id;
		this.rxcfg.name = config.name;
		this.rxcfg.realName = config.realName;
		this.rxcfg.showIcon = config.showIcon;
		this.rxcfg.anchor = config.anchor||'83%';
		this.rxcfg.readOnly = config.readOnly||true;
		this.rxcfg.hidden = config.hidden||false;
		this.rxcfg.fieldLabel = config.fieldLabel||'客户经理';
		this.rxcfg.allowBlank= config.allowBlank || true;
		Ext.MyField.superclass.constructor.call(this, config);
    },
	initComponent: function(){
		//var p = this;
		Ext.applyIf(this, {
			id : this.rxcfg.id,
			anchor:this.rxcfg.anchor,
			fieldLabel:this.rxcfg.fieldLabel,
			name:this.rxcfg.name,
			realName:this.rxcfg.realName,
			readOnly : this.rxcfg.readOnly,
			hidden : this.rxcfg.hidden,
			allowBlank : this.rxcfg.allowBlank,
			listeners:{
				afterrender : function(c){
					if(this.rxcfg.showIcon){
						var imgPath = pathUrl+'/public/images/icons/change.png'
						var div = Ext.getDom(c.getId()).parentNode;
						var span = document.createElement("span");
						span.style.border = "1px solid #B5B8C8";
						span.style.padding = "1px 1px 1px 1px";
						span.style.margin = "0px 0px 0px 2px";
						span.style.verticalAlign = "MIDDLE";
						span.innerHTML = "<a href='javascript:Ext.getCmp(\""+c.getId()+"\").showMgr()'><img src=\""+imgPath+"\"></a>";
						div.appendChild(span);
					}
				} 
			}
		});
		Ext.MyField.superclass.initComponent.call(this);
	} ,
	showMgr :function(){
		showAccountAllotWin(this); 
	}
})
 
//执行查询
function doQuery(tmpl_id,isCurrent){
	Ext.getCmp('dataGrid'+tmpl_id).getStore().load({ 
		params:{
			start			: 0,
			limit			: 30,
			isQuery         : 'Y'
		}
	});
}

/**
	编辑数据方法
*/
function doSaveData(templateId){
	 if (dataForm.form.isValid()) {
		  dataForm.form.submit({
	            waitMsg:'正在处理，请稍候。。。。。。',
	            params : {},
	            failure: function(form, action) {
	            	var errs = action.result.errors;
	            	if(errs){
	            		if(errs[0].id==''){
	            			Ext.Msg.alert('错误',errs[0].msg);
	            		}else{
	            			//弹出错误
	            			showErrors(action.result.errors);
	            		}
	            	}
				},
				success: function(form, action) {
				    Ext.Msg.alert('提示', '数据提交完成！');
				    windows.destroy();
					Ext.getCmp('dataGrid'+templateId).getStore().reload();
				}
	        });
         } else{
			Ext.MessageBox.alert('错误', '请填写必输项！');
		}   
}
/******************* 校验异常信息显示-start*********************************/
var expander = new Ext.grid.RowExpander({
	tpl : new Ext.Template('<br><p><b>错误消息:</b> {messages}</p><br>')
});

var errorWin ;
var store = new Ext.data.ArrayStore({
    fields: ['message_id', 'messages' ]
});
//为了提高效率，目前错误显示方式不混合,即showgrid值相同
function showErrors(errors){
	var myData = [];
	for(var i=0;i<errors.length;i++){
		if(errors[i].showgrid){
			myData.push([errors[i].id,errors[i].msg]);
		}else{
			return ;
		}
	}
	if(myData.length>0){
		showErrorWin(myData);
	}
}
//弹出错误面板
function showErrorWin(myData){
	//清楚store
	store.removeAll();
	store.loadData(myData);
	
	if(!errorWin){
		errorWin = new Ext.Window({
             layout:'fit',
             title: '校验错误信息列表',
             width:600,
             height:400,
             modal:true,
             border:false,
             closeAction:'hide',
             plain: true,
             items:[new Ext.grid.GridPanel({
                 store: store,
                 border:false,
                 plugins : expander,
                 flex : 1,
 				 viewConfig : {
 					forceFit : true
 				 },
 				sm : new Ext.grid.RowSelectionModel({
					singleSelect : true
				}),
                 columns : [
				    expander,
				    {
				        header: "校验错误消息",
				        width:100,
				        dataIndex: 'message_id'
				    } 
				 ]
 				 //,autoExpandColumn: 'messages' 
             })] ,
             buttons: [{
                 text: '关闭',
                 handler: function(){
                	 errorWin.hide();
                 }
             }]
         });
     }
	 errorWin.show();
}
/******************* 校验异常信息显示-end************************************/

//删除数据方法
function doDelData(business_no,templ_id){
//    var business_nos='';
//	if(records == null || records == '' || records.length <= 0){
//		Ext.Msg.alert('提示','请选择一行数据！');
//		return;
//	}
//	for(var i=0;i<records.length;i++){
//        var record=records[i];
//        var business_no = record.get("business_no");//业务编号
//     
//        var status_code = record.get("status_code");//数据状态
//        if(status_code != '01' && status_code != '03' && status_code != '10'){
//        	Ext.Msg.alert('错误','选中的有未完成审批的数据！');
//        	return false;
//        }
//
//        if(records.length-1 == i){
//        	business_nos  += business_no;
//        }else{
//        	business_nos  += business_no+ '_';
//        }
//    }
	Ext.MessageBox.confirm('确认','确认删除选中的数据吗?', 
		function(btn) {
			if(btn == 'yes')
			{
				Ext.Ajax.request({
					url: pathUrl+'/bckTrackAjax/executeMetaData/del', 
					method: 'POST',
					params: {key: business_no,_templateId:templ_id},
					callback: function (options, success, response) {
						var json=Ext.util.JSON.decode(response.responseText);
						if (json.success) { 
							Ext.Msg.alert('提示','删除成功');
							 Ext.getCmp('dataGrid'+templ_id).getStore().reload();
							 for ( var int = 0; int < storeArray.length; int++) {
								 Ext.getCmp('dataGrid'+storeArray[int]).getStore().reload();
							}
						} else {
							Ext.Msg.alert('错误',json.info);
						}
					}
				});
			}
		}
	);
}

//导入方法
function doImportData(templateId,metadata_id,button_id,relaValues){
	var impWindow = new Ext.Window({
		title: '模板导入',
		width: 450,
		height:200,
		modal:true,
		minWidth: 450,
		minHeight: 200,
		layout:'form',
		bodyStyle:'padding:10px 10px 10px 10px;',
		items : [{
			xtype:'panel',
			layout:'form',
			height:30,
			labelWidth: 100,  
			items:[{
				xtype : 'radiogroup',
				fieldLabel : '导入方式',
				id : 'imp_type',
				focusClass :'',
				items : [
				         {boxLabel : '覆盖导入',name : 'imp_type',inputValue : '0'},
				         {boxLabel : '增量导入',name : 'imp_type',inputValue : '1', checked : true}
				         ]
			}]
		},
		formUpload = new Ext.form.FormPanel({
			layout:'table',
			baseCls: 'x-plain',  
			labelWidth: 40,  
			id:'uploadWinForm',
			fileUpload:true
		})],
		buttonAlign:'center',
		buttons : [{xtype:'button',text:'导入',handler:function(){
			var filePath = document.getElementById('uploadFile').value;
			if(!filePath){
				Ext.Msg.alert('提示信息','请点击【浏览...】,选择导入文件。');
				return ;
			}
			
			var fileName = filePath.substr(filePath.lastIndexOf('.'));
			if(fileName != '.xls' && fileName != '.csv'){//&& fileName != '.xlsx'
				Ext.Msg.alert('提示信息','请选择以下类型的文件  [ .xls ,.csv ] 导入。');
				return ;
			}
			
			var imp_type = Ext.getCmp('imp_type').getValue().getRawValue();
			Ext.Ajax.request({  
				url: pathUrl+'/pageManager/importTempFile?imp_type='+imp_type+'&templateId='+templateId+'&metadata_id='+metadata_id+'&button_id='+button_id+'&relaValues='+relaValues,//上传文件
				waitMsg:'正在处理，请稍候。。。。。。',
				isUpload : true,  
				form :'upForm',
				success:function(response){
					var json = Ext.util.JSON.decode(response.responseText);
					var errs = json.errors;
					if(errs){
						if(errs[0].id==''){
							Ext.Msg.alert('错误',errs[0].msg);
						}else{
							//弹出错误
							showErrors(errs);
						}
					}else{
						Ext.Msg.alert('提示',json.info);
					}
					impWindow.destroy(); 
				}
			});
		}},{xtype:'button',text:'取消',handler : function(){
			impWindow.destroy();
		}}]
	});
	impWindow.show();
	var uploadWinForm = Ext.getDom('uploadWinForm');
	uploadWinForm.innerHTML='<form id="upForm"> <font class="x-form-field">请选择文件:<font><input type="file" size="40" style="height : 25px;" id="uploadFile" name="uploadFile"></form>';
}

/**
 * 导出模版方法
 * @param tmpl_id
 * @param metadata_id
 * @param button_id
 */
function doExportData(tmpl_id,metadata_id,button_id){
	if(!metadata_id){
		Ext.Msg.alert('提示','未配置关联Excel模板！！！');
		return;
	}
	var form = document.getElementById("expForm");
	form.action = pathUrl+'/pageManager/exportTempFile/'+tmpl_id+'/'+metadata_id+'/'+button_id;
	form.submit();
}

/**
 * 导出数据
 * @param tmpl_id
 */
function doExport(tmpl_id){
	var pars = _queryForm.getForm().getValues(true);
	var form = document.getElementById("expForm");
	document.getElementById("baseparams").value = pars;
	document.getElementById("hasApprove").value = hasApprove;
	form.action = pathUrl+'/pageManager/exportDataFile/'+tmpl_id;
	form.submit();
}

/**
*批量提交申请数据
*/ 
function submitApprove(){
    var business_nos='';
    var task_ids = "";
    var tmpl_id = "";
    if(records==null||records.length <= 0){
    	Ext.Msg.alert('提示','未选择数据');
        	return false;
    }
	for(var i=0;i<records.length;i++){
        var record=records[i];
        var business_no = record.get("business_no");//业务编号
        var status_code = record.get("status_code");//数据状态
         var task_id = record.get("task_id");//数据状态
         tmpl_id = record.get('tmpl_id');
        if(status_code != '01' && status_code != '03' && status_code != '10'){
        	Ext.Msg.alert('错误','选中的有未完成审批的数据！');
        	return false;
        }
        if(records.length-1 == i){
        	business_nos  += business_no;
        	task_ids += task_id;
        }else{
        	business_nos  += business_no+ '_';
        	task_ids += task_id+'_';
        }
    }
	Ext.MessageBox.confirm('提示','确认提交选中的数据?',function(btn){
		if(btn == 'yes'){
			Ext.Ajax.request({
				url : pathUrl + '/pageManager/submitApplyFlow',
				method : 'POST',
				params : { business_nos : business_nos,task_ids:task_ids,flow_status_code : status_code,applyType:'01',tmpl_id:tmpl_id },
				callback : function(options, success, response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if (success) { 
						Ext.MessageBox.alert('提示',json.info);
						dataStore.reload();
					} else {
						Ext.MessageBox.alert(json.info);
					}
				}
			})
		}
	});
}

/**
*用户主动撤回
*
*/
function backApprove(records){
    var business_nos='';
    var task_ids = "";
    var tmpl_id= "";
    if(records==null||records.length <= 0){
    	Ext.Msg.alert('提示','未选择数据');
        	return false;
    }
	for(var i=0;i<records.length;i++){
        var record=records[i];
        var business_no = record.get("business_no");//业务编号
        var status_code = record.get("status_code");//数据状态
        var task_id = record.get("task_id");//数据状态
        tmpl_id = record.get('tmpl_id');
        if(status_code != '02'){
        	Ext.Msg.alert('错误','当前状态的数据不允许撤回！');
        	return false;
        }
        if(records.length-1 == i){
        	business_nos  += business_no;
        	task_ids += task_id;
        }else{
        	business_nos  += business_no+ '_';
        	task_ids += task_id+'_';
        }
    }
	Ext.MessageBox.confirm('提示','确认提交选中的数据?',function(btn){
		if(btn == 'yes'){
			Ext.Ajax.request({
				url : pathUrl + '/pageManager/submitApplyFlow',
				method : 'POST',
				params : { business_nos : business_nos,task_ids:task_ids,flow_status_code : status_code,applyType:'02',tmpl_id:tmpl_id },
				callback : function(options, success, response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if (success) { 
						Ext.Msg.alert('提示',json.info);
						dataStore.reload();
					} else {
						Ext.MessageBox.alert(json.info);
					}
				}
			})
		}
	});
}


/**
*审批方法
isAgree  0: 同意  1:退回
*/ 
function approveFlow(approve_role,isAgree,recs,tmpl_id){
    var business_nos='';
    var task_ids = "";
	for(var i=0;i<recs.length;i++){
        var record=recs[i];
        var business_no = record.get("business_no");//业务编号
        var status_code = record.get("status_code");//数据状态
        var task_id = record.get("task_id");//数据状态
        if(records.length-1 == i){
        	business_nos  += business_no;
        	task_ids += task_id;
        }else{
        	business_nos  += business_no+ '_';
        	task_ids += task_id+'_';
        }
    }
    var status_code = '10';
    var applyType;
    var remark='退回';
    if(approve_role == '02' && isAgree == '0'){  //一级审核同意
    	status_code = '04';
    	applyType = '03';
    	remark='复核通过';
    }else if(approve_role == '02' && isAgree == '1'){
    	applyType = '03';
    	 
    }
    
    if(approve_role == '05' && isAgree == '0'){
	    	status_code = '11';
	    	applyType = '04';
	    	remark='终审通过';
	    }else if(approve_role == '05' && isAgree == '1'){
	    	applyType = '04';
	    }
    
    
    
	Ext.MessageBox.confirm('提示','确认提交选中的数据?',function(btn){
		if(btn == 'yes'){
			Ext.Ajax.request({
				url : pathUrl + '/pageManager/submitApplyFlow',
				method : 'POST',
				params : { business_nos : business_nos,status_code : status_code,task_ids:task_ids,remark:remark,applyType:applyType,tmpl_id:tmpl_id},   //status_code  =  04  or  10
				callback : function(options, success, response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if (success) { 
						Ext.MessageBox.alert('提示',json.info);
						dataStore.reload();
					} else {
						Ext.MessageBox.alert(json.info);
					}
				}
			})
		}
	});
}


/**
 * 机构树下拉框
 */
var TreeCombo=function(config){
	//var width = config.width || 260;   //宽
	//var height = config.height || 240;//高
	var anchor = config.anchor || '90%';//占比
	var name = config.name ||'bank_org';//控件名称
	var is_expand = config.is_expand || false;//是否展开
	var fieldLabel =  config.label;     //标签名称
	var value = config.value;    //控件默认值
	//默认根节点
	var rootId = config.rootId;   //控件根节点
	var rootName = config.rootName ;    //控件根节点名称
	//是否隐藏显示
	var hidden = config.hidden || false;
	//隐藏ID
	var hiddenValue =config.rootId; 
	//隐藏维度条件
	var dim_code = config.dim_code;  //

	TreeCombo.superclass.constructor.call(this,{
		autoSelect:true,
		name : name,
		valueField : 'nodeid',
		displayField : 'nodename',
		uxType:'treeCombo',
		mode: 'local',
		triggerAction : "all",
		editable: false,
		anchor:anchor,
		hiddenName : name,
		realName:config.realName,
		allowBlank:config.allowBlank,
		value : value,
		fieldLabel : fieldLabel,
		store : {
			xtype:'arraystore',
			fields : ['nodeid','nodename'],
		    data:[['','']]
		}
	});
	//添加隐藏属性，
	this.getHiddenValue = function(){
		return hiddenValue;
	}
	//添加隐藏属性，
	this.setHiddenValue = function(v){
		return hiddenValue = v;
	}
	this.getDimCode = function(){
		return dim_code;
	}
	//展开事件
	this.expand=function(){
		var combo = this;
		if(!is_expand){
	        this.menu = new Ext.menu.Menu({
	            items : [
	            treePanel_ = new Ext.tree.TreePanel({
						border: false,
						lines:false,
						width: 260,
	                	height: 240,
						bodyStyle:'padding:2px',
						autoScroll:true,
						rootVisible: true,
	                	root :getRootNode(rootId, rootName,dim_code, expandTree),
	                	listeners: {
	                    click: function(node){
	                    	//设置选中值
	                    	combo.getStore().removeAll();
	                    	combo.getStore().insert(0,new Ext.data.Record({nodeid:node.id,nodename:node.text}));
	                    	combo.setValue(node.id);
	                    	//设置隐藏值
	                    	hiddenValue = node.id;
							//关闭面板
							this.ownerCt.hide();
							//毁掉函数
							if(null != config.callback){
								config.callback(node.id);
							}
	                    },
	                    load : function(node){
	                    	is_expand = true;
	                    }
	                }
					})]
	        });
	        this.menu.show(this.el, "tl-bl?");
	        treePanel_.getRootNode().expand();
		}else{
			this.menu.show(this.el, "tl-bl?");
		}
    };
}
Ext.extend(TreeCombo,Ext.form.ComboBox);

//修改时候初始化下拉树
function reloadTreeCombo(){
	var treeCombos = dataForm.find('uxType', 'treeCombo');
	if(treeCombos){
		for ( var i  = 0; i  < treeCombos.length; i ++) {
		  setTreeComboValue(treeCombos[i]);
		}
	}
}
//加载某个下拉树
function setTreeComboValue(tComb){
  Ext.Ajax.request({
	   url : pathUrl + '/bckTrackAjax/getDimValue/'+tComb.getDimCode()+'/'+tComb.getValue(),
	   method: 'POST',
	   success: function(response, opts) {
	      var json=Ext.util.JSON.decode(response.responseText);
	      var arys =json.results;
	      if(arys && arys.length>0){
	    	  tComb.getStore().removeAll();
	    	  tComb.getStore().insert(0,new Ext.data.Record({nodeid:arys[0].nodeid,nodename:arys[0].nodename}));
	    	  tComb.setValue(arys[0].nodeid);
	      }
	   } 
	});
}

/**
 * 创建树节点
 * @param id
 * @param text
 * @param dim_code
 * @param fn
 * @returns {Ext.tree.AsyncTreeNode}
 */
function getRootNode(id, text,dim_code, fn) {
	var root = new Ext.tree.AsyncTreeNode({
		id : id,
		text : text,
		qtip: text,
		dim_code : dim_code,
		children : [{
			text : 'loading',
			cls : 'x-tree-node-loading',
			leaf : true
		}]
	});

	if (fn != undefined)
		root.on('expand', fn);
	return root;
}

/**
 * 机构树展开方法
 */
function expandTree(node){
	if(node.firstChild.text == 'loading'){
		Ext.Ajax.request({
			url : pathUrl + '/pageManager/getLinkData/'+node.attributes.dim_code,
			params : {
				nodeId : node.id,
				dimcode :node.attributes.dim_code,
				mode : 'DrillDown'
			},
			waitMsg : '正在处理,请稍候...',
			method : 'POST',
			callback : function(options,success,response){
				var json = Ext.util.JSON.decode(response.responseText);
				var tl = json.results;
				if(tl){
					for(var i=0;i<tl.length;i++){
						var cnode = new Ext.tree.AsyncTreeNode({
							id : tl[i].nodeid,
							text : '['+tl[i].nodeid+']'+tl[i].nodename,
							leaf : tl[i].leaf == 'Y'? true : false,
							dim_code : node.attributes.dim_code,
							children : [{
								text : 'loading',
								cls : 'x-tree-node-loading',
								leaf : true
							}]
						});
						cnode.on('expand',expandTree);
						node.appendChild(cnode);
					}
					//展开时为treePanel添加一个空选择项
					if(node.parentNode == null){
						node.appendChild(new Ext.tree.TreeNode({
			        		id : ' ',
			        		text : '--全部--',
			        		leaf : true
			        	})); 
					}
				}else{
					Ext.MessageBox.alert('错误',json.info);
				}
				node.firstChild.remove();
			},
			failure : function(response,options){
				Ext.MessageBox.alert('错误',response.responseText);
			},
			success : function(options,response){
			}
		});
	}
}

//流程状态下拉框
FlowStatusCombo = function(config){
	var anchor = config.anchor || '91%';
	var type = config.Etype || '01';
	var statueStore = null;
//	if(type == '01'){
//		statueStore = new Ext.data.SimpleStore({
//			fields : ['displayValue','displayText'],
//			data : [['','全部'],['01','编辑'],['02','待审'],['03','撤回'],['04','复核待审'],['10','退回'],['11','通过']]
//		});
//		FlowStatusCombo.superclass.constructor.call(this,{
//		store : statueStore,
//		valueField : 'displayValue',
//		displayField : 'displayText',
//		mode : 'local',
//		hiddenName : 'StatusCombo',
//		editable: false,
//		triggerAction: 'all',
//		allowBlank:false,
//		fieldLabel:'审批状态',
//		name: 'StatusCombo',
//		value: '',
//	    anchor:anchor
//	});
//		
//	}else if(type == '02'){
//		statueStore = new Ext.data.SimpleStore({
//			fields : ['displayValue','displayText'],
//			data : [['','全部'],['02','待审'],['04','复核待审'],['10','退回'],['11','通过']]
//		});
//		FlowStatusCombo.superclass.constructor.call(this,{
//		store : statueStore,
//		valueField : 'displayValue',
//		displayField : 'displayText',
//		mode : 'local',
//		hiddenName : 'StatusCombo',
//		editable: false,
//		triggerAction: 'all',
//		allowBlank:false,
//		fieldLabel:'审批状态',
//		name: 'StatusCombo',
//		value: '',
//	    anchor:anchor
//	});
//	}else{
		statueStore = new Ext.data.SimpleStore({
			fields : ['displayValue','displayText'],
			data : [['','全部'],['null','新建'],['00','编辑'],['01','待审'],['02','完成']]
		});
		FlowStatusCombo.superclass.constructor.call(this,{
		store : statueStore,
		valueField : 'displayValue',
		displayField : 'displayText',
		mode : 'local',
		hiddenName : 'StatusCombo',
		editable: false,
		triggerAction: 'all',
		allowBlank:false,
		fieldLabel:'审批状态',
		name: 'StatusCombo',
		value: '',
	    anchor:anchor
	});
//	}
	
}
Ext.extend(FlowStatusCombo,Ext.form.ComboBox);

/**
 * 金额格式化
 * @param {} s
 * @return {}
 */
function fmoney(s){
	if(!s){
		s='0';
	}
	//n = n>0 && n<=20 ? n : 2;
	s = parseFloat((s+"").replace(/[^\d\.-]/g,"")).toFixed(2)+"";
	var l = s.split(".")[0].split("").reverse();
	r = s.split(".")[1];
	t = "";
	for(i=0;i<l.length;i++){
		t += l[i] + ((i+1) % 3 == 0 && (i+1) != l.length ? "," : "");
	}
	return t.split("").reverse().join("")+"."+r;
}

function rmoney(s){
	if(!s){
		return 0;
	}
	return parseFloat(s.replace(/[^\d\.-]/g,""));
}

/**
 * 审批状态
 * @param {} v
 * @return {String}
 */
function approvetype(v,metadata,r){
	switch(v)
    {
    case null:
    	return "<span style='color:blue;'>新建</span>";
    case '-1':
    	return "<span style='color:blue;'>新建</span>";
    case '00':
    	return "<span style='color:blue;'>编辑</span>";
    case '01':
    	var nodeVal = getNodeName(r.get('next_node_id'));
    	return "<span style='color:blue;'>待审"+nodeVal+"</span>";
    case '02':
    	return "<span style='color:green;'>完成</span>";
    case '03':
    	return "<span style='color:red;'>退回</span>";
    case '04':
    	return "<span style='color:green;'>撤回</span>";
    default:
    	return "<span style='color:blue;'>编辑</span>";
   }
}

/**
 * 数据状态 ,增加20状态，只在无流程的时候使用
 * 20 已校验
 * @param v
 * @param metadata
 * @param r
 * @returns {String}
 */
function datatype(v,metadata,r){
	switch(v)
    {
    case null:
    	return "<span style='color:blue;'>新建</span>";
    case '00':
    	return "<span style='color:blue;'>编辑</span>";
    case '05':
    	return "<span style='color:blue;'>审批中</span>";
    case '10':
    	return "<span style='color:green;'>完成</span>";
    case '20':
    	return "<span style='color:#be11e9;'>已校验</span>";
    default:
    	return "<span style='color:blue;'>编辑</span>";
   }
}

//当前谁审批节点
function getNodeName(v){
	if(approveNodeStr && v){
		var i = approveNodeStr.indexOf(v);
		if(i>-1){
			return "["+approveNodeStr.substring(approveNodeStr.indexOf("[",i),approveNodeStr.indexOf("]",i))+"]";
		}
	}
	return "";
}

//是否是子模版
function is_child(tmpl_id){
	var is_child = 'N';
	if(templateId != tmpl_id){
		is_child = 'Y';
	}
	return is_child;
}

function doSave(btn,tmpl_id,type,button_id){
	var dataForm  = btn.ownerCt.ownerCt.findByType('form')[0];
	 if (dataForm.form.isValid()) {
		  dataForm.form.submit({
			    url: pathUrl+"/pageManager/executeMetaData",
	            waitMsg:'正在处理，请稍候。。。。。。',
	            params : {tmpl_id:tmpl_id,type_000:type,button_id:button_id,execType:'add',is_child:is_child(tmpl_id)},
	            failure: function(form, action) {
	            	var errs = action.result.errors;
	            	if(errs){
	            		if(errs[0].id==''){
	            			Ext.Msg.alert('错误',errs[0].msg);
	            		}else{
	            			//弹出错误
	            			showErrors(action.result.errors);
	            		}
	            	}
				},
				success: function(form, action) {
				    Ext.Msg.alert('提示', '数据提交完成！');
				    btn.ownerCt.ownerCt.destroy();
					Ext.getCmp('dataGrid'+tmpl_id).getStore().reload();
				}
	        });
        } else{
			Ext.MessageBox.alert('错误', '请填写必输项！');
		}   
}

function doUpdate(btn,tmpl_id,button_id){
	var dataForm  = btn.ownerCt.ownerCt.findByType('form')[0];
	if (dataForm.form.isValid()) {
		dataForm.form.submit({
			url: pathUrl+"/pageManager/executeMetaData",
			waitMsg:'正在处理，请稍候。。。。。。',
            params : {tmpl_id:tmpl_id,button_id:button_id,execType:'upd',is_child:is_child(tmpl_id)},
			failure: function(form, action) {
				var errs = action.result.errors;
				if(errs){
					if(errs[0].id==''){
						Ext.Msg.alert('错误',errs[0].msg);
					}else{
						//弹出错误
						showErrors(action.result.errors);
					}
				}
			},
			success: function(form, action) {
				Ext.Msg.alert('提示', '数据提交完成！');
				btn.ownerCt.ownerCt.destroy();
				Ext.getCmp('dataGrid'+tmpl_id).getStore().reload();
			}
		});
	} else{
		Ext.MessageBox.alert('错误', '请填写必输项！');
	}   
}

function doLoad(win,r,tmpl_id,button_id){
	    dataForm  = win.findByType('form')[0];
	    dataForm.load({
    		params :{key:r.get('business_no'),tmpl_id:tmpl_id,button_id:button_id},
			url: pathUrl+"/pageManager/getMetaDataById"
		});
}

/**
 * 删除
 * @param r
 * @param tmpl_id
 */
function doDelete(key,taskIds,tmpl_id){
	Ext.Ajax.request({
		url: pathUrl+"/pageManager/executeMetaData",
		method: 'POST',
		params :{key:key,tmpl_id:tmpl_id,task_id:taskIds,execType:'del',is_child:is_child(tmpl_id)},
		callback : function(options,success,response){
			var json = Ext.util.JSON.decode(response.responseText);
			Ext.getCmp('dataGrid'+tmpl_id).getStore().reload();
			Ext.Msg.alert('错误',json.errors[0].msg);
		}
	});
}

/**
*批量提交申请数据
var hasFlow = (workflowId && workflowId !='null' && templateId == tmpl_id);
*/
function doValidData(r,tmpl_id){
	Ext.MessageBox.confirm('提示','确认要校验选中的数据吗?',function(btn){
		var key = "";
		for (var int = 0; int < r.length; int++) {
			if(int>0){
				key +="_";
			}
			key +=r[int].get('business_no');
		}
		
		if(btn == 'yes'){
			Ext.Ajax.request({
				url : pathUrl + '/pageManager/validData',
				method : 'POST',
				params : { key : key,tmpl_id : tmpl_id},
				callback : function(options, success, response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if (success) { 
						Ext.MessageBox.alert('提示',json.info);
						dataStore.reload();
					} else {
						Ext.MessageBox.alert(json.info);
					}
				}
			})
		}
	});
}

//刷新主模版数据
function reloadMainGrid(){
	if(Ext.getCmp('dataGrid'+templateId)){
		Ext.getCmp('dataGrid'+templateId ).getStore().reload();
	}
}
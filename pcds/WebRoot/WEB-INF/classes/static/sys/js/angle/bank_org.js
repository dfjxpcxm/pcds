bankOrgAddor = function(tree) {
    var id = tree.getSelectionModel().getSelectedNode().id;
    var formPanel = new Ext.form.FormPanel({
        baseCls: 'x-plain',
        labelWidth: 75,
        url:pathUrl+'/angleBankOrg/addBankOrg?moduleCode=0096',
        items: [{
			xtype : 'textfield' ,
			fieldLabel: '机构代码',
			allowBlank: false,
			name: 'bank_org_id',
			id: 'bank_org_id',
			anchor: '90%'
		},
		{
			xtype : 'textfield' ,
			fieldLabel: '机构名称',
			allowBlank: false,
			name: 'bank_org_name',
			id: 'bank_org_name',
			anchor: '90%'  
		},{
			xtype : 'textfield' ,
			fieldLabel: '父级机构号',
			allowBlank: false,
			name: 'parent_bank_org_id',
			id: 'parent_bank_org_id',
			anchor: '90%',
			readOnly: true,
			value: id
		},
		new BankOrgTypeSelector(),
		//new bankAreaSelector(),
		{
			xtype : 'textfield',
			fieldLabel : '机构地区',
			name : 'location',
			id : 'location',
			allowBlank : false,
			readOnly : true,
			anchor : '85%'
		}, {
			xtype : 'hidden',
			name : 'location_code',
			id : 'location_code'
		},
		{
			xtype:'combo',
			store: new Ext.data.SimpleStore({
				fields: ["retrunValue", "displayText"],
				data: [['1','1'],['2','2'],['3','3'],['4','4'],['5','5'],['6','6']]
			}),
			valueField :"retrunValue",
			displayField: "displayText",
			mode: 'local',
			forceSelection: true,
			editable: false,
			hiddenName:'bank_org_level',
			triggerAction: 'all',
			allowBlank:false,
			fieldLabel: '机构级别',
			name: 'bankOrgLevel',
			id: 'bank_org_leave',
			value: '2',
			anchor: '90%'  
		},
		{
			xtype:'textfield',
			fieldLabel: '显示顺序',
			name: 'display_order',
			id: 'display_order',
			anchor: '90%'
		}/*,
		{
			xtype:'hidden',
			name: 'bankMgrType',
			id: 'bankMgrType',
			value: bankTypeHide.getValue()
		}*/]
	});

    var window = new Ext.Window({
        title: '机构维护',
        width: 400,
        height:300,
        layout:'fit',
        modal : true,
        bodyStyle:'padding:10px;',
        buttonAlign:'center',
        items: formPanel,
        buttons: [{
            text: '保存', 
            handler: function() {
            	if(!checkInput()){
            		return;
            	}
                if (formPanel.form.isValid()) 
				{
	 		        formPanel.form.submit({      
			            waitMsg:'正在处理，请稍候。。。。。。',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('错误', action.result.info);
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('确认', '保存完毕！');
						    var nodeID=action.result.results.bank_org_id;
							var nodeName=action.result.results.bank_org_name;
							var node=new Ext.tree.AsyncTreeNode({
								id:nodeID,
								text:'['+nodeID+']'+nodeName ,
								leaf:true
							});
							//node.on('expand',getEvaBankOrgTreeNode);
							tree.getSelectionModel().getSelectedNode().appendChild(node);
							tree.getSelectionModel().getSelectedNode().expand();

						    window.destroy();
						}
			        });                   
                } else{
					Ext.MessageBox.alert('错误', '请填写必输项！');
				}             
	        }
        },{
            text: '取消',
            handler: function(){window.destroy();}
        }]
    });

    window.show();
    var offsetLeft = window.getPosition(true)[0];
    var offsetTop = window.getPosition(true)[1];
    
    var div = Ext.getDom('location').parentNode;
	var span = document.createElement("span");
	span.innerHTML = "<a id='edImg' href='javascript:showArea("+offsetLeft+","+offsetTop+");'><img src="+ pathUrl + "/static/sys/images/angle/edit.png></a>";
	div.appendChild(span);
};


bankOrgEditor = function(tree) {
	var selectModel = tree.getSelectionModel().getSelectedNode();
	if(!selectModel){
		Ext.Msg.alert('提示信息','请选择一个机构节点');
		return;
	}
    var id=tree.getSelectionModel().getSelectedNode().id;
    if(id == '8888'){
    	return;
    }
    var formPanel = new Ext.form.FormPanel({
        baseCls: 'x-plain',
        labelWidth: 75,
        url:pathUrl+'/angleBankOrg/editBankOrg?moduleCode=0097',
        
        reader: new Ext.data.JsonReader({
				root: 'results'
			}, [{name: 'bank_org_id'},
			{name: 'bank_org_name' },
			{name: 'parent_bank_org_id'},
			{name: 'bank_org_type_code'},
			{name: 'location_code' },
			{name: 'bank_org_level' },
			{name: 'display_order' }]
        ),
        items: [{
			xtype : 'textfield' ,
			fieldLabel: '机构代码',
			allowBlank: false,
			name: 'bank_org_id',
			id: 'bank_org_id',
			readOnly:true,
			anchor: '90%'
		},
		{
			xtype : 'textfield' ,
			fieldLabel: '机构名称',
			allowBlank: false,
			name: 'bank_org_name',
			id: 'bank_org_name',
			anchor: '90%'  
		},{
			xtype : 'textfield' ,
			fieldLabel: '父级机构号',
			allowBlank: false,
			name: 'parent_bank_org_id',
			id: 'parent_bank_org_id',
			readOnly: true,
			anchor: '90%'
		},
		new BankOrgTypeSelector(),
		//new bankAreaSelector(),
		{
			xtype : 'textfield',
			fieldLabel : '机构地区',
			name : 'location',
			id : 'location',
			allowBlank : false,
			readOnly : true,
			anchor : '85%'
		}, {
			xtype : 'hidden',
			name : 'location_code',
			id : 'location_code',
			listener:{
			  	change:function(){
			  		alert();
			  		initLocation();
			  	}
			}
		},
		{
			xtype:'combo',
			store: new Ext.data.SimpleStore({
				fields: ["retrunValue", "displayText"],
				data: [['1','1'],['2','2'],['3','3'],['4','4'],['5','5'],['6','6']]
			}),
			valueField :"retrunValue",
			displayField: "displayText",
			mode: 'local',
			forceSelection: true,
			editable: false,
			hiddenName:'bank_org_level',
			triggerAction: 'all',
			allowBlank:false,
			fieldLabel: '机构级别',
			name: 'level',
			id: 'level',
			anchor: '90%'  
		},
		{
			xtype:'textfield',
			fieldLabel: '显示顺序',
			name: 'display_order',
			id: 'display_order',
			anchor: '90%'
		},
		{
			xtype:'hidden',
			name: 'origBankOrgID',
			id: 'origBankOrgID',
			value: id
		}/*,
		{
			xtype:'hidden',
			name: 'bankMgrType',
			id: 'MgrType',
			value: bankTypeHide.getValue()
		}*/]
	});

    // load form and assign value to fields
    formPanel.form.load({
    				url:pathUrl+'/angleBankOrg/queryBankOrgById', 
    				params: {bank_org_id: id},
    				success:function(){
    					initLocation(new Object());
    				}
    
    });    

    // define window and show it in desktop
    var window = new Ext.Window({
        title: '机构维护',
        width: 400,
        height:300,
        minWidth: 400,
        minHeight: 300,
        layout:'fit',
        modal : true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
        buttons: [{
            text: '保存', 
            handler: function() {
            	if(!checkInput()){
            		return;
            	}
                if (formPanel.form.isValid()) 
				{
	 		        formPanel.form.submit({		
	 		            //params:{resourceID : rid;},	      
			            waitMsg:'正在处理，请稍候。。。。。。',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('错误', action.result.info);
						},
						success: function(form, action) {
						    var rname=action.result.results.bank_org_name;
						    var rid = action.result.results.bank_org_id;
						    rname = '['+rid+']'+rname;
							tree.getSelectionModel().getSelectedNode().setText(rname);
						    Ext.MessageBox.alert('确认', '保存完毕！');
						    window.destroy();
						}
			        });                   
                } else{
					Ext.MessageBox.alert('错误', '请填写必输项！');
				}             
	        }
        },{
            text: '取消',
            handler: function(){window.destroy();}
        }]
    });

    window.show();
    var offsetLeft = window.getPosition(true)[0];
    var offsetTop = window.getPosition(true)[1];
    
    var div = Ext.getDom('location').parentNode;
	var span = document.createElement("span");
	span.innerHTML = "<a id='edImg' href='javascript:showArea("+offsetLeft+","+offsetTop+");'><img src="+ pathUrl + "/static/sys/images/angle/edit.png></a>";
	div.appendChild(span);
	
};

bankOrgDeletor=function(tree,id){
	 if(id == '8888'){
		 return;    
	 }
    Ext.Msg.confirm("删除提示","确认要删除所选机构吗？",function(btn){
    	if(btn == 'yes'){
    		
    		//校验是否有子节点 如果有子节点 需先删除 子节点
    		Ext.Ajax.request({
    			url:pathUrl+'/angleBankOrg/queryBankOrgChildren',
    			method: 'POST',
    			params: {bank_org_id: id},
    			callback:function (options,success,response){
    				var json = Ext.util.JSON.decode(response.responseText);
    				if(json.results == ''){
    					Ext.Ajax.request({
    		    			url: pathUrl+'/angleBankOrg/deleteBankOrg?moduleCode=0098', //url to server side script
    		    			method: 'POST',
    		    			params: {bank_org_id: id},//the unique id(s)
    		    			callback: function (options, success, response) {
    		    				var json=Ext.util.JSON.decode(response.responseText);
    		    				if (success) { //success will be true if the request succeeded
    		    					Ext.MessageBox.hide();
    		    					tree.getSelectionModel().getSelectedNode().remove();
    		    					Ext.MessageBox.alert("提示信息",json.info);
    		    					//tree.getSelectionModel().getSelectedNode().expand();
    		    				} else {
    		    					Ext.MessageBox.alert("提示信息",json.info);
    		    					Ext.MessageBox.hide();
    		    				}
    		    			}
    		    		});
    				}else{
    					Ext.Msg.alert('提示信息','含有子节点的机构无法删除，请先删除子节点');
    				}
    			}
    			
    		});
    	}
    })
	
}

/**
 * 提交前校验录入结果
 * @returns {Boolean}
 */
function checkInput(){
	var idValue = Ext.getCmp('bank_org_id').getValue();
    var nameValue = Ext.getCmp('bank_org_name').getValue();
    var orderValue = Ext.getCmp('display_order').getValue();
    if(idValue == ''){
        Ext.Msg.alert('提示信息','机构代码输入不能为空!');
        return false;
    }
    if(idValue.length>6){
    	Ext.Msg.alert('提示信息','机构代码输入长度不能大于6!');
        return false;
    }
    if(nameValue == ''){
    	Ext.Msg.alert('提示信息','机构名称输入不能为空!');
        return false;
    }
    var re = /^[1-9]+[0-9]*]*$/;
    if(!re.test(orderValue)){
    	Ext.Msg.alert('提示信息','显示顺序必须为整数!');
    	return false;
    }
    return true;
}

/**
 * 初始化所属地区
 * @param obj
 */
function initLocation(obj){
	//获取地区编码
	var location_code = Ext.getCmp('location_code').getValue();
	//获取地区名称 赋值
	var location_name = getLocationName(location_code);
	Ext.getCmp('location').setValue(location_name);
}

/*
Ext.onReady(function(){
	Ext.lib.Ajax.defaultPostHeader += '; charset=utf-8';
});
*/
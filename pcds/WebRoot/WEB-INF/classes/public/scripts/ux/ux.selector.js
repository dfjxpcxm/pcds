//ux. 定义扩展的的下拉框
/**
 * 机构树下拉框 <br>
 * 页面必须包含hideBankOrgId,hideBankOrgName 隐藏字段
 */
var BankCombo=function(config){
	
	var width = config.width || 260;
	var height = config.height || 240;
	var anchor = config.anchor || '81%';
	var is_expand = config.is_expand || false;
	var fieldLabel = config.fieldLabel ||'机构';
	//机构ID
	var bankId = config.bankOrgId || hideBankOrgId;
	//机构名
	var bankName = config.bankOrgName || hideBankOrgName;
	//隐藏机构ID
	var hiddenValue = bankId; 
	//默认根节点
	var rootId = config.rootId || bankId;
	var rootName = config.rootName || bankName;
	//默认ID
	var id = config.id || 'bankCombo';
	
	BankCombo.superclass.constructor.call(this,{
        id: id,
		autoSelect:true,
		mode: 'local',
		triggerAction : "all",
		editable: false,
		anchor:anchor,
		value : bankName,
		fieldLabel : fieldLabel,
		store: {
		    xtype:'arraystore',
		    fields : ['bank_org_id','bank_org_name'],
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
	//展开事件
	this.expand=function(){
		if(!is_expand){
	        this.menu = new Ext.menu.Menu({
	            items : [{xtype: 'treepanel',
	                border: false,
	                id : id+'Tree',
	                autoScroll: true,
	                width: width,
	                height: height,
	                bodyStyle: 'padding:2px;',
	                rootVisible: true,
	                root :getRootNode(rootId, rootName, expandBankTree),
	                listeners: {
	                    click: function(node){
	                    	var comboStore = Ext.getCmp(id).getStore();
	                    	comboStore.removeAll();
	                    	comboStore.insert(0,new Ext.data.Record({bank_org_id:node.id,bank_org_name:''}));
							Ext.getCmp(id).setValue(node.text);
	 						//设置隐藏值
							hiddenValue = node.id;
							//关闭面板
							this.ownerCt.hide();
							//回调函数
							if(null != config.callback){
								config.callback(node.id);
							}
	                    },
	                    load : function(node){
	                    	is_expand = true;
	                    }
	                }
	            }]
	        });
	        this.menu.show(this.el, "tl-bl?");
	        Ext.getCmp(id+'Tree').getRootNode().expand();
		}else{
			this.menu.show(this.el, "tl-bl?");
		}
    };
}
Ext.extend(BankCombo,Ext.form.ComboBox);


/**
* 客户经理
*/
//参数：paramBank
var paramBank = hideBankOrgId;

var MgrCombo=function(config){
	var width = config.width || 260;
	var height = config.height || 240;
	var anchor = config.anchor || '81%';
	var is_expand = config.is_expand || false;
	//机构ID
	var mgrId = config.custMgrId || hideCustMgrId;
	//机构名
	var mgrName = config.custMgrName || hideCustMgrName;
	//隐藏机构ID
	var hiddenValue = mgrId;
	//默认ID
	var id = config.id || 'mgrCombo';
	var name = config.name || 'mgr_combo';
	
	MgrCombo.superclass.constructor.call(this,{
        id: id,
        name:name,
        hiddenName:name,
        valueField : 'cust_mgr_id',
		displayField : 'cust_mgr_name',
		autoSelect:true,
		mode: 'local',
		triggerAction : "all",
		editable: false,
		anchor:anchor,
		value : mgrName,
		fieldLabel : '客户经理',
		store: {
		    xtype:'arraystore',
		    fields : ['cust_mgr_id','cust_mgr_name'],
		    data:[['','']]
		}
	});
	//添加隐藏属性
	this.getHiddenValue = function(){
		return hiddenValue;
	}
	//添加隐藏属性
	this.setHiddenValue = function(v){
		return hiddenValue = v;
	}
	//初始化
	this.init = function(){
		if(isManager){
			var comboStore = Ext.getCmp(id).getStore();
          	comboStore.removeAll();
          	comboStore.insert(0,new Ext.data.Record({cust_mgr_id:'00' ,cust_mgr_name:'--全部--'}));
			Ext.getCmp(id).setValue('00');
			//设置隐藏值
			hiddenValue = '00';
		}else{
			Ext.Ajax.request({
				url : pathUrl + '/selectorAjax.do?method=listOrgMgr',
				method : 'POST',
				params : {},
				success : function(resp, opts) {
					var json = Ext.util.JSON.decode(resp.responseText);
					if(json.results && json.results.length>0){
					 	var cid = json.results[0].cust_mgr_id;
					 	var cname =json.results[0].cust_mgr_name; 
					 	var comboStore = Ext.getCmp(id).getStore();
                    	comboStore.removeAll();
                    	comboStore.insert(0,new Ext.data.Record({cust_mgr_id:cid ,cust_mgr_name:cname}));
						Ext.getCmp(id).setValue(cid);
 						//设置隐藏值
						hiddenValue = cid;
					}
				} 
			});
			//隐藏选择
			this.expand = function(){
				return ;
			}
		}
	}
	//下拉展开事件,listBankMgr
	this.expand=function(){
		if(!is_expand){
			var custMgrDS = new Ext.data.JsonStore({
			    url : pathUrl + '/selectorAjax.do?method=listBankMgr',
			    totalProperty: 'totalCount',
			    root:'results',
			    fields:['cust_mgr_id','cust_mgr_name'] 
			});
			custMgrDS.on('beforeload',function(custMgrDS){
			    var custMgrStr = Ext.getCmp("custName").getValue();
			    if(custMgrStr==null)custMgrStr='';
			    //机构条件
			    custMgrDS.baseParams = {custMgrID:encodeURI(custMgrStr),bankOrgId : paramBank }; 
			});
			//展开
	        this.menu = new Ext.menu.Menu({
	            items : [{
			        xtype : 'grid',
			        id : id+'Grid',
			        width:width,
			        height:height,
     	            autoScroll: true,
			        border:false,
					stripeRows : true,
			        sm : new Ext.grid.RowSelectionModel({
			        	listeners:{
			        		rowselect : function(t,index,r){
			        			var comboStore = Ext.getCmp(id).getStore();
		                    	comboStore.removeAll();
		                    	comboStore.insert(0,new Ext.data.Record({cust_mgr_id:r.get('cust_mgr_id'),cust_mgr_name:''}));
								Ext.getCmp(id).setValue(r.get('cust_mgr_name')+'['+r.get('cust_mgr_id')+']');
		 						//设置隐藏值
								hiddenValue = r.get('cust_mgr_id');
								//关闭面板
								Ext.getCmp(id+'Grid').ownerCt.hide();
								//回调函数
								if(null != config.callback){
									config.callback(r.get('cust_mgr_id'));
								}
			        		}
			        	}
			        }),
			        columns : [
					    new Ext.grid.RowNumberer(),
					    {
					        id: 'cust_mgr_id',
					        header: "客户经理号",
					        dataIndex: 'cust_mgr_id'
					    },{
					        header: "客户经理名", 
					        dataIndex: 'cust_mgr_name'
					    }
					],
			        viewConfig:{
			           forceFit : true
			        },
			        store : custMgrDS,
			        tbar:[{
					    xtype:'tbtext',
					    text:'客户经理:'
					},{
					    xtype:'textfield',
					    id:"custName",
					    name:"custName",
					    width:80,
					    listeners : {
					        specialkey : function(field, e) {
					            if (e.getKey() == Ext.EventObject.ENTER) {
					                custMgrDS.load({params:{start:0,limit:30}});
					            }
					        }
					    }
					},{
					    text:"搜索",
					    tooltip:"搜索客户经理",
					    iconCls:"search",
					    handler:function(){
					        custMgrDS.load({params:{start:0,limit:30}});
					    }
					}],
			        bbar: new Ext.PagingToolbar({
			            pageSize: 30,
			            store: custMgrDS,
			            displayInfo: true,
			            displayMsg: '{0}-{1}',
			            emptyMsg: "没有记录"
			        }) 
			    } ]
	        });
	        is_expand = true;
	        this.menu.show(this.el, "tl-bl?");
		}else{
			this.menu.show(this.el, "tl-bl?");
		}
    };
}
Ext.extend(MgrCombo,Ext.form.ComboBox);

/**
 * 产品
*/
var prodCombo=function(config){
	
	var width = config.width || 260;
	var height = config.height || 240;
	var anchor = config.anchor || '81%';
	var is_expand = config.is_expand || false;
	var fieldLabel = config.fieldLabel ||'产品';
	//产品名
	var prodId = config.productId || '';
	var prodName = config.productName || '';
	//隐藏机构ID
	var hiddenValue = prodId; 
	//默认根节点
	var rootId = config.rootId || prodId;
	var rootName = config.rootName || prodName;
	//默认ID
	var id = config.id || 'prodCombo';
	//根节点
	var rootNode = new Ext.tree.TreeNode({id:'@@@@@',text:'根节点'});
	
	BankCombo.superclass.constructor.call(this,{
        id: id,
		autoSelect:true,
		mode: 'local',
		triggerAction : "all",
		editable: false,
		anchor:anchor,
		fieldLabel : fieldLabel,
		store: {
		    xtype:'arraystore',
		    fields : ['product_id','product_name'],
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
	//初始化
	this.init = function(){
		Ext.Ajax.request({
			url : pathUrl + '/selectorAjax.do?method=listProfitProduct',
			method : 'POST',
			params : {},
			success : function(resp, opts) {
				var json = Ext.util.JSON.decode(resp.responseText);
				if(json.results && json.results.length>0){
					//清空下拉框
					var comboStore = Ext.getCmp(id).getStore();
                   	comboStore.removeAll();
                   	
					for(var i=0;i<json.results.length;i++){
					 	var cid = json.results[i].productId;
					 	var cname =json.results[i].productName; 
					 	//子节点
					 	rootNode.appendChild(getRootNode(cid,cname,expandProductTree));
	                   	//默认选中
	                   	if(i==0){
		                   	comboStore.insert(0,new Ext.data.Record({product_id:cid ,product_name:''}));
							Ext.getCmp(id).setValue(cname);
							//设置隐藏值
							hiddenValue = cid;
						}
					}
				}
			} 
		});
	}
	//展开事件
	this.expand=function(){
		if(!is_expand){
	        this.menu = new Ext.menu.Menu({
	            items : [{xtype: 'treepanel',
	                border: false,
	                id : id+'Tree',
	                autoScroll: true,
	                width: width,
	                height: height,
	                bodyStyle: 'padding:2px;',
	                rootVisible: false,
	                root : rootNode,
	                listeners: {
	                    click: function(node){
	                    	var comboStore = Ext.getCmp(id).getStore();
	                    	comboStore.removeAll();
	                    	comboStore.insert(0,new Ext.data.Record({product_id:node.id,product_name:''}));
							Ext.getCmp(id).setValue(node.text);
	 						//设置隐藏值
							hiddenValue = node.id;
							//关闭面板
							this.ownerCt.hide();
							//回调函数
							if(null != config.callback){
								config.callback(node.id);
							}
	                    } 
	                }
	            }]
	        });
	        this.menu.show(this.el, "tl-bl?");
	        //已经打开过
			is_expand = true;
	        //Ext.getCmp(id+'Tree').getRootNode().expand();
		}else{
			this.menu.show(this.el, "tl-bl?");
		}
    };
}
Ext.extend(prodCombo,Ext.form.ComboBox);

/**
 * 元数据树下拉框 <br>
 */
var MetadataCombo=function(config){
	var width = config.width || 260;
	var height = config.height || 240;
	var anchor = config.anchor || '81%';
	var is_expand = config.is_expand || false;
	var fieldLabel = config.fieldLabel ||'元数据';
	var metadataId = config.metadata_id || 'root';
	var metadataName = config.metadata_name || '元数据';
	var rootId = config.root_id || metadataId;
	var rootName = config.root_name || metadataName;
	var id = config.id || 'metadataCombo';
	
	MetadataCombo.superclass.constructor.call(this,{
        id: id,
		autoSelect:true,
		mode: 'local',
		triggerAction : "all",
		editable: false,
		anchor:anchor,
		value : metadataName,
		fieldLabel : fieldLabel,
		store: {
		    xtype:'arraystore',
		    fields : ['metadata_id','metadata_name'],
		    data:[['','']]
		}
	});
	
	function getRootNode(id,text,fn)
	{
		var root = new Ext.tree.AsyncTreeNode({
			id:id,
			text:text,
			children:[{
				text:'loading',
				cls: 'x-tree-node-loading',
				leaf:true
			}]
		});
	
		if(fn!=undefined)
			root.on('expand',fn);
	
		return root;
	}
	
	
	function expandMetadataTree(node)
	{
		if(node.firstChild.text=='loading'){
			Ext.Ajax.request({
				url: pathUrl+'/themeClass/queryMetadataTreeNode',
				waitMsg:'正在处理，请稍候。。。。。。',
				method: 'POST',
				params: {nodeID:node.id},
				callback: function (options, success, response) {
					var json=Ext.util.JSON.decode(response.responseText);
					var tl=json.results;
					if(tl)
					{
						for(var i=0;i<tl.length;i++){
							var cnode=new Ext.tree.AsyncTreeNode({
								id:tl[i].id,
	//							text:'['+tl[i].id+']'+tl[i].text+'('+tl[i].property+')',
								text:tl[i].text,
								leaf:tl[i].leaf,
								children:[{
									text:'loading',
									cls: 'x-tree-node-loading',
									leaf:true
								}]
							});
							cnode.on('expand',expandMetadataTree);
							node.appendChild(cnode);
						}
					}
					node.firstChild.remove();
				},
	
				failure:function(response, options){
					Ext.MessageBox.hide();
					var msg = '出错!'
					Ext.MessageBox.alert(response.responseText+' '+msg);
				},  
	
				success:function(response, options){
					Ext.MessageBox.hide();
				}
			});
		}
	}


	//添加隐藏属性，
	this.getHiddenValue = function(){
		return hiddenValue;
	}
	//添加隐藏属性，
	this.setHiddenValue = function(v){
		return hiddenValue = v;
	}
	//展开事件
	this.expand=function(){
		if(!is_expand){
	        this.menu = new Ext.menu.Menu({
	            items : [{xtype: 'treepanel',
	                border: false,
	                id : id+'Tree',
	                autoScroll: true,
	                width: width,
	                height: height,
	                bodyStyle: 'padding:2px;',
	                rootVisible: true,
	                root :getRootNode(rootId, rootName, expandMetadataTree),
	                listeners: {
	                    click: function(node){
	                    	var comboStore = Ext.getCmp(id).getStore();
	                    	comboStore.removeAll();
	                    	comboStore.insert(0,new Ext.data.Record({metadata_id:node.id,metadata_name:''}));
							Ext.getCmp(id).setValue(node.text);
	 						//设置隐藏值
							hiddenValue = node.id;
							//关闭面板
							this.ownerCt.hide();
							//回调函数
							if(null != config.callback){
								config.callback(node.id);
							}
	                    },
	                    load : function(node){
	                    	is_expand = true;
	                    }
	                }
	            }]
	        });
	        this.menu.show(this.el, "tl-bl?");
	        Ext.getCmp(id+'Tree').getRootNode().expand();
		}else{
			this.menu.show(this.el, "tl-bl?");
		}
    };
}
Ext.extend(MetadataCombo,Ext.form.ComboBox);

/**
 * 主题树下拉框 <br>
 */
var ThemeCombo=function(config){
	var width = config.width || 260;
	var height = config.height || 240;
	//var anchor = config.anchor || '81%';
	var is_expand = config.is_expand || false;
	var fieldLabel = config.fieldLabel ||'主题';
	var metadataId = config.metadata_id || 'theme';
	var metadataName = config.metadata_name || '主题';
	var rootId = config.root_id || metadataId;
	var rootName = config.root_name || metadataName;
	var id = config.id || 'themeCombo';
	var allowBlank = config.allowBlank || false;
	ThemeCombo.superclass.constructor.call(this,{
        id: id,
		autoSelect:true,
		mode: 'local',
		triggerAction : "all",
		editable: false,
		valueField : 'theme_id',
		displayField : 'theme_name',
		name:'theme_id',
		allowBlank:allowBlank,
		hiddenName:"theme_id",
		width:width,
		//anchor:anchor,
		//value : metadataName,
		fieldLabel : fieldLabel,
		store: {
		    xtype:'arraystore',
		    fields : ['metadata_id','metadata_name'],
		    data:[['','']]
		}
	});
	
	function getRootNode(id,text,fn)
	{
		var root = new Ext.tree.AsyncTreeNode({
			id:id,
			text:text,
			children:[{
				text:'loading',
				cls: 'x-tree-node-loading',
				leaf:true
			}]
		});
	
		if(fn!=undefined)
			root.on('expand',fn);
	
		return root;
	}
	
	
	/** 指标节点展开方法  **/
	function expandThemeTree(node){
		if(node.firstChild.text=='loading'){
			Ext.Ajax.request({
				url: pathUrl+'/themeClass/tree',
				waitMsg:'正在处理，请稍候。。。。。。',
				method: 'POST',
				params: {nodeID:node.id},
	
				callback: function (options, success, response) {
					var json=Ext.util.JSON.decode(response.responseText);
					var tl=json.results;
					if(tl)
					{
						for(var i=0;i<tl.length;i++){
							var cnode=new Ext.tree.AsyncTreeNode({
								id:tl[i].id,
	//							text:'['+tl[i].id+']'+tl[i].text,
								text:tl[i].text,
								leaf:tl[i].leaf,
								children:[{
									text:'loading',
									cls: 'x-tree-node-loading',
									leaf:true
								}]
							});
							cnode.on('expand',expandThemeTree);
							node.appendChild(cnode);
						}
					}
					node.firstChild.remove();
				},
	
				failure:function(response, options){
					Ext.MessageBox.hide();
					var msg = '出错!'
					Ext.MessageBox.alert(response.responseText+' '+msg);
				},  
	
				success:function(response, options){
					Ext.MessageBox.hide();
				}
			});
		}
	}


	//添加隐藏属性，
	this.getHiddenValue = function(){
		return hiddenValue;
	}
	//添加隐藏属性，
	this.setHiddenValue = function(v){
		return hiddenValue = v;
	}
	//展开事件
	this.expand=function(){
		if(!is_expand){
	        this.menu = new Ext.menu.Menu({
	            items : [{xtype: 'treepanel',
	                border: false,
	                id : id+'Tree',
	                autoScroll: true,
	                width: width,
	                height: height,
	                bodyStyle: 'padding:2px;',
	                rootVisible: true,
	                root :getRootNode(rootId, rootName, expandThemeTree),
	                listeners: {
	                    click: function(node){
	                    	var comboStore = Ext.getCmp(id).getStore();
	                    	comboStore.removeAll();
	                    	comboStore.insert(0,new Ext.data.Record({theme_id:node.id,theme_name:''}));
							Ext.getCmp(id).setValue(node.id);
							Ext.getCmp(id).setRawValue(node.text);
	 						//设置隐藏值
							hiddenValue = node.id;
							//关闭面板
							this.ownerCt.hide();
							//回调函数
							if(null != config.callback){
								config.callback(node.id);
							}
	                    },
	                    load : function(node){
	                    	is_expand = true;
	                    }
	                }
	            }]
	        });
	        this.menu.show(this.el, "tl-bl?");
	        Ext.getCmp(id+'Tree').getRootNode().expand();
		}else{
			this.menu.show(this.el, "tl-bl?");
		}
    };
}
Ext.extend(ThemeCombo,Ext.form.ComboBox);

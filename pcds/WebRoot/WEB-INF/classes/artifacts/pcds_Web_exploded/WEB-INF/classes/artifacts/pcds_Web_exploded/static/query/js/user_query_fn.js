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

var configTbar = [{
	text : '自定义查询配置',
	iconCls : 'page_white_wrench',
	handler:function(){
		showOrderWin(userDsId);
	}
}];

//执行查询
function doQuery(dsId){
	Ext.getCmp('dataGrid'+dsId).getStore().load({ 
		params:{
			start : 0,
			limit : 30 
		}
	});
}

/**
 * 导出数据
 * @param tmpl_id
 */
function doExport(dsId){
	var pars = _queryForm.getForm().getValues(true);
	var form = document.getElementById("expForm");
	document.getElementById("baseparams").value = pars;
	form.action = pathUrl+'/queryTmpl/exportData/'+dsId;
	form.submit();
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

//刷新主模版数据
function reloadMainGrid(){
	if(Ext.getCmp('dataGrid'+templateId)){
		Ext.getCmp('dataGrid'+templateId ).getStore().reload();
	}
}
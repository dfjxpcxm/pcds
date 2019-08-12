var selectLinkId = '', isTree = '', tree = '', rootValue = '';

Ext.onReady(function() {
	MyViewportUi = new Ext.Viewport({
			layout : 'border',
			items : [
			         mainPanel=new Ext.Panel({
					region:'center',
					tbar:[{
						xtype:'label',
						style:'padding:0 10px 0 5px;',
						html:'请选择查询模版:'
					},
					new BlmbCombo({width:240})
					],
					contentEl: 'center',
					resizeEl: 'center-iframe',
					border:false
				})
			]
		});
});



/**
 * 主题树下拉框 <br>
 */
var BlmbCombo=function(config){
	var width = config.width || 260;
	var height = config.height || 240;
	//var anchor = config.anchor || '81%';
	var is_expand = config.is_expand || false;
	var metadataId = config.metadata_id || 'root';
	var metadataName = config.metadata_name || '所有信息';
	var rootId = config.root_id || metadataId;
	var rootName = config.root_name || metadataName;
	var id = config.id || 'BlmbCombo';
	var allowBlank = config.allowBlank || false;
	BlmbCombo.superclass.constructor.call(this,{
        id: id,
		autoSelect:true,
		mode: 'local',
		triggerAction : "all",
		editable: false,
		valueField : 'tmpl_id',
		displayField : 'template_name',
		name:'tmpl_id',
//		allowBlank:allowBlank,
		hiddenName:"tmpl_id",
		width:width,
		//anchor:anchor,
		//value : metadataName,
		fieldLabel : '',
		store: {
		    xtype:'arraystore',
		    fields : ['tmpl_id','template_name'],
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
	                loader: new Ext.tree.TreeLoader({dataUrl: pathUrl + '/queryTmpl/getUserDsTree'}),
	                root :new Ext.tree.AsyncTreeNode({
	                    text: '数据集',
	                    iconCls:'folder_table',
	                    id:'root'
	                }),
	                listeners: {
	                    click: function(node){
	                    	var comboStore = Ext.getCmp(id).getStore();
	                    	comboStore.removeAll();
	                    	comboStore.insert(0,new Ext.data.Record({tmpl_id:node.id,template_name:''}));
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
							var t_t_c = node.attributes.attr1;//模板类别   01：目录     02：补录表
							var form = document.getElementById("f_form");
							if('1'==t_t_c ){
								form.action = pathUrl + "/queryTmpl/installPage/"+node.id;
								form.target = "center-iframe";
								form.submit();
							}else{
								form.action = pathUrl + "/flow/testInfo/"+encodeURI(encodeURI(node.text));
								form.target = "center-iframe";
								form.submit();
								Ext.getCmp('warn_info').setText('');
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
Ext.extend(BlmbCombo,Ext.form.ComboBox);

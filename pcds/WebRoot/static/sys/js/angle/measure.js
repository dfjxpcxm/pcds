measureAddor = function(tree) {
    var id = tree.getSelectionModel().getSelectedNode().id;
    if(id == '~13')
       return;
    var formPanel = new Ext.form.FormPanel({
        baseCls: 'x-plain',
        labelWidth: 75,
        url:pathUrl+'/angleMeasure/addMeasure?moduleCode=0096',
        items: [
		{
			xtype : 'textfield' ,
			fieldLabel: '指标代码',
			allowBlank: false,
			name: 'measure_id',
			id: 'measure_id',
			anchor: '90%'
		},
		{
			xtype : 'textfield' ,
			fieldLabel: '指标名称',
			allowBlank: false,
			name: 'measure_name',
			id: 'measure_name',
			anchor: '90%'  
		},
		{
			xtype:'datefield',
			fieldLabel:'启用日期',
			format:'Y-m-d',
			value:new Date(),
			id:'being_date',
			name:'begin_date',
			anchor:'90%'
		},{
			xtype:'datefield',
			fieldLabel:'结束日期',
			format:'Y-m-d',
			value:'2100-12-31',
			id:'end_date',
			name:'end_date',
			anchor:'90%'
		},
		{
			xtype:'combo',
			store: new Ext.data.SimpleStore({
				fields: ["retrunValue", "displayText"],
				data: [['+','+'],['-','-'],['~','~']]
			}),
			valueField :"retrunValue",
			displayField: "displayText",
			mode: 'local',
			forceSelection: true,
			editable: false,
			hiddenName:'measure_property',
			triggerAction: 'all',
			allowBlank:false,
			fieldLabel: '指标属性',
			name: 'type',
			id: 'type',
			value: '+',
			anchor: '90%'  
		},
		{
			xtype : 'textfield' ,
			fieldLabel: '指标等级',
			name: 'measure_level',
			id: 'measure_level',
			anchor: '90%'  
		},
		new MeasureTreeSelector({anchor:'90%'}),
		{
			xtype:'hidden',
			name: 'parent_measure_id',
			id: 'parent_measure_id',
			anchor: '90%' ,
			value:id
		}
		]
	});

    var window = new Ext.Window({
        title: '指标维护',
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
                if (formPanel.form.isValid()) 
				{
	 		        formPanel.form.submit({      
			            waitMsg:'正在处理，请稍候。。。。。。',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('错误', action.result.info);
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('确认', '保存完毕！');
						    var nodeID=action.result.results.measure_id;
							var nodeName=action.result.results.measure_name;
							var nodeProperty = action.result.results.measure_property;
							var node=new Ext.tree.AsyncTreeNode({
								id:nodeID,
								text:'['+nodeID+']'+nodeName+'('+nodeProperty+')',
								leaf:true
							});
							//node.on('expand',getEvaMeasureTreeNode);
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
};


measureEditor = function(tree) {
    var id=tree.getSelectionModel().getSelectedNode().id;
    if(id == '~13')
       return;
    var formPanel = new Ext.form.FormPanel({
        baseCls: 'x-plain',
        labelWidth: 75,
        url:pathUrl+'/angleMeasure/editMeasure?moduleCode=0097',
        
        reader: new Ext.data.JsonReader({
				root: 'results'
			}, [{name: 'measure_id'},
			{name: 'measure_name' },
			{name: 'parent_measure_id'},
			{name: 'begin_date'},
			{name: 'end_date' },
			{name: 'measure_property' },
			{name: 'measure_tree_code' },
			{name: 'measure_level' }]
        ),
        items: [{
			xtype : 'textfield' ,
			fieldLabel: '指标代码',
			allowBlank: false,
			name: 'measure_id',
			id: 'measure_id',
			anchor: '90%'
		},
		{
			xtype : 'textfield' ,
			fieldLabel: '指标名称',
			allowBlank: false,
			name: 'measure_name',
			id: 'measure_name',
			anchor: '90%'  
		},
		{
			xtype:'datefield',
			fieldLabel:'启用日期',
			format:'Y-m-d',
			id:'being_date',
			name:'begin_date',
			anchor:'90%'
		},{
			xtype:'datefield',
			fieldLabel:'结束日期',
			format:'Y-m-d',
			id:'end_date',
			name:'end_date',
			anchor:'90%'
		},
		{
			xtype:'combo',
			store: new Ext.data.SimpleStore({
				fields: ["retrunValue", "displayText"],
				data: [['+','+'],['-','-'],['~','~']]
			}),
			valueField :"retrunValue",
			displayField: "displayText",
			mode: 'local',
			forceSelection: true,
			hiddenName:'measure_property',
			triggerAction: 'all',
			allowBlank:false,
			editable: false,
			fieldLabel: '指标类型',
			name: 'type',
			id: 'type',
			anchor: '90%'  
		},{
			xtype : 'textfield' ,
			fieldLabel: '指标等级',
			name: 'measure_level',
			id: 'measure_level',
			anchor: '90%'  
		},new MeasureTreeSelector({anchor:'90%'}),
		{
			xtype:'hidden',
			name: 'parent_measure_id',
			id: 'parent_measure_id',
			anchor: '90%', 
			value: id
		}]
	});

    // load form and assign value to fields
    formPanel.form.load({url:pathUrl+'/angleMeasure/queryMeasureById', params: {measure_id: id}});    

    // define window and show it in desktop
    var window = new Ext.Window({
        title: '指标维护',
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
                if (formPanel.form.isValid()) 
				{
	 		        formPanel.form.submit({		
	 		            //params:{resourceID : rid;},	      
			            waitMsg:'正在处理，请稍候。。。。。。',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('错误', action.result.info);
						},
						success: function(form, action) {
						    var rname=action.result.results.measure_name;
						    var rid = action.result.results.measure_id;
						    var rproperty = action.result.results.measure_property;
						    rname = '['+rid+']'+rname+'('+rproperty+')';
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
};

measureDeletor=function(tree,id){
    if(id == '~13')
       return;
    
    Ext.Msg.confirm("删除提示","确认要删除所选指标吗？",function(btn){
    	if(btn == 'yes'){
    		
    		//校验是否有子节点 如果有子节点 需先删除 子节点
    		Ext.Ajax.request({
    			url:pathUrl+'/angleMeasure/queryMeasureChildren',
    			method: 'POST',
    			params: {measure_id: id},
    			callback:function (options,success,response){
    				var json = Ext.util.JSON.decode(response.responseText);
    				if(json.results == ''){
    					Ext.Ajax.request({
    		    			url: pathUrl+'/angleMeasure/deleteMeasure?moduleCode=0098', //url to server side script
    		    			method: 'POST',
    		    			params: {measure_id: id},//the unique id(s)
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
    					Ext.Msg.alert('提示信息','含有子节点的指标无法删除，请先删除子节点');
    				}
    			}
    			
    		});
    	}
    })
	
}

/*
Ext.onReady(function(){
	Ext.lib.Ajax.defaultPostHeader += '; charset=utf-8';
});
*/
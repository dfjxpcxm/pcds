MonthSelector = function(config) {
	
	var an =  config.anchor || '91%';
	var hiddenName = config.hiddenName || 'month_id';
	var id = config.id || 'monthId';
	var fieldLabel = config.fieldLabel || '月份';
	
	var store = new Ext.data.JsonStore({
		url : pathUrl + '/selector/listMonth',
		root : 'results',
		totalProperty : 'totalCount',
		fields : ['month_id', 'month_name']
	});

	store.on('load', changeSelect);
	store.load();

	MonthSelector.superclass.constructor.call(this, {
		store : store,
		valueField : 'month_id',
		displayField : 'month_name',
		mode : 'local',
		hiddenName : hiddenName,
		editable : false,
		triggerAction : 'all',
		allowBlank : false,
		fieldLabel : fieldLabel,
		name : 'month_id',
		id : id,
		anchor : an
	});

	function changeSelect() {
		if (store.getCount() > 0) {
			var combo = Ext.getCmp('monthId');
			var value = store.getAt(0).get('month_id');
			combo.setValue(value);
		}
	}
}
Ext.extend(MonthSelector, Ext.form.ComboBox);

/**
 * 机构树下拉框
 */
var BankCombo=function(config){
	
	var width = config.width || 300;
	var height = config.height || 260;
	var anchor = config.anchor || '81%';
	var is_expand = config.is_expand || false;
	
	BankCombo.superclass.constructor.call(this,{
        id: 'bankCombo',
		autoSelect:true,
		mode: 'local',
		triggerAction : "all",
		editable: false,
		anchor:anchor,
		value : '['+bank_org_id+']'+bank_org_name,
		fieldLabel : '机构',
		store: {
		    xtype:'arraystore',
		    fields : ['bank_org_id','bank_org_name'],
		    data:[['','']]
		}
	});
	
	this.expand=function(){
		if(!is_expand){
        this.menu = new Ext.menu.Menu({
            items : [{xtype: 'treepanel',
                border: false,
                id : 'comboTree',
                autoScroll: true,
                width: width,
                height: height,
                bodyStyle: 'padding:2px;',
                rootVisible: true,
                root :getRootNode(bank_org_id, "["+bank_org_id+"]"+bank_org_name, expandBankTree),
                listeners: {
                    click: function(node){
                    	var comboStore = Ext.getCmp('bankCombo').getStore();
                    	comboStore.removeAll();
                    	comboStore.insert(0,new Ext.data.Record({bank_org_id:node.id,bank_org_name:node.text}));
						Ext.getCmp('bankCombo').setValue(node.text);
						this.ownerCt.hide();
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
        Ext.getCmp('comboTree').getRootNode().expand();
	}else{
		this.menu.show(this.el, "tl-bl?");
	}
    };
}
Ext.extend(BankCombo,Ext.form.ComboBox);

/**
 * 
 * @param config
 * @returns
 */
var ProductCombo=function(config){
	
	var width = config.width || 300;
	var height = config.height || 260;
	var anchor = config.anchor || '81%';
	var is_expand = config.is_expand || false;
	
	ProductCombo.superclass.constructor.call(this,{
        id: 'productCombo',
		autoSelect:true,
		mode: 'local',
		triggerAction : "all",
		editable: false,
		anchor:anchor,
		value : '['+product_id+']'+product_name,
		fieldLabel : '产品',
		store: {
		    xtype:'arraystore',
		    fields : ['product_id','product_name'],
		    data:[['','']]
		}
	});
	
	this.expand=function(){
		if(!is_expand){
        this.menu = new Ext.menu.Menu({
            items : [{xtype: 'treepanel',
                border: false,
                id : 'productComboTree',
                autoScroll: true,
                width: width,
                height: height,
                bodyStyle: 'padding:2px;',
                rootVisible: true,
                root :getRootNode(product_id, "["+product_id+"]"+product_name, expandProductTree),
                listeners: {
                    click: function(node){
                    	var comboStore = Ext.getCmp('productCombo').getStore();
                    	comboStore.removeAll();
                    	comboStore.insert(0,new Ext.data.Record({product_id:node.id,product_name:node.text}));
						Ext.getCmp('productCombo').setValue(node.text);
						this.ownerCt.hide();
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
        Ext.getCmp('productComboTree').getRootNode().expand();
	}else{
		this.menu.show(this.el, "tl-bl?");
	}
    };
}
Ext.extend(ProductCombo,Ext.form.ComboBox);

/**
 * 账户类型
 */
CategoryTypeSelector=function(params){
	
	var v_anchor = params.anchor || '81%';
	
	var store = new Ext.data.JsonStore({
		url: pathUrl+'/selector/listCategoryType',
		totalProperty : 'totalCount',
		root : "results",
		fields : [ 'category_code', 'category_name']
	});
    
    store.on('load',changeSelect);
    store.load({params:{custType:params.custType}});
    
    CategoryTypeSelector.superclass.constructor.call(this,{
        store: store,
        valueField :'category_code',
        displayField:'category_name',
        mode: 'local',
        hiddenName:'category_code',
        editable: false,
        triggerAction: 'all',
        allowBlank:false,
        fieldLabel:'账户类型',
        name: 'category_code',
        id: 'categoryCode',
        anchor:v_anchor
    });
    
    function changeSelect()
    {
        if(store.getCount()>0)
        {
            var combo=Ext.getCmp('categoryCode');
            var value=store.getAt(0).get('category_code');
            combo.setValue(value);
        }
    }
}
Ext.extend(CategoryTypeSelector, Ext.form.ComboBox);

/**
 * 账户分类
 */
AccountTypeSelector=function(width){
	
	this.width = width || '81%';
    
    AccountTypeSelector.superclass.constructor.call(this,{
        store:  {
        	xtype:'simplestore',
            fields: ["retrunValue", "displayText"],
            data: [['01','对私'],['02','对公']]
        },
        valueField :'retrunValue',
        displayField:'displayText',
        mode: 'local',
        hiddenName:'accoountType',
        editable: false,
        triggerAction: 'all',
        allowBlank:false,
        fieldLabel:'账户分类',
        name: 'accoountType',
        id: 'accoount_type',
        value: '01',
        anchor:this.width,
        listeners:{//下拉列表变动时,重新加载账户类型
           select:function(c,r,i){
           	  categoryTypeSelector.clearValue();
              categoryTypeSelector.store.reload({params:{custType:c.getValue()}});
           }
        }
    });
}
Ext.extend(AccountTypeSelector, Ext.form.ComboBox);

/**
 * 获取下拉树的值,包含 [ ]
 * @param id
 * @returns
 */
function getComboTreeValue(id){
	var val = Ext.getCmp(id).getValue();
	if(val){
		return val.slice(val.indexOf("[")+1,val.indexOf("]"))
	}
	return val;
}
/*
 * 用途 ：机构（管理机构、营业机构、其他管理机构）选择选择下拉框 
 * 输入 ：无 
 * 返回 ：无
 */
BankWholeSelector = function() {
	var store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : pathUrl + '/selector/listBankOrganization'
		}),
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [{
			name : 'bankOrgID',
			mapping : 'bank_org_id'
		}, {
			name : 'bankOrgName',
			mapping : 'bank_org_name'
		}]),
		remoteSort : false
	});

	store.on('load', changeSelect);
	store.load();

	BankWholeSelector.superclass.constructor.call(this, {
		layout : 'form',
		border : false,
		items : [{
			xtype : 'combo',
			store : store,
			valueField : 'bankOrgID',
			displayField : 'bankOrgName',
			mode : 'local',
			hiddenName : 'bank_org_id',
			editable : false,
			triggerAction : 'all',
			allowBlank : false,
			fieldLabel : '归属机构',
			name : 'bankWholeSelector',
			id : 'bankWholeSelector',
			anchor : '75%'
		}]
	});

	function changeSelect() {
		if (store.getCount() > 0) {
			var combo = Ext.getCmp('bankWholeSelector');
			var value = store.getAt(0).get('bankOrgID');
			combo.setValue(value);
		}
	}

	this.load = function(m) {
		var combo = Ext.getCmp('bankWholeSelector');
		var bankOrgID = combo.getValue();
		store.load({
			params : {
				bank_org_id : bankOrgID,
				mode : m
			}
		});
	}

	this.initUI = function() {
		var div = Ext.getDom('bankWholeSelector').parentNode;
		var span1 = document.createElement("span");
		span1.style.marginLeft=20;
		
		span1.className = "span_left";
		span1.innerHTML = "<a href='javascript:bankWholeSelector.load(\"DrillUP\")'><img src="+ pathUrl + "/public/images/leftArrow.gif></a>";
		div.appendChild(span1);
		
		var span2 = document.createElement("span");
		span2.style.marginLeft=20;
		
		span2.className = "span_right";
		span2.innerHTML = "<a href='javascript:bankWholeSelector.load(\"DrillDown\")'><img src="+ pathUrl + "/public/images/rightArrow.gif></a>";
		div.appendChild(span2);
	}
}
Ext.extend(BankWholeSelector, Ext.Panel);

/*
用途 ：所属部门（管理机构、营业机构、其他管理机构）选择选择下拉框（数据和BankWholeSelector一样）
输入 ：无
返回 ：无 
*/
OwnerOrgSelector = function(ownerOrgId) {
	var store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : pathUrl + '/selector/listBankOrganization'
		}),
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [{
			name : 'bankOrgID',
			mapping : 'bank_org_id'
		}, {
			name : 'bankOrgName',
			mapping : 'bank_org_name'
		}]),
		remoteSort : false
	});

	store.on('load', changeSelect);
	store.load({params:{bank_org_id:ownerOrgId}});
	
	OwnerOrgSelector.superclass.constructor.call(this, {
		layout : 'form',
		border : false,
		items : [{
			xtype : 'combo',
			store : store,
			valueField : 'bankOrgID',
			displayField : 'bankOrgName',
			mode : 'local',
			hiddenName : 'owner_org_id',
			editable : false,
			triggerAction : 'all',
			allowBlank : false,
			fieldLabel : '归属机构<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
			name : 'ownerOrgSelector',
			id : 'ownerOrgSelector',
			anchor : '75%'
		}]
	});

	function changeSelect() {
		if (store.getCount() > 0) {
			var combo = Ext.getCmp('ownerOrgSelector');
			var value = store.getAt(0).get('bankOrgID');
			combo.setValue(value);
		}
	}

	this.load = function(m) {
		var combo = Ext.getCmp('ownerOrgSelector');
		var bankOrgID = combo.getValue();
		store.load({
			params : {
				bank_org_id : bankOrgID,
				mode : m
			}
		});
	}

	this.initUI = function() {
		var div = Ext.getDom('owner_org_id').parentNode;
		var span1 = document.createElement("span");
		span1.style.marginLeft=20;
		
		span1.className = "span_left";
		span1.innerHTML = "<a href='javascript:ownerOrgSelector.load(\"DrillUP\")'><img src="+ pathUrl + "/public/images/leftArrow.gif></a>";
		div.appendChild(span1);
		var span2 = document.createElement("span");
		span2.style.marginLeft=20;

		span2.className = "span_right";
		span2.innerHTML = "<a href='javascript:ownerOrgSelector.load(\"DrillDown\")'><img src="+ pathUrl + "/public/images/rightArrow.gif></a>";
		div.appendChild(span2);
	}
}
Ext.extend(OwnerOrgSelector, Ext.Panel);
/*
用途 ：管理机构（管理机构、营业机构、其他管理机构）选择选择下拉框（数据和BankWholeSelector一样）
输入 ：无
返回 ：无 
*/
ManageOrgSelector = function(bankOrgId) {
	var store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : pathUrl + '/selector/listBankOrganization'
		}),
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [{
			name : 'bankOrgID',
			mapping : 'bank_org_id'
		}, {
			name : 'bankOrgName',
			mapping : 'bank_org_name'
		}]),
		remoteSort : false
	});
	store.on('load', changeSelect);
	store.load({params:{bank_org_id:bankOrgId}});

	ManageOrgSelector.superclass.constructor.call(this, {
		layout : 'form',
		border : false,
		items : [{
			xtype : 'combo',
			store : store,
			valueField : 'bankOrgID',
			displayField : 'bankOrgName',
			mode : 'local',
			hiddenName : 'bank_org_id',
			editable : false,
			triggerAction : 'all',
			allowBlank : false,
			fieldLabel : '权限机构<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
			name : 'manageOrgSelector',
			id : 'manageOrgSelector',
			anchor : '75%'
		}]
	});

	function changeSelect() {
		if (store.getCount() > 0) {
			var combo = Ext.getCmp('manageOrgSelector');
			var value = store.getAt(0).get('bankOrgID');
			combo.setValue(value);
		}
	}

	this.load = function(m) {
		var combo = Ext.getCmp('manageOrgSelector');
		var bankOrgID = combo.getValue();
		store.load({
			params : {
				bank_org_id : bankOrgID,
				mode : m
			}
		});
	}

	this.initUI = function() {
		var div = Ext.getDom('manageOrgSelector').parentNode;
		var span1 = document.createElement("span");
		span1.style.marginLeft=20;
		span1.className = "span_left";
		span1.innerHTML = "<a href='javascript:manageOrgSelector.load(\"DrillUP\")'><img src="+ pathUrl + "/public/images/leftArrow.gif></a>";
		div.appendChild(span1);
		var span2 = document.createElement("span");
		span2.style.marginLeft=20;
		span2.className = "span_right";
		span2.innerHTML = "<a href='javascript:manageOrgSelector.load(\"DrillDown\")'><img src="+ pathUrl + "/public/images/rightArrow.gif></a>";
		div.appendChild(span2);
	}
}
Ext.extend(ManageOrgSelector, Ext.Panel);

// 状态selector
StatusSelector = function() {
	var store = new Ext.data.ArrayStore({
		fields : ['display', 'value'],
		data : [['正常', '00'], ['用户锁定', '01'], ['系统预锁定', '02'], ['系统锁定', '03']]
	});

	StatusSelector.superclass.constructor.call(this, {
		fieldLabel : '状态<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
		mode : 'local',
		editable : false,
		allowBlank : false,
		blankText : '不能为空',
		store : store,
		triggerAction : 'all',
		displayField : 'display',
		valueField : 'value',
		hiddenName : 'status_code',
		name : 'status_code',
		id : 'statusSelector',
		anchor : '91%'
	});
};
Ext.extend(StatusSelector, Ext.form.ComboBox);

// grid下拉框
GridSelector = function(obj) {
	var random = new Date().getMilliseconds();
	var ds = obj.ds; // 必需
	var width = obj.width ? obj.width : 300;
	var height = obj.height ? obj.height : 200;
	var anchor = obj.anchor ? obj.anchor : '90%';
	var valueField = obj.valueField; // 必需
	this.store = ds;
	obj.id = obj.id ? obj.id : random;
	obj.id = obj.id ? obj.id : 'GridSelector';
	obj.value = obj.value ? obj.value : "";
	this.fieldLabel = obj.fieldLabel; // 必需
	this.hidden = obj.hidden ? obj.hidden : false;
	this.displayField = obj.displayField ? obj.displayField : obj.fieldLabel;
	this.valueField = valueField;
	this.hiddenName = obj.name ? obj.name : valueField;
	this.name = obj.name ? obj.name : valueField;
	this.editable = obj.editable ? obj.editable : false;
	this.allowBlank = obj.allowBlank ? obj.allowBlank : false;
	this.width = obj.labelWidth ? obj.labelWidth : 190;
	this.anchor = anchor;
	ds.load({
		callback : obj.callback
	});
	var grid = {
		xtype : 'grid',
		width : width,
		height : height,
		columns : obj.cm,
		store : ds,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		viewConfig : {
			forceFit : true
		},
		listeners : {
			rowclick : function(grid, rowIndex, e) {
				showMenu.hide();
				var record = ds.getAt(rowIndex);
				Ext.getCmp(obj.id).setValue(record.get(valueField));
				if (obj.listeners) {
					var items = obj.itemIDs.split(",");
					for (var i = 0; i < items.length; i++) {
						var info = '';
						record.fields.each(function(c, d) {
							if (record.get(c.name) != '' && c.name != undefined)
								info += ',' + c.name + ':' + "'"
										+ record.get(c.name) + "'";
						});
						var gid = obj.id.substring(0, 4);
						if (info != '') {
							info = info.substring(1);
							info = '{' + info + '}';
						}
						if (Ext.getCmp(items[i])) {
							Ext.getCmp(items[i]).setValue(null);
							Ext.getCmp(items[i]).getStore().reload({
								params : {
									itemID : items[i],
									comboID : record.get(valueField),
									filter : info
								}
							});
						} else if (Ext.getCmp(gid + items[i])) {
							Ext.getCmp(gid + items[i]).setValue(null);
							Ext.getCmp(gid + items[i]).getStore().reload({
								params : {
									itemID : items[i],
									comboID : record.get(valueField),
									filter : info
								}
							});
						}
					}
				}
			}
		},
		tbar : ["关键字：", {
			xtype : 'textfield',
			name : 'keyWord',
			width : 100
		}, {
			xtype : 'button',
			iconCls : 'search',
			handler : function() {
				var keyWord = this.previousSibling().getValue();
				if (obj.searchName) {
					var params = {};
					params[obj.searchName] = keyWord; // 必需
					ds.load({
						params : params,
						callback : obj.callback
					});
				} else
					ds.load({
						params : {
							keyWord : keyWord
						},
						callback : obj.callback
					});
			}
		}]
	};
	if (obj.bbar)
		grid.bbar = {
			xtype : 'paging',
			pageSize : obj.pageSize ? obj.pageSize : 10,
			store : ds,
			displayInfo : true,
			displayMsg : '第{0}-{1}条记录,共{2}条',
			emptyMsg : "没有记录"
		};
	var showMenu = new Ext.menu.Menu({
		items : [grid]
	});

	GridSelector.superclass.constructor.call(this, {
		id : obj.id,
		mode : 'local',
		value : obj.value,
		blankText : '不能为空',
		store : ds,
		triggerAction : 'all'
	});
	this.expand = function() {
		if (this.menu == null) {
			this.menu = showMenu;
		}

		this.menu.show(this.el, "tl-bl?");
	};
};
Ext.extend(GridSelector, Ext.form.ComboBox);

/**
 * 月份选择下拉框
 */
MonthSelector = function(config) {
	
	var an = config.anchor ? config.anchor : '91%';
	var hiddenName = config.hiddenName ? config.hiddenName : 'month_id';
	var id = config.id ? config.id : 'monthId';
	var fieldLabel = config.fieldLabel ? config.fieldLabel : '月份';
	
	var store = new Ext.data.JsonStore({
		storeId : 'safd',
		url : 'selector_listMonth.action',
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
 * 归属条线
 */
BusiLineSelector = function(config){

	var hiddenName = config.hiddenName ? config.hiddenName : 'busi_line_code';
	var fieldLabel = config.fieldLabel ? config.fieldLabel : '归属条线';
	var id = config.id ? config.id : 'busiLineCode';
	//var an = config.anchor ? config.anchor : '91%';
	
	var store = new Ext.data.JsonStore({
		storeId : 'busiLineStoreId',
		url :pathUrl+ '/selector/queryBusiLineList',
		root : 'results',
		totalProperty : 'totalCount',
		fields : ['busi_line_code','busi_line_name']
	});
	
	store.on('load',changeSelect);
	store.load();
	
	BusiLineSelector.superclass.constructor.call(this,{
		store : store,
		valueField : 'busi_line_code',
		displayField : 'busi_line_name',
		mode : 'local',
		hiddenName : hiddenName,
		editable : false,
		triggerAction :'all',
		allowBlank : false,
		fieldLabel : fieldLabel,
		id : id,
		name : 'busiLineCode',
		anchor :  '91%'
	});
	
	function changeSelect(){
		if(store.getCount()>0){
			var combo = Ext.getCmp(id);
			var value = store.getAt(0).get("busi_line_code");
			combo.setValue(value);
		}
	}
}
Ext.extend(BusiLineSelector,Ext.form.ComboBox);

/**
 * 职位类型
 */
JobTypeCodeSelector = function(config){
	var hiddenName = config.hiddenName ? config.hiddenName : 'job_title_code';
	var id = config.id ? config.id : 'jobTitleCode';
	var fieldLabel = config.fieldLabel ? config.fieldLabel : '职位类型';
	
	var store = new Ext.data.JsonStore({
		id : 'jobTypeCodeStore',
		url : pathUrl + '/selector/queryJobTitle',
		root : 'results',
		totalProperty : 'totalCount',
		fields : ['job_title_code','job_title_name']
	});
	
	store.on('load',changeSelect);
	store.load();
	
	JobTypeCodeSelector.superclass.constructor.call(this,{
		id : id,
		hiddenName : hiddenName,
		valueField : 'job_title_code',
		displayField : 'job_title_name',
		store : store,
		fieldLabel : fieldLabel,
		editable : false,
		mode : 'local',
		triggerAction :'all',
		name : 'job_title_code',
		anchor :  '91%'
	});
	
	function changeSelect(){
		if(store.getCount()>0){
			var combo = Ext.getCmp(id);
			var value = store.getAt(0).get("job_title_code");
			combo.setValue(value);
		}
	}
}
Ext.extend(JobTypeCodeSelector,Ext.form.ComboBox);



/**
 * 机构树下拉框
 */
var MyCombo=function(changeFun){
    MyCombo.superclass.constructor.call(this,{
        id: 'myCombo',
		autoSelect:true,
		mode: 'local',
		triggerAction : "all",
		editable: false,
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
                width: 400,
                height: 300,
                bodyStyle: 'padding:2px;',
                rootVisible: true,
                root :getRootNode(bank_org_id, "["+bank_org_id+"]"+bank_org_name, expandBankTree),
                
                listeners: {
                    click: function(node){
                    	var comboStore = Ext.getCmp('myCombo').getStore();
                    	comboStore.removeAll();
                    	comboStore.insert(0,new Ext.data.Record({bank_org_id:node.id,bank_org_name:node.text}));
						Ext.getCmp('myCombo').setValue(node.text);
						this.ownerCt.hide();
						if(null != changeFun){
							changeFun(node.id);
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
Ext.extend(MyCombo,Ext.form.ComboBox);


/**
 * 性别
 */
GenderSelector = function(config){
	var hiddenName = 'gender_code';
	var id = 'gender';
	var fieldLabel = '性别';
	if(config){
		hiddenName = config.hiddenName ? config.hiddenName : 'genderCode';
		id = config.id ? config.id : 'gender_code';
		fieldLabel = config.fieldLabel ? config.fieldLabel : '性别';
	}
	
	var store = new Ext.data.JsonStore({
		id : 'genderSelector',
		url : pathUrl + '/selector/queryGender',
		root : 'results',
		totalProperty : 'totalCount',
		fields : ['gender_code','gender_name']
	});
	
	store.on('load',changeSelect);
	store.load();
	
	GenderSelector.superclass.constructor.call(this,{
		id : id,
		hiddenName : hiddenName,
		valueField : 'gender_code',
		displayField : 'gender_name',
		store : store,
		fieldLabel : fieldLabel,
		editable : false,
		mode : 'local',
		triggerAction :'all',
		name : 'genderCode',
		anchor :  '91%'
	});
	
	function changeSelect(){
		if(store.getCount()>0){
			var combo = Ext.getCmp(id);
			var value = store.getAt(0).get("gender_code");
			combo.setValue(value);
		}
	}
}
Ext.extend(GenderSelector,Ext.form.ComboBox);



/**
 * 民族
 */
EthnicitySelector = function(config){
	var hiddenName = 'ethnicity_code';
	var id = 'ethnicity';
	var fieldLabel = '民族';
	if(config){
		hiddenName = config.hiddenName ? config.hiddenName : hiddenName;
		id = config.id ? config.id : id;
		fieldLabel = config.fieldLabel ? config.fieldLabel : fieldLabel;
	}
	
	var store = new Ext.data.JsonStore({
		id : 'ethnicitySelector',
		url : pathUrl + '/selector/queryEthnicity',
		root : 'results',
		totalProperty : 'totalCount',
		fields : ['ethnicity_code','ethnicity_name']
	});
	
	store.on('load',changeSelect);
	store.load();
	
	EthnicitySelector.superclass.constructor.call(this,{
		id : id,
		hiddenName : hiddenName,
		valueField : 'ethnicity_code',
		displayField : 'ethnicity_name',
		store : store,
		fieldLabel : fieldLabel,
		editable : false,
		mode : 'local',
		triggerAction :'all',
		name : 'ethnicityCode',
		anchor :  '91%'
	});
	
	function changeSelect(){
		if(store.getCount()>0){
			var combo = Ext.getCmp(id);
			var value = store.getAt(0).get("ethnicity_code");
			combo.setValue(value);
		}
	}
}
Ext.extend(EthnicitySelector,Ext.form.ComboBox);


/**
 * 学历
 */
EduBackSelector = function(config){
	var hiddenName = 'edu_back_code';
	var id = 'eduBack';
	var fieldLabel = '学历';
	if(config){
		hiddenName = config.hiddenName ? config.hiddenName : hiddenName;
		id = config.id ? config.id : id;
		fieldLabel = config.fieldLabel ? config.fieldLabel : fieldLabel;
	}
	
	var store = new Ext.data.JsonStore({
		id : 'eduBackSelector',
		url : pathUrl + '/selector/queryEduBack',
		root : 'results',
		totalProperty : 'totalCount',
		fields : ['file_edu_back_code','file_edu_back_name']
	});
	
	store.on('load',changeSelect);
	store.load();
	
	EduBackSelector.superclass.constructor.call(this,{
		id : id,
		hiddenName : hiddenName,
		valueField : 'file_edu_back_code',
		displayField : 'file_edu_back_name',
		store : store,
		fieldLabel : fieldLabel,
		editable : false,
		mode : 'local',
		triggerAction :'all',
		name : 'eduBackCode',
		anchor :  '91%'
	});
	
	function changeSelect(){
		if(store.getCount()>0){
			var combo = Ext.getCmp(id);
			var value = store.getAt(0).get("file_edu_back_code");
			combo.setValue(value);
		}
	}
}
Ext.extend(EduBackSelector,Ext.form.ComboBox);


/**
 * 序列
 */
CustJobSeqSelector = function(config){
	var hiddenName = 'job_seq_code';
	var id = 'jobSeqCode';
	var fieldLabel = '序列';
	if(config){
		hiddenName = config.hiddenName ? config.hiddenName : hiddenName;
		id = config.id ? config.id : id;
		fieldLabel = config.fieldLabel ? config.fieldLabel : fieldLabel;
	}
	
	var store = new Ext.data.JsonStore({
		id : 'custJobSeqSelector',
		url : pathUrl + '/selector/queryCustJobSeq',
		root : 'results',
		totalProperty : 'totalCount',
		fields : ['job_seq_code','job_seq_name']
	});
	
	store.on('load',changeSelect);
	store.load();
	
	CustJobSeqSelector.superclass.constructor.call(this,{
		id : id,
		hiddenName : hiddenName,
		valueField : 'job_seq_code',
		displayField : 'job_seq_name',
		store : store,
		fieldLabel : fieldLabel,
		editable : false,
		mode : 'local',
		triggerAction :'all',
		name : 'jobSeqCode',
		anchor :  '91%'
	});
	
	function changeSelect(){
		if(store.getCount()>0){
			var combo = Ext.getCmp(id);
			var value = store.getAt(0).get("job_seq_code");
			combo.setValue(value);
		}
	}
}
Ext.extend(CustJobSeqSelector,Ext.form.ComboBox);

/**
 * 岗位 
 */
PostTypeSelector = function(config){
	var hiddenName = 'post_type_code';
	var id = 'postType';
	var fieldLabel = '岗位';
	if(config){
		hiddenName = config.hiddenName ? config.hiddenName : hiddenName;
		id = config.id ? config.id : id;
		fieldLabel = config.fieldLabel ? config.fieldLabel : fieldLabel;
	}
	
	var store = new Ext.data.JsonStore({
		id : 'postTypeSelector',
		url : pathUrl + '/selector/queryPostType',
		root : 'results',
		totalProperty : 'totalCount',
		fields : ['post_type_code','post_type_name']
	});
	
	store.on('load',changeSelect);
	store.load();
	
	PostTypeSelector.superclass.constructor.call(this,{
		id : id,
		hiddenName : hiddenName,
		valueField : 'post_type_code',
		displayField : 'post_type_name',
		store : store,
		fieldLabel : fieldLabel,
		editable : false,
		mode : 'local',
		triggerAction :'all',
		name : 'postTypeCode',
		anchor :  '91%'
	});
	
	function changeSelect(){
		if(store.getCount()>0){
			var combo = Ext.getCmp(id);
			var value = store.getAt(0).get("post_type_code");
			combo.setValue(value);
		}
	}
}
Ext.extend(PostTypeSelector,Ext.form.ComboBox);

/**
 * 指标体系树
 */
MeasureTreeSelector = function(config){
	var hiddenName = 'measure_tree_code';
	var id = 'measureTreeCode';
	var fieldLabel = '指标体系';
	var anchor = '91%';
	
	if(config){
		hiddenName = config.hiddenName ? config.hiddenName : hiddenName;
		id = config.id ? config.id : id;
		fieldLabel = config.fieldLabel ? config.fieldLabel : fieldLabel;
		anchor = config.anchor ? config.anchor : anchor;
	}
	
	var store = new Ext.data.JsonStore({
		id : 'measureTreeSelector',
		url : pathUrl + '/selector/queryMeasureTree',
		root : 'results',
		totalProperty : 'totalCount',
		fields : ['measure_tree_code','measure_tree_name']
	});
	
	store.on('load',changeSelect);
	store.load();
	
	MeasureTreeSelector.superclass.constructor.call(this,{
		id : id,
		hiddenName : hiddenName,
		valueField : 'measure_tree_code',
		displayField : 'measure_tree_name',
		store : store,
		fieldLabel : fieldLabel,
		editable : false,
		mode : 'local',
		triggerAction :'all',
		name : 'measureTreeCode',
		anchor :  anchor
	});
	
	function changeSelect(){
		if(store.getCount()>0){
			var combo = Ext.getCmp(id);
			var value = store.getAt(0).get("measure_tree_code");
			combo.setValue(value);
		}
	}
}
Ext.extend(MeasureTreeSelector,Ext.form.ComboBox);


BankOrgTypeSelector=function(){
	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: pathUrl+'/selector/queryBankOrgType'
		}),
		reader: new Ext.data.JsonReader({
			root: 'results'
		},
		[{name: 'bank_org_type_code', mapping:'bank_org_type_code'},
		 {name: 'bank_org_type_name', mapping:'bank_org_type_name'}]),
		remoteSort: false
	});
	
	store.on('load',changeSelect);
	store.load();
	
	BankOrgTypeSelector.superclass.constructor.call(this,{
		store: store,
		valueField :'bank_org_type_code',
		displayField:'bank_org_type_name',
		mode: 'local',
		hiddenName:'bank_org_type_code',
		editable: false,
		triggerAction: 'all',
		allowBlank:false,
		fieldLabel:'机构类型',
		name: 'bankOrgTypeCode',
		id: 'bankOrgTypeCode',
	    anchor:'90%'
	});
	
	function changeSelect()
	{
		if(store.getCount()>0)
		{
			var combo=Ext.getCmp('bankOrgTypeCode');
			var value=store.getAt(0).get('bank_org_type_code');
			combo.setValue(value);
		}
	}
}
Ext.extend(BankOrgTypeSelector, Ext.form.ComboBox);

/**
 * 数据库类型
 */
UppDatabaseTypeSelector = function(config){
	var hiddenName = 'database_type_id';
	var id = 'databaseTypeId';
	var fieldLabel = '数据库类型';
	if(config){
		hiddenName = config.hiddenName ? config.hiddenName : hiddenName;
		id = config.id ? config.id : id;
		fieldLabel = config.fieldLabel ? config.fieldLabel : fieldLabel;
	}
	
	var store=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:pathUrl+'/selector/queryUppDatabaseType'
		}),
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [{
			name : 'database_type_id',
			mapping : 'database_type_id'
		}, {
			name : 'database_type_name',
			mapping : 'database_type_name'
		}]),
		remoteSort : false
	   });
	store.on('load',changeSelect);
	store.load();
	
	UppDatabaseTypeSelector.superclass.constructor.call(this,{
		id : id,
		hiddenName : hiddenName,
		valueField : 'database_type_id',
		displayField : 'database_type_name',
		store : store,
		fieldLabel : fieldLabel,
		editable : false,
		mode : 'local',
		triggerAction :'all',
		name : 'databaseTypeId',
		anchor :  '95%'
	});
	
	function changeSelect(){
		
		if(store.getCount()>0){
			var combo = Ext.getCmp(id);
			var value = store.getAt(0).get("database_type_id");
			combo.setValue(value);
		}
	}
}
Ext.extend(UppDatabaseTypeSelector,Ext.form.ComboBox);

/***
 * 按钮功能列表
 */
UppButtonFunctionSelector = function(config){
	var hiddenName = 'button_func_cd';
	var id = 'buttonFuncCd';
	var fieldLabel = '按钮功能';
	var anchor = '91%';
	if(config){
		hiddenName = config.hiddenName ? config.hiddenName : hiddenName;
		id = config.id ? config.id : id;
		fieldLabel = config.fieldLabel ? config.fieldLabel : fieldLabel;
		anchor = config.anchor ? config.anchor : anchor;
	}
	
	var store=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:pathUrl+'/selector/queryUppButtonFunction'
		}),
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [{
			name : 'button_func_cd',
			mapping : 'button_func_cd'
		}, {
			name : 'button_func_name',
			mapping : 'button_func_name'
		}]),
		remoteSort : false
	   });
	store.on('load',changeSelect);
	store.load();
	
	UppButtonFunctionSelector.superclass.constructor.call(this,{
		id : id,
		hiddenName : hiddenName,
		valueField : 'button_func_cd',
		displayField : 'button_func_name',
		store : store,
		fieldLabel : fieldLabel,
		editable : false,
		mode : 'local',
		triggerAction :'all',
		name : 'buttonFuncCd',
		anchor :  anchor
	});
	
	function changeSelect(){
		if(store.getCount()>0){
			var combo = Ext.getCmp(id);
			var value = store.getAt(0).get("button_func_cd");
			combo.setValue(value);
		}
	}
}
Ext.extend(UppButtonFunctionSelector,Ext.form.ComboBox);

/***
 * 维度列表 
 */
UppDimSourceSelector = function(config){
	var hiddenName = 'dim_cd';
	var id = 'dimCd';
	var fieldLabel = '维度';
	var anchor = '91%';
	var isDisable = true;
	if(config){
		hiddenName = config.hiddenName ? config.hiddenName : hiddenName;
		id = config.id ? config.id : id;
		fieldLabel = config.fieldLabel ? config.fieldLabel : fieldLabel;
		anchor = config.anchor ? config.anchor : anchor;
		isDisable = config.isDisable ? config.isDisable : isDisable;
	}
	
	var store=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:pathUrl+'/selector/queryUppDimSource'
		}),
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [{
			name : 'dim_cd',
			mapping : 'dim_cd'
		}, {
			name : 'dim_name',
			mapping : 'dim_name'
		}]),
		remoteSort : false
	   });
	store.on('load',changeSelect);
	store.load();
	UppDimSourceSelector.superclass.constructor.call(this,{
		id : id,
		hiddenName : hiddenName,
		valueField : 'dim_cd',
		displayField : 'dim_name',
		store : store,
		fieldLabel : fieldLabel,
		editable : false,
		mode : 'local',
		triggerAction :'all',
		name : 'dim_cd',
		anchor :  anchor,
		disabled:isDisable
	});
	
	this.loadDimData = function(isTree) {
		var is_tree = isTree ? 'Y' : 'N';
		this.store.load({
			params : {
				is_tree : is_tree
			}
		})
	};
	
	function changeSelect(){
		
		if(store.getCount()>0){
			var combo = Ext.getCmp(id);
			var value = store.getAt(0).get("dim_cd");
			combo.setValue(value);
		}
	}
}
Ext.extend(UppDimSourceSelector,Ext.form.ComboBox);

/***
 * 关联元数据列表 

UppPageFieldSelector = function(config){
	var 
	var hiddenName = 'rela_metadata_id';
	var id = 'relaMetadataId';
	var fieldLabel = '关联元数据';
	var anchor = '91%';
	if(config){
		hiddenName = config.hiddenName ? config.hiddenName : hiddenName;
		id = config.id ? config.id : id;
		fieldLabel = config.fieldLabel ? config.fieldLabel : fieldLabel;
		anchor = config.anchor ? config.anchor : anchor;
	}
	
	var store=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:pathUrl+'/selector/queryRelaMetadata'
		}),
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [{
			name : 'rela_metadata_id',
			mapping : 'metadata_id'
		}, {
			name : 'rela_metadata_name',
			mapping : 'metadata_name'
		}]),
		remoteSort : false
	   });
	store.on('load',changeSelect);
	store.load();
	
	UppPageFieldSelector.superclass.constructor.call(this,{
		id : id,
		hiddenName : hiddenName,
		valueField : 'rela_metadata_id',
		displayField : 'rela_metadata_name',
		store : store,
		fieldLabel : fieldLabel,
		editable : false,
		mode : 'local',
		triggerAction :'all',
		name : 'relaMetadataId',
		anchor :  anchor
	});
	
	function changeSelect(){
		
		if(store.getCount()>0){
			var combo = Ext.getCmp(id);
			var value = store.getAt(0).get("rela_metadata_id");
			combo.setValue(value);
		}
	}
}
Ext.extend(UppPageFieldSelector,Ext.form.ComboBox);

 */
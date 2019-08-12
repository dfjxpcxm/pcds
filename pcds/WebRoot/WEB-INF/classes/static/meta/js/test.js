Ext.onReady(function(){templet_id='up20141209945310000';cl201412031000130378_country_cdStore= new Ext.data.Store({
 proxy: new Ext.data.HttpProxy({
url : pathUrl + '/bckTrackAjax/getLinkData/COUNTRY'
 }),
autoLoad : false,
reader :cl201412031000130378Reader = new Ext.data.JsonReader({
root : 'results',
totalProperty : 'totalCount'
},
[
{name :'std_cd_desc',mapping : 'std_cd_desc',type :'string'},
{name :'std_cd',mapping : 'std_cd',type :'string'}
,{name :'task_id',mapping :'task_id'},{name :'tmpl_id',mapping :'tmpl_id'},{name :'apply_date',mapping :'apply_date'},{name :'status_code',mapping :'status_code'}]),remoteSort: false
})
;cl201412031000130378_country_cdStore.load();cl201412031000130416_int_type_flagStore= new Ext.data.Store({
 proxy: new Ext.data.HttpProxy({
url : pathUrl + '/bckTrackAjax/getLinkData/INT_TYPE'
 }),
autoLoad : false,
reader :cl201412031000130416Reader = new Ext.data.JsonReader({
root : 'results',
totalProperty : 'totalCount'
},
[
{name :'int_type_desc',mapping : 'int_type_desc',type :'string'},
{name :'int_type_flag',mapping : 'int_type_flag',type :'string'}
,{name :'task_id',mapping :'task_id'},{name :'tmpl_id',mapping :'tmpl_id'},{name :'apply_date',mapping :'apply_date'},{name :'status_code',mapping :'status_code'}]),remoteSort: false
})
;cl201412031000130416_int_type_flagStore.load();cl201412031000130412_int_cate_cdStore= new Ext.data.Store({
 proxy: new Ext.data.HttpProxy({
url : pathUrl + '/bckTrackAjax/getLinkData/INT_CALC'
 }),
autoLoad : false,
reader :cl201412031000130412Reader = new Ext.data.JsonReader({
root : 'results',
totalProperty : 'totalCount'
},
[
{name :'int_calc_desc',mapping : 'int_calc_desc',type :'string'},
{name :'int_calc_cd',mapping : 'int_calc_cd',type :'string'}
,{name :'task_id',mapping :'task_id'},{name :'tmpl_id',mapping :'tmpl_id'},{name :'apply_date',mapping :'apply_date'},{name :'status_code',mapping :'status_code'}]),remoteSort: false
})
;cl201412031000130412_int_cate_cdStore.load();cl201412031000130400_curr_cdStore= new Ext.data.Store({
 proxy: new Ext.data.HttpProxy({
url : pathUrl + '/bckTrackAjax/getLinkData/currency'
 }),
autoLoad : false,
reader :cl201412031000130400Reader = new Ext.data.JsonReader({
root : 'results',
totalProperty : 'totalCount'
},
[
{name :'currency_desc',mapping : 'currency_desc',type :'string'},
{name :'currency_code',mapping : 'currency_code',type :'string'}
,{name :'task_id',mapping :'task_id'},{name :'tmpl_id',mapping :'tmpl_id'},{name :'apply_date',mapping :'apply_date'},{name :'status_code',mapping :'status_code'}]),remoteSort: false
})
;cl201412031000130400_curr_cdStore.load();cl201412ZB933310002_int_pay_freq_cdStore= new Ext.data.Store({
 proxy: new Ext.data.HttpProxy({
url : pathUrl + '/bckTrackAjax/getLinkData/INT_PAY_FR'
 }),
autoLoad : false,
reader :cl201412ZB933310002Reader = new Ext.data.JsonReader({
root : 'results',
totalProperty : 'totalCount'
},
[
{name :'ip_fr_desc',mapping : 'ip_fr_desc',type :'string'},
{name :'ip_fr_cd',mapping : 'ip_fr_cd',type :'string'}
,{name :'task_id',mapping :'task_id'},{name :'tmpl_id',mapping :'tmpl_id'},{name :'apply_date',mapping :'apply_date'},{name :'status_code',mapping :'status_code'}]),remoteSort: false
})
;cl201412ZB933310002_int_pay_freq_cdStore.load();cl201412031000130382_cust_cate_cdStore= new Ext.data.Store({
 proxy: new Ext.data.HttpProxy({
url : pathUrl + '/bckTrackAjax/getLinkData/CUST_TYPE'
 }),
autoLoad : false,
reader :cl201412031000130382Reader = new Ext.data.JsonReader({
root : 'results',
totalProperty : 'totalCount'
},
[
{name :'cust_type_desc',mapping : 'cust_type_desc',type :'string'},
{name :'cust_type_cd',mapping : 'cust_type_cd',type :'string'}
,{name :'task_id',mapping :'task_id'},{name :'tmpl_id',mapping :'tmpl_id'},{name :'apply_date',mapping :'apply_date'},{name :'status_code',mapping :'status_code'}]),remoteSort: false
})
;cl201412031000130382_cust_cate_cdStore.load();var dataView = new Ext.Viewport({
layout : 'fit',
items : [
{
xtype:'panel',
layout:'border',
items:[{
layout:'border',
region:'center',
items:[
dataGridup20141209945310000Grid = new Ext.grid.GridPanel({
region : 'center',
ds : dataStore= new Ext.data.Store({
 proxy: new Ext.data.HttpProxy({
url : pathUrl + '/bckTrackAjax/getMetaData'
 }),
autoLoad : false,
baseParams : {
start : 0,
limit : 30
},
reader :dataup20141209945310000Reader = new Ext.data.JsonReader({
root : 'results',
totalProperty : 'totalCount'
},
[
{name :'cust_id',mapping : 'cust_id',type :'string'},
{name :'cust_name',mapping : 'cust_name',type :'string'},
{name :'country_cd',mapping : 'country_cd',type :'string'},
{name :'institution_cd',mapping : 'institution_cd',type :'string'},
{name :'cust_cate_cd',mapping : 'cust_cate_cd',type :'string'},
{name :'inner_rating',mapping : 'inner_rating',type :'string'},
{name :'outer_rating',mapping : 'outer_rating',type :'string'},
{name :'finance_license',mapping : 'finance_license',type :'string'},
{name :'acct_id',mapping : 'acct_id',type :'string'},
{name :'biz_prod_no',mapping : 'biz_prod_no',type :'string'},
{name :'biz_prod_name',mapping : 'biz_prod_name',type :'string'},
{name :'contract_no',mapping : 'contract_no',type :'string'},
{name :'acct_prod_id',mapping : 'acct_prod_id',type :'string'},
{name :'acct_prod_name',mapping : 'acct_prod_name',type :'string'},
{name :'curr_cd',mapping : 'curr_cd',type :'string'},
{name :'int_start_date',mapping : 'int_start_date',type :'string'},
{name :'int_due_date',mapping : 'int_due_date',type :'string'},
{name :'trans_amt',mapping : 'trans_amt',type :'string'},
{name :'balance',mapping : 'balance',type :'string'},
{name :'int_cate_cd',mapping : 'int_cate_cd',type :'string'},
{name :'yearly_int_rate',mapping : 'yearly_int_rate',type :'string'},
{name :'int_type_flag',mapping : 'int_type_flag',type :'string'},
{name :'finance_org_id',mapping : 'finance_org_id',type :'string'},
{name :'finance_org_name',mapping : 'finance_org_name',type :'string'},
{name :'cust_mgr_id',mapping : 'cust_mgr_id',type :'string'},
{name :'cust_mgr_name',mapping : 'cust_mgr_name',type :'string'},
{name :'cust_mgr_org_id',mapping : 'cust_mgr_org_id',type :'string'},
{name :'biz_line_id',mapping : 'biz_line_id',type :'string'},
{name :'acct_name',mapping : 'acct_name',type :'string'},
{name :'biz_type_cd',mapping : 'biz_type_cd',type :'string'},
{name :'int_pay_freq_cd',mapping : 'int_pay_freq_cd',type :'string'},
{name :'biz_date',mapping : 'biz_date',type :'string'},
{name :'next_pay_int_date',mapping : 'next_pay_int_date',type :'string'},
{name :'term',mapping : 'term',type :'string'},
{name :'trans_opponent',mapping : 'trans_opponent',type :'string'},
{name :'rtn_acct_id',mapping : 'rtn_acct_id',type :'string'},
{name :'rtn_acct_name',mapping : 'rtn_acct_name',type :'string'},
{name :'rtn_bank_no',mapping : 'rtn_bank_no',type :'string'},
{name :'contact_name',mapping : 'contact_name',type :'string'},
{name :'contact_tel',mapping : 'contact_tel',type :'string'},
{name :'contact_addr',mapping : 'contact_addr',type :'string'},
{name :'contact_email',mapping : 'contact_email',type :'string'},
{name :'business_no',mapping : 'business_no',type :'string'}
,{name :'task_id',mapping :'task_id'},{name :'tmpl_id',mapping :'tmpl_id'},{name :'apply_date',mapping :'apply_date'},{name :'status_code',mapping :'status_code'}]),remoteSort: false
})
,
cm : dataup20141209945310000Cm = new Ext.grid.ColumnModel([
 winRoleSm = new Ext.grid.CheckboxSelectionModel(),
{id :'cust_id',header : '客户号',hidden:false,dataIndex :'cust_id'},
{id :'cust_name',header : '客户名称',hidden:false,dataIndex :'cust_name'},
{id :'country_cd',header : '国别',hidden:false,dataIndex :'country_cd'},
{id :'institution_cd',header : '组织机构代码',hidden:false,dataIndex :'institution_cd'},
{id :'cust_cate_cd',header : '客户类别',hidden:false,dataIndex :'cust_cate_cd'},
{id :'inner_rating',header : '内部评级',hidden:true,dataIndex :'inner_rating'},
{id :'outer_rating',header : '外部评级',hidden:true,dataIndex :'outer_rating'},
{id :'finance_license',header : '金融许可证代码',hidden:true,dataIndex :'finance_license'},
{id :'acct_id',header : '账号',hidden:false,dataIndex :'acct_id'},
{id :'biz_prod_no',header : '产品编号',hidden:true,dataIndex :'biz_prod_no'},
{id :'biz_prod_name',header : '产品名称',hidden:true,dataIndex :'biz_prod_name'},
{id :'contract_no',header : '合同编号',hidden:false,dataIndex :'contract_no'},
{id :'acct_prod_id',header : '科目',hidden:false,dataIndex :'acct_prod_id'},
{id :'acct_prod_name',header : '科目名称',hidden:true,dataIndex :'acct_prod_name'},
{id :'curr_cd',header : '币种',hidden:false,dataIndex :'curr_cd'},
{id :'int_start_date',header : '起息日',hidden:false,dataIndex :'int_start_date'},
{id :'int_due_date',header : '到息日',hidden:false,dataIndex :'int_due_date'},
{id :'trans_amt',header : '交易金额',hidden:false,dataIndex :'trans_amt',renderer : fmoney},
{id :'balance',header : '账户余额',hidden:false,dataIndex :'balance',renderer : fmoney},
{id :'int_cate_cd',header : '计息方式代码',hidden:false,dataIndex :'int_cate_cd'},
{id :'yearly_int_rate',header : '年利率%',hidden:false,dataIndex :'yearly_int_rate'},
{id :'int_type_flag',header : '利率类型',hidden:false,dataIndex :'int_type_flag'},
{id :'finance_org_id',header : '入账机构',hidden:false,dataIndex :'finance_org_id'},
{id :'finance_org_name',header : '入账机构名称',hidden:true,dataIndex :'finance_org_name'},
{id :'cust_mgr_id',header : '客户经理号',hidden:false,dataIndex :'cust_mgr_id'},
{id :'cust_mgr_name',header : '客户经理姓名',hidden:true,dataIndex :'cust_mgr_name'},
{id :'cust_mgr_org_id',header : '客户经理机构',hidden:false,dataIndex :'cust_mgr_org_id'},
{id :'biz_line_id',header : '业务条线',hidden:false,dataIndex :'biz_line_id'},
{id :'acct_name',header : '账户名称',hidden:false,dataIndex :'acct_name'},
{id :'biz_type_cd',header : '业务关系类型',hidden:false,dataIndex :'biz_type_cd'},
{id :'int_pay_freq_cd',header : '付息频率',hidden:false,dataIndex :'int_pay_freq_cd'},
{id :'biz_date',header : '业务日期',hidden:false,dataIndex :'biz_date'},
{id :'next_pay_int_date',header : '下一个付息日',hidden:false,dataIndex :'next_pay_int_date'},
{id :'term',header : '期限(天)',hidden:false,dataIndex :'term'},
{id :'trans_opponent',header : '交易对手',hidden:false,dataIndex :'trans_opponent'},
{id :'rtn_acct_id',header : '回款账号',hidden:false,dataIndex :'rtn_acct_id'},
{id :'rtn_acct_name',header : '回款户名',hidden:false,dataIndex :'rtn_acct_name'},
{id :'rtn_bank_no',header : '回款大额行号',hidden:false,dataIndex :'rtn_bank_no'},
{id :'contact_name',header : '联系人名称',hidden:false,dataIndex :'contact_name'},
{id :'contact_tel',header : '联系人电话',hidden:false,dataIndex :'contact_tel'},
{id :'contact_addr',header : '联系人地址',hidden:false,dataIndex :'contact_addr'},
{id :'contact_email',header : '联系人邮箱',hidden:false,dataIndex :'contact_email'},
{id :'business_no',header : '业务编号',hidden:true,dataIndex :'business_no'}
,{id :'task_id',header : '任务编号',dataIndex :'task_id',hidden : true},{id :'tmpl_id',header : '模板编号',dataIndex :'tmpl_id',hidden : true},{id :'apply_date',header : '申请时间',dataIndex :'apply_date'},{id :'status_code',header : '状态',dataIndex :'status_code',renderer : approvetype}
]),
sm:winRoleSm,
loadMask : true,
id : 'dataGridup20141209945310000',
disabled : false,
hidden : false,
border : true,
hidden : false,
readOnly : false,
closable : true,
frame : false,
autoScroll : true,
tbar : [{
disabled : false,
hidden : false,
text : '新增',
iconCls : 'add',
handler : function (){
doAddDataup20141209945310000();
}
},'-',
{
disabled : false,
hidden : false,
text : '编辑',
iconCls : 'edit',
handler : function (){
doEditDataup20141209945310000();
}
},'-',
{
disabled : false,
hidden : false,
text : '删除',
iconCls : 'delete',
handler : function (){
doDelDataup20141209945310000();
}
},'-',
{
disabled : false,
hidden : false,
text : '提交',
iconCls : 'submit',
handler : function (){
submitApprove()
}
},'-',
{
disabled : false,
hidden : false,
text : '撤回',
iconCls : 'back',
handler : function (){
backApprove()
}
}
]
})
,
{
xtype:'tabpanel',
height:200,
region:'south',
activeTab:0,
items:[
dataGridup201412102111480043Grid = new Ext.grid.GridPanel({
region : 'center',
ds : dataup201412102111480043Store= new Ext.data.Store({
 proxy: new Ext.data.HttpProxy({
url : pathUrl + '/bckTrackAjax/getMetaData'
 }),
autoLoad : false,
baseParams : {
start : 0,
limit : 30
},
reader :dataup201412102111480043Reader = new Ext.data.JsonReader({
root : 'results',
totalProperty : 'totalCount'
},
[
{name :'acct_prod_id',mapping : 'acct_prod_id',type :'string'},
{name :'business_no',mapping : 'business_no',type :'string'},
{name :'end_date',mapping : 'end_date',type :'string'},
{name :'alloc_rate',mapping : 'alloc_rate',type :'string'},
{name :'begin_date',mapping : 'begin_date',type :'string'},
{name :'cust_mgr_id',mapping : 'cust_mgr_id',type :'string'},
{name :'acct_id',mapping : 'acct_id',type :'string'}
,{name :'task_id',mapping :'task_id'},{name :'tmpl_id',mapping :'tmpl_id'},{name :'apply_date',mapping :'apply_date'},{name :'status_code',mapping :'status_code'}]),remoteSort: false
})
,
cm : dataup201412102111480043Cm = new Ext.grid.ColumnModel([
 winRoleSm = new Ext.grid.CheckboxSelectionModel(),
{id :'acct_prod_id',header : '科目',hidden:false,dataIndex :'acct_prod_id'},
{id :'business_no',header : '业务编号',hidden:false,dataIndex :'business_no'},
{id :'end_date',header : '结束日期',hidden:false,dataIndex :'end_date'},
{id :'alloc_rate',header : '分配比例',hidden:false,dataIndex :'alloc_rate'},
{id :'begin_date',header : '开始日期',hidden:false,dataIndex :'begin_date'},
{id :'cust_mgr_id',header : '客户经理员工号',hidden:false,dataIndex :'cust_mgr_id'},
{id :'acct_id',header : '账号',hidden:false,dataIndex :'acct_id'}

]),
sm:winRoleSm,
loadMask : true,
id : 'dataGridup201412102111480043',
disabled : false,
hidden : false,
title : '账户客户经理关系表',
border : true,
hidden : false,
readOnly : false,
closable : false,
frame : false,
autoScroll : true,
tbar : [{
disabled : false,
hidden : false,
text : '新增',
iconCls : 'add',
handler : function (){
doAddDataup201412102111480043();
}
},'-',
{
disabled : false,
hidden : false,
text : '编辑',
iconCls : 'edit',
handler : function (){
doEditDataup201412102111480043();
}
},'-',
{
disabled : false,
hidden : false,
text : '删除',
iconCls : 'delete',
handler : function (){
doDelDataup201412102111480043();
}
}
]
})
,dataGridup201412102111070042Grid = new Ext.grid.GridPanel({
region : 'center',
ds : dataup201412102111070042Store= new Ext.data.Store({
 proxy: new Ext.data.HttpProxy({
url : pathUrl + '/bckTrackAjax/getMetaData'
 }),
autoLoad : false,
baseParams : {
start : 0,
limit : 30
},
reader :dataup201412102111070042Reader = new Ext.data.JsonReader({
root : 'results',
totalProperty : 'totalCount'
},
[
{name :'business_no',mapping : 'business_no',type :'string'},
{name :'acct_id',mapping : 'acct_id',type :'string'},
{name :'trans_id',mapping : 'trans_id',type :'string'},
{name :'trans_date',mapping : 'trans_date',type :'string'},
{name :'dc_flag',mapping : 'dc_flag',type :'string'},
{name :'trans_amt',mapping : 'trans_amt',type :'string'}
,{name :'task_id',mapping :'task_id'},{name :'tmpl_id',mapping :'tmpl_id'},{name :'apply_date',mapping :'apply_date'},{name :'status_code',mapping :'status_code'}]),remoteSort: false
})
,
cm : dataup201412102111070042Cm = new Ext.grid.ColumnModel([
 winRoleSm = new Ext.grid.CheckboxSelectionModel(),
{id :'business_no',header : '业务编号',hidden:false,dataIndex :'business_no'},
{id :'acct_id',header : '账号',hidden:false,dataIndex :'acct_id'},
{id :'trans_id',header : '交易序号',hidden:false,dataIndex :'trans_id'},
{id :'trans_date',header : '交易日期',hidden:false,dataIndex :'trans_date'},
{id :'dc_flag',header : '借贷标识',hidden:false,dataIndex :'dc_flag'},
{id :'trans_amt',header : '交易金额',hidden:false,dataIndex :'trans_amt',renderer : fmoney}

]),
sm:winRoleSm,
loadMask : true,
id : 'dataGridup201412102111070042',
disabled : false,
hidden : false,
title : '同业交易表',
border : true,
hidden : false,
readOnly : false,
closable : false,
frame : false,
autoScroll : true,
tbar : [{
disabled : false,
hidden : false,
text : '新增',
iconCls : 'add',
handler : function (){
doAddDataup201412102111070042();
}
},'-',
{
disabled : false,
hidden : false,
text : '编辑',
iconCls : 'edit',
handler : function (){
doEditDataup201412102111070042();
}
},'-',
{
disabled : false,
hidden : false,
text : '删除',
iconCls : 'delete',
handler : function (){
doDelDataup201412102111070042();
}
},'-',
{
disabled : false,
hidden : false,
text : '导入',
iconCls : 'importData',
handler : function (){
doImportDataup201412102111070042();
}
}
]
})
]}

]},queryForm = new Ext.form.FormPanel({
region : 'east',
labelWidth : 70,
labelAlign : 'left',
labelSepaator : ':',
collapsible : true,
split : true,
id : 'query',
width : 270,
disabled : false,
hidden : false,
title : '设置查询条件',
layout : 'form',
border : true,
hidden : false,
readOnly : false,
closable : false,
frame : false,
autoScroll : true,
bodyStyle : 'padding:5px 10px 0 10px',
items : [
cl201412031000130376_cust_nameField = new Ext.form.TextField({
fieldLabel : '客户名称',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130376_cust_name',
realName : 'cust_name',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
,
cl201412031000130370_acct_idField = new Ext.form.TextField({
fieldLabel : '账号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130370_acct_id',
realName : 'acct_id',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
,
new TreeCombo({
name:'cl201412031000130396_acct_prod_id',
label:'科目',
rootId:'2012',
rootName:'同业存放',
realName:'null',
dim_code:'fin_prod',
readOnly:false,
hidden:false,
allowBlank:true
})
,
new TreeCombo({
name:'cl201412031000120356_finance_org_id',
label:'入账机构',
rootId:'8888',
rootName:'总行',
realName:'null',
dim_code:'bankOrgId',
readOnly:false,
hidden:false,
allowBlank:true
})
,
cl201412031000130360_cust_mgr_idField = new Ext.form.TextField({
fieldLabel : '客户经理号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130360_cust_mgr_id',
realName : 'cust_mgr_id',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
,
new TreeCombo({
name:'cl201412031000130364_cust_mgr_org_id',
label:'客户经理机构',
rootId:'8888',
rootName:'总行',
realName:'null',
dim_code:'bankOrgId',
readOnly:false,
hidden:false,
allowBlank:true
})

],
buttonAlign : 'center',
buttons : [ {
xtype : 'button',
disabled : false,
hidden : false,
text : '查询',
iconCls : 'query',
handler : function (){
doQuery();
}
}
]

,listeners:{actioncomplete: function(form, action){if(action.type == 'load'){reloadTreeCombo();}}}})
]}

]
});Ext.getCmp('dataGridup20141209945310000').on('rowclick',function(store,e){ records = Ext.getCmp('dataGridup20141209945310000').getSelectionModel().getSelections(); Ext.getCmp('dataGridup201412102111480043').getStore().baseParams={type_000:'child',tmpl_id :'up201412102111480043',baseparams:'aa_acct_id='+records[0].get('acct_id')}; Ext.getCmp('dataGridup201412102111480043').getStore().load(); Ext.getCmp('dataGridup201412102111070042').getStore().baseParams={type_000:'child',tmpl_id :'up201412102111070042',baseparams:'aa_acct_id='+records[0].get('acct_id')}; Ext.getCmp('dataGridup201412102111070042').getStore().load();});function doAddDataup20141209945310000(){windows = new Ext.Window({
modal : true,
maximized : true,
id : 'add',
height : 400,
disabled : false,
hidden : false,
title : '新增数据',
layout : 'fit',
border : true,
hidden : false,
readOnly : false,
closable : true,
frame : true,
autoScroll : true,
bodyStyle : 'padding:5px 10px 0 10px',
items : dataForm = new Ext.form.FormPanel({
url :pathUrl +  '/bckTrackAjax/executeMetaData/add',
labelWidth : 90,
labelAlign : 'left',
labelSepaator : ':',
collapsible : false,
split : true,
errorReader : nullReader = new Ext.data.JsonReader({
root : 'errors', 
fields : [{name : 'msg'},{name : 'id'},{name:'showgrid'}],
success : 'success',
idProperty : 'id'
}
),
id : 'data',
disabled : false,
hidden : false,
layout : 'form',
border : true,
hidden : false,
readOnly : false,
closable : true,
frame : false,
autoScroll : true,
bodyStyle : 'padding:5px 10px 0 10px',
items : [field0Set = new Ext.form.FieldSet({title : '客户类',
layout : 'column',
autoHeight : true,
bodyStyle:'padding:5px 5px 0',
collapsible :false,
collapsed :false,
items : [{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412031000130374_cust_idField = new Ext.form.TextField({
fieldLabel : '客户号<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130374_cust_id',
realName : 'cust_id',
invalidText : '输入数据无效',listeners:{	blur:function(t){	   if(t.getValue().trim().length==0){return ;}	   Ext.Ajax.request({		   url : pathUrl + '/bckTrackAjax/doEvent/up20141209945310000/cust_id',		   method: 'POST',		   params: {value: t.getValue()},		   success: function(response, opts) {		      var json=Ext.util.JSON.decode(response.responseText);		      var arys =json.results;			  if (arys) {				  for(var i=0;i<arys.length;i++){					  var ary = dataForm.find('realName',arys[i].name);					  if(ary){					      ary[0].setValue(arys[i].value);					  }				  }			  } 		   } 		});	}} ,
allowBlank : false,
minLength : 5,
minLengthText : '输入长度不能少于5',
MaxLength : 20,
maxLengthText : '输入长度不能多于20'
})
,cl201412031000130376_cust_nameField = new Ext.form.TextField({
fieldLabel : '客户名称<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130376_cust_name',
realName : 'cust_name',
invalidText : '输入数据无效',
allowBlank : false,
minLength : 2,
minLengthText : '输入长度不能少于2',
MaxLength : 100,
maxLengthText : '输入长度不能多于100'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412031000130378_country_cdCombo = new Ext.form.ComboBox({
store : cl201412031000130378_country_cdStore,
valueField : 'std_cd',
displayField : 'std_cd_desc',
mode : 'local',
loadingText : '查询中...',
triggerAction : 'all',
hiddenName : 'cl201412031000130378_country_cd',
allowBlank : false,
fieldLabel : '国别<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : false,
name : 'cl201412031000130378_country_cd',
realName : 'country_cd',
invalidText : '输入数据无效',
value : 'CHN'}),cl201412031000130380_institution_cdField = new Ext.form.TextField({
fieldLabel : '组织机构代码<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130380_institution_cd',
realName : 'institution_cd',
invalidText : '输入数据无效',
allowBlank : false,
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 20,
maxLengthText : '输入长度不能多于20'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412031000130382_cust_cate_cdCombo = new Ext.form.ComboBox({
store : cl201412031000130382_cust_cate_cdStore,
valueField : 'cust_type_cd',
displayField : 'cust_type_desc',
mode : 'local',
loadingText : '查询中...',
triggerAction : 'all',
hiddenName : 'cl201412031000130382_cust_cate_cd',
allowBlank : false,
fieldLabel : '客户类别<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : false,
name : 'cl201412031000130382_cust_cate_cd',
realName : 'cust_cate_cd',
invalidText : '输入数据无效',
value : '02'})]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
]
}
]
}),field1Set = new Ext.form.FieldSet({title : '账户类',
layout : 'column',
autoHeight : true,
bodyStyle:'padding:5px 5px 0',
collapsible :false,
collapsed :false,
items : [{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412031000130370_acct_idField = new Ext.form.TextField({
fieldLabel : '账号<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130370_acct_id',
realName : 'acct_id',
invalidText : '输入数据无效',
allowBlank : false,
minLength : 10,
minLengthText : '输入长度不能少于10',
MaxLength : 50,
maxLengthText : '输入长度不能多于50'
})
,cl201412031000130394_contract_noField = new Ext.form.TextField({
fieldLabel : '合同编号<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130394_contract_no',
realName : 'contract_no',
invalidText : '输入数据无效',
allowBlank : false,
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 30,
maxLengthText : '输入长度不能多于30'
})
,new TreeCombo({
name:'cl201412031000130396_acct_prod_id',
label:'科目<font color=red>*</font>',
rootId:'2012',
rootName:'同业存放',
realName:'null',
dim_code:'fin_prod',
readOnly:false,
hidden:false,
allowBlank:false
})
,cl201412031000130400_curr_cdCombo = new Ext.form.ComboBox({
store : cl201412031000130400_curr_cdStore,
valueField : 'currency_code',
displayField : 'currency_desc',
mode : 'local',
loadingText : '查询中...',
triggerAction : 'all',
hiddenName : 'cl201412031000130400_curr_cd',
allowBlank : false,
fieldLabel : '币种<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : false,
name : 'cl201412031000130400_curr_cd',
realName : 'curr_cd',
invalidText : '输入数据无效',
value : '01'}),cl20141209XA38080006_int_start_dateDate = new Ext.form.DateField({
fieldLabel : '起息日<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl20141209XA38080006_int_start_date',
realName : 'int_start_date',
invalidText : '输入数据无效',listeners:{	change:function(t,n,o){if (t.getRawValue().trim().length == 0) { return; } var end = dataForm .find('realName', 'int_due_date'); if(end){ var endValue = end[0].getValue(); if(endValue){ var startValue = new Date(t.getValue()); var endValue = new Date(endValue); var diffday = endValue - startValue; if(diffday > 0){ var target = dataForm.find('realName', 'term'); if(target){ target[0].setValue(diffday.toFixed(2)/86400000); } }else{ Ext.Msg.alert('提示信息','到息日必须大于起息日。'); } } } }},
allowBlank : false,
format : 'Y-m-d'
})]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412031000130404_int_due_dateDate = new Ext.form.DateField({
fieldLabel : '到息日<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130404_int_due_date',
realName : 'int_due_date',
invalidText : '输入数据无效',listeners:{	change:function(t,n,o){if (t.getRawValue().trim().length == 0) { return; } var start = dataForm .find('realName', 'int_start_date'); if(start){ var startValue = start[0].getValue(); if(startValue){ var startValue = new Date(startValue); var endValue = new Date(t.getValue()); var diffday = endValue - startValue; if(diffday>0){ var target = dataForm.find('realName', 'term'); if(target){ target[0].setValue(diffday.toFixed(2)/86400000); } }else{ Ext.Msg.alert('提示信息','到息日必须大于起息日。'); } } } }},
allowBlank : false,
format : 'Y-m-d'
}),cl201412031000130408_trans_amtField = new Ext.form.TextField({
fieldLabel : '交易金额<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130408_trans_amt',
realName : 'trans_amt',
invalidText : '输入数据无效',
allowBlank : false,
listeners :{
	blur : function(field) { 		field.setValue(fmoney(field.getValue()));	}}
})
,cl201412031000130410_balanceField = new Ext.form.TextField({
fieldLabel : '账户余额<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130410_balance',
realName : 'balance',
invalidText : '输入数据无效',
allowBlank : false,
listeners :{
	blur : function(field) { 		field.setValue(fmoney(field.getValue()));	}}
})
,cl201412031000130412_int_cate_cdCombo = new Ext.form.ComboBox({
store : cl201412031000130412_int_cate_cdStore,
valueField : 'int_calc_cd',
displayField : 'int_calc_desc',
mode : 'local',
loadingText : '查询中...',
triggerAction : 'all',
hiddenName : 'cl201412031000130412_int_cate_cd',
allowBlank : false,
fieldLabel : '计息方式代码<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : false,
name : 'cl201412031000130412_int_cate_cd',
realName : 'int_cate_cd',
invalidText : '输入数据无效',
value : '02'}),cl201412031000130414_yearly_int_rateField = new Ext.form.NumberField({
fieldLabel : '年利率%<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130414_yearly_int_rate',
realName : 'yearly_int_rate',
invalidText : '输入数据无效',
allowBlank : false,
minValue : 0,
maxValue : 100
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412031000130416_int_type_flagCombo = new Ext.form.ComboBox({
store : cl201412031000130416_int_type_flagStore,
valueField : 'int_type_flag',
displayField : 'int_type_desc',
mode : 'local',
loadingText : '查询中...',
triggerAction : 'all',
hiddenName : 'cl201412031000130416_int_type_flag',
allowBlank : false,
fieldLabel : '利率类型<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : false,
name : 'cl201412031000130416_int_type_flag',
realName : 'int_type_flag',
invalidText : '输入数据无效',
value : '0'}),new TreeCombo({
name:'cl201412031000120356_finance_org_id',
label:'入账机构<font color=red>*</font>',
rootId:'8888',
rootName:'总行',
realName:'null',
dim_code:'bankOrgId',
readOnly:false,
hidden:false,
allowBlank:false
})
,cl201412031000130360_cust_mgr_idField = new Ext.form.TextField({
fieldLabel : '客户经理号<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130360_cust_mgr_id',
realName : 'cust_mgr_id',
invalidText : '输入数据无效',
allowBlank : false,
minLength : 5,
minLengthText : '输入长度不能少于5',
MaxLength : 6,
maxLengthText : '输入长度不能多于6'
})
,new TreeCombo({
name:'cl201412031000130364_cust_mgr_org_id',
label:'客户经理机构<font color=red>*</font>',
rootId:'8888',
rootName:'总行',
realName:'null',
dim_code:'bankOrgId',
readOnly:false,
hidden:false,
allowBlank:false
})
,new TreeCombo({
name:'cl201412031000130366_biz_line_id',
label:'业务条线<font color=red>*</font>',
rootId:'B99',
rootName:'总条线',
realName:'null',
dim_code:'BUSI_LINE',
readOnly:false,
hidden:false,
allowBlank:false
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412ZB933310001_acct_nameField = new Ext.form.TextField({
fieldLabel : '账户名称',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310001_acct_name',
realName : 'acct_name',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 50,
maxLengthText : '输入长度不能多于50'
})
,cl201412031000130368_biz_type_cdField = new Ext.form.TextField({
fieldLabel : '业务关系类型',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130368_biz_type_cd',
realName : 'biz_type_cd',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 8,
maxLengthText : '输入长度不能多于8'
})
,cl201412ZB933310002_int_pay_freq_cdCombo = new Ext.form.ComboBox({
store : cl201412ZB933310002_int_pay_freq_cdStore,
valueField : 'ip_fr_cd',
displayField : 'ip_fr_desc',
mode : 'local',
loadingText : '查询中...',
triggerAction : 'all',
hiddenName : 'cl201412ZB933310002_int_pay_freq_cd',
allowBlank : true,
fieldLabel : '付息频率',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : false,
name : 'cl201412ZB933310002_int_pay_freq_cd',
realName : 'int_pay_freq_cd',
invalidText : '输入数据无效',
value : '04'}),cl20141205934510013_biz_dateDate = new Ext.form.DateField({
fieldLabel : '业务日期<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl20141205934510013_biz_date',
realName : 'biz_date',
invalidText : '输入数据无效',
allowBlank : false,
format : 'Y-m-d'
}),cl201412ZB933310003_next_pay_int_dateDate = new Ext.form.DateField({
fieldLabel : '下一个付息日',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310003_next_pay_int_date',
realName : 'next_pay_int_date',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
format : 'Y-m-d'
})]
}
]
}),field2Set = new Ext.form.FieldSet({title : '其他类',
layout : 'column',
autoHeight : true,
bodyStyle:'padding:5px 5px 0',
collapsible :false,
collapsed :false,
items : [{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412031000130406_termField = new Ext.form.TextField({
fieldLabel : '期限(天)<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : true,
editable : true,
name : 'cl201412031000130406_term',
realName : 'term',
invalidText : '输入数据无效',
allowBlank : false,
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 20,
maxLengthText : '输入长度不能多于20'
})
,cl201412ZB933310004_trans_opponentField = new Ext.form.TextField({
fieldLabel : '交易对手',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310004_trans_opponent',
realName : 'trans_opponent',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 32,
maxLengthText : '输入长度不能多于32'
})
,cl201412ZB933310005_rtn_acct_idField = new Ext.form.TextField({
fieldLabel : '回款账号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310005_rtn_acct_id',
realName : 'rtn_acct_id',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 30,
maxLengthText : '输入长度不能多于30'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412ZB933310006_rtn_acct_nameField = new Ext.form.TextField({
fieldLabel : '回款户名',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310006_rtn_acct_name',
realName : 'rtn_acct_name',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 60,
maxLengthText : '输入长度不能多于60'
})
,cl201412ZB933310007_rtn_bank_noField = new Ext.form.TextField({
fieldLabel : '回款大额行号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310007_rtn_bank_no',
realName : 'rtn_bank_no',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 20,
maxLengthText : '输入长度不能多于20'
})
,cl201412ZB933310008_contact_nameField = new Ext.form.TextField({
fieldLabel : '联系人名称',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310008_contact_name',
realName : 'contact_name',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 60,
maxLengthText : '输入长度不能多于60'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412ZB933310009_contact_telField = new Ext.form.TextField({
fieldLabel : '联系人电话',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310009_contact_tel',
realName : 'contact_tel',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 20,
maxLengthText : '输入长度不能多于20'
})
,cl201412ZB933310010_contact_addrField = new Ext.form.TextField({
fieldLabel : '联系人地址',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310010_contact_addr',
realName : 'contact_addr',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 60,
maxLengthText : '输入长度不能多于60'
})
,cl201412ZB933310011_contact_emailField = new Ext.form.TextField({
fieldLabel : '联系人邮箱',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310011_contact_email',
realName : 'contact_email',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 40,
maxLengthText : '输入长度不能多于40'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
]
}
]
}),cl20141209XA38080001_inner_ratingHidden  = new Ext.form.Hidden({name : 'cl20141209XA38080001_inner_rating',
value : ''
}),cl20141209XA38080002_outer_ratingHidden  = new Ext.form.Hidden({name : 'cl20141209XA38080002_outer_rating',
value : ''
}),cl20141209XA38080003_finance_licenseHidden  = new Ext.form.Hidden({name : 'cl20141209XA38080003_finance_license',
value : ''
}),cl20141209XA38080004_biz_prod_noHidden  = new Ext.form.Hidden({name : 'cl20141209XA38080004_biz_prod_no',
value : ''
}),cl20141209XA38080005_biz_prod_nameHidden  = new Ext.form.Hidden({name : 'cl20141209XA38080005_biz_prod_name',
value : ''
}),cl201412031000130398_acct_prod_nameHidden  = new Ext.form.Hidden({name : 'cl201412031000130398_acct_prod_name',
value : ''
}),cl201412031000120358_finance_org_nameHidden  = new Ext.form.Hidden({name : 'cl201412031000120358_finance_org_name',
value : ''
}),cl201412031000130362_cust_mgr_nameHidden  = new Ext.form.Hidden({name : 'cl201412031000130362_cust_mgr_name',
value : ''
}),cl201412031000130372_business_noHidden  = new Ext.form.Hidden({name : 'cl201412031000130372_business_no',
value : ''
}),_templateIdHidden  = new Ext.form.Hidden({name : '_templateId',
value : 'up20141209945310000'
})]
,listeners:{actioncomplete: function(form, action){if(action.type == 'load'){reloadTreeCombo();}}}}),
buttonAlign : 'center',
buttons : [ {
disabled : false,
hidden : false,
text : '确定',
handler : function (){
doSaveData('up20141209945310000');
}
},
{
disabled : false,
hidden : false,
text : '取消',
handler : function (){
windows.destroy();
}
}
]

});
windows.show();};function doEditDataup20141209945310000(){ var record = Ext.getCmp('dataGridup20141209945310000').getSelectionModel().getSelected();if(record == null || record.length <= 0){Ext.Msg.alert('提示','请选择一行数据！');return;}var business_no = record.get('business_no');var status_code = record.get('status_code');if(status_code!=null&&status_code!=''&&status_code != '01' && status_code != '03' && status_code != '10'){ Ext.Msg.alert('错误','数据处于待审批或已审批，不能编辑！'); return false;}windows = new Ext.Window({
modal : true,
maximized : true,
id : 'edit',
height : 400,
disabled : false,
hidden : false,
title : '编辑数据',
layout : 'fit',
border : true,
hidden : false,
readOnly : false,
closable : true,
frame : true,
autoScroll : true,
bodyStyle : 'padding:5px 10px 0 10px',
items : dataForm = new Ext.form.FormPanel({
url :pathUrl +  '/bckTrackAjax/executeMetaData/edit',
labelWidth : 90,
labelAlign : 'left',
labelSepaator : ':',
collapsible : false,
split : true,
reader : editReader = new Ext.data.JsonReader({
root : 'results',
totalProperty : 'totalCount'
},
[
{name :'cl201412031000130374_cust_id',mapping : 'cust_id',type :'string'},
{name :'cl201412031000130376_cust_name',mapping : 'cust_name',type :'string'},
{name :'cl201412031000130378_country_cd',mapping : 'country_cd',type :'string'},
{name :'cl201412031000130380_institution_cd',mapping : 'institution_cd',type :'string'},
{name :'cl201412031000130382_cust_cate_cd',mapping : 'cust_cate_cd',type :'string'},
{name :'cl20141209XA38080001_inner_rating',mapping : 'inner_rating',type :'string'},
{name :'cl20141209XA38080002_outer_rating',mapping : 'outer_rating',type :'string'},
{name :'cl20141209XA38080003_finance_license',mapping : 'finance_license',type :'string'},
{name :'cl201412031000130370_acct_id',mapping : 'acct_id',type :'string'},
{name :'cl20141209XA38080004_biz_prod_no',mapping : 'biz_prod_no',type :'string'},
{name :'cl20141209XA38080005_biz_prod_name',mapping : 'biz_prod_name',type :'string'},
{name :'cl201412031000130394_contract_no',mapping : 'contract_no',type :'string'},
{name :'cl201412031000130396_acct_prod_id',mapping : 'acct_prod_id',type :'string'},
{name :'cl201412031000130398_acct_prod_name',mapping : 'acct_prod_name',type :'string'},
{name :'cl201412031000130400_curr_cd',mapping : 'curr_cd',type :'string'},
{name :'cl20141209XA38080006_int_start_date',mapping : 'int_start_date',type :'date',dateFormat :'Y-m-d'},
{name :'cl201412031000130404_int_due_date',mapping : 'int_due_date',type :'date',dateFormat :'Y-m-d'},
{name :'cl201412031000130408_trans_amt',mapping : 'trans_amt',convert :fmoney},
{name :'cl201412031000130410_balance',mapping : 'balance',convert :fmoney},
{name :'cl201412031000130412_int_cate_cd',mapping : 'int_cate_cd',type :'string'},
{name :'cl201412031000130414_yearly_int_rate',mapping : 'yearly_int_rate',type :'number'},
{name :'cl201412031000130416_int_type_flag',mapping : 'int_type_flag',type :'string'},
{name :'cl201412031000120356_finance_org_id',mapping : 'finance_org_id',type :'string'},
{name :'cl201412031000120358_finance_org_name',mapping : 'finance_org_name',type :'string'},
{name :'cl201412031000130360_cust_mgr_id',mapping : 'cust_mgr_id',type :'string'},
{name :'cl201412031000130362_cust_mgr_name',mapping : 'cust_mgr_name',type :'string'},
{name :'cl201412031000130364_cust_mgr_org_id',mapping : 'cust_mgr_org_id',type :'string'},
{name :'cl201412031000130366_biz_line_id',mapping : 'biz_line_id',type :'string'},
{name :'cl201412ZB933310001_acct_name',mapping : 'acct_name',type :'string'},
{name :'cl201412031000130368_biz_type_cd',mapping : 'biz_type_cd',type :'string'},
{name :'cl201412ZB933310002_int_pay_freq_cd',mapping : 'int_pay_freq_cd',type :'string'},
{name :'cl20141205934510013_biz_date',mapping : 'biz_date',type :'date',dateFormat :'Y-m-d'},
{name :'cl201412ZB933310003_next_pay_int_date',mapping : 'next_pay_int_date',type :'date',dateFormat :'Y-m-d'},
{name :'cl201412031000130406_term',mapping : 'term',type :'string'},
{name :'cl201412ZB933310004_trans_opponent',mapping : 'trans_opponent',type :'string'},
{name :'cl201412ZB933310005_rtn_acct_id',mapping : 'rtn_acct_id',type :'string'},
{name :'cl201412ZB933310006_rtn_acct_name',mapping : 'rtn_acct_name',type :'string'},
{name :'cl201412ZB933310007_rtn_bank_no',mapping : 'rtn_bank_no',type :'string'},
{name :'cl201412ZB933310008_contact_name',mapping : 'contact_name',type :'string'},
{name :'cl201412ZB933310009_contact_tel',mapping : 'contact_tel',type :'string'},
{name :'cl201412ZB933310010_contact_addr',mapping : 'contact_addr',type :'string'},
{name :'cl201412ZB933310011_contact_email',mapping : 'contact_email',type :'string'},
{name :'cl201412031000130372_business_no',mapping : 'business_no',type :'string'}
]),
errorReader : nullReader = new Ext.data.JsonReader({
root : 'errors', 
fields : [{name : 'msg'},{name : 'id'},{name:'showgrid'}],
success : 'success',
idProperty : 'id'
}
),
id : 'data',
disabled : false,
hidden : false,
layout : 'form',
border : true,
hidden : false,
readOnly : false,
closable : true,
frame : false,
autoScroll : true,
bodyStyle : 'padding:5px 10px 0 10px',
items : [field0Set = new Ext.form.FieldSet({title : '客户类',
layout : 'column',
autoHeight : true,
bodyStyle:'padding:5px 5px 0',
collapsible :false,
collapsed :false,
items : [{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412031000130374_cust_idField = new Ext.form.TextField({
fieldLabel : '客户号<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130374_cust_id',
realName : 'cust_id',
invalidText : '输入数据无效',listeners:{	blur:function(t){	   if(t.getValue().trim().length==0){return ;}	   Ext.Ajax.request({		   url : pathUrl + '/bckTrackAjax/doEvent/up20141209945310000/cust_id',		   method: 'POST',		   params: {value: t.getValue()},		   success: function(response, opts) {		      var json=Ext.util.JSON.decode(response.responseText);		      var arys =json.results;			  if (arys) {				  for(var i=0;i<arys.length;i++){					  var ary = dataForm.find('realName',arys[i].name);					  if(ary){					      ary[0].setValue(arys[i].value);					  }				  }			  } 		   } 		});	}} ,
allowBlank : false,
minLength : 5,
minLengthText : '输入长度不能少于5',
MaxLength : 20,
maxLengthText : '输入长度不能多于20'
})
,cl201412031000130376_cust_nameField = new Ext.form.TextField({
fieldLabel : '客户名称<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130376_cust_name',
realName : 'cust_name',
invalidText : '输入数据无效',
allowBlank : false,
minLength : 2,
minLengthText : '输入长度不能少于2',
MaxLength : 100,
maxLengthText : '输入长度不能多于100'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412031000130378_country_cdCombo = new Ext.form.ComboBox({
store : cl201412031000130378_country_cdStore,
valueField : 'std_cd',
displayField : 'std_cd_desc',
mode : 'local',
loadingText : '查询中...',
triggerAction : 'all',
hiddenName : 'cl201412031000130378_country_cd',
allowBlank : false,
fieldLabel : '国别<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : false,
name : 'cl201412031000130378_country_cd',
realName : 'country_cd',
invalidText : '输入数据无效',
value : 'CHN'}),cl201412031000130380_institution_cdField = new Ext.form.TextField({
fieldLabel : '组织机构代码<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130380_institution_cd',
realName : 'institution_cd',
invalidText : '输入数据无效',
allowBlank : false,
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 20,
maxLengthText : '输入长度不能多于20'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412031000130382_cust_cate_cdCombo = new Ext.form.ComboBox({
store : cl201412031000130382_cust_cate_cdStore,
valueField : 'cust_type_cd',
displayField : 'cust_type_desc',
mode : 'local',
loadingText : '查询中...',
triggerAction : 'all',
hiddenName : 'cl201412031000130382_cust_cate_cd',
allowBlank : false,
fieldLabel : '客户类别<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : false,
name : 'cl201412031000130382_cust_cate_cd',
realName : 'cust_cate_cd',
invalidText : '输入数据无效',
value : '02'})]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
]
}
]
}),field1Set = new Ext.form.FieldSet({title : '账户类',
layout : 'column',
autoHeight : true,
bodyStyle:'padding:5px 5px 0',
collapsible :false,
collapsed :false,
items : [{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412031000130370_acct_idField = new Ext.form.TextField({
fieldLabel : '账号<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130370_acct_id',
realName : 'acct_id',
invalidText : '输入数据无效',
allowBlank : false,
minLength : 10,
minLengthText : '输入长度不能少于10',
MaxLength : 50,
maxLengthText : '输入长度不能多于50'
})
,cl201412031000130394_contract_noField = new Ext.form.TextField({
fieldLabel : '合同编号<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130394_contract_no',
realName : 'contract_no',
invalidText : '输入数据无效',
allowBlank : false,
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 30,
maxLengthText : '输入长度不能多于30'
})
,new TreeCombo({
name:'cl201412031000130396_acct_prod_id',
label:'科目<font color=red>*</font>',
rootId:'2012',
rootName:'同业存放',
realName:'null',
dim_code:'fin_prod',
readOnly:false,
hidden:false,
allowBlank:false
})
,cl201412031000130400_curr_cdCombo = new Ext.form.ComboBox({
store : cl201412031000130400_curr_cdStore,
valueField : 'currency_code',
displayField : 'currency_desc',
mode : 'local',
loadingText : '查询中...',
triggerAction : 'all',
hiddenName : 'cl201412031000130400_curr_cd',
allowBlank : false,
fieldLabel : '币种<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : false,
name : 'cl201412031000130400_curr_cd',
realName : 'curr_cd',
invalidText : '输入数据无效',
value : '01'}),cl20141209XA38080006_int_start_dateDate = new Ext.form.DateField({
fieldLabel : '起息日<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl20141209XA38080006_int_start_date',
realName : 'int_start_date',
invalidText : '输入数据无效',listeners:{	change:function(t,n,o){if (t.getRawValue().trim().length == 0) { return; } var end = dataForm .find('realName', 'int_due_date'); if(end){ var endValue = end[0].getValue(); if(endValue){ var startValue = new Date(t.getValue()); var endValue = new Date(endValue); var diffday = endValue - startValue; if(diffday > 0){ var target = dataForm.find('realName', 'term'); if(target){ target[0].setValue(diffday.toFixed(2)/86400000); } }else{ Ext.Msg.alert('提示信息','到息日必须大于起息日。'); } } } }},
allowBlank : false,
format : 'Y-m-d'
})]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412031000130404_int_due_dateDate = new Ext.form.DateField({
fieldLabel : '到息日<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130404_int_due_date',
realName : 'int_due_date',
invalidText : '输入数据无效',listeners:{	change:function(t,n,o){if (t.getRawValue().trim().length == 0) { return; } var start = dataForm .find('realName', 'int_start_date'); if(start){ var startValue = start[0].getValue(); if(startValue){ var startValue = new Date(startValue); var endValue = new Date(t.getValue()); var diffday = endValue - startValue; if(diffday>0){ var target = dataForm.find('realName', 'term'); if(target){ target[0].setValue(diffday.toFixed(2)/86400000); } }else{ Ext.Msg.alert('提示信息','到息日必须大于起息日。'); } } } }},
allowBlank : false,
format : 'Y-m-d'
}),cl201412031000130408_trans_amtField = new Ext.form.TextField({
fieldLabel : '交易金额<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130408_trans_amt',
realName : 'trans_amt',
invalidText : '输入数据无效',
allowBlank : false,
listeners :{
	blur : function(field) { 		field.setValue(fmoney(field.getValue()));	}}
})
,cl201412031000130410_balanceField = new Ext.form.TextField({
fieldLabel : '账户余额<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130410_balance',
realName : 'balance',
invalidText : '输入数据无效',
allowBlank : false,
listeners :{
	blur : function(field) { 		field.setValue(fmoney(field.getValue()));	}}
})
,cl201412031000130412_int_cate_cdCombo = new Ext.form.ComboBox({
store : cl201412031000130412_int_cate_cdStore,
valueField : 'int_calc_cd',
displayField : 'int_calc_desc',
mode : 'local',
loadingText : '查询中...',
triggerAction : 'all',
hiddenName : 'cl201412031000130412_int_cate_cd',
allowBlank : false,
fieldLabel : '计息方式代码<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : false,
name : 'cl201412031000130412_int_cate_cd',
realName : 'int_cate_cd',
invalidText : '输入数据无效',
value : '02'}),cl201412031000130414_yearly_int_rateField = new Ext.form.NumberField({
fieldLabel : '年利率%<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130414_yearly_int_rate',
realName : 'yearly_int_rate',
invalidText : '输入数据无效',
allowBlank : false,
minValue : 0,
maxValue : 100
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412031000130416_int_type_flagCombo = new Ext.form.ComboBox({
store : cl201412031000130416_int_type_flagStore,
valueField : 'int_type_flag',
displayField : 'int_type_desc',
mode : 'local',
loadingText : '查询中...',
triggerAction : 'all',
hiddenName : 'cl201412031000130416_int_type_flag',
allowBlank : false,
fieldLabel : '利率类型<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : false,
name : 'cl201412031000130416_int_type_flag',
realName : 'int_type_flag',
invalidText : '输入数据无效',
value : '0'}),new TreeCombo({
name:'cl201412031000120356_finance_org_id',
label:'入账机构<font color=red>*</font>',
rootId:'8888',
rootName:'总行',
realName:'null',
dim_code:'bankOrgId',
readOnly:false,
hidden:false,
allowBlank:false
})
,cl201412031000130360_cust_mgr_idField = new Ext.form.TextField({
fieldLabel : '客户经理号<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130360_cust_mgr_id',
realName : 'cust_mgr_id',
invalidText : '输入数据无效',
allowBlank : false,
minLength : 5,
minLengthText : '输入长度不能少于5',
MaxLength : 6,
maxLengthText : '输入长度不能多于6'
})
,new TreeCombo({
name:'cl201412031000130364_cust_mgr_org_id',
label:'客户经理机构<font color=red>*</font>',
rootId:'8888',
rootName:'总行',
realName:'null',
dim_code:'bankOrgId',
readOnly:false,
hidden:false,
allowBlank:false
})
,new TreeCombo({
name:'cl201412031000130366_biz_line_id',
label:'业务条线<font color=red>*</font>',
rootId:'B99',
rootName:'总条线',
realName:'null',
dim_code:'BUSI_LINE',
readOnly:false,
hidden:false,
allowBlank:false
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412ZB933310001_acct_nameField = new Ext.form.TextField({
fieldLabel : '账户名称',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310001_acct_name',
realName : 'acct_name',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 50,
maxLengthText : '输入长度不能多于50'
})
,cl201412031000130368_biz_type_cdField = new Ext.form.TextField({
fieldLabel : '业务关系类型',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412031000130368_biz_type_cd',
realName : 'biz_type_cd',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 8,
maxLengthText : '输入长度不能多于8'
})
,cl201412ZB933310002_int_pay_freq_cdCombo = new Ext.form.ComboBox({
store : cl201412ZB933310002_int_pay_freq_cdStore,
valueField : 'ip_fr_cd',
displayField : 'ip_fr_desc',
mode : 'local',
loadingText : '查询中...',
triggerAction : 'all',
hiddenName : 'cl201412ZB933310002_int_pay_freq_cd',
allowBlank : true,
fieldLabel : '付息频率',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : false,
name : 'cl201412ZB933310002_int_pay_freq_cd',
realName : 'int_pay_freq_cd',
invalidText : '输入数据无效',
value : '04'}),cl20141205934510013_biz_dateDate = new Ext.form.DateField({
fieldLabel : '业务日期<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl20141205934510013_biz_date',
realName : 'biz_date',
invalidText : '输入数据无效',
allowBlank : false,
format : 'Y-m-d'
}),cl201412ZB933310003_next_pay_int_dateDate = new Ext.form.DateField({
fieldLabel : '下一个付息日',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310003_next_pay_int_date',
realName : 'next_pay_int_date',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
format : 'Y-m-d'
})]
}
]
}),field2Set = new Ext.form.FieldSet({title : '其他类',
layout : 'column',
autoHeight : true,
bodyStyle:'padding:5px 5px 0',
collapsible :false,
collapsed :false,
items : [{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412031000130406_termField = new Ext.form.TextField({
fieldLabel : '期限(天)<font color=red>*</font>',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : true,
editable : true,
name : 'cl201412031000130406_term',
realName : 'term',
invalidText : '输入数据无效',
allowBlank : false,
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 20,
maxLengthText : '输入长度不能多于20'
})
,cl201412ZB933310004_trans_opponentField = new Ext.form.TextField({
fieldLabel : '交易对手',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310004_trans_opponent',
realName : 'trans_opponent',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 32,
maxLengthText : '输入长度不能多于32'
})
,cl201412ZB933310005_rtn_acct_idField = new Ext.form.TextField({
fieldLabel : '回款账号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310005_rtn_acct_id',
realName : 'rtn_acct_id',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 30,
maxLengthText : '输入长度不能多于30'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412ZB933310006_rtn_acct_nameField = new Ext.form.TextField({
fieldLabel : '回款户名',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310006_rtn_acct_name',
realName : 'rtn_acct_name',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 60,
maxLengthText : '输入长度不能多于60'
})
,cl201412ZB933310007_rtn_bank_noField = new Ext.form.TextField({
fieldLabel : '回款大额行号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310007_rtn_bank_no',
realName : 'rtn_bank_no',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 20,
maxLengthText : '输入长度不能多于20'
})
,cl201412ZB933310008_contact_nameField = new Ext.form.TextField({
fieldLabel : '联系人名称',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310008_contact_name',
realName : 'contact_name',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 60,
maxLengthText : '输入长度不能多于60'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412ZB933310009_contact_telField = new Ext.form.TextField({
fieldLabel : '联系人电话',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310009_contact_tel',
realName : 'contact_tel',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 20,
maxLengthText : '输入长度不能多于20'
})
,cl201412ZB933310010_contact_addrField = new Ext.form.TextField({
fieldLabel : '联系人地址',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310010_contact_addr',
realName : 'contact_addr',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 60,
maxLengthText : '输入长度不能多于60'
})
,cl201412ZB933310011_contact_emailField = new Ext.form.TextField({
fieldLabel : '联系人邮箱',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412ZB933310011_contact_email',
realName : 'contact_email',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
minLength : 0,
minLengthText : '输入长度不能少于0',
MaxLength : 40,
maxLengthText : '输入长度不能多于40'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
]
}
]
}),cl20141209XA38080001_inner_ratingHidden  = new Ext.form.Hidden({name : 'cl20141209XA38080001_inner_rating',
value : ''
}),cl20141209XA38080002_outer_ratingHidden  = new Ext.form.Hidden({name : 'cl20141209XA38080002_outer_rating',
value : ''
}),cl20141209XA38080003_finance_licenseHidden  = new Ext.form.Hidden({name : 'cl20141209XA38080003_finance_license',
value : ''
}),cl20141209XA38080004_biz_prod_noHidden  = new Ext.form.Hidden({name : 'cl20141209XA38080004_biz_prod_no',
value : ''
}),cl20141209XA38080005_biz_prod_nameHidden  = new Ext.form.Hidden({name : 'cl20141209XA38080005_biz_prod_name',
value : ''
}),cl201412031000130398_acct_prod_nameHidden  = new Ext.form.Hidden({name : 'cl201412031000130398_acct_prod_name',
value : ''
}),cl201412031000120358_finance_org_nameHidden  = new Ext.form.Hidden({name : 'cl201412031000120358_finance_org_name',
value : ''
}),cl201412031000130362_cust_mgr_nameHidden  = new Ext.form.Hidden({name : 'cl201412031000130362_cust_mgr_name',
value : ''
}),cl201412031000130372_business_noHidden  = new Ext.form.Hidden({name : 'cl201412031000130372_business_no',
value : ''
}),_templateIdHidden  = new Ext.form.Hidden({name : '_templateId',
value : 'up20141209945310000'
})]
,listeners:{actioncomplete: function(form, action){if(action.type == 'load'){reloadTreeCombo();}}}}),
buttonAlign : 'center',
buttons : [ {
disabled : false,
hidden : false,
text : '确定',
handler : function (){
doSaveData('up20141209945310000');
}
},
{
disabled : false,
hidden : false,
text : '取消',
handler : function (){
windows.destroy();
}
}
]

});
windows.show();dataForm.form.load({url : pathUrl + '/bckTrackAjax/getMetaDataById',params :{key:business_no,tmpl_id:'up20141209945310000'}});};function doDelDataup20141209945310000(){ var  business_nos = ''; var record = Ext.getCmp('dataGridup20141209945310000').getSelectionModel().getSelections();if(record == null || record.length <= 0){Ext.Msg.alert('提示','请选择一行数据！！！');return;} for(var i=0;i<record.length;i++){  var tmp=record[i];  var business_no = tmp.get('business_no');  var status_code = tmp.get('status_code');  if(status_code!=null&&status_code!=''&&status_code != '01' && status_code != '03' && status_code != '10'){      Ext.Msg.alert('错误','选中的有未完成审批的数据！');     return false;    }   if(record.length-1 == i){     business_nos  += business_no;   }else{    business_nos  += business_no+ '_';   }  }  doDelData(business_nos,'up20141209945310000');}; doImportDataup20141209945310000(){if(records == null || records.length <= 0){Ext.Msg.alert('提示','请选择一行数据！');return;}  var status = records[0].get('status_code');  if(status!=null&&status!=''&&status != '01' && status != '03' && status != '10'){      Ext.Msg.alert('错误','选中的有未完成审批的数据！');     return false;    }    var rela_value='';  alert(rela_value);doImportData('up20141209945310000',rela_value); }function doAddDataup201412102111480043(){if(records == null || records.length <= 0){Ext.Msg.alert('提示','请选择一行数据！');return;}  var status = records[0].get('status_code');  if(status!=null&&status!=''&&status != '01' && status != '03' && status != '10'){      Ext.Msg.alert('错误','选中的有未完成审批的数据！');     return false;    } windows = new Ext.Window({
modal : true,
maximized : true,
id : 'add',
height : 400,
disabled : false,
hidden : false,
title : '新增数据',
layout : 'fit',
border : true,
hidden : false,
readOnly : false,
closable : true,
frame : true,
autoScroll : true,
bodyStyle : 'padding:5px 10px 0 10px',
items : dataForm = new Ext.form.FormPanel({
url :pathUrl +  '/bckTrackAjax/executeMetaData/add',
labelWidth : 90,
labelAlign : 'left',
labelSepaator : ':',
collapsible : false,
split : true,
errorReader : nullReader = new Ext.data.JsonReader({
root : 'errors', 
fields : [{name : 'msg'},{name : 'id'},{name:'showgrid'}],
success : 'success',
idProperty : 'id'
}
),
id : 'data',
disabled : false,
hidden : false,
layout : 'form',
border : true,
hidden : false,
readOnly : false,
closable : true,
frame : false,
autoScroll : true,
bodyStyle : 'padding:5px 10px 0 10px',
items : [field0Set = new Ext.form.FieldSet({title : '默认',
layout : 'column',
autoHeight : true,
bodyStyle:'padding:5px 5px 0',
collapsible :false,
collapsed :false,
items : [{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
new TreeCombo({
name:'cl201412102002020017_acct_prod_id',
label:'科目',
rootId:'cl201412102002020017',
rootName:'科目',
realName:'null',
dim_code:'fin_prod',
readOnly:false,
hidden:false,
allowBlank:true
})
,cl201412102002020021_business_noField = new Ext.form.TextField({
fieldLabel : '业务编号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002020021_business_no',
realName : 'business_no',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412102002020019_end_dateDate = new Ext.form.DateField({
fieldLabel : '结束日期',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002020019_end_date',
realName : 'end_date',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
format : 'Y-m-d'
}),cl201412102002020015_alloc_rateField = new Ext.form.TextField({
fieldLabel : '分配比例',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002020015_alloc_rate',
realName : 'alloc_rate',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412102002020013_begin_dateDate = new Ext.form.DateField({
fieldLabel : '开始日期',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002020013_begin_date',
realName : 'begin_date',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
format : 'Y-m-d'
}),cl201412102002020011_cust_mgr_idField = new Ext.form.TextField({
fieldLabel : '客户经理员工号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002020011_cust_mgr_id',
realName : 'cust_mgr_id',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412102002020009_acct_idField = new Ext.form.TextField({
fieldLabel : '账号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002020009_acct_id',
realName : 'acct_id',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
]
}
]
}),_templateIdHidden  = new Ext.form.Hidden({name : '_templateId',
value : 'up201412102111480043'
})]
,listeners:{actioncomplete: function(form, action){if(action.type == 'load'){reloadTreeCombo();}}}}),
buttonAlign : 'center',
buttons : [ {
disabled : false,
hidden : false,
text : '确定',
handler : function (){
doSaveData('up201412102111480043');
}
},
{
disabled : false,
hidden : false,
text : '取消',
handler : function (){
windows.destroy();
}
}
]

});
windows.show();dataForm.find('realName','acct_id')[0].setValue(records[0].get('acct_id'));};function doEditDataup201412102111480043(){if(records == null || records.length <= 0){Ext.Msg.alert('提示','请选择一行数据！');return;}  var status = records[0].get('status_code');  if(status!=null&&status!=''&&status != '01' && status != '03' && status != '10'){      Ext.Msg.alert('错误','选中的有未完成审批的数据！');     return false;    }   showAccountAllotWin(records[0].get('acct_id'),'',Ext.getCmp('dataGridup201412102111480043').getStore()) };function doDelDataup201412102111480043(){if(records == null || records.length <= 0){Ext.Msg.alert('提示','请选择一行数据！');return;}  var status = records[0].get('status_code');  if(status!=null&&status!=''&&status != '01' && status != '03' && status != '10'){      Ext.Msg.alert('错误','选中的有未完成审批的数据！');     return false;    }  var  business_nos = ''; var record = Ext.getCmp('dataGridup201412102111480043').getSelectionModel().getSelections();if(record == null || record.length <= 0){Ext.Msg.alert('提示','请选择一行数据！！！');return;} for(var i=0;i<record.length;i++){  var tmp=record[i];  var business_no = tmp.get('business_no');  var status_code = tmp.get('status_code');  if(status_code!=null&&status_code!=''&&status_code != '01' && status_code != '03' && status_code != '10'){      Ext.Msg.alert('错误','选中的有未完成审批的数据！');     return false;    }   if(record.length-1 == i){     business_nos  += business_no;   }else{    business_nos  += business_no+ '_';   }  }  doDelData(business_nos,'up201412102111480043');}; doImportDataup201412102111480043(){if(records == null || records.length <= 0){Ext.Msg.alert('提示','请选择一行数据！');return;}  var status = records[0].get('status_code');  if(status!=null&&status!=''&&status != '01' && status != '03' && status != '10'){      Ext.Msg.alert('错误','选中的有未完成审批的数据！');     return false;    }    var rela_value=''; rela_value +=records[0].get('acct_id')+','; alert(rela_value);doImportData('up201412102111480043',rela_value); }function doAddDataup201412102111070042(){if(records == null || records.length <= 0){Ext.Msg.alert('提示','请选择一行数据！');return;}  var status = records[0].get('status_code');  if(status!=null&&status!=''&&status != '01' && status != '03' && status != '10'){      Ext.Msg.alert('错误','选中的有未完成审批的数据！');     return false;    } windows = new Ext.Window({
modal : true,
maximized : true,
id : 'add',
height : 400,
disabled : false,
hidden : false,
title : '新增数据',
layout : 'fit',
border : true,
hidden : false,
readOnly : false,
closable : true,
frame : true,
autoScroll : true,
bodyStyle : 'padding:5px 10px 0 10px',
items : dataForm = new Ext.form.FormPanel({
url :pathUrl +  '/bckTrackAjax/executeMetaData/add',
labelWidth : 90,
labelAlign : 'left',
labelSepaator : ':',
collapsible : false,
split : true,
errorReader : nullReader = new Ext.data.JsonReader({
root : 'errors', 
fields : [{name : 'msg'},{name : 'id'},{name:'showgrid'}],
success : 'success',
idProperty : 'id'
}
),
id : 'data',
disabled : false,
hidden : false,
layout : 'form',
border : true,
hidden : false,
readOnly : false,
closable : true,
frame : false,
autoScroll : true,
bodyStyle : 'padding:5px 10px 0 10px',
items : [field0Set = new Ext.form.FieldSet({title : '默认',
layout : 'column',
autoHeight : true,
bodyStyle:'padding:5px 5px 0',
collapsible :false,
collapsed :false,
items : [{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412102002410041_business_noField = new Ext.form.TextField({
fieldLabel : '业务编号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002410041_business_no',
realName : 'business_no',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
,cl201412102002410031_acct_idField = new Ext.form.TextField({
fieldLabel : '账号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002410031_acct_id',
realName : 'acct_id',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412102002410033_trans_idField = new Ext.form.TextField({
fieldLabel : '交易序号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002410033_trans_id',
realName : 'trans_id',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
,cl201412102002410035_trans_dateDate = new Ext.form.DateField({
fieldLabel : '交易日期',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002410035_trans_date',
realName : 'trans_date',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
format : 'Y-m-d'
})]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412102002410037_dc_flagField = new Ext.form.TextField({
fieldLabel : '借贷标识',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002410037_dc_flag',
realName : 'dc_flag',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
,cl201412102002410039_trans_amtField = new Ext.form.TextField({
fieldLabel : '交易金额',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002410039_trans_amt',
realName : 'trans_amt',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
listeners :{
	blur : function(field) { 		field.setValue(fmoney(field.getValue()));	}}
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
]
}
]
}),_templateIdHidden  = new Ext.form.Hidden({name : '_templateId',
value : 'up201412102111070042'
})]
,listeners:{actioncomplete: function(form, action){if(action.type == 'load'){reloadTreeCombo();}}}}),
buttonAlign : 'center',
buttons : [ {
disabled : false,
hidden : false,
text : '确定',
handler : function (){
doSaveData('up201412102111070042');
}
},
{
disabled : false,
hidden : false,
text : '取消',
handler : function (){
windows.destroy();
}
}
]

});
windows.show();dataForm.find('realName','acct_id')[0].setValue(records[0].get('acct_id'));};function doEditDataup201412102111070042(){if(records == null || records.length <= 0){Ext.Msg.alert('提示','请选择一行数据！');return;}  var status = records[0].get('status_code');  if(status!=null&&status!=''&&status != '01' && status != '03' && status != '10'){      Ext.Msg.alert('错误','选中的有未完成审批的数据！');     return false;    }  var record = Ext.getCmp('dataGridup201412102111070042').getSelectionModel().getSelected();if(record == null || record.length <= 0){Ext.Msg.alert('提示','请选择一行数据！');return;}var business_no = record.get('business_no');var status_code = record.get('status_code');if(status_code!=null&&status_code!=''&&status_code != '01' && status_code != '03' && status_code != '10'){ Ext.Msg.alert('错误','数据处于待审批或已审批，不能编辑！'); return false;}windows = new Ext.Window({
modal : true,
maximized : true,
id : 'edit',
height : 400,
disabled : false,
hidden : false,
title : '编辑数据',
layout : 'fit',
border : true,
hidden : false,
readOnly : false,
closable : true,
frame : true,
autoScroll : true,
bodyStyle : 'padding:5px 10px 0 10px',
items : dataForm = new Ext.form.FormPanel({
url :pathUrl +  '/bckTrackAjax/executeMetaData/edit',
labelWidth : 90,
labelAlign : 'left',
labelSepaator : ':',
collapsible : false,
split : true,
reader : editReader = new Ext.data.JsonReader({
root : 'results',
totalProperty : 'totalCount'
},
[
{name :'cl201412102002410041_business_no',mapping : 'business_no',type :'string'},
{name :'cl201412102002410031_acct_id',mapping : 'acct_id',type :'string'},
{name :'cl201412102002410033_trans_id',mapping : 'trans_id',type :'string'},
{name :'cl201412102002410035_trans_date',mapping : 'trans_date',type :'date',dateFormat :'Y-m-d'},
{name :'cl201412102002410037_dc_flag',mapping : 'dc_flag',type :'string'},
{name :'cl201412102002410039_trans_amt',mapping : 'trans_amt',convert :fmoney}
]),
errorReader : nullReader = new Ext.data.JsonReader({
root : 'errors', 
fields : [{name : 'msg'},{name : 'id'},{name:'showgrid'}],
success : 'success',
idProperty : 'id'
}
),
id : 'data',
disabled : false,
hidden : false,
layout : 'form',
border : true,
hidden : false,
readOnly : false,
closable : true,
frame : false,
autoScroll : true,
bodyStyle : 'padding:5px 10px 0 10px',
items : [field0Set = new Ext.form.FieldSet({title : '默认',
layout : 'column',
autoHeight : true,
bodyStyle:'padding:5px 5px 0',
collapsible :false,
collapsed :false,
items : [{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412102002410041_business_noField = new Ext.form.TextField({
fieldLabel : '业务编号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002410041_business_no',
realName : 'business_no',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
,cl201412102002410031_acct_idField = new Ext.form.TextField({
fieldLabel : '账号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002410031_acct_id',
realName : 'acct_id',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412102002410033_trans_idField = new Ext.form.TextField({
fieldLabel : '交易序号',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002410033_trans_id',
realName : 'trans_id',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
,cl201412102002410035_trans_dateDate = new Ext.form.DateField({
fieldLabel : '交易日期',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002410035_trans_date',
realName : 'trans_date',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
format : 'Y-m-d'
})]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
cl201412102002410037_dc_flagField = new Ext.form.TextField({
fieldLabel : '借贷标识',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002410037_dc_flag',
realName : 'dc_flag',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空'
})
,cl201412102002410039_trans_amtField = new Ext.form.TextField({
fieldLabel : '交易金额',
anchor : '90%',
disabled : false,
hidden : false,
readOnly : false,
editable : true,
name : 'cl201412102002410039_trans_amt',
realName : 'trans_amt',
invalidText : '输入数据无效',
allowBlank : true,
blankText : '该项不能为空',
listeners :{
	blur : function(field) { 		field.setValue(fmoney(field.getValue()));	}}
})
]
},
{layout : 'form',
columnWidth : 0.25,
labelWidth : 90,
border : false,
items :[
]
}
]
}),_templateIdHidden  = new Ext.form.Hidden({name : '_templateId',
value : 'up201412102111070042'
})]
,listeners:{actioncomplete: function(form, action){if(action.type == 'load'){reloadTreeCombo();}}}}),
buttonAlign : 'center',
buttons : [ {
disabled : false,
hidden : false,
text : '确定',
handler : function (){
doSaveData('up201412102111070042');
}
},
{
disabled : false,
hidden : false,
text : '取消',
handler : function (){
windows.destroy();
}
}
]

});
windows.show();dataForm.form.load({url : pathUrl + '/bckTrackAjax/getMetaDataById',params :{key:business_no,tmpl_id:'up201412102111070042'}});};function doDelDataup201412102111070042(){if(records == null || records.length <= 0){Ext.Msg.alert('提示','请选择一行数据！');return;}  var status = records[0].get('status_code');  if(status!=null&&status!=''&&status != '01' && status != '03' && status != '10'){      Ext.Msg.alert('错误','选中的有未完成审批的数据！');     return false;    }  var  business_nos = ''; var record = Ext.getCmp('dataGridup201412102111070042').getSelectionModel().getSelections();if(record == null || record.length <= 0){Ext.Msg.alert('提示','请选择一行数据！！！');return;} for(var i=0;i<record.length;i++){  var tmp=record[i];  var business_no = tmp.get('business_no');  var status_code = tmp.get('status_code');  if(status_code!=null&&status_code!=''&&status_code != '01' && status_code != '03' && status_code != '10'){      Ext.Msg.alert('错误','选中的有未完成审批的数据！');     return false;    }   if(record.length-1 == i){     business_nos  += business_no;   }else{    business_nos  += business_no+ '_';   }  }  doDelData(business_nos,'up201412102111070042');}; doImportDataup201412102111070042(){if(records == null || records.length <= 0){Ext.Msg.alert('提示','请选择一行数据！');return;}  var status = records[0].get('status_code');  if(status!=null&&status!=''&&status != '01' && status != '03' && status != '10'){      Ext.Msg.alert('错误','选中的有未完成审批的数据！');     return false;    }    var rela_value=''; rela_value +=records[0].get('acct_id')+','; alert(rela_value);doImportData('up201412102111070042',rela_value); }})
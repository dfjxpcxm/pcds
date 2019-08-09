//说明：页面必须包含  <div id="grid" .. 元素

var gridHeader ; 
/**
 * 通过 ajax请求 加载dhtmlxGrid 数据
 * <br>
 * 类型: 默认-xml, csv,json,jsarray
 */
function parseGridAjax(url,grid,callback){
    loadMask.show();
	Ext.Ajax.request({
		url : url,
		method : 'POST',
		params : {},
		failure : function(response, options) {
		},
		success : function(response, options) {
			loadMask.hide();
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				grid.clearAll();
				if(json.type){
					grid.parse(json.data,json.type); //通过json类型加载
				}else{
					grid.parse(json.data); //默认是xml类型
				}
				//回调函数
				if(callback){
					eval(callback);
				}
			} else {
				Ext.MessageBox.alert('错误',json.info);
			}
		}
	});
}

/**
 * dhtmlxGrid 初始化请求表头 <br>
 * conf 对象
 * 属性： url , params ,callback
 */
function gridInitAjax(conf) {
	
	var scope = conf.scope||window;
	
	Ext.Ajax.request({
		timeout : 10 * 60000,
		url : conf.url,
		method : 'POST',
		params:conf.params,
		failure : function(response, options) {
		},
		success : function(response, options) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				initDhtmlxGrid(json);
				//回调方法
				if(conf.callback){
					conf.callback.call(scope,response, options);
				}
			} else {
				Ext.MessageBox.alert('提示信息', json.info);
			}
		}
	});
}

/**
 * 通过url加载dhtmlxGrid数据
 * <br>
 * 类型: 默认-xml, csv,json,jsarray
 */
function loadGrid(url){
	 loadMask.show();
	 grid.clearAll();
	 //destructor
	 grid.load(url, loadMask.hide());
}

/*function loadGrid(url,callback){
	 loadMask.show();
	 grid.clearAll();
	 if(callback){
		 grid.load(url,function(){
			 loadMask.hide()
			 eval(callback);
		 });  
	 }else{
		
	 }
}*/

/**
 * 初始化
 * @param json
 */
function initDhtmlxGrid(json){
	//表头配置信息
	gridHeader = null;
	gridHeader = json;
	//创建表对象
	window["grid"] = new dhtmlXGridObject("grid");
	grid.setImagePath(pathUrl+"/public/scripts/dhtmlx/imgs/");
	grid.setHeader(json.header);
	//多层副表头
	if(json.attachHeader){
		var attachAry = json.attachHeader.split(";");
		for(var i = 0 ; i<attachAry.length;i++){
			grid.attachHeader(attachAry[i]);
		}
	}
	grid.setInitWidths(json.colWidths); 
	grid.setColAlign(json.colAlign);
	grid.setColTypes(json.colTypes);
	grid.init();
	//列分割线
	if(json.splitAt){
		grid.splitAt(json.splitAt);
	}
}
 
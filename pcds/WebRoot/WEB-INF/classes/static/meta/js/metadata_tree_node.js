/***
 * 元数据树的通用方法 
 * 1.树节点图标样式
 */

/**
 * 类型对应样式对象映射 暂时从此文件中维护
 * 对应icon.css
 * 如 1010:table
 */
var cateMappingObjs = {
		'1010':'tree_theme',//主题
		'101010':'tree_table',//表
		'1010101':'tree_column',//表字段  
		'101020':'tree_database',//数据库
		'101021':'tree_tablespace',//数据库表空间
		'101022':'tree_database_user',//数据库用户
		
		'2010':'tree_function',//功能
		'201010':'tree_bar',//工具条
		'2010101':'tree_view_column'//显示字段tree_view_column
		
};

/***
 * 元数据树节点
 * @param cateObj
 * @returns
 */
function getIconCls(cateObj){
	if(cateObj){
		return cateMappingObjs[cateObj+''];
	}else{
		return 'x-tree-node-icon';
	}
	
}

/***
 * 模板下拉树节点 
 * 用于数据补录 选择的模板节点样式
 * @param template_type_code 
 * @returns
 */
function getIconClsForTemp(template_type_code){
	if(template_type_code == '02'){//补录表  02
		return 'tree_temp_leaf';
	}
	return 'x-tree-node-icon';
	
}
package com.shuhao.clean.apps.model.ext;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.model.ext.base.BaseExt;
import com.shuhao.clean.constant.ExtConstant;
import com.shuhao.clean.utils.GlobalUtil;

public class FormLayout extends Layout{
	
	private int colNum = 4;  //控件默认列数
	
	private String layout = "form";
	private double columnWidth =  0.25;
	private int labelWidth = 90;
	private boolean border = false;
	
	private String layoutJs;
	
	public FormLayout(Map<String,List<BaseExt>> extControlMap,List<BaseExt> extHideControlList) throws Exception {
			this.layoutJs = getWindowLayout(extControlMap,extHideControlList);
	}
	
	public FormLayout(List<BaseExt> ExtObj, String layoutType,String approve_role) throws Exception {
//			this.layoutJs = getQueryFormLayout(ExtObj,layoutType,approve_role);
	}


	/**
	 * @param listBizTypeFields
	 * @param eleMap
	 * @param i
	 * @throws Exception 
	 */
	public FormLayout(List<Map<String, Object>> listBizTypeFields,
			Map<String, Object> eleMap, int i) throws Exception {
		
		if(0==i){
			this.layoutJs  = getEditForm(listBizTypeFields,(List<BaseExt>)eleMap.get("hidden"));
		}else{
			this.layoutJs  = getQueryForm((List<BaseExt>)eleMap.get("query"),(String)eleMap.get("flow_tmpl_id"));
		}
	}

	/**
	 * @param approve_role 
	 * @param field
	 * @return
	 * @throws Exception 
	 */
	private String getQueryForm(List<BaseExt> ExtObj, String flow_tmpl_id) throws Exception {
		String layoutType = "east";
		boolean hasAppendBtn = false;  //判断是否已填充过按钮
		boolean hasBlank = true;   //判断控件是否完全填充
		int rowNum = 0;  //行数
		int num = ExtObj.size();
		if(num % colNum ==0){
			this.columnWidth = 0.22;
			rowNum = num / colNum;
			hasBlank = false;
		}else{
			rowNum = (num / colNum)+1;
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("[").append(enter);
		if (layoutType.equals("north")) {
			//开始循环列
			//此判断用户处理控件布满的情况放置查询按钮
			//创建查询按钮
			Button btn = new Button(ExtConstant.EXT_BUTTON_TYPE_QUE);
			btn.setXtype("button");
			btn.setHandler("doQuery();");
			if (layoutType.equals("north")) {
				for (int i = 0; i < colNum ; i++) {
					buffer.append("{");
					buffer.append("layout : '").append(this.layout).append("',").append(enter);
					buffer.append("columnWidth : ").append(this.columnWidth).append(",").append(enter);
					buffer.append("labelWidth : ").append(this.labelWidth).append(",").append(enter);
					buffer.append("border : ").append(this.border).append(",").append(enter);
					buffer.append("items :[").append(enter);
					
					for (int j = 0; j < rowNum; j++) {
						if((rowNum*i+j) < ExtObj.size()){
							BaseExt ext = ExtObj.get(   rowNum*i+j    );
							buffer.append(ext.output());
							if(j != rowNum-1){
								buffer.append(",").append(enter);
							}else {
								buffer.append(enter);
							}
						}else{
							//添加查询按钮
							if(!hasAppendBtn){
								//开始判断是否需要逗号    ??
								if(j == 0 && buffer.toString().endsWith(")"))
									buffer.append(",");
								buffer.append(btn.output());
								hasAppendBtn  = true;
							}
						}
					}
					
					buffer.append("]").append(enter);
					buffer.append("}");
					if(i != colNum-1){
						buffer.append(",").append(enter);
					}else{
						buffer.append(enter);
					}
				}
				if(!hasBlank && !hasAppendBtn){
					this.columnWidth = 0.15;
					buffer.append(",{");
					buffer.append("layout : '").append(this.layout).append("',").append(enter);
					buffer.append("columnWidth : ").append(this.columnWidth).append(",").append(enter);
					buffer.append("border : ").append(this.border).append(",").append(enter);
					buffer.append("items :[").append(enter);
					buffer.append(btn.output()).append(enter);
					buffer.append("]").append(enter);
					buffer.append("}");
				}
			}else{
				for (int i = 0; i < ExtObj.size(); i++) {
					BaseExt ext = ExtObj.get(i);
					buffer.append(ext.output());
					if(i != ExtObj.size()-1){
						buffer.append(",").append(enter);
					}else {
						buffer.append(enter);
					}
				}
			}
		
		}else {
			for (int j = 0; j < ExtObj.size(); j++) {
				BaseExt ext = ExtObj.get(j);
				buffer.append(ext.output());
				if(j !=  ExtObj.size()-1){
					buffer.append(",").append(enter);
				}else {
					buffer.append(enter);
				}
			}
		}
		if(ExtObj!=null&&ExtObj.size()!=0){
			buffer.append(",");
		}
		if(GlobalUtil.isNotNull(flow_tmpl_id)){
			buffer.append(" new FlowStatusCombo({anchor : '91%'})");
		}else{
			buffer.append(" {xtype:'hidden'} ");
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * @param tempForm
	 * @param list
	 * @param hiddenField 
	 * @return
	 * @throws Exception 
	 */
	private String getEditForm(
			List<Map<String, Object>> list, List<BaseExt> hiddenField) throws Exception {
		StringBuffer buffer = new StringBuffer();
		int num = 0;
		buffer.append("[");
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> bizTypeField = list.get(i);
			String type = GlobalUtil.getStringValue(bizTypeField, "col_biz_type_cd");
			String type_name = GlobalUtil.getStringValue(bizTypeField, "col_biz_type_desc");
			List<BaseExt> extControl = (List<BaseExt>) bizTypeField.get("fields");
			if (extControl != null && extControl.size() > 0) {
				if (!buffer.toString().endsWith("[")) {
					buffer.append(",");
				}
				FieldSet set = new FieldSet();
				set.setId("field"+num);
				set.setTitle(type_name);
				set.setLayout("column");
				set.setCollapsed(false);
				set.setCollapsible(false);
				String items = createLayout(extControl);
				set.setItem(items);
				buffer.append(set.output());
			}
			num ++;
		}
		if (hiddenField.size() > 0) {
			if (!buffer.toString().endsWith(",")&& !buffer.toString().endsWith("[")) {
				buffer.append(",");
			}
			for (int i = 0; i < hiddenField.size(); i++) {
				buffer.append(hiddenField.get(i).output());
				if (i != hiddenField.size()-1) {
					buffer.append(",");
				}
			}
		}
		buffer.append("]");
		return buffer.toString();
	}

	@Override
	public String output()throws Exception {
		return this.layoutJs;
	}
	
//	/**
//	 * 生成查询表单布局
//	 * @param queryObj 查询条件控件集合
//	 * @throws Exception
//	 */
//	private String  getQueryFormLayout(List<BaseExt>  ExtObj,String layoutType,String approve_role) throws Exception{
//		//计算查询条件布局
//		boolean hasAppendBtn = false;  //判断是否已填充过按钮
//		boolean hasBlank = true;   //判断控件是否完全填充
//		int rowNum = 0;  //行数
//		int num = ExtObj.size();
//		if(num % colNum ==0){
//			this.columnWidth = 0.22;
//			rowNum = num / colNum;
//			hasBlank = false;
//		}else{
//			rowNum = (num / colNum)+1;
//		}
//		
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("[").append(enter);
//		
//		
//		if (layoutType.equals("north")) {
//			
//		
//			
//			//开始循环列
//			
//			//此判断用户处理控件布满的情况放置查询按钮
//			
//			//创建查询按钮
//			Button btn = new Button(ExtConstant.EXT_BUTTON_TYPE_QUE);
//			btn.setXtype("button");
//			btn.setHandler("doQuery();");
//			
//			if (layoutType.equals("north")) {
//				for (int i = 0; i < colNum ; i++) {
//					buffer.append("{");
//					buffer.append("layout : '").append(this.layout).append("',").append(enter);
//					buffer.append("columnWidth : ").append(this.columnWidth).append(",").append(enter);
//					buffer.append("labelWidth : ").append(this.labelWidth).append(",").append(enter);
//					buffer.append("border : ").append(this.border).append(",").append(enter);
//					buffer.append("items :[").append(enter);
//					
//					for (int j = 0; j < rowNum; j++) {
//						if((rowNum*i+j) < ExtObj.size()){
//							BaseExt ext = ExtObj.get(   rowNum*i+j    );
//							buffer.append(ext.output());
//							if(j != rowNum-1){
//								buffer.append(",").append(enter);
//							}else {
//								buffer.append(enter);
//							}
//						}else{
//							//添加查询按钮
//							if(!hasAppendBtn){
//								//开始判断是否需要逗号    ??
//								if(j == 0 && buffer.toString().endsWith(")"))
//									buffer.append(",");
//								buffer.append(btn.output());
//								hasAppendBtn  = true;
//							}
//						}
//					}
//					
//					buffer.append("]").append(enter);
//					buffer.append("}");
//					if(i != colNum-1){
//						buffer.append(",").append(enter);
//					}else{
//						buffer.append(enter);
//					}
//				}
//				if(!hasBlank && !hasAppendBtn){
//					this.columnWidth = 0.15;
//					buffer.append(",{");
//					buffer.append("layout : '").append(this.layout).append("',").append(enter);
//					buffer.append("columnWidth : ").append(this.columnWidth).append(",").append(enter);
//					buffer.append("border : ").append(this.border).append(",").append(enter);
//					buffer.append("items :[").append(enter);
//					buffer.append(btn.output()).append(enter);
//					buffer.append("]").append(enter);
//					buffer.append("}");
//				}
//			}else{
//				for (int i = 0; i < ExtObj.size(); i++) {
//					BaseExt ext = ExtObj.get(i);
//					buffer.append(ext.output());
//					if(i != ExtObj.size()-1){
//						buffer.append(",").append(enter);
//					}else {
//						buffer.append(enter);
//					}
//				}
//			}
//		
//		}else {
//			for (int j = 0; j < ExtObj.size(); j++) {
//				BaseExt ext = ExtObj.get(j);
//				buffer.append(ext.output());
//				if(j !=  ExtObj.size()-1){
//					buffer.append(",").append(enter);
//				}else {
//					buffer.append(enter);
//				}
//			}
//		}
//		buffer.append(",");
//		if ("01".equals(approve_role)) {
//			buffer.append(" new FlowStatusCombo({anchor : '91%',Etype : '01'})");
//		}else if("02".equals(approve_role)){
//			buffer.append(" new FlowStatusCombo({anchor : '91%',Etype : '02'})");
//		}else{
//			buffer.append(" new FlowStatusCombo({anchor : '91%',Etype : ''})");
//		}
//		buffer.append("]");
//		return buffer.toString();
//	}
//	
	/**
	 * 生成新增／修改表单布局
	 * @param queryObj 查询条件控件集合
	 * @throws Exception
	 * 
	 * ××××注：目前没有区分textArea 的控件类型××××
	 * 
	 * 窗体大小　随控件数自动计算
	 * 布局计算标准：　系统默认使用form布局，默认＜= 3列，最多４列，每列最多 10 个控件
	 * １，每列少于10个控件的情况下自动减少列
	 * ２，当控件数量大于30的情况自动新增一列
	 * 
	 */
	private String  getWindowLayout(Map<String,List<BaseExt>> extControlMap,List<BaseExt> extHideControlList) throws Exception{
		StringBuffer buffer = new StringBuffer();
		int num = 0;
		int size = extControlMap.size();
		buffer.append("[");
		for (Iterator<String> iterator = extControlMap.keySet().iterator(); iterator.hasNext();) {
			String type = iterator.next();
			List<BaseExt> extControl = extControlMap.get(type);
			if (extControl != null && extControl.size() > 0) {
				FieldSet set = new FieldSet();
				set.setId("field"+num);
				set.setTitle(type);
				set.setLayout("column");
				set.setCollapsed(false);
				set.setCollapsible(false);
				String items = createLayout(extControl);
				set.setItem(items);
				buffer.append(set.output());
				
				if(size==1){
					buffer.append(",");
				}else{
					if (num != size-1 && !buffer.toString().endsWith("[")) {
						buffer.append(",");
					}
				}
			}
			num ++;
		}
		if (extHideControlList.size() > 0) {
			if (!buffer.toString().endsWith(",")&& !buffer.toString().endsWith("[")) {
				buffer.append(",");
			}
			for (int i = 0; i < extHideControlList.size(); i++) {
				buffer.append(extHideControlList.get(i).output());
				if (i != extHideControlList.size()-1) {
					buffer.append(",");
				}
			}
		}
		buffer.append("]");
		return buffer.toString();
	}
	
	/**
	 * 布局创建类
	 * @param ExtObj   控件集合
	 * @param rowNum　行数
	 * @param colNum　列数
	 * @throws Exception
	 */
	private String  createFormLayout(List<BaseExt>  ExtObj,int rowNum,int colNum) throws Exception{
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < colNum ; i++) {
			buffer.append("{");
			buffer.append("layout : '").append(this.layout).append("',").append(enter);
			buffer.append("columnWidth : ").append(this.columnWidth).append(",").append(enter);
			buffer.append("labelWidth : ").append(this.labelWidth).append(",").append(enter);
			buffer.append("border : ").append(this.border).append(",").append(enter);
			buffer.append("items :[").append(enter);
			
			for (int j = 0; j < rowNum; j++) {
				if(i+j < ExtObj.size()){
					BaseExt ext = ExtObj.get(i+j);
					buffer.append(ext.output());
					if(j == rowNum-1){
						buffer.append(",");
					}
				}
			}
			
			buffer.append("]").append(enter);
			buffer.append("}");
			if(i == colNum-1){
				buffer.append(enter);
			}else{
				buffer.append(",").append(enter);
			}
		}
		return buffer.toString();
	}
	
	
	private String createLayout(List<BaseExt>  ExtObj) throws Exception{
		int num = ExtObj.size();   //控件个数,
		//默认四列　
		this.columnWidth = .25;
		int columns = 4;
		//int rows = num%columns==0 ? (num/columns) : (num/columns+1);
		int rows = num/columns==0?1:num/columns;
		//剩余多少字段
		int oddSize =num/columns==0 ? 0:num%columns;
		 
		int last = 0;
		int index = 0;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < columns ; i++) {
			buffer.append("{");
			buffer.append("layout : '").append(this.layout).append("',").append(enter);
			buffer.append("columnWidth : ").append(this.columnWidth).append(",").append(enter);
			buffer.append("labelWidth : ").append(this.labelWidth).append(",").append(enter);
			buffer.append("border : ").append(this.border).append(",").append(enter);
			buffer.append("items :[").append(enter);
			
			//循环向column布局中添加组件
			for (int j = 0; j < rows; j++) {
				if(j>0){
					buffer.append(",");
				}
				if(index>=num){
					break;
				}
				//列添加组件
				BaseExt ext = ExtObj.get(index);
				buffer.append(ext.output());
				index++;
			}
			if(last<oddSize){
				last++;
				if(index>0){
					buffer.append(",");
				}
				BaseExt ext = ExtObj.get(index++);
				buffer.append(ext.output());
			} 
			//以前的布局方式
//			for (int j = 0; j < rows; j++) {
//				if((rows*i+j) < ExtObj.size()){
//					BaseExt ext = ExtObj.get(rows*i+j);
//					buffer.append(ext.output());
//					if(j != rows-1 && (rows*i+j) != ExtObj.size()-1){
//						buffer.append(",");
//					}
//				}
//			}
			
			buffer.append("]").append(enter);
			buffer.append("}");
			if(i == columns-1){
				buffer.append(enter);
			}else{
				buffer.append(",").append(enter);
			}
		}
		return buffer.toString();
	}

}

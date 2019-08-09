package com.shuhao.clean.apps.meta.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.PageButtonDao;
import com.shuhao.clean.apps.meta.entity.UppPageButton;
import com.shuhao.clean.apps.meta.service.IPageButtonService;
import com.shuhao.clean.apps.meta.service.IMetadataService;
import com.shuhao.clean.utils.FileUtil;

/**
 * 
 * 类描述: 元数据[页面按钮对象]业务逻辑实现类 
 * @author bixb
 * 创建时间：2015-1
 */
@Service
public class PageButtonServiceImpl implements IPageButtonService {
	
	@Autowired
	private IMetadataService metadataService = null;
	
	@Autowired
	private PageButtonDao pageButtonDao = null;
	
	/**
	 * 添加页面按钮对象
	 * @param metadata
	 * @param pageButton
	 * @throws Exception
	 */
	public void addPageButton(UppPageButton pageButton) throws Exception {
		this.metadataService.addMetadata(pageButton);
		this.pageButtonDao.addPageButton(pageButton);
	}
	
	
	/**
	 * 根据id获取页面按钮对象
	 * @param button_id
	 * @return
	 * @throws Exception
	 */
	public UppPageButton getPageButtonById(String button_id) throws Exception {
		return this.pageButtonDao.getPageButtonById(button_id);
	}
	
	/**
	 * 保存页面按钮对象
	 * @param pageButton
	 * @throws Exception
	 */
	public void savePageButton(UppPageButton pageButton) throws Exception {
		this.metadataService.saveMetadata(pageButton);
		this.pageButtonDao.savePageButton(pageButton);
	}
	
	/**
	 * 删除页面按钮对象
	 * @param button_id
	 * @throws Exception
	 */
	public void deletePageButton(String button_id) throws Exception {
		this.metadataService.deleteMetadata(button_id);
		this.pageButtonDao.deletePageButton(button_id);
	}
	
	/**
	 * 批量删除页面按钮对象
	 * @param metadata_id
	 * @throws Exception
	 */
	public void deletePageButtonBatch(String[] metadata_ids) throws Exception {
		if(metadata_ids.length == 0){
			return;
		}
		String del_para_str = "";
		for(int i = 0;i<metadata_ids.length;i++){
			if(i == metadata_ids.length -1){
				del_para_str = del_para_str + "'"+metadata_ids[i]+"'" ;
			}else{
				del_para_str =  del_para_str + "'"+metadata_ids[i]+"',"  ;
			}
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("del_para_str", del_para_str);
		this.pageButtonDao.deletePageButtonBatch(params);
		this.metadataService.deleteMetadataBatch(metadata_ids);
	}
	
	/**
	 * 查询关联元数据
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryRelaMetadata(Map<String, Object> params) throws Exception{
		return this.pageButtonDao.queryRelaMetadata(params);
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageButtonService#queryIcons()
	 */
	public List<Map<String, Object>> queryIcons() {
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("icon_path", " ");
		map.put("icon_desc", "不显示图标");
		dataList.add(map);
		
		BufferedReader br = null;
		try {
			String pathname = FileUtil.getWebRootPath()+File.separator+"public"+File.separator+"css"+File.separator+"icon.css";
			br = new BufferedReader(new FileReader(pathname));// 建立BufferedReader对象，并实例化为br
			String Line = br.readLine();// 从文件读取一行字符串
			
			while (Line != null) {
				if(Line.equals("")){
					break;
				}
				
				String iconPath = Line.substring(Line.indexOf(".")+1, Line.indexOf("{"));
				map = new HashMap<String, Object>();
				map.put("icon_path", iconPath);
				map.put("icon_desc", iconPath);
				dataList.add(map);
				
				Line = br.readLine();// 从文件中继续读取一行数据
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();// 关闭BufferedReader对象
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dataList;
	}
	
	/**
	 * 查询页面按钮对象列表
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryPageButtons(Map<String, Object> params) throws Exception{
		List<Map<String, Object>>  dataList = this.pageButtonDao.queryPageButtons(params);
		return dataList;
	}
	
	/***
	 *  获取字段顺序列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getButtonsForDisOrder(Map<String, Object> params) throws Exception{
		return pageButtonDao.getButtonsForDisOrder(params);
	}
	
	/**
	 * 更新排序
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateDisOrder(Map<String, Object> params) throws Exception{
		//解析参数
		String paramStr =  String.valueOf(params.get("orderParam"));
		String[] fieldArr = paramStr.split(";");
		Map<String, Object> param = new HashMap<String, Object>();
		String metadata_id = "";
		String display_order = "";
		for(int i = 0;i<fieldArr.length;i++){
			metadata_id =  fieldArr[i].split(",")[0];
			display_order = fieldArr[i].split(",")[1];
			//更新字段表
			param.put("display_order", display_order);
			param.put("metadata_id", metadata_id);
			param.put("user_id", params.get("user_id"));
			pageButtonDao.updateDisOrder(param);
		}
		
	}
}

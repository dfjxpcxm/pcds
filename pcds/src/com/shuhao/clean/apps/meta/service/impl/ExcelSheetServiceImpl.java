package com.shuhao.clean.apps.meta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.ExcelSheetDao;
import com.shuhao.clean.apps.meta.dao.UppDicRelaDao;
import com.shuhao.clean.apps.meta.entity.UppDicRela;
import com.shuhao.clean.apps.meta.entity.UppExcelColumn;
import com.shuhao.clean.apps.meta.entity.UppExcelSheet;
import com.shuhao.clean.apps.meta.entity.UppTableColumn;
import com.shuhao.clean.apps.meta.service.IColumnService;
import com.shuhao.clean.apps.meta.service.IExcelColumnService;
import com.shuhao.clean.apps.meta.service.IExcelSheetService;
import com.shuhao.clean.apps.meta.service.IMetadataService;
import com.shuhao.clean.utils.UID;

/**
 * 
 * 类描述: Excel Sheet页元数据业务逻辑接实现类
 * @author chenxiangdong
 * 创建时间：2015-1-13下午02:58:50
 */
@Service
public class ExcelSheetServiceImpl implements IExcelSheetService {
	
	@Autowired
	private IMetadataService metadataService;
	
	@Autowired
	private IExcelColumnService excelColumnService;
	
	@Autowired
	private ExcelSheetDao excelSheetDao;
	
	@Autowired
	private UppDicRelaDao dicRelaDao;
	
	@Autowired
	private IColumnService columnService;
	
	/**
	 * 添加Sheet页对象
	 * @param sheet
	 * @throws Exception
	 */
	public void addSheet(UppExcelSheet sheet) throws Exception {
		this.metadataService.addMetadata(sheet);
		this.excelSheetDao.addSheet(sheet);
		if(sheet.getTable().getTable_id() != null) {
			this.dicRelaDao.addRela(new UppDicRela(sheet.getSheet_id(), sheet.getTable().getTable_id()));
			//将表的字段默认添加为sheet页列
			List<UppTableColumn> columnList = this.columnService.listTableColumns(sheet.getTable().getTable_id());
			for (UppTableColumn uppTableColumn : columnList) {
				UppExcelColumn ec = this.packToExcelColumn(uppTableColumn, sheet);
				this.excelColumnService.addExcelColumn(ec);
			}
		}
	}
	
	/**
	 * 将表中的列转换为sheet页的默认列对象
	 * @param tc
	 * @return
	 */
	protected UppExcelColumn packToExcelColumn(UppTableColumn tc, UppExcelSheet sheet) {
		UppExcelColumn ec = new UppExcelColumn();
		ec.setXls_col_id(UID.next());
		ec.setXls_col_name(tc.getColumn_name());
		ec.setXls_col_label(tc.getColumn_desc()==null ? tc.getColumn_name() : tc.getColumn_desc());
		ec.setCreate_user_id(sheet.getCreate_user_id());
		ec.setPrt_metadata_id(sheet.getSheet_id());
		ec.setMd_cate_cd("XCL");
		ec.setIs_display("Y");
		ec.setDisplay_order(tc.getDisplay_order() == null ? "1" : tc.getDisplay_order());
		ec.setStatus_cd(sheet.getStatus_cd());
		ec.setTableColumn(tc);
		return ec;
	}
	
	
	/**
	 * 根据id获取sheet页对象
	 * @param sheet_id
	 * @return
	 * @throws Exception
	 */
	public UppExcelSheet getSheetById(String sheet_id) throws Exception {
		return this.excelSheetDao.getSheetById(sheet_id);
	}
	
	
	/**
	 * 保存sheet页对象
	 * @param sheet
	 * @throws Exception
	 */
	public void saveSheet(UppExcelSheet sheet) throws Exception {
		this.metadataService.saveMetadata(sheet);
		this.excelSheetDao.saveSheet(sheet);
		
		dicRelaDao.deleteRela(sheet.getSheet_id());
		if(sheet.getTable().getTable_id() != null) {
			dicRelaDao.addRela(new UppDicRela(sheet.getSheet_id(), sheet.getTable().getTable_id()));
		}
	}
	
	
	/**
	 * 删除sheet页对象
	 * @param sheet_id
	 * @throws Exception
	 */
	public void deleteSheet(String sheet_id) throws Exception {
		List<UppExcelColumn> colList = excelColumnService.queryExcelColumnBySheetId(sheet_id);
		for (UppExcelColumn col : colList) {
			this.excelColumnService.deleteExcelColumn(col.getXls_col_id());
		}
		this.metadataService.deleteMetadata(sheet_id);
		this.excelSheetDao.deleteSheet(sheet_id);
		this.dicRelaDao.deleteRela(sheet_id);
	}
	
	
	/**
	 * 根据Excel id查询sheet页列表
	 * @param table_id
	 * @return
	 * @throws Exception
	 */
	public List<UppExcelSheet> querySheetByExcelId(String excel_id) throws Exception {
		return this.excelSheetDao.querySheetByExcelId(excel_id);
	}
	
}

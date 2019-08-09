package com.shuhao.clean.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcelData {
	
	String fileName = null;
	String sheetName = null;
	int sheetIndex;
	Workbook wb = null;
	
	/**
	 * 构造方法 只给出文件名 默认为第一个表单
	 * 
	 * @param fileName
	 */
	public ReadExcelData(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 构造方法 给出文件名及表单名
	 * 
	 * @param fileName
	 */
	public ReadExcelData(String fileName, String sheetName) {
		this.fileName = fileName;
		this.sheetName = sheetName;
	}

	/**
	 * 构造方法 给出文件名及表单位置
	 * 
	 * @param fileName
	 */
	public ReadExcelData(String fileName, int sheetIndex) {
		this.fileName = fileName;
		this.sheetIndex = sheetIndex;
	}

	/**
	 * 此方法返回Excel中单个表单的数据
	 * 如果没有指定表单名或第几个表单时，则默认为Excel中的第一张表单
	 * 
	 * @return
	 */
	public String[][] getAppointSheetData() {
		String[][] data = null;
		File file = new File(this.fileName);
		try {
			
			this.wb = Workbook.getWorkbook(file);
			Sheet sheet = null;
			if(this.sheetName == null || "".equals(this.sheetName.trim())) {
				sheet = wb.getSheet(this.sheetIndex);
			} else {
				sheet = wb.getSheet(sheetName);
			}
			data = getSheetData(sheet);
			
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
	}

	/**
	 * 得到指定Excel文件中的所有表单中的数据 返回一个list 
	 * 其中的每一项是一个表单的数据 用二维数组存放
	 * @return
	 */
	public List getAllSheetData(){
		List list = null;
		File file = new File(this.fileName);	
		try {
			
			this.wb = Workbook.getWorkbook(file);
			int sheetNum = wb.getNumberOfSheets();
			list = new ArrayList(sheetNum);
			/**
			Sheet[] sheets = wb.getSheets();
			for(int i = 0; i < sheets.length; i++) {
				String[][] data = null;
				Sheet sheet = sheets[i];
				data = getSheetData(sheet);
				list.add(data);
			}
			**/
			for(int i = 0; i < sheetNum; i++) {
				String[][] data = null;
				Sheet sheet = wb.getSheet(i);
				data = getSheetData(sheet);
				list.add(data);
			}

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * 得到单个表单的数据 放在二维数组中返回
	 * 
	 * @param sheet
	 * @return
	 */
	private String[][] getSheetData(Sheet sheet) {
		
		String[][] data = null;
		int rowNum = sheet.getRows();
		int len = 0;
		for(int i = 0; i < rowNum; i++) {
			Cell[] cells = sheet.getRow(i);
			if(!"".equals(cells[0].getContents())){
				len++;
			}
		}
		data = new String[len][];
		
		for(int i = 0; i < len; i++) {
			Cell[] cells = sheet.getRow(i);
			int cellNum = cells.length;
			data[i] = new String[cellNum];
			for(int j = 0; j < cellNum; j++) {
				Cell cell = cells[j];
				data[i][j] = cell.getContents();
			}
		}
		
		return data;
	}
	
}

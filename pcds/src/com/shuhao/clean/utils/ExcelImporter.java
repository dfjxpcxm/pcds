package com.shuhao.clean.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelImporter {
	//private Logger 					logger 			=	Logger.getLogger(this.getClass());
	
	private	InputStream 			_fileIn 			=	null;
	private	Workbook				_book				=	null;
	
	private String					_fileName			=	null;
	private String					_sheetName			=	null;
	private int						_sheetIndex			=	0;
		
	/**
	 * 构造方法 给出文件名，默认取Excel第一个sheet页
	 * @throws IOException 
	 */
	public ExcelImporter(String fileName){
		this._fileName	=	fileName;
	}
	
	/**
	 * 构造方法 给出文件名及表单位置
	 * @param sheetName 指定sheet名称；如果sheet名称不存在，则抛出异常
	 */
	public ExcelImporter(String fileName, String sheetName){
		this._fileName	=	fileName;
		this._sheetName	=	sheetName;
	}
	
	/**
	 * 构造方法 给出文件名及表单位置
	 * @param sheetIndex (0-based)指定sheet页位置
	 */
	public ExcelImporter(String fileName, int sheetIndex){
		this._fileName		=	fileName;
		this._sheetIndex	=	sheetIndex;
	}
	
	
	/**
	 * 此方法返回Excel中单个表单的数据
	 * 如果没有指定表单名或第几个表单时，则默认为Excel中的第一张表单
	 * 
	 * @return
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public String[][] getAppointSheetData() throws IOException, InvalidFormatException{
		String[][]	data	=	null;
		
		try{
			createWorkbook();
			
			//尝试使用sheetName
			if(this._sheetName != null && !"".equals(this._sheetName)){
				this._sheetIndex = this._book.getSheetIndex(this._sheetName);
			}
			
			//如果找不到，则直接使用第一个sheet页
			if(this._sheetIndex < 0){
				this._sheetIndex	=	0;
			}
			
			data	=	this.getSheetData(this._book.getSheetAt(this._sheetIndex));
		}
		finally{
			closeFile();
		}
		
		return data;
	}
	
	/**
	 * 得到指定Excel文件中的所有表单中的数据 返回一个list 
	 * 其中的每一项是一个表单的数据 用二维数组存放
	 * @return
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public List<String[][]> getAllSheetData() throws InvalidFormatException, IOException{

		List<String[][]>	data		=	null;
		String[][]			sheetData	=	null;
		
		try{
			createWorkbook();
			
			int	sheetNum	=	this._book.getNumberOfSheets();
			data			=	new	ArrayList<String[][]>(sheetNum);
			
			for(int i=0; i < sheetNum; i++){
				sheetData	=	this.getSheetData(this._book.getSheetAt(i));
				data.add(sheetData);
			}
		}
		finally{
			closeFile();
		}
		
		return data;
	}
	/**
	 * 得到单个表单的数据 放在二维数组中返回
	 * 
	 * @param sheet
	 * @return
	 */
	private String[][] getSheetData(Sheet sheet) {
		
		int	rowCount	=	sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;
		int	colCount	=	0;
		
		if(rowCount < 1)
			return null;
		
		boolean is2007 = sheet instanceof XSSFSheet; 
		
		String[][] data = new String[rowCount][];
		
		Row				row		=	null;
		Cell			cell	=	null;

		for(int i = 0; i < rowCount; i++){
			
			//如果未定义行，则返回空
			row		=	sheet.getRow(i);
			if(row == null){
				continue;
			}
			
			colCount		=	row.getLastCellNum();
			if(colCount < 1){
				continue;
			}
			
			data[i]	=	new	String[colCount];
			for(int j=0; j < colCount; j++){
				cell = row.getCell(j);
				
				data[i][j]	=	getCellValueAsString(cell, is2007);
			}
		}
		
		return data;
	}
	
	private String getCellValueAsString(Cell cell, boolean is2007){
		//如果为空返回空值
		if(cell == null){
			return "";
		}
		
		int		cellType	=	cell.getCellType();
		String	data		=	null;

		switch(cellType)
		{
		case Cell.CELL_TYPE_STRING:
			data	=	cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if(is2007){
				data	=	((XSSFCell)cell).getRawValue();
			}
			else{
				data	=	Double.toString(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BLANK:
			data	=	"";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			data	=	cell.getBooleanCellValue() + "";
			break;
		case Cell.CELL_TYPE_ERROR:
			data	=	cell.getErrorCellValue() + "";
			break;
		case Cell.CELL_TYPE_FORMULA:
			data	=	cell.getCellFormula();
			break;
		default:
			break;
		}
		
		return data;
	}
	
	private Workbook createWorkbook() throws IOException, InvalidFormatException{
		this._fileIn	=	new FileInputStream(this._fileName);
		
		this._book	=	WorkbookFactory.create(_fileIn);
		
		return this._book;
	}
	
	private void closeFile(){
		if(this._book instanceof SXSSFWorkbook){
			//删除临时目录下的临时文件
			((SXSSFWorkbook)this._book).dispose();
		}
		
		if(this._fileIn != null){
			try {
				this._fileIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setFileName(String fileName) {
		this._fileName = fileName;
	}

	public void setSheetIndex(int sheetIndex) {
		this._sheetIndex = sheetIndex;
	}
	
}

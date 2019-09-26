package com.shuhao.clean.utils;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Read2007ExcelWrapper {

	String fileName = null;
	int sheetIndex;
	InputStream input = null;

	public Read2007ExcelWrapper() {

	}

	public Read2007ExcelWrapper(String fileName) {
		this.fileName = fileName;
		try {
			this.input = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Read2007ExcelWrapper(String fileName, int sheetIndex) {
		this.fileName = fileName;
		this.sheetIndex = sheetIndex;
		try {
			this.input = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	//读取2003版
	public String[][] getAppointSheetData() throws Exception {
		String[][] data = null;

		HSSFWorkbook wbs = new HSSFWorkbook(input);
		HSSFSheet sheet = wbs.getSheetAt(sheetIndex);
		int rowNum = sheet.getLastRowNum();
		data = new String[rowNum][];

		for (int j = 0; j < rowNum; j++) {
			HSSFRow row = sheet.getRow(j);
			if (null != row) {
				int cellNum = row.getLastCellNum();
				data[j] = new String[cellNum];
				for (int k = 0; k < cellNum; k++) {
					HSSFCell cell = row.getCell(k);
					if (null != cell) {
						data[j][k] = getStringCellValue(cell);
					}
				}
			}
		}

		return data;
	}

	//读取2007版本以上
	public List<Map<String, Object>> getHardSheetData() throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		XSSFWorkbook xwb = new XSSFWorkbook(input);
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(sheetIndex);

		int rowNum = sheet.getLastRowNum();

		for (int j = 0; j < rowNum; j++) {
			XSSFRow row = sheet.getRow(j);
			if (null != row) {
				int cellNum = row.getLastCellNum();
				Map<String, Object> map = new HashMap<String, Object>();
				for (int k = 0; k < cellNum; k++) {
					XSSFCell cell = row.getCell(k);
					if (null != cell) {
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC: // 数字
							map.put("col_" + k, cell.getNumericCellValue());
							break;
						case HSSFCell.CELL_TYPE_STRING: // 字符串
							map.put("col_" + k, cell.getStringCellValue());
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
							map.put("col_" + k, cell.getBooleanCellValue());
							break;
						case HSSFCell.CELL_TYPE_FORMULA: // 公式
							map.put("col_" + k, cell.getCellFormula());
							break;
						case HSSFCell.CELL_TYPE_BLANK: // 空值
							map.put("col_" + k, "");
							break;
						case HSSFCell.CELL_TYPE_ERROR: // 故障
							map.put("col_" + k, "");
							break;
						default:
							break;
						}
					}
				}
				list.add(map);
			}
		}
		return list;
	}
	
	//兼容读取2003和2007版本以上
	public List<Map<String, Object>> getNormalSheetData() throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(fileName);
		} catch (Exception e) {
			wb = new HSSFWorkbook(input);
		}
		
		Sheet sheet = wb.getSheetAt(sheetIndex);

		int rowNum = sheet.getLastRowNum();

		for (int j = 0; j < rowNum; j++) {
			Row row = sheet.getRow(j);
			if (null != row) {
				int cellNum = row.getLastCellNum();
				Map<String, Object> map = new HashMap<String, Object>();
				for (int k = 0; k < cellNum; k++) {
					Cell cell = row.getCell(k);
					if (null != cell) {
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC: // 数字
							map.put("col_" + k, cell.getNumericCellValue());
							break;
						case HSSFCell.CELL_TYPE_STRING: // 字符串
							map.put("col_" + k, cell.getStringCellValue());
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
							map.put("col_" + k, cell.getBooleanCellValue());
							break;
						case HSSFCell.CELL_TYPE_FORMULA: // 公式
							map.put("col_" + k, cell.getCellFormula());
							break;
						case HSSFCell.CELL_TYPE_BLANK: // 空值
							map.put("col_" + k, "");
							break;
						case HSSFCell.CELL_TYPE_ERROR: // 故障
							map.put("col_" + k, "");
							break;
						default:
							break;
						}
					}
				}
				list.add(map);
			}
		}
		return list;
	}

	private String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		if (cell == null)
			return "";
		
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue().trim();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			strCell = cell.getCellFormula();
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}		
		return strCell;
	}
	
	/**
	 * 读取2003版以下的excel文档
	 * @param filePath
	 * @param rowNum
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,List<List<String>>>> readXls(String filePath,int rowNum) throws Exception{
		return readXls(filePath, rowNum, false);
	}
	
	/**
	 * 校验是否是Numeri类型,校验尾数是否以00+结尾
	 * @param s
	 * @return
	 */
	public static boolean validNumeric(String s){
		if(s.indexOf(".")>-1){
			return !Pattern.matches("^0*0$", s.substring(s.indexOf(".")+1));
		}else{
			return false;
		}
	}
	
	//xls
	public static String getSimpleCellValue(HSSFCell cell){
		if(cell!=null){
			return cell.toString();
		}
		return "";
	}
	
	//xlsx
	public static String getSimpleCellValue(XSSFCell cell){
		if(cell!=null){
			return cell.toString();
		}
		return "";
	}

	/**
	 * 读取2003版以下的excel文档
	 * 问题如下：
	 * 空行不读
	 * 以第一行为基础生成列数
	 * @param filePath 文件路径 
	 * @param rowNum  开始读取数据的行号
	 * @return excel文档内容
	 * @throws Exception 
	 * 
	 */
	public static List<Map<String,List<List<String>>>> readXls(String filePath,int rowNum,boolean isNullCellRow) throws Exception{
		List<Map<String,List<List<String>>>> dataList = new ArrayList<Map<String,List<List<String>>>>();
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
		HSSFWorkbook workBook = new HSSFWorkbook(fs);
		FormulaEvaluator evaluator = workBook.getCreationHelper().createFormulaEvaluator();
		for (int i = 0; i < workBook.getNumberOfSheets(); i++) { //sheet循环
			HSSFSheet sheet = workBook.getSheetAt(i);
			String sheetName = sheet.getSheetName();
			int rows = sheet.getPhysicalNumberOfRows(); // 获得行数
			List<Integer> dateCols = new ArrayList<Integer>(); //存放日期类型列索引
			Map<String,List<List<String>>> sheetMap = new HashMap<String,List<List<String>>>();
			if (rows > rowNum) {
				//head
				int cells = 0;
				HSSFRow header = sheet.getRow(rowNum>0 ? (rowNum-1) : 0);
				if(header!=null){
					cells = header.getLastCellNum();//获得列数
					//将列中的所有日期类型放入dateCols
					for (int j = 0; j < cells; j++) {
						if(header.getCell(j) !=null && getSimpleCellValue(header.getCell(j)).indexOf("[D]")>-1){
							dateCols.add(j);
						}
					}
				}
				
				List<List<String>> recordList = new ArrayList<List<String>>();
				sheet.getMargin(HSSFSheet.TopMargin);
				for (int j = rowNum; j < rows; j++) { // 行循环
					List<String> rowList = new ArrayList<String>();
					HSSFRow row = sheet.getRow(j);
					
					if (row != null) {
						if(cells == 0){
							cells = row.getLastCellNum();//获得列数
						}
						
						for (int k = 0; k < cells; k++) {// 列循环
							HSSFCell cell = row.getCell(k);
							//计算
							CellValue cellValue = null;
							try {
								cellValue = evaluator.evaluate(cell);
							} catch (Exception e) {
								throw new Exception("第"+(k+1)+"列["+cell.toString()+"],解析失败,请修改excel.");
							}
							
							String value = "";
							if (cell != null && cellValue != null) {
								switch (cellValue.getCellType()) {
								case HSSFCell.CELL_TYPE_NUMERIC: // 数值型
									if (HSSFDateUtil.isCellDateFormatted(cell)) {
										//如果是date类型则 ，获取该cell的date值
										value=new SimpleDateFormat("yyyy-MM-dd").format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
									} else {
										//是否包含日期
										if(j >= rowNum+1 && dateCols.contains(k)){
											value = DateTimeUtil.getSimpleDate(cell.toString().trim());
										}else{
											//纯数字
											//校验尾数是否以00+结尾
											if(validNumeric(cell.toString())){
												double d = cell.getNumericCellValue();
												DecimalFormat df = new DecimalFormat("0.00");//防止科学计数
												value = df.format(d);
											}else{
												//不是double类型时
												String cellv = cell.toString();
												if(cellv.indexOf(".")>-1){
													value = cellv.substring(0,cellv.lastIndexOf("."));
												}else{
													value = cellv.trim();
												}
											}
										}
									}
									break;
								case HSSFCell.CELL_TYPE_STRING: // 字符串型,去空格
									//是否是日期字符串
									if(j >= rowNum+1 && dateCols.contains(k)){
										value = DateTimeUtil.getSimpleDate(cell.toString().trim());
									}else{
										value = cell.getRichStringCellValue().toString().trim();
									}
									break;
								case HSSFCell.CELL_TYPE_FORMULA://公式型
									value = String.valueOf(cell.getNumericCellValue());
									if (value.equals("NaN")) {//如果获取的数据值为非法值,则转换为获取字符串
										value = cell.getRichStringCellValue().toString().trim();
									}
									break;
								case HSSFCell.CELL_TYPE_BOOLEAN://布尔
									value = ""+cell.getBooleanCellValue();
									break;
								case HSSFCell.CELL_TYPE_BLANK: // 空值
									value = "";
									break;
								case HSSFCell.CELL_TYPE_ERROR: // 故障
									value = "";
									break;
								default:
									//value = cell.getRichStringCellValue().toString();
									value = cell.toString().trim(); //直接调用toString()
								}
							}
							
							//首列为null时读下一行
							if(isNullCellRow && k==0 && value.equals("")){
								break;
							}
							rowList.add(value);
						}
						recordList.add(rowList);
					}
				}
				sheetMap.put(sheetName, recordList);
			}
			dataList.add(sheetMap);
		}
		return dataList;
	}
	
	/**
	 * 读取2007版以上的excel文档
	 * @param filePath 文件路径
	 * @param rowNum  开始读取数据的行号
	 * @return excel文档内容
	 * @throws Exception 
	 * 
	 */
	public static List<Map<String,List<List<String>>>> readXlsx(String filePath,int rowNum,boolean isNullCellRow) throws Exception{
		List<Map<String,List<List<String>>>> dataList = new ArrayList<Map<String,List<List<String>>>>();
		XSSFWorkbook workBook = new XSSFWorkbook(filePath);
		XSSFFormulaEvaluator evaluator = workBook.getCreationHelper().createFormulaEvaluator();   //处理公式
		for (int i = 0; i < workBook.getNumberOfSheets(); i++) { //sheet循环
			// 创建工作表
			XSSFSheet sheet = workBook.getSheetAt(i);
			String sheetName = sheet.getSheetName();
//			List<String> head=GetXlsxHead(sheet);
			int rows = sheet.getLastRowNum(); // 获得行数
			Map<String,List<List<String>>> sheetMap = new HashMap<String,List<List<String>>>();
			List<Integer> dateCols = new ArrayList<Integer>(); //存放日期类型列索引
			if (rows >= rowNum) {
				//head
				int cells = 0;
				XSSFRow header = sheet.getRow(rowNum>0 ? (rowNum-1) : 0);
				if(header!=null){
					cells = header.getLastCellNum();//获得列数
					//将列中的所有日期类型放入dateCols
					for (int j = 0; j < cells; j++) {
						if(header.getCell(j) !=null && getSimpleCellValue(header.getCell(j)).indexOf("[D]")>-1){
							dateCols.add(j);
						}
					}
				}
				
				List<List<String>> recordList = new ArrayList<List<String>>();
				sheet.getMargin(HSSFSheet.TopMargin);
				for (int j = rowNum; j <= rows; j++) { // 行循环
					List<String> rowList = new ArrayList<String>();
					XSSFRow row = sheet.getRow(j);
					if (row != null) {
						//计算列数
						if(cells==0){
							cells = row.getLastCellNum();//获得列数
						}
						
						for (int k = 0; k < cells; k++) { // 列循环
							XSSFCell cell = row.getCell(k);
							//计算
							CellValue cellValue = null;
							try {
								cellValue = evaluator.evaluate(cell);
							} catch (Exception e) {
								throw new Exception("第"+(k+1)+"列["+cell.toString()+"],解析失败,请修改excel.");
							}
							
							String value = "";
							if (cell != null && cellValue != null) {
								switch (cellValue.getCellType()) {
								case XSSFCell.CELL_TYPE_NUMERIC: // 数值型
									if (HSSFDateUtil.isCellDateFormatted(cell)) {
										//如果是date类型则 ，获取该cell的date值
										value=new SimpleDateFormat("yyyy-MM-dd").format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
									} else {
										//是否包含日期
										if(j >= rowNum+1 && dateCols.contains(k)){
											value = DateTimeUtil.getSimpleDate(cell.toString().trim());
										}else{
											//纯数字
											//校验尾数是否以00+结尾
											if(validNumeric(cell.toString())){
												double d = cell.getNumericCellValue();
												DecimalFormat df = new DecimalFormat("0.00");//防止科学计数
												value = df.format(d);
											}else{
												//不是double类型时
												String cellv = cell.toString();
												if(cellv.indexOf(".")>-1){
													value = cellv.substring(0,cellv.lastIndexOf("."));
												}else{
													value = cellv.trim();
												}
											}
										}
									}
									break;
								case XSSFCell.CELL_TYPE_STRING: // 字符串型
									//是否是日期字符串
									if(j >= rowNum+1 && dateCols.contains(k)){
										value = DateTimeUtil.getSimpleDate(cell.toString().trim());
									}else{
										value = cell.getRichStringCellValue().toString().trim();
									}
									break;
								case XSSFCell.CELL_TYPE_FORMULA://公式型
									value = String.valueOf(cell.getNumericCellValue());
									if (value.equals("NaN")) {//如果获取的数据值为非法值,则转换为获取字符串
										value = cell.getRichStringCellValue().toString().trim();
									}
									break;
								case XSSFCell.CELL_TYPE_BOOLEAN://布尔
									value = ""+ cell.getBooleanCellValue();
									break;
								case XSSFCell.CELL_TYPE_BLANK: // 空值
									value = "";
									break;
								case HSSFCell.CELL_TYPE_ERROR: // 故障
									value = "";
									break;
								default:
									//value = cell.getRichStringCellValue().toString();
									value = cell.toString().trim();
								}
							}
							
							//首列为null时读下一行
							if(isNullCellRow && k==0 && value.equals("")){
								break;
							}
							
							rowList.add(value);
//							if(!head.get(k).trim().equals(value)){
//							}
						}
						recordList.add(rowList);
					}
				}
				sheetMap.put(sheetName,recordList);
			}
			dataList.add(sheetMap);
		}
		return dataList;
	}
	
	/**
	 * 
	 * @param filePath
	 * @param rowNum
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,List<List<String>>>> readXlsx(String filePath,int rowNum) throws Exception{
		return readXlsx(filePath, rowNum, false);
	}
	
	/**
	 * 获取Excel所有头
	 * @param sheet
	 * @return
	 */
	private static List<String> GetXlsHead(HSSFSheet sheet){
		List<String> head=new ArrayList<String>();
		HSSFRow row = sheet.getRow(0);
		int headcount=100;
		int now=0;
		if(row!=null){
			while(headcount<=100){
				now=100-headcount;
				HSSFCell c = row.getCell(now);
				if(null==c)
				{
					break;
				}
				else
				{
				if (c.getCellType() == HSSFCell.CELL_TYPE_STRING)
					head.add(c.getStringCellValue());
		    		else 
					head.add(String.valueOf((long) c.getNumericCellValue()));
				}
				headcount--;
			}
		}
		return head;
	}
	
	
	
	/**
	 * 获取Excel所有头
	 * @param sheet
	 * @return
	 */
	private static List<String> GetXlsxHead(XSSFSheet sheet){
		List<String> head=new ArrayList<String>();
		XSSFRow row = sheet.getRow(0);
		int headcount=100;
		int now=0;
		if(row!=null){
		while(headcount<=100){
			now=100-headcount;
			XSSFCell c = row.getCell(now);
			if(null==c)
			{
				break;
			}
			else
			{
			if (c.getCellType() == HSSFCell.CELL_TYPE_STRING) 
			head.add(c.getStringCellValue());
    		else 
			head.add(String.valueOf((long) c.getNumericCellValue()));
				
			}
			headcount--;
		}
		}
		return head;
	}


	//读取csv
	public static List<Map<String, List<List<String>>>> readCsv(String fullFileName) {
		String pfileName = fullFileName.substring(fullFileName.lastIndexOf("/")+1);
		List<Map<String, List<List<String>>>> dataList = new ArrayList<>();
		Map<String,List<List<String>>> sheetMap = new HashMap<String,List<List<String>>>();
		List<List<String>> recordList = new ArrayList<List<String>>();
		try {
			String record;
			String enc = "UTF-8";
			BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(fullFileName), enc));
			// 遍历数据行并存储在名为records的ArrayList中，每一行records中存储的对象为一个String数组
			while ((record = file.readLine()) != null) {
				String fields[] = record.trim().split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)",-1);
				List<String> rowList = new ArrayList<String>();
				for (String s : fields){
					s = s.replaceAll("\"","");
					rowList.add(s);
				}
				recordList.add(rowList);
			}
			sheetMap.put(pfileName.substring(0,pfileName.lastIndexOf(".")),recordList);//截取文件名
			dataList.add(sheetMap);
			// 关闭文件
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;
	}

}

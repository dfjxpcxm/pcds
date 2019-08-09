package com.shuhao.clean.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

public class ExportDataUtil {
    public static List pageHeader = Collections.synchronizedList(new ArrayList());

    /**
     * 生成EXCEL工作 
     * @param tableHeader EXCEL表头字符 ,例如  
     * 产品,余额,#cspan,#cspan,日均,#cspan,#cspan,借记卡发卡量,#cspan,#cspan,借记卡消费额,#cspan,#cspan,贷记卡发卡量,#cspan,#cspan,贷记卡消费额,#cspan,#cspan;
     * #rspan,上期 ,计划增长比例,本期计划,上期 ,计划增长比例,本期计划,上期 ,计划增长比例,本期计划,上期 ,计划增长比例,本期计划,上期 ,计划增长比例,本期计划,上期 ,计划增长比例,本期计划

     * @param tableColumnAlign 各列对齐方式,例如   
     * left,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right

     * @param tableInitCellWidth 各列宽度,例如 
     * 178,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98
     * @param tableData EXCEL数据，XML格式,例如 
     * <pre> 
     * <?xml version="1.0"?>
     *	<rows>
     *	<row id='02'><cell>公司客户定期存款</cell><cell>40,000,000</cell><cell></cell><cell></cell><cell>40,000,000</cell><cell>-34.4%</cell><cell>26,250,000</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
     *	<row id='03'><cell>公司客户通知存款</cell><cell>50,000,000</cell><cell></cell><cell></cell><cell>50,000,000</cell><cell>-97.3%</cell><cell>1,341,028</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
     *	<row id='04'><cell>应解汇款及临时存 </cell><cell>712,053</cell><cell>244.5%</cell><cell>2,453,253</cell><cell>708,677</cell><cell>-77.3%</cell><cell>160,635</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
     *	<row id='05'><cell>保证金（不含承兑保证金）</cell><cell>19,042,050</cell><cell>18.3%</cell><cell>22,523,523</cell><cell>19,041,089</cell><cell>-52.9%</cell><cell>8,973,500</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
     *	<row id='06'><cell>短期信用贷款</cell><cell>0</cell><cell></cell><cell></cell><cell>117,857</cell><cell>129.1%</cell><cell>270,000</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
     *	<row id='07'><cell>短期保证贷款</cell><cell>67,300,000</cell><cell>-47.0%</cell><cell>35,653,576</cell><cell>66,610,714</cell><cell>-100.0%</cell><cell>12,500</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
     *	<row id='08'><cell>短期抵押质押贷款</cell><cell>40,000,000</cell><cell></cell><cell></cell><cell>45,714,286</cell><cell>-20.4%</cell><cell>36,375,000</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
     *	<row id='09'><cell>中长期信用贷 </cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
     *	<row id='10'><cell>中长期保证贷 </cell><cell>0</cell><cell></cell><cell>312,343</cell><cell>0</cell><cell></cell><cell>36,285</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
     *	<row id='107'><cell>不良 (  )</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
     *	</rows>
     * </pre>
     * @param tableTitle EXCEL标题,例如 "经营计划填报-产品计划" 
     * @return
     */
    public static HSSFWorkbook getWorkBook(String tableHeader, String tableColumnAlign, String tableInitCellWidth,
            String tableData, String tableTitle) {
        try {
            //表格各列对齐方式
            String[] tableColumnAlignArray = tableColumnAlign.split(",");
            //各列宽度
            String[] tableInitCellWidthArray = tableInitCellWidth.split(",");
            //表头字符串数 
            String[][] tableHeaderArray = getTableHeaderArray(tableHeader, tableColumnAlignArray.length);
            //表格数据
            List dataCellTextList = getDataCellTextList(tableData,0);

            //创建新的Excel 工作 
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();//在Excel工作簿中建一工作表，其名为缺省 ?
            //workbook.setSheetName(0, tableTitle,HSSFWorkbook.ENCODING_UTF_16);//设置中文支持	

            //设置列宽
            for (int i = 0; i < tableInitCellWidthArray.length; i++) {
                sheet.setColumnWidth(i, (Integer.parseInt(tableInitCellWidthArray[i]) * 40));
            }

            //创建报表主标题行	
            HSSFRow row = sheet.createRow((short) 0);
            int mergedRegionNum = sheet.addMergedRegion(new Region(0, (short) (0), 0,
                    (short) (tableColumnAlignArray.length - 1)));
            sheet.getMergedRegionAt(mergedRegionNum);//主标题行合并
            HSSFCell cell = row.createCell(0);

            //报表主标题单元格字体
            HSSFFont font = workbook.createFont();//字体
            font.setFontHeightInPoints((short) 14);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            //报表主标题单元格样式
            HSSFCellStyle cs = workbook.createCellStyle();
            cs.setFont(font);
            cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            cell.setCellStyle(cs);//挷定样式到单元格			
            //单元格类 
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            //设置为中文字 ,应该放在setCellValue方法之前
            //cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
            //给单元格赋
            cell.setCellValue(tableTitle);

            HSSFRow first = sheet.createRow(1);//创建报表主标题隔 
            if (pageHeader != null && pageHeader.size() > 0) {//组装页眉
                for (int i = 0; i < pageHeader.size(); i++) {
                    HSSFCell hcell = first.createCell(i);
                    hcell.setCellStyle(cs);
                    //hcell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
                    hcell.setCellValue(pageHeader.get(i).toString());
                }
            }

            sheet.createRow(2);//创建报表导出信息 

            //生成EXCEL表头单元 
            for (int i = 0; i < tableHeaderArray.length; i++) {
                row = sheet.createRow((short) i + 3);//创建报表表头 			
                for (int j = 0; j < tableHeaderArray[i].length; j++) {
                    //创建报表表头单元 
                    HSSFCell headerCell2 = row.createCell(j);

                    //设置报表表头单元格字 												
                    HSSFFont headerFont2 = workbook.createFont();
                    headerFont2.setFontHeightInPoints((short) 10);
                    //headerFont2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

                    //设置报表表头单元格样 
                    HSSFCellStyle headerCs2 = workbook.createCellStyle();
                    headerCs2.setFont(headerFont2);//设置字体									
                    headerCs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平对齐方式
                    headerCs2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式
                    headerCs2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//设置单元格显示颜 
                    headerCs2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    setBorderStyle(headerCs2);//设置单元格边框样 

                    headerCell2.setCellStyle(headerCs2);//挷定样式到单元格	
                    headerCell2.setCellType(HSSFCell.CELL_TYPE_STRING);//单元格类 
                    //headerCell2.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//设置为中文字 ,应该放在setCellValue方法之前
                    headerCell2.setCellValue(tableHeaderArray[i][j]);//赋 ?						
                }

                //处理报表表头的单元格合并
                //short firstCellNum=sheet.getRow(i+3).getFirstCellNum();//第一个单元格编号
                short lastCellNum = sheet.getRow(i + 3).getLastCellNum();// 后一个单元格编号

                int endCellNum = 1;
                //原：for (int j = 0; j <= lastCellNum; j++) {
                for (int j = 0; j <= lastCellNum - 1; j++) {
                    String startCellValue = sheet.getRow(i + 3).getCell(j).getStringCellValue();//当前单元格的 
                    String endCellValue = null;
                    if (j == lastCellNum - 1) {
                        endCellValue = sheet.getRow(i + 3).getCell((j)).getStringCellValue();//相邻单元格的 
                    }
                    else {
                        endCellValue = sheet.getRow(i + 3).getCell((j + 1)).getStringCellValue();//相邻单元格的 
                    }

                    if ("#cspan".equals(endCellValue) && j < lastCellNum) {
                        endCellNum++;
                        sheet.getRow(i + 3).getCell((j + 1)).setCellValue("");
                    }
                    else {
                        //		    			mergedRegionNum=sheet.addMergedRegion(new Region(i+3,(short)(startCellNum),i+3,(short)endCellNum));
                        //						sheet.getMergedRegionAt(mergedRegionNum);
                        //						startCellNum = j+1;
                        //						endCellNum = j+1;						
                    }
                    if ("#rspan".equals(startCellValue)) {
                        //						mergedRegionNum=sheet.addMergedRegion(new Region(i+2,(short)j,i+3,(short)j));
                        //						sheet.getMergedRegionAt(mergedRegionNum);
                        sheet.getRow(i + 3).getCell(j).setCellValue("");
                    }

                }
                //		    	处理表头的列合并
                String cols = getCols(tableHeaderArray[i], "#cspan");
                if (cols.length() > 0) {
                    String[] colsArr = cols.split(";");
                    for (int k = 0; k < colsArr.length; k++) {
                        String[] subColsArr = colsArr[k].split(":");
                        short fromColumn = (short) (Integer.parseInt(subColsArr[0]) - 1);
                        short toColumn = (short) (Integer.parseInt(subColsArr[1]));
                        int fromRow = i + 3;
                        int toRow = i + 3;

                        Region region = new Region();
                        region.setColumnFrom(fromColumn);
                        region.setColumnTo(toColumn);
                        region.setRowFrom(fromRow);
                        region.setRowTo(toRow);
                        sheet.addMergedRegion(region);
                    }

                }

            }
            //			处理表头的行合并
            String rows = getRows(tableHeaderArray, "#rspan");
            if (rows.length() > 0) {
                String[] rowsArr = rows.split(";");
                for (int k = 0; k < rowsArr.length; k++) {
                    String[] subRowsArr = rowsArr[k].split(":");
                    short fromColumn = (short) (Integer.parseInt(subRowsArr[2]));
                    short toColumn = (short) (Integer.parseInt(subRowsArr[2]));
                    int fromRow = Integer.parseInt(subRowsArr[0]) + 3 - 1;
                    int toRow = Integer.parseInt(subRowsArr[1]) + 3;
                    Region region = new Region();
                    region.setColumnFrom(fromColumn);
                    region.setColumnTo(toColumn);
                    region.setRowFrom(fromRow);
                    region.setRowTo(toRow);
                    sheet.addMergedRegion(region);
                }
            }
            //生成数据单元 
            for (int i = 0; i < dataCellTextList.size(); i++) {
                List rowTextList = (List) dataCellTextList.get(i);//获得行数 
                row = sheet.createRow((short) i + tableHeaderArray.length + 3);//创建数据 
                int rownum = (short) i + tableHeaderArray.length + 3;

                List rowspanList = (List) rowTextList.get(rowTextList.size() - 2);
                List colspanList = (List) rowTextList.get(rowTextList.size() - 1);
                //行合并单元格
                for (int k = 0; k < rowspanList.size(); k++) {
                    String rnum = (String) rowspanList.get(k);
                    if (rnum != null && !rnum.equals("") && !rnum.equals("null")) {
                        int innum = Integer.parseInt(rnum);
                        mergedRegionNum = sheet.addMergedRegion(new Region(rownum, (short) k, rownum + innum - 1,
                                (short) k));
                        sheet.getMergedRegionAt(mergedRegionNum);
                    }
                }
                //列合并单元格
                for (int k = 0; k < colspanList.size(); k++) {
                    String rnum = (String) colspanList.get(k);
                    if (rnum != null && !rnum.equals("") && !rnum.equals("null")) {
                        int innum = Integer.parseInt(rnum);
                        mergedRegionNum = sheet.addMergedRegion(new Region(rownum, (short) k, rownum, (short) (k
                                + innum - 1)));
                        sheet.getMergedRegionAt(mergedRegionNum);
                    }
                }

                for (int j = 0; j < rowTextList.size() - 2; j++) {
                    HSSFCell dataCell = row.createCell((short) j);//创建单元 
                    String cellValue = (String) rowTextList.get(j);
                    cellValue = cellValue.replaceAll(",", "");

                    //设置单元格数据格
                    if (getCellAlign(tableColumnAlignArray[j]) == HSSFCellStyle.ALIGN_LEFT
                            || getCellAlign(tableColumnAlignArray[j]) == HSSFCellStyle.ALIGN_CENTER) {
                        HSSFCellStyle dataCs = ExportDataUtil.getDataStyle(workbook, workbook.toString(), "cell000");
                        setBorderStyle(dataCs);//设置单元格边框样 

                        dataCell.setCellStyle(dataCs);//挷定样式到单元格
                        dataCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        //dataCell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//设置为中文字 ,应该放在setCellValue方法之前
                        dataCell.setCellValue(cellValue);//赋 ?	
                    }
                    else if (getCellAlign(tableColumnAlignArray[j]) == HSSFCellStyle.ALIGN_RIGHT) {
                        dataCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

                        cellValue = StringUtil.isNullStr(cellValue) ? "0" : cellValue;
                        double temp = Double.parseDouble(cellValue.replaceAll("%", ""));
                        HSSFCellStyle dataCs = null;
                        if (cellValue.endsWith("%")) {
                            dataCs = ExportDataUtil.getDataStyle(workbook, workbook.toString(), "cell100");
                            setBorderStyle(dataCs);//设置单元格边框样 

                            //dataCs.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));//百分 
                            dataCell.setCellValue(temp / 100);//赋 ?	
                            dataCell.setCellStyle(dataCs);//挷定样式到单元格
                        }
                        else if (cellValue.matches("^(-?\\d+)(\\.\\d+)$")) {
                            dataCs = ExportDataUtil.getDataStyle(workbook, workbook.toString(), "cell200");
                            setBorderStyle(dataCs);//设置单元格边框样 
                            //dataCs=ExportDataUtil.getDataStyle(workbook, tableColumnAlignArray[j], cellValue, "cell","cell200");
                            //dataCs.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));//两位小数
                            dataCell.setCellValue(temp);//赋 ?	
                            dataCell.setCellStyle(dataCs);//挷定样式到单元格
                        }
                        else if (cellValue.matches("^-?\\d+$")) {
                            dataCs = getDataStyle(workbook, workbook.toString(), "cell300");
                            setBorderStyle(dataCs);//设置单元格边框样 

                            dataCell.setCellValue(Long.parseLong(cellValue));//赋
                            dataCell.setCellStyle(dataCs);//挷定样式到单元格
                        }

                    }
                }
            }

            return workbook;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            HSSFMAP.clear();
        }
    }

    // 个HSSFWorkbook 公用的样 
    private static Map HSSFMAP = new HashMap();

    public static HSSFCellStyle getDataStyle(HSSFWorkbook workbook, String key1, String key2) {
        HSSFCellStyle dataCs = null;
        Map smap = null;
        if (HSSFMAP.containsKey(key1)) {
            return (HSSFCellStyle) ((Map) HSSFMAP.get(key1)).get(key2);
        }
        else {
            dataCs = workbook.createCellStyle();
            smap = new HashMap();

            HSSFCellStyle dataCs1 = workbook.createCellStyle();
            dataCs1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式	
            dataCs1.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平对齐方式
            smap.put("cell000", dataCs1);

            HSSFCellStyle dataCs2 = workbook.createCellStyle();
            dataCs2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式	
            dataCs2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//水平对齐方式
            dataCs2.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));//百分 
            smap.put("cell100", dataCs2);

            HSSFCellStyle dataCs3 = workbook.createCellStyle();
            dataCs3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式	
            dataCs3.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//水平对齐方式
            dataCs3.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));//两位小数 
            smap.put("cell200", dataCs3);

            HSSFCellStyle dataCs4 = workbook.createCellStyle();
            dataCs4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式	
            dataCs4.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//水平对齐方式
            dataCs4.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));//整数
            smap.put("cell300", dataCs4);

            HSSFMAP.put(key1, smap);
        }

        return (HSSFCellStyle) ((Map) HSSFMAP.get(key1)).get(key2);
    }

    /**
     * 解析 xml字符串tableData,生成表格数据
     * @param tableData
     * @return
     */
    private static List getDataCellTextList(String tableData,int colNum) {
        List dataCellTextList = new ArrayList();
        Document doc;
        try {
            doc = DOM4JUtil.loadDocFromXMLString(tableData);//加裁xml字符串tableData
            Element root = doc.getRootElement();//定位到根节点	

            List rowElementList = root.selectNodes("row");//取第 层的 有行数据
            for (int i = 0; i < rowElementList.size(); i++) {//遍历第一层的 有行数据
                Element rowElement = (Element) rowElementList.get(i);
                List rowCellElementList = rowElement.selectNodes("cell");//解析 行的单元 
                //将一行的数据放到rowTextList
                List rowTextList = new ArrayList();
                for (int j = 0; j < rowCellElementList.size(); j++) {
                    Element cellElement = (Element) rowCellElementList.get(j);
                    //String text = cellElement.attributeValue("text");
                    String text = cellElement.getStringValue();
                    rowTextList.add(null == text ? "" : text);
                }

                //行合并单元格设置
                List rowspanList = new ArrayList();
                for (int k = 0; k < rowCellElementList.size(); k++) {
                    Element cellElement = (Element) rowCellElementList.get(k);
                    String rows = cellElement.attributeValue("rowspan");
                    rowspanList.add(null == rows ? "" : rows);
                }
                rowTextList.add(rowspanList);

                //列合并单元格设置
                List colspanList = new ArrayList();
                for (int k = 0; k < rowCellElementList.size(); k++) {
                    Element cellElement = (Element) rowCellElementList.get(k);
                    String rows = cellElement.attributeValue("colspan");
                    colspanList.add(null == rows ? "" : rows);
                }
                rowTextList.add(colspanList);

                dataCellTextList.add(rowTextList);//将rowTextList存到dataCellTextList
                //该行的下 级行数据
                //List subRowElementList = rowElement.selectNodes("row");
                getSubRowElementListData(dataCellTextList, rowElement,colNum);
            }

        }
        catch (DocumentException e) {
            e.printStackTrace();
        }

        return dataCellTextList;
    }

    /**
     * 处理下级节点的数 
     * @param dataCellTextList
     * @param rowElement
     */
    public static void getSubRowElementListData(List dataCellTextList, Element rowElement,int colNum) {
        List subRowElementList = rowElement.selectNodes("row");
        for (int i = 0; i < subRowElementList.size(); i++) {
            Element subRowElement = (Element) subRowElementList.get(i);
            String path = subRowElement.getPath();//取得当前节点的路 
            List rowCellElementList = subRowElement.selectNodes("cell");//解析 行的单元 
            //将一行的数据放到rowTextList
            List rowTextList = new ArrayList();
            for (int j = 0; j < rowCellElementList.size(); j++) {
                Element cellElement = (Element) rowCellElementList.get(j);
                //String text = cellElement.attributeValue("text");

                String text = cellElement.getStringValue();
                if (j == colNum) {
                    //根据节点嵌套的深度来添加缩进
                    text = (null == text) ? "" : path.replaceAll("/rows/row/", "").replaceAll("row", "        ")
                            .replaceAll("/", "")
                            + text;
                    rowTextList.add(text);
                }
                else {
                    rowTextList.add(null == text ? "" : text);
                }
            }

            //行合并单元格设置
            List rowspanList = new ArrayList();
            for (int k = 0; k < rowCellElementList.size(); k++) {
                Element cellElement = (Element) rowCellElementList.get(k);
                String rows = cellElement.attributeValue("rowspan");
                rowspanList.add(null == rows ? "" : rows);
            }
            rowTextList.add(rowspanList);

            //列合并单元格设置
            List cospanList = new ArrayList();
            for (int k = 0; k < rowCellElementList.size(); k++) {
                Element cellElement = (Element) rowCellElementList.get(k);
                String rows = cellElement.attributeValue("colspan");
                cospanList.add(null == rows ? "" : rows);
            }
            rowTextList.add(cospanList);

            dataCellTextList.add(rowTextList);//将rowTextList存到dataCellTextList
            getSubRowElementListData(dataCellTextList, subRowElement,colNum);//递归调用处理下级行数 
        }
    }

    /**
     * 获取单元格的对齐样式
     * @param cellAlign
     * @return
     */
    public static short getCellAlign(String cellAlign) {
        if ("left".equalsIgnoreCase(cellAlign)) {
            return HSSFCellStyle.ALIGN_LEFT;
        }
        else if ("center".equalsIgnoreCase(cellAlign)) {
            return HSSFCellStyle.ALIGN_CENTER;
        }
        else if ("right".equalsIgnoreCase(cellAlign)) {
            return HSSFCellStyle.ALIGN_RIGHT;
        }
        else {
            return HSSFCellStyle.ALIGN_FILL;
        }
    }

    /**
     * 设置单元格的边框样式
     * @param cellStyle
     */
    private static void setBorderStyle(HSSFCellStyle cellStyle) {
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
    }

    /**
     * 把EXCEL工作簿写入到指定的文件路 
     * @param book
     * @param filePath
     * @throws IOException
     */
    public static void writeExcelToFile(HSSFWorkbook book, String filePath) throws IOException {
        FileOutputStream fOut = new FileOutputStream(filePath);//新建 输出文件 
        book.write(fOut);// 把相应的Excel 工作簿存 
        fOut.flush();// 操作结束，关闭文 
        fOut.close();//关闭文件 
    }

    /**
     * 	使用poi的hssf生成 个excel文件以后	有一个主类Workbook(相当于一个excel文件)的方 
     *	Workbook.write(OutputStream)可以写到response.getOutputStream()里面
     *	如果事先设置response的contentType为excel和下载的附件名称就可下载excel
     * @throws IOException 
     */
    public static void writeExcelToResponse(HSSFWorkbook book, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (book != null) {
            response.setContentType("application/ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String("导出Excel.xls".getBytes("gb2312"), "iso-8859-1"));
            OutputStream stream = response.getOutputStream();
            book.write(response.getOutputStream());
            try {

                book.write(stream);
            }
            catch (Exception e) {

            }
            finally {
                if (stream != null) {
                    try {
                        stream.flush();
                        stream.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void writeExcelToResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        HSSFWorkbook workbook = (HSSFWorkbook) request.getAttribute("workbook");

        response.setHeader("Content-Disposition", "attachment; filename="
                + new String(workbook.getSheetName(0).getBytes("gb2312"), "iso-8859-1") + ".xls");
        response.setContentType("application/x-download;charset=GB2312");
        OutputStream stream = response.getOutputStream();
        try {

            workbook.write(stream);
        }
        catch (Exception e) {

        }
        finally {
            if (stream != null) {
                try {
                    stream.flush();
                    stream.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成EXCEL工作 
     * @param tableHeader EXCEL表头字符 ,例如  
     * 产品,余额,#cspan,#cspan,日均,#cspan,#cspan,借记卡发卡量,#cspan,#cspan,借记卡消费额,#cspan,#cspan,贷记卡发卡量,#cspan,#cspan,贷记卡消费额,#cspan,#cspan;
     * #rspan,上期 ,计划增长比例,本期计划,上期 ,计划增长比例,本期计划,上期 ,计划增长比例,本期计划,上期 ,计划增长比例,本期计划,上期 ,计划增长比例,本期计划,上期 ,计划增长比例,本期计划

     * @param tableColumnAlign 各列对齐方式,例如   
     * left,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right

     * @param tableInitCellWidth 各列宽度,例如 
     * 178,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98
     * @param tableData EXCEL数据，XML格式,例如  
     * <?xml version="1.0"?>
    	<rows>
    	<row id='02'><cell>公司客户定期存款</cell><cell>40,000,000</cell><cell></cell><cell></cell><cell>40,000,000</cell><cell>-34.4%</cell><cell>26,250,000</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
    	<row id='03'><cell>公司客户通知存款</cell><cell>50,000,000</cell><cell></cell><cell></cell><cell>50,000,000</cell><cell>-97.3%</cell><cell>1,341,028</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
    	<row id='04'><cell>应解汇款及临时存 </cell><cell>712,053</cell><cell>244.5%</cell><cell>2,453,253</cell><cell>708,677</cell><cell>-77.3%</cell><cell>160,635</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
    	<row id='05'><cell>保证金（不含承兑保证金）</cell><cell>19,042,050</cell><cell>18.3%</cell><cell>22,523,523</cell><cell>19,041,089</cell><cell>-52.9%</cell><cell>8,973,500</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
    	<row id='06'><cell>短期信用贷款</cell><cell>0</cell><cell></cell><cell></cell><cell>117,857</cell><cell>129.1%</cell><cell>270,000</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
    	<row id='07'><cell>短期保证贷款</cell><cell>67,300,000</cell><cell>-47.0%</cell><cell>35,653,576</cell><cell>66,610,714</cell><cell>-100.0%</cell><cell>12,500</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
    	<row id='08'><cell>短期抵押质押贷款</cell><cell>40,000,000</cell><cell></cell><cell></cell><cell>45,714,286</cell><cell>-20.4%</cell><cell>36,375,000</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
    	<row id='09'><cell>中长期信用贷 </cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
    	<row id='10'><cell>中长期保证贷 </cell><cell>0</cell><cell></cell><cell>312,343</cell><cell>0</cell><cell></cell><cell>36,285</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
    	<row id='107'><cell>不良 (  )</cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell><cell>0</cell><cell></cell><cell></cell></row>
    	</rows>
     * @param tableTitle EXCEL标题,例如：经营计划填 -产品计划 
     * @param exportHeader 导出信息,例如  机构:0201:日期:2009 3 6 :单位: 
     * @return
     */
    public static HSSFWorkbook getWorkBook(String tableHeader, String tableColumnAlign, String tableInitCellWidth,
            String tableData, String tableTitle, String exportHeader) {
        try {
            //表格各列对齐方式
            String[] tableColumnAlignArray = tableColumnAlign.split(",");
            //各列宽度
            String[] tableInitCellWidthArray = tableInitCellWidth.split(",");
            //表头字符串数 
            String[][] tableHeaderArray = getTableHeaderArray(tableHeader, tableColumnAlignArray.length);
            //表格数据
            List dataCellTextList = getDataCellTextList(tableData,0);

            //创建新的Excel 工作 
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();//在Excel工作簿中建一工作表，其名为缺省 ?
            //workbook.setSheetName(0, tableTitle,HSSFWorkbook.ENCODING_UTF_16);//设置中文支持	

            //设置列宽
            for (int i = 0; i < tableInitCellWidthArray.length; i++) {
                sheet.setColumnWidth((short) i, (short) (Integer.parseInt(tableInitCellWidthArray[i]) * 40));
            }

            //创建报表主标题行	
            HSSFRow row = sheet.createRow((short) 0);
            int mergedRegionNum = sheet.addMergedRegion(new Region(0, (short) (0), 0,
                    (short) (tableColumnAlignArray.length - 1)));
            sheet.getMergedRegionAt(mergedRegionNum);//主标题行合并
            HSSFCell cell = row.createCell((short) 0);

            //报表主标题单元格字体
            HSSFFont font = workbook.createFont();//字体
            //font.setColor(HSSFFont.COLOR_NORMAL);
            font.setFontHeightInPoints((short) 14);
            //font.setFontName("黑体");
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            //font.setItalic(true);
            //font.setStrikeout(false);
            //font.setTypeOffset(HSSFFont.SS_SUB);
            //font.setUnderline(HSSFFont.U_NONE);

            //报表主标题单元格样式
            HSSFCellStyle cs = workbook.createCellStyle();
            cs.setFont(font);
            cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //cs.setDataFormat(HSSFDataFormat.getFormat("m/d/yy h:mm"));//设置cell样式为定制的日期格式
            //setBorderStyle(cs);//设置单元格边框样 
            //cs.setLocked(true);//设置锁定状 ?	

            cell.setCellStyle(cs);//挷定样式到单元格			
            //单元格类 
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            //设置为中文字 ,应该放在setCellValue方法之前
            //cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
            //给单元格赋 ?
            cell.setCellValue(tableTitle);
            //cell.setCellValue(new Date());

            sheet.createRow(1);//创建报表主标题隔 
            sheet.createRow(2);//创建报表导出信息 
            //生成报表导出信息行单元格
            String[] headerTextArray = exportHeader.split(":");
            for (int i = 0; i < headerTextArray.length; i++) {
                HSSFCell headerCell = sheet.getRow(2).createCell(i);
                //设置字体
                HSSFFont headerFont = workbook.createFont();
                headerFont.setFontHeightInPoints((short) 10);

                //设置单元格样 			
                HSSFCellStyle headerCs = workbook.createCellStyle();
                headerCs.setFont(headerFont);//设置字体									
                headerCs.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平对齐方式
                headerCs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式				

                headerCell.setCellStyle(headerCs);//挷定样式到单元格
                headerCell.setCellType(HSSFCell.CELL_TYPE_STRING);//单元格类 
                //headerCell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//设置为中文字 ,应该放在setCellValue方法之前	
                headerCell.setCellValue(i % 2 == 0 ? headerTextArray[i] + " " : headerTextArray[i]);//赋 ?
            }

            //生成EXCEL表头单元 
            for (int i = 0; i < tableHeaderArray.length; i++) {
                row = sheet.createRow((short) i + 3);//创建报表表头 			
                for (int j = 0; j < tableHeaderArray[i].length; j++) {
                    //创建报表表头单元 
                    HSSFCell headerCell2 = row.createCell(j);

                    //设置报表表头单元格字 												
                    HSSFFont headerFont2 = workbook.createFont();
                    headerFont2.setFontHeightInPoints((short) 10);
                    //headerFont2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

                    //设置报表表头单元格样 
                    HSSFCellStyle headerCs2 = workbook.createCellStyle();
                    headerCs2.setFont(headerFont2);//设置字体									
                    headerCs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平对齐方式
                    headerCs2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式
                    headerCs2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//设置单元格显示颜 
                    headerCs2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    setBorderStyle(headerCs2);//设置单元格边框样 

                    headerCell2.setCellStyle(headerCs2);//挷定样式到单元格	
                    headerCell2.setCellType(HSSFCell.CELL_TYPE_STRING);//单元格类 
                    //headerCell2.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//设置为中文字 ,应该放在setCellValue方法之前
                    headerCell2.setCellValue(tableHeaderArray[i][j]);//赋 ?						
                }

                //处理报表表头的单元格合并
                //short firstCellNum=sheet.getRow(i+3).getFirstCellNum();//第一个单元格编号
                short lastCellNum = sheet.getRow(i + 3).getLastCellNum();// 后一个单元格编号

                int startCellNum = 1;
                int endCellNum = 1;
                for (int j = 0; j <= lastCellNum; j++) {
                    String startCellValue = sheet.getRow(i + 3).getCell(j).getStringCellValue();//当前单元格的 
                    String endCellValue = null;
                    if (j == lastCellNum) {
                        endCellValue = sheet.getRow(i + 3).getCell((j)).getStringCellValue();//相邻单元格的 
                    }
                    else {
                        endCellValue = sheet.getRow(i + 3).getCell((j + 1)).getStringCellValue();//相邻单元格的 
                    }

                    if ("#cspan".equals(endCellValue) && j < lastCellNum) {
                        endCellNum++;
                    }
                    else {
                        mergedRegionNum = sheet.addMergedRegion(new Region(i + 3, (short) (startCellNum), i + 3,
                                (short) endCellNum));
                        sheet.getMergedRegionAt(mergedRegionNum);
                        startCellNum = j + 1;
                        endCellNum = j + 1;
                    }
                    if ("#rspan".equals(startCellValue)) {
                        mergedRegionNum = sheet.addMergedRegion(new Region(i + 2, (short) j, i + 3, (short) j));
                        sheet.getMergedRegionAt(mergedRegionNum);
                    }

                }

                mergedRegionNum = sheet.addMergedRegion(new Region(i + 3, (short) (startCellNum), i + 3,
                        (short) endCellNum));
                sheet.getMergedRegionAt(mergedRegionNum);

            }

            //生成数据单元 
            for (int i = 0; i < dataCellTextList.size(); i++) {
                List rowTextList = (List) dataCellTextList.get(i);//获得行数 
                row = sheet.createRow((short) i + tableHeaderArray.length + 3);//创建数据 				
                for (int j = 0; j < rowTextList.size(); j++) {
                    HSSFCell dataCell = row.createCell(j);//创建单元 
                    String cellValue = (String) rowTextList.get(j);
                    cellValue = cellValue.replaceAll(",", "");
                    //设置字体
                    //HSSFFont dataFont = workbook.createFont();
                    //dataFont.setFontHeightInPoints((short) 10);
                    //dataFont.setFontName("黑体");

                    //设置单元格样 			
                    HSSFCellStyle dataCs = workbook.createCellStyle();
                    //dataCs.setFont(dataFont);//设置字体									
                    dataCs.setAlignment(getCellAlign(tableColumnAlignArray[j]));//水平对齐方式
                    dataCs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式		
                    //dataCs.setDataFormat(workbook.createDataFormat().getFormat("#,##0.000000"));  //自定义格式，显示六位小数

                    setBorderStyle(dataCs);//设置单元格边框样 

                    if (dataCs.getAlignment() == HSSFCellStyle.ALIGN_LEFT
                            || dataCs.getAlignment() == HSSFCellStyle.ALIGN_CENTER) {
                        dataCell.setCellStyle(dataCs);//挷定样式到单元格
                        dataCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        //dataCell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//设置为中文字 ,应该放在setCellValue方法之前
                        dataCell.setCellValue(cellValue);//赋 ?	
                    }
                    else if (dataCs.getAlignment() == HSSFCellStyle.ALIGN_RIGHT) {
                        dataCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

                        cellValue = StringUtil.isNullStr(cellValue) ? "0" : cellValue;
                        double temp = Double.parseDouble(cellValue.replaceAll("%", ""));

                        if (cellValue.endsWith("%")) {
                            dataCs.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));//百分 
                            dataCell.setCellValue(temp / 100);//赋 ?	
                        }
                        else if (cellValue.matches("^(-?\\d+)(\\.\\d+)$")) {
                            dataCs.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));//两位小数
                            dataCell.setCellValue(temp);//赋 ?	

                        }
                        else if (cellValue.matches("^-?\\d+$")) {
                            dataCs.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));//整数
                            dataCell.setCellValue(Integer.parseInt(cellValue));//赋 ?
                        }

                    }
                    dataCell.setCellStyle(dataCs);//挷定样式到单元格
                }
            }

            return workbook;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 处理表头的列合并
     * 解析数组中连续type的每一段的首尾下标
     * @agrs:表头各行的数组,如String [] arr = {"aa","bb","cc","cols","cols","cols","dd","ee","cols","cols","vv","cols"} ;
     * @return String:首尾下标以":"相隔",各个起始下标以";"相隔,如:3:5;8:9;11:11"
     */
    public static String getCols(String[] colsOrRows, String type) {
        String returnValue = "";
        int index = 0;
        for (int i = 0; i < colsOrRows.length; i++) {
            if (colsOrRows[i].equals(type) && (i == 0 || !colsOrRows[i - 1].equals(type))) {
                returnValue = returnValue + i + ":";
                index = index + 1;
            }
            else if (!colsOrRows[i].equals(type) && i != 0 && colsOrRows[i - 1].equals(type)) {
                returnValue = returnValue + (i - 1);
                index = index + 1;
            }
            if (index == 2) {
                returnValue = returnValue + ";";
                index = 0;
            }
            if (i == colsOrRows.length - 1 && colsOrRows[i].equals(type)) {
                returnValue = returnValue + i + ";";
            }

        }
        return returnValue;
    }

    /**
     * 处理表头的行合并
     * @args:表头二维数组,"#rspan"
     * @return:要合并行的起始下标和行所在的列,其中起始下标和列以":"相隔,
     * 各个起始下标和列以";"相隔,如:1:2:1;1:2:2;
     */
    public static String getRows(String[][] rows, String type) {
        String returnValue = "";
        if (rows[0].length > 0) {
            for (int i = 0; i < rows[0].length; i++) {//i代表列
                String[] tempCols = new String[rows.length];
                for (int j = 0; j < rows.length; j++) {
                    tempCols[j] = rows[j][i];
                }
                int index = 0;
                for (int k = 0; k < tempCols.length; k++) {
                    if (tempCols[k].equals(type) && (k == 0 || !tempCols[k - 1].equals(type))) {
                        returnValue = returnValue + k + ":";
                        index = index + 1;
                    }
                    else if (!tempCols[k].equals(type) && k != 0 && tempCols[k - 1].equals(type)) {
                        returnValue = returnValue + (k - 1);
                        index = index + 1;
                    }
                    if (index == 2) {
                        returnValue = returnValue + ":" + i + ";";
                        index = 0;
                    }
                    if (k == tempCols.length - 1 && tempCols[k].equals(type)) {
                        returnValue = returnValue + k + ":" + i + ";";
                    }

                }
            }
        }

        return returnValue;
    }

    /**
     * 表头字符 
     * @param tableHeader
     * @param columnNum
     * @return
     */
    public static String[][] getTableHeaderArray(String tableHeader, int columnNum) {
        String[] tableHeaderArrayTemp = tableHeader.split(";");
        String[][] tableHeaderArray = new String[tableHeaderArrayTemp.length][columnNum];
        for (int i = 0; i < tableHeaderArrayTemp.length; i++) {
            tableHeaderArray[i] = tableHeaderArrayTemp[i].split(",");
        }
        return tableHeaderArray;
    }

    /**
     * 表头字符串
     * @param tableHeader
     */
    public static void setTableHeaderArray(List tableHeader) {
        pageHeader = tableHeader;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
    	System.out.println(getcellColumnFlag(16384));
    }

    /**
     * 事业部导出方法
     * @param workbook
     * @param startNumber
     * @param tableHeader
     * @param tableColumnAlign
     * @param tableInitCellWidth
     * @param tableData
     * @param tableTitle
     * @return
     */
    public static HSSFWorkbook appendWorkBook(HSSFWorkbook workbook,int startNumber, String tableHeader, String tableColumnAlign, String tableInitCellWidth,
    		String tableData, String tableTitle) {
    	try {
    		//表格各列对齐方式
    		String[] tableColumnAlignArray = tableColumnAlign.split(",");
    		//各列宽度
    		String[] tableInitCellWidthArray = tableInitCellWidth.split(",");
    		//表头字符串数??
    		String[][] tableHeaderArray = getTableHeaderArray(tableHeader, tableColumnAlignArray.length);
    		//表格数据
    		List dataCellTextList = getDataCellTextList(tableData,0);
    		
    		//创建新的Excel 工作??
    		//HSSFWorkbook workbook = new HSSFWorkbook();
    		//HSSFSheet sheet = workbook.createSheet();//在Excel工作簿中建一工作表，其名为缺省???
    		//workbook.setSheetName(0, tableTitle,HSSFWorkbook.ENCODING_UTF_16);//设置中文支持	
    		
    		HSSFSheet sheet = workbook.getSheetAt(0);
    		
    		//设置列宽
    		for (int i = 0; i < tableInitCellWidthArray.length; i++) {
    			sheet.setColumnWidth(i, (Integer.parseInt(tableInitCellWidthArray[i]) * 40));
    		}
    		
    		//创建报表主标题行	
    		HSSFRow row = sheet.createRow((short) 0);
    		int mergedRegionNum = sheet.addMergedRegion(new Region(0, (short) (0), 0,
    				(short) (tableColumnAlignArray.length - 1)));
    		sheet.getMergedRegionAt(mergedRegionNum);//主标题行合并
    		HSSFCell cell = row.createCell(0);
    		
    		//报表主标题单元格字体
    		HSSFFont font = workbook.createFont();//字体
    		font.setFontHeightInPoints((short) 14);
    		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    		
    		//报表主标题单元格样式
    		HSSFCellStyle cs = workbook.createCellStyle();
    		cs.setFont(font);
    		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    		
    		cell.setCellStyle(cs);//挷定样式到单元格			
    		//单元格类??
    		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    		//设置为中文字??,应该放在setCellValue方法之前
    		//cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
    		//给单元格赋
    		cell.setCellValue(tableTitle);
    		
    		sheet.createRow(startNumber+1);//创建报表导出信息??
    		
    		//生成EXCEL表头单元??
    		for (int i = startNumber; i < tableHeaderArray.length+startNumber; i++) {
    			row = sheet.createRow((short) i);//创建报表表头??			
    			for (int j = 0; j < tableHeaderArray[i-startNumber].length; j++) {
    				//创建报表表头单元??
    				HSSFCell headerCell2 = row.createCell(j);
    				
    				//设置报表表头单元格字??												
    				HSSFFont headerFont2 = workbook.createFont();
    				headerFont2.setFontHeightInPoints((short) 10);
    				//headerFont2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    				
    				//设置报表表头单元格样??
    				HSSFCellStyle headerCs2 = workbook.createCellStyle();
    				headerCs2.setFont(headerFont2);//设置字体									
    				headerCs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平对齐方式
    				headerCs2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式
    				headerCs2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//设置单元格显示颜??
    				headerCs2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    				setBorderStyle(headerCs2);//设置单元格边框样??
    				
    				headerCell2.setCellStyle(headerCs2);//挷定样式到单元格	
    				headerCell2.setCellType(HSSFCell.CELL_TYPE_STRING);//单元格类??
    				//headerCell2.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//设置为中文字??,应该放在setCellValue方法之前
    				headerCell2.setCellValue(tableHeaderArray[i-startNumber][j]);//赋???						
    			}
    			
    			//处理报表表头的单元格合并
    			//short firstCellNum=sheet.getRow(i+3).getFirstCellNum();//第一个单元格编号
    			short lastCellNum = sheet.getRow(i).getLastCellNum();//??后一个单元格编号
    			
    			int endCellNum = 1;
    			//原：for (int j = 0; j <= lastCellNum; j++) {
    			for (int j = 0; j <= lastCellNum - 1; j++) {
    				String startCellValue = sheet.getRow(i).getCell(j).getStringCellValue();//当前单元格的??
    				String endCellValue = null;
    				if (j == lastCellNum - 1) {
    					endCellValue = sheet.getRow(i).getCell((j)).getStringCellValue();//相邻单元格的??
    				}
    				else {
    					endCellValue = sheet.getRow(i).getCell((j + 1)).getStringCellValue();//相邻单元格的??
    				}
    				
    				if ("#cspan".equals(endCellValue) && j < lastCellNum) {
    					endCellNum++;
    					sheet.getRow(i).getCell((j + 1)).setCellValue("");
    				}
    				else {
    					//		    			mergedRegionNum=sheet.addMergedRegion(new Region(i+3,(short)(startCellNum),i+3,(short)endCellNum));
    					//						sheet.getMergedRegionAt(mergedRegionNum);
    					//						startCellNum = j+1;
    					//						endCellNum = j+1;						
    				}
    				if ("#rspan".equals(startCellValue)) {
    					//						mergedRegionNum=sheet.addMergedRegion(new Region(i+2,(short)j,i+3,(short)j));
    					//						sheet.getMergedRegionAt(mergedRegionNum);
    					sheet.getRow(i).getCell(j).setCellValue("");
    				}
    				
    			}
    			//		    	处理表头的列合并
    			String cols = getCols(tableHeaderArray[i-startNumber], "#cspan");
    			if (cols.length() > 0) {
    				String[] colsArr = cols.split(";");
    				for (int k = 0; k < colsArr.length; k++) {
    					String[] subColsArr = colsArr[k].split(":");
    					short fromColumn = (short) (Integer.parseInt(subColsArr[0]) - 1);
    					short toColumn = (short) (Integer.parseInt(subColsArr[1]));
    					int fromRow = i;
    					int toRow = i;
    					
    					Region region = new Region();
    					region.setColumnFrom(fromColumn);
    					region.setColumnTo(toColumn);
    					region.setRowFrom(fromRow);
    					region.setRowTo(toRow);
    					sheet.addMergedRegion(region);
    				}
    				
    			}
    			
    		}
    		//			处理表头的行合并
    		String rows = getRows(tableHeaderArray, "#rspan");
    		if (rows.length() > 0) {
    			String[] rowsArr = rows.split(";");
    			for (int k = 0; k < rowsArr.length; k++) {
    				String[] subRowsArr = rowsArr[k].split(":");
    				short fromColumn = (short) (Integer.parseInt(subRowsArr[2]));
    				short toColumn = (short) (Integer.parseInt(subRowsArr[2]));
    				int fromRow = Integer.parseInt(subRowsArr[0]) + 3 - 1;
    				int toRow = Integer.parseInt(subRowsArr[1]) + 3;
    				Region region = new Region();
    				region.setColumnFrom(fromColumn);
    				region.setColumnTo(toColumn);
    				region.setRowFrom(fromRow);
    				region.setRowTo(toRow);
    				sheet.addMergedRegion(region);
    			}
    		}
    		//生成数据单元??
    		for (int i = startNumber; i < dataCellTextList.size()+startNumber; i++) {
    			List rowTextList = (List) dataCellTextList.get(i-startNumber);//获得行数??
    			row = sheet.createRow((short) i + tableHeaderArray.length);//创建数据??
    			int rownum = (short) i + tableHeaderArray.length;
    			
    			List rowspanList = (List) rowTextList.get(rowTextList.size() - 2);
    			List colspanList = (List) rowTextList.get(rowTextList.size() - 1);
    			//行合并单元格
    			for (int k = 0; k < rowspanList.size(); k++) {
    				String rnum = (String) rowspanList.get(k);
    				if (rnum != null && !rnum.equals("") && !rnum.equals("null")) {
    					int innum = Integer.parseInt(rnum);
    					mergedRegionNum = sheet.addMergedRegion(new Region(rownum, (short) k, rownum + innum - 1,
    							(short) k));
    					sheet.getMergedRegionAt(mergedRegionNum);
    				}
    			}
    			//列合并单元格
    			for (int k = 0; k < colspanList.size(); k++) {
    				String rnum = (String) colspanList.get(k);
    				if (rnum != null && !rnum.equals("") && !rnum.equals("null")) {
    					int innum = Integer.parseInt(rnum);
    					mergedRegionNum = sheet.addMergedRegion(new Region(rownum, (short) k, rownum, (short) (k
    							+ innum - 1)));
    					sheet.getMergedRegionAt(mergedRegionNum);
    				}
    			}
    			
    			for (int j = 0; j < rowTextList.size() - 2; j++) {
    				HSSFCell dataCell = row.createCell((short) j);//创建单元??
    				String cellValue = (String) rowTextList.get(j);
    				cellValue = cellValue.replaceAll(",", "");
    				
    				//设置单元格数据格
    				if (getCellAlign(tableColumnAlignArray[j]) == HSSFCellStyle.ALIGN_LEFT
    						|| getCellAlign(tableColumnAlignArray[j]) == HSSFCellStyle.ALIGN_CENTER) {
    					HSSFCellStyle dataCs = ExportDataUtil.getDataStyle(workbook, workbook.toString(), "cell000");
    					setBorderStyle(dataCs);//设置单元格边框样??
    					
    					dataCell.setCellStyle(dataCs);//挷定样式到单元格
    					dataCell.setCellType(HSSFCell.CELL_TYPE_STRING);
    					//dataCell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//设置为中文字??,应该放在setCellValue方法之前
    					dataCell.setCellValue(cellValue);//赋???	
    				}
    				else if (getCellAlign(tableColumnAlignArray[j]) == HSSFCellStyle.ALIGN_RIGHT) {
    					dataCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    					
    					cellValue = StringUtil.isNullStr(cellValue) ? "0" : cellValue;
    					double temp = Double.parseDouble(cellValue.replaceAll("%", ""));
    					HSSFCellStyle dataCs = null;
    					if (cellValue.endsWith("%")) {
    						dataCs = ExportDataUtil.getDataStyle(workbook, workbook.toString(), "cell100");
    						setBorderStyle(dataCs);//设置单元格边框样??
    						
    						//dataCs.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));//百分??
    						dataCell.setCellValue(temp / 100);//赋???	
    						dataCell.setCellStyle(dataCs);//挷定样式到单元格
    					}
    					else if (cellValue.matches("^(-?\\d+)(\\.\\d+)$")) {
    						dataCs = ExportDataUtil.getDataStyle(workbook, workbook.toString(), "cell200");
    						setBorderStyle(dataCs);//设置单元格边框样??
    						//dataCs=ExportDataUtil.getDataStyle(workbook, tableColumnAlignArray[j], cellValue, "cell","cell200");
    						//dataCs.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));//两位小数
    						dataCell.setCellValue(temp);//赋???	
    						dataCell.setCellStyle(dataCs);//挷定样式到单元格
    					}
    					else if (cellValue.matches("^-?\\d+$")) {
    						dataCs = getDataStyle(workbook, workbook.toString(), "cell300");
    						setBorderStyle(dataCs);//设置单元格边框样??
    						
    						dataCell.setCellValue(Long.parseLong(cellValue));//赋
    						dataCell.setCellStyle(dataCs);//挷定样式到单元格
    					}
    					
    				}
    			}
    		}
    		
    		return workbook;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    	finally {
    		HSSFMAP.clear();
    	}
    }    
    
    /**
     * 根据传入的colNum来判断哪行始缩进的
     * @param tableHeader
     * @param tableColumnAlign
     * @param tableInitCellWidth
     * @param tableData
     * @param tableTitle
     * @param colNum
     * @return
     */
    
    public static HSSFWorkbook getFlexConfigWorkBook(String tableHeader, String tableColumnAlign, String tableInitCellWidth,
            String tableData, String tableTitle,int colNum) {
        try {
            //表格各列对齐方式
            String[] tableColumnAlignArray = tableColumnAlign.split(",");
            //各列宽度
            String[] tableInitCellWidthArray = tableInitCellWidth.split(",");
            //表头字符串数 
            String[][] tableHeaderArray = getTableHeaderArray(tableHeader, tableColumnAlignArray.length);
            //表格数据
            List dataCellTextList = getDataCellTextList(tableData,colNum);

            //创建新的Excel 工作 
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();//在Excel工作簿中建一工作表，其名为缺省 ?
            //workbook.setSheetName(0, tableTitle,HSSFWorkbook.ENCODING_UTF_16);//设置中文支持	

            //设置列宽
            for (int i = 0; i < tableInitCellWidthArray.length; i++) {
                sheet.setColumnWidth(i, (Integer.parseInt(tableInitCellWidthArray[i]) * 40));
            }

            //创建报表主标题行	
            HSSFRow row = sheet.createRow((short) 0);
            int mergedRegionNum = sheet.addMergedRegion(new Region(0, (short) (0), 0,
                    (short) (tableColumnAlignArray.length - 1)));
            sheet.getMergedRegionAt(mergedRegionNum);//主标题行合并
            HSSFCell cell = row.createCell(0);

            //报表主标题单元格字体
            HSSFFont font = workbook.createFont();//字体
            font.setFontHeightInPoints((short) 14);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            //报表主标题单元格样式
            HSSFCellStyle cs = workbook.createCellStyle();
            cs.setFont(font);
            cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            cell.setCellStyle(cs);//挷定样式到单元格			
            //单元格类 
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            //设置为中文字 ,应该放在setCellValue方法之前
            //cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
            //给单元格赋
            cell.setCellValue(tableTitle);

            HSSFRow first = sheet.createRow(1);//创建报表主标题隔 
            if (pageHeader != null && pageHeader.size() > 0) {//组装页眉
                for (int i = 0; i < pageHeader.size(); i++) {
                    HSSFCell hcell = first.createCell(i);
                    hcell.setCellStyle(cs);
                    //hcell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
                    hcell.setCellValue(pageHeader.get(i).toString());
                }
            }

            sheet.createRow(2);//创建报表导出信息 

            //生成EXCEL表头单元 
            for (int i = 0; i < tableHeaderArray.length; i++) {
                row = sheet.createRow((short) i + 3);//创建报表表头 			
                for (int j = 0; j < tableHeaderArray[i].length; j++) {
                    //创建报表表头单元 
                    HSSFCell headerCell2 = row.createCell(j);

                    //设置报表表头单元格字 												
                    HSSFFont headerFont2 = workbook.createFont();
                    headerFont2.setFontHeightInPoints((short) 10);
                    //headerFont2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

                    //设置报表表头单元格样 
                    HSSFCellStyle headerCs2 = workbook.createCellStyle();
                    headerCs2.setFont(headerFont2);//设置字体									
                    headerCs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平对齐方式
                    headerCs2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式
                    headerCs2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//设置单元格显示颜 
                    headerCs2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    setBorderStyle(headerCs2);//设置单元格边框样 

                    headerCell2.setCellStyle(headerCs2);//挷定样式到单元格	
                    headerCell2.setCellType(HSSFCell.CELL_TYPE_STRING);//单元格类 
                    //headerCell2.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//设置为中文字 ,应该放在setCellValue方法之前
                    headerCell2.setCellValue(tableHeaderArray[i][j]);//赋 ?						
                }

                //处理报表表头的单元格合并
                //short firstCellNum=sheet.getRow(i+3).getFirstCellNum();//第一个单元格编号
                short lastCellNum = sheet.getRow(i + 3).getLastCellNum();// 后一个单元格编号

                int endCellNum = 1;
                //原：for (int j = 0; j <= lastCellNum; j++) {
                for (int j = 0; j <= lastCellNum - 1; j++) {
                    String startCellValue = sheet.getRow(i + 3).getCell(j).getStringCellValue();//当前单元格的 
                    String endCellValue = null;
                    if (j == lastCellNum - 1) {
                        endCellValue = sheet.getRow(i + 3).getCell((j)).getStringCellValue();//相邻单元格的 
                    }
                    else {
                        endCellValue = sheet.getRow(i + 3).getCell((j + 1)).getStringCellValue();//相邻单元格的 
                    }

                    if ("#cspan".equals(endCellValue) && j < lastCellNum) {
                        endCellNum++;
                        sheet.getRow(i + 3).getCell((j + 1)).setCellValue("");
                    }
                    else {
                        //		    			mergedRegionNum=sheet.addMergedRegion(new Region(i+3,(short)(startCellNum),i+3,(short)endCellNum));
                        //						sheet.getMergedRegionAt(mergedRegionNum);
                        //						startCellNum = j+1;
                        //						endCellNum = j+1;						
                    }
                    if ("#rspan".equals(startCellValue)) {
                        //						mergedRegionNum=sheet.addMergedRegion(new Region(i+2,(short)j,i+3,(short)j));
                        //						sheet.getMergedRegionAt(mergedRegionNum);
                        sheet.getRow(i + 3).getCell(j).setCellValue("");
                    }

                }
                //		    	处理表头的列合并
                String cols = getCols(tableHeaderArray[i], "#cspan");
                if (cols.length() > 0) {
                    String[] colsArr = cols.split(";");
                    for (int k = 0; k < colsArr.length; k++) {
                        String[] subColsArr = colsArr[k].split(":");
                        short fromColumn = (short) (Integer.parseInt(subColsArr[0]) - 1);
                        short toColumn = (short) (Integer.parseInt(subColsArr[1]));
                        int fromRow = i + 3;
                        int toRow = i + 3;

                        Region region = new Region();
                        region.setColumnFrom(fromColumn);
                        region.setColumnTo(toColumn);
                        region.setRowFrom(fromRow);
                        region.setRowTo(toRow);
                        sheet.addMergedRegion(region);
                    }

                }

            }
            //			处理表头的行合并
            String rows = getRows(tableHeaderArray, "#rspan");
            if (rows.length() > 0) {
                String[] rowsArr = rows.split(";");
                for (int k = 0; k < rowsArr.length; k++) {
                    String[] subRowsArr = rowsArr[k].split(":");
                    short fromColumn = (short) (Integer.parseInt(subRowsArr[2]));
                    short toColumn = (short) (Integer.parseInt(subRowsArr[2]));
                    int fromRow = Integer.parseInt(subRowsArr[0]) + 3 - 1;
                    int toRow = Integer.parseInt(subRowsArr[1]) + 3;
                    Region region = new Region();
                    region.setColumnFrom(fromColumn);
                    region.setColumnTo(toColumn);
                    region.setRowFrom(fromRow);
                    region.setRowTo(toRow);
                    sheet.addMergedRegion(region);
                }
            }
            //生成数据单元 
            for (int i = 0; i < dataCellTextList.size(); i++) {
                List rowTextList = (List) dataCellTextList.get(i);//获得行数 
                row = sheet.createRow((short) i + tableHeaderArray.length + 3);//创建数据 
                int rownum = (short) i + tableHeaderArray.length + 3;

                List rowspanList = (List) rowTextList.get(rowTextList.size() - 2);
                List colspanList = (List) rowTextList.get(rowTextList.size() - 1);
                //行合并单元格
                for (int k = 0; k < rowspanList.size(); k++) {
                    String rnum = (String) rowspanList.get(k);
                    if (rnum != null && !rnum.equals("") && !rnum.equals("null")) {
                        int innum = Integer.parseInt(rnum);
                        mergedRegionNum = sheet.addMergedRegion(new Region(rownum, (short) k, rownum + innum - 1,
                                (short) k));
                        sheet.getMergedRegionAt(mergedRegionNum);
                    }
                }
                //列合并单元格
                for (int k = 0; k < colspanList.size(); k++) {
                    String rnum = (String) colspanList.get(k);
                    if (rnum != null && !rnum.equals("") && !rnum.equals("null")) {
                        int innum = Integer.parseInt(rnum);
                        mergedRegionNum = sheet.addMergedRegion(new Region(rownum, (short) k, rownum, (short) (k
                                + innum - 1)));
                        sheet.getMergedRegionAt(mergedRegionNum);
                    }
                }

                for (int j = 0; j < rowTextList.size() - 2; j++) {
                    HSSFCell dataCell = row.createCell((short) j);//创建单元 
                    String cellValue = (String) rowTextList.get(j);
                    cellValue = cellValue.replaceAll(",", "");

                    //设置单元格数据格
                    if (getCellAlign(tableColumnAlignArray[j]) == HSSFCellStyle.ALIGN_LEFT
                            || getCellAlign(tableColumnAlignArray[j]) == HSSFCellStyle.ALIGN_CENTER) {
                        HSSFCellStyle dataCs = ExportDataUtil.getDataStyle(workbook, workbook.toString(), "cell000");
                        setBorderStyle(dataCs);//设置单元格边框样 

                        dataCell.setCellStyle(dataCs);//挷定样式到单元格
                        dataCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        //dataCell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//设置为中文字 ,应该放在setCellValue方法之前
                        dataCell.setCellValue(cellValue);//赋 ?	
                    }
                    else if (getCellAlign(tableColumnAlignArray[j]) == HSSFCellStyle.ALIGN_RIGHT) {
                        dataCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

                        cellValue = StringUtil.isNullStr(cellValue) ? "0" : cellValue;
                        double temp = Double.parseDouble(cellValue.replaceAll("%", ""));
                        HSSFCellStyle dataCs = null;
                        if (cellValue.endsWith("%")) {
                            dataCs = ExportDataUtil.getDataStyle(workbook, workbook.toString(), "cell100");
                            setBorderStyle(dataCs);//设置单元格边框样 

                            //dataCs.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));//百分 
                            dataCell.setCellValue(temp / 100);//赋 ?	
                            dataCell.setCellStyle(dataCs);//挷定样式到单元格
                        }
                        else if (cellValue.matches("^(-?\\d+)(\\.\\d+)$")) {
                            dataCs = ExportDataUtil.getDataStyle(workbook, workbook.toString(), "cell200");
                            setBorderStyle(dataCs);//设置单元格边框样 
                            //dataCs=ExportDataUtil.getDataStyle(workbook, tableColumnAlignArray[j], cellValue, "cell","cell200");
                            //dataCs.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));//两位小数
                            dataCell.setCellValue(temp);//赋 ?	
                            dataCell.setCellStyle(dataCs);//挷定样式到单元格
                        }
                        else if (cellValue.matches("^-?\\d+$")) {
                            dataCs = getDataStyle(workbook, workbook.toString(), "cell300");
                            setBorderStyle(dataCs);//设置单元格边框样 

                            dataCell.setCellValue(Long.parseLong(cellValue));//赋
                            dataCell.setCellStyle(dataCs);//挷定样式到单元格
                        }

                    }
                }
            }

            return workbook;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            HSSFMAP.clear();
        }
    }
    /**
     * 补录模板导出
     * @param title  模板名称
     * @param excelHeadData  模板列头
     * @param sql  模板sql
     * @param colName  数据列名
     * @param metaData 导出的数据
     * @return
     * @throws Exception
     */
    public static HSSFWorkbook expTemplate(String title,Map<String,List<String>> excelHeadData,String sql,List<String> colName,List<Map<String, Object>> metaData) throws Exception{ 
    	HSSFWorkbook workbook = new HSSFWorkbook();
    	HSSFSheet sheet = workbook.createSheet(title);
    	//生成数据字典
    	
    	sheet.setDefaultColumnWidth(20);
    	
    	 //单元格类型
        DataFormat fmt = workbook.createDataFormat(); 
        //文本类型
        CellStyle textStyle = workbook.createCellStyle(); 
        textStyle.setDataFormat(fmt.getFormat("@")); 
    	
    	Map<Integer,List<String>> linkMap = new LinkedHashMap<Integer,List<String>>();
    	
    	HSSFFont headerFont2 = workbook.createFont();
    	headerFont2.setFontHeightInPoints((short) 10);
    	
    	//设置报表表头单元格样
    	HSSFCellStyle headerCs2 = workbook.createCellStyle();
    	headerCs2.setFont(headerFont2);//设置字体	
    	headerCs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平对齐方式
    	headerCs2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式
    	headerCs2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//设置单元格显示颜
    	headerCs2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    	setBorderStyle(headerCs2);//设置单元格边框样
    	
       	//必输入单元格样式
        Font font = workbook.createFont();
        font.setColor(HSSFColor.RED.index);
        font.setFontHeightInPoints((short)10); //字体大小
//         font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
        
        HSSFCellStyle headerCs3 = workbook.createCellStyle();
        headerCs3.setFont(font);//设置字体	
        headerCs3.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平对齐方式
        headerCs3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式
        headerCs3.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//设置单元格显示颜
        headerCs3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        setBorderStyle(headerCs3);//设置单元格边框样
    	
    	HSSFRow titlerow = sheet.createRow(0);
    	
    	int start = 0;
    	int end = 0;
    	String date = "";
    	
    	HSSFRow defaultrow = null;
    	HSSFCell defaultcell = null;
    	
    	int colNum = 0;
    	int sheetIndex = 0;
    	
    	for(Entry<String,List<String>> entry : excelHeadData.entrySet()){
    		String key = entry.getKey();
    		List<String> dataVal = entry.getValue();
    		HSSFCell cell = titlerow.createCell(colNum);
    		cell.setCellStyle(headerCs2);//挷定样式到单元格	
    		cell.setCellType(HSSFCell.CELL_TYPE_STRING);//单元格
    		//开始拆分标题
    		String [] cellConfig = key.split("#"); //单元格配置
    		//开始拆分标题
    		String cellText = cellConfig[0];
    		String cellType = cellConfig[1];
    		String mustInput = cellConfig[2];
    		String cellFormula = cellConfig.length>3 ? cellConfig[3] : ""; //cell公式
    		//日期
    		if(cellType.equals("DATE")){
    			cellText = cellText + "[D]";
    		}
    		if("Y".equals(mustInput)){
    			cell.setCellStyle(headerCs3);
    			cell.setCellValue(cellText+"(必填)");
    		}else{
    			cell.setCellValue(cellText);
    		}
    		 
    		
    		if (metaData == null || metaData.size() <= 0) {
    			defaultrow = sheet.createRow(1);
    			defaultcell = defaultrow.createCell(colNum);
    		}
    		//判断是否有维表数据（下拉框）
    		if (dataVal != null && dataVal.size() > 0) {
    			linkMap.put(colNum, dataVal);
    			//下拉框设置默认值
    			if (metaData == null || metaData.size() <= 0) {
    				defaultcell.setCellValue(dataVal.get(0));
    			}
    			
    			HSSFSheet dic = workbook.createSheet(cellText);
    			sheetIndex ++;
    			for (int i = 0; i < dataVal.size(); i++) {
    				HSSFRow dicRow = dic.createRow(i);
    				HSSFCell dicCell = dicRow.createCell(0);
    				dicCell.setCellType(HSSFCell.CELL_TYPE_STRING);//单元格
    				dicCell.setCellValue(dataVal.get(i));
    			}
    			String formula = dic.getSheetName()+"!$A$1:$A$"+dataVal.size();
    			CellRangeAddressList regions = new CellRangeAddressList(1, 60000, colNum, colNum);
    			DVConstraint constraint = DVConstraint.createFormulaListConstraint(formula);
    			HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);
    			sheet.addValidationData(data_validation);
    			workbook.setSheetHidden(sheetIndex, true);
    		}else{
    			if (cellType.equals("DATE")) {
    				start = colNum;
					CellRangeAddressList regions = new CellRangeAddressList(1, 60000, colNum, colNum);//设置范围  
					DVConstraint constraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.BETWEEN, "1900-01-01", "2999-12-31","yyyy-MM-dd");
					HSSFDataValidation dataValidate = new HSSFDataValidation(regions, constraint);  
					dataValidate.createErrorBox("错误", "日期范围必须在1900-01-01至2999-12-31之间");  
					sheet.addValidationData(dataValidate);
    				/*if (cellText.contains("起息日") || cellText.contains("起始日")) {
    					start = colNum;
    					CellRangeAddressList regions = new CellRangeAddressList(1, 60000, colNum, colNum);//设置范围  
    					DVConstraint constraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.BETWEEN, "1900-01-01", "2999-12-31","yyyy-MM-dd");
    					HSSFDataValidation dataValidate = new HSSFDataValidation(regions, constraint);  
    					dataValidate.createErrorBox("错误", "日期范围必须在1900-01-01至2999-12-31之间");  
    					sheet.addValidationData(dataValidate);
    				}
    				if (cellText.contains("到期日") || cellText.contains("到息日")) {
    					end = colNum;
    					CellRangeAddressList regions = new CellRangeAddressList(1, 60000, colNum, colNum);//设置范围  
    					DVConstraint constraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.BETWEEN, "1900-01-01", "2999-12-31","yyyy-MM-dd");
    					HSSFDataValidation dataValidate = new HSSFDataValidation(regions, constraint);  
    					dataValidate.createErrorBox("错误", "日期范围必须在1900-01-01至2999-12-31之间");  
    					sheet.addValidationData(dataValidate);
    				}*/
    			}else if(cellType.equals("STRING")){
    				sheet.setDefaultColumnStyle(colNum, textStyle);
    				sheet.setColumnWidth(colNum, 20*256);
    			}
    		}
    		colNum ++;
    	}
    	//导入执行的sql
    	HSSFSheet sheetSql = workbook.createSheet("SQL");
    	HSSFRow crow = sheetSql.createRow(0);
    	for (int i = 0; i < colName.size(); i++) {
    		HSSFCell scell = crow.createCell(i);
    		scell.setCellValue(colName.get(i).toLowerCase());
    	}
    	HSSFRow srow = sheetSql.createRow(1);
    	HSSFCell scell = srow.createCell(0);
    	scell.setCellValue(sql);
    	
    	workbook.setSheetHidden(sheetIndex+1, true);
    	
    	//开始导出数据
    	if (metaData != null && metaData.size() > 0) {
    		for (int i = 0; i < metaData.size(); i++) {
    			HSSFRow dataRow = sheet.createRow(i+1);
    			Map<String, Object> mapData = metaData.get(i);
    			for (int j = 0; j < colName.size(); j++) {
    				String columnName = colName.get(j).toLowerCase();
    				//业务编号直接跳过
					if ("business_no".equals(columnName)) {
						continue;
					}
    				String value = GlobalUtil.parse2String(mapData.get(columnName));
    				HSSFCell dataCell = dataRow.createCell(j);
    				List<String> linkList = linkMap.get(j);
    				if (linkList != null && linkList.size() > 0) {
    					for (int k = 0; k < linkList.size(); k++) {
    						String val = linkList.get(k);
    						if (val.contains("]")) {
    							String text = val.split("]")[1];
    							if (text != null && text.equals(value)) {
    								dataCell.setCellValue(val);
    								break;
    							}else{
    								dataCell.setCellValue(value);
    							}
    						}
    					}
    				}else
    					dataCell.setCellValue(value);
    			}
    		}
    	}
    	
    	return workbook;
    }
    
    /**
     * 补录模板导出
     * @param title  模板名称
     * @param excelHeadData  模板列头
     * @param sql  模板sql
     * @param colName  数据列名
     * @param metaData 导出的数据
     * @return
     * @throws Exception
     */
    public static HSSFWorkbook expTemplate(List<Map<String,Object>> excelList) throws Exception{ 
    	int dic_num = 0;
    	HSSFWorkbook workbook = new HSSFWorkbook();
    	HSSFSheet dic = null;
    	for (int m = 0; m < excelList.size();m++) {
    		Map<String,Object> map = excelList.get(m);
    		String title = GlobalUtil.getStringValue(map, "sheet_name");
        	Map<String,List<String>> excelHeadData = (Map<String, List<String>>) map.get("excelHeadData");
        	String default_value =  GlobalUtil.getStringValue(map, "default_value");
        	List<String> colName = (List<String>) map.get("colName");
        	List<Map<String, Object>> metaData  = (List<Map<String, Object>>) map.get("metaData");
        	
        	HSSFSheet sheet = workbook.createSheet(title);
        	
        	//默认列宽
        	sheet.setDefaultColumnWidth(20);
        	
        	Map<Integer,List<String>> linkMap = new LinkedHashMap<Integer,List<String>>();
        	
        	HSSFFont headerFont2 = workbook.createFont();
        	headerFont2.setFontHeightInPoints((short) 10);
        	
        	//设置报表表头单元格样
        	HSSFCellStyle headerCs2 = workbook.createCellStyle();
        	headerCs2.setFont(headerFont2);//设置字体	
        	headerCs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平对齐方式
        	headerCs2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式
        	headerCs2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//设置单元格显示颜
        	headerCs2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        	setBorderStyle(headerCs2);//设置单元格边框样
        	
        	//必输入单元格样式
            Font font = workbook.createFont();
            font.setColor(HSSFColor.RED.index);
            font.setFontHeightInPoints((short)10); //字体大小
//             font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
            
            HSSFCellStyle headerCs3 = workbook.createCellStyle();
            headerCs3.setFont(font);//设置字体	
            headerCs3.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平对齐方式
            headerCs3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式
            headerCs3.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//设置单元格显示颜
            headerCs3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            setBorderStyle(headerCs3);//设置单元格边框样
        	
        	HSSFRow titlerow = sheet.createRow(0);
        	
        	int start = 0;
        	int end = 0;
        	String date = "";
        	
        	HSSFRow defaultrow = null;
        	HSSFCell defaultcell = null;
        	
        	int colNum = 0;
        	int sheetIndex = 0;
        	
        	for(Entry<String,List<String>> entry : excelHeadData.entrySet()){
        		String key = entry.getKey();
        		List<String> dataVal = entry.getValue();
        		HSSFCell cell = titlerow.createCell(colNum);
        		cell.setCellStyle(headerCs2);//挷定样式到单元格	
        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);//单元格
        		String [] cellConfig = key.split("#"); //单元格配置
        		//开始拆分标题
        		String cellText = cellConfig[0];
        		String cellType = cellConfig[1];
        		String mustInput = cellConfig[2];
        		String cellFormula = cellConfig.length>3 ? cellConfig[3] : "";
        		//日期
        		if(cellType.equals("DATE")){
        			cellText = cellText + "[D]";
        		}
        		if("Y".equals(mustInput)){
        			cell.setCellStyle(headerCs3);
        			cell.setCellValue(cellText+"(必填)");
        		}else{
        			cell.setCellValue(cellText);
        		}
        		
//        		if (metaData == null || metaData.size() <= 0) {
//        			if(defaultrow==null){
//        				defaultrow = sheet.createRow(1);
//        			}
//        			defaultcell = defaultrow.createCell(colNum);
//        		}
        		//判断是否有维表数据（下拉框）
        		if (dataVal != null && dataVal.size() > 0) {
        			//创建下拉框值sheet
        			if(dic==null){
        				dic = workbook.createSheet("dic_val");
            			workbook.setSheetHidden(workbook.getSheetIndex(dic), true);
        			}
        			linkMap.put(colNum, dataVal);
//        			//下拉框设置默认值
//        			if (metaData == null || metaData.size() <= 0) {
//        				defaultcell.setCellValue(dataVal.get(0));
//        			}
        			for (int i = 0; i < dataVal.size(); i++) {
        				HSSFRow dicRow = dic.getRow(i);
        				if(dicRow==null){
            				dicRow = dic.createRow(i);
        				}
        				HSSFCell dicCell = dicRow.createCell(dic_num);
        				dicCell.setCellType(HSSFCell.CELL_TYPE_STRING);//单元格
        				dicCell.setCellValue(dataVal.get(i));
        			}
        			String colFlag = getcellColumnFlag(dic_num+1);
        			String formula = dic.getSheetName()+"!$"+colFlag+"$1:$"+colFlag+"$"+dataVal.size();
        			CellRangeAddressList regions = new CellRangeAddressList(1, 60000, colNum, colNum);
        			DVConstraint constraint = DVConstraint.createFormulaListConstraint(formula);
        			HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);
        			sheet.addValidationData(data_validation);
        			dic_num++;
        		}else{
        			if (cellType.equals("DATE")) {
        				start = colNum;
    					CellRangeAddressList regions = new CellRangeAddressList(1, 60000, colNum, colNum);//设置范围  
    					DVConstraint constraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.BETWEEN, "1900-01-01", "2999-12-31","yyyy-MM-dd");
    					HSSFDataValidation dataValidate = new HSSFDataValidation(regions, constraint);  
    					dataValidate.createErrorBox("错误", "日期范围必须在1900-01-01至2999-12-31之间");  
    					sheet.addValidationData(dataValidate);
        				/*if (cellText.contains("起息日") || cellText.contains("起始日")) {
        					start = colNum;
        					CellRangeAddressList regions = new CellRangeAddressList(1, 60000, colNum, colNum);//设置范围  
        					DVConstraint constraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.BETWEEN, "1900-01-01", "2999-12-31","yyyy-MM-dd");
        					HSSFDataValidation dataValidate = new HSSFDataValidation(regions, constraint);  
        					dataValidate.createErrorBox("错误", "日期范围必须在1900-01-01至2999-12-31之间");  
        					sheet.addValidationData(dataValidate);
        				}
        				if (cellText.contains("到期日") || cellText.contains("到息日")) {
        					end = colNum;
        					CellRangeAddressList regions = new CellRangeAddressList(1, 60000, colNum, colNum);//设置范围  
        					DVConstraint constraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.BETWEEN, "1900-01-01", "2999-12-31","yyyy-MM-dd");
        					HSSFDataValidation dataValidate = new HSSFDataValidation(regions, constraint);  
        					dataValidate.createErrorBox("错误", "日期范围必须在1900-01-01至2999-12-31之间");  
        					sheet.addValidationData(dataValidate);
        				}*/
        			}else if("STRING".equals(cellType)){
    					HSSFDataFormat hdf = workbook.createDataFormat();
    					HSSFCellStyle wCellStyle = workbook.createCellStyle();
    					wCellStyle.setDataFormat(hdf.getFormat("@"));
    					sheet.setDefaultColumnStyle(colNum, wCellStyle);
    					sheet.setColumnWidth(colNum, 20*256);
    				}else if("HIDDEN".equals(cellType)){
    					sheet.setColumnHidden(colNum, true);
    				}
        		}
        		colNum ++;
        	}
        	
//        	//导入执行的sql
//        	HSSFSheet sheetSql = workbook.createSheet("SQL");
//        	HSSFRow crow = sheetSql.createRow(0);
//        	for (int i = 0; i < colName.size(); i++) {
//        		HSSFCell scell = crow.createCell(i);
//        		scell.setCellValue(colName.get(i).toLowerCase());
//        	}
//        	HSSFRow srow = sheetSql.createRow(1);
//        	HSSFCell scell = srow.createCell(0);
//        	scell.setCellValue(sql);
//        	
//        	workbook.setSheetHidden(sheetIndex+1, true);
        	
        	//开始导出数据
        	if (metaData != null && metaData.size() > 0) {
        		for (int i = 0; i < metaData.size(); i++) {
        			HSSFRow dataRow = sheet.createRow(i+1);
        			Map<String, Object> mapData = metaData.get(i);
        			for (int j = 0; j < colName.size(); j++) {
        				String columnName = colName.get(j).toLowerCase();
        				//业务编号直接跳过
    					if ("business_no".equals(columnName)) {
    						continue;
    					}
        				String value = GlobalUtil.parse2String(mapData.get(columnName));
        				
        				HSSFCell dataCell = dataRow.createCell(j);
        				List<String> linkList = linkMap.get(j);
        				if (linkList != null && linkList.size() > 0) {
        					for (int k = 0; k < linkList.size(); k++) {
        						String val = linkList.get(k);
        						if (val.contains("]")) {
        							String text = val.split("]")[1];
        							if (text != null && text.equals(value)) {
        								dataCell.setCellValue(val);
        								break;
        							}else{
        								dataCell.setCellValue(value);
        							}
        						}
        					}
        				}else
        					dataCell.setCellValue(value);
        			}
        		}
        	}
		}
    	return workbook;
    }
    
    
    /**
     * 前端导出数据专用,直接导出不处理link相关信息
     * @param pConfig 父模版导出信息
     * @param subConfigs 子模版导出信息
     * @return
     * @throws Exception
     */
    public static HSSFWorkbook exportData(List<SheetConfig> sheets) throws Exception{ 
    	int dic_num = 0;
    	HSSFWorkbook workbook = new HSSFWorkbook();
    	HSSFSheet dic = null;
    	for (int m = 0; m < sheets.size(); m++) {
    		SheetConfig sheetConfig = sheets.get(m);
    		HSSFSheet sheet = workbook.createSheet(sheetConfig.getSheetName());
    		//生成数据字典
    		sheet.setDefaultColumnWidth(20);
    		
    		Map<Integer,List<String>> linkMap = new LinkedHashMap<Integer,List<String>>();
    		
    		HSSFFont headerFont2 = workbook.createFont();
    		headerFont2.setFontHeightInPoints((short) 10);
    		
    		//设置报表表头单元格样
    		HSSFCellStyle headerCs2 = workbook.createCellStyle();
    		headerCs2.setFont(headerFont2);//设置字体	
    		headerCs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平对齐方式
    		headerCs2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式
    		headerCs2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//设置单元格显示颜
    		headerCs2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    		setBorderStyle(headerCs2);//设置单元格边框样
    		
    		Font font = workbook.createFont();
    		font.setColor(HSSFColor.RED.index);
    		font.setFontHeightInPoints((short)10); //字体大小
//         font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
    		
    		HSSFCellStyle headerCs3 = workbook.createCellStyle();
    		headerCs3.setFont(font);//设置字体	
    		headerCs3.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平对齐方式
    		headerCs3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式
    		headerCs3.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//设置单元格显示颜
    		headerCs3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    		setBorderStyle(headerCs3);//设置单元格边框样
    		
    		HSSFRow titlerow = sheet.createRow(0);
    		
    		HSSFRow defaultrow = null;
    		HSSFCell defaultcell = null;
    		
    		int colNum = 0;
    		int sheetIndex = 0; //起始sheet的索引位置
    		
    		for(Entry<String,List<String>> entry : sheetConfig.getColumnData().entrySet()){
    			String key = entry.getKey();
    			List<String> dataVal = entry.getValue();
    			HSSFCell cell = titlerow.createCell(colNum);
    			cell.setCellStyle(headerCs2);//挷定样式到单元格	
    			cell.setCellType(HSSFCell.CELL_TYPE_STRING);//单元格
    			//开始拆分标题
    			String cellText = key.split("#")[0];
    			String cellType = key.split("#")[1];
    			String mustInput = key.split("#")[2];
    			//日期
    			if(cellType.equals("DATE")){
    				cellText = cellText + "[D]";
    			}
    			if("Y".equals(mustInput)){
    				cell.setCellStyle(headerCs3);
    				cell.setCellValue(cellText+"(必填)");
    			}else{
    				cell.setCellValue(cellText);
    			}
    			
    			//判断是否有维表数据（下拉框）
        		if (dataVal != null && dataVal.size() > 0) {
        			//创建下拉框值sheet
        			if(dic==null){
        				dic = workbook.createSheet("dic_val");
        				sheetIndex ++;
            			workbook.setSheetHidden(workbook.getSheetIndex(dic), true);
        			}
        			linkMap.put(colNum, dataVal);
 
        			for (int i = 0; i < dataVal.size(); i++) {
        				HSSFRow dicRow = dic.getRow(i);
        				if(dicRow==null){
            				dicRow = dic.createRow(i);
        				}
        				HSSFCell dicCell = dicRow.createCell(dic_num);
        				dicCell.setCellType(HSSFCell.CELL_TYPE_STRING);//单元格
        				dicCell.setCellValue(dataVal.get(i));
        			}
        			String colFlag = getcellColumnFlag(dic_num+1);
        			String formula = dic.getSheetName()+"!$"+colFlag+"$1:$"+colFlag+"$"+dataVal.size();
        			CellRangeAddressList regions = new CellRangeAddressList(1, 60000, colNum, colNum);
        			DVConstraint constraint = DVConstraint.createFormulaListConstraint(formula);
        			HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);
        			sheet.addValidationData(data_validation);
        			dic_num++;
        		}else{
        			if (cellType.equals("DATE")) {
    					CellRangeAddressList regions = new CellRangeAddressList(1, 60000, colNum, colNum);//设置范围  
    					DVConstraint constraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.BETWEEN, "1900-01-01", "2999-12-31","yyyy-MM-dd");
    					HSSFDataValidation dataValidate = new HSSFDataValidation(regions, constraint);  
    					dataValidate.createErrorBox("错误", "日期范围必须在1900-01-01至2999-12-31之间");  
    					sheet.addValidationData(dataValidate);
        			}else if("STRING".equals(cellType)){
    					HSSFDataFormat hdf = workbook.createDataFormat();
    					HSSFCellStyle wCellStyle = workbook.createCellStyle();
    					wCellStyle.setDataFormat(hdf.getFormat("@"));
    					sheet.setDefaultColumnStyle(colNum, wCellStyle);
    					sheet.setColumnWidth(colNum, 20*256);
    				}else if("HIDDEN".equals(cellType)){
    					sheet.setColumnHidden(colNum, true);
    				}
        		}
    			colNum ++;
    		}
    		
    		//导入执行的sql
    		if(sheetConfig.getSql()!=null){
    			HSSFSheet sheetSql = workbook.createSheet("SQL");
    			HSSFRow crow = sheetSql.createRow(0);
    			for (int i = 0; i < sheetConfig.getColumn().size(); i++) {
    				HSSFCell scell = crow.createCell(i);
    				scell.setCellValue(sheetConfig.getColumn().get(i).toLowerCase());
    			}
    			
    			HSSFRow srow = sheetSql.createRow(1);
    			HSSFCell scell = srow.createCell(0);
    			scell.setCellValue(sheetConfig.getSql());
    			workbook.setSheetHidden(sheetIndex+1, true);
    		}
    		
    		//开始导出数据
    		List<Map<String, Object>> metaData = sheetConfig.getDataList();
    		if (metaData != null && metaData.size() > 0) {
    			for (int i = 0; i < metaData.size(); i++) {
    				HSSFRow dataRow = sheet.createRow(i+1);
    				Map<String, Object> mapData = metaData.get(i);
    				for (int j = 0; j < sheetConfig.getColumn().size(); j++) {
    					String columnName = sheetConfig.getColumn().get(j).toLowerCase();
    					if ("business_no".equals(columnName)) {
    						continue;
    					}
    					String value = GlobalUtil.parse2String(mapData.get(sheetConfig.getColumn().get(j).toLowerCase()));
    					HSSFCell dataCell = dataRow.createCell(j);
    					List<String> linkList = linkMap.get(j);
    					if (linkList != null && linkList.size() > 0) {
    						for (int k = 0; k < linkList.size(); k++) {
    							String val = linkList.get(k);
    							if (val.contains("]")) {
    								String text = val.split("]")[1];
    								if (text != null && text.equals(value)) {
    									dataCell.setCellValue(val);
    									break;
    								}else{
    									dataCell.setCellValue(value);
    								}
    							}
    						}
    					}else
    						dataCell.setCellValue(value);
    				}
    			}
    		}
		}
      
        return workbook;
    }
    
    private static String getcellColumnFlag(int num) {
		String columFiled = "";
		int chuNum = 0;
		int yuNum = 0;
		if(num >= 1 && num <= 26){
			columFiled = doHandle(num);
		}else{
			chuNum = num / 26;
			yuNum = num % 26;

			columFiled +=  getcellColumnFlag(chuNum);
			columFiled +=  doHandle(yuNum);
		}
		return columFiled;
	}
    
	private static String doHandle(final int num) {
		String[] charArr = {"A","B","C","D","E","F","G","H","I","J","K","L","M"
				           ,"N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		return charArr[num-1].toString();
	}
}

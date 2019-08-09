package com.shuhao.clean.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportExcelService extends HttpServlet {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		//页面取值
        String tableHeader=request.getParameter("tableHeader");
        String tableColumnAlign=request.getParameter("tableColumnAlign");
        String tableInitCellWidth=request.getParameter("tableInitCellWidth");
        String tableData=request.getParameter("tableData");
        String tableTitle=request.getParameter("tableTitle");
        String exportHeader=request.getParameter("exportHeader");        
        String tableColType=request.getParameter("tableColType");
        String attachTableHeader=request.getParameter("attachTableHeader");
        
        if(tableData == null || tableData.equals("") || tableData.equals("null")){
        	tableData = (String)request.getAttribute("tableData");
        }
        if(tableHeader == null || tableHeader.equals("") || tableHeader.equals("null")){
        	tableHeader = (String)request.getAttribute("tableHeader");
        }
        if(tableColumnAlign == null || tableColumnAlign.equals("") || tableColumnAlign.equals("null")){
        	tableColumnAlign = (String)request.getAttribute("tableColumnAlign");
        }
        if(tableInitCellWidth == null || tableInitCellWidth.equals("") || tableInitCellWidth.equals("null")){
        	tableInitCellWidth = (String)request.getAttribute("tableInitCellWidth");
        }
        if(tableTitle == null || tableTitle.equals("") || tableTitle.equals("null")){
        	tableTitle = (String)request.getAttribute("tableTitle");
        }
        if(tableColType == null || tableColType.equals("") || tableColType.equals("null")){
        	tableColType = (String)request.getAttribute("tableColType");
        }
        
        /*********************/
        /**
         * 用来判断哪列缩进
         */
        int k = 0;
        if(null!=tableColType&&!"".equals(tableColType)){
        	for (int i = 0; i < tableColType.split(",").length; i++) {
				if("tree".equals(tableColType.split(",")[i])){
					k=i;
				}
			}
        }
        /*********************/
        List<String> titles=new ArrayList<String>();
        if(attachTableHeader!=null && !attachTableHeader.trim().isEmpty()){
        	String [] titleArr=attachTableHeader.split(";");
        	for (String title : titleArr) {
				titles.add(title);
			}
        }
        ExportDataUtil.setTableHeaderArray(titles);
        HSSFWorkbook workbook = ExportDataUtil.getFlexConfigWorkBook(tableHeader, tableColumnAlign,tableInitCellWidth, tableData,tableTitle,k);
        response.setContentType ("application/ms-excel") ;
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(tableTitle.getBytes("gb2312"),"iso-8859-1") + ".xls");
		
		OutputStream stream = response.getOutputStream();
		try {

			workbook.write(stream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.flush();
					stream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * Constructor of the object.
	 */
	public ExportExcelService() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); 
		// Put your code here
	}
}

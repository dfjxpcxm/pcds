package com.shuhao.clean.apps.sys.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.constant.ServerConstant;

/**
 * 文件操作Action
 * @author bixb
 *
 */
@Controller
@RequestMapping("/file")
public class FileCtrlr extends  BaseCtrlr {
	
	/**
	 * 上传文件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="upload",method=RequestMethod.POST)
	public void upload(HttpServletResponse response)throws Exception {
		Map<String, Object> paramMap = getRequestParam();
		
		String path = request.getRealPath("/");
		String tempPath = path+ServerConstant.UPLOAD_DIRCTORY +"temp/";
		Map<String, Object> result = new HashMap<String, Object>();
		String resultName = "";
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
    	try {
			java.io.File tmpfile = new java.io.File(tempPath);
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(10240000);// 设置缓冲区大小，这里是10MB
			factory.setRepository(tmpfile); // 设置临时目录

			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(102400000);// 设置最大文件尺寸，这里是100MB

			List fileItems = upload.parseRequest(request);// 得到所有的文件：
			Iterator i = fileItems.iterator();
			// 依次处理每一个文件：重命名文件
			String pfileName  = String.valueOf(System.currentTimeMillis());
			
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				String fileName = fi.getName();// 获得文件名，这个文件名包括路径：
				if(fileName == null ){
					continue;
				}
				//校验文件名称
				if(!this.checkFileName(fileName)){
					out.print("上传失败:文件格式不正确");
					out.flush();
					out.close();
					return;
				}
				
				pfileName = pfileName +fi.getName().substring(fi.getName().lastIndexOf(".")); 
				if (fileName != null) {
					fi.write(new File(tempPath + pfileName ));
				}
			}
			resultName = pfileName;
			
		} catch (Exception e) {
			e.printStackTrace();
			resultName = "上传失败:"+e.getMessage();
		}
		out.print(resultName);
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="deleteTempFile" )
	@ResponseBody
	public Map<String, Object> deleteTempFile(){
		String imgName = request.getParameter("imgName");
		String path = request.getRealPath("/");
		String tempPath = path+ServerConstant.UPLOAD_DIRCTORY +"temp/";
		
		File img = new File(tempPath+imgName);
		if(img.exists()){
			img.delete();
		}
		return doSuccessInfoResponse("删除成功");
	}
	
	
	 /**
     * 检验文件格式
     * 校验后缀 只允许 xls xlsx doc docx rar文件
     * @param fileName
     * @return
     */
    private  boolean checkFileName(String fileName){
    	//获取文件后缀
    	if(fileName == null ){//非空判断
    		return false;
    	}
    	//无后缀
    	if(fileName.indexOf(".") == -1){
    		return false;
    	}
    	String suffixName = fileName.substring(fileName.indexOf(".")+1);
    	if("xls".equals(suffixName)||"xlsx".equals(suffixName)
    		|| "doc".equals(suffixName)||"docx".equals(suffixName)
    		||"rar".equals(suffixName)
    	){
    		return true;
    	}
    	return false;
    }
}

package com.shuhao.clean.utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUpload {
	/**
	 * 文件上传类
	 * @param request
	 * @param response
	 * @param tempPath
	 * @param uploadPath
	 * @return
	 */
	public static String uploadFileIo(HttpServletRequest request, HttpServletResponse response, String tempPath, String uploadPath){
    	String fullFileName = "";
    	try {
			java.io.File tmpfile = new java.io.File(tempPath);
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(10240000);// 设置缓冲区大小，这里是10MB
			factory.setRepository(tmpfile); // 设置临时目录

			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(102400000);// 设置最大文件尺寸，这里是100MB

			List fileItems = upload.parseRequest(request);// 得到所有的文件：
			Iterator i = fileItems.iterator();
			// 依次处理每一个文件：
			String pfName="";
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				String fileName = fi.getName();// 获得文件名，这个文件名包括路径：
				
				if (fileName != null) {
					
					String pfileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					
					fi.write(new File(uploadPath + pfileName ));
					
					pfName = uploadPath + pfileName;
				}
			}
			fullFileName=pfName;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return fullFileName;
    }
	
}

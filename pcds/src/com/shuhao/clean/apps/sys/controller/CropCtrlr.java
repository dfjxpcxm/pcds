package com.shuhao.clean.apps.sys.controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.base.BaseCtrlr;

/**
 * 截图Action
 * @author bixb
 *
 */
@Controller
@RequestMapping("/crop")
public class CropCtrlr extends  BaseCtrlr {
	
	/**
	 * 上传图表文件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="upload",method=RequestMethod.POST)
	public void upload(HttpServletResponse response)throws Exception {
		Map<String, Object> paramMap = getRequestParam();
		
		String path = request.getRealPath("/");
		String tempPath = path +"upload/temp/";
//		String savePath = path + "test/upload/";
		
		String resultName = "";
//		String pfName = "";
		int srcWidth = 0;
		int srcHeight = 0;
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
			String pfileName  = "icon_"+System.currentTimeMillis();
			
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				String fileName = fi.getName();// 获得文件名，这个文件名包括路径：
				pfileName = pfileName +fi.getName().substring(fi.getName().lastIndexOf(".")); 
				if (fileName != null) {
					//pfileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					fi.write(new File(tempPath + pfileName ));
					//pfName = tempPath + pfileName;
				}
				// 读取源图像
				BufferedImage bi;
				bi = ImageIO.read(new File(tempPath + pfileName));
				srcWidth = bi.getWidth(); // 源图宽度
				srcHeight = bi.getHeight(); // 源图高度
			}
			resultName = pfileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
//    	return doSuccessInfoResponse(resultName+"@"+srcWidth+"@"+srcHeight);
//    	return doJSONResponse(resultName+"@"+srcWidth+"@"+srcHeight);
		PrintWriter out = response.getWriter();
		out.print(resultName+"@"+srcWidth+"@"+srcHeight);
		out.flush();
		out.close();
	
	}
	
	/**
	 * 截图操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="crop",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> crop()throws Exception{

		//获取切割参数
    	String startX = request.getParameter("x");
    	String startY = request.getParameter("y");
    	String width =  request.getParameter("w");
    	String height = request.getParameter("h");
    	String imgName = request.getParameter("imgName");
    	String viewWidth =  request.getParameter("viewWidth");
    	String viewHeight = request.getParameter("viewHeight");
    	
    	String path = request.getRealPath("/");
		String tempPath = path +"upload/temp/";
		String savePath = path + "upload/icon/";
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//切割
	    	this.cropImp(tempPath+imgName, savePath+imgName, Integer.parseInt(startX), Integer.parseInt(startY), Integer.parseInt(width), Integer.parseInt(height), Integer.parseInt(viewWidth), Integer.parseInt(viewHeight));
	    	result.put("imgName", imgName);
		    result.put("info", "截取成功");
		    result.put("success", Boolean.valueOf(true));
		} catch (Exception e) {
			result.put("info", e.getMessage());
		    result.put("success", Boolean.valueOf(false));
		}
		return result;
	
	}
	
	private void cropImp(String oldImg,String newImg,int startX,int startY,int width,int height,int viewWidth,int viewHeight) throws Exception{
    	try {
    	Image img;
		ImageFilter cropFilter;
		// 读取源图像
		BufferedImage bi;
		bi = ImageIO.read(new File(oldImg));
		String format = oldImg.substring(oldImg.lastIndexOf(".")+1); 
		
		int srcWidth = bi.getWidth(); // 源图宽度
		int srcHeight = bi.getHeight(); // 源图高度
		
//		System.out.println(" 源图宽度:"+srcWidth);
//		System.out.println(" 源图高度:"+srcHeight);
//		System.out.println(" 截图宽度：" + width);
//		System.out.println(" 截图高度：" + height );
//		System.out.println(" 显示宽度：" + viewWidth);
//		System.out.println(" 显示高度：" + viewHeight);
		
		// 若原图大小大于切片大小，则进行切割
//		if (srcWidth >= width && srcHeight >= height) {
			Image image = bi.getScaledInstance(srcWidth, srcHeight,
					Image.SCALE_DEFAULT);

			int x1 = startX * srcWidth / viewWidth;
			int y1 = startY * srcHeight / viewHeight;
			int w1 = width * srcWidth / viewWidth;
			int h1 = height * srcHeight / viewHeight;

			cropFilter = new CropImageFilter(x1, y1, w1, h1);
			img = Toolkit.getDefaultToolkit().createImage(
					new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage tag = null;
			//PNG图像特殊处理
			if("PNG".equalsIgnoreCase(format)){
				tag = new BufferedImage(48, 48,
						BufferedImage.TYPE_INT_ARGB_PRE);
			}else{
				tag = new BufferedImage(48, 48,
						BufferedImage.TYPE_INT_RGB);
			}
			
			Graphics g = tag.getGraphics();
//			g.drawImage(img, 0, 0, null); // 绘制缩小后的图
			g.drawImage(img, 0, 0, 48,48,null);  
			g.dispose();
			// 输出为文件
			ImageIO.write(tag, format, new File(newImg));
			
			//删除中间文件
			new File(oldImg).delete();
//		}
    	} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
    }
	
	@RequestMapping(value="deleteTempFile" )
	@ResponseBody
	public Map<String, Object> deleteTempFile(){
		String imgName = request.getParameter("imgName");
		String path = request.getRealPath("/");
		String tempPath = path +"upload/temp/";
		
		File img = new File(tempPath+imgName);
		if(img.exists()){
			img.delete();
		}
		return doSuccessInfoResponse("删除成功");
	}
}

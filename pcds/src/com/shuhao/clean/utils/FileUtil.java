package com.shuhao.clean.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;

/**
 * 文件处理 此目前只用于图片
 * <b>Date:</b>Aug 29, 2013<br>
 * @author wangfl
 * @version $Revision$
 */
public class FileUtil {
	
	private static Logger logger = Logger.getLogger(FileUtil.class);
	
    public static String FilePath;

    public static boolean existFile(String path, String fileName) {
        File f = new File(path, fileName);
        return f.exists();
    }

    public static boolean existFile(String filePath) {
        File f = new File(filePath);
        return f.exists();
    }

    /**
     * 创建目录
     * @param dir
     */
    public static void mkDirs(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 
     * @param path
     *            目录名
     * @param fileName
     *            文件名
     * @throws Exception
     */
    public static File createNewFile(String path, String fileName) throws Exception {
        File dir = new File(path);
        if (dir.exists()) {
            dir.mkdirs();
        }

        File f = new File(path, fileName);

        if (f.exists()) {// 检查File.txt是否存在
            f.delete();// 删除File.txt文件
            logger.info(path + "\\" + fileName + " 存在，已删除。");
        }
        else {
            f.createNewFile();// 在当前目录下建立一个名为File.txt的文件
            logger.info(path + "\\" + fileName + " 不存在，已建立。");
        }
        return f;
    }
    
    /**
     * 重新创建文件，如果存在覆盖
     * @param path
     * @param fileName
     * @throws Exception
     */
    public synchronized static File replaceFile(String path, String fileName) throws Exception {

        File dir = new File(path);
        if (dir.exists()) {
            dir.mkdirs();
        }

        File f = new File(path, fileName);

        if (f.exists()) {// 检查File.txt是否存在
            f.delete();// 删除File.txt文件
            logger.info(path + "\\" + fileName + " 存在，已删除。");
            f.createNewFile();// 在当前目录下建立一个名为File.txt的文件
            logger.info(path + "\\" + fileName + " 已创建。");
        }
        else {
            f.createNewFile();// 在当前目录下建立一个名为File.txt的文件
            logger.info(path + "\\" + fileName + " 不存在，已建立。");
        }
        return f;
    }

    /**
     * 读取文件
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String readFile(String filePath) throws Exception {
        FileReader fr = new FileReader(filePath);// 建立FileReader对象，并实例化为fr
        BufferedReader br = new BufferedReader(fr);// 建立BufferedReader对象，并实例化为br
        String Line = br.readLine();// 从文件读取一行字符串
        StringBuffer str = new StringBuffer();
        // 判断读取到的字符串是否不为空
        while (Line != null) {
        	str.append(Line).append("\n");
            Line = br.readLine();// 从文件中继续读取一行数据
        }
        br.close();// 关闭BufferedReader对象
        fr.close();// 关闭文件
        return str.toString();
    }
    
    /**
     * 写入文件
     * @param filePath
     * @param line
     * @throws Exception
     */
	public synchronized static void writeFile(String filePath,String fileName,String line) throws Exception {
		FileWriter fw = new FileWriter(filePath+fileName);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line);
		bw.newLine();// 断行
		bw.flush();// 将数据更新至文件
		fw.close();// 关闭文件流
	}
	
	public synchronized static void writeFile(File file,String line) throws Exception {
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line);
		bw.newLine();// 断行
		bw.flush();// 将数据更新至文件
		fw.close();// 关闭文件流
	}

    /**
     * 删除目录下的文件及目录下的其他目录
     * 
     * @param file
     */
    public static void deleteFile(File file) {
        deleteFile(file, 0);
    }

    /**
     * 删除目录下所有文件,目录不删除；如果file为文件，level>0 删除
     * @param file
     * @param level
     */
    public static void deleteFile(File file, int level) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i], level + 1);
                }
            }
            if (level != 0) {// 如果是chart目录的话不删除
                file.delete();
            }
        }
    }

    /**
     * 删除过滤文件
     * @param file
     * @param filter 过滤文件名
     */
    public static void deleteFile(File file, String filter) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i], filter);
                }
            }
            else {
                if (file.getName().indexOf(filter) > -1) {
                    file.delete();
                }
            }
        }
    }
    
    /**
     * 检验文件格式
     * 校验后缀 只允许 xls xlsx doc docx rar文件
     * @param fileName
     * @return
     */
    public static boolean checkFileName(String fileName){
    	//获取文件后缀
    	if(fileName == null ){//非空判断
    		return false;
    	}
    	//无后缀
    	if(fileName.indexOf(".") == -1){
    		return false;
    	}
    	String suffixName = fileName.substring(fileName.indexOf(".")+1);
    	if("xls".equalsIgnoreCase(suffixName)||"xlsx".equalsIgnoreCase(suffixName)
    		|| "doc".equalsIgnoreCase(suffixName)||"docx".equalsIgnoreCase(suffixName)
    		||"rar".equalsIgnoreCase(suffixName)
    	){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 取二进制文件
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] getBytes(File file) throws IOException{
		byte[] ret = null;
		FileInputStream in = null;
		ByteArrayOutputStream out = null;

		try {
			in = new FileInputStream(file);
			out = new ByteArrayOutputStream(1024);
			byte[] b = new byte[1024];
			int n;
			while ((n = in.read(b)) != -1) {
				out.write(b, 0, n);
			}

			ret = out.toByteArray();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
			try {
				out.close();
			} catch (Exception e) {
			}
		}
		return ret;
	}
	
    /**
     * 想文件末尾最近字符串
     * @param file
     * @param line
     */
	public static void append(File file,String line)
	{
		BufferedWriter bw=null;
		OutputStreamWriter osw=null;
		FileOutputStream fos=null;
		try{
			fos = new FileOutputStream(file,true);
			osw = new OutputStreamWriter(fos);
			bw=new BufferedWriter(osw);
			bw.write(line);
			bw.newLine();
			bw.flush();
		}catch(Exception e)
		{
			
		}
		finally{
			try {
				fos.close();
			} catch (IOException e) {
			}
			try {
				osw.close();
			} catch (IOException e) {
			}
			try {
				bw.close();
			} catch (IOException e) {
			}
		}
	}

    public static String getClassLoaderPath()
	{
		String path=FileUtil.class.getClassLoader().getResource("").getPath();
		path = path.replaceAll("%20", " ");
		return path;
	}
	
	public static String getWebRootPath()
	{
	    String classLoaderPath = getClassLoaderPath();
	    File file = new File(classLoaderPath);
	    String path = file.getParentFile().getParentFile().getAbsolutePath();
	    path = path.replaceAll("%20", " ");
	    return path;
	}
	
	public static void main(String[] args) throws Exception{
		
		String pathname = FileUtil.getWebRootPath()+File.separator+"public"+File.separator+"css"+File.separator+"icon.css";
		FileReader fr = new FileReader(pathname);// 建立FileReader对象，并实例化为fr
		
		BufferedReader br = new BufferedReader(fr);// 建立BufferedReader对象，并实例化为br
		String Line = br.readLine();// 从文件读取一行字符串
		StringBuffer str = new StringBuffer();
		
		// 判断读取到的字符串是否不为空
		while (Line != null) {
			if(Line.equals("")){
				break;
			}
			str.append(Line.substring(Line.indexOf(".")+1, Line.indexOf("{"))).append("\n");
			Line = br.readLine();// 从文件中继续读取一行数据
		}
		br.close();// 关闭BufferedReader对象
		fr.close();// 关闭文件
		
		
		System.out.println(str.toString());
	}
}

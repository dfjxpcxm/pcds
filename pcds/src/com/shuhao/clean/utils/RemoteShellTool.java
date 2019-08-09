/**
 * FileName:     RemoteShellTool.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2015-4-20 下午2:20:02 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-4-20       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.shuhao.clean.utils.xml.XmlUtil;

/**
 * @Description: TODO
 * 
 * @author: gongzhiyang
 */
public class RemoteShellTool {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	private Connection conn;
	private String ipAddr;
	private String userName;
	private String password;
	private String charset = Charset.defaultCharset().toString();
	
	private List<String> cmds = new ArrayList<String>(); //命令列表
	
	public static void main(String[] args) throws Exception{
		RemoteShellTool tool = new RemoteShellTool();
		for (int i = 0; i < 5; i++) {
			String s = tool.exec("sh /root/shl/test.sh");
			System.out.println(s);
		}
	}
	
	public RemoteShellTool(){
		try {
			logger.info("开始解析ssh2.xml配置文件.");
			Document doc = XmlUtil.getDocument("ssh2.xml");
			Element root = doc.getRootElement();
			this.ipAddr = root.elementText("ipAddr");
			this.userName = root.elementText("userName");
			this.password = root.elementText("password");
			String s = root.elementText("charset");
			if(!s.trim().equals("")){
				this.charset  = s;
			}
			//命令行
			if(root.element("cmds")!=null){
				List<Element> cmdEle = root.element("cmds").elements("cmd");
				for (Element element : cmdEle) {
					this.cmds.add(element.getText());
				}
			}
			logger.info("开始解析ssh2.xml配置文件成功.");
		} catch (Exception e) {
			logger.error("解析ssh2.xml报错，"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public RemoteShellTool(String ipAddr, String userName, String password,
			String charset) {
		this.ipAddr = ipAddr;
		this.userName = userName;
		this.password = password;
		if (charset != null) {
			this.charset = charset;
		}
	}

	/**
	 * 登录远程Linux主机
	 * 
	 * @return
	 * @throws IOException
	 */
	public boolean login() throws IOException {
		conn = new Connection(ipAddr);
		logger.info("连接服务器:" + this.ipAddr + "....");
		conn.connect(); // 连接
		logger.info("连接成功,正在登陆....");
		return conn.authenticateWithPassword(userName, password); // 认证
	}

	/**
	 * 执行Shell脚本或命令,每次执行都关闭
	 * 
	 * @param cmds
	 *            命令行序列
	 * @return
	 */
	public String exec(String cmds) {
		InputStream in = null;
		String result = "";
		Session session = null;
		try {
			if (this.login()) {
				session = conn.openSession(); // 打开一个会话
				logger.info("打开session");
				session.execCommand(cmds);
				logger.info("执行命令:"+cmds);
				in = session.getStdout();
				result = this.processStdout(in, this.charset);
				logger.info("执行命令成功。");
			}
		} catch (IOException e1) {
			result = "[ERROR]"+e1.getMessage()+result;
			logger.info("执行命令失败,"+e1.getMessage());
		}finally{
			try {
				conn.close();
				logger.info("关闭连接.");
			} catch (Exception e) { }
		}
		return result;
	}
	
	/**
	 * 执行默认的命令
	 * @return
	 */
	public List<String> defaultExec(){
		if(this.cmds.size()>0){
			List<String> results = new ArrayList<String>();
			for (String cmd : cmds) {
				results.add(exec(cmd));
			}
			return results;
		}
		return null;
	}
	
	/**
	 * 执行带字符串参数的命令
	 * @return
	 */
	public List<String> execStrPara(String param){
		if(this.cmds.size()>0){
			List<String> results = new ArrayList<String>();
			for (String cmd : cmds) {
				results.add(exec(cmd+" "+param));
			}
			return results;
		}
		return null;
	}

	/**
	 * 解析流获取字符串信息
	 * 
	 * @param in
	 *            输入流对象
	 * @param charset
	 *            字符集
	 * @return
	 */
	public String processStdout(InputStream in, String charset) {
		byte[] buf = new byte[1024];
		StringBuffer sb = new StringBuffer();
		try {
			while (in.read(buf) != -1) {
				sb.append(new String(buf, charset));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
package com.shuhao.clean.utils;

import java.security.MessageDigest;
import sun.misc.BASE64Encoder;
public class Md5Util {
	public static String getPasswordForMD5(String password){
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64 = new BASE64Encoder();
			return base64.encode(md5.digest(password.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

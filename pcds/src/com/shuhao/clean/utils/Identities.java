package com.shuhao.clean.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类,引用spring-side4
 * 
 * @author gongzy
 */
public class Identities {

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid2() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}
	
	/**
	 * 使用SecureRandom随机生成Int.
	 */
	public static long randomInt(int n) {
		return Math.abs(random.nextInt(n));
	}
	
	/**
	 * 按日期和长度生成随机数
	 * @param length
	 * @return
	 */
	public static String getRandomID(int length)
	{
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        sb.append(System.currentTimeMillis());

        int size=length-sb.length();
        for (int i = 0; i < size; i++) {
            sb.append(random.nextInt(10));
        }

        String s=sb.toString();
        if(s.length()>length)
        	s=s.substring(s.length()-length);
        
        return s;
	}
	
	/**
	 * 获取随机数数组
	 * @param length 随机数长度
	 * @param ary 随机数个数
	 * @return
	 */
	public static String[] getRandomArray(int length,int ary)
	{
		String randoms[]=new String[ary];
		
		for (int i = 0; i < ary; i++) {
			randoms[i]=getRandomID(length);
			
			for (int j = 0; j <i; j++) {
				if(randoms[j].equals(randoms[i])){
					i--;
					break;
				}
			}
		}
        return randoms;
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}
	
	public static void main(String[] args) {
		System.out.println(Identities.randomBase62(18));
		System.out.println(Identities.getRandomID(18));
	}
}

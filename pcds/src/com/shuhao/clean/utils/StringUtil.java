package com.shuhao.clean.utils;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 操作String 字符串的工具类
 * @author gongzy
 *
 */
public class StringUtil {

	public static boolean isNullStr(String str) {
		return null == str || "".equals(str.trim());
	}

	public static boolean isNumAndCharStr(String str) {
		if (str.matches("[0-9A-Za-z]+"))
			return true;
		return false;
	}

	public static boolean isNumStr(String str) {
		if (str.matches("[0-9]+"))
			return true;
		return false;
	}
	
	/**
	 * 将数组转换为string
	 * @param list
	 * @param delimiter
	 * @return
	 */
	public static String arrayListToStr(List<String> list,String delimiter)
	{
		String pkStr="";
		if(list==null) return "";
		for(int i=0;i<list.size();i++)
			pkStr=pkStr+list.get(i).toString()+delimiter;
		return pkStr.substring(0,pkStr.length()-delimiter.length());
	}

	public static String ToHexString(String str) {
		String hexString = "";
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			hexString += "\\u" + Integer.toHexString((int) c);
		}
		return hexString;
	}

	public static String getExceptionMessages(String prestr, Throwable e) {
		if (e == null)
			return "";
		String str = e.toString() + "\r\n";
		if (prestr != null)
			str = prestr + str;
		StackTraceElement[] stes = e.getStackTrace();
		for (int i = 0, len = stes.length; i < len; i++) {
			str = str + "\t" + stes[i].toString() + "\r\n";
		}

		prestr = "Caused by: ";
		str = str + getExceptionMessages(prestr, e.getCause());
		return str;
	}

	public static boolean isTrue(String bstr) {
		if (isNullStr(bstr))
			return false;
		if (bstr.toLowerCase().equals("true")
				|| bstr.toLowerCase().equals("yes")
				|| bstr.toLowerCase().equals("t")
				|| bstr.toLowerCase().equals("y"))
			return true;
		return false;
	}

	public static String getNotNullString(String str) {
		return str == null ? "" : str;
	}

	/**
	 * string 数组快速排序
	 * @param a
	 * @param lo0
	 * @param hi0
	 * @return
	 */
	public static String[][] QuickSort(String a[][], int lo0, int hi0) {
		int lo = lo0;
		int hi = hi0;
		if (hi0 > lo0) {
			int mid = Integer.parseInt(a[(lo0 + hi0) / 2][0]);
			do {
				if (lo > hi)
					break;
				for (; lo < hi0 && Integer.parseInt(a[lo][0]) < mid; lo++)
					;
				for (; hi > lo0 && Integer.parseInt(a[hi][0]) > mid; hi--)
					;
				if (lo <= hi) {
					String[] str = null;
					str = a[lo];
					a[lo] = a[hi];
					a[hi] = str;
					lo++;
					hi--;
				}
			} while (true);
			if (lo0 < hi)
				QuickSort(a, lo0, hi);
			if (lo < hi0)
				QuickSort(a, lo, hi0);
		}
		return a;
	}

 
	/** * 转换unicode字符串为其对应的实际字符�?, 
	 * UnicodeToString("测试\\u4E2D\\u6587") 输出�?: "测试中文" *  
	 * * @param str 
	 * * @return */
	public static String UnicodeToString(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			//str = str.replace(matcher.group(1), ch + "");
			str = str.replaceAll(matcher.group(1), ch + "");
		}
		return str;
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
 	
	public static void main(String[] args) {
		
		
	}
}

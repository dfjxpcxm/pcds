package com.shuhao.clean.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.shuhao.clean.apps.validate.vrules.domain.ValidResult;
/**
 * 
 * @author Administrator
 *
 * map = {id=1,name=测试1,value=11.0,balance=,email=gzy123@qq.com}
 * 
 *     = {200001=1,200001=测试1,20002=11.0,200003=,200003 = gzy123@qq.com}
 *
 */
public class Test {
	public static void main(String[] args) throws Exception {
		String ssss="a b c\n" +
				" aaaa \n" +
				"ddd \n";
		System.out.println(ssss.replaceAll("\\s", " "));
		
		System.out.println(Pattern.matches("^\\d*(\\.\\d{1,4})?$", "111111.00100"));
		System.out.println(Pattern.matches("^0\\.\\d{1,4}$", "0.3311"));
		
		
		String as = "aa?rootId=root&rootName=元数据";
		System.out.println(as.substring(as.indexOf("?")));
		
		String value=null;
		
		String formula ="__self_val != nil";
		Map<String, Object> env11 = new HashMap<String, Object>();
		env11.put("__self_val", value);
		//替换变量
		System.out.println(formula);
		System.out.println(AviatorEvaluator.execute(formula,env11));
		

		Map<String, Object> map = new HashMap<String, Object>();  
		String obj = null;
		map.put("int_start_date", "2014-12-01"); 
		map.put("int_due_date","2014-12-01"); 
		String s1 = "string_to_date('[@int_start_date]','%Y-%m-%d')<=string_to_date('[@int_due_date]','%Y-%m-%d')";
		String s2 = GlobalUtil.getParamsForReplace(s1, "[@", "]", map);
		System.out.println(s2);
		System.out.println("dddddddd:"+AviatorEvaluator.execute(s2,map));
		System.out.println(map);
		//System.out.println(dd.replaceAll("[@self_val]", ""));
		System.out.println("---"+AviatorEvaluator.execute("__int_due_date != nil && __int_due_date != ''",map));
		//System.out.println("aaa:"+AviatorEvaluator.execute("__int_due_date != nil ? string_to_date(__int_start_date,'yyyy-MM-dd')<=string_to_date(__int_due_date,'yyyy-MM-dd'):true",map));  
//		System.out.println("aaa:"+AviatorEvaluator.execute("__end_date != nil ? string_to_date(__begin_date,'yyyy-mm-dd')<=string_to_date(__end_date,'yyyy-mm-dd'):true",map));  
//		System.out.println( AviatorEvaluator.execute("string_to_date(__int_start_date,'yyyy-mm-dd')<=string_to_date(__int_due_date,'yyyy-mm-dd')  ",map));
		
//		System.out.println("exe:"+AviatorEvaluator.execute("string_to_date('2014-10-10','yyyy-mm-dd')<string_to_date('2014-9-10','yyyy-mm-dd')",map));
			
	//	System.out.println("exe:"+AviatorEvaluator.execute("string.length($self_val) < 100"));
		//System.out.println("exe1:"+AviatorEvaluator.execute("string.length('aaaaa')<13"));
		
		String str = "我是{0},我来自{1},今年{2}岁";
		String[] arr = { "中国人", "北京", "22" };
		System.out.println(fillStringByArgs(str, arr));
		
		String t1= "^[+\\-]?\\d+(.[0-9]{1,{0}})?$";
		System.out.println(t1);
		System.out.println(fillStringByArgs(t1, new String[]{"4"}));

		String str1 = "大秦 {0} {1} {2}";
		System.out.println(MessageFormat.format(str1, "将军", "梦回", "秦朝"));

		boolean b = Pattern.matches("^\\d+[0-9]?\\d+$", "1a2223");// 返回true
		System.out.println(b);
		
		//算术表达式：
		Long result1 = (Long) AviatorEvaluator.execute("1+2+3");  
		System.out.println(result1);  
		//逻辑表达式和关系运算：
		System.out.println("3y:"+AviatorEvaluator.execute("(3>1 && 2!=4 || true)"));  
		
		//用变量和字符串相加
		String yourname = "aviator";  
		 Map<String, Object> env = new HashMap<String, Object>();  
		env.put("yourname", yourname);  
		String result = (String) AviatorEvaluator.execute(" 'hello ' + yourname ", env);  
		System.out.println(result);  
		
		//变量的访问支持嵌套访
//		Foo foo = new Foo(100, 3.14f, new Date());  
//		Map<String, Object> env1 = new HashMap<String, Object>();  
//		env1.put("foo", foo);  
//		System.out.println(foo.i);
//		String resultpo =  (String) AviatorEvaluator.execute(" '[foo i='+ foo.i + ' f='+foo.f+' year='+(foo.date.year+1900)+ ' month='+foo.date.month +']' ",  env1);  

		//三元表达式
		AviatorEvaluator.execute("3>0? 'yes':'no'");  
		
		//类Ruby、Perl的正则匹配
		Boolean bo = (Boolean)AviatorEvaluator.execute("'killme2008'=~/([\\w0-8]+@\\w+[\\.\\w+]+)/ ");  
		System.out.println(bo);
		//匹配成功，获得匹配的分组，利用变量$digit
		AviatorEvaluator.execute("'killme2008@gmail.com'=~/([\\w0-8]+@\\w+[\\.\\w+]+)/ ? $1:'unknow'");  
		
//		函数调用
		AviatorEvaluator.execute("sysdate()");  
		AviatorEvaluator.execute("string.length('hello')");    // 求字符串长度  
		AviatorEvaluator.execute("string.contains('hello','h')");  //判断字符串是否包含字符串  
		AviatorEvaluator.execute("string.startsWith('hello','h')");  //是否以子串开头  
		AviatorEvaluator.execute("string.endsWith('hello','llo')");  //是否以子串结尾  
		AviatorEvaluator.execute("math.pow(-3,2)");   // 求n次方  
		AviatorEvaluator.execute("math.sqrt(14.0)");   //开平方根  
		AviatorEvaluator.execute("math.sin(20)");    //正弦函数  
		
		//自定义函数
		
		//seq
		Map<String, Object> env2 = new HashMap<String, Object>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(3);
		list.add(100);
		list.add(-100);
		env2.put("list", list);
		
		System.out.println(AviatorEvaluator.execute("count(list)",env2));
		
		System.out.println(AviatorEvaluator.execute("map(list,math.log)",env2));

	}
	
	private static String fillStringByArgs(String str, String[] arr) {
		Matcher m = Pattern.compile("\\{(\\d)\\}").matcher(str);
		while (m.find()) {
			str = str.replace(m.group(), arr[Integer.parseInt(m.group(1))]);
		}
		return str;
	}
	
	public static String deepIff(String str,String leftEl,String rightEl){
		if(str.indexOf(leftEl)>-1){
			
			char [] chs = str.toCharArray();
			int s=0,e=chs.length;
			
			int leftLength = leftEl.length();
			int rightLength = rightEl.length();
			
			String left="",right="" ;
			
			while(s <= e){
				if(chs[s] == '\0'){
					s++;
					continue;
				}
				
				
				if(left.length()==leftLength){
					if(left.equalsIgnoreCase(leftEl)){
						break;
					}else{
						//计算left
						left  = left.substring(1) + chs[s];
						s++;
					}
				}else{
					left += chs[s];
					s++;
				}
			}
			
			while(s <= e){
				if(chs[e] == '\0'){
					e--;
					continue;
				}
				
				if(right.length()==rightLength){
					if(right.equalsIgnoreCase(rightEl)){
						break;
					}else{
						//计算left
						right  = right.substring(1) + chs[e];
						e--;
					}
				}else{
					right += chs[e];
					e--;
				}
			}
			
			if(s>=e){
				
				
				return str;
			}else{
				String temp = str.substring(s+leftLength,e);
				return deepIff(temp, leftEl, rightEl);
			}
		}
		
		return str;
	}
	
	
	public static String deepIff1(String str,String leftEl,String rightEl){
		if(str.indexOf(leftEl)>-1){
			
			char [] chs = str.toCharArray();
			int s=0,e=chs.length;
			
			int leftLength = leftEl.length();
			int rightLength = rightEl.length();
			
			String left="",right="" ;
			
			while(s <= e){
				if(chs[s] == '\0'){
					s++;
					continue;
				}
				
				
				if(left.length() == leftLength){
					if(left.equalsIgnoreCase(leftEl)){
						break;
					}else{
						//计算left
						left  = left.substring(1) + chs[s];
					}
				}else{
					left += chs[s];
				}
				
				if(right.length() == rightLength){
					if(right.equalsIgnoreCase(rightEl)){
						break;
					}else{
						//计算left
						right  = right.substring(1) + chs[s];
					}
				}else{
					right += chs[s];
				}
				
				s++;
			}
			
			while(s <= e){
				if(chs[e] == '\0'){
					e--;
					continue;
				}
				
				if(right.length()==rightLength){
					if(right.equalsIgnoreCase(rightEl)){
						break;
					}else{
						//计算left
						right  = right.substring(1) + chs[e];
						e--;
					}
				}else{
					right += chs[e];
					e--;
				}
			}
			
			if(s>=e){
				
				
				return str;
			}else{
				String temp = str.substring(s+leftLength,e);
				return deepIff(temp, leftEl, rightEl);
			}
		}
		
		return str;
	}
}
//自定义函数
class addFun extends AbstractFunction{

	public AviatorObject call(Map<String, Object> env, AviatorObject
			arg1, AviatorObject arg2) {
			Number left = FunctionUtils.getNumberValue(arg1, env);
			Number right = FunctionUtils.getNumberValue(arg2, env);
			return new AviatorDouble(left.doubleValue() +
			right.doubleValue());
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

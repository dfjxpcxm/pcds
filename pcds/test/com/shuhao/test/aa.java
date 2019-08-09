package com.shuhao.test;
/**
 * FileName:     aa.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2015-10-15 下午11:55:37 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-10-15       author          1.0             1.0
 * Why & What is modified: 
 */

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
public class aa {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(PeachNumber(5));
		System.out.println(PeachNumber1(5));
		System.out.println(peach(5));
	}
	public static int PeachNumber(int n) 
     {
         if (n == 1)
         {
             //最后一个是至少是六个
             return 6;
         }
         else 
         {
             return  PeachNumber(n - 1) * 5 + 1;
         }
     }
	
	  public static int PeachNumber1(int n) 
       {
           if (n == 1)
           {
               //最后一个是至少是六个
               return 6;
           }
           else 
           {
               return (PeachNumber1(n - 1) + 1) * 5;
           }
       }
	   
	public static int peach(int count) {
		  int peach = 1; //最后剩一个桃子
		  for (int i = 0; i < count; i++) {
		   peach = peach * 5; //如果最初多四个就可以一直被5整除
		  }
		  return peach - 4; //最后把多出的4个减去
		 }
}

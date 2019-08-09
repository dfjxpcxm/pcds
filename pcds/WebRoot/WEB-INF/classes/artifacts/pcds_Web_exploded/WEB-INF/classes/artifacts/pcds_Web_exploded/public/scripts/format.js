/**
 * 格式化字符串为 ##,###.00
 * @param {Object} s
 * @param {Object} n 保留小数位数
 * @return {TypeName} 
 */
function fmoney(s, n)   
{  if(!s){s='0';} 
   n = n > 0 && n <= 20 ? n : 2;   
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
   var l = s.split(".")[0].split("").reverse(),   
   r = s.split(".")[1];   
   t = "";   
   for(i = 0; i < l.length; i ++ )   
   {   
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
   }   
   return t.split("").reverse().join("") + "." + r;   
}
/**
 * 转换符串 ##,###.00 为 number类型
 * @param {Object} s (如果s==null 返回 0)
 * @return {TypeName} 
 */
function rmoney(s)
{   
   if(!s)return 0;
   
   return parseFloat(s.replace(/[^\d\.-]/g, ""));   
}   
/**
 * ext格式化数字列
 * @param v
 * @param n
 * @returns {String}
 */
function valueFormat(v,n){
	var fvalue = fmoney(v,n);
	return "<div align='right'>" + fvalue + "</div>";
}
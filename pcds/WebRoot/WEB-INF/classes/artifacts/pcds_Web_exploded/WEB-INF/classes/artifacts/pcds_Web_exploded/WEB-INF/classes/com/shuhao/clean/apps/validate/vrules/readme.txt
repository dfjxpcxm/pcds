--内置函数
函数名称                                                                                                        说明
sysdate()                          返回当前日期对象 java.util.Date
rand()                             返回一个介于 0-1 的随机数，double 类型
print( [ out ] ,obj)               打印对象，如果指定 out，向 out 打印，否则输出到控制台
println( [ out ] ,obj)             与 print 类似，但是在输出后换行
now()                              返回 System.currentTimeMillis
long(v)                            将值的类型转为 long
double(v)                          将值的类型转为 double
str(v)                             将值的类型转为 string
date_to_string(date,format)        将 Date 对象转化化特定格式的字符串,
string_to_date(source,format)      将特定格式的字符串转化为 Date 对象
string.contains(s1,s2)             判断 s1 是否包含 s2，返回 Boolean
string.length(s)                   求字符串长度,返回 Long
string.startsWith(s1,s2)           s1 是否以 s2 开始，返回 Boolean
string.endsWith(s1,s2)             s1 是否以 s2 结尾,返回 Boolean
string.substring(s,begin [ ,end ] )
                                                                                                                         截取字符串 s，从 begin 到 end，end 如果忽略的话，将从 begin 到结尾，与
java.util.String.substring         一样。
string.indexOf(s1,s2)              java 中的 s1.indexOf(s2)，求 s2 在 s1 中的起始索引位置，如果不存在为-1
string.split(target,regex, [ limit ] )
                                   Java 里的 String.split 方法一致,
string.join(seq,seperator)         将集合 seq 里的元素以 seperator 为间隔连接起来形成字符串 
string.replace_first(s,regex,replacement)
                                   Java 里的 String.replaceFirst 方法，
string.replace_all(s,regex,replacement)
                                   Java 里的 String.replaceAll 方法 ，
math.abs(d)                        求 d 的绝对值
math.sqrt(d)                       求 d 的平方根
math.pow(d1,d2)                    求 d1 的 d2 次方
math.log(d)                        求 d 的自然对数
math.log10(d)                      求 d 以 10 为底的对数
math.sin(d)                        正弦函数
math.cos(d)                        余弦函数
math.tan(d)                        正切函数
map(seq,fun)                       将函数 fun 作用到集合 seq 每个元素上，返回新元素组成的集合
filter(seq,predicate)              将谓词 predicate 作用在集合的每个元素上，返回谓词为 true 的元素组成的集合
count(seq)                         返回集合大小
include(seq,element)               判断 element 是否在集合 seq 中，返回boolean 值
sort(seq)                          排序集合，仅对数组和 List 有效，返回排序后的新集合
reduce(seq,fun,init)               fun 接收两个参数，第一个是集合元素，第二个是累积的函数，本函数用于将 fun作用在集合每个元素和初始值上面，返回最终的 init 值
seq.eq(value)                      返回一个谓词，用来判断传入的参数是否跟 value 相等,用于 filter 函数，如
filter(seq,seq.eq(3))              过滤返回等于 3的元素组成的集合
seq.neq(value)                     与 seq.eq 类似，返回判断不等于的谓词
seq.gt(value)                      返回判断大于 value 的谓词
seq.ge(value)                      返回判断大于等于 value 的谓词
seq.lt(value)                      返回判断小于 value 的谓词
seq.le(value)                      返回判断小于等于 value 的谓词
seq.nil()                          返回判断是否为 nil 的谓词
seq.exists()                       返回判断不为 nil 的谓词

-----------------------------------------------------------------------------
--sql一般校验
select count(*) r_size from dmb_save_account t.account_id = '#self_val'

--表间校验规则
select
    t1.left_value, 
    t2.right_value
from
    (select A1,B1,sum(C1+D1) as left_value from T1 where A1 = $A1 and B1=$B1 group by A1,B1) t1,
    (select A2,B2,sum(C2) as right_value from T2 where A2 = $A1 and B2=$B1 group by A2,B2) t2
where
    t1.A1 = t2.A2
and t1.B1 = t2.B2
and (not( t1.left_value >= t2.right_value))

---------------------------------------------------------------------------
单元格                                    列间                                        表间
正则表达式                           java          java 
java
sql

变量替换规则
#self_val

${bank_org_id} 





select
    c.currency_desc,
    a.balance                 as balance_dtl,
    b.balance                 as balance_gl,
    b.balance - a.balance     as balance_diff
from
    (select curr_cd, sum(balance) as balance 
    from #table_name
    where flow_status_code = '05'
     and finance_org_id in (select * from dmd_bank_relation 
                            where parent_bank_org_id = #privilege_org_id
                            and bank_rela_type_cd = '00'
                            )
    group by curr_cd 
    )a,
    (select curr_cd, sum(abs(curr_dr_bal-curr_cr_bal)) as balance 
     from dw_gl_his 
     where bank_org_id in (select bank_org_id from dmd_bank_relation 
                            where parent_bank_org_id = #privilege_org_id
                            and bank_rela_type_cd = '00'
                            )
     and record_dt = (select max(biz_date) from #table_name 
                      where flow_status_code = '05'
                        and finance_org_id in (select bank_org_id from dmd_bank_relation 
                                              where parent_bank_org_id = #privilege_org_id
                                              and bank_rela_type_cd = '00'
                                              )
                      )
     and ledger_type_cd = '00'
     and prod_grp_id = #product_id
     ) b,
     dmd_currency c
where
    a.curr_cd = b.curr_cd
and a.curr_cd = c.currency_code


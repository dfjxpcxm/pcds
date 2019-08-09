package com.shuhao.clean.toolkit.log.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
@Inherited
public @interface UseLog {
	boolean isLog() default false;//是否记录日志 
	boolean isQueryLog() default false; //是否记录查询日志
}

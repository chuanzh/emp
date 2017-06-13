package cn.chuanz.util.anotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KeywordQuery {
	
	int[] value();
	
	int weight(); //权重,权重越小优先判断关键字是否符合条件
	
	boolean disable(); //是否可用
}

package supportnet.common.form;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FieldCondition {
	public Operation op() default Operation.defaultOP;
	public String field() default "";
	public double nullValue() default 0;
	
}

package supportnet.common.form;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JTField {
	/**
	 * 
	 * @return
	 */
	public JTFieldType fieldType() default JTFieldType.String;
	
	/**
	 * 中文名称
	 * @return
	 */
	public String chineseName();
	
	public int order() default 90;
	
}

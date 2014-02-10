package supportnet.common.form;

import java.lang.reflect.Method;
import java.util.Comparator;

public class ComparatorField implements Comparator {
	public int compare(Object arg0, Object arg1) {
		Method m0 = (Method) arg0;
		Method m1 = (Method) arg1;
		JTField annotation0 = m0.getAnnotation(JTField.class);
		JTField annotation1 = m1.getAnnotation(JTField.class);
		return annotation0.order() - annotation1.order();
	}
}

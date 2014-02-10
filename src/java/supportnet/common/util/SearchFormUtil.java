package supportnet.common.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import supportnet.common.exception.JTException;
import supportnet.common.form.FieldCondition;
import supportnet.common.form.Operation;
import supportnet.common.form.SearchForm;

public class SearchFormUtil {
	private static Logger logger = Logger.getLogger(SearchFormUtil.class);
	private static final String MAINTYPE_PREFIX = "me";

	/**
	 * 生成HQL中的where语句
	 * 
	 * @param form
	 *            搜索的form.
	 * @param 入参
	 *            ，传入一个没有内容的List，方法调研时会把where需要的参数加进来。
	 * @return HQL中的where语句
	 * @throws JTException
	 */
	public static String toHQLSuffix(SearchForm form, ArrayList<Object> params)
			throws JTException {
		if (form == null)
			return "";
		Method[] methods = form.getClass().getMethods();
		StringBuffer sbuff = new StringBuffer();
		boolean isFirst = true;
		for (Method method : methods) {
			SearchField filed = new SearchField();
			filed.method = method;
			filed.annotation = method.getAnnotation(FieldCondition.class);
			if (!determineFileName(filed)) {
				continue;
			}
			logger.debug(filed);
			if (!determineValue(filed, form)) {
				continue;
			}
			logger.debug(filed);
			if (!determineOP(filed)) {
				continue;
			}
			logger.debug(filed);
			appendWhereToken(sbuff, isFirst, filed);
			addParamValue(params, filed);
			isFirst = false;

		}
		if (isFirst)
			return "";
		return sbuff.toString();
	}

	private static boolean determineOP(SearchField filed) {

		if (filed.annotation != null
				&& filed.annotation.op() != Operation.defaultOP) {
			filed.op = filed.annotation.op();
		} else {
			if ("start".equals(filed.surfix)) {
				filed.op = Operation.ge;
			} else if ("end".equals(filed.surfix)) {
				filed.op = Operation.le;
			} else {
				if (filed.isArray) {
					filed.op = Operation.in;
				} else {
					filed.op = Operation.like;
				}
			}
		}
		return true;
	}

	private static boolean determineValue(SearchField filed, SearchForm form)
			throws JTException {

		Object value;
		try {
			value = filed.method.invoke(form);
		} catch (Exception e) {
			throw new JTException();
		}
		logger.debug(value);
		Class<?> returnType = filed.method.getReturnType();
		filed.isArray = returnType.isArray();
		if (filed.isArray) {
			Class<?> componentType = returnType.getComponentType();
			if (!componentType.equals(String.class)) {
				return false;
			}
			filed.arrayValue = (String[]) value;
			if (filed.arrayValue == null || filed.arrayValue.length < 1)
				return false;
			filed.singleValue = filed.arrayValue[0];
		} else {
			if (!returnType.equals(String.class)) {
				return false;
			}
			filed.arrayValue = new String[] { (String) value };
			filed.singleValue = (String) value;
		}
		if (StringUtil.isEmpty(filed.singleValue)) {
			return false;
		}
		return true;
	}

	private static void addParamValue(ArrayList<Object> params,
			SearchField field) {
		switch (field.op) {
		case like:
			params.add("%" + field.singleValue + "%");
			break;
		case like_nocase:
			params.add("%" + field.singleValue.toLowerCase() + "%");
			break;
		case startWiths:
			params.add(field.singleValue + "%");
			break;
		case endsWidth:
			params.add("%" + field.singleValue);
			break;
		case in:
			for (Object param : field.arrayValue) {
				params.add(param);
			}
			break;
		default:
			params.add(field.singleValue);
			break;
		}

	}

	private static void appendWhereToken(StringBuffer sbuff, boolean isFirst,
			SearchField field) {
		sbuff.append(isFirst ? " where " : " and ");
		boolean isCI = field.op == Operation.like_nocase;
		String name = MAINTYPE_PREFIX + "." + field.name;
		if (isCI) {
			name = "lower(" + name + ")";
			field.singleValue = field.singleValue.toLowerCase();
		}
		sbuff.append("(").append(name).append(" ").append(field.op).append(" ");
		switch (field.op) {
		case in:
			sbuff.append("(?");
			for (int i = 1; i < field.arrayValue.length; i++) {
				sbuff.append(",?");
			}
			sbuff.append(")");
		default:
			sbuff.append("?");
		}
		sbuff.append(")");
	}

	private static boolean determineFileName(SearchField field) {
		String methodName = field.method.getName();
		if (methodName.equals("getClass")) {
			return false;
		}
		if (methodName.startsWith("is")) {
			field.name = (char) (methodName.charAt(2) | 32)
					+ methodName.substring(3);
		} else if (methodName.startsWith("get")) {
			field.name = (char) (methodName.charAt(3) | 32)
					+ methodName.substring(4);
		} else {
			return false;
		}
		int idxUnderScore = field.name.indexOf('_');

		if (idxUnderScore > 0) {
			field.surfix = field.name.substring(idxUnderScore + 1);
			field.name = field.name.substring(0, idxUnderScore);
		}

		if (field.annotation != null && field.annotation.field().length() > 0) {
			field.name = field.annotation.field();
		}
		logger.debug(methodName + ":>>" + field);
		return true;
	}

	private static class SearchField {
		public Operation op;
		public Method method;
		public FieldCondition annotation;
		public String name;
		public boolean isArray;
		public String surfix;
		public String singleValue;
		public String[] arrayValue;

		@Override
		public String toString() {
			try {
				String svUTF = singleValue == null || singleValue.length() == 0 ? ""
						: new String(singleValue.getBytes(), "utf-8");
				return "SearchField [op=" + op + ", method=" + method
						+ ", annotation=" + annotation + ", name=" + name
						+ ", isArray=" + isArray + ", surfix=" + surfix
						+ ", singleValue=" + singleValue + ", singValue utf-8"
						+ svUTF + ", arrayValue=" + Arrays.toString(arrayValue)
						+ "]";
			} catch (UnsupportedEncodingException e) {
				return "!!ERROR!!";
			}
		}
	}

/*	public static void main(String[] args) throws JTException {
		KaohetongzhiSearchForm form = new KaohetongzhiSearchForm();
		form.setTitle("标");
		BaseDAO dao = new BaseDAO(DBtools.getSession());
		ArrayList<Object> params = new ArrayList<Object>();
		List list = dao.find(
				"from Kaohetongzhi me" + toHQLSuffix(form, params),
				params.toArray());
		System.out.println(list);
	}*/
}

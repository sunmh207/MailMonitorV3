package supportnet.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import supportnet.common.JITActionBase;

public class ActionUtil {

	private static Logger logger = Logger.getLogger(ActionUtil.class);

	public static Class<?> retireDomainClassNameFromAction(JITActionBase action) {
		String businessClassName = action.businessClass;
		if (action.businessClass == null) {
			String actionClassName = action.getClass().getName();
			businessClassName = actionClassName
					.replaceFirst("action", "domain");
			businessClassName = businessClassName.replaceFirst("Action", "");
		}
		try {
			return Class.forName(businessClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("找不到Business Class[" + businessClassName
					+ "]. 请检查struts中关于" + action.getClass().getName()
					+ "的相关配置，或者将Action重新命名。");
		}
	}



	
	

	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}

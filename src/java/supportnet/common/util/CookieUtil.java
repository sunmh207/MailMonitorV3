package supportnet.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class CookieUtil {

	private static Logger logger = Logger.getLogger(CookieUtil.class);

	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		logger.debug("cookie numbers: "+cookies.length);
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			logger.debug("cookie: "+cookie.getName()+":="+cookie.getValue());
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return "";
	}

	public static void setCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(-1);
		cookie.setPath(request.getContextPath());
		logger.debug("cookie[domain:" + cookie.getDomain() + ", path: "
				+ cookie.getPath() + ", name: " + name + ", value: " + value
				+ "]");

		response.addCookie(cookie);
	}
}

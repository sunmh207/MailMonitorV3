package supportnet.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	// private static final String symbols =
	// "ABCDEFGHJKLMNOPQRSTUVWXYZ012345d789abcdefghijkmnopqrstuvwx";
	private static final String symbols = "0123456789";
	private static Random random = new Random();

	public static String trim(String str) {
		if (str == null)
			return "";
		else
			return str.trim();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static String fillNull(String str) {
		if (str == null)
			return "";
		return str;
	}

	/**
	 * 将字符串进行MD5加密
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static String md5(String input) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = md.digest(input.getBytes());
		BigInteger bi = new BigInteger(digest);
		return bi.toString(16);
	}

	public static String join(String[] strArr) {
		if (strArr == null || strArr.length == 0) {
			return "";
		}
		StringBuffer sbuff = new StringBuffer(strArr[0]);
		for (int i = 1; i < strArr.length; i++) {
			sbuff.append(", ").append(strArr[i]);
		}
		return sbuff.toString();
	}

	/**
	 * 
	 * @param inStrList
	 * @param inStrDeli
	 * @return StringUtil.parseString2Array("abc,efg,hi", ",") ->
	 *         {"abc","efg","hi"}
	 */
	public static String[] parseString2Array(String inStrList, String inStrDeli) {
		String[] strRes = null;
		int iLength = 0;
		int i = 0;
		StringTokenizer strToken = new StringTokenizer(inStrList, inStrDeli);
		iLength = strToken.countTokens();
		strRes = new String[iLength];
		for (i = 0; i < iLength; i++) {
			strRes[i] = strToken.nextToken();
		}
		return strRes;
	}

	/**
	 * "true"->"1" "false"->"0" "1"->"1" "0"->"0" ""->"0" null->"0"
	 * 
	 * @param bool
	 * @return
	 */
	
	public static String randomString() {
		return randomString(6);
	}

	public static String randomString(int length) {
		char[] chars = new char[length];
		for (int i = 0; i < chars.length; i++) {
			int idx = random.nextInt(Integer.MAX_VALUE);
			chars[i] = symbols.charAt(idx % symbols.length());
		}
		return new String(chars);
	}

	public static boolean matchRegexp(String message,String regexp){
		if(message==null||regexp==null){
			return false;
		}
		Pattern p = Pattern.compile(regexp);
		Matcher m = p.matcher(message);
		
		if (m.find()) {
			return true;
		}
		return false;
	}	
	
	/**
	 * get the first string which match the regex
	 * @param str
	 * @param regex
	 * @return
	 */
	public static String firstMatch(String str, String regex){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		while (m.find()) {
			String group = m.group();
			return group;
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		// System.out.println(cutEndWithEllipsis("abcdeft",5));
		// System.out.println(StringUtil.md5("Sunr!se.123Pr0d"));
		// System.out.println(StringUtil.md5("Sunset.123$"));
		//System.out.println("abc".substring(0, 3));
		
		String v = "abc\\d+";
		System.out.println(v);
		System.out.println(StringUtil.firstMatch("abc10110abcced",v));
	}

}

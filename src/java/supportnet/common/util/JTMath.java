package supportnet.common.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class JTMath {
	public static String filterNullToZero(String str) {
		String s = "";
		if (str == null || "".equals(str)) {
			s = "0";
		} else {
			s = str.trim();
		}
		return s;
	}
	
	private static final int DEF_DIV_SCALE = 5;

	public static String add(String v1, String v2) {
		v1 = filterNullToZero(v1);
		v2 = filterNullToZero(v2);

		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);

		return b1.add(b2).toString();
	}

	public static String sub(String v1, String v2) {
		v1 = filterNullToZero(v1);
		v2 = filterNullToZero(v2);

		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);

		return b1.subtract(b2).toString();
	}

	public static String mul(String v1, String v2) {
		v1 = filterNullToZero(v1);
		v2 = filterNullToZero(v2);

		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).toString();
		
		/*String reciprocal_v2 = JTMath.div("1",v2);
		return JTMath.div(v1,reciprocal_v2);*/
	}

	public static String div(String v1, String v2) {
		v1 = filterNullToZero(v1);
		v2 = filterNullToZero(v2);

		return div(v1, v2, DEF_DIV_SCALE);
	}

	public static int compareTo(String v1, String v2) {
		v1 = filterNullToZero(v1);
		v2 = filterNullToZero(v2);
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.compareTo(b2);

	}

	public static String div(String v1, String v2, int scale) {
		v1 = filterNullToZero(v1);
		v2 = filterNullToZero(v2);

		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);

		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
	}

	public static boolean isInteger(String str) {
		if(StringUtil.isEmpty(str)) return false;
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	public static boolean isDouble(String str) {
		if(StringUtil.isEmpty(str)) return false;
		try{
			Double.parseDouble(str);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	/*
	 * BigDecimal.
	 * ROUND_CEILING: Ceiling function
                 0.333  ->   0.34 
                -0.333  ->  -0.33
		ROUND_DOWN: Round towards zero
                 0.333  ->   0.33 
                -0.333  ->  -0.33
		ROUND_FLOOR: Floor function
                 0.333  ->   0.33
                -0.333  ->  -0.34
		ROUND_HALF_UP: Round up if decimal >= .5
                 0.5  ->  1.0
                 0.4  ->  0.0
		ROUND_HALF_DOWN: Round up if decimal > .5
                 0.5  ->  0.0
                 0.6  ->  1.0
	 */
	public static String round(String v, int scale, int roundMode){
		if(v==null) return null;
		BigDecimal r = new BigDecimal(v); 
		return r.setScale(scale,roundMode).toString();
	} 
	
	
	public static void main(String[] args){
		/*String total = JTMath.mul("6", "1.2");
		System.out.print(total);*/
		System.out.println(JTMath.round("12.356", 2, BigDecimal.ROUND_FLOOR));
		
		
		BigDecimal a = new BigDecimal("2.5"); // digit left of 5 is even, so round down
		BigDecimal b = new BigDecimal("1.5"); // digit left of 5 is odd, so round up
		// a.setScale(0, BigDecimal.ROUND_HALF_EVEN).toString(); // => 2
		// b.setScale(0, BigDecimal.ROUND_HALF_EVEN).toString(); // => 2
		 System.out.println(a.setScale(0, BigDecimal.ROUND_HALF_EVEN).toString());
		 System.out.println(b.setScale(0, BigDecimal.ROUND_HALF_EVEN).toString());
	}
}

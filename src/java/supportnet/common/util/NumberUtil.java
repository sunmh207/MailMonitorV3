package supportnet.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtil {
	private static final double E = 0.0001;

	public static boolean eq(double v1, double v2) {
		return v1 - v2 < E && v1 - v2 > -E;
	}

	/** "零","壹","贰","叁","肆","伍","陆","柒","捌","玖" */
	//private final static String[] nums = new String[] { "\u96f6", "\u58f9", "\u8d30", "\u53c1", "\u8086", "\u4f0d", "\u9646", "\u67d2", "\u634c", "\u7396" };
	
	/** "零","一","二","三","四","五","六","七","八","九" */
	private final static String[] nums = new String[] { "\u96f6", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d", "\u4e03", "\u516b", "\u4e5d" };

	/** "拾","佰","仟","万","亿" */
	//private final static String[] units = new String[] { "\u62fe", "\u4f70", "\u4edf", "\u4e07", "\u4ebf" };
	/** "十","百","千","万","亿" */
	private final static String[] units = new String[] { "\u5341", "\u767e", "\u5343", "\u4e07", "\u4ebf" };

	private final static NumberFormat numberFormat = new DecimalFormat("#,####.#");

	public static String convertToCNNumber(double number) {
		if (number > Double.MAX_VALUE || number < 0.0) {
			return null;
		}

		String srcNum = numberFormat.format(number); // 按个数转换,如"21,1234,4567,5487.4543544"

		String prefixNum = srcNum;
		if (srcNum.indexOf(".") != -1) {
			prefixNum = srcNum.substring(0, srcNum.indexOf(".")); // 小数点前
		}

		StringBuffer result = new StringBuffer(0); // 用于保存结果

		String[] numPices = prefixNum.split(","); // 4个数字一组
		for (int i = 0; i < numPices.length; i++) // 遍历每个组
		{
			for (int j = 0; j < numPices[i].length(); j++) // 遍历组中的每个数字
			{
				int k = Integer.parseInt(String.valueOf(numPices[i].charAt(j)));
				int len = numPices[i].length();
				result.append(nums[k]); // 变成汉字
				result.append(len - 2 - j >= 0 && k > 0 ? units[len - 2 - j] : ""); // 添加仟佰拾
				result.append(j != len - 1 ? "" : ((i + numPices.length) % 2 == 0 ? (i == numPices.length - 1 ? "" : units[3])
						: i != numPices.length - 1 ? units[4] : "")); // 添加亿万
			}
		}

		String resutlStr = result.toString();

		resutlStr = resutlStr.replaceAll(nums[0] + "{2,}", nums[0]); // "零零" to
																		// "零"
		resutlStr = resutlStr.replaceAll(nums[0] + units[3] + "{1}", units[3]); // "零万"
																				// to
																				// "万"
		resutlStr = resutlStr.replaceAll(nums[0] + units[4] + "{1}", units[4]); // "零亿"
																				// to
																				// "亿"
		resutlStr = resutlStr.replaceAll(units[4] + units[3] + "{1}", units[4]); // "亿万"
																					// to
																					// "亿零"
		if (resutlStr.length()>1 && resutlStr.lastIndexOf(nums[0]) == resutlStr.length() - 1) { // 去掉最后的"零",如果只是一个零则不去掉
			resutlStr = resutlStr.substring(0, resutlStr.length() - 1);
		}

		return resutlStr;
	}

	public static void main(String[] args) {
		String a = NumberUtil.convertToCNNumber(123456);
		System.out.println(a);

	}

	/*public static void main(String[] args) {
		System.out.println(eq(1, 1));
		System.out.println(eq(1, 1.1));
		System.out.println(eq(0, 0));
		System.out.println(eq(12, 12));
	}*/
}

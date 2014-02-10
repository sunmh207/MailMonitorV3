package supportnet.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import supportnet.common.exception.JTException;

public class DateUtil {
	private static SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat defaultTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	{
		defaultDateFormat.setTimeZone(TimeZone.getTimeZone("GMT 0:00"));
		defaultTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT 0:00"));
	}
	public static final long MILISECONDS_ONE_DAY = 24 * 60 * 60 * 1000;

	public static String getCurrentTime(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new Date()).toString();
	}

	public static String getCurrentDate() {
		return getCurrentTime("yyyy-MM-dd");
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 检查一个定时发送的短信是否需要在今天发送。
	 * 
	 * @param sSendTime
	 * @param schedule
	 * @return 字符串，如果不是今天发送的，则返回null；如果是今天发送的，则返回代表时间间隔的字符串（比如10天，1星期等）。
	 * @throws JTException
	 */
	public static String shallSendToday(String sSendTime, String schedule) throws JTException {
		try {
			Date sendDate = defaultDateFormat.parse(sSendTime);
			Schedule parseSchedule = parseSchedule(schedule);
			boolean shallSendToday = shallSendToday(sendDate, parseSchedule);
			return shallSendToday ? parseSchedule.realAmount + parseSchedule.displayUnit : null;
		} catch (ParseException e) {
			throw new JTException("不可识别的日期格式：" + sSendTime, DateUtil.class);
		}
	}

	@SuppressWarnings("deprecation")
	private static boolean shallSendToday(Date sendDate, Schedule schedule) {

		int diffUnit;
		if (Schedule.CALC_UNIT_DAY == schedule.calUnit) {
			Calendar calToday = Calendar.getInstance(TimeZone.getTimeZone("GMT 0:00"));
			Calendar calSendDate = Calendar.getInstance(TimeZone.getTimeZone("GMT 0:00"));
			calSendDate.set(sendDate.getYear() + 1900, sendDate.getMonth(), sendDate.getDate());
			long daysToday = calToday.getTimeInMillis() / MILISECONDS_ONE_DAY;
			long daysSendDate = calSendDate.getTimeInMillis() / MILISECONDS_ONE_DAY;
			diffUnit = (int) (daysToday - daysSendDate);
		} else {
			Date today = new Date();
			if (today.getDate() != sendDate.getDate()) {
				return false;
			}
			int amoutSendDate = sendDate.getYear() * 12 + sendDate.getMonth();
			int amoutToday = today.getYear() * 12 + today.getMonth();
			diffUnit = amoutSendDate - amoutToday;
		}
		schedule.realAmount = diffUnit / schedule.calAmout * schedule.amount;
		schedule.realAmount = schedule.realAmount > 0 ? schedule.realAmount : -schedule.realAmount;
		boolean onSchedule = diffUnit % schedule.calAmout == 0;
		return onSchedule;
	}

	private static Schedule parseSchedule(String sSchedule) throws JTException {
		if (!sSchedule.matches("每?[0-9]*([年月日天周]|星期)")) {
			throw new JTException("时间间隔格式错误：" + sSchedule, DateUtil.class);
		}
		String sAmount = sSchedule.replaceAll("[^0-9]", "");
		int amount = sAmount.length() == 0 ? 1 : Integer.parseInt(sAmount);
		Schedule schedule = new Schedule();
		schedule.amount = amount;

		if (sSchedule.endsWith("年")) {
			schedule.displayUnit = "周年";
			schedule.calAmout = 12 * amount;
			schedule.calUnit = Schedule.CALC_UNIT_MONTH;
		} else if (sSchedule.endsWith("月")) {
			schedule.displayUnit = "个月";
			schedule.calAmout = amount;
			schedule.calUnit = Schedule.CALC_UNIT_MONTH;
		} else if (sSchedule.endsWith("天")) {
			schedule.displayUnit = "天";
			schedule.calAmout = amount;
			schedule.calUnit = Schedule.CALC_UNIT_DAY;
		} else if (sSchedule.endsWith("日")) {
			schedule.displayUnit = "天";
			schedule.calAmout = amount;
			schedule.calUnit = Schedule.CALC_UNIT_DAY;
		} else if (sSchedule.endsWith("周")) {
			schedule.displayUnit = "个星期";
			schedule.calAmout = 7 * amount;
			schedule.calUnit = Schedule.CALC_UNIT_DAY;
		} else if (sSchedule.endsWith("星期")) {
			schedule.displayUnit = "个星期";
			schedule.calAmout = 7 * amount;
			schedule.calUnit = Schedule.CALC_UNIT_DAY;
		}
		return schedule;
	}

	private static class Schedule {
		public static final int CALC_UNIT_MONTH = 1;
		public static final int CALC_UNIT_DAY = 2;
		public String displayUnit;
		public int calUnit;
		public int calAmout;
		public int amount;
		public int realAmount;
	}

	/**
	 * 
	 * @param dtStr
	 * @param format
	 * @return (XXXX-XX-XX XX:XX:XX, yyyy-MM-dd HH:mm:ss)->XXXX年XX月XX日XX点XX分XX秒
	 *         (XX:XX, HH:mm)->XX点XX分
	 */
	public static String date2cnString(String dtStr, String format) throws Exception {
		String cnString = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date dt = dateFormat.parse(dtStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		if (format.indexOf("yyyy") >= 0) {
			cnString = cnString + (NumberUtil.convertToCNNumber(cal.get(Calendar.YEAR)) + "年");
		}
		if (format.indexOf("MM") >= 0) {
			cnString = cnString + (NumberUtil.convertToCNNumber(cal.get(Calendar.MONTH)) + "月");
		}
		if (format.indexOf("dd") >= 0) {
			cnString = cnString + (NumberUtil.convertToCNNumber(cal.get(Calendar.DAY_OF_MONTH)) + "日");
		}
		if (format.indexOf("HH") >= 0) {
			cnString = cnString + (NumberUtil.convertToCNNumber(cal.get(Calendar.HOUR_OF_DAY)) + "点");
		}
		if (format.indexOf("mm") >= 0) {
			cnString = cnString + (NumberUtil.convertToCNNumber(cal.get(Calendar.MINUTE)) + "分");
		}
		if (format.indexOf("ss") >= 0) {
			cnString = cnString + (NumberUtil.convertToCNNumber(cal.get(Calendar.SECOND)) + "秒");
		}
		return cnString;
	}

	/**
	 * 在一个日期上加上一定的时间（年，月，日，时，分，秒） 比如：
	 * datePlus("2011-02-02","yyyy-MM-dd",Calendar.YEAR,
	 * 100)表示在"2011-02-02"基础上加100年，即2111-02-02
	 * 
	 * @param date
	 *            ： 基础日期
	 * @param format
	 *            日期格式
	 * @param calendarField
	 *            ： Calendar字段，比如Calendar.YEAR等
	 * @param value
	 *            ,增加的数量
	 * @return
	 * @throws ParseException
	 */
	public static String datePlus(String date, String format, int calendarField, int value) throws ParseException {
		System.out.println(date);
		Calendar c = toCalendar(date, format);
		c.set(calendarField, c.get(calendarField) + value);
		SimpleDateFormat df = new SimpleDateFormat(format);
		String newdate= df.format(c.getTime());
		System.out.println(newdate);
		return newdate;
	}

	public static Calendar toCalendar(String date, String format) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date dt = df.parse(date);
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		return c;
	}

	public static String convertDateFormat(String date, String fromFormat, String toFormat) throws ParseException {
		Calendar c = toCalendar(date, fromFormat);
		SimpleDateFormat df = new SimpleDateFormat(toFormat);
		return df.format(c.getTime());
	}

	public static int getCalendarFieldValue(String date, String format, int calendarField) throws ParseException {
		Calendar c = toCalendar(date, format);
		return c.get(calendarField);
	}

	/**
	 * @param d1
	 * @param format1
	 * @param d2
	 * @param format2
	 * @return d1-d2
	 */
	public static int dateDayMinus(String d1, String format1, String d2, String format2) {
		int ret = 0;
		try {
			Calendar c1 = toCalendar(d1, format1);
			Calendar c2 = toCalendar(d2, format2);
			long m1 = c1.getTimeInMillis();
			long m2 = c2.getTimeInMillis();
			ret = (int) (m1 - m2) / (1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
	}
	public static long differDays(Date date1, Date date2) 	{ 
	    //return date1.getTime() / (24*60*60*1000) - date2.getTime() / (24*60*60*1000); 
	    return date2.getTime() / 86400000 - date1.getTime() / 86400000;  //用立即数，减少乘法计算的开销
	} 
	public static long differSeconds(Date date1, Date date2) 	{ 
		return date2.getTime() / 1000 - date1.getTime() / 1000;  //
	} 
	public static int differMonth(String date1, String format1, String date2,String format2) throws ParseException{ 
		Calendar c1 = DateUtil.toCalendar(date1,format1);
		Calendar c2 = DateUtil.toCalendar(date2,format2);
		int y1=c1.get(Calendar.YEAR);
		int m1=c1.get(Calendar.MONTH);
		int y2=c2.get(Calendar.YEAR);
		int m2=c2.get(Calendar.MONTH);
		return y1*12+m1 - y2*12-m2;
	} 
	/**
	 * 
	 * @param month
	 * @return  2013-08 --> [2013-07-26][2013-08-25]
	 */
	public static String[] month2evadate(String year, String month){
		String[] startend=new String[2];
		if(year==null||StringUtil.isEmpty(year)||year.equals("%")){
			startend[0]=null;
			startend[1]=null;
			return startend;
		}
		
		int year_num=Integer.parseInt(year);
		
		if(month==null||StringUtil.isEmpty(month)||month.equals("%")){
			startend[0]=(year_num-1)+"-12-26";
			startend[1]=year_num+"-12-25";
			return startend;
		}
		
		int month_num=Integer.parseInt(month);
		Calendar start_c = Calendar.getInstance();
		start_c.set(Calendar.YEAR,year_num);
		start_c.set(Calendar.MONTH, month_num-2);
		start_c.set(Calendar.DAY_OF_MONTH, 26);
		
		Calendar end_c = Calendar.getInstance();
		end_c.set(Calendar.YEAR,year_num);
		end_c.set(Calendar.MONTH, month_num-1);
		end_c.set(Calendar.DAY_OF_MONTH, 25);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		startend[0]=df.format(start_c.getTime());
		startend[1]=df.format(end_c.getTime());
		return startend;
	}
	public static void main(String[] args) throws Exception {
		/*
		 * System.out.println(DateUtil.date2cnString("11:30", "HH:mm"));
		 * System.out.println(DateUtil.date2cnString("11:30", "mm:ss"));
		 * System.out.println(DateUtil.date2cnString("2011-02-03 11:30:00",
		 * "yyyy-MM-dd HH:mm:ss"));
		 * System.out.println(NumberUtil.convertToCNNumber(0));
		 */

		// System.out.println(datePlus("2011-02-02","yyyy-MM-dd",Calendar.YEAR,
		// 100));
		/*Calendar c = Calendar.getInstance();
		System.out.println(c.get(Calendar.DAY_OF_WEEK));
		System.out.println(c.get(Calendar.DAY_OF_WEEK_IN_MONTH));
		System.out.println(c.get(Calendar.MONTH));
		System.out.println(c.get(Calendar.DAY_OF_MONTH));*/
		/*String month="2013-06";
		String s = DateUtil.datePlus(month, "yyyy-MM", Calendar.MONTH, -1);*/
		//System.out.println("%".equals("%"));
		/*String[] month = month2evadate("2019","9");
		System.out.println(month[0]+" ~ "+month[1]);*/
		System.out.println(differMonth("2013-01","yyyy-MM","2012-5","yyyy-MM"));
		
	}
}

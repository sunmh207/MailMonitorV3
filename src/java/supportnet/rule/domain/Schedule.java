package supportnet.rule.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import supportnet.common.util.StringUtil;

public class Schedule {
		
	public static final String[] TIMEZONE_IDS=new String[]{"Asia/Shanghai","America/New_York","America/Chicago","America/Denver","America/Los_Angeles","IST"};
	
	private String id;
	private String name;
	private String desc;
	private String daysOfWeek;// Sun:1, Mon:2, Tue3, Wed:4, Thursday:5, Fri:6,
								// Sat7
	private String timeRange;// 08:00:00-11:00:00||13:00:00-17:30:00;
								// timeRange=null/"" is the same to
								// timeRange="00:00:00-24:00:00"
	private String timeZoneID; // see TimeZone.getAvailableIDs() for detail

	public boolean match(Date date) {
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneID);

		Calendar c = Calendar.getInstance(timeZone);
		c.setTime(date);

		// Check Day of Week
		boolean availalbe_daysofweek = false;
		int dayofweek = c.get(Calendar.DAY_OF_WEEK);
		if (!StringUtil.isEmpty(daysOfWeek) && daysOfWeek.indexOf(String.valueOf(dayofweek)) >= 0) {
			availalbe_daysofweek = true;
		}

		// Check Time
		boolean availalbe_time = false;
		if (StringUtil.isEmpty(timeRange)) {
			availalbe_time = true;
		} else {
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			dateFormat.setTimeZone(timeZone);
			String formatTime = dateFormat.format(c.getTime());
			String[] timeRangeArray = timeRange.split("\\|");
			for (String singleRange : timeRangeArray) {
				String[] startEnd = singleRange.split("-");
				if (startEnd.length == 2) {
					if (formatTime.compareTo(startEnd[0]) >= 0 && formatTime.compareTo(startEnd[1]) <= 0) {
						availalbe_time = true;
						break;
					}
				}
			}
		}

		return availalbe_daysofweek && availalbe_time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(String daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	public String getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}

	public String getTimeZoneID() {
		return timeZoneID;
	}

	public void setTimeZoneID(String timeZoneID) {
		this.timeZoneID = timeZoneID;
	}

	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof Schedule) ) return false;
        final Schedule schedule = (Schedule) other;
        if (schedule.getId()==null) return false;
        if (!schedule.getId().equals( getId() ) ) return false;
        return true;
    }

    public int hashCode() {
    	int result;
    	if(id!=null){
    		result = id.hashCode();
    	}else{
    		result=this.hashCode();
    	}
        return result;
    }
	
	public static void main(String[] args) {
		// System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
		
		/*for(String s:TimeZone.getAvailableIDs() ){
			System.out.println(s);
		}*/
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
		String formatTime = dateFormat.format(Calendar.getInstance().getTime());
		System.out.println(formatTime);
	}

}

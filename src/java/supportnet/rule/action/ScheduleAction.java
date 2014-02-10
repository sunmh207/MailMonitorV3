package supportnet.rule.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import supportnet.common.JITActionBase;
import supportnet.common.exception.JTException;
import supportnet.common.service.BaseService;
import supportnet.common.util.StringUtil;
import supportnet.mail.domain.EmailAccount;
import supportnet.rule.domain.Schedule;

import com.opensymphony.xwork2.Preparable;

public class ScheduleAction extends JITActionBase implements Preparable  {
	protected static BaseService service;
	protected Schedule schedule;
	protected String sunday;
	protected String monday;
	protected String tuesday;
	protected String wednesday;
	protected String thursday;
	protected String friday;
	protected String saturday;
	
	private String[] timeZoneIdArray = Schedule.TIMEZONE_IDS;
	public void prepare() throws JTException {
		if (service == null) {
			service = new BaseService();
		}
		if (schedule != null && schedule.getId() != null) {
			schedule = (Schedule) service.findBoById(Schedule.class, schedule.getId());
		}
		if (schedule != null) {
		String daysofweek = schedule.getDaysOfWeek();
		if (!StringUtil.isEmpty(daysofweek)) {
			if (daysofweek.indexOf(String.valueOf(Calendar.SUNDAY)) >= 0) {
				sunday = "true";
			}
			if (daysofweek.indexOf(String.valueOf(Calendar.MONDAY)) >= 0) {
				monday = "true";
			}
			if (daysofweek.indexOf(String.valueOf(Calendar.TUESDAY)) >= 0) {
				tuesday = "true";
			}
			if (daysofweek.indexOf(String.valueOf(Calendar.WEDNESDAY)) >= 0) {
				wednesday = "true";
			}
			if (daysofweek.indexOf(String.valueOf(Calendar.THURSDAY)) >= 0) {
				thursday = "true";
			}
			if (daysofweek.indexOf(String.valueOf(Calendar.FRIDAY)) >= 0) {
				friday = "true";
			}
			if (daysofweek.indexOf(String.valueOf(Calendar.SATURDAY)) >= 0) {
				saturday = "true";
			}
		}
		}
	}


	public String input() {
		return INPUT;
	}

	
	public String delete() throws JTException {
		service.deleteBo(Schedule.class, schedule.getId());
		return SUCCESS;
	}

	public String save() throws Exception {
		try {
			String daysOfWeek = sunday + "," + monday + "," + tuesday + "," + wednesday + "," + thursday + "," + friday + "," + saturday;
			schedule.setDaysOfWeek(daysOfWeek);

			// new
			if (schedule.getId() == null || "".equals(schedule.getId())) {
				service.createBo(schedule);
				// modify
			} else {
				service.updateBo(schedule);
			}
		} catch (Exception e) {
			this.addActionError(e.getMessage());
			return INPUT;
		}
		return SUCCESS;
	}

	public String getListHQL(ArrayList<Object> params) throws JTException {
		String hql = "from Schedule me order by me.timeRange";
		return hql;
	}


	public Schedule getSchedule() {
		return schedule;
	}


	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}


	public String getSunday() {
		return sunday;
	}


	public void setSunday(String sunday) {
		this.sunday = sunday;
	}


	public String getMonday() {
		return monday;
	}


	public void setMonday(String monday) {
		this.monday = monday;
	}


	public String getTuesday() {
		return tuesday;
	}


	public void setTuesday(String tuesday) {
		this.tuesday = tuesday;
	}


	public String getWednesday() {
		return wednesday;
	}


	public void setWednesday(String wednesday) {
		this.wednesday = wednesday;
	}


	public String getThursday() {
		return thursday;
	}


	public void setThursday(String thursday) {
		this.thursday = thursday;
	}


	public String getFriday() {
		return friday;
	}


	public void setFriday(String friday) {
		this.friday = friday;
	}


	public String getSaturday() {
		return saturday;
	}


	public void setSaturday(String saturday) {
		this.saturday = saturday;
	}


	public String[] getTimeZoneIdArray() {
		return timeZoneIdArray;
	}


	public void setTimeZoneIdArray(String[] timeZoneIdArray) {
		this.timeZoneIdArray = timeZoneIdArray;
	}
	
}

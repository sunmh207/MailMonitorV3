package supportnet.rule.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import microsoft.exchange.webservices.data.EmailMessage;
import supportnet.common.util.InfoHandler;

public class NoMailOnTimeRule extends BaseRule {
	private int skew;//minutes
	private String expectedTime;//e.g. 10:00:00; The email received in [expectedTime-skew ~ expectedTime+skew] is acceptable
	private Set<EmailMessage> receivedMails = new HashSet<EmailMessage>();
	private TimerTask alertTask;
	
	public String getType(){
		return "NoMailOnTimeRule";
	}
	public void init() {		
		Date nextTriggerTime =getNextTriggerTime();
		startCheckingTask(nextTriggerTime);
	}
	/**
	 * The email received in [expectedTime-skew ~ expectedTime+skew] is acceptable
	 * @param date
	 * @return
	 */
	private boolean inAcceptableTimeToday(Date date){
		Date expectedDate =null;
		try{
			 expectedDate = new SimpleDateFormat("HH:mm:ss").parse(expectedTime);
		}catch(ParseException pe){
			InfoHandler.error("inAcceptableTimeToday failed. expectedTime="+expectedTime, pe);
		}
		Calendar tmpCal = Calendar.getInstance();
		tmpCal.setTime(expectedDate);
		
		Calendar startCal = Calendar.getInstance();
		startCal.set(Calendar.HOUR_OF_DAY, tmpCal.get(Calendar.HOUR_OF_DAY));
		startCal.set(Calendar.MINUTE, tmpCal.get(Calendar.MINUTE)-skew);
		startCal.set(Calendar.SECOND, tmpCal.get(Calendar.SECOND));
		
		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.HOUR_OF_DAY, tmpCal.get(Calendar.HOUR_OF_DAY));
		endCal.set(Calendar.MINUTE, tmpCal.get(Calendar.MINUTE)+skew);
		endCal.set(Calendar.SECOND, tmpCal.get(Calendar.SECOND));
		
		SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		InfoHandler.info("date:"+fmt.format(date)+",startDate:"+fmt.format(startCal.getTime())+",endDate:"+fmt.format(endCal.getTime()));
		
		if(date.after(startCal.getTime())&&date.before(endCal.getTime())){
			return true;
		}else{
			return false;
		}
	}
	/*
	 * return the next trigger time. 
	 * i.e. If current time is 10:00:00, and the expectedTime=06:00:00, then return 10:00:00 of next day.
	 * i.e. If current time is 10:00:00, and the expectedTime=13:00:00, then return 13:00:00 of current day.
	 */
	private Date getNextTriggerTime() {
		Calendar cal = Calendar.getInstance();

		try {
			Date expectedDate = new SimpleDateFormat("HH:mm:ss").parse(expectedTime);
			Calendar tmpCal = Calendar.getInstance();
			tmpCal.setTime(expectedDate);

			cal.set(Calendar.HOUR_OF_DAY, tmpCal.get(Calendar.HOUR_OF_DAY));
			cal.set(Calendar.MINUTE, tmpCal.get(Calendar.MINUTE)+skew);
			cal.set(Calendar.SECOND, tmpCal.get(Calendar.SECOND));

			Calendar currentCal = Calendar.getInstance();
			if (cal.before(currentCal)) {
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+1);
			}
		} catch (ParseException pe) {
			InfoHandler.error("getNextTriggerTime failed. expectedTime="+expectedTime, pe);
		}
		return cal.getTime();
	}
	/**
	 * check if the mail match the rule for the first mail
	 * 
	 * @param mail
	 * @return. if mail match this rule, return true; otherwise return false;
	 */

	@Override
	public boolean execute(EmailMessage email) {
		try{
			//if (match(email)&&inAcceptableTimeToday(email.getDateTimeReceived())) {
			if (match(email)&&inAcceptableTimeToday(Calendar.getInstance().getTime())) {
				receivedMails.add(email);
			}
		}catch(Exception e){
			InfoHandler.error("PairMailRule match1stMailRule error",e);
		}
		return false;
	}

	private void startCheckingTask(Date nextTriggerTime) {
		if (alertTask != null) {
			if(alertTask.scheduledExecutionTime()!=nextTriggerTime.getTime()){
				alertTask.cancel();
				InfoHandler.info("["+this.getName()+"] the nextTriggerTime is not the same, cancel the old one, and created a new one.");
				new Timer().scheduleAtFixedRate(new CheckingTask(this), nextTriggerTime, 24 * 60 * 60 * 1000);// repeat daily
				InfoHandler.info("timer.schedule(" + this.getClass().getSimpleName() + ".alertTask, " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(nextTriggerTime) + ")");
			}else{
				InfoHandler.info("["+this.getName()+"] the nextTriggerTime is the same, do nothing.");
			}
		}else{
			new Timer().scheduleAtFixedRate(new CheckingTask(this), nextTriggerTime, 24 * 60 * 60 * 1000);// repeat daily
			InfoHandler.info("timer.schedule(" + this.getClass().getSimpleName() + ".alertTask, " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(nextTriggerTime) + ")");
		}
	}


	public static void main(String[] args) throws Exception{
		Date expectedDate = new SimpleDateFormat("HH:mm:ss").parse("23:20:00");
		String s= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expectedDate);
		System.out.println(s);
		Calendar tmpCal =Calendar.getInstance();
		tmpCal.setTime(expectedDate);
		
		Calendar cal =Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, tmpCal.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, tmpCal.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, tmpCal.get(Calendar.SECOND));
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
		
	/*	Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TestTask() , cal.getTime(), 24 * 60 * 60 * 1000);*/
	}
	public int getSkew() {
		return skew;
	}
	public void setSkew(int skew) {
		this.skew = skew;
	}
	public String getExpectedTime() {
		return expectedTime;
	}
	public void setExpectedTime(String expectedTime) {
		this.expectedTime = expectedTime;
	}
	public Set<EmailMessage> getReceivedMails() {
		return receivedMails;
	}
	public void setReceivedMails(Set<EmailMessage> receivedMails) {
		this.receivedMails = receivedMails;
	}
	public TimerTask getAlertTask() {
		return alertTask;
	}
	public void setAlertTask(TimerTask alertTask) {
		this.alertTask = alertTask;
	}
	
}
/*class TestTask extends TimerTask {
	public void run() {
		System.out.println("TestTask:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
	}
}*/

class CheckingTask extends TimerTask {
	private NoMailOnTimeRule rule;
	public CheckingTask(NoMailOnTimeRule r) {
		this.rule = r;
	}

	public void run() {
		try {
			if(rule.matchSchedule(Calendar.getInstance().getTime())){// if match schedule, then trigger the action.
				if(rule.getReceivedMails().isEmpty()){
					rule.doAction(null);
				}else{
					rule.getReceivedMails().clear();
					InfoHandler.info("["+rule.getName()+"]===ReceivedMails Cleared");
				}
			}
		} catch (Exception e) {
			InfoHandler.error(this.getClass() + " encountered an error.", e);
		}
	}
}

package supportnet.rule.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import microsoft.exchange.webservices.data.EmailMessage;
import supportnet.common.Constants;
import supportnet.common.util.DateUtil;
import supportnet.common.util.InfoHandler;

public class NoMailRule extends BaseRule {
	private int period;
	private String startTime;
	private TimerTask alertTask;
	
	public String getType(){
		return "NoMailRule";
	}
	public void init() {		
		restartCountDown();
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
			if (match(email)) {
				restartCountDown();
			}
		}catch(Exception e){
			InfoHandler.error("PairMailRule match1stMailRule error",e);
		}
		return false;
	}

	private void restartCountDown() {
		if(alertTask!=null){
			alertTask.cancel();
		}
		startTime = DateUtil.getCurrentTime();
		// check time = startTime+ period
		try {
			Calendar c = DateUtil.toCalendar(startTime, Constants.DATETIME_FORMAT);
			c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + period);
			Timer timer = new Timer();
			alertTask = new NoMailAlertTask(this);
			timer.schedule(alertTask, c.getTime());
			InfoHandler.info("timer.schedule(NoMail.alertTask, "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime())+");");
		} catch (ParseException pe) {
			InfoHandler.error("startTime:" + startTime + " is not a valide date format", pe);
		}
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public TimerTask getCountingDownTask() {
		return alertTask;
	}

	public void setCountingDownTask(TimerTask countingDownTask) {
		this.alertTask = countingDownTask;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
}

class NoMailAlertTask extends TimerTask {
	private NoMailRule rule;
	public NoMailAlertTask(NoMailRule r) {
		this.rule = r;
	}

	public void run() {
		try {
			if(rule.matchSchedule(Calendar.getInstance().getTime())){// if match schedule, then trigger the action.
				rule.doAction(null);
			}
		} catch (Exception e) {
			InfoHandler.error(this.getClass() + " encountered an error.", e);
		}
	}
}

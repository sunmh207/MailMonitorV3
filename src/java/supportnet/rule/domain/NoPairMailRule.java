package supportnet.rule.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ServiceLocalException;
import supportnet.common.Constants;
import supportnet.common.util.DateUtil;
import supportnet.common.util.InfoHandler;
import supportnet.common.util.StringUtil;

public class NoPairMailRule extends BaseRule {
	private int period;
	private String firstMailComeTime;

	protected List<RuleCondition2> conditions2 = new ArrayList<RuleCondition2>();

	private TimerTask countingDownTask;

	public String getType(){
		return "NoPairMailRule";
	}
	public void init() {}
	/**
	 * check if the mail match the rule for the first mail
	 * 
	 * @param mail
	 * @return. if mail match this rule, return true; otherwise return false;
	 */
	private boolean match1stMailRule(EmailMessage email) {
		boolean ret = false;
		try {
			ret = this.match(email);
		} catch (Exception e) {
			InfoHandler.error("PairMailRule match1stMailRule error",e); 
		}

		return ret;
	}
	
	private boolean match2ndMailRule(EmailMessage email){
		try{
			//return matchConditions2(email)&&!matchExceptions2(email) && this.schedule.match(email.getDateTimeReceived());
			return matchConditions2(email) && this.schedule.match(email.getDateTimeReceived());
		}catch(ServiceLocalException sle){
			InfoHandler.error("getDateTimeReceived encountered error.", sle);
		}
		return false;
	}
	
	private boolean matchConditions2(EmailMessage email){
		for(RuleCondition2 c:conditions2){
			if(c!=null&&c.match(email)){
				return true;
			}
		}
		return false;
	}
	/*private boolean matchExceptions2(EmailMessage email){ 
		for(RuleException2 e:exceptions2){
			if(e.match(email)){
				return true;
			}
		}
		return false;
	}*/
	

	@Override
	public boolean execute(EmailMessage email) {

		if (StringUtil.isEmpty(firstMailComeTime)) {// this is the first mail
			InfoHandler.debug("firstMailComeTime="+firstMailComeTime);
			if (match1stMailRule(email)) {
				InfoHandler.debug("match1stMailRule=true");
				try {
					firstMailComeTime =new SimpleDateFormat(Constants.DATETIME_FORMAT).format(email.getDateTimeReceived());
				} catch (ServiceLocalException e) {
					InfoHandler.error("item.getDateTimeReceived() encountered errors.", e);
				}
				startCountDown();
			}
		} else {
			if (match2ndMailRule(email)) {
				stopCountDown();
			}
		}
		return false;
	}

	private void startCountDown() {
		if (period <= 0) {
			return;
		}
		// check time = firstMailComeTime + periodMinute
		try {
			Calendar c = DateUtil.toCalendar(firstMailComeTime, Constants.DATETIME_FORMAT);
			c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + period);
			Timer timer = new Timer();
			countingDownTask = new PairMailCountingDownTask(this);
			timer.schedule(countingDownTask, c.getTime());
			InfoHandler.info("timer.schedule(countingDownTask, "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime())+");");
		} catch (ParseException pe) {
			InfoHandler.error("firstMailComeTime:" + firstMailComeTime + " is not a valide date format", pe);
		}
	}

	private void stopCountDown() {
		firstMailComeTime = null;
		if (countingDownTask != null) {
			countingDownTask.cancel();
			InfoHandler.info("timer.cancelled);");
		}
	}

	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getFirstMailComeTime() {
		return firstMailComeTime;
	}

	public void setFirstMailComeTime(String firstMailComeTime) {
		this.firstMailComeTime = firstMailComeTime;
	}

	

	public TimerTask getCountingDownTask() {
		return countingDownTask;
	}

	public void setCountingDownTask(TimerTask countingDownTask) {
		this.countingDownTask = countingDownTask;
	}

	public static void main(String[] args) throws Exception {
		NoPairMailRule r = new NoPairMailRule();
		r.setFirstMailComeTime("2013-08-31 21:09:00");

		Calendar c = DateUtil.toCalendar(r.getFirstMailComeTime(), Constants.DATETIME_FORMAT);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + Integer.valueOf("3"));
		Timer timer = new Timer();

		timer.schedule(new PairMailCountingDownTask(r), c.getTime());

		Thread.sleep(10000);
		timer.cancel();
	}
	public List<RuleCondition2> getConditions2() {
		return conditions2;
	}
	public void setConditions2(List<RuleCondition2> conditions2) {
		this.conditions2 = conditions2;
	}
	/*public List<RuleException2> getExceptions2() {
		return exceptions2;
	}
	public void setExceptions2(List<RuleException2> exceptions2) {
		this.exceptions2 = exceptions2;
	}
	*/
}

class PairMailCountingDownTask extends TimerTask { 
	private NoPairMailRule rule;
	public PairMailCountingDownTask(NoPairMailRule r) {
		this.rule = r;
	}

	public void run() {
		if (!StringUtil.isEmpty(rule.getFirstMailComeTime())) {
			try {
				rule.doAction(null);
			} catch (Exception e) {
				InfoHandler.error(this.getClass() + "Error happend when send email", e);
			}
		} else {
			this.cancel();
		}
	}	
}

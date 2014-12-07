package supportnet.rule.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ServiceLocalException;
import supportnet.common.util.InfoHandler;
/**
 * No Pair Mail Rule: If the first mail come, but the second mail doesn't come in a period of time, the rule will be triggered.
 * @author stanley.sun
 *
 */
public class NoPairMailRule extends BaseRule {
	private int period;
	//private String firstMailComeTime;
	private String firstMailMessageId;

	protected List<RuleCondition2> conditions2 = new ArrayList<RuleCondition2>();

	//private TimerTask countingDownTask;
	private Map<String,TimerTask> countingDownTaskMap= new HashMap<String,TimerTask>();

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
		boolean match = false;
		try {
			return this.match(email);			
		} catch (Exception e) {
			InfoHandler.error("PairMailRule match1stMailRule error",e); 
		}
		return match;
	}
	
	private boolean match2ndMailRule(EmailMessage email){
		try{			
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
		

	@Override
	public boolean execute(EmailMessage email) {
		if (match1stMailRule(email)) {//this is the first mail
			InfoHandler.debug("match1stMailRule=true");			
			firstMailMessageId = this.parseMessageId(email);
			startCountDown(Calendar.getInstance().getTime(),firstMailMessageId);
		} else	if (match2ndMailRule(email)) {
			InfoHandler.debug("match2ndMailRule=true");
			 String secondMailMessageId = this.parseMessageId(email);
			 if(firstMailMessageId!=null&&firstMailMessageId.equals(secondMailMessageId)){
				 stopCountDown(secondMailMessageId);
			 }
		}
	
		return false;
	}

	private void startCountDown(Date firstMailComeTime,String messageId) {
		if (period <= 0) {
			return;
		}
		// check time = firstMailComeTime + periodMinute
			Calendar c = Calendar.getInstance();
			c.setTime(firstMailComeTime);
			c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + period);
			Timer timer = new Timer();
			TimerTask countingDownTask = new PairMailCountingDownTask(this);
			timer.schedule(countingDownTask, c.getTime());
			countingDownTaskMap.put(messageId, countingDownTask);
			InfoHandler.info("timer.schedule(countingDownTask, "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime())+"); [ruleName="+this.getName()+"][messageid="+messageId+"]");
		
	}

	private void stopCountDown(String messageId) {
		//firstMailComeTime = null;
		//firstMailMessageId = null;
		TimerTask countingDownTask = this.countingDownTaskMap.get(messageId);
		if (countingDownTask != null) {
			countingDownTask.cancel();
			InfoHandler.info("timer.cancelled);[ruleName="+this.getName()+"][messageid="+messageId+"]");
		}
	}

	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	/*public String getFirstMailComeTime() {
		return firstMailComeTime;
	}

	public void setFirstMailComeTime(String firstMailComeTime) {
		this.firstMailComeTime = firstMailComeTime;
	}*/

	

	/*public TimerTask getCountingDownTask() {
		return countingDownTask;
	}

	public void setCountingDownTask(TimerTask countingDownTask) {
		this.countingDownTask = countingDownTask;
	}
*/
	public static void main(String[] args) throws Exception {
		NoPairMailRule r = new NoPairMailRule();
		//r.setFirstMailComeTime("2013-08-31 21:09:00");
/*
		Calendar c = DateUtil.toCalendar(r.getFirstMailComeTime(), Constants.DATETIME_FORMAT);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + Integer.valueOf("3"));
		Timer timer = new Timer();

		timer.schedule(new PairMailCountingDownTask(r), c.getTime());

		Thread.sleep(10000);
		timer.cancel();*/
	}
	public List<RuleCondition2> getConditions2() {
		return conditions2;
	}
	public void setConditions2(List<RuleCondition2> conditions2) {
		this.conditions2 = conditions2;
	}	
}

class PairMailCountingDownTask extends TimerTask { 
	private NoPairMailRule rule;
	public PairMailCountingDownTask(NoPairMailRule r) {
		this.rule = r;
	}

	public void run() {
		//if (!StringUtil.isEmpty(rule.getFirstMailComeTime())) {
			try {
				rule.doAction(null);
			} catch (Exception e) {
				InfoHandler.error(this.getClass() + "Error happend when send email", e);
			}
		/*} else {
			this.cancel();
		}*/
	}	
}

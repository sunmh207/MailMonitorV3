package supportnet.rule.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.Item;
import microsoft.exchange.webservices.data.ServiceLocalException;
import supportnet.common.util.DateUtil;
import supportnet.common.util.InfoHandler;

public class ContinuesMailRule extends BaseRule {
	private int period;
	private int mailNumberThreshold;
	private List<EmailMessage> mailList = new ArrayList<EmailMessage>();

	public String getType(){
		return "ContinuesMailRule";
	}
	public void init() {
		
	}

	@Override
	public boolean execute(EmailMessage email) {
		try{
			if (this.match(email)) {
				return addAndCheckMailQueue(email);
			}
		} catch (Exception e) {
			InfoHandler.error("ContinuesMailRule method encountered an error",e);
		}
		return false;
	}

	/**
	 * if receive a new email, check the queue, see if the queue number reach mailNumberThreshold
	 * 
	 * @param email
	 * @return
	 */
	private boolean addAndCheckMailQueue(EmailMessage email) {
		mailList.add(email);
		int size = mailList.size();
		while(size>mailNumberThreshold){
			mailList.remove(size-1);
			size=mailList.size();
		}
		if(size==mailNumberThreshold){
			try{
				Item firstItem = mailList.get(size-1);
				Item lastItem = mailList.get(0);
				Date firstTime = firstItem.getDateTimeReceived();
				Date lastTime = lastItem.getDateTimeReceived();
			
				Calendar cFirst =Calendar.getInstance();
				cFirst.setTime(firstTime);
				Calendar cLast =Calendar.getInstance();
				cLast.setTime(lastTime);
				
				Long diff =DateUtil.differSeconds(cLast.getTime(), cFirst.getTime())/60;
				if(diff<period){
					this.doAction(email);
				}
			}catch(ServiceLocalException e){
				InfoHandler.error("Error happened when checking Continues Mail Rule.",e);
			}
		}
		return false;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getMailNumberThreshold() {
		return mailNumberThreshold;
	}

	public void setMailNumberThreshold(int mailNumberThreshold) {
		this.mailNumberThreshold = mailNumberThreshold;
	}

	public static void main(String[] args){
		List<String> l = new ArrayList<String>();
		l.add("a");
		l.add("b");
		l.add("c");
		l.add("d");
		System.out.println(l.get(0));
		System.out.println(l.get(1));
		System.out.println(l.get(2));
		System.out.println(l.get(3));
		l.remove(0);
		System.out.println(l.get(0));
		System.out.println(l.get(1));
		System.out.println(l.get(2));
		
	}

}

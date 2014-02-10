package supportnet.rule.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import supportnet.common.Constants;
import supportnet.common.JITActionBase;
import supportnet.common.exception.JTException;
import supportnet.common.util.DateUtil;
import supportnet.mail.domain.EmailAccount;
import supportnet.mail.service.EmailAccountService;
import supportnet.rule.domain.BaseRule;
import supportnet.rule.domain.Schedule;
import supportnet.rule.domain.SingleMailRule;

import com.opensymphony.xwork2.Preparable;

public class BaseRuleAction  extends JITActionBase implements Preparable  {
	protected static EmailAccountService service;
	protected List<String> projectList = Constants.PROJECT_LIST;
	protected Map<String,String> emailAccountMap;
	protected Map<String,String> scheduleMap;
	
	//protected BaseRule rule;
	protected EmailAccount emailAccount;
	protected Schedule schedule;
	
	public void prepare() throws JTException {
		if (service == null) {
			service = new EmailAccountService();
		}
		List<EmailAccount> emailAccountList = service.queryAllEmailAccounts();
		emailAccountMap=new HashMap<String,String>();
		for(EmailAccount account:emailAccountList){
			emailAccountMap.put(account.getId(), account.getUsername()+"/"+account.getFolder());
		}
		
		List scheduleList = service.queryByHql("from Schedule order by timeRange");
		scheduleMap=new HashMap<String,String>();
		for(Object o:scheduleList){
			Schedule schedule =(Schedule)o;
			scheduleMap.put(schedule.getId(), schedule.getName());
		}
	
	}

	public List<String> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<String> projectList) {
		this.projectList = projectList;
	}

	public Map<String, String> getEmailAccountMap() {
		return emailAccountMap;
	}

	public void setEmailAccountMap(Map<String, String> emailAccountMap) {
		this.emailAccountMap = emailAccountMap;
	}

	public Map<String, String> getScheduleMap() {
		return scheduleMap;
	}

	public void setScheduleMap(Map<String, String> scheduleMap) {
		this.scheduleMap = scheduleMap;
	}

	public EmailAccount getEmailAccount() {
		return emailAccount;
	}

	public void setEmailAccount(EmailAccount emailAccount) {
		this.emailAccount = emailAccount;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

}

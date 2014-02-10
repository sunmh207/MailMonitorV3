package supportnet.rule.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ServiceLocalException;
import supportnet.common.util.InfoHandler;
import supportnet.mail.domain.EmailAccount;

public class BaseRule {
	public static final String TYPE_SINGLE_MAIL_RULE="SingleMailRule";
	public static final String TYPE_CONTINUE_MAIL_RULE="ContinueMailRule";
	public static final String TYPE_NO_MAIL_RULE="NoMailRule";
	public static final String TYPE_NOPAIR_MAIL_RULE="NoPairMailRule";
	
	protected String id;
	protected String project;
	protected String name;
	protected String desc;
	protected String createTime;
	protected String creator;
	protected Boolean active = false;
	protected Integer order = 100;
	protected Boolean stopProcessingMoreRules=false;
	//protected String triggerTimes;
	
	protected EmailAccount emailAccount;
	protected Schedule schedule;
	
	protected List<RuleCondition> conditions = new ArrayList<RuleCondition>();
	/*protected List<RuleException> exceptions= new ArrayList<RuleException>();*/
	protected List<BaseAction> actions= new ArrayList<BaseAction>();
	
	

	public void init(){};
	public String getType(){return "BaseRule";}
	public boolean execute(EmailMessage email){return false;}
	/**
	 * Check if the rule is active. If it is not active, shouldn't take action.
	 * @return
	 */
	

	private boolean matchConditions(EmailMessage email){
		for(RuleCondition c:conditions){
			if(c!=null&&c.match(email)){
				return true;
			}
		}
		return false;
	}
	
/*	private boolean matchExceptions(EmailMessage email){ 
		for(RuleException e:exceptions){
			if(e.match(email)){
				return true;
			}
		}
		return false;
	}*/
	private boolean matchSchedule(Date date){
		return schedule.match(date);
	}
	
	protected boolean match(EmailMessage email){
		try{
			//return matchConditions(email)&&!matchExceptions(email) && matchSchedule(email.getDateTimeReceived());
			return matchConditions(email) && matchSchedule(email.getDateTimeReceived());
		}catch(ServiceLocalException sle){
			InfoHandler.error("getDateTimeReceived encountered error.", sle);
		}
		return false;
	}
	
	
	protected void doAction(EmailMessage email){
		for(BaseAction act:actions){
			if(act!=null) {
				act.doAction(email);
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	
	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public List<RuleCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<RuleCondition> conditions) {
		this.conditions = conditions;
	}

/*	public List<RuleException> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<RuleException> exceptions) {
		this.exceptions = exceptions;
	}*/

	

	public EmailAccount getEmailAccount() {
		return emailAccount;
	}

	public List<BaseAction> getActions() {
		return actions;
	}
	public void setActions(List<BaseAction> actions) {
		this.actions = actions;
	}
	
	public void setEmailAccount(EmailAccount emailAccount) {
		this.emailAccount = emailAccount;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Boolean isStopProcessingMoreRules() {
		return stopProcessingMoreRules;
	}

	public void setStopProcessingMoreRules(Boolean stopProcessingMoreRules) {
		this.stopProcessingMoreRules = stopProcessingMoreRules==null?false:stopProcessingMoreRules;
	}
	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof BaseRule) ) return false;
        final BaseRule rule = (BaseRule) other;
        if (rule.getId()==null) return false;
        if (!rule.getId().equals(getId() ) ) return false;
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
    
  /*  public static void main(String[] args){
    	Rule r = new SingleMailRule();
    	System.out.print(r instanceof Rule);
    	System.out.print(r instanceof SingleMailRule);
    	SingleMailRule r2 = new SingleMailRule();
    	System.out.print(r2 instanceof Rule);
    }*/
}

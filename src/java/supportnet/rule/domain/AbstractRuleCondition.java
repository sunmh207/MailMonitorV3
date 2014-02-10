package supportnet.rule.domain;

import microsoft.exchange.webservices.data.EmailMessage;
import supportnet.common.util.InfoHandler;
import supportnet.common.util.StringUtil;

@org.hibernate.annotations.ForceDiscriminator
public abstract class AbstractRuleCondition {
	public static final String CONDITION_RESULT_MATCH = "1";
	public static final String CONDITION_RESULT_NOTMATCH = "0";
	
	private String id;
	private String subjectExp;
	private String bodyExp;	
	private String fromExp;
	private int order;
	private BaseRule rule;
	
	public abstract String getType();
	
	protected boolean isMatch(String result){
		return (CONDITION_RESULT_MATCH.equals(result));
	}
	protected boolean isNotMatch(String result){
		return (CONDITION_RESULT_NOTMATCH.equals(result));
	}
	/**
	 * 
	 * @param email
	 * @return  match subjectExp&& bodyExp && fromExp -> true; else -> false;
	 */
	public boolean match(EmailMessage email){
		boolean ret = false;
		try{
			String subject = email.getSubject();
			String body = email.getBody().toString();
			String from = email.getFrom().toString();

			boolean subjectMatch=false;
			if(StringUtil.isEmpty(subjectExp)){
				subjectMatch=true;
			}else{
				subjectMatch=StringUtil.matchRegexp(subject, subjectExp);
			}
			
			boolean bodyMatch=false;
			if(StringUtil.isEmpty(bodyExp)){
				bodyMatch=true;
			}else{
				bodyMatch=StringUtil.matchRegexp(body, bodyExp);
			}
			
			boolean fromMatch=false;
			if(StringUtil.isEmpty(fromExp)){
				fromMatch=true;
			}else{
				fromMatch=StringUtil.matchRegexp(from, fromExp);
			}
			
			ret= subjectMatch&&bodyMatch&&fromMatch;
		}catch(Exception e){
			InfoHandler.error("Check Conditon encountered errors!", e);
		}
		return ret;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubjectExp() {
		return subjectExp;
	}

	public void setSubjectExp(String subjectExp) {
		this.subjectExp = subjectExp;
	}

	public String getBodyExp() {
		return bodyExp;
	}

	public void setBodyExp(String bodyExp) {
		this.bodyExp = bodyExp;
	}

	public String getFromExp() {
		return fromExp;
	}

	public void setFromExp(String fromExp) {
		this.fromExp = fromExp;
	}
	public BaseRule getRule() {
		return rule;
	}
	public void setRule(BaseRule rule) {
		this.rule = rule;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof AbstractRuleCondition) ) return false;
        final AbstractRuleCondition condition = (AbstractRuleCondition) other;
        if (condition.getId()==null) return false;
        if (!condition.getId().equals( getId() ) ) return false;
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

}

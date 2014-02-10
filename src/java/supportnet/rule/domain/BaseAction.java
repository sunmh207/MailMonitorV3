package supportnet.rule.domain;

import microsoft.exchange.webservices.data.EmailMessage;

public  class BaseAction {
	private String id;
	private int order;
	protected BaseRule rule;
	
	public void doAction(EmailMessage email){
		//base action do nothing 
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
        if ( !(other instanceof BaseRule) ) return false;
        final BaseAction action = (BaseAction) other;
        if (action.getId()==null) return false;
        if (!action.getId().equals( getId() ) ) return false;
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

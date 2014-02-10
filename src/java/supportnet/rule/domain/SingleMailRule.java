package supportnet.rule.domain;

import microsoft.exchange.webservices.data.EmailMessage;

public class SingleMailRule extends BaseRule{
	
	public String getType(){
		return "SingleMailRule";
	}
	public void init() {
	}
	/**
	 * execute rule
	 * 
	 * @param mail
	 * @return. if mail match this rule, return true; otherwise return false;
	 */
	public boolean execute(EmailMessage email) {
		boolean match = this.match(email);
		if (match) {
			this.doAction(email);
		}
		return match;
	}
	
	
}

package supportnet.rule.action.ruleaction;

import supportnet.common.JITActionBase;
import supportnet.common.exception.JTException;
import supportnet.common.service.BaseService;
import supportnet.rule.domain.BaseRule;
import supportnet.rule.domain.SendMailAction;

import com.opensymphony.xwork2.Preparable;

public class SendMailAction_FormAction extends JITActionBase implements Preparable  {
	protected BaseService service;
	private BaseRule rule;
	private SendMailAction action;
	
	public void prepare() throws JTException {
		if (service == null) {
			service = new BaseService();
		}
		if(rule != null && rule.getId() != null){
			rule = (BaseRule) service.findBoById(BaseRule.class, rule.getId());
		}

		if (action != null && action.getId() != null) {
			action = (SendMailAction) service.findBoById(SendMailAction.class, action.getId());
		}
	}


	public String input() {
		return INPUT;
	}

	
	public String delete() throws JTException {
		service.deleteBo(SendMailAction.class, action.getId());
		return SUCCESS;
	}

	public String save() throws Exception {
		try {
			// new
			if (action.getId() == null || "".equals(action.getId())) {
				action.setRule(rule);
				service.createBo(action);
				
				// modify
			} else {
				service.updateBo(action);
			}
			this.getSession().put("rule",rule);
		} catch (Exception e) {
			this.addActionError(e.getMessage());
			return INPUT;
		}
		return SUCCESS;
	}


	public BaseRule getRule() {
		return rule;
	}


	public void setRule(BaseRule rule) {
		this.rule = rule;
	}


	public SendMailAction getAction() {
		return action;
	}


	public void setAction(SendMailAction action) {
		this.action = action;
	}


	
}

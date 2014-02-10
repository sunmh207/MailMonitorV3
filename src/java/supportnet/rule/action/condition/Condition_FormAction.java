package supportnet.rule.action.condition;

import supportnet.common.JITActionBase;
import supportnet.common.exception.JTException;
import supportnet.common.service.BaseService;
import supportnet.rule.domain.BaseRule;
import supportnet.rule.domain.RuleCondition;

import com.opensymphony.xwork2.Preparable;

public class Condition_FormAction extends JITActionBase implements Preparable  {
	protected static BaseService service;
	private BaseRule rule;
	private RuleCondition condition;
	
	public void prepare() throws JTException {
		if (service == null) {
			service = new BaseService();
		}
		if(rule != null && rule.getId() != null){
			rule = (BaseRule) service.findBoById(BaseRule.class, rule.getId());
		}

		if (condition != null && condition.getId() != null) {
			condition = (RuleCondition) service.findBoById(RuleCondition.class, condition.getId());
		}
	}


	public String input() {
		return INPUT;
	}

	
	public String delete() throws JTException {
		service.deleteBo(RuleCondition.class, condition.getId());
		return SUCCESS;
	}

	public String save() throws Exception {
		try {
			// new
			if (condition.getId() == null || "".equals(condition.getId())) {
				condition.setRule(rule);
				service.createBo(condition);
				
				// modify
			} else {
				service.updateBo(condition);
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


	public RuleCondition getCondition() {
		return condition;
	}


	public void setCondition(RuleCondition condition) {
		this.condition = condition;
	}

	
}

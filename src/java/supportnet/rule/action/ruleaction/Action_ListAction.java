package supportnet.rule.action.ruleaction;

import java.util.List;

import supportnet.common.JITActionBase;
import supportnet.common.exception.JTException;
import supportnet.common.service.BaseService;
import supportnet.rule.domain.BaseAction;
import supportnet.rule.domain.BaseRule;
import supportnet.rule.domain.SingleMailRule;

import com.opensymphony.xwork2.Preparable;

public class Action_ListAction extends JITActionBase implements Preparable {
	protected BaseService service; 
	private BaseAction action;
	private BaseRule rule;
	public void prepare() throws JTException {
		if (service == null) {
			service = new BaseService();
		}
		if (rule != null && rule.getId() != null) {
			rule = (BaseRule) service.findBoById(BaseRule.class, rule.getId());
			this.session.put("rule", rule);
		}
		if(rule==null){
			Object o =this.session.get("rule");
			if(o instanceof BaseRule){
			rule = (BaseRule)o;
			}
		}
	}
	public String input() {
		return INPUT;
	}

	
	/*public String delete() throws JTException {
		service.deleteBo(AbstractRuleCondition.class, action.getId());
		return SUCCESS;
	}*/


	public String list() {
		try{
			/*String hql = "from BaseAction action where action.rule.id='"+rule.getId()+"' order by action.order ";
			List list=	service.queryByHql(hql);
			request.setAttribute("objectList", list);
			request.setAttribute("rule", rule);
			this.setObjectList(list);*/
			if (rule != null && rule.getId() != null) {
				service.getS().flush();
				rule = (BaseRule) service.findBoById(BaseRule.class, rule.getId());
			}
		}catch(JTException e){
			e.printStackTrace();
			this.addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	public BaseRule getRule() {
		return rule;
	}
	public void setRule(BaseRule rule) {
		this.rule = rule;
	}
	public BaseAction getAction() {
		return action;
	}
	public void setAction(BaseAction action) {
		this.action = action;
	}
	
}

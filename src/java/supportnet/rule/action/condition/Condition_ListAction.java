package supportnet.rule.action.condition;

import supportnet.common.JITActionBase;
import supportnet.common.exception.JTException;
import supportnet.common.service.BaseService;
import supportnet.rule.domain.AbstractRuleCondition;
import supportnet.rule.domain.BaseRule;
import supportnet.rule.domain.RuleCondition;
import supportnet.rule.domain.SingleMailRule;

import com.opensymphony.xwork2.Preparable;

public class Condition_ListAction extends JITActionBase implements Preparable {
	protected BaseService service; 
	private RuleCondition condition;
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

	
	public String delete() throws JTException {
		service.deleteBo(AbstractRuleCondition.class, condition.getId());
		return list();
	}

	/*public String getListHQL(ArrayList<Object> params) throws JTException {
		String hql = "select condition from AbstractRuleCondition condition where condition.rule.id='"+rule.getId()+"' order by condition.order ";
		return hql;
	}
	*/
	public String list() {
	/*	try{
			service.getS().flush();
			String hql = "select condition from RuleCondition condition where condition.rule.id='"+rule.getId()+"' order by condition.order ";
			List list=	service.queryByHql(hql);
			request.setAttribute("conditions", list);
			
			String hql2 = "select condition from RuleCondition2 condition where condition.rule.id='"+rule.getId()+"' order by condition.order ";
			List list2=	service.queryByHql(hql2);
			request.setAttribute("conditions2", list2);
			
			request.setAttribute("rule", rule);
			this.setObjectList(list);
		}catch(JTException e){
			e.printStackTrace();
			this.addActionError(e.getMessage());
		}*/
		try {
			if (rule != null && rule.getId() != null) {
				service.getS().flush();
				rule = (BaseRule) service.findBoById(BaseRule.class, rule.getId());
			}
		} catch (JTException e) {
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
	public RuleCondition getCondition() {
		return condition;
	}
	public void setCondition(RuleCondition condition) {
		this.condition = condition;
	}
}

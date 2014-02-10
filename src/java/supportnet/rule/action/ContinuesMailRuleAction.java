package supportnet.rule.action;

import supportnet.common.exception.JTException;
import supportnet.common.util.DateUtil;
import supportnet.mail.domain.EmailAccount;
import supportnet.rule.domain.ContinuesMailRule;
import supportnet.rule.domain.Schedule;

public class ContinuesMailRuleAction  extends BaseRuleAction  {
	protected ContinuesMailRule rule;
	
	public void prepare() throws JTException {
		super.prepare();
		if (rule != null && rule.getId() != null&&!"".equals(rule.getId())) {
			rule = (ContinuesMailRule) service.findBoById(ContinuesMailRule.class, rule.getId());
		}
		
	}

	public String input() {
		if (rule != null) {
			emailAccount = rule.getEmailAccount();
			schedule =rule.getSchedule();
		}
		return INPUT;
	}

	public String deactivate() throws JTException {
		rule.setActive(false);
		service.updateBo(rule);
		return SUCCESS;
	}

	public String activate() throws JTException {
		rule.setActive(true);
		service.updateBo(rule);
		return SUCCESS;
	}

	public String delete() throws JTException {
		service.deleteBo(rule);
		return SUCCESS;
	}

	public String save() throws Exception {
		try {
			if (emailAccount != null && emailAccount.getId() != null) {
				emailAccount = (EmailAccount) service.findBoById(EmailAccount.class, emailAccount.getId());
			}
			if (schedule != null && schedule.getId() != null) {
				schedule = (Schedule) service.findBoById(Schedule.class, schedule.getId());
			}
			
			rule.setEmailAccount(emailAccount);
			rule.setSchedule(schedule);
			// new
			if (rule.getId() == null || "".equals(rule.getId())) {
				String now = DateUtil.getCurrentDate();
				rule.setCreateTime(now);
				service.createBo(rule);
				// modify
			} else {
				service.mergeBo(rule);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError(e.getMessage());
			return INPUT;
		}
		return SUCCESS;
	}

	public ContinuesMailRule getRule() {
		return rule;
	}

	public void setRule(ContinuesMailRule rule) {
		this.rule = rule;
	}	

}

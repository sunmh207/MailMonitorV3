package supportnet.rule.action;

import supportnet.common.exception.JTException;
import supportnet.common.util.DateUtil;
import supportnet.mail.domain.EmailAccount;
import supportnet.rule.domain.NoMailOnTimeRule;
import supportnet.rule.domain.Schedule;

public class NoMailOnTimeRuleAction  extends BaseRuleAction  {
	protected NoMailOnTimeRule rule;
	
	public void prepare() throws JTException {
		super.prepare();
		if (rule != null && rule.getId() != null&&!"".equals(rule.getId())) {
			rule = (NoMailOnTimeRule) service.findBoById(NoMailOnTimeRule.class, rule.getId());
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

	public NoMailOnTimeRule getRule() {
		return rule;
	}

	public void setRule(NoMailOnTimeRule rule) {
		this.rule = rule;
	}	


}

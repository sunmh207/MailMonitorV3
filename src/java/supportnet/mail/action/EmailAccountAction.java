package supportnet.mail.action;

import java.util.ArrayList;

import supportnet.common.JITActionBase;
import supportnet.common.exception.JTException;
import supportnet.common.service.BaseService;
import supportnet.common.util.SysCache;
import supportnet.mail.domain.EmailAccount;

import com.opensymphony.xwork2.Preparable;

public class EmailAccountAction extends JITActionBase implements Preparable  {
	protected static BaseService service;
	protected EmailAccount account;
	
	public void prepare() throws JTException {
		if (service == null) {
			service = new BaseService();
		}
		if (account != null && account.getId() != null) {
			account = (EmailAccount) service.findBoById(EmailAccount.class, account.getId());
		}
	}


	public String input() {
		return INPUT;
	}

	
	public String delete() throws JTException {
		String id=account.getId();
		service.deleteBo(EmailAccount.class, id);
		//SysCache.emailAccountMap.remove(id);
		return SUCCESS;
	}

	public String save() throws Exception {
		String accountId =account.getId();
		try {
			// new
			if (account.getId() == null || "".equals(account.getId())) {
				accountId=service.createBo(account);
				// modify
			} else {
				service.updateBo(account);
			}
		} catch (Exception e) {
			this.addActionError(e.getMessage());
			return INPUT;
		}
		//SysCache.emailAccountMap.put(accountId, account);
		return SUCCESS;
	}

	public String getListHQL(ArrayList<Object> params) throws JTException {
		String hql = "from EmailAccount me order by me.username";
		return hql;
	}
	public EmailAccount getAccount() {
		return account;
	}


	public void setAccount(EmailAccount account) {
		this.account = account;
	}	

	
	
}

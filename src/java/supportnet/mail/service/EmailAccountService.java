package supportnet.mail.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import supportnet.common.exception.JTException;
import supportnet.common.service.BaseService;
import supportnet.mail.domain.EmailAccount;

public class EmailAccountService extends BaseService {
	public EmailAccountService(Session s) {
		super(s);
	}

	public EmailAccountService() throws JTException {
		super();
	}

	public List<EmailAccount> queryAllEmailAccounts() throws JTException {
		try {
			String hql="from EmailAccount account";
			Query query = this.s.createQuery(hql);
			List<EmailAccount> list = query.list();
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					this.s.evict(list.get(i));
				}
			}
			return list;
		} catch (HibernateException e) {
			throw new JTException("load EmailAccount error", e, this.getClass());
		}
	}

}

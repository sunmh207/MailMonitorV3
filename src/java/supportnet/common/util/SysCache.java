package supportnet.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import supportnet.common.exception.JTException;
import supportnet.common.service.BaseService;
import supportnet.mail.domain.EmailAccount;
import supportnet.rule.domain.BaseRule;

public class SysCache {
	public static final int OPER_ADD = 0;
	public static final int OPER_UPDATE = 1;
	public static final int OPER_DELETE = 2;

	private static Session session;
	private static boolean closeSession = false;

	//public static final Map<String, EmailAccount> emailAccountMap = new HashMap<String, EmailAccount>();
	//public static final Map<String, Rule> ruleMap = new HashMap<String, Rule>();

	public synchronized static void loadSysCache() throws JTException {
		try {
			closeSession = false;
			//loadEmailAccounts();
			//loadRules();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JTException(e.getMessage(), e, SysCache.class);
		} finally {
			session.close();
			session = null;
			closeSession = true;
		}
	}

	private synchronized static void checkSession() throws JTException {
		if (session == null || !session.isConnected()) {
			try {
				session = DBtools.getExclusiveSession();
			} catch (JTException e) {
				throw e;
			}
		}
	}


	/*public synchronized static void loadEmailAccounts() throws JTException {
		checkSession();
		try {
			emailAccountMap.clear();
			BaseService service = new BaseService(session);
			List<Object> list = service.queryByHql("from EmailAccount account");
			int count = list.size();
			if (count > 0) {
				for (int i = 0; i < count; i++) {
					EmailAccount account = (EmailAccount) list.get(i);
					emailAccountMap.put(account.getId(), account);
				}
			}

			if (closeSession) {
				session.close();
				session = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JTException("Load Rule failed.", e, SysCache.class);
		}
	}
	public synchronized static void updateEmailAccountMap(EmailAccount account) throws JTException {
		if(account==null) return;
		String id = account.getId();
		emailAccountMap.put(id, account);		
	}*/
	/*public synchronized static void loadRules() throws JTException {
		checkSession();
		try {
			ruleMap.clear();
			BaseService service = new BaseService(session);
			List<Object> list = service.queryByHql("from Rule rule where rule.active is true order by rule.project, rule.order");
			int count = list.size();
			if (count > 0) {
				for (int i = 0; i < count; i++) {
					Rule rule = (Rule) list.get(i);
					rule.init();
					ruleMap.put(rule.getId(), rule);
				}
			}
			
			if (closeSession) {
				session.close();
				session = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JTException("Load Rule failed.", e, SysCache.class);
		}
	}
	public synchronized static void updateRuleMap(Rule rule) throws JTException {
		if(rule==null) return;
		String id = rule.getId();
		ruleMap.put(id, rule);		
	}*/
}

package supportnet.common.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import supportnet.common.exception.JTException;

public class DefaultBaseService extends BaseService {
	public DefaultBaseService(Session s) {
		super(s);
	}

	public DefaultBaseService() throws JTException {
		super();
	}

	public List<Object> queryByHql(String hql) throws JTException {
		try {
			Query query = this.s.createQuery(hql);
			List<Object> list = query.list();
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					this.s.evict(list.get(i));
				}
			}
			return list;
		} catch (HibernateException e) {
			throw new JTException("读取持久化对象错误", e, this.getClass());
		}
	}

	public int queryForInt(String hql) throws JTException {
		return ((Long) this.s.createQuery(hql).iterate().next()).intValue();
	}
}

package supportnet.common.dao;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import supportnet.common.Pager;
import supportnet.common.exception.JTException;
import supportnet.common.util.DBtools;

@SuppressWarnings("rawtypes")
public class BaseDAO {
	protected Session s;

	/**
	 * @param session
	 */
	public BaseDAO(Session session) {
		this.s = session;
	}

	public void mergeBO(Object bo) throws JTException {
		try {
			if (bo != null) {
				this.s.merge(bo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JTException("merge持久化对象错误", e, this.getClass());
		}
	}

	/**
	 * @param bo
	 * @return
	 * @throws JTException
	 */
	public String createBo(Object bo) throws JTException {
		try {
			if (bo != null) {
				String id = (String) this.s.save(bo);
				this.s.flush();
				return id;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JTException("新增持久化对象错误", e, this.getClass());
		}
		return null;
	}

	public void saveOrUpdateBo(Object bo) throws JTException {
		try {
			if (bo != null) {
				this.s.saveOrUpdate(bo);
				this.s.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JTException("新增持久化对象错误", e, this.getClass());
		}
	}

	/**
	 * 根据主键删除一行记录.
	 * 
	 * @param clazz
	 *            待删除BO的类名
	 * @param pk
	 * @throws JTException
	 */
	public void deleteBo(Class clazz, String pk) throws JTException {
		try {
			Object bo = this.s.get(clazz, pk);
			if (bo != null) {
				this.s.delete(bo);
				this.s.flush();//add by stanley 2014-02-05
			}

		} catch (Exception e) {
			throw new JTException("删除持久化对象错误", e, this.getClass());
		}
	}

	/**
	 * 将传入的bo数据更新到数据库中 ,需要传入主键值,bo数据
	 * 
	 * @param pk
	 *            待更新bo的主键
	 * @param bo
	 *            待更新bo
	 */
	public void updateBo(String pk, Object bo) throws JTException {
		try {
			if (pk == null || bo == null) {
				return;
			}
			// s.update(pk, bo);
			this.s.update(bo);
			this.s.flush();
		} catch (Exception e) {
			throw new JTException("更新持久化对象错误", e, this.getClass());
		}
	}

	public void updateBo(Object bo) throws JTException {
		try {
			if (bo == null) {
				return;
			}
			this.s.update(bo);
			this.s.flush();
		} catch (Exception e) {
			throw new JTException("更新持久化对象错误", e, this.getClass());
		}
	}

	/**
	 * 批量更新数据对象
	 * 
	 * @param list
	 */
	public void saveOrUpdateAll(Collection list) throws JTException {
		try {
			for (Iterator it = list.iterator(); it.hasNext(); this.s.saveOrUpdate(it.next())) {
				;
			}
		} catch (HibernateException e) {
			throw new JTException("批量更新持久化对象错误", e, this.getClass());
		}

	}

	/**
	 * 删除一个对象
	 * 
	 * @param po
	 * @throws com.icitic.hrms.common.exception.JTException
	 * 
	 */
	public void deletePo(Object po) throws JTException {
		try {
			this.s.delete(po);
			this.s.flush();
		} catch (HibernateException e) {
			throw new JTException("删除持久化对象错误", e, this.getClass());
		}
	}

	/**
	 * 批量删除数据对象
	 * 
	 * @param list
	 */
	public void deleteAll(Collection list) throws JTException {
		try {
			if (list != null && list.size() > 0) {
				for (Iterator it = list.iterator(); it.hasNext(); this.s.delete(it.next())) {
					;
				}
				this.s.flush();
			}
		} catch (HibernateException e) {
			throw new JTException("批量删除持久化对象错误", e, this.getClass());
		}
	}

	/**
	 * 根据ID查询一个数据对象
	 * 
	 * @param cl
	 * @param pkId
	 * @return java.lang.Object
	 * 
	 */
	public Object findBoById(Class cl, String pkId) throws JTException {
		try {
			if (pkId == null) {
				return null;
			}
			Object obj = this.s.get(cl, pkId);
			this.s.evict(obj);
			return obj;
		} catch (HibernateException e) {
			throw new JTException("读取持久化对象错误", e, this.getClass());
		}
	}

	/**
	 * @param queryString
	 * @param values
	 * @return
	 * @throws JTException
	 */

	public List findWithPager(String queryString, Pager pager) throws JTException {
		return findWithPager(queryString, pager, new Object[0]);
	}

	public List findWithPager(String queryString, Pager pager, Object[] values) throws JTException {
		try {
			Query query = this.s.createQuery(queryString);
			if (queryString != null && values != null) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
			}

			if (pager != null) {
				int pageNumber = pager.getCurrentPage();
				pageNumber = pageNumber < 1 ? 1 : pageNumber;
				pager.setCurrentPage(pageNumber);
				query = query.setFirstResult(pager.getPageSize() * (pageNumber - 1));
				query.setMaxResults(pager.getPageSize());
			}

			List list = query.list();
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					this.s.evict(list.get(i));
				}
			}
			if (pager != null) {
				countResult(queryString, pager, values);
			}
			return list;
		} catch (HibernateException e) {
			throw new JTException("读取持久化对象错误", e, this.getClass());
		}
	}

	private void countResult(String queryString, Pager pager, Object[] values) {
		int idx = queryString.toLowerCase().indexOf("from ");
		String countQueryString = "select count(*) " + queryString.substring(idx);
		Query countQuery = this.s.createQuery(countQueryString);
		if (countQueryString != null && values != null) {
			for (int i = 0; i < values.length; i++) {
				countQuery.setParameter(i, values[i]);
			}
		}
		Number numCount = (Number) countQuery.uniqueResult();
		int totalNumber = numCount == null ? 0 : numCount.intValue();
		pager.setTotalRecords(totalNumber);
		int totalPages = totalNumber / pager.getPageSize();
		totalPages += totalNumber % pager.getPageSize() == 0 ? 0 : 1;
		pager.setTotalPages(totalPages);
	}

	public List find(String queryString, Object[] values) throws JTException {
		return findWithPager(queryString, null, values);
	}

	public List find(String queryString) throws JTException {
		return findWithPager(queryString, null, null);
	}

	/**
	 * @param hql
	 * @return
	 * @throws JTException
	 */
	public List queryByHql(String hql) throws JTException {
		try {
			Query query = this.s.createQuery(hql);
			List list = query.list();
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

	/**
	 * @param obj
	 * @throws JTException
	 */
	public void evict(Object obj) throws JTException {
		try {
			this.s.evict(obj);
		} catch (HibernateException e) {
			throw new JTException("持久化对象错误", e, this.getClass());
		}
	}

	public static void main(String[] args) throws Exception {
		Session session = DBtools.getSession();
		BaseDAO dao = new BaseDAO(session);
		/*Pager pager = new Pager();
		pager.setPageSize(1);
		dao.findWithPager("from com.jitong.yunshuzhihui.domain.Yunshuzhihui", pager);
		System.out.println(pager);*/
		List list = dao.queryByHql("select dial.finger, count(dial.finger) from OutBoxDialVO dial group by dial.finger");
		for(int i=0;i<list.size();i++){
			Object[] os =(Object[])list.get(i);
			System.out.println(os[0]+"-"+os[1]);
		}
	}
}

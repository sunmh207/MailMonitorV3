package supportnet.common.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import supportnet.common.dao.BaseDAO;
import supportnet.common.exception.JTException;
import supportnet.common.util.DBtools;

public class BaseService {
	public boolean selfCommit;
	public String beginTransactionMethod;
	public boolean isTransOpen;
	protected Session s;

	/**
	 * @param s
	 */
	public BaseService(Session s) {
		this.s = s;
		this.selfCommit = false;
	}

	public BaseService() throws JTException {
		this.s = DBtools.getSession();
		this.selfCommit = true;
	}

	/**
	 * 基类中提供事务管理方法.所有的manage都要使用beginTrans开启事务. 根据事务处理标记进行事务开启.
	 * 
	 * @return Transaction
	 * 
	 * 
	 */
	public Transaction beginTransaction() throws JTException {
		Throwable t = new Throwable();
		if (this.selfCommit && !this.isTransOpen) {
			this.beginTransactionMethod = t.getStackTrace()[1].getMethodName();
			this.isTransOpen = true;
			try {
				return this.s.beginTransaction();
			} catch (HibernateException e) {
				throw new JTException(this.beginTransactionMethod + " 开启事务失败", e, this.getClass());
			}
		}
		return null;
	}

	/**
	 * manage中要提交事务需要调用本方法.将beginTransaction返回的transaction传入提交. 判断事务处理标记 在提交.
	 * 
	 * @param transaction
	 */
	public void commitTransaction(Transaction transaction) throws JTException {
		Throwable t = new Throwable();
		String methodname = t.getStackTrace()[1].getMethodName();
		if (this.selfCommit && this.isTransOpen && methodname.equals(this.beginTransactionMethod)) {
			try {
				if (null != transaction) {
					transaction.commit();
					this.isTransOpen = false;
					this.beginTransactionMethod = "";
				}
			} catch (HibernateException e) {
				throw new JTException(this.beginTransactionMethod + " 提交事务失败", e, this.getClass());
			}
		}
	}

	/**
	 * 使用本方法回滚事务,在方法中根据事务处理标记选择是否要进行回滚.
	 * 
	 * @param transaction
	 */
	public void rollbackTransaction(Transaction transaction) throws JTException {
		Throwable t = new Throwable();
		String methodname = t.getStackTrace()[1].getMethodName();
		if (this.selfCommit && this.isTransOpen && methodname.equals(this.beginTransactionMethod)) {
			try {
				if (null != transaction) {
					transaction.rollback();
					this.isTransOpen = false;
					this.beginTransactionMethod = "";
				}
			} catch (HibernateException e) {
				throw new JTException(this.beginTransactionMethod + " 回滚事务失败", e, this.getClass());
			}
		}
	}

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

	public List<Object> queryAll(Class cl) throws JTException {
		try {
			return (List<Object>) new BaseDAO(this.s).find("from " + cl.getName() + " u");

		} catch (HibernateException e) {
			throw new JTException("读取持久化对象错误", e, this.getClass());
		}
	}

	public void deleteAll(String hql) throws JTException {
		List<Object> list = new BaseDAO(this.s).find(hql);
		deleteAll(list);
	}
	
	public List queryByHql(String hql) throws JTException {
		return new BaseDAO(this.s).queryByHql(hql);
	}

	public boolean objectsExist(String hql) throws JTException {
		List<Object> list = new BaseDAO(this.s).find(hql);
		return !list.isEmpty();
	}

	/**
	 * @param bo
	 * @return
	 * @throws JTException
	 */
	public String createBo(Object bo) throws JTException {
		String id = null;
		Transaction tx = null;
		try {
			tx = this.beginTransaction();
			if (bo != null) {
				id = (String) this.s.save(bo);
				this.s.flush();
			}
			this.commitTransaction(tx);
			return id;
		} catch (Exception e) {
			this.rollbackTransaction(tx);
			throw new JTException("新增对象失败", e, this.getClass());
		}
	}
	public void saveOrUpdateBo(Object bo) throws JTException {
		Transaction tx = null;
		try {
			tx = this.beginTransaction();
			if (bo != null) {
				this.s.saveOrUpdate(bo);
				this.s.flush();
			}
			this.commitTransaction(tx);
		} catch (Exception e) {
			this.rollbackTransaction(tx);
			throw new JTException("新增对象失败", e, this.getClass());
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
		Transaction tx = null;
		try {
			tx = this.beginTransaction();
			Object bo = this.s.get(clazz, pk);
			if (bo != null) {
				this.s.delete(bo);
				this.s.flush();
			}
			this.commitTransaction(tx);
		} catch (Exception e) {
			this.rollbackTransaction(tx);
			throw new JTException("删除对象失败", e, this.getClass());
		}
	}
	public void deleteBo(Object bo) throws JTException {
		Transaction tx = null;
		try {
			tx = this.beginTransaction();
			if (bo != null) {
				this.s.delete(bo);
				this.s.flush();
			}
			this.commitTransaction(tx);
		} catch (Exception e) {
			this.rollbackTransaction(tx);
			throw new JTException("删除对象失败", e, this.getClass());
		}
	}

	public void deleteAll(Collection list) throws JTException {
		Transaction tx = null;
		try {
			tx = this.beginTransaction();
			if (list != null && list.size() > 0) {
				for (Iterator it = list.iterator(); it.hasNext();) {
					this.s.delete(it.next());
					this.s.flush();
				}
			}
			this.commitTransaction(tx);
		} catch (Exception e) {
			this.rollbackTransaction(tx);
			throw new JTException("删除对象失败", e, this.getClass());
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
		Transaction tx = null;
		try {
			tx = this.beginTransaction();
			if (pk == null || bo == null) {
				return;
			}
			this.s.update(bo);
			this.s.flush();
			this.commitTransaction(tx);
		} catch (Exception e) {
			this.rollbackTransaction(tx);
			throw new JTException("更新对象失败", e, this.getClass());
		}
	}

	public void updateBo(Object bo) throws JTException {
		Transaction tx = null;
		try {
			tx = this.beginTransaction();
			if (bo == null) {
				return;
			}
			this.s.update(bo);
			this.s.flush();
			this.commitTransaction(tx);
		} catch (Exception e) {
			this.rollbackTransaction(tx);
			throw new JTException("更新对象失败", e, this.getClass());
		}
	}
	public void mergeBo(Object bo) throws JTException {
		Transaction tx = null;
		try {
			tx = this.beginTransaction();
			if (bo == null) {
				return;
			}
			this.s.merge(bo);
			this.s.flush();
			this.commitTransaction(tx);
		} catch (Exception e) {
			this.rollbackTransaction(tx);
			throw new JTException("更新对象失败", e, this.getClass());
		}
	}

	public Session getS() {
		return s;
	}

	public void setS(Session s) {
		this.s = s;
	}
}

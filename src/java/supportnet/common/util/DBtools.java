package supportnet.common.util;

import java.net.URL;
import java.sql.Connection;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import supportnet.common.exception.JTException;

public class DBtools { 
    protected static SessionFactory sf;
    public static final ThreadLocal tl = new ThreadLocal();
    public static final ThreadLocal querySession = new ThreadLocal();

    /**
     * 本方法初始化Hibernate SessionFactory.系统使用一个SessionFactory.
     * 并且只初始化一次</br>
     *
     */
    public void init() throws JTException {
        try {
            String file = "/hibernate.cfg.xml";
            URL in = this.getClass().getResource(file);
            sf = new Configuration().configure(in).buildSessionFactory();
        } catch (HibernateException e) {
            throw new JTException("初始化SessionFactory出错.", e, this.getClass());
        }
    }

    /**
     * @return java.sql.Connection
     *
     */
    public static Connection getConnection() throws JTException {
    	return getSession().connection();
        //return null;
    }

    /**
     * 本方法返回一个session,并且该session和当前线程绑定一起.
     * 约束一个线程只使用一个session.
     *
     * @return org.hibernate.Session
     *
     */
    public static Session getSession() throws JTException {
        try {
            if (null == sf)
                new DBtools().init();
            Session s = (Session) tl.get();
            if (null == s||!s.isOpen()) {
                s = sf.openSession();
                tl.set(s);
            }
            return s;
        } catch (HibernateException e) {
            throw new JTException("创建Session出错.", e, DBtools.class);
        }
    }

    /**
     * 关闭并使session与当前线程脱离.在session关闭前需要手工提交事务.不要在Manager和DAO层
     * 干预session的关闭操作.关闭操作由Action或Filter统一处理.
     *
     *
     */
    public static void closeSession() throws JTException {
        try {
            Session s = (Session) tl.get();
            if (null != s) {
                s.close();
                tl.set(null);
            }
        } catch (Exception e) {
            throw new JTException("关闭Session出错.", e, DBtools.class);
        }
    }

    /**
     * 本方法返回一个独立的session.该session脱离Manager的事务管理.当需要独立管理事务
     * 的时候,可以使用本方法返回session.返回的session需要手工开启,提交,回滚事务,使用完毕
     * 后需要关闭.
     *
     * @return org.hibernate.Session
     *
     */
    public static Session getExclusiveSession() throws JTException {
        if (null == sf) {
            new DBtools().init();
        }
        try {
            return sf.openSession();
        } catch (HibernateException e) {
            throw new JTException("创建Session出错.", e, DBtools.class);
        }
    }
}
package supportnet.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import supportnet.common.dao.BaseDAO;
import supportnet.common.exception.JTException;
import supportnet.common.util.ActionUtil;
import supportnet.common.util.CookieUtil;
import supportnet.common.util.DBtools;
import supportnet.common.util.StringUtil;

import com.opensymphony.xwork2.ActionSupport;

public abstract class JITActionBase extends ActionSupport implements ServletRequestAware, SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected static Logger logger = Logger.getLogger(JITActionBase.class);
	protected HttpServletRequest request;
	protected Map<String, Object> session;

	private String categoryId;
	protected int currentPage = 1;
	// protected int pageSize = 20;
	public String businessClass;

	private List objectList;
	
	public String getCategoryId() {
		//return this.categoryId;
		return this.getClass().toString();
	}

	/*public void setCategoryId(String pageId0) {
		this.categoryId = pageId0;
	}*/

	public List getObjectList() {
		return objectList;
	}

	public void setObjectList(List objectList) {
		this.objectList = objectList;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public Map<String, Object> getSession() {
		return this.session;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

	public int getCurrentPage() {
		String categoryId = getCategoryId();
		if (categoryId == null) {
			return this.currentPage;//if no cetegoryId, use the value which saved in action.
		}
		Integer p = (Integer) session.get("currentPage-" + categoryId);
		if (p != null) {
			return p;
		} else {
			return 1;
		}
	}

	public void setCurrentPage(int pageNumber) {
		session.put("currentPage-" + getCategoryId(), Integer.valueOf(pageNumber));
		this.currentPage=pageNumber;
	}

	/*
	 * public int getCurrentPage() { return this.currentPage; } public void
	 * setCurrentPage(int pageNumber) { this.currentPage=pageNumber; }
	 */

	protected void nextPage() {
		int i = getCurrentPage();
		session.put("currentPage-" + getCategoryId(), Integer.valueOf(i + 1));
	}

	protected void previousPage() {
		int i = getCurrentPage();
		session.put("currentPage-" + getCategoryId(), Integer.valueOf(i - 1));
	}

	/*public User getLoginUser() {
		User user= (User) session.get(JitongConstants.USER);
		if(user==null){
			logger.info("当前用户超时");
		}else{
			logger.info("当前用户:"+user.getName());
		}
		return user;
	}
*/
	public int getPageSize() {
		int pageSize;
		Integer p = (Integer) session.get("pageSize-" + getCategoryId());
		if (p != null) {
			pageSize = p;
		} else {
			pageSize = 20;
		}

		if (pageSize < 1) {
			String cookiePageSize = CookieUtil.getCookie(request, "pageSize");
			logger.debug("cookiePageSize" + cookiePageSize);
			if (cookiePageSize != null && cookiePageSize.matches("[0-9]{1,3}")) {
				pageSize = Integer.parseInt(cookiePageSize);
			}
		}
		if (pageSize < 1)
			pageSize = 20;
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		session.put("pageSize-" + getCategoryId(), Integer.valueOf(pageSize));
	}

	public String getBusinessClass() {

		return businessClass;
	}

	public void setBusinessClass(String businessClass) {
		this.businessClass = businessClass;
	}

	public String list() {
		try{
		BaseDAO dao = new BaseDAO(DBtools.getSession());
		Pager pager = new Pager(this.getCurrentPage(), this.getPageSize());
		List<?> list = null;
		ArrayList<Object> params = new ArrayList<Object>();
		String listHQL = getListHQL(params);
		logger.debug("listHQL:" + listHQL);
		Object[] arrayParams = params.toArray();
		if (StringUtil.isEmpty(listHQL)) {
			Class<?> domainClass = ActionUtil.retireDomainClassNameFromAction(this);
			list = dao.findWithPager("from " + domainClass.getName(), pager, arrayParams);
		} else {
			list = dao.findWithPager(listHQL, pager, arrayParams);
		}
		request.setAttribute("objectList", list);
		request.setAttribute("pager", pager);
		this.setObjectList(list);
		}catch(JTException e){
			e.printStackTrace();
			this.addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	public String getListHQL(ArrayList<Object> params) throws JTException {
		Class<?> domainClass = ActionUtil.retireDomainClassNameFromAction(this);
		String hql = "from " + domainClass.getName();
		return hql;
	}

	public String getRemoteAddr() {
		return ActionUtil.getRemoteAddr(request);
	}

	/*public String getLoginUserInfo() {
		User u = this.getLoginUser();
		return "用户名：" + u==null?"":u.getName() + " IP:" + getRemoteAddr();
	}*/

	public String exportExcel() throws JTException {
		BaseDAO dao = new BaseDAO(DBtools.getSession());
		Pager pager = new Pager(1, Constants.MAX_PAGE_SIZE);
		List<?> list = null;
		ArrayList<Object> params = new ArrayList<Object>();
		String listHQL = getListHQL(params);
		logger.debug("listHQL:" + listHQL);
		Object[] arrayParams = params.toArray();
		if (StringUtil.isEmpty(listHQL)) {
			Class<?> domainClass = ActionUtil.retireDomainClassNameFromAction(this);
			list = dao.findWithPager("from " + domainClass.getName(), pager, arrayParams);
		} else {
			list = dao.findWithPager(listHQL, pager, arrayParams);
		}

		session.put(Constants.SESSION_OBJECT, list);
		return "exportExcel";
	}

	/**
	 * 当前用户是admin
	 * @return
	 */
	/*public boolean isAdmin(){
		User currUser = this.getLoginUser();
		return (currUser!=null && JitongConstants.ADMIN.equals(currUser.getLoginName()));
	}*/
	
	public void addFieldError(String fieldName, String errorMessage){
		logger.error("addFieldError:"+fieldName+"="+errorMessage);
		super.addFieldError(fieldName, errorMessage);
	}
	public void addActionError(String errorMessage){
		logger.error("addActionError:"+errorMessage);
		super.addActionError(errorMessage);
	}
}

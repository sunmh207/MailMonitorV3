package supportnet.rule.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import supportnet.common.Constants;
import supportnet.common.JITActionBase;
import supportnet.common.exception.JTException;
import supportnet.common.service.BaseService;
import supportnet.common.util.DateUtil;
import supportnet.common.util.SearchFormUtil;
import supportnet.common.util.StringUtil;
import supportnet.common.util.SysCache;
import supportnet.rule.domain.BaseRule;
import supportnet.rule.domain.SingleMailRule;
import supportnet.rule.form.RuleSearchForm;

import com.opensymphony.xwork2.Preparable;

public class RuleAction extends JITActionBase implements Preparable {

	protected BaseService service;
	protected RuleSearchForm searchForm;
	private List<String> projectList = Constants.PROJECT_LIST;
	public void prepare() throws JTException {
		if (service == null) {
			service = new BaseService();
		}
	}

	public String list() {
		return super.list();
	}

	public String getListHQL(ArrayList<Object> params) throws JTException {
		if (searchForm == null) {
			searchForm = new RuleSearchForm();
		}
		String hqlsufix = SearchFormUtil.toHQLSuffix(searchForm, params);
		Class<?> domainClass = BaseRule.class;
		String hql = "from " + domainClass.getName() + " me " + hqlsufix;
		hql += " order by me.project, me.order ";
		return hql;
	}

	public RuleSearchForm getSearchForm() {
		return searchForm;
	}

	public void setSearchForm(RuleSearchForm searchForm) {
		this.searchForm = searchForm;
	}
	public List<String> getProjectList() {
		return projectList;
	}


	public void setProjectList(List<String> projectList) {
		this.projectList = projectList;
	}
}

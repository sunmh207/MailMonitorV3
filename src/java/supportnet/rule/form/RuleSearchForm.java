package supportnet.rule.form;

import supportnet.common.form.FieldCondition;
import supportnet.common.form.Operation;
import supportnet.common.form.SearchForm;

public class RuleSearchForm  extends SearchForm{
	private String name;
	private String project;
	
	@FieldCondition(op=Operation.like)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@FieldCondition(op=Operation.eq)
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	
}

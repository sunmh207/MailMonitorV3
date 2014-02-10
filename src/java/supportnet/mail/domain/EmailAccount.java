package supportnet.mail.domain;

import java.util.List;

import supportnet.rule.domain.BaseRule;
import supportnet.rule.domain.Schedule;

/**
 * @Entity
 * @author stanley.sun
 *
 */
public class EmailAccount {
	private String id;
	private String username;
	private String password;
	private String folder;
	
	private List<BaseRule> rules;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public List<BaseRule> getRules() {
		return rules;
	}
	public void setRules(List<BaseRule> rules) {
		this.rules = rules;
	}
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof EmailAccount) ) return false;
        final EmailAccount account = (EmailAccount) other;
        if (account.getId()==null) return false;
        if (!account.getId().equals( getId() ) ) return false;
        return true;
    }

    public int hashCode() {
    	int result;
    	if(id!=null){
    		result = id.hashCode();
    	}else{
    		result=this.hashCode();
    	}
        return result;
    }
}

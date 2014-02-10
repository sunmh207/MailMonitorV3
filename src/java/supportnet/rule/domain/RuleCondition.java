package supportnet.rule.domain;

/**
 * Create this class because Rule use  Conditon class for multiple attribute; and Hibernate Entity Mapping doesn't support it.
 * @author stanley.sun
 *
 */
public class RuleCondition extends AbstractRuleCondition{

	@Override
	public String getType() {
		return "Condition";
	}
	
}

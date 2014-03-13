package supportnet.test;


public class Client {
	public static void main(String[] args) {
		/*String configureFile = "C:/private/EclipseWorkspace/MailMonitorV3/src/resources/config.properties";
		Constants.init(configureFile);
		
		
		/////////////////////
		BaseRule r1 = new SingleMailRule();
		
		RuleCondition c = new RuleCondition();
		c.setSubjectExp("SSO");
		List<RuleCondition> conditions = new ArrayList<RuleCondition>();
		conditions.add(c);
		
		Action act1 = new SendMailAction(r1);
		List<Action> actions = new ArrayList<Action>();
		actions.add(act1);
		
		Schedule s1= new Schedule();
		s1.setDayOfWeek("6");
		
		r1.setConditions(conditions);
		r1.setActions(actions);
		r1.setSchedule(s1);
		
		//SysCache.ruleMap.put("1", r1);
		List<BaseRule> rules = new ArrayList<BaseRule>();
		rules.add(r1);
		EmailAccount account1 = new EmailAccount("Test_Supportnet_Automation@perficient.com","Duqub8te",null);
		account1.setRules(rules);
		
		List<EmailAccount> accounts = new ArrayList<EmailAccount>();
		accounts.add(account1);
		//////////////////////
		int seconds = Constants.seconds;
		
		for(EmailAccount account:accounts){
			StoreConnector connector=new  StoreConnector(account);
			RuleProcessor processor = new RuleProcessor(seconds,connector);
			processor.start();
		}*/
	}
}

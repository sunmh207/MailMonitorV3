package supportnet.common.action;

import org.apache.log4j.Logger;

import supportnet.common.JITActionBase;



public class WelcomeAction extends JITActionBase {
	private static Logger logger = Logger.getLogger(WelcomeAction.class);
	@Override
	public String execute() throws Exception {
		logger.debug("in welcome action.");
		logger.debug(this.session);
		return SUCCESS;
	}
}

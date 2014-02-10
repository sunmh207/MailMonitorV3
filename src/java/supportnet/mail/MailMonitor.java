package supportnet.mail;

import supportnet.common.Constants;


/**
 * Monitors a given mailbox for new mail and maps the messages to bean objects
 * that are forwarded to a processor.
 */
public class MailMonitor {
	
	/*public static void main(String[] args) {
		String configureFile = "config.properties";
		if (args != null) {
			int length = args.length;
			if (length == 1) {
				configureFile = args[0];
			} else if (length == 0) {
			} else {
				System.exit(0);
			}
		}
		Constants.init(configureFile);
		
		int seconds = Constants.seconds;
		StoreConnector connector=new  DefaultStoreConnector(Constants.MAIL_MONITOR_USERNAME,  Constants.MAIL_MONITOR_PASSWORD,  "INBOX");
		
		new MailMonitor(seconds, connector).start();
		//StatusReportJob.start();//send Report Status mail near switch time
	}*/
}
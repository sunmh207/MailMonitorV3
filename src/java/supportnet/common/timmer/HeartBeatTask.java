package supportnet.common.timmer;

import java.util.Timer;
import java.util.TimerTask;

import supportnet.common.util.InfoHandler;
import supportnet.mail.MailSender;
import supportnet.mail.domain.EmailAccount;

public class HeartBeatTask extends TimerTask {

	@Override
	public void run() {

		String subject="I am alive";
		String body="This is my heart beat email. If you haven't received it in 30 minutes, that means I am dead.";
		String to="perficient.apex@gmail.com";
		String cc =null;
		EmailAccount emailAccount = new EmailAccount();
		emailAccount.setUsername(supportnet.common.Constants.DEFAULT_MAIL_SENDER_USERNAME);
		emailAccount.setPassword(supportnet.common.Constants.DEFAULT_MAIL_SENDER_PASSWORD);
		
		MailSender.sendMail(subject,body,to,cc,emailAccount); 
		InfoHandler.info("HeartBeatTask: send mail out! subject="+subject);
	}

	
	public static void start(){
		new Timer().schedule(new HeartBeatTask(), 0, 10*60*1000);//run every 10 mins
	}
}

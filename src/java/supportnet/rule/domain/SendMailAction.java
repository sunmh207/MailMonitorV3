package supportnet.rule.domain;

import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ServiceLocalException;
import supportnet.common.util.InfoHandler;
import supportnet.mail.MailSender;

public class SendMailAction extends BaseAction{
	
	private String to;
	private String cc;
	private String subject;
	private String body;
	@Override
	public void doAction(EmailMessage email) {
		MailSender.sendMail(subject,body,to,cc,rule.getEmailAccount()); 
		InfoHandler.info("SendMailAction.doAction: send mail out! subject="+subject);
		/*try {
			System.out.println("Action: Received email:"+email.getSubject());
		} catch (ServiceLocalException e) {
			e.printStackTrace();
			InfoHandler.error("doAction encountered errors", e);
		}*/
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

}

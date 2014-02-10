package supportnet.mail;

import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.WebCredentials;
import supportnet.common.util.InfoHandler;
import supportnet.common.util.StringUtil;
import supportnet.mail.domain.EmailAccount;

public class MailSender {
	public static void sendMail(String subject, String body, String mailToList, String mailCCList, EmailAccount emailAccount) {
		try {
			ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
			ExchangeCredentials credentials = new WebCredentials(emailAccount.getUsername(), emailAccount.getPassword());
			service.setCredentials(credentials);
			service.autodiscoverUrl(emailAccount.getUsername());
			service.setTraceEnabled(true);
			EmailMessage msg = new EmailMessage(service);
			msg.setSubject(subject);
			msg.setBody(MessageBody.getMessageBodyFromText(body));

			if (!StringUtil.isEmpty(mailToList)) {
				String[] mailToArray = mailToList.split(";");
				for (String mailto : mailToArray) {
					msg.getToRecipients().add(mailto);
				}
			}
			if (!StringUtil.isEmpty(mailCCList)) {
				String[] mailCCArray = mailCCList.split(";");
				for (String mailcc : mailCCArray) {
					msg.getCcRecipients().add(mailcc);
				}
			}

			msg.send();

			InfoHandler.info("message sent.");
		} catch (Exception e) {
			InfoHandler.error("sendMail encountered errors!",e);
		}
	}

}

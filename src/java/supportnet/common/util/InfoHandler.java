package supportnet.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class InfoHandler {
	private static org.apache.log4j.Logger logger =  org.apache.log4j.Logger.getLogger(InfoHandler.class);
	
	public static void debug(String msg){
		logger.debug(msg);
	}
	public static void info(String msg){
		logger.info(msg);
	}
	public static void warn(String msg){
		logger.warn(msg);
	}
	
	/**
	 * TODO
	 * @param msg
	 */
	public static void error(String msg){
		logger.error(msg);
	}
	/**
	 * TODO
	 * @param msg
	 * @param e
	 */
	public static void error(String msg, Throwable e){
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		String errorStackTrace = writer.toString();
		logger.error(errorStackTrace);
		//sendCriticalErrorEmail(msg+"\n"+errorStackTrace);
	}
	/*private static void sendCriticalErrorEmail(String msg){
		logger.error("******** Sending alert mail...:[MailMonitor encountered an error] " + msg);
		try{
			alert("MailMonitor encountered an error",msg,"stanley.sun@perficient.com",null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void alert(String subject, String body,String mailToList, String mailCCList){
		new MailSender().sendMail(subject,body,mailToList,mailCCList);
	}*/
}

package supportnet.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import supportnet.common.util.InfoHandler;

public class Constants {
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final int MAX_PAGE_SIZE = 500;
	public static final String SESSION_OBJECT = "SESSION_OBJECT";

	private static boolean hasInit = false;
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(InfoHandler.class);
	public static Properties properties = null;
	private static String configureFile=null;
	
	
	public static String DEFAULT_MAIL_SENDER_USERNAME = "Test_Supportnet_Automation@perficient.com";
	public static String DEFAULT_MAIL_SENDER_PASSWORD = "Duqub8te";
	public static int seconds = 10;
	//public static String statusReportCron = "0 50 5,13,21 * * ?";
	public static List<String> PROJECT_LIST = new ArrayList<String>();

	public static void loadProps(String file) {
		configureFile=file;
		properties = new Properties();
		InputStream in = null;
		try {
			logger.info("Read config.properties ...");
			if (configureFile == null) {
				in = Constants.class.getResourceAsStream("/config.properties");
			} else {
				in = new FileInputStream(new File(configureFile));
			}
			properties.load(in);
			String secondsStr = getProp("Seconds", "30");
			seconds = Integer.parseInt(secondsStr);
			DEFAULT_MAIL_SENDER_USERNAME = getProp("DEFAULT_MAIL_SENDER_USERNAME", "Test_Supportnet_Automation@perficient.com");
			DEFAULT_MAIL_SENDER_PASSWORD = getProp("DEFAULT_MAIL_SENDER_PASSWORD", "Duqub8te");

			//statusReportCron = getProp("statusReportCron", "0 50 5,13,21 * * ?");

			PROJECT_LIST = new ArrayList<String>();
			String projectStr = getProp("Projects", "CAT;Greyhound;Bunge");
			String[] projects = projectStr.split(";");
			for (String p : projects) {
				PROJECT_LIST.add(p);
			}

			logger.info("Read config.properties ...[done]");
		} catch (Exception ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveProps() {
		try {
			OutputStream fos = null;
			if (configureFile == null) {
				fos = new FileOutputStream("config.properties");
			} else {
				fos = new FileOutputStream(configureFile);
			}
			properties.store(fos, "Updated value");
		} catch (IOException e) {
			System.err.println("Visit " + configureFile + " for updating value error");
		}
	}
	

	public static String getProp(String name, String defaultValue) {
		if (properties == null) {
			return null;
		}
		String property = properties.getProperty(name);
		if (property == null) {
			return defaultValue;
		}
		return property.trim();
	}

	public static void init(String configureFile) {
		if (!hasInit) {
			loadProps(configureFile);
			hasInit = true;
		}
	}
	
	public static void main(String[] args)throws Exception{
		loadProps(null);
		String s=getProp("seconds","0");
		System.out.println(s);
		properties.setProperty("seconds","100");
		saveProps();
	}

}

package supportnet.common;

import java.util.List;

import javax.servlet.ServletConfig;

import supportnet.common.util.DateUtil;
import supportnet.common.util.InfoHandler;
import supportnet.mail.RuleProcessor;
import supportnet.mail.StoreConnector;
import supportnet.mail.domain.EmailAccount;
import supportnet.mail.service.EmailAccountService;
import supportnet.rule.domain.BaseRule;

public class CacheServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	public void init(ServletConfig config) {
		loadData();
	}

	private void loadData() {
		System.out.println("Start to load system data to cache ... "+DateUtil.getCurrentTime("yyyy-MM-dd hh:mm:ss"));
		try {
			Constants.init(null); 
			//SysCache.loadSysCache();
			
			EmailAccountService accountService = new EmailAccountService();
			List<EmailAccount> accounts = accountService.queryAllEmailAccounts();
			int seconds = Constants.seconds;
			
			for(EmailAccount account:accounts){
				InfoHandler.info("=== === ===EMail:"+account.getUsername()+"/"+account.getFolder());
				List<BaseRule> rules =account.getRules();
				for(BaseRule rule: rules){
					if(rule!=null&&rule.isActive()){
						rule.init();
						InfoHandler.info("=== === === === Rule initialized:"+rule.getName());
					}
				}
				StoreConnector connector=new  StoreConnector(account);
				RuleProcessor processor = new RuleProcessor(seconds,connector);
				processor.start();
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
			InfoHandler.error("Load Data error!",e);
		}
		System.out.println("Load system data to cache end. "+DateUtil.getCurrentTime("yyyy-MM-dd hh:mm:ss"));
	}
}

package supportnet.mail;

import java.util.List;

import microsoft.exchange.webservices.data.DeleteMode;
import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;
import microsoft.exchange.webservices.data.FindFoldersResults;
import microsoft.exchange.webservices.data.FindItemsResults;
import microsoft.exchange.webservices.data.Folder;
import microsoft.exchange.webservices.data.FolderView;
import microsoft.exchange.webservices.data.Item;
import microsoft.exchange.webservices.data.ItemView;
import microsoft.exchange.webservices.data.PropertySet;
import microsoft.exchange.webservices.data.WebCredentials;
import microsoft.exchange.webservices.data.WellKnownFolderName;
import supportnet.common.util.InfoHandler;
import supportnet.mail.domain.EmailAccount;
import supportnet.rule.domain.BaseRule;

public class StoreConnector  {

	private ExchangeService service=null;
	private EmailAccount emailAccount=null;
	private Folder folder=null;
	
	private boolean connected=false;
	/**
	 * Don't allow to new an instance without parameter
	 */
	private StoreConnector() {
		super();
	}

	public StoreConnector(EmailAccount emailAccount) {
		this.emailAccount = emailAccount;
	}

	public boolean connect() {
		InfoHandler.info("Try to connect to "+emailAccount.getUsername());
		try {
			service =new ExchangeService(ExchangeVersion.Exchange2010_SP2);
			service.setCredentials(new WebCredentials(emailAccount.getUsername(), emailAccount.getPassword()));
			service.autodiscoverUrl(emailAccount.getUsername());
			service.setTraceEnabled(false);
			
			if(emailAccount.getFolder()==null||emailAccount.getFolder()==""||"INBOX".equalsIgnoreCase(emailAccount.getFolder())){
				folder = Folder.bind(service,WellKnownFolderName.Inbox);
			}else{
				FindFoldersResults findFolderResults = service.findFolders(WellKnownFolderName.Inbox,new FolderView(Integer.MAX_VALUE));
				for(Folder f:findFolderResults.getFolders()){
					if(f.getDisplayName().equals(emailAccount.getFolder())){
						folder = f;
						break;
					}
				}
				InfoHandler.error("Cannot find folder: "+emailAccount.getUsername()+"/"+emailAccount.getFolder()); 
				return false;
			}
			InfoHandler.info("Connected to "+emailAccount.getUsername()+"/"+emailAccount.getFolder()+" successfully!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void process() {
		try {
			ItemView view = new ItemView(Integer.MAX_VALUE);
			FindItemsResults<Item> findResults;
			do {
				findResults = service.findItems(folder.getId(), view);
				if(!findResults.getItems().isEmpty()){
					service.loadPropertiesForItems(findResults.getItems(), PropertySet.FirstClassProperties);
				}
				List<Item> items = findResults.getItems();
				for (int i = 0; i < items.size(); i++) {
					Item item = items.get(i);
					if (item instanceof EmailMessage) {
						EmailMessage email = (EmailMessage) item;
						InfoHandler.info("EmailMessage["+i+"]:<<" + email.getSubject() + ">>");
						processRules(email);
						email.delete(DeleteMode.HardDelete);
					} else {
						InfoHandler.info("Item[i]:<<" + item.getSubject() + ">>");
					}
				}
			} while (findResults.isMoreAvailable());

		} catch (Exception e) {
			InfoHandler.error("process error", e);
		}
	}

	private void processRules(EmailMessage email) {
		BaseRule rule=null;
		try {
			if (email == null || email.getSubject() == null) {
				return;
			}
			List<BaseRule> rules=emailAccount.getRules();
			for (int i=0;i<rules.size();i++) {
				rule = rules.get(i);
				if(rule!=null&&rule.isActive()){
					rule.execute(email);
				}
			}
		} catch (Exception e) {
			InfoHandler.error("process Rule <"+rule==null?"":rule.getName()+"> error", e);
		}
	}




}

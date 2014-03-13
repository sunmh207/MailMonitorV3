package supportnet.mail;

import supportnet.common.util.InfoHandler;
import supportnet.mail.domain.EmailAccount;

public class RuleProcessor {
	protected int seconds;
	protected StoreConnector connector;

	// Monitor state
	private Thread thread;
	private boolean interruptionRequest;

	private RuleProcessor() {
	}

	public RuleProcessor(int seconds, StoreConnector connector) {
		thread = null;
		interruptionRequest = false;
		this.seconds = seconds;
		this.connector = connector;
	}

	/**
	 * If the connect() fails, this method brings the monitor in its initial
	 * state. Otherwise it calls process() in a loop.
	 */

	protected void loop() {
		boolean connected = connector.connect();
		if (!connected) {
			// Do the cleaning even if the connection failed
			synchronized (this) {
				thread = null;
				interruptionRequest = false;
			}
		} else {
			InfoHandler.info("[CONNECTED]");
			int milisec = seconds * 1000;
			while (true) {
				InfoHandler.info("[BEGIN_PROCESSING]");
				// Get and process the new messages
				connector.process();
				InfoHandler.info("[END_PROCESSING]");
				synchronized (this) {
					if (!interruptionRequest)
						try {
							// This is called instead of Thread.sleep()
							// It has the advantage that it can be interrupted
							// by notifyAll().
							wait(milisec);
						} catch (InterruptedException e) {
							// This exception shouldn't occur
							// because thread.interrupt() isn't used
							InfoHandler.error(null, e);
						}
					if (interruptionRequest) {
						InfoHandler.info("[DISCONNECTED]");
						thread = null;
						break;
					}
				}
			}
		}
	}

	/**
	 * Creates and starts the monitor's thread whose run() method calls loop().
	 * If the monitor's thread was already started, this method makes sure that
	 * the thread will continue its execution even if an interruption was
	 * requested by stop().
	 */
	public synchronized void start() {
		if (thread == null) {
			thread = new Thread() {
				public void run() {
					loop();
				}
			};
			interruptionRequest = false;
			EmailAccount emailAccount=connector.getEmailAccount();
			String emailAddress=emailAccount==null?"":emailAccount.getUsername();
			String folder=emailAccount==null?"":emailAccount.getFolder();
			String id=emailAccount==null?"":emailAccount.getId();
			InfoHandler.info("[THREAD_START]:"+emailAddress+"/"+folder+"("+id+")");
			thread.start();
		} else if (interruptionRequest) {
			InfoHandler.info("[THREAD_CANCEL_INTERRUPTION_REQUEST]");
			interruptionRequest = false;
		}
	}
	
	/**
	 * Requests the monitor's thread to interrupt itself. This is done by setting the interruptionRequest flag to true. The thread.interrupt() call
	 * wouldn't have been safe since it can interrupt any method that runs
	 * within the monitor's thread, including process(), which does I/O that may
	 * throw InterruptedIOException That's why the mail monitor uses the
	 * interruptionRequest flag.
	 */
	public synchronized void stop() {
		if (thread != null && !interruptionRequest) {
			InfoHandler.info("[THREAD_INTERRUPTION_REQUEST]");
			interruptionRequest = true;
			notifyAll();
		}
	}
	
}

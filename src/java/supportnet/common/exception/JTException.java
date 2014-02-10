package supportnet.common.exception;

import org.apache.log4j.Logger;

public class JTException extends Exception {

	private String message;
	private boolean isErrorMessage = true;

	public JTException() {

	}

	public JTException(String infoMesssge, Class cl) {
		if (null != cl) {
			if (null == infoMesssge)
				infoMesssge = "消息内容为空";

			message = infoMesssge;
			this.isErrorMessage = false;
			Logger logger = Logger.getLogger(cl);
			logger.info(infoMesssge);
		}
	}

	public JTException(String errorMessage, Exception exception, Class cl) {
		super(exception);
		if (null != cl) {
			Logger logger = Logger.getLogger(cl);
			if (null == errorMessage)
				errorMessage = "错误";

			message = errorMessage;
			

			logger.error(errorMessage);
			if (null != exception) {
				logger.error(exception.toString());
				StackTraceElement[] trace = this.getCause().getStackTrace();
				for (int i = 0; i < trace.length; i++)
					logger.error("\tat " + trace[i]);

				// 当日志中记录的错误信息不足以分析出错误原因.需要在这里追加引起exception"causedException"的错误堆栈输出.
			}
		}
	}

	public String getMessage() {
		return this.message;
	}

	public boolean getIsErrorMessage() {
		return this.isErrorMessage;
	}
}

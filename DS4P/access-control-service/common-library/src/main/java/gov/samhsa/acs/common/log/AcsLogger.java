package gov.samhsa.acs.common.log;

import org.slf4j.Logger;

public class AcsLogger {
	
	private Logger logger;	

	public AcsLogger(Logger logger) {
		super();
		this.logger = logger;
	}

	public Logger getLogger() {
		return logger;
	}
	
	public void debug(String messageId, String message){
		String logMessage = createLogMessage(messageId, message);
		logger.debug(logMessage);
	}
	
	public void info(String messageId, String message){
		String logMessage = createLogMessage(messageId, message);
		logger.info(logMessage);
	}
	
	public void warn(String messageId, String message){
		String logMessage = createLogMessage(messageId, message);
		logger.warn(logMessage);
	}
	
	public void warn(String messageId, String message, Throwable expection){
		String logMessage = createLogMessage(messageId, message);
		logger.warn(logMessage, expection);
	}
	
	public void error(String messageId, String message){
		String logMessage = createLogMessage(messageId, message);
		logger.error(logMessage);
	}
	
	public void error(String messageId, String message, Throwable expection){
		String logMessage = createLogMessage(messageId, message);
		logger.error(logMessage, expection);
	}

	private String createLogMessage(String messageId, String message) {
		StringBuilder builder = new StringBuilder();
		builder.append(messageId);
		builder.append(" : ");
		builder.append(message);
		String logMessage = builder.toString();
		return logMessage;
	}
}

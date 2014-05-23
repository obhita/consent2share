/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *   
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.common.log;

import org.slf4j.Logger;

/**
 * The Class AcsLogger.
 */
public class AcsLogger {

	/** The logger. */
	private Logger logger;

	/**
	 * Instantiates a new acs logger.
	 * 
	 * @param logger
	 *            the logger
	 */
	public AcsLogger(Logger logger) {
		super();
		this.logger = logger;
	}

	/**
	 * Gets the logger.
	 * 
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * Debug.
	 * 
	 * @param messageId
	 *            the message id
	 * @param message
	 *            the message
	 */
	public void debug(String messageId, String message) {
		String logMessage = createLogMessage(messageId, message);
		logger.debug(logMessage);
	}

	/**
	 * Debug.
	 * 
	 * @param messageId
	 *            the message id
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 */
	public void debug(String messageId, String message, Throwable exception) {
		String logMessage = createLogMessage(messageId, message);
		logger.debug(logMessage, exception);
	}

	/**
	 * Info.
	 * 
	 * @param messageId
	 *            the message id
	 * @param message
	 *            the message
	 */
	public void info(String messageId, String message) {
		String logMessage = createLogMessage(messageId, message);
		logger.info(logMessage);
	}

	/**
	 * Warn.
	 * 
	 * @param messageId
	 *            the message id
	 * @param message
	 *            the message
	 */
	public void warn(String messageId, String message) {
		String logMessage = createLogMessage(messageId, message);
		logger.warn(logMessage);
	}

	/**
	 * Warn.
	 * 
	 * @param messageId
	 *            the message id
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 */
	public void warn(String messageId, String message, Throwable exception) {
		String logMessage = createLogMessage(messageId, message);
		logger.warn(logMessage, exception);
	}

	/**
	 * Error.
	 * 
	 * @param messageId
	 *            the message id
	 * @param message
	 *            the message
	 */
	public void error(String messageId, String message) {
		String logMessage = createLogMessage(messageId, message);
		logger.error(logMessage);
	}

	/**
	 * Error.
	 * 
	 * @param messageId
	 *            the message id
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 */
	public void error(String messageId, String message, Throwable exception) {
		String logMessage = createLogMessage(messageId, message);
		logger.error(logMessage, exception);
	}

	/**
	 * Creates the log message.
	 * 
	 * @param messageId
	 *            the message id
	 * @param message
	 *            the message
	 * @return the string
	 */
	private String createLogMessage(String messageId, String message) {
		StringBuilder builder = new StringBuilder();
		builder.append(messageId);
		builder.append(" : ");
		builder.append(message);
		String logMessage = builder.toString();
		return logMessage;
	}
}

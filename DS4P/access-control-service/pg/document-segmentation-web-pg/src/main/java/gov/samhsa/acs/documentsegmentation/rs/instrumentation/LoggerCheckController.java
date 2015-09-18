package gov.samhsa.acs.documentsegmentation.rs.instrumentation;

import gov.samhsa.acs.common.cxf.CXFLoggingConfigurer;

import java.util.Optional;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService
@Path("/instrumentation")
@Produces(MediaType.TEXT_HTML)
public class LoggerCheckController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String loggerInstrumentationKey;
	private final Optional<CXFLoggingConfigurer> cxfLoggingConfigurer;

	public LoggerCheckController(String loggerInstrumentationKey,
			Optional<CXFLoggingConfigurer> cxfLoggingConfigurer) {
		super();
		this.loggerInstrumentationKey = loggerInstrumentationKey;
		this.cxfLoggingConfigurer = cxfLoggingConfigurer;
	}

	@GET
	@Path("/loggerCheck")
	@WebMethod
	public String check(@QueryParam("instrumentationKey") String key) {

		if (!loggerInstrumentationKey.equals(key)) {
			return "<h2>You are not authorized to access this page.</h2>";
		}

		// The following loops are used to make
		// ch.qos.logback.classic.turbo.ReconfigureOnChangeFilter be alive to
		// scan logback configuration changes if logback is used
		// You may request this url several times to activate the configuration
		// changes
		for (int i = 0; i < 200; i++) {
			logger.trace("trace: just a test.");
		}

		logger.debug("debug: just a test.");
		logger.info("info: just a test.");
		logger.warn("warn: just a test.");
		logger.error("error: just a test.");

		String loggerLevel;

		if (logger.isDebugEnabled()) {
			loggerLevel = "Debug";
		} else if (logger.isInfoEnabled()) {
			loggerLevel = "Info";
		} else if (logger.isWarnEnabled()) {
			loggerLevel = "Warn";
		} else {
			loggerLevel = "Error";
		}

		return new StringBuilder(
				"<p>This page is used for logging test. And if logback is the logging library, you can request this page serveral times to activate logback configuration changes.</p><hr/>")
				.append("<h3>Logger named [").append(logger.getName())
				.append("]</h3>\r\n ").append("<h3>Logger Level is [")
				.append(loggerLevel).append("]</h3>").toString();

	}

	@PUT
	@Path("/cxfLoggingInterceptors/enabled/{enabled}")
	@WebMethod
	public String loggingInterceptors(
			@QueryParam("instrumentationKey") String key,
			@PathParam("enabled") boolean enabled) {
		this.cxfLoggingConfigurer.ifPresent(cxf -> cxf
				.setEnableLoggingInterceptors(enabled));
		return new StringBuilder()
				.append(check(key))
				.append("<h3>cxfLoggingConfigurer: ")
				.append(this.cxfLoggingConfigurer)
				.append("</h3><h3>CXF Logging Interceptors enabled: ")
				.append(this.cxfLoggingConfigurer.map(
						CXFLoggingConfigurer::isEnableLoggingInterceptors)
						.orElse(null)).append("</h3>").toString();
	}

}

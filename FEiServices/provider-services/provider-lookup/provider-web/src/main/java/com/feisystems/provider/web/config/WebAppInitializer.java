package com.feisystems.provider.web.config;

import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.feisystems.provider.web.config.di.AppConfig;

/** WebApplicationInitializer is used to replace web.xml in Servlet 3.0+ environments like Tomcat 7.0 and above.
 *
 * <p>One application could have multiple WebApplicationInitializer implementations
 * which can be used to partition configurations and as well as the old web.xml.
 *
 * <p>One application could have one root web application context which associates with the webapp and
 * multiple servlet application contexts each of which defines the beans for one Spring servlet's app context.
 *
 * <p>The root web application context is loaded by org.springframework.web.context.ContextLoaderListener.
 * <br>servlet application context is loaded by org.springframework.web.servlet.FrameworkServlet which is the parent class of org.springframework.web.servlet.DispatcherServlet.
 *
 * <p>All Spring MVC controllers must go in the servlet application context.
 * <br>Beans in servlet application contexts can reference beans in root web application context, but not vice versa.
 *
 * <p>In most simple cases, the root application context is unnecessary.
 * It is generally used to contain beans that are shared between all servlets and filters in a webapp.
 * If you only have one servlet, then there's not really much point, unless you have a specific use for it.
 *
 */

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String SERVLET_NAME = "npiservlet";

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		logger.debug("getServletConfigClasses is called.");
		return new Class<?>[] { AppConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		// Map Servlet to "/"
		return new String[] { "/" };
	}

	@Override
	protected String getServletName() {
		logger.debug("Servlet Name: " + SERVLET_NAME);
		return SERVLET_NAME;
	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		super.customizeRegistration(registration);
	}
}

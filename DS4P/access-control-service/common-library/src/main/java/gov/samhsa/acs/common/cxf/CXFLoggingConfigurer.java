package gov.samhsa.acs.common.cxf;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.xml.ws.Endpoint;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.spring.EndpointDefinitionParser.SpringEndpointImpl;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CXFLoggingConfigurer {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(CXFLoggingConfigurer.class);

	private static final String ADD = "Adding";
	private static final String REMOVE = "Removing";

	private boolean enableLoggingInterceptors;
	private final Optional<SpringEndpointImpl> endpoint;
	private final Optional<Client> client;

	public CXFLoggingConfigurer(Client client) {
		this.endpoint = Optional.empty();
		this.client = Optional.of(client);
	}

	public CXFLoggingConfigurer(Endpoint endpoint) {
		if (!(endpoint instanceof SpringEndpointImpl)) {
			final String error = "Endpoint is not an instance of org.apache.cxf.jaxws.spring.EndpointDefinitionParser.SpringEndpointImpl";
			LOGGER.error(error);
			throw new CXFLoggingConfigurerException(error);
		}
		this.endpoint = Optional.of((SpringEndpointImpl) endpoint);
		this.client = Optional.empty();
		this.enableLoggingInterceptors = false;
	}

	public boolean isEnableLoggingInterceptors() {
		return this.enableLoggingInterceptors;
	}

	public synchronized void setEnableLoggingInterceptors(
			boolean enableLoggingInterceptors) {
		this.endpoint.ifPresent(ep -> configureInterceptors(
				ep::getInInterceptors, ep::getOutInterceptors,
				serviceNameWithInvokingInstance(ep, this),
				enableLoggingInterceptors));
		this.client.ifPresent(client -> configureInterceptors(
				client::getInInterceptors, client::getOutInterceptors,
				serviceNameWithInvokingInstance(client, this),
				enableLoggingInterceptors));
		this.enableLoggingInterceptors = enableLoggingInterceptors;
	}

	public static final void configureInterceptors(Client client,
			boolean enableLoggingInterceptors) {
		configureInterceptors(client::getInInterceptors,
				client::getOutInterceptors, client.getEndpoint().getService()
						.getName()::toString, enableLoggingInterceptors);
	}

	public static final void configureInterceptors(Client client,
			Supplier<String> serviceName, boolean enableLoggingInterceptors) {
		configureInterceptors(client::getInInterceptors,
				client::getOutInterceptors, serviceName,
				enableLoggingInterceptors);
	}

	public static final void configureInterceptors(
			Supplier<List<Interceptor<? extends Message>>> inInterceptors,
			Supplier<List<Interceptor<? extends Message>>> outInterceptors,
			Supplier<String> serviceName, boolean enableLoggingInterceptors) {
		if (enableLoggingInterceptors) {
			LOGGER.info("Enabling CXF logging interceptors at "
					+ serviceName.get());
			if (inInterceptors
					.get()
					.stream()
					.noneMatch(
							interceptor -> interceptor instanceof LoggingInInterceptor)) {
				final LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
				byLoggingDebug(ADD).accept(loggingInInterceptor);
				inInterceptors.get().add(loggingInInterceptor);
			}
			if (outInterceptors
					.get()
					.stream()
					.noneMatch(
							interceptor -> interceptor instanceof LoggingOutInterceptor)) {
				final LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
				byLoggingDebug(ADD).accept(loggingOutInterceptor);
				outInterceptors.get().add(loggingOutInterceptor);
			}
		} else {
			LOGGER.info("Disabling CXF logging interceptors at "
					+ serviceName.get());
			inInterceptors.get().stream()
					.filter(by(LoggingInInterceptor.class))
					.peek(byLoggingDebug(REMOVE))
					.forEach(inInterceptors.get()::remove);
			outInterceptors.get().stream()
					.filter(by(LoggingOutInterceptor.class))
					.peek(byLoggingDebug(REMOVE))
					.forEach(outInterceptors.get()::remove);
		}
	}

	public static final Supplier<String> serviceNameWithInvokingInstance(
			Client client, Object invokingInstance) {
		return serviceNameWithInvokingInstance(client.getEndpoint()
				.getService().getName()::toString, invokingInstance);
	}

	public static final Supplier<String> serviceNameWithInvokingInstance(
			SpringEndpointImpl endpoint, Object invokingInstance) {
		return serviceNameWithInvokingInstance(
				endpoint.getServiceName()::toString, invokingInstance);
	}

	private static final <T> Predicate<? super Interceptor<? extends Message>> by(
			Class<T> clazz) {
		return interceptor -> interceptor.getClass() == clazz;
	}

	private static final Consumer<? super Interceptor<? extends Message>> byLoggingDebug(
			String logMsgPrefix) {
		return interceptor -> LOGGER.debug(new StringBuilder()
				.append(logMsgPrefix).append(" - ")
				.append(interceptor.getClass().toString()).toString());
	}

	private static final Supplier<String> serviceNameWithInvokingInstance(
			Supplier<String> serviceName, Object invokingInstance) {
		return () -> new StringBuilder().append(serviceName.get())
				.append("; invoking instance: ")
				.append(invokingInstance.toString()).toString();
	}
}

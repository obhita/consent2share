package gov.samhsa.consent2share.domain.domainevent;

import gov.samhsa.consent2share.infrastructure.domainevent.handler.Handler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ServiceLoader;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component(value="domainEvents")
public class DomainEvents implements ApplicationContextAware{
	private Set<Handler> handlers;
	private ApplicationContext applicationContext;

	public void raise(DomainEvent args){
		for (Handler handler:handlers){
			handler.handle(args);
		}
	}
	
	public void addHandler(Handler handler){
		if (handlers==null)
			handlers= new HashSet<Handler>();
		handlers.add(handler);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
		handlers=new HashSet<Handler>();
		Iterator<Entry<String, Handler>> iterator= applicationContext.getBeansOfType(Handler.class).entrySet().iterator();
		
		while (iterator.hasNext()){
			handlers.add(iterator.next().getValue());
		}
		
	}
}

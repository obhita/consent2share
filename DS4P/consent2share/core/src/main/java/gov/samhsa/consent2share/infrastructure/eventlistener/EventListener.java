// EventListener.java
package gov.samhsa.consent2share.infrastructure.eventlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class EventListener implements InitializingBean {

   Logger log = LoggerFactory.getLogger(this.getClass());

   @Autowired
   EventDispatcher eventDispatcher;

   /* (non-Javadoc)
    * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
    */
   public void afterPropertiesSet() throws Exception {
       eventDispatcher.registerListener(this);
   }

   /**
    * Can handle.
    *
    * @param event the event
    * @return true, if successful
    */
   public abstract boolean canHandle(Object event);

   /**
    * Handle.
    *
    * @param event the event
    */
   public abstract void handle(Object event);
}

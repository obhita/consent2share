package gov.samhsa.acs.pep.aspect;

import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Configurable
public class LoggingAspect {

	/** The logger. */
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	/** The marshaller. */
	private SimpleMarshaller marshaller;
	
	@Autowired
	private AspectUtils aspectUtils;
	
	public SimpleMarshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(SimpleMarshaller marshaller) {
		this.marshaller = marshaller;
	}

	public AspectUtils getAspectUtils() {
		return aspectUtils;
	}

	public void setAspectUtils(AspectUtils aspectUtils) {
		this.aspectUtils = aspectUtils;
	}

	@Around("( allContextHandlerClassMethods() || loggableAnnotation() || allPepMethods()) && ( ! noLogAnnotation())")
	public Object logAndExecute(ProceedingJoinPoint joinPoint) throws Throwable {
		if (null == aspectUtils) {
			if(null == marshaller)
				marshaller = new SimpleMarshallerImpl();
			
			aspectUtils = new AspectUtils(marshaller);
		}
		aspectUtils.beforeMethod(joinPoint);

		long startNanos = System.nanoTime();
		Object result = joinPoint.proceed();
		long stopNanos = System.nanoTime();
		long lengthMillis = TimeUnit.NANOSECONDS.toMillis(stopNanos
				- startNanos);

		aspectUtils.afterMethod(joinPoint, result, lengthMillis);

		return result;
	}

	// The advice is what you want to do, the pointcut is where you want to do 
	// it and execution is for methods
	@Pointcut("execution(* gov.samhsa.acs.pep.*.*(..))")
	public void allPepMethods() {
	}

	@Pointcut("@annotation(gov.samhsa.acs.common.log.Loggable)")
	public void loggableAnnotation() {
	}
	
	//wihtin is for class
	@Pointcut("execution(* gov.samhsa.acs.contexthandler.ContextHandlerImpl.*(..))")
	public void allContextHandlerClassMethods() {}
	
	@Pointcut("@annotation(gov.samhsa.acs.common.log.NO_LOG)")
	public void noLogAnnotation(){}

}

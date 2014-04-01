package gov.samhsa.acs.common.log;

import org.slf4j.LoggerFactory;

public class AcsLoggerFactory {
	
	public static AcsLogger getLogger(Object target){
		return new AcsLogger(LoggerFactory.getLogger(target.getClass()));
	}
	
	@SuppressWarnings("rawtypes")
	public static AcsLogger getLogger(Class clazz){
		return new AcsLogger(LoggerFactory.getLogger(clazz));
	}
}

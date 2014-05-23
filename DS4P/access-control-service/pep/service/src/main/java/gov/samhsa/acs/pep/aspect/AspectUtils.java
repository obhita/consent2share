package gov.samhsa.acs.pep.aspect;

import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AspectUtils {

	/** The marshaller. */
	@Autowired
	private SimpleMarshaller marshaller;
	
	public AspectUtils(){
		
	}
	
	public AspectUtils(SimpleMarshaller marshaller){
		
		this.marshaller = marshaller;
	}
	
	public void beforeMethod(JoinPoint joinPoint) {
		CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

	//	Class<?> clazz = codeSignature.getDeclaringType();
		String methodName = codeSignature.getName();
		//  Parameter names won't be available if the advised method is implementing an interface
        //  because java does NOT store parameter names for interfaces - ever!!!
		String[] parameterNames = codeSignature.getParameterNames();
		Object[] parameterValues = joinPoint.getArgs();

		StringBuilder builder = new StringBuilder();
		builder.append(methodName).append('(');
		if (null != parameterNames && parameterNames.length > 0)
			logParamValues(builder, parameterNames, parameterValues);
		builder.append(") Started ");
		
		LoggerFactory.getLogger(joinPoint.getTarget().getClass()).info(builder.toString());
	}

	public void afterMethod(JoinPoint joinPoint, Object result,
			long lengthMillis) {
		Signature signature = joinPoint.getSignature();

		//Class<?> clazz = signature.getDeclaringType();
		String methodName = signature.getName();
		boolean hasReturnType = signature instanceof MethodSignature
				&& ((MethodSignature) signature).getReturnType() != void.class;

		StringBuilder builder = new StringBuilder();
	//	builder.append(asTag(clazz)).append(':');
		builder.append(methodName).append("() Ended [").append(lengthMillis).append("ms]");
		LoggerFactory.getLogger(joinPoint.getTarget().getClass()).info(builder.toString());
		
		if (hasReturnType) {
			builder.append(" = ");
			builder.append(toString(result));
		}

		LoggerFactory.getLogger(joinPoint.getTarget().getClass()).debug(builder.toString());
	}

	public StringBuilder logParamValues(StringBuilder logLine,
			String[] paramNames, Object[] paramValues) {
		for (int i = 0; i < paramValues.length; i++) {
			if (null != paramNames && paramNames.length > 0)
				logLine.append(paramNames[i]).append("=");
			logLine.append(paramValues[i]);
			if (i < paramValues.length - 1)
				logLine.append(", ");
		}
		return logLine;
	}

	@SuppressWarnings("rawtypes")
	public String toString(Object object) {
		if (object == null)
			return "<null>";
		else if (object instanceof String) {
			if (((String) object).length() > 100)
				return ((String) object).substring(0, 100) + "...[more]";
			else
				return (String) object;
		} else if (object instanceof Long)
			return ((Long) object).toString();
		else if (object instanceof Boolean)
			return ((Boolean) object).toString();
		else if (object instanceof Double)
			return ((Double) object).toString();
		else if (object instanceof Integer)
			return ((Integer) object).toString();
		else if (object instanceof List)
			return "items{" + ((List) object).size() + "}";
		else {
			String retVal = getInXMLFormat(object);
			retVal = (null != retVal && retVal.length() > 0) ? retVal : asTag(object.getClass());
			return retVal;
		}
	}

	public SimpleMarshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(SimpleMarshaller marshaller) {
		this.marshaller = marshaller;
	}

	protected String asTag(final Class<?> clazz) {
		if (clazz.isAnonymousClass()) {
			return asTag(clazz.getEnclosingClass());
		}
		return clazz.getSimpleName();
	}


	public String getObjectdetails(Object obj){

		StringBuilder builder = new StringBuilder();
		try {
			Map<String, String> properties = BeanUtils.describe(obj);
			for (Map.Entry<String, String> entry : properties.entrySet()) {
				if( null != entry.getValue() && entry.getValue().length() > 0) {
					String value = toString(entry.getValue());
					builder.append( entry.getKey() + " : " + value + "\t");
				}
			}		
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return builder.toString();
		
	}
	
	
	
	public String getInXMLFormat(Object object){
		String retVal = "";
		if(null == marshaller)
			marshaller = new SimpleMarshallerImpl();
		try {
			retVal = asTag(object.getClass()) + " In XML format : " +  marshaller.marshall(object);
		} catch (SimpleMarshallerException e1) {
			retVal =  asTag(object.getClass()) +  " : " + getObjectdetails(object);
		}	
		return retVal;
	}
	
}
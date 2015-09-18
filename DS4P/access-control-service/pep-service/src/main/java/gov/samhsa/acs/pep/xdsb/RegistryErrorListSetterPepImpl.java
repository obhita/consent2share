package gov.samhsa.acs.pep.xdsb;

import gov.samhsa.acs.xdsb.common.RegistryErrorListSetter;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryErrorList;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;

public class RegistryErrorListSetterPepImpl implements RegistryErrorListSetter {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void setRegistryErrorList(RetrieveDocumentSetResponse response, RegistryErrorList registryErrorList, boolean isPartial){
		RegistryResponseType errorResponse = getNewRegistryResponseType();
		// TODO: This is a temporary solution to prevent NoSuchMethodException.
		// Try to find a better solution other than using Java reflections.
		try {
			Field f = response.getClass().getDeclaredField("registryResponse");
			f.setAccessible(true);
			f.set(response, errorResponse);
		} catch (SecurityException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchFieldException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		// This method throws NoSuchMethodException. The generated java class
		// uses RegistryResponse for argument, but it throws the exception. It
		// works with RegistryResponseType as argument.
		// response.setRegistryResponse(errorResponse);

		if (isPartial) {
			errorResponse
					.setStatus("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:PartialSuccess");
		} else {
			errorResponse
					.setStatus("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure");
		}
		errorResponse.setRegistryErrorList(registryErrorList);
	}
	
	RegistryResponseType getNewRegistryResponseType(){
		return new RegistryResponseType();
	}
}

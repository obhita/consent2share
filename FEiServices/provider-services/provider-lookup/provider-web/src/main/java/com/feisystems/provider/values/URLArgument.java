package com.feisystems.provider.values;

/**
 * Enumeration that provides URL parameter names and parsing 
 * logic for {@code com.feisystems.provider.service.controllers.ProviderController}.   
 * 
 * @author Jason A. Hoppes
 *
 */
public enum URLArgument {
	GENDER (URLHelper.GENDER, 0, 0),
	POSTAL_CODE (URLHelper.POSTAL_CODE, -1, 1),
	US_STATE (URLHelper.US_STATE, 1, -1),
	CITY (URLHelper.CITY, 2, -1),
	SPECIALTY (URLHelper.SPECIALTY, 3, 2),
	TELEPHONE (URLHelper.TELEPHONE, 4, 3),
	LAST_NAME (URLHelper.LAST_NAME, 5, 4),
	FIRST_NAME (URLHelper.FIRST_NAME, 6, 5),
	ENTITY_TYPE (URLHelper.FIRST_NAME, 7, 6),
	PROVIDER_ORGANIZATION_NAME (URLHelper.PROVIDER_ORGANIZATION_NAME, 8, 7),
	PAGE_NUMBER (URLHelper.PAGE_NUMBER, 9, 8);
	
    private final String argumentName;
    private final int postalCodeArrayIdx;
    private final int cityStateArrayIdx;

    URLArgument(String argumentName, int cityStateArrayIdx, int postalCodeArrayIdx) {
        this.argumentName = argumentName;
        this.postalCodeArrayIdx = postalCodeArrayIdx;
        this.cityStateArrayIdx = cityStateArrayIdx;
    }
    
    public String argumentName() { return argumentName; }
    
    public static class URLHelper {
    	public static final String POSTAL_CODE_METHOD_TYPE = "zipcode";
    	public static final String CITY_STATE_METHOD_TYPE = "cityState";
    	public static final String GENDER ="gender";
    	public static final String ENTITY_TYPE = "entitytype";
    	public static final String POSTAL_CODE = "zipcode";
    	public static final String US_STATE = "usstate";
    	public static final String CITY = "city";
    	public static final String SPECIALTY = "specialty";
    	public static final String TELEPHONE = "phone";
    	public static final String LAST_NAME = "lastname";
    	public static final String FIRST_NAME = "firstname";
    	public static final String PROVIDER_ORGANIZATION_NAME = "orgname";
    	public static final String PAGE_NUMBER = "pageNumber";
    	public static final String SEARCH_STRING = "%";
    }
    
    public static URLArgument getURLArgumentInstance(String name) {
    	if (name.equals(URLHelper.POSTAL_CODE)){
    		return POSTAL_CODE;
    	}
    	else if (name.equals(URLHelper.US_STATE)){
    		return US_STATE;
    	}
    	else if (name.equals(URLHelper.CITY)){
    		return CITY;
    	}
    	else if (name.equals(URLHelper.SPECIALTY)){
    		return SPECIALTY;
    	}
    	else if(name.equals(URLHelper.GENDER)){
    		return GENDER;
    	}
    	else if (name.equals(URLHelper.TELEPHONE)){
    		return TELEPHONE;
    	}
    	else if (name.equals(URLHelper.LAST_NAME)){
    		return LAST_NAME;
    	}
    	else if (name.equals(URLHelper.FIRST_NAME)){
    		return FIRST_NAME;
    	}
    	else if (name.equals(URLHelper.PROVIDER_ORGANIZATION_NAME)){
    		return PROVIDER_ORGANIZATION_NAME;
    	}
    	else if (name.equals(URLHelper.ENTITY_TYPE)){
    		return ENTITY_TYPE;
    	}
    	else if (name.equals(URLHelper.PAGE_NUMBER)){
    		return PAGE_NUMBER;
    	}
    	throw new IllegalArgumentException("URL paramter type '" + name + "' not found.");
    }
    
    public static String getMethodType(String url) {
    	if(url.indexOf(URLHelper.US_STATE) >= 0){
    		return URLHelper.CITY_STATE_METHOD_TYPE;    		
    	}
		return URLHelper.POSTAL_CODE_METHOD_TYPE;
    }

    public static String[] createArgumentArray(String methodType) {
    	if(methodType.equals(URLHelper.POSTAL_CODE_METHOD_TYPE)){
    		return new String[]{URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING};
    	}
    	else if (methodType.equals(URLHelper.CITY_STATE_METHOD_TYPE)){
    		return new String[]{URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING, URLHelper.SEARCH_STRING};
    	}
    	throw new IllegalArgumentException("Method type '" + methodType + "' not found.");
    }
        
    public void fillArgument(String[] args, String methodType, String value){
    	if (URLHelper.CITY_STATE_METHOD_TYPE.equals(methodType)){
    		args[cityStateArrayIdx] = value;
    		return;
    	}
    	args[postalCodeArrayIdx] = value;
    }
}

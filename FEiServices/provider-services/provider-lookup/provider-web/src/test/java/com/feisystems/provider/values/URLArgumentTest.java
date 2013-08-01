package com.feisystems.provider.values;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.feisystems.provider.values.URLArgument.URLHelper;

public class URLArgumentTest {
	
	private URLArgument[] cut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cut = new URLArgument[] {URLArgument.CITY, URLArgument.FIRST_NAME, 
				URLArgument.GENDER, URLArgument.LAST_NAME, 
				URLArgument.POSTAL_CODE, URLArgument.SPECIALTY, 
				URLArgument.TELEPHONE, URLArgument.US_STATE};
	}

	@Test
	public void testArgumentName() {
		assertEquals(URLArgument.URLHelper.CITY, cut[0].argumentName());
		assertEquals(URLArgument.URLHelper.FIRST_NAME, cut[1].argumentName());
		assertEquals(URLArgument.URLHelper.GENDER, cut[2].argumentName());
		assertEquals(URLArgument.URLHelper.LAST_NAME, cut[3].argumentName());
		assertEquals(URLArgument.URLHelper.POSTAL_CODE, cut[4].argumentName());
		assertEquals(URLArgument.URLHelper.SPECIALTY, cut[5].argumentName());
		assertEquals(URLArgument.URLHelper.TELEPHONE, cut[6].argumentName());
		assertEquals(URLArgument.URLHelper.US_STATE, cut[7].argumentName());
	}

	@Test
	public void testGetURLArgumentInstance() {
		assertEquals(URLHelper.CITY,URLArgument.getURLArgumentInstance(URLHelper.CITY).argumentName());
		assertEquals(URLHelper.FIRST_NAME,URLArgument.getURLArgumentInstance(URLHelper.FIRST_NAME).argumentName());
		assertEquals(URLHelper.GENDER,URLArgument.getURLArgumentInstance(URLHelper.GENDER).argumentName());
		assertEquals(URLHelper.LAST_NAME,URLArgument.getURLArgumentInstance(URLHelper.LAST_NAME).argumentName());
		assertEquals(URLHelper.POSTAL_CODE,URLArgument.getURLArgumentInstance(URLHelper.POSTAL_CODE).argumentName());
		assertEquals(URLHelper.SPECIALTY,URLArgument.getURLArgumentInstance(URLHelper.SPECIALTY).argumentName());
		assertEquals(URLHelper.TELEPHONE,URLArgument.getURLArgumentInstance(URLHelper.TELEPHONE).argumentName());
		assertEquals(URLHelper.US_STATE,URLArgument.getURLArgumentInstance(URLHelper.US_STATE).argumentName());
	}

	@Test
	public void testGetMethodType() {
		assertEquals(URLHelper.CITY_STATE_METHOD_TYPE, 
				URLArgument.getMethodType("http://context/providers/gender/m/usstate/pa/lastname/smith"));
	}

	@Test
	public void testCreateArgumentArray() {
		String[] cityStateArray = URLArgument.createArgumentArray(URLHelper.CITY_STATE_METHOD_TYPE);
		assertEquals(9, cityStateArray.length);

		String[] zipArray = URLArgument.createArgumentArray(URLHelper.POSTAL_CODE_METHOD_TYPE);
		assertEquals(8, zipArray.length);		
	}

	@Test
	public void testFillArgument() {
		String[] csArray = URLArgument.createArgumentArray(URLHelper.CITY_STATE_METHOD_TYPE);
		cut[0].fillArgument(csArray, URLHelper.CITY_STATE_METHOD_TYPE, "gotham");//2
		cut[1].fillArgument(csArray, URLHelper.CITY_STATE_METHOD_TYPE, "john");//6;
		cut[2].fillArgument(csArray, URLHelper.CITY_STATE_METHOD_TYPE, "m");//0
		cut[3].fillArgument(csArray, URLHelper.CITY_STATE_METHOD_TYPE, "smith");//5;
		cut[5].fillArgument(csArray, URLHelper.CITY_STATE_METHOD_TYPE, "general");//3
		cut[6].fillArgument(csArray, URLHelper.CITY_STATE_METHOD_TYPE, "4105555555");//4
		cut[7].fillArgument(csArray, URLHelper.CITY_STATE_METHOD_TYPE, "ny");//1
		
		assertEquals("m", csArray[0]);
		assertEquals("ny", csArray[1]);
		assertEquals("gotham", csArray[2]);
		assertEquals("general", csArray[3]);
		assertEquals("4105555555", csArray[4]);
		assertEquals("smith", csArray[5]);
		assertEquals("john", csArray[6]);

		String[] zipArray = URLArgument.createArgumentArray(URLHelper.POSTAL_CODE_METHOD_TYPE);
		cut[4].fillArgument(zipArray, URLHelper.POSTAL_CODE_METHOD_TYPE, "123450987");//2
		cut[1].fillArgument(zipArray, URLHelper.POSTAL_CODE_METHOD_TYPE, "john");//6;
		cut[2].fillArgument(zipArray, URLHelper.POSTAL_CODE_METHOD_TYPE, "m");//0
		cut[3].fillArgument(zipArray, URLHelper.POSTAL_CODE_METHOD_TYPE, "smith");//5;
		cut[5].fillArgument(zipArray, URLHelper.POSTAL_CODE_METHOD_TYPE, "general");//3
		cut[6].fillArgument(zipArray, URLHelper.POSTAL_CODE_METHOD_TYPE, "4105555555");//4
		
		assertEquals("123450987", zipArray[1]);
		assertEquals("general", zipArray[2]);
		assertEquals("4105555555", zipArray[3]);
		assertEquals("smith", zipArray[4]);
		assertEquals("john", zipArray[5]);
		assertEquals("m", zipArray[0]);
}

}

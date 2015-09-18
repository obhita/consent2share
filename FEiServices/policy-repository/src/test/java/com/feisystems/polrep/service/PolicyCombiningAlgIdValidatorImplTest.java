package com.feisystems.polrep.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.feisystems.polrep.service.exception.InvalidPolicyCombiningAlgIdException;
import com.feisystems.polrep.service.exception.PolicyCombiningAlgIdNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class PolicyCombiningAlgIdValidatorImplTest {

	private static final String KEY1 = "KEY1";
	private static final String VALUE1 = "VALUE1";
	private static final String KEY2 = "KEY2";
	private static final String VALUE2 = "VALUE2";
	private static final String INVALID = "INVALID";

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	private Map<String, String> algs;

	@InjectMocks
	private PolicyCombiningAlgIdValidatorImpl sut;

	@Before
	public void setUp() throws Exception {
		algs = new HashMap<String, String>();
		algs.put(KEY1, VALUE1);
		algs.put(KEY2, VALUE2);
		ReflectionTestUtils.setField(sut, "combiningAlgs", algs);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCombiningAlgs() {
		sut.getCombiningAlgs()
				.entrySet()
				.forEach(
						e -> assertTrue(algs.keySet().contains(e.getKey())
								&& algs.values().contains(e.getValue())));
	}

	@Test
	public void testValidateAndReturn_By_Key1() {
		assertEquals(VALUE1, sut.validateAndReturn(KEY1));
	}

	@Test
	public void testValidateAndReturn_By_Key2() {
		assertEquals(VALUE2, sut.validateAndReturn(KEY2));
	}

	@Test
	public void testValidateAndReturn_By_Value1() {
		assertEquals(VALUE1, sut.validateAndReturn(VALUE1));
	}

	@Test
	public void testValidateAndReturn_By_Value2() {
		assertEquals(VALUE2, sut.validateAndReturn(VALUE2));
	}

	@Test
	public void testValidateAndReturn_Throws_InvalidPolicyCombiningAlgIdException() {
		thrown.expect(InvalidPolicyCombiningAlgIdException.class);
		sut.validateAndReturn(INVALID);
	}

	@Test
	public void testValidateAndReturn_Throws_PolicyCombiningAlgIdNotFoundException_By_Empty_String() {
		thrown.expect(PolicyCombiningAlgIdNotFoundException.class);
		sut.validateAndReturn("");
	}

	@Test
	public void testValidateAndReturn_Throws_PolicyCombiningAlgIdNotFoundException_By_Null() {
		thrown.expect(PolicyCombiningAlgIdNotFoundException.class);
		sut.validateAndReturn(null);
	}

}

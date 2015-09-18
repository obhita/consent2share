package com.feisystems.polrep.service;

import java.util.Map;

public interface PolicyCombiningAlgIdValidator {

	public abstract Map<String, String> getCombiningAlgs();

	public abstract String validateAndReturn(String policyCombiningAlgId);
}
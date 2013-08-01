package com.feisystems.provider.dtos;

import java.io.Serializable;
import java.util.List;



public class ProvidersDto implements Serializable {

	private static final long serialVersionUID = -4799400167093664L;

	private List<ProviderDto> providerBeans;

	public ProvidersDto() {
	}
	
	public ProvidersDto(List<ProviderDto> providerBeans) {
		this.providerBeans = providerBeans;
	}
	
	public List<ProviderDto> getProviders() {
		return providerBeans;
	}

	public void setProviders(List<ProviderDto> providerBeans) {
		this.providerBeans = providerBeans;
	}

}

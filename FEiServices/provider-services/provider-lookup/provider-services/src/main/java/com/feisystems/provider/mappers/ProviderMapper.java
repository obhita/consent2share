package com.feisystems.provider.mappers;

import java.io.Serializable;
import java.util.List;

import com.feisystems.provider.Provider;
import com.feisystems.provider.dtos.ProviderDto;

/**
 * Provides mapping methods to map between {@code ccom.feisystems.provider.ProviderDto}
 * and {@code com.feisystems.provider.Provider} and 
 * {@code java.util.List}s thereof.
 * 
 * @author Jason A. Hoppes
 */
public interface ProviderMapper extends Serializable{
	public ProviderDto map(Provider provider); 
	public Provider map(ProviderDto dto);
	public List<ProviderDto> mapToProviderDtoList(List<Provider> providers);
	public List<Provider> mapToProviderList(List<ProviderDto> providers);
}

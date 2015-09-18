package com.feisystems.provider.service.mapper;

import java.util.List;

import com.feisystems.provider.domain.Provider;
import com.feisystems.provider.service.dto.ProviderDto;

/**
 * Provides mapping methods to map between {@code ccom.feisystems.provider.ProviderDto}
 * and {@code com.feisystems.provider.domain.Provider} and
 * {@code java.util.List}s thereof.
 *
 */
public interface ProviderMapper {
	public ProviderDto map(Provider provider);
	public Provider map(ProviderDto dto);
	public List<ProviderDto> mapToProviderDtoList(List<Provider> providers);
	public List<Provider> mapToProviderList(List<ProviderDto> providers);
}

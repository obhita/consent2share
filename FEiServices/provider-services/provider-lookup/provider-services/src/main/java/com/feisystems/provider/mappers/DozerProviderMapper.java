package com.feisystems.provider.mappers;

import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.feisystems.provider.Provider;
import com.feisystems.provider.dtos.ProviderDto;

/**
 * 
 * @author jason.hoppes
 * @see ProviderMapper
 */
@SuppressWarnings("unchecked")
/**
 * Dozer implementation of {@code ProviderMapper}.
 * @author Jason A. Hoppes
 * @see ProviderMapper
 */
public class DozerProviderMapper implements ProviderMapper {

	private static final long serialVersionUID = -7415482188094707824L;
	
	@Autowired private Mapper mapper;

	public DozerProviderMapper() {}

	@Override
	public ProviderDto map(Provider provider) {
		return mapper.map(provider, ProviderDto.class);
	}

	@Override
	public Provider map(ProviderDto provider) {
		return mapper.map(provider, Provider.class);
	}

	@Override
	public List<ProviderDto> mapToProviderDtoList(List<Provider> providers) {
		return mapper.map(providers, List.class);
	}

	@Override
	public List<Provider> mapToProviderList(List<ProviderDto> providers) {
		return mapper.map(providers, List.class);
	}

}

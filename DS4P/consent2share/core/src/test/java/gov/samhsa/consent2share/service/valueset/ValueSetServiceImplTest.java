package gov.samhsa.consent2share.service.valueset;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.domain.valueset.ConceptCodeValueSetRepository;
import gov.samhsa.consent2share.domain.valueset.ValueSet;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategory;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.consent2share.domain.valueset.ValueSetRepository;
import gov.samhsa.consent2share.service.dto.ValueSetDto;
import gov.samhsa.consent2share.service.dto.ValueSetVSCDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ValueSetServiceImplTest {
	
	@InjectMocks
	ValueSetService vst=new ValueSetServiceImpl();

	@Mock
	ValueSetRepository valueSetRepository;
	
	@Mock
	ValueSetCategoryRepository valueSetCategoryRepository;
	
	@Mock
	ConceptCodeValueSetRepository conceptCodeValueSetRepository;
	
	@Mock
	ValueSetMgmtHelper valueSetMgmtHelper;;
	 
	
	@Test(expected = ValueSetCategoryNotFoundException.class)
	public void testCreateValueSet_throw_ValueSetCategoryNotFoundException() throws ValueSetCategoryNotFoundException{
		ValueSetDto created=mock(ValueSetDto.class);
		when(created.getDescription()).thenReturn("description");
		when(created.getName()).thenReturn("name");
		when(created.getCode()).thenReturn("code");
		when(created.getValueSetCategoryId()).thenReturn((long) 1);
		
		ValueSetCategory selected=new ValueSetCategory(); 
		selected.setCode("code");
		selected.setName("name");
		selected.setId((long) 1);
		when(valueSetCategoryRepository.findOne((long) 1)).thenReturn(null);
		
		ValueSet valueSet=new ValueSet();
		
		when(valueSetMgmtHelper.createValuesetDtoFromEntity(any(ValueSet.class))).thenReturn(created);
		
		ValueSetDto result=vst.create(created);
		
		
	}
	
	
	@Test
	public void testDeleteVelueSet() throws ValueSetNotFoundException {
		
		ValueSet deleted = mock(ValueSet.class);
		when(valueSetRepository.findOne(anyLong())).thenReturn(deleted);
		
		ValueSetDto valueSetDto = mock(ValueSetDto.class);
		when(valueSetMgmtHelper.createValuesetDtoFromEntity(deleted)).thenReturn(valueSetDto);
		
		Assert.assertEquals(vst.delete((long)1), valueSetDto);
	}
	
	@Test(expected = ValueSetNotFoundException.class)
	public void testDeleteVelueSet_throw_ValueSetNotFoundException() throws ValueSetNotFoundException {
		
		when(valueSetRepository.findOne(anyLong())).thenReturn(null);
		vst.delete((long)1);
		
	}
	
	
	@Test
	public void testfindAll(){
		
		List<ValueSet> valueSets=(List<ValueSet>)mock(List.class);
		when(valueSetRepository.findAll()).thenReturn(valueSets);
		
		List<ValueSetDto> valueSetDtos=new ArrayList();
		ValueSetDto valueSetDto=mock(ValueSetDto.class);
		valueSetDtos.add(valueSetDto);
		when(valueSetMgmtHelper.convertValueSetEntitiesToDtos(valueSets)).thenReturn(valueSetDtos);
		
		when(valueSetDto.getId()).thenReturn((long) 1);
		when(conceptCodeValueSetRepository.findAllByPkValueSetId(anyLong())).thenReturn(null);
		
		assertEquals(vst.findAll(),valueSetDtos);
	}
	
	
	@Test(expected = ValueSetCategoryNotFoundException.class)
	public void testfindId() throws ValueSetCategoryNotFoundException{
		
		ValueSet valueSet=mock(ValueSet.class);
		
		when(valueSetRepository.findOne(anyLong())).thenReturn(valueSet);
		
		ValueSetDto valueSetDto=mock(ValueSetDto.class);
		when(valueSetMgmtHelper.createValuesetDtoFromEntity(valueSet)).thenReturn(valueSetDto);
		
		assertEquals(vst.findById((long)1),valueSetDto);
	}
	
	@Test
	public void testUpdateValueSet() throws ValueSetNotFoundException, ValueSetCategoryNotFoundException{
		
		ValueSetDto updated=mock(ValueSetDto.class);
		
		when(updated.getId()).thenReturn((long)1);
		when(updated.getCode()).thenReturn("code");
		when(updated.getName()).thenReturn("name");
		when(updated.getUserName()).thenReturn("username");
		
		ValueSet valueSet=mock(ValueSet.class);
		
		when(valueSetRepository.findOne(anyLong())).thenReturn(valueSet);
		//when(valueSet.getValueSetCategory().getId()).thenReturn((long)1);
		ValueSetDto valueSetDto=mock(ValueSetDto.class);
		when(valueSetMgmtHelper.createValuesetDtoFromEntity(valueSet)).thenReturn(valueSetDto);
		
		//assertEquals(vst.update(updated),valueSetDto);
	}
	
	@Test(expected = ValueSetNotFoundException.class)
	public void testUpdateValueSet_throw_ValueSetNotFoundException() throws ValueSetNotFoundException, ValueSetCategoryNotFoundException {
		ValueSetDto updated=mock(ValueSetDto.class);
		when(valueSetRepository.findOne(anyLong())).thenReturn(null);
		vst.update(updated);
		
	}
	
	@Test(expected = ValueSetCategoryNotFoundException.class)
	public void testValueSetVSCDto_throw_ValueSetCategoryNotFoundException() throws ValueSetCategoryNotFoundException {
		
		when(valueSetCategoryRepository.findAll()).thenReturn(null);
		vst.create();
		
	}
	
	@Test
	public void testValueSetVSCDto() throws ValueSetCategoryNotFoundException {
		ValueSetVSCDto valueSetVSCDto = new ValueSetVSCDto();
		
		
		ValueSetCategory valueSetCategory=mock(ValueSetCategory.class);
		List<ValueSetCategory> valueSetCategories =  Arrays.asList(valueSetCategory); 
		
		Map<Long, String> valueSetCategoriesMap=mock(Map.class);
		
		when(valueSetCategoryRepository.findAll()).thenReturn(valueSetCategories);
		when(valueSetMgmtHelper
				.convertValueSetCategoryEntitiesToMap(valueSetCategories)).thenReturn(valueSetCategoriesMap);
		valueSetVSCDto.setValueSetCategoryMap(valueSetCategoriesMap);
		
		vst.create();
		
	}
	
	@Test
	public void testFindAllByName() {
		List<ValueSet> valueSets = mock(List.class);
		List<ValueSetDto> valueSetDtos = mock(List.class);
		
		when(valueSetRepository.findAllByNameLike(anyString())).thenReturn(valueSets);
		when(valueSetMgmtHelper.convertValueSetEntitiesToDtos(valueSets)).thenReturn(valueSetDtos);
		assertEquals(vst.findAllByName("a"),valueSetDtos);
		
	}
	
	@Test
	public void testFindAllByCode() {
		List<ValueSet> valueSets = mock(List.class);
		List<ValueSetDto> valueSetDtos = mock(List.class);
		
		when(valueSetRepository.findAllByCodeLike(anyString())).thenReturn(valueSets);
		when(valueSetMgmtHelper.convertValueSetEntitiesToDtos(valueSets)).thenReturn(valueSetDtos);
		assertEquals(vst.findAllByCode("a"),valueSetDtos);
		
	}
	
}

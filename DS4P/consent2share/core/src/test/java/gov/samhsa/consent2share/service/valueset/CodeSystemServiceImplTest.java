package gov.samhsa.consent2share.service.valueset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.systemnotification.SystemNotification;
import gov.samhsa.consent2share.domain.systemnotification.SystemNotificationRepository;
import gov.samhsa.consent2share.domain.valueset.ConceptCodeValueSetRepository;
import gov.samhsa.consent2share.domain.valueset.ValueSet;
import gov.samhsa.consent2share.domain.valueset.ValueSet.Builder;
import gov.samhsa.consent2share.domain.valueset.CodeSystem;
import gov.samhsa.consent2share.domain.valueset.CodeSystemRepository;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategory;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.consent2share.domain.valueset.ValueSetRepository;
import gov.samhsa.consent2share.service.dto.CodeSystemDto;
import gov.samhsa.consent2share.service.dto.SystemNotificationDto;
import gov.samhsa.consent2share.service.dto.ValueSetDto;
import gov.samhsa.consent2share.service.dto.ValueSetVSCDto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.test.annotation.ExpectedException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CodeSystemServiceImplTest {
	
	@InjectMocks
	CodeSystemService vst=new CodeSystemServiceImpl();

	@Mock
	CodeSystemRepository codeSystemRepository;
	
	@Mock
	ValueSetMgmtHelper valueSetMgmtHelper;;
	 
	
	@Test
	public void testCreateCodeSystem(){
		CodeSystemDto created=mock(CodeSystemDto.class);
		when(created.getDisplayName()).thenReturn("displayName");
		
		CodeSystem codeSystem=mock(CodeSystem.class);
		when(codeSystemRepository.save(any(CodeSystem.class))).thenReturn(codeSystem);
		when(valueSetMgmtHelper.createCodeSystemDtoFromEntity(codeSystem)).thenReturn(created);
		
		Assert.assertEquals(vst.create(created), created);
		
		
	}
	
	
	@Test
	public void testDeleteCodeSystem() throws CodeSystemNotFoundException {
		
		CodeSystem deleted = mock(CodeSystem.class);
		when(codeSystemRepository.findOne(anyLong())).thenReturn(deleted);
		
		CodeSystemDto codeSystemDto = mock(CodeSystemDto.class);
		when(valueSetMgmtHelper.createCodeSystemDtoFromEntity(deleted)).thenReturn(codeSystemDto);
		
		Assert.assertEquals(vst.delete((long)1), codeSystemDto);
	}
	
	@Test(expected = CodeSystemNotFoundException.class)
	public void testDeleteCodeSystem_throw_CodeSystemNotFoundException() throws CodeSystemNotFoundException {
		
		when(codeSystemRepository.findOne(anyLong())).thenReturn(null);
		vst.delete((long)1);
		
	}
	
	
	@Test
	public void testfindAll(){
		
		List<CodeSystem> codeSystems=(List<CodeSystem>)mock(List.class);
		when(codeSystemRepository.findAll()).thenReturn(codeSystems);
		
		List<CodeSystemDto> codeSystemDtos=new ArrayList();
		CodeSystemDto codeSystemDto=mock(CodeSystemDto.class);
		codeSystemDtos.add(codeSystemDto);
		when(valueSetMgmtHelper.convertCodeSystemEntitiesToDtos(codeSystems)).thenReturn(codeSystemDtos);
				
		assertEquals(vst.findAll(),codeSystemDtos);
	}
	
	
	@Test
	public void testfindId(){
		
		CodeSystem codeSystem=mock(CodeSystem.class);
		
		when(codeSystemRepository.findOne(anyLong())).thenReturn(codeSystem);
		
		CodeSystemDto codeSystemDto=mock(CodeSystemDto.class);
		when(valueSetMgmtHelper.createCodeSystemDtoFromEntity(codeSystem)).thenReturn(codeSystemDto);
		
		assertEquals(vst.findById((long)1),codeSystemDto);
	}
	
	@Test
	public void testUpdateCodeSystem() throws CodeSystemNotFoundException{
		
		CodeSystemDto updated=mock(CodeSystemDto.class);
		
		when(updated.getId()).thenReturn((long)1);
		when(updated.getCode()).thenReturn("code");
		when(updated.getName()).thenReturn("name");
		when(updated.getUserName()).thenReturn("username");
		
		CodeSystem codeSystem=mock(CodeSystem.class);
		
		when(codeSystemRepository.findOne(anyLong())).thenReturn(codeSystem);
		
		CodeSystemDto codeSystemDto=mock(CodeSystemDto.class);
		when(valueSetMgmtHelper.createCodeSystemDtoFromEntity(codeSystem)).thenReturn(codeSystemDto);
		
		assertEquals(vst.update(updated),codeSystemDto);
	}
	
	@Test(expected = CodeSystemNotFoundException.class)
	public void testUpdateCodeSystem_throw_CodeSystemNotFoundException() throws CodeSystemNotFoundException {
		CodeSystemDto updated=mock(CodeSystemDto.class);
		when(codeSystemRepository.findOne(anyLong())).thenReturn(null);
		vst.update(updated);
		
	}
	
}

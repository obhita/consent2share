/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.service.systemnotification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.provider.IndividualProvider;
import gov.samhsa.consent2share.domain.systemnotification.SystemNotification;
import gov.samhsa.consent2share.domain.systemnotification.SystemNotificationRepository;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.HistoryDto;
import gov.samhsa.consent2share.service.dto.IndividualProviderDto;
import gov.samhsa.consent2share.service.dto.SystemNotificationDto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class NotificationServiceImpl.
 */
@Service
@Transactional
public class SystemNotificationServiceImpl implements SystemNotificationService {
	
	/** The patient repository. */
	@Autowired
	private PatientRepository patientRepository;
	
	/** The patient repository. */
	@Autowired
	private SystemNotificationRepository systemNotificationRepository;
	
	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper;
	
	
	public List<SystemNotificationDto> findAllSystemNotificationDtosByPatient(long patientId){
		
		List<SystemNotification> systemNotifications = systemNotificationRepository.findAllByPatientId(patientId);
		List<SystemNotificationDto> systemNotificationDtos=makeSystemNotificationDtos();
		
		
		for (SystemNotification systemNotification : systemNotifications) {
			systemNotificationDtos.add( modelMapper.map(systemNotification, SystemNotificationDto.class));
		}
		
		return getReversed(systemNotificationDtos);
	}
	
	public ArrayList<SystemNotificationDto> makeSystemNotificationDtos(){
		return new ArrayList<SystemNotificationDto>();
	}
	
	public List<SystemNotificationDto> getReversed(List<SystemNotificationDto> original) {
		List<SystemNotificationDto> copy = new ArrayList<SystemNotificationDto>(original);
		Collections.reverse(copy);
		return copy;
	}
}



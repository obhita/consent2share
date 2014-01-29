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
package gov.samhsa.consent2share.service.audit;

import gov.samhsa.consent2share.domain.audit.ModifiedEntityTypeEntity;
import gov.samhsa.consent2share.domain.audit.ModifiedEntityTypeEntityRepository;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientLegalRepresentativeAssociationRepository;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.audit.RevisionInfoEntity;
import gov.samhsa.consent2share.domain.audit.RevisionInfoEntityRepository;
import gov.samhsa.consent2share.service.dto.HistoryDto;
import gov.samhsa.consent2share.service.dto.RecentAcctivityDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class AuditServiceImpl.
 * @param <T>
 */
@Service
@Transactional
public class AdminAuditServiceImpl implements AdminAuditService {
	
	
	@Value("${numberRecentPatientActivities}")
	Integer numberRecentPatientActivities;
	

	/** The entity manager factory. */
	@Autowired
	EntityManagerFactory entityManagerFactory;

	/** The patient repository. */
	@Autowired
	PatientRepository patientRepository;

	/** The patient revision entity repository. */
	@Autowired
	RevisionInfoEntityRepository patientRevisionEntityRepository;

	/** The modified entity type entity repository. */
	@Autowired
	ModifiedEntityTypeEntityRepository modifiedEntityTypeEntityRepository;

	/** The patient legal representative association repository. */
	@Autowired
	PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository;

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.audit.AuditService#getReversed(java.util.List)
	 */
	public List<RevisionInfoEntity> getReversed(List<RevisionInfoEntity> original) {
		List<RevisionInfoEntity> copy = new ArrayList<RevisionInfoEntity>(original);
		Collections.reverse(copy);
		return copy;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.audit.AuditService#findHistoryDetails(java.util.List)
	 */
	public List<HistoryDto> findHistoryDetails(List<Number> revisions) {
		List<HistoryDto> historyDtos = makeHistoryDtos();
		for (Number n : revisions) {
			HistoryDto hd = findHistoryDetail(n);
			historyDtos.add(hd);

		}
		return historyDtos;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.audit.AuditService#makeHistoryDtos()
	 */
	public List<HistoryDto> makeHistoryDtos() {
		return new ArrayList<HistoryDto>();
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.audit.AuditService#makeHistoryDto()
	 */
	public HistoryDto makeHistoryDto() {
		return new HistoryDto();
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.audit.AuditService#findHistoryDetail(java.lang.Number)
	 */
	public HistoryDto findHistoryDetail(Number n) {
		HistoryDto hd = makeHistoryDto();
		hd.setRevisionid(n.longValue());

		RevisionInfoEntity patientRevisionEntity = patientRevisionEntityRepository
				.findOneById(n);
		List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys = modifiedEntityTypeEntityRepository
				.findAllByRevision(patientRevisionEntity);

		if (patientRevisionEntity.getUsername() != null) {
			String username1 = patientRevisionEntity.getUsername();
			if(patientRepository.findByUsername(username1)!=null){
			Patient patientA1 = patientRepository.findByUsername(username1);
			String changedBy = patientA1.getLastName() + ",   "
					+ patientA1.getFirstName();
			hd.setChangedBy(changedBy);
			}
		}

		Long timestamp = patientRevisionEntity.getTimestamp();
		String revdate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
				.format(timestamp);

		hd.setTimestamp(revdate);

		String revtp = findRevType(modifiedEntityTypeEntitys);
		String entityClassname = findRevClassName(modifiedEntityTypeEntitys);

		hd.setRecType(revtp);
		hd.setType(entityClassname);

		return hd;
	}



	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.audit.AuditService#findRevType(java.util.List)
	 */
	public String findRevType(
			List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys) {
		Byte btype = 1;
		if (modifiedEntityTypeEntitys.size() == 1)
			btype = modifiedEntityTypeEntitys.get(0).getRevisionType();

		if (modifiedEntityTypeEntitys.size() == 2) {
			if (modifiedEntityTypeEntitys.get(0).getRevisionType() != 1)
				btype = modifiedEntityTypeEntitys.get(0).getRevisionType();
			else
				btype = modifiedEntityTypeEntitys.get(1).getRevisionType();

		}
		if (modifiedEntityTypeEntitys.size() == 3) {

			if (modifiedEntityTypeEntitys.get(0).getRevisionType() == 2
					|| modifiedEntityTypeEntitys.get(1).getRevisionType() == 2
					|| modifiedEntityTypeEntitys.get(2).getRevisionType() == 2)
				btype = 2;

			else if (modifiedEntityTypeEntitys.get(0).getRevisionType() == 0
					|| modifiedEntityTypeEntitys.get(1).getRevisionType() == 0
					|| modifiedEntityTypeEntitys.get(2).getRevisionType() == 0)
				btype = 0;

		}
		String revType = findRevType(btype);
		return revType;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.audit.AuditService#findRevType(java.lang.Byte)
	 */
	public String findRevType(Byte btype) {
		if (btype == 1)
			return "Updated";
		if (btype == 0)
			return "Created";
		if (btype == 2)
			return "Deleted";
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.audit.AuditService#findRevClassName(java.util.List)
	 */
	public String findRevClassName(
			List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys) {
		StringBuffer entityClassname = new StringBuffer();

		if (modifiedEntityTypeEntitys.size() == 1) {
			String entityname = new String(modifiedEntityTypeEntitys.get(0)
					.getEntityClassName());
			entityClassname.append(entityname
					.substring(entityname.lastIndexOf('.') + 1).trim()
					.replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2"));
		}

		if (modifiedEntityTypeEntitys.size() == 2) {
			if (modifiedEntityTypeEntitys.get(0).getRevisionType() != 1) {
				String entityname = new String(modifiedEntityTypeEntitys.get(0)
						.getEntityClassName());
				entityClassname.append(entityname
						.substring(entityname.lastIndexOf('.') + 1).trim()
						.replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2"));
			} else {
				String entityname = new String(modifiedEntityTypeEntitys.get(1)
						.getEntityClassName());
				entityClassname.append(entityname
						.substring(entityname.lastIndexOf('.') + 1).trim()
						.replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2"));
			}
		}
		if (modifiedEntityTypeEntitys.size() == 3) {

			entityClassname.append("Signed PDF Consent");

		}
		return entityClassname.toString();
	}
	
	
	
	public List<RecentAcctivityDto> findAdminHistoryByUsername(String username) {
		EntityManager em2 = entityManagerFactory.createEntityManager();
		AuditReader reader2 = AuditReaderFactory.get(em2);
		List<RevisionInfoEntity> revisionHistoryList= patientRevisionEntityRepository.findAllByUsername(username);
		revisionHistoryList=getReversed(revisionHistoryList);
		List<RecentAcctivityDto> recentPatientActivites=new ArrayList<RecentAcctivityDto>();
		Set<Long> patientIds=new HashSet<Long>();
		for (RevisionInfoEntity revision : revisionHistoryList) {
			
			RecentAcctivityDto ra=new RecentAcctivityDto();
			ra.setRevisionid(revision.getId());
			List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys = modifiedEntityTypeEntityRepository.findAllByRevision(revision);
			
			ra.setType(findRevClassName(modifiedEntityTypeEntitys));
			if(ra.getType().equals("Patient")){
				
			ra.setActivity(findRevType(modifiedEntityTypeEntitys));
			
			Long timestamp = revision.getTimestamp();
			String revdate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
					.format(timestamp);
			ra.setTimestamp(revdate);
			
				AuditQuery query = reader2
						.createQuery()
						.forEntitiesModifiedAtRevision(
								Patient.class,
								revision.getId());
				
							
				Patient patient = (Patient) query.getSingleResult();
								
				ra.setPatientid(patient.getId());
				if(!patientIds.contains(patient.getId())){
					patientIds.add(patient.getId());
				ra.setFirstname(patient.getFirstName());
				ra.setLastname(patient.getLastName());
				ra.setBirthDate(patient.getBirthDay());
				ra.setAdministrativeGenderCode(patient.getAdministrativeGenderCode().getDisplayName().toString());
				
			   recentPatientActivites.add(ra);
				}
			   if(recentPatientActivites.size()>=numberRecentPatientActivities)
				   break;
				   
		}}

	em2.close();	
	return recentPatientActivites;	
	}


	
	
}

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
package gov.samhsa.consent2share.service.admin;

import java.util.Date;
import java.util.UUID;

import javax.mail.MessagingException;

import gov.samhsa.consent2share.domain.account.EmailToken;
import gov.samhsa.consent2share.domain.account.EmailTokenRepository;
import gov.samhsa.consent2share.domain.account.TokenGenerator;
import gov.samhsa.consent2share.domain.account.TokenType;
import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.domain.account.UsersRepository;
import gov.samhsa.consent2share.domain.commondomainservices.EmailSender;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.reference.AdministrativeGenderCode;
import gov.samhsa.consent2share.domain.reference.AdministrativeGenderCodeRepository;
import gov.samhsa.consent2share.domain.staff.Staff;
import gov.samhsa.consent2share.domain.staff.StaffRepository;
import gov.samhsa.consent2share.infrastructure.DtoToDomainEntityMapper;
import gov.samhsa.consent2share.infrastructure.EmailType;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.AuthenticationFailedException;
import gov.samhsa.consent2share.infrastructure.security.EmailAddressNotExistException;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.dto.AdminProfileDto;
import gov.samhsa.consent2share.service.dto.BasicPatientAccountDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class PatientServiceImpl.
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The Administrator repository. */
	@Autowired
	private StaffRepository administratorRepository;
	
	/** The patient repository. */
	@Autowired
	private PatientRepository patientRepository;
	
	
	/** The patient profile dto to patient mapper. */
	@Autowired
	private DtoToDomainEntityMapper<PatientProfileDto, Patient> patientProfileDtoToPatientMapper;
	
	@Autowired
	AdminProfileDtoToAdministratorMapper adminProfileDtoToAdministratorMapper;
	
	/** The model mapper. */
	@Autowired
	private ModelMapper modelMapper;

	/** The user context. */
	@Autowired
	private UserContext userContext;
	
	/** The password encoder. */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/** The email sender. */
	@Autowired
	private EmailSender emailSender;
	
	/** The token generator. */
	@Autowired
	private TokenGenerator tokenGenerator;
	
	/** The email token repository. */
	@Autowired
	private EmailTokenRepository emailTokenRepository;
	
		/** The users repository. */
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private AdministrativeGenderCodeRepository administrativeGenderCodeRepository;
	
	@Value("${accountVerificationTokenExpireInHours}")
	private Integer accountVerificationTokenExpireInHours;
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.patient.PatientService#findPatientProfileByUsername(java.lang.String)
	 */
	public AdminProfileDto findAdminProfileByUsername(String username) {
		Staff admin = administratorRepository.findByUsername(username);
		AdminProfileDto adminProfileDto = modelMapper.map(admin,
				AdminProfileDto.class);

		return adminProfileDto;
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.patient.PatientService#updatePatient(gov.samhsa.consent2share.service.dto.PatientProfileDto)
	 */
	@Override
	@Transactional
	public void updateAdministrator(AdminProfileDto adminProfileDto) throws AuthenticationFailedException{
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String username=adminProfileDto.getUsername();
		if (!currentUser.getUsername().equals(username))
			throw new AuthenticationFailedException("Username does not match current active user.");
		Users user=usersRepository.loadUserByUsername(username);
		if (user!=null)
		if (!passwordEncoder.matches(adminProfileDto.getPassword(), user.getPassword()))
			throw new AuthenticationFailedException("Password is incorrect.");
		logger.info("{} being run...", "updatePatient");
		Staff admin = adminProfileDtoToAdministratorMapper.map(adminProfileDto);
		administratorRepository.save(admin);
		try {
			emailSender.sendMessage(adminProfileDto.getFirstName()+" "+adminProfileDto.getLastName(),
					adminProfileDto.getEmail(), EmailType.USER_PROFILE_CHANGE, null, null);
		} catch (MessagingException e) {
			logger.warn("Error when sending the email message.");
		}
	}
	
	@Override
	@Transactional
	public void updatePatient(PatientProfileDto patientDto){
		Patient patient = patientProfileDtoToPatientMapper.map(patientDto);
		patientRepository.save(patient);
		}

	@Override
	public long createPatientAccount(
			BasicPatientAccountDto basicPatientAccountDto) {
		Patient patient = new Patient();
		patient.setBirthDay(basicPatientAccountDto.getBirthDate());
		patient.setFirstName(basicPatientAccountDto.getFirstName());
		patient.setLastName(basicPatientAccountDto.getLastName());
		patient.setEmail(basicPatientAccountDto.getEmail());
		AdministrativeGenderCode administrativeGenderCode = administrativeGenderCodeRepository
				.findByCode(basicPatientAccountDto.getAdministrativeGenderCode());
		patient.setAdministrativeGenderCode(administrativeGenderCode);
		patient.setVerificationCode(UUID.randomUUID().toString().substring(0, 7));
		
		patientRepository.save(patient);
		
		return patient.getId();
	}
	
	@Override
	public Boolean sendLoginInformationEmail(
			long patientId, String linkUrl) throws EmailAddressNotExistException, MessagingException {
		
		Patient patient =patientRepository.findOne(patientId);
		//create emailToken 
		if (patient.getEmail()==null) {
			String message = String.format(
					"Email address %s doesn't exist for username %s.");
			
			logger.info("message");
			throw new EmailAddressNotExistException(message);
		}
		
		EmailToken accountLoginInfoToken = new EmailToken();
		accountLoginInfoToken.setExpireInHours(accountVerificationTokenExpireInHours);
		accountLoginInfoToken.setRequestDateTime(new Date());
		String token = tokenGenerator.generateToken();
		accountLoginInfoToken.setToken(token);
		accountLoginInfoToken.setIsTokenUsed(false);
		accountLoginInfoToken.setPatientId(patientId);
		accountLoginInfoToken.setTokenType(TokenType.NEW_LOGIN_ACCOUNT);
		emailTokenRepository.save(accountLoginInfoToken);

		String emailLinkPlaceHolder = "?token=%s";
		String link = String.format(emailLinkPlaceHolder, token);

		emailSender.sendMessage(
				patient.getFirstName() + " " + patient.getLastName(),
				patient.getEmail(), EmailType.NEW_LOGIN_ACCOUNT, linkUrl, token);
		return true;
	}
	

	
}

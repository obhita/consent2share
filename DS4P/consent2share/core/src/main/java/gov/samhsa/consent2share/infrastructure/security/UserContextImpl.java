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
package gov.samhsa.consent2share.infrastructure.security;

import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * The Class UserContextImpl.
 */
@Component
public class UserContextImpl implements UserContext {

	/** The patient repository. */
	private PatientRepository patientRepository;
	
	/** The user details service. */
	private UserDetailsService userDetailsService;

	/**
	 * Instantiates a new user context impl.
	 *
	 * @param patientRepository the patient repository
	 * @param userDetailsService the user details service
	 */
	@Autowired
	public UserContextImpl(PatientRepository patientRepository,
			UserDetailsService userDetailsService) {
		this.patientRepository = patientRepository;
		this.userDetailsService = userDetailsService;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.security.UserContext#getCurrentUser()
	 */
	@Override
	public AuthenticatedUser getCurrentUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication == null) {
			return null;
		}

		String username = authentication.getName();

		Patient patient = patientRepository.findByUsername(username);

		AuthenticatedUser authenticatedUser = new AuthenticatedUser();
		authenticatedUser.setUsername(username);
		if (patient != null) {
			authenticatedUser.setFirstName(patient.getFirstName());
			authenticatedUser.setLastName(patient.getLastName());
			authenticatedUser.setBirthDate(patient.getBirthDay());
			authenticatedUser.setGenderDisplayName(patient
					.getAdministrativeGenderCode().getDisplayName());
		}

		return authenticatedUser;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.security.UserContext#setCurrentUser(java.lang.String)
	 */
	@Override
	public void setCurrentUser(String username) {

		UserDetails userDetails = null;
		try {
			userDetails = userDetailsService.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {

		}
		
		if (userDetails != null) {
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					userDetails, "", userDetails.getAuthorities());
			SecurityContextHolder.getContext()
					.setAuthentication(authentication);
		}
	}
}

package gov.samhsa.consent2share.domain.valueset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueSetRepository extends JpaRepository<ValueSet, Long> {
	
	
	

}

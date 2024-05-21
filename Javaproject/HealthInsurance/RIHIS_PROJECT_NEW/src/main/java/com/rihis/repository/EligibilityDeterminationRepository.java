package com.rihis.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rihis.model.EligibilityDetermination;

public interface EligibilityDeterminationRepository extends JpaRepository<EligibilityDetermination, Integer> {
	
	@Query("select e from EligibilityDetermination e where e.benefitAmount != 0")
	public Page<EligibilityDetermination> findAllByBenefitCitizens(Pageable pageable);

	
	public Optional<EligibilityDetermination> findByCaseNumber(Integer caseNumber);
	
	@Query("select e from EligibilityDetermination e where e.benefitAmount != 0 and e.caseNumber=:caseNumber")
	public Optional<EligibilityDetermination> getBenefitedCitizenByCaseNumber(@Param("caseNumber") Integer caseNumber);
}

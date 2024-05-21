package com.rihis.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rihis.model.CorrespondenceTrigger;

public interface CorrespondenceRepository extends JpaRepository<CorrespondenceTrigger, Integer>{

	public Page<CorrespondenceTrigger> findAllByNoticeStatus(String string, PageRequest of);
	
	@Transactional
	@Modifying
	@Query("update CorrespondenceTrigger c set c.noticeStatus='COMPLETED' where c.caseNumber=:caseNumber")
	public int updateStatus(@Param("caseNumber") Integer caseNumber);
	
	public Optional<CorrespondenceTrigger> findByCaseNumber(Integer caseNumber);

}

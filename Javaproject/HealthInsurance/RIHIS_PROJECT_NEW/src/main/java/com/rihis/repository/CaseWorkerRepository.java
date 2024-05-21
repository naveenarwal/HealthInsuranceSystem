package com.rihis.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rihis.model.CaseWorker;

@Repository
public interface CaseWorkerRepository extends JpaRepository<CaseWorker, Integer> {

	public Optional<CaseWorker> findByEmailAndIsActiveAndIsDeleted(String email,boolean active,boolean delete);
	
	@Transactional
	@Modifying
	@Query("UPDATE CaseWorker c SET c.password=:password , c.isActive=1 WHERE c.email=:email")
	public int generatePassword(@Param("password") String password,@Param("email") String email);

	@Transactional
	@Modifying
	@Query("UPDATE CaseWorker c SET c.isActive=:active WHERE c.email=:email")
	public int accountActivateAndDeactivate(@Param("email") String username,@Param("active") boolean active);

	@Transactional
	@Modifying
	@Query("UPDATE CaseWorker c SET c.isDeleted=1 WHERE c.email=:email")
	public int deleteAccount(@Param("email") String username);

	public Page<CaseWorker> findAllByRolesAndIsDeleted(String role, boolean b,Pageable pageable);

	public Optional<CaseWorker> findByEmailAndIsDeleted(String email, boolean delete);
}

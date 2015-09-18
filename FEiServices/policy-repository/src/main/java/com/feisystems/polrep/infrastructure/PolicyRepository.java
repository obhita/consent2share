package com.feisystems.polrep.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feisystems.polrep.domain.Policy;

public interface PolicyRepository extends JpaRepository<Policy, String> {

	public List<Policy> findAllByIdLike(String policyId);

}

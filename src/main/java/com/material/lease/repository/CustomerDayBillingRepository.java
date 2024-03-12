package com.material.lease.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.material.lease.model.CustomerDayBilling;

@Repository
public interface CustomerDayBillingRepository extends JpaRepository<CustomerDayBilling, Long>{
	
	List<CustomerDayBilling> findByPhoneNoContaining(String phoneNo);
	
}

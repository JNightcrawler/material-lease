package com.material.lease.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.material.lease.model.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
	
	List<Material> findByNameContaining(String name);
	
}

package com.material.lease.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.material.lease.model.ERole;
import com.material.lease.model.Role;
import com.material.lease.repository.RoleRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RoleInitializationService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@PostConstruct
	public void initializeRole() {
		Arrays.stream(ERole.values())
			.forEach(role -> {
				if(!roleRepository.existsByName(role)) {
					Role roleEntity = new Role();
					roleEntity.setName(role);
					roleRepository.save(roleEntity);
				}
			});
	}
	
}

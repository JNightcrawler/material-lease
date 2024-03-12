package com.material.lease.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.material.lease.model.Material;
import com.material.lease.repository.MaterialRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/material")
public class MaterialController {
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<List<Material>> getAllMaterials(@RequestParam(required = false) String name){
		try {
			List<Material> materials = new ArrayList<Material>();	
			
			if(name == null)	
				materialRepository.findAll().forEach(materials::add);
			else 
				materialRepository.findByNameContaining(name).forEach(materials::add);		
			
			if(materials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<List<Material>>(materials, HttpStatus.OK);
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Material> getMaterialById(@PathVariable("id") long id){
		try {
			Optional<Material> material = materialRepository.findById(id);
			
			if(material.isPresent()) {
				return new ResponseEntity<>(material.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			//Need to log the error
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Material> createMaterial(@RequestBody Material material){
		try {
			Material newMaterial = materialRepository
									.save(new Material(material.getName(), material.getPricePerDay(), material.getQuantity() ,material.getRemaining()));
			return new ResponseEntity<>(newMaterial, HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Material> updateMaterial(@PathVariable("id") long id, @RequestBody Material material){
		
		Optional<Material> retrievedMaterial = materialRepository.findById(id);
		
		if(retrievedMaterial.isPresent()) {
			Material updatedMaterial = retrievedMaterial.get();
			updatedMaterial.setName(material.getName());
			updatedMaterial.setPricePerDay(material.getPricePerDay());
			updatedMaterial.setQuantity(material.getQuantity());
			updatedMaterial.setRemaining(material.getRemaining());	
			
			return new ResponseEntity<>(materialRepository.save(updatedMaterial), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<HttpStatus> deleteMaterial(@PathVariable("id") long id){
		try {
			materialRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			//Need to log the error
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete/all")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<HttpStatus> deleteAllMaterials(){
		try {
			materialRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			//Need to log the error
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

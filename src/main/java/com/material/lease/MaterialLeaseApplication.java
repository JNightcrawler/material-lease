package com.material.lease;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;


@SpringBootApplication
@EnableEncryptableProperties
public class MaterialLeaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaterialLeaseApplication.class, args);
	}

}

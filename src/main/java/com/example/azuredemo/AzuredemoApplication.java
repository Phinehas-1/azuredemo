package com.example.azuredemo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@SpringBootApplication
public class AzuredemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzuredemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(DemoEntityRepo demoEntityRepo) {
		return args -> {
			var demoEntityList = List.of(new DemoEntity("mango"), new DemoEntity("banana"),
					new DemoEntity("orange"));
			demoEntityRepo.saveAll(demoEntityList);
		};
	}
}

@RestController("/demo")
class DemoController {
	private final DemoEntityRepo demoEntityRepo;

	public DemoController(DemoEntityRepo demoEntityRepo) {
		this.demoEntityRepo = demoEntityRepo;
	}

	@PostMapping("/entity/{name}")
	public String postMethodName(@PathVariable String name) {
		return demoEntityRepo.findByName(name).getName();
	}
}

@Entity
class DemoEntity {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(unique = true)
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public DemoEntity() {
	}

	public DemoEntity(String name) {
		this.name = name;
	}
}

interface DemoEntityRepo extends JpaRepository<DemoEntity, Integer> {
	DemoEntity findByName(String name);
}

package com.jyothi.animal.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jyothi.animal.dao.AnimalDao;
import com.jyothi.animal.exception.AnimalNameNotUniqueException;
import com.jyothi.animal.exception.AnimalNotFoundException;
import com.jyothi.animal.model.Animal;
import com.jyothi.animal.service.AnimalService;

@RestController
public class AnimalController {

	@Autowired
	private AnimalService service;

	@GetMapping("/animals")
	public List<Animal> retrieveAllAnimals() {
		return service.findAll();
	}

	@GetMapping("/animals/{name}")
	public Resource<Animal> retrieveAnimal(@PathVariable String name) {
		Animal animal = service.findOne(name);

		if (animal == null)
			throw new AnimalNotFoundException("name-" + name);

		Resource<Animal> resource = new Resource<>(animal);

		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllAnimals());

		resource.add(linkTo.withRel("all-animals"));

		// HATEOAS

		return resource;
	}

	@DeleteMapping("/animal/{name}")
	public void deleteAnimal(@PathVariable String name) {
		Animal animal = service.deleteByName(name);

		if (animal == null)
			throw new AnimalNotFoundException("name-" + name);
	}

	//
	// input - details of animal
	// output - CREATED & Return the created URI

	// HATEOAS

	@PostMapping("/animals")
	public ResponseEntity<Object> createAnimal(@Valid @RequestBody Animal animal) {
		Animal savedAnimal = service.save(animal);
		if (savedAnimal == null) {
			throw new AnimalNameNotUniqueException("Animal name " + animal.getName() + " already exists in the DB");
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
				.buildAndExpand(savedAnimal.getName()).toUri();

		return ResponseEntity.created(location).build();

	}
}

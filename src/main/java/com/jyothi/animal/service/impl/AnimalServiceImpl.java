package com.jyothi.animal.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jyothi.animal.dao.AnimalDao;
import com.jyothi.animal.model.Animal;
import com.jyothi.animal.service.AnimalService;

@Component
public class AnimalServiceImpl implements AnimalService {

	private static List<Animal> animals = new ArrayList<>();

	static {
		animals.add(new Animal("Adam", "BIRD", "FLY"));
		animals.add(new Animal("Peter", "SNAKE", "SLITHER"));

	}
	@Autowired
	AnimalDao dao;

	public List<Animal> findAll() {
		return animals;
	}

	public Animal save(Animal animal) {
		return dao.save(animal);
	}

	public Animal findOne(String name) {
		return dao.findOne(name);
	}

	public Animal deleteByName(String name) {

		return dao.deleteByName(name);

	}
}

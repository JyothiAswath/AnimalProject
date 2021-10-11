package com.jyothi.animal.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jyothi.animal.dao.AnimalDao;
import com.jyothi.animal.model.Animal;

@Component
public class AnimalDaoImpl implements AnimalDao{

	private static List<Animal> animals = new ArrayList<>();

	static {
		animals.add(new Animal("Adam", "BIRD", "FLY"));
		animals.add(new Animal("Peter", "SNAKE", "SLITHER"));

	}

	public List<Animal> findAll() {
		return animals;
	}

	public Animal save(Animal animal) {
		if (findOne(animal.getName()) != null) {
			return null;
		}
		animals.add(animal);
		return animal;
	}

	public Animal findOne(String name) {
		for (Animal animal : animals) {
			if (animal.getName().equalsIgnoreCase(name)) {
				return animal;
			}
		}
		return null;
	}

	public Animal deleteByName(String name) {
		Iterator<Animal> iterator = animals.iterator();
		while (iterator.hasNext()) {
			Animal animal = iterator.next();
			if (animal.getName().equalsIgnoreCase(name)) {
				iterator.remove();
				return animal;
			}
		}
		return null;
	}

}

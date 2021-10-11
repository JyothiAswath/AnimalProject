package com.jyothi.animal.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jyothi.animal.model.Animal;

@Component
public interface AnimalDao {

	public List<Animal> findAll();

	public Animal save(Animal animal);

	public Animal findOne(String name);

	public Animal deleteByName(String name);

}

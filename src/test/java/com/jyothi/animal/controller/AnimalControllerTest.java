package com.jyothi.animal.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyothi.animal.dao.AnimalDao;
import com.jyothi.animal.dao.impl.AnimalDaoImpl;
import com.jyothi.animal.model.Animal;

@RunWith(SpringRunner.class)
@WebMvcTest(AnimalController.class)
public class AnimalControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AnimalDaoImpl dao;

	@Test
	public void givenAnimals_whenFindAll_thenReturnJsonArray() throws Exception {

		Animal adam = new Animal("Adam", "BIRD", "FLY");
		Animal peter = new Animal("Peter", "SNAKE", "SLITHER");

		List<Animal> allAnimals = Arrays.asList(adam, peter);
		when(dao.findAll()).thenReturn(allAnimals);
		mvc.perform(get("/animals").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].name", is(adam.getName())));
	}

	@Test
	public void givenAnimals_whencreate_thenReturnJsonArray() throws Exception {

		Animal adam = new Animal("Adam", "BIRD", "FLY");
		when(dao.save(ArgumentMatchers.any(Animal.class))).thenReturn(adam);
		mvc.perform(post("/animals").contentType(MediaType.APPLICATION_JSON).content(asJsonString(adam)))
				.andExpect(status().isCreated()).andExpect(header().exists("Location"));

	}

	@Test
	public void givenExistingAnimal_whenCreate_thenThrowException() throws Exception {

		Animal adam = new Animal("Adam", "BIRD", "FLY");
		when(dao.findOne(ArgumentMatchers.anyString())).thenReturn(adam);
		mvc.perform(post("/animals").contentType(MediaType.APPLICATION_JSON).content(asJsonString(adam)))
				.andExpect(status().is4xxClientError());

	}

	@Test
	public void givenIncorrectAnimalType_whenCreate_thenThrowException() throws Exception {

		Animal adam = new Animal("Adam", "CARNIVOR", "FLY");
		mvc.perform(post("/animals").contentType(MediaType.APPLICATION_JSON).content(asJsonString(adam)))
				.andExpect(status().is4xxClientError());

	}

	@Test
	public void givenIncorrectAnimalMove_whenCreate_thenThrowException() throws Exception {

		Animal adam = new Animal("Adam", "KANGAROO", "JUMP");
		mvc.perform(post("/animals").contentType(MediaType.APPLICATION_JSON).content(asJsonString(adam)))
				.andExpect(status().is4xxClientError());

	}

	@Test
	public void givenIncorrectAnimalTypeMoveCombination_whenCreate_thenThrowException() throws Exception {

		Animal adam = new Animal("Adam", "KANGAROO", "FLY");
		mvc.perform(post("/animals").contentType(MediaType.APPLICATION_JSON).content(asJsonString(adam)))
				.andExpect(status().is4xxClientError());

	}

	@Test
	public void givenAnimals_whenDelete_thenReturnSatusOk() throws Exception {
		when(dao.deleteByName(ArgumentMatchers.anyString())).thenCallRealMethod();
		mvc.perform(delete("/animal/Adam").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

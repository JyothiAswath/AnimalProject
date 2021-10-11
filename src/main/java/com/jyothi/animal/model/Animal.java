
package com.jyothi.animal.model;

import javax.validation.constraints.NotBlank;

import com.jyothi.animal.annotation.AnimalTypeMovementMatch;
import com.sun.istack.internal.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "All details about the animal. ")
@AnimalTypeMovementMatch(type = "type", move = "move", message = "Type and move do not match!")
public class Animal {

	@NotNull @NotBlank
	@ApiModelProperty(name = "name", required = true, notes = "name given to the animal")
	private String name;

	@NotNull
	@ApiModelProperty(name = "type", allowableValues = "BIRD, SNAKE", required = true, notes = "Same Combination of type and move is expected")
	private String type;

	@NotNull
	@ApiModelProperty(name = "move", allowableValues = "FLY, SLITHER", required = true, notes = "Same Combination of type and move is expected")
	private String move;

	protected Animal() {

	}

	public Animal(String name, String type, String move) {
		super();
		this.type = type;
		this.name = name;
		this.move = move;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	@Override
	public String toString() {
		return String.format("Animal [name=%s, type=%s, move=%s]", name, type, move);
	}

}

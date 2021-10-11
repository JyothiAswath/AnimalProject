package com.jyothi.animal.validator;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

import com.jyothi.animal.annotation.AnimalTypeMovementMatch;;

public class AnimalTypeMovementMatchValidator implements ConstraintValidator<AnimalTypeMovementMatch, Object>{

	private String type;
	private String move;

	private static Map<String,String> animalsTypeMoveMap = new HashMap<String,String>();
	static {
		animalsTypeMoveMap.put("BIRD", "FLY");
		animalsTypeMoveMap.put("SNAKE", "SLITHER");
		animalsTypeMoveMap.put("FISH", "SWIM");
		animalsTypeMoveMap.put("HUMAN", "WALK");
		animalsTypeMoveMap.put("KANGAROO", "HOP");

	}

	@Override
	public void initialize(AnimalTypeMovementMatch constraintAnnotation) {
		this.type = constraintAnnotation.type();
		this.move = constraintAnnotation.move();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		String typeValue = new BeanWrapperImpl(value)
				.getPropertyValue(type).toString().toUpperCase();
		String moveValue = new BeanWrapperImpl(value)
				.getPropertyValue(move).toString().toUpperCase();
		
		

		if (animalsTypeMoveMap.containsKey(typeValue) && moveValue.equals(animalsTypeMoveMap.get(typeValue))) {
			return true;
		}

		return false;
	}

}

package com.jyothi.animal.annotation;

import java.lang.annotation.Target;

import  java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import  java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.jyothi.animal.validator.AnimalTypeMovementMatchValidator;

@Constraint(validatedBy = AnimalTypeMovementMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AnimalTypeMovementMatch {

    String message() default "Type and move doesnt match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

    String type();

    String move();

   
}
package com.java.spring.form.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueQwnValidator implements ConstraintValidator<OwnAnnotation, String>{

	@Override
	public void initialize(OwnAnnotation arg0) {
		
	}

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		if(arg0 != null && arg0.length() > 30) {
			return false;
		}
		return true;
	}

}

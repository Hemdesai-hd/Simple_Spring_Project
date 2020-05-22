package com.java.spring.form.annotations;

@java.lang.annotation.Target(value={java.lang.annotation.ElementType.FIELD})
@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
@javax.validation.Constraint(validatedBy={UniqueQwnValidator.class})
public @interface OwnAnnotation {
	  String message() default "{javax.validation.constraints.Size.message}";
	  
	  Class<?>[] groups() default {};
	  
	  Class<?>[] payload() default {};
	  
	  int min() default 0;
	  
	  int max() default (int) 2147483647;
}

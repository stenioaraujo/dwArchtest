package org.designwizard.archmodule;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ArchModule {
	String teste = "teste";
	String value();
}
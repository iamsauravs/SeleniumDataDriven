package com.automation.core.dataprovider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface DataProviderParameter {
	String[] value();
}
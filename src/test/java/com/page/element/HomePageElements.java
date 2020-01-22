package com.page.element;

import org.openqa.selenium.support.PageFactory;

import com.setup.ModuleSetup;

public class HomePageElements extends ModuleSetup{
	
	public HomePageElements(){
		PageFactory.initElements(driver, this);
	}

}

package com.automation.core.exceptions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.setup.ModuleSetup;

public class ExceptionHandling extends ModuleSetup {
	
	JavascriptExecutor js;
	
/*
elemenClick Method :: 
	
	Purpose: This Method consists a Code which resolves the Exception faced while executing a Code Mainly in Chrome
	
	Exception: org.openqa.selenium.WebDriverException: unknown error: Element is not clickable at point (676, 442). Other element 
	would receive the click

*/
		
	public void elementClick(WebElement element) throws InterruptedException {
		
		int attempts = 0;
		try {
			js.executeScript("window.scrollTo("+element.getLocation().x+","+element.getLocation().y+")");
		} catch (WebDriverException e) {}
		
		while(attempts < 4) {
			try {
				element.click();
				break;
			} catch (WebDriverException e) {
				Thread.sleep(1000);
			}
			attempts ++;
		}
	}

}

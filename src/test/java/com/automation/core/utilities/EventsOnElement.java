package com.automation.core.utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.core.customtestng.AssertCustom;
import com.setup.ModuleSetup;

public class EventsOnElement extends ModuleSetup {

	static WebDriverWait wait = new WebDriverWait(driver,60);

	public static void clickOnElement(WebElement element){
		Tools.waitForElementToBeClickable(element);
		element.click();
	}

	public static void typeInFieldUsingSelenium(String value, WebElement element){
		element.sendKeys(value);
	}
	
	public static void clearTextBox(WebElement element){
		element.clear();
	}
	
	public static String getValueFromElementAttribute(WebElement element, String attribute){
		return element.getAttribute(attribute).trim();
	}
	
	public static void updateElementAttribute(WebElement element, String attribute, String attributeValue){
		String id = getValueFromElementAttribute(element, "id");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementById('" + id + "').setAttribute('" + attribute + "', '" + attributeValue + "')");
	}
	
	public static WebElement childNodeOfElement(WebElement parentNode, String childNode) {
		return parentNode.findElement(By.xpath("."+childNode));
	}
	
	public static void clearAndTypeInElement(WebElement element, String value){
		element.clear();
		element.sendKeys(value);
	}
	
	public static String getTextFromElement(WebElement element){
		Tools.waitForElementToBePresent(element);
		return element.getText().trim();
	}
	
	public static String getPageTitle() {
		return driver.getTitle();
	}
	
	public static int getElementSize(List<WebElement> element) {
		return element.size();
	}
	
	public static void loadPage(String url) {
		driver.get(url);
	}
	
	public static Boolean dropDownSelection(WebElement element, String value) {
		Select typeDpDown = new Select(element);
		List<String> valuesToSelect = Tools.stringToList(value);
		Boolean success = true;
		for(String str : valuesToSelect) {
			try{
				typeDpDown.selectByVisibleText(value);
			} catch(NoSuchElementException e) {
				String DropDownName = getValueFromElementAttribute(element, "name");
				AssertCustom.verifyTrue(false,"Specified Drop Down Value '" + str + "' for '" + DropDownName + 
						"' doesn't exists for selection.");
				success = false;
			}
		}
		return success;
	}
	
	public static void keyInteractionOnElement(WebElement element, Keys key) {
		element.sendKeys(key);
	}
	
	public static void customizedDropDown(List<WebElement> elements, List<String> valuesToSelect) {
		for(WebElement obj: elements) {
			for(String str: valuesToSelect) {
				if(obj.getText().equals(str))
					obj.click();
			}
		}
	}
	
	public static void searchAndClick(List<WebElement> elements, String valueToSelect) {
		for(WebElement obj : elements){
			if(obj.getText().equalsIgnoreCase(valueToSelect)){
				obj.click();
				break;
			}
		}		
	}
	
	public static void searchAndClick(List<WebElement> elements, List<String> valueToSelect) {
		for(String str : valueToSelect) {
			Boolean valueFound = false;
			for(WebElement obj : elements){
				if(obj.getText().trim().equalsIgnoreCase(str)){
					valueFound = true;
					obj.click();
					break;
				}
			}
			if(!valueFound)
				AssertCustom.verifyFalse(false, str+" isn't available for Selection.");
		}
				
	}
	
	public static boolean searchElementByText(List<WebElement> elements, String value) {
		Boolean itemFound = false;
		for(WebElement obj : elements){
			if(obj.getText().equalsIgnoreCase(value)){
				itemFound = true;
				break;
			}
		}
		return itemFound;
	}
	
}

package com.page.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.automation.core.utilities.EventsOnElement;
import com.automation.core.utilities.Tools;
import com.page.element.GlobalNavElements;

public class GlobalNavWrapper {

	GlobalNavElements globalNavElements;
	
	public GlobalNavWrapper() {
		globalNavElements = new GlobalNavElements();
	}
	
	public void clickSelectVehicleMenu(WebDriver driver) {
		EventsOnElement.clickOnElement(GlobalNavElements.selectVehicleLink);
		Tools.waitForElementToBePresent(globalNavElements.carsMinivanTabHeader);
	}
	
	public void clickCarsAndMinivanTab() {
		EventsOnElement.clickOnElement(globalNavElements.carsMinivanTabHeader);
	}
	
	public void clickTrucksHeader() {
		EventsOnElement.clickOnElement(globalNavElements.trucksTabHeader);
	}
	
	public void clickCrossoverAndSUVHeader() {
		EventsOnElement.clickOnElement(globalNavElements.crossoverSUVTabHeader);
	}
	
	public void clickHybridsAndFuelCellHeader() {
		EventsOnElement.clickOnElement(globalNavElements.hybridFuelCellTabHeader);
	}
	
	public void clickUpcomingVehicleHeader() {
		EventsOnElement.clickOnElement(globalNavElements.upcomingVehiclesTabHeader);
	}
	
	/*
	 * Returns Vehicle Names of Active Tab including Model Year and Name REMOVING ALL WHITESPACES
	 * MODELYEAR+MODELNAME
	*/
	public ArrayList<String> getVehicleList() {
		
		ArrayList<String> vehicleList = new ArrayList<String>();
		for (WebElement element : globalNavElements.vehicleListUnderActiveSlider) {
			vehicleList.add(EventsOnElement.getTextFromElement(element).toLowerCase().replaceAll("\\s", "").replaceAll("-", ""));
		}
		
		return vehicleList;
	}
	
	/*
	 * Returns Vehicle Name And Model Year in Key - Value pair
	 * Key - Vehicle Name
	 * Value - Model Year
	*/
	public Map<String, String> getVehicleNameAndModel() {
		Map<String, String> vehicleList = new HashMap<String, String>();
		String vehicleName;
		
		for (WebElement element : globalNavElements.vehicleListUnderActiveSlider) {
			vehicleName = EventsOnElement.getTextFromElement(element);
			vehicleList.put(
					vehicleName.substring(vehicleName.indexOf(' ') + 1), 
					vehicleName.substring(0, vehicleName.indexOf(' ')));
		}
		return vehicleList;
	}
	
	public ArrayList<String> mergeVehicleNameAndModelYear(ArrayList<String> vehicleNames, ArrayList<String> modelYears) {
		ArrayList<String> vehicleList = new ArrayList<String>();
		
		// Condition for UVP - where Full Name is speciifed in modelName parameter, no need to append modelYears in That Case
		if(modelYears != null) {
			int counter=0;
			for (String vehicleName : vehicleNames) {
				vehicleList.add(modelYears.get(counter)+vehicleName.replaceAll("\\s", "").replaceAll("-", ""));
				counter++;
			}
		} else {
			for (String vehicleName : vehicleNames)
				vehicleList.add(vehicleName.replaceAll("\\s", "").replaceAll("-", ""));
				
		}
		
		return vehicleList;
	}
	
}

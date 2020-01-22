package com.test;

import java.util.ArrayList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automation.core.customtestng.AssertCustom;
import com.automation.core.utilities.ReadJSON;
import com.page.wrapper.CommonWrapper;
import com.page.wrapper.GlobalNavWrapper;
import com.page.wrapper.HomePageWrapper;
import com.setup.ModuleSetup;

public class TOY_13613_GlobalNav_SelectVehicle_Category_Vehicles extends ModuleSetup{
	
	ReadJSON readJson;
	HomePageWrapper homePageWrapper;
	GlobalNavWrapper globalNavWrapper;
	CommonWrapper commonWrapper;
	
	ArrayList<String> actualVehicleList;
	ArrayList<String> expectedVehicleName;
	ArrayList<String> expectedVehicleModelYear;
	ArrayList<String> expectedVehicleList;
	
	String globalOrderDCRPath;
	
	@BeforeClass
	public void beforeTestOperations() {
		
		readJson = new ReadJSON();
		homePageWrapper = new HomePageWrapper();
		globalNavWrapper = new GlobalNavWrapper();
		commonWrapper = new CommonWrapper();
		
		globalOrderDCRPath = commonWrapper.getRegionNode("Staging", "dcrurl")+commonWrapper.getDCRURL("globalorder");
		
	}
	
	@Test(description="Validate Global Nav Vehicles",groups = { "functest" }) 
	public void vehicleValidation() throws Exception {
		
		commonWrapper.authenticationForEnv("Staging");
		homePageWrapper.navigateToHomePage("Staging");
		
		globalNavWrapper.clickSelectVehicleMenu(driver);
		
		// Assert Vehicles under Cars & Minivan Tab
		actualVehicleList = globalNavWrapper.getVehicleList();
		expectedVehicleName = readJson.getValuesUsingJsonPath(
				globalOrderDCRPath, 
				"$.Root.globalNav.category.[0].vehicleSelection.[*].series");
		
		expectedVehicleModelYear = readJson.getValuesUsingJsonPath(
				globalOrderDCRPath, 
				"$.Root.globalNav.category.[0].vehicleSelection.[*].modelYear");
		
		expectedVehicleList = globalNavWrapper.mergeVehicleNameAndModelYear(expectedVehicleName, expectedVehicleModelYear);
		
		if(actualVehicleList.size() != expectedVehicleList.size()) {
			AssertCustom.verifyFalse(true, " Number of expected Vehicles - "
					+expectedVehicleList.size()+" doesn't matches number of actual vehicles - "+actualVehicleList.size());
		}
		
		int counter = 0;
		for(String vehicleName : expectedVehicleList) {
			if(actualVehicleList.get(counter).compareToIgnoreCase(vehicleName) != 0) {
				AssertCustom.verifyFalse(true, "Expected Vehicle - "+vehicleName+" , Actual Vehicle - "+actualVehicleList.get(counter));
			}
			counter++;
		}
		
		// Assert Vehicles under Trucks Tab
		globalNavWrapper.clickTrucksHeader();
		
		actualVehicleList = globalNavWrapper.getVehicleList();
		expectedVehicleName = readJson.getValuesUsingJsonPath(
				globalOrderDCRPath, 
				"$.Root.globalNav.category.[1].vehicleSelection.[*].series");
		
		expectedVehicleModelYear = readJson.getValuesUsingJsonPath(
				globalOrderDCRPath, 
				"$.Root.globalNav.category.[1].vehicleSelection.[*].modelYear");
		
		expectedVehicleList = globalNavWrapper.mergeVehicleNameAndModelYear(expectedVehicleName, expectedVehicleModelYear);
		
		if(actualVehicleList.size() != expectedVehicleList.size()) {
			AssertCustom.verifyFalse(true, " Number of expected Vehicles - "
					+expectedVehicleList.size()+" doesn't matches number of actual vehicles - "+actualVehicleList.size());
		}
		
		counter = 0;
		for(String vehicleName : expectedVehicleList) {
			if(actualVehicleList.get(counter).compareToIgnoreCase(vehicleName) != 0) {
				AssertCustom.verifyFalse(true, "Expected Vehicle - "+vehicleName+" , Actual Vehicle - "+actualVehicleList.get(counter));
			}
			counter++;
		}
		
		// Assert Vehicles under Crossover's & SUV Tab
		globalNavWrapper.clickCrossoverAndSUVHeader();
		
		actualVehicleList = globalNavWrapper.getVehicleList();
		expectedVehicleName = readJson.getValuesUsingJsonPath(
				globalOrderDCRPath, 
				"$.Root.globalNav.category.[2].vehicleSelection.[*].series");
		
		expectedVehicleModelYear = readJson.getValuesUsingJsonPath(
				globalOrderDCRPath, 
				"$.Root.globalNav.category.[2].vehicleSelection.[*].modelYear");
		
		expectedVehicleList = globalNavWrapper.mergeVehicleNameAndModelYear(expectedVehicleName, expectedVehicleModelYear);
		
		if(actualVehicleList.size() != expectedVehicleList.size()) {
			AssertCustom.verifyFalse(true, " Number of expected Vehicles - "
					+expectedVehicleList.size()+" doesn't matches number of actual vehicles - "+actualVehicleList.size());
		}
		
		counter = 0;
		for(String vehicleName : expectedVehicleList) {
			if(actualVehicleList.get(counter).compareToIgnoreCase(vehicleName) != 0) {
				AssertCustom.verifyFalse(true, "Expected Vehicle - "+vehicleName+" , Actual Vehicle - "+actualVehicleList.get(counter));
			}
			counter++;
		}
		
		// Assert Vehicles under Hybrid & Fuel Tab
		globalNavWrapper.clickHybridsAndFuelCellHeader();
		
		actualVehicleList = globalNavWrapper.getVehicleList();
		expectedVehicleName = readJson.getValuesUsingJsonPath(
				globalOrderDCRPath, 
				"$.Root.globalNav.category.[3].vehicleSelection.[*].series");
		
		expectedVehicleModelYear = readJson.getValuesUsingJsonPath(
				globalOrderDCRPath, 
				"$.Root.globalNav.category.[3].vehicleSelection.[*].modelYear");
		
		expectedVehicleList = globalNavWrapper.mergeVehicleNameAndModelYear(expectedVehicleName, expectedVehicleModelYear);
		
		if(actualVehicleList.size() != expectedVehicleList.size()) {
			AssertCustom.verifyFalse(true, " Number of expected Vehicles - "
					+expectedVehicleList.size()+" doesn't matches number of actual vehicles - "+actualVehicleList.size());
		}
		
		counter = 0;
		for(String vehicleName : expectedVehicleList) {
			if(actualVehicleList.get(counter).compareToIgnoreCase(vehicleName) != 0) {
				AssertCustom.verifyFalse(true, "Expected Vehicle - "+vehicleName+" , Actual Vehicle - "+actualVehicleList.get(counter));
			}
			counter++;
		}

		// Assert Vehicles under Upcoming Vehicles
		globalNavWrapper.clickUpcomingVehicleHeader();
		
		actualVehicleList = globalNavWrapper.getVehicleList();
		expectedVehicleName = readJson.getValuesUsingJsonPath(
				globalOrderDCRPath, 
				"$.Root.globalNav.UpcomingVehicles.vehicle.[*].modelName");
		
		expectedVehicleList = globalNavWrapper.mergeVehicleNameAndModelYear(expectedVehicleName, null);
		
		if(actualVehicleList.size() != expectedVehicleList.size()) {
			AssertCustom.verifyFalse(true, " Number of expected Vehicles - "
					+expectedVehicleList.size()+" doesn't matches number of actual vehicles - "+actualVehicleList.size());
		}
		
		counter = 0;
		for(String vehicleName : expectedVehicleList) {
			if(actualVehicleList.get(counter).compareToIgnoreCase(vehicleName) != 0) {
				AssertCustom.verifyFalse(true, "Expected Vehicle - "+vehicleName+" , Actual Vehicle - "+actualVehicleList.get(counter));
			}
			counter++;
		}
	}

	@AfterTest
	public void afterTest(){
		driver.quit();
	}
}

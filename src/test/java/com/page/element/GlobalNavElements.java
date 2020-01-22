package com.page.element;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.setup.ModuleSetup;

public class GlobalNavElements extends ModuleSetup {

	public GlobalNavElements(){
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[contains(.,'Select Vehicle')]")
	public static WebElement selectVehicleLink;
	
	@FindBy(xpath="//button[contains(.,'Cars & Minivan')]")
	public WebElement carsMinivanTabHeader;
	
	@FindBy(xpath="//button[contains(.,'Trucks')]")
	public WebElement trucksTabHeader;
	
	@FindBy(xpath="//button[contains(.,'Crossovers & SUVs')]")
	public WebElement crossoverSUVTabHeader;
	
	@FindBy(xpath="//button[contains(.,'Hybrids & Fuel Cell')]")
	public WebElement hybridFuelCellTabHeader;
	
	@FindBy(xpath="//button[contains(.,'Upcoming Vehicles')]")
	public WebElement upcomingVehiclesTabHeader;
	
	@FindBy(xpath="//li[@class='js_slide active']//p[@class='model']")
	public List<WebElement> vehicleListUnderActiveSlider;

}

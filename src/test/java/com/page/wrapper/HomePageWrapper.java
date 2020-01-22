package com.page.wrapper;

import com.automation.core.utilities.EventsOnElement;
import com.automation.core.utilities.ReadXMLFile;
import com.automation.core.utilities.Tools;
import com.page.element.HomePageElements;

public class HomePageWrapper {

	HomePageElements homePageElements = new HomePageElements();
	ReadXMLFile readXML = new ReadXMLFile("config/regions.xml");
	
	public String getHomeTitle(){
		return EventsOnElement.getPageTitle();
	}
	
	public void navigateToHomePage(String envName) {
		String homeURL = readXML.getKeyValue(envName, "url");
		EventsOnElement.loadPage(homeURL);
		Tools.waitForPageLoad();
	}
	
}

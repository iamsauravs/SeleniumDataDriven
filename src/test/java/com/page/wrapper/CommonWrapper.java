package com.page.wrapper;

import com.automation.core.utilities.EventsOnElement;
import com.automation.core.utilities.ReadXMLFile;
import com.automation.core.utilities.Tools;

public class CommonWrapper {
	
	ReadXMLFile readXML;
	
	public void authenticationForEnv(String envName) {
		readXML = new ReadXMLFile("config/regions.xml");
		String url = readXML.getKeyValue(envName, "loginurl");
		EventsOnElement.loadPage(url);
		Tools.waitForPageLoad();
	}
	
	public String getDCRURL(String dcrName) {
		readXML = new ReadXMLFile("config/dcrList.xml");
		return readXML.getKeyValue("dcrlist", "globalorder");
	}
	
	public String getRegionNode(String regionName, String nodeName) {
		readXML = new ReadXMLFile("config/regions.xml");
		return readXML.getKeyValue(regionName, nodeName);
	}

}

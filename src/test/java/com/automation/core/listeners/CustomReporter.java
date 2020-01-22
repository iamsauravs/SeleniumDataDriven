package com.automation.core.listeners;

import java.util.List;

import org.testng.ISuite;
import org.testng.xml.XmlSuite;
import org.uncommons.reportng.HTMLReporter;

import com.automation.core.constants.*;
import com.automation.core.utilities.SendMail;
import com.automation.core.constants.Constants;

public class CustomReporter extends HTMLReporter {

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectoryName) {
		super.generateReport(xmlSuites, suites, outputDirectoryName);

		try {
			SendMail.execute(Constants.REPORT_ZIP_FILENAME);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

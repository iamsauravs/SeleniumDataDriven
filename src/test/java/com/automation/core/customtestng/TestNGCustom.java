package com.automation.core.customtestng;

//Author 

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.automation.core.utilities.Tools;


public class TestNGCustom extends TestListenerAdapter {

	// Take screen shot only for failed test case
	@Override
	public void onTestFailure(ITestResult tr) {
		// Capture screenshot on every failur
		if (!(tr.getThrowable() == null)) {

		//	CommonFunctions.ScreenShot(tr.getThrowable(), tr);
			Tools.CaptureScreenShotAndPutInResult(tr);
		}
		// CommonFunctions.xScreenshot(tr.getThrowable(),tr,AdminSetup.driver);}
		try {

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		// p2pZions.PageObjects.UtilityScript.xScreenShot();
		try {
			// Specimen.PageObjects.UtilityScript.xUpdateTestDetails("SKIPPED");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		// p2pZions.PageObjects.UtilityScript.xScreenShot();
		try {
			// Specimen.PageObjects.UtilityScript.xUpdateTestDetails("PASS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package com.automation.core.utilities;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.automation.core.constants.Constants;
import com.setup.ModuleSetup;

public class Tools extends ModuleSetup{

	private static final Logger log = Logger.getLogger(Tools.class);
	static WebDriverWait wait;
	
	public static String generateRandomFilename(Throwable arg0) {
		Calendar c = Calendar.getInstance();
		String filename = arg0.getMessage();
		log.info("screen capture file name " + filename);
		int i = filename.indexOf('\n');
		filename = filename.substring(0, i).replaceAll("\\s", "_")
				.replaceAll(":", "")
				+ ".jpg";
		filename = "" + c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH)
		+ "-" + c.get(Calendar.DAY_OF_MONTH) + "-"
		+ c.get(Calendar.HOUR_OF_DAY) + "-" + c.get(Calendar.MINUTE)
		+ "-" + c.get(Calendar.SECOND) + "-" + filename;
		return filename;
	}

	public static String generateRandomNumber() {
		Calendar c = Calendar.getInstance();
		String str = String.valueOf(c.get(Calendar.YEAR))
				+ String.valueOf(c.get(Calendar.MONTH))
				+ String.valueOf(c.get(Calendar.DAY_OF_MONTH))
				+ String.valueOf(c.get(Calendar.HOUR_OF_DAY))
				+ String.valueOf(c.get(Calendar.MINUTE))
				+ String.valueOf(c.get(Calendar.SECOND));
		log.info("str" + str);
		return str;
	}

	public static String generateTenRandomNumber() {
		Random rand = new Random();
		return String.valueOf(rand.nextInt(Integer.MAX_VALUE));
	}

	public static String getMessageForImageFile(Throwable arg0) {
		String filename = arg0.getMessage();
		log.info("getmsgimage " + filename);
		int filelenght = filename.length();
		if (filelenght > 40) {
			filelenght = 10;
		}

		if (filename.indexOf('\n') > 0) {
			filename = filename.replaceAll(
					"^[.\\\\/:*?\"<>|,]?[\\\\/:*?\"<>|]*", "");
		}
		filename = filename.substring(0, filelenght);
		return filename;
	}

	public static void ScreenShot(Throwable args, ITestResult tr)
			throws InterruptedException {
		try {

			String NewFileNamePath;

			java.awt.Dimension resolution = Toolkit.getDefaultToolkit()
					.getScreenSize();
			Rectangle rectangle = new Rectangle(resolution);

			File directory = new File(".\\ScreenCapture");
			DateFormat dateFormat = new SimpleDateFormat("MMM_dd_yyyy__hh_mm_ssaa");
			Date date = new Date();
			NewFileNamePath = directory.getCanonicalPath()
					+ "\\Failed\\"
					+ tr.getName() + "_" + "_" + dateFormat.format(date) + "_"
					+ ".png";
			Robot robot = new Robot();
			BufferedImage bi = robot.createScreenCapture(new Rectangle(rectangle));
			ImageIO.write(bi, "png", new File(NewFileNamePath));
			Reporter.setCurrentTestResult(tr);
			Reporter.log("<a href=\"" + NewFileNamePath + "\">ScreenShot</a>");
			Wait(3000);

		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void waitForElementToBeClickable(WebElement element){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public static void waitForElementToBePresent(WebElement element){
		wait=new WebDriverWait(driver,30);
		List<WebElement> elements=new ArrayList<WebElement>();
		elements.add(element);
		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	public static void waitForElementToBeInvisible(WebElement element) {
		wait=new WebDriverWait(driver,30);
		List<WebElement> elements=new ArrayList<WebElement>();
		elements.add(element);
		wait.until(ExpectedConditions.invisibilityOfAllElements(elements));
	}
	
	public static void Wait(int MilliSec) {
		try{
			Thread.sleep(MilliSec);
		}catch(InterruptedException e){}
	}

	public static boolean isTextPresent(WebDriver driver, String text) {
		WebElement tempText = driver.findElement(By.tagName("body"));
		// log.info("temp text " + tempText.getText());
		if (tempText.getText().contains(text)) {
			return true;
		} else {
			return false;
		}

	}
	
	public static Boolean isSelected(WebElement element) {
		return element.isSelected();
	}
	
	public static Boolean isEnabled(WebElement element) {
		return element.isEnabled();
	}

	public static boolean isTextPresentNew(WebDriver driver, String text, By by)
			throws InterruptedException {
		WebElement tempText = driver.findElement(by);

		for (int second = 0; second < 20; second++) {
			try {

				if (tempText.isDisplayed()) {
					break;
				}
			} catch (Exception ignore) {
			}
			Tools.Wait(1000);
		}

		if (tempText.getText().contains(text)) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean isTextPresentNew(WebDriver driver, String text, WebElement tempText)
			throws InterruptedException {
		//		WebElement tempText = driver.findElement(by);

		for (int second = 0; second < 20; second++) {
			try {

				if (tempText.isDisplayed()) {
					break;
				}
			} catch (Exception ignore) {
			}
			Tools.Wait(1000);
		}

		if (tempText.getText().contains(text)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Boolean isElementPresentInList(List<WebElement> elements, String text) {
		Boolean present = false;
		
		for(WebElement obj : elements) {
			if(obj.getText().equalsIgnoreCase(text)) {
				present = true;
				break;
			}
		}
		return present;
	}

	public static void xScreenshot(Throwable args, ITestResult tr,
			WebDriver driver) {

		Calendar calendar = Calendar.getInstance();

		// Get the users home path and append the screen shots folder
		// destination
		String userHome = System.getProperty("user.dir");
		String screenShotsFolder = userHome + "\\Failed\\";

		// The file includes the the test method and the test class
		String testMethodAndTestClass = tr.getMethod().getMethodName() + "("
				+ tr.getTestClass().getName() + ")";

		System.out
		.println(" *** This is where the capture file is created for the Test \n"
				+ testMethodAndTestClass);

		// Create the filename for the screen shots
		String filename = screenShotsFolder + testMethodAndTestClass + "-"
				+ calendar.get(Calendar.YEAR) + "-"
				+ calendar.get(Calendar.MONTH) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "-"
				+ calendar.get(Calendar.HOUR_OF_DAY) + "-"
				+ calendar.get(Calendar.MINUTE) + "-"
				+ calendar.get(Calendar.SECOND) + "-"
				+ calendar.get(Calendar.MILLISECOND) + ".png";

		// Take the screen shot and then copy the file to the screen shot folder

		// File scrFile =
		// ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		// File scrFile = ((Screenshot)driver).getScreenshotAs(file);

		try {
			FileUtils.copyFile(scrFile, new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void captureScreen(WebDriver driver) {

		String path;
		try {
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			File source = ((TakesScreenshot) augmentedDriver)
					.getScreenshotAs(OutputType.FILE);
			path = "C:\\Automation\\" + source.getName();
			FileUtils.copyFile(source, new File(path));
		} catch (IOException e) {
			path = "Failed to capture screenshot: " + e.getMessage();
		}
	}

	public static void WebdDriverScreenShot(Throwable args, ITestResult tr,
			WebDriver driver) throws InterruptedException {
		try {

			String NewFileNamePath;

			java.awt.Dimension resolution = Toolkit.getDefaultToolkit()
					.getScreenSize();
			Rectangle rectangle = new Rectangle(resolution);

			// Get the dir path
			File directory = new File(".\\ScreenCapture");
			DateFormat dateFormat = new SimpleDateFormat(
					"MMM_dd_yyyy__hh_mm_ssaa");
			// get current date time with Date()
			Date date = new Date();

			NewFileNamePath = directory.getCanonicalPath()
					+ "\\Failed\\"
					+ tr.getName() + "_" + "_" + dateFormat.format(date) + "_"
					+ ".png";
			Robot robot = new Robot();
			BufferedImage bi = robot.createScreenCapture(new Rectangle(
					rectangle));
			ImageIO.write(bi, "png", new File(NewFileNamePath));

			WebDriver wd = new Augmenter().augment(driver);
			File f = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(f, new File(NewFileNamePath));
			} catch (IOException e) {
				e.printStackTrace();
			}

			NewFileNamePath = "<a href=" + NewFileNamePath + ">ScreenShot"
					+ "</a>";
			// Place the reference in TestNG web report
			Reporter.log(NewFileNamePath);
			Wait(3000);

		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// @Override
	public static void CaptureScreenShotAndPutInResult(ITestResult tr) {
		try {

			String parent = Constants.REPORT_SOURCE_PATH;
			String imgfile = Constants.SCREENCAPTURE_FOLDER_NAME + new SimpleDateFormat("MM-dd-yyyy_HH-ss-SSS").format(new Date().getTime())+ ".gif";
			File scrFile = null;
			if(tr.getInstance().toString().contains("com.test.UserLogin")){
				scrFile = ((TakesScreenshot) ModuleSetup.driver).getScreenshotAs(OutputType.FILE);
			}
			FileUtils.copyFile(scrFile, new File(parent, imgfile));
			String userDirector = System.getProperty("user.dir") + "/";
			log.info(userDirector);
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			Reporter.log("<a href=" + imgfile + ">" + "<img src=" + imgfile
					+ " alt=\"\"" + "height='100' width='100'/> " + "<br />");
			Reporter.setCurrentTestResult(null);
		} catch (Exception a) {
			a.printStackTrace();
		}
	}

	public static String CaptureScreenShot() {
		String imgfile="";
		try {
			String parent = "target/test-classes/com/intellih/resources/";
			imgfile = new SimpleDateFormat("MM-dd-yyyy_HH-ss-SSS").format(new Date().getTime())+ ".png";
			File scrFile = null;
			scrFile = ((TakesScreenshot) ModuleSetup.driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(parent, imgfile));
		} catch (Exception a) {
			a.printStackTrace();
		}
		return imgfile;
	}

	public static boolean isElementPresent(By by)  
	{  
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);  
		try  
		{  
			driver.findElement(by);  
			return true;  
		}  
		catch(Exception e)  
		{  
			return false;  
		}  
		finally  
		{  
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);  
		}  
	}

	public static boolean isElementPresent(By by, int seconds)  
	{  
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);  
		try  
		{  
			driver.findElement(by);  
			return true;  
		}  
		catch(Exception e)  
		{  
			return false;  
		}  
		finally  
		{  
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);  
		}  
	}

	public static boolean isElementPresent(WebElement element)  
	{  
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);  
		try  
		{  
			element.isDisplayed();
			return true;  
		}  
		catch(Exception e)  
		{  
			return false;  
		}  
		finally  
		{  
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);  
		}  
	}

	public static String getRunConfiguration(String testName) throws BiffException, IOException
	{ 
		String value;
		ExcelSheetDriver xlsUtil = new ExcelSheetDriver(ReadPropertiesFile.getProperty(
				"TestDataPath")+"\\TestDataConfig.xls", "RunSetting");
		value = xlsUtil.retrieveCellData("RunPreference", testName);
		return value;
	}

	//Wait till page load	
	public static void waitForPageLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}

	public static void waitWhileElementHasAttributeValue(WebDriver driver,String locator, String attribute, String value) throws InterruptedException {
		Tools.Wait(500);
		int timeout = 30;
		while(driver.findElement(com.automation.core.utilities.LocatorStrategy.getLocatorMethod(locator)).getAttribute(attribute).equalsIgnoreCase(value)) {
			if(timeout > 0) {
				timeout--;
				Tools.Wait(1000);
			}
		}
	}

	public static String getDate(){
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		return df.format(new Date());
	}

	public static String getDate2(){
		SimpleDateFormat df = new SimpleDateFormat("MMM, dd yyyy");
		return df.format(new Date());
	}

	public static String random(){
		String string = RandomStringUtils.random(4, false, true);
		string = RandomStringUtils.randomAlphabetic(4);
		return string;
	}

	public static void slider(String loc, WebDriver driver) {
		try{
			Thread.sleep(2000);
			WebElement slider = driver.findElement(By.xpath(loc));
			int width=slider.getSize().getWidth();
			Actions move = new Actions(driver);
			move.dragAndDropBy(slider, (width-5), 0).click();
			move.build().perform();
		}catch(Exception e){}
	}

	public static ExpectedCondition<WebElement> presenceOfElement(final WebElement element) {
		return new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return element.isDisplayed()? element : null;
			}

			@Override
			public String toString() {
				return "presence of any element by " + element;
			}
		};
	}

	public static String[] splitStringReturnStringArray(String value, String regex){
		return value.split(regex);
	}

	public static String getCurrentDate(){
		SimpleDateFormat df = new SimpleDateFormat("MMM dd,yyyy");
		return df.format(new Date());
	}
	
	public static List<String> stringToList(String inputString) {
		List<String> outputString = Arrays.asList(inputString.split(","));
		return outputString;
	}
	
	// This method is to Overcome the WebDriver Exception -> 
	// Element is not clickable at point (691, 333). Other element would receive the click
	public static void elementClick(WebElement element) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor)driver;
					
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
	
	public static void setAttributeOfTag(WebElement element, String attribute, String newValue) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attribute, newValue);
	}
	
	public static void scrollIntoView(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public static void elementMoveByOffset(WebElement element,int xOffSet, int yOffSet) {
		Actions builder = new Actions(driver);
		builder.clickAndHold(element).moveByOffset(xOffSet, yOffSet).release().build().perform();
		
	}
}
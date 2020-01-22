package com.setup;

import java.net.MalformedURLException;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import com.automation.core.constants.Constants;
import com.automation.core.utilities.ReadPropertiesFile;

@Listeners({ com.automation.core.customtestng.TestNGCustom.class,com.automation.core.customtestng.TestNGCustomeListener.class })

public class ModuleSetup {

	protected static String TESTDATAPATH;
	protected static String BROWSER;// Browser used for test script running
	protected static ReadPropertiesFile properties = new ReadPropertiesFile();
	public static WebDriver driver;
	
	@BeforeClass(alwaysRun = true)
	public void setupWebDriver() throws MalformedURLException {
		try{
			PropertyConfigurator.configure(Constants.CONFIG_LOG4J_PROPERTY_PATH);
			BROWSER = ReadPropertiesFile.getProperty(Constants.BROWSER);
			
			System.out.println("Browser:"+BROWSER);
			
			if (BROWSER.equals("FF")) {
				driver = new FirefoxDriver();
			} else if (BROWSER.equals("CH")) {
				String path = "lib/chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", path);
				driver = new ChromeDriver();
			} else if (BROWSER.equals("IE")) {
				String path = "lib/IEDriverServer.exe";
				System.setProperty("webdriver.ie.driver", path);
				driver = new InternetExplorerDriver();
			}
			else {
				throw new RuntimeException("Browser type unsupported");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		driver.manage().window().maximize();
	}

}

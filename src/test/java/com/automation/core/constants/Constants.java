
package com.automation.core.constants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Constants {
	//PATH-File names
	public static final String REPORT_SOURCE_PATH= "target"+File.separator+"surefire-reports"+File.separator+"html";
	public static final String REPORT_ZIP_FILENAME = "Toyota.zip";
	public static final String REPORT_ZIP_PATH = "ReportZip"+File.separator+REPORT_ZIP_FILENAME;
	public static final String CONFIG_PROPERTY_FILENAME = "config"+File.separator+"Config.properties";
	public static final String CONFIG_LOG4J_PROPERTY_PATH = "config"+File.separator+"log4j.properties";
	
	//Constants
	public static final String XLS_DATA = "XLS_DATA";
	public static final String USERS_LIST = "USERS_LIST";
	public static final String BROWSER = "BROWSER";
	public static final String PROJECT_URL= "Project_URL";
	public static final String PracticePortal_URL= "PracticePortal_URL";
	
	//Mail Constants
	public static final String EMAIL_TO_LIST = "EMAIL_TO_LIST";
	public static final String EMAIL_CC_LIST = "EMAIL_CC_LIST";
	public static final String EMAIL_BCC_LIST = "EMAIL_BCC_LIST";
	public static final String USER_NAME = "USER_NAME";
	public static final String PASSWORD = "PASSWORD";
	public static final String EMAIL_BODY_OVERVIEW_HTML_PAGE = "target/surefire-reports/html/overview.html";
	public static final String EMAIL_SUBJECT = "Plumslice Automation Test Report";
	public static final String PORT = "587";
	public static final String SMTP_GMAIL_COM = "smtp.gmail.com";
	
	public static final String SCREENCAPTURE_FOLDER_NAME = "ScreenCapture/";
	public static final String FAILED_IMAGE_FILE_NAME = SCREENCAPTURE_FOLDER_NAME + new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new Date().getTime())+ ".gif";
}

package Util;

public class Constants {
//**ADD ALL THE DATA THAT IS CONSTANT  
	
	
	
	//path
	public static final String PROPERTIES_FILE_PATH=System.getProperty("user.dir")+"/src/test/java/Util/config.properties";
	public static final String SCREENSHOT_PATH=System.getProperty("user.dir")+"/ExecutionInfo/Screenshots";                            
	
	
	//runmodes
	public static final String RUNMODE_YES="y";
	public static final String RUNMODE_NO="n";
	
	//browsertype
	public static final String MOZILLA = "mozilla";
	public static final String CHROME = "chrome";
	public static final String IE = "internet explorer";
	
	//environments
	public static final String TEST = "test";
	public static final String STAGE = "stage";
	
	
	//Assertion values
	public static final String PASS = "pass";
	public static final String TRUE = "true";
	
	
	//Error messages
	public static final String OPENBROWSER_ERROR = "ERROR:Failed to open browser";
	public static final String NAVIGATE_ERROR = "ERROR : Failing to navigate";
	public static final String LOCATOR_ERROR = "ERROR: Invalid locator";
	public static final String FIND_ELEMENT_ERROR = "ERROR:Unable to find element";
	public static final String GENERAL_ERROR = "ERROR";
	//Failure messages
	public static final String ELEMENT_NOT_FOUND_FAILURE = "FAIL: element not found";
	public static final String TITLE_MISMATCH_FAILURE = "FAIL: mismatch in title";
	public static final String LOGIN_FAILURE = "FAIL:unable to login with default username/passwrd";
	public static final String CLICKANDWAIT_FAILURE = "FAIL:unable to click and wait";
	
	


	
	
	
	
}

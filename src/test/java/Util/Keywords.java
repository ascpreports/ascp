package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

//** THIS IS A HELPER CLASS THAT WILL BE CALLED WHILE EXECUTING EACH OF THE TEST CASE

public class Keywords {
    Properties prop;
	WebDriver driver;
	WebDriverWait expWait;
	static Keywords k;
	Map<String,String> hash = new HashMap<String,String>();
	HashMap<String,WebDriver> map;
	public String Browser,Sandbox,currentTestCaseName;
	public static Logger Auto_LOGS=Logger.getLogger("devpinoyLogger");
	
	public Keywords() throws IOException {//Constructor
    	//initialize the browser to NULL
    	map =new HashMap<String, WebDriver>();
    	map.put(Constants.MOZILLA,null);
    	map.put(Constants.CHROME,null);
    	//initialize the properties
    	prop = new Properties();
    	FileInputStream fs = new FileInputStream(Constants.PROPERTIES_FILE_PATH);
		prop.load(fs);
		}
 
    

	public static Keywords getInstance() {//singleton class : to ensure only one driver exists for each browser type
		if (k==null){
			try {
				k = new Keywords();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return k;
	}

 //-----------------------Setup--------
    public void InitialSetup(String sandbox, String browser) {
    	log("-------INITIAL SETUP-----");
    	Sandbox=sandbox;
    	Browser=browser;
    	log("Sandbox ==>"+Sandbox);
    	log("Browser==>"+Browser);
    	
    }
    
 //---------------------------------Generic Keywords-----------------------------------------   
	public String openBrowser(String browserType){
		log("Start OpenBrowser : "+ browserType);
		try{
			
			if(map.get(browserType.toLowerCase()) == null){
				
				if(browserType.equalsIgnoreCase(Constants.MOZILLA)){
					driver = new FirefoxDriver();
				    System.out.println("browser opened");}
				else if(browserType.equalsIgnoreCase(Constants.CHROME)){
					System.setProperty("webdriver.chrome.driver",prop.getProperty("chromedriverexe") );
					driver = new ChromeDriver();
				}
				map.put(browserType.toLowerCase(), driver);
			}
			else{ // flag
				driver= map.get(browserType.toLowerCase());
			}
		
	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    //driver.manage().window().maximize();
   }
   catch(Exception e){
	reportError(Constants.OPENBROWSER_ERROR + browserType);  
	 return null;
   }
	log("End OpenBrowser with status -> "+Constants.PASS);
    return Constants.PASS;
	}
	
	
	
	public String navigate( String Env,String Title){
	//System.out.println("inside navigate");
	//System.out.println("---"+Env);
	log("Navigate to url: "+prop.getProperty("url"+Env));	
	try{
	String url=prop.getProperty("url"+Env);//making it dynamic
	driver.get(url);
	String actualTitle= driver.getTitle();
	String expectedTitle=prop.getProperty(Title);
	if (!actualTitle.equals(expectedTitle))//check for failure
	reportFailureAndStop(Constants.TITLE_MISMATCH_FAILURE+"Expected"+expectedTitle + "Actual : "+actualTitle);
	}catch(Exception e){
		reportError(Constants.NAVIGATE_ERROR+e.getMessage());  	
	}
	log("End Navigation with status : "+Constants.PASS);
	return Constants.PASS;
	}
	
	
	
	public String click(String objectKey){
		log("Click : "+objectKey);
		element(objectKey).click();
		log("End Click with status"+Constants.PASS);
		return Constants.PASS;
	}
	
	public String getText(String objectKey){
		log("Get Text :  "+objectKey);
		return element(objectKey).getText();
	}
	
	public String getAttribute(String objectKey,String tag){
		log("getting Attribute ->"+objectKey);
		return element(objectKey).getAttribute(tag);
	}
	
	public String input(String objectKey, String data){
		log(" Input : "+objectKey+","+data);
		log("Input Value-->"+prop.getProperty(data));
		element(objectKey).sendKeys(prop.getProperty(data));
		
		return Constants.PASS;
	}
	
	public String clickAndWait(String object){
		log("Starting function clickAndWait"+ object);
		
		try{
			Thread.sleep(3000);
			String temp[] = object.split(",");
			String elementToBeClicked=temp[0];
			String elementToBeVisible=temp[1];
			
			for(int i=0;i<5;i++){
				element(elementToBeClicked).click();
				if(isElementPresent(elementToBeVisible,5) && element(elementToBeVisible).isDisplayed()){
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
							e.printStackTrace();
					}				return Constants.PASS;
				}else{
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		}
		}catch(Exception e){
			reportError(Constants.GENERAL_ERROR +"clickAndWait" );
		}
		
		reportFailureAndStop(Constants.CLICKANDWAIT_FAILURE+ object);
		return null;
	}
	
	public WebElement element(String objectKey){
		log("Find Element : "+objectKey);
		try{
		if (objectKey.endsWith("_id")){
			return driver.findElement(By.id(prop.getProperty(objectKey)));}
		else if (objectKey.endsWith("_name")){
			//System.out.println(driver.findElement(By.name(prop.getProperty(objectKey))));
			return driver.findElement(By.name(prop.getProperty(objectKey)));}
		else if (objectKey.endsWith("_xpath"))
			return driver.findElement(By.xpath(prop.getProperty(objectKey)));
		else{			
		reportError(Constants.LOCATOR_ERROR+objectKey);
		return null;}
		}
		catch(NoSuchElementException e){
			reportFailureAndStop(Constants.ELEMENT_NOT_FOUND_FAILURE +objectKey);
		}catch(Exception e){
			reportError(Constants.FIND_ELEMENT_ERROR+objectKey);
		}
		return null;
	
	}
	
	public boolean isElementPresent(String objectKey,int timeout){
		driver.manage().timeouts().implicitlyWait(timeout,TimeUnit.SECONDS);
		int size=0;
		if (objectKey.endsWith("_id"))
			size= driver.findElements(By.id(prop.getProperty(objectKey))).size();
		else if (objectKey.endsWith("_name"))
			size= driver.findElements(By.name(prop.getProperty(objectKey))).size();
		else if (objectKey.endsWith("_xpath"))
			size= driver.findElements(By.xpath(prop.getProperty(objectKey))).size();
		
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);//change the timeout back to 20 sec after completion
		if (size!=0)
			return true;
		else 
		return false;
		
	}
	
	public void waitForPageToLoad(){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		while (!js.executeScript("return document.readyState").toString().equals("complete"))//not complete
		try {
			log("Waiting for page to load");
			Thread.sleep(8000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void ExplicitWait(String element,int time){
		expWait = new WebDriverWait(driver,time);
		System.out.println("Explicit Wait : "+element);
		try{
			if (element.endsWith("_id"))
				expWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(prop.getProperty(element))));
			else if (element.endsWith("_name"))
				//System.out.println(driver.findElement(By.name(prop.getProperty(objectKey))));
				expWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(prop.getProperty(element))));
			else if (element.endsWith("_xpath"))
				expWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty(element))));
			else{			
			reportError(Constants.LOCATOR_ERROR+element);
			}
		}
			catch(NoSuchElementException e){
				reportFailureAndStop(Constants.ELEMENT_NOT_FOUND_FAILURE +element);
			}catch(Exception e){
				reportError(Constants.FIND_ELEMENT_ERROR+element);
			}
	  }//end of Exp wait
	
	
	public void wait(String time){
		try{
			System.out.println("waiting..");
			Thread.sleep(Integer.parseInt(time)*1000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void closeBrowser(String browserName){
		driver.quit();
		map.put(browserName.toLowerCase(),null);
	}
	
	//---------------------------------APP keywords--------------------------
	
	//---Sample Method : Userlogin---
	
	public Map<String,String> AdminLogin() throws InterruptedException{	
		openBrowser(Browser);
		
		navigate(Sandbox,"homePageTitle");
		ExplicitWait("username_id",30);	
		input("username_id","Username");
		input("password_id","Password");	
		click("loginButton_id");
		log("Admin :"+ prop.getProperty("Username")+" logged in");
		hash.put("AdminLogin",Constants.PASS);
		return hash;
	 	}
	

public Map<String, String> Method1() {
	//write the selenium code & capture the result and pass it in the hash
	hash.put("key1", "Key 1");//
	return hash;
}

public Map<String, String> Method2() {
	hash.put("key2", "Key 2");//
	return hash;
}
		
	
//--------------------------------------LOGGER----------------------------------	
	
	
	public void log(String message){
		System.out.println(message);
		Auto_LOGS.debug(message);	
	}

	
	
//--------------------------------------UTILITY----------------------------------------
	
	 
	 public void reportError(String msg){
		 	log(msg);
		 	Assert.fail(msg);
	
	 	}

	 private void reportFailureAndStop(String Errmsg){
		 	log(Errmsg);
		 	Assert.fail(Errmsg);
		 	//add screen shot latter
	 	}

   public void takeScreenShot(){
	String filePath=Constants.SCREENSHOT_PATH+currentTestCaseName+".png";
	File targetFile= new File(filePath);
	File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    try {
		FileUtils.copyFile(srcFile, targetFile);
	} catch (IOException e) {
		e.printStackTrace();
	}
	}//end of screen shot









	
}



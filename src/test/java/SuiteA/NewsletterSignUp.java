package SuiteA;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import Util.Keywords;

import Util.ErrorUtil;

public class NewsletterSignUp {
  private WebDriver driver;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  Keywords app= Keywords.getInstance();

  @Test
  public void setUp() throws Exception {
	 //System.setProperty("webdriver.chrome.driver", "C:\\Users\\ksahota\\Desktop\\Automation\\WorkPlace\\chromedriver_win32\\chromedriver.exe");
	  //driver = new ChromeDriver();
	  
	  WebDriver driver = new FirefoxDriver();
	  
	   //driver = new SafariDriver();
	   
	   Thread.sleep(20000);
		
		//System.setProperty("webdriver.ie.driver", "C:\\Users\\ksahota\\Desktop\\Automation\\Eclipse Testing\\IEDriverServer_Win32_2.46.0\\IEDriverServers.exe");
		 // driver = new InternetExplorerDriver();
  
    driver.get("http://adsolutions.stage.yp.com/email-support/newsletter-sign-up?ccategory=kul&utm_campaign=kul&utm_content=kul&utm_medium=kul&utm_source=kul&utm_term=kul");
    Thread.sleep(20000);
		try{
			Assert.assertTrue(driver.findElement(By.id("newsletter-submit")).isEnabled());
			driver.findElement(By.id("newsletter-submit")).submit();
			Assert.assertEquals("...that’s not a valid email. Try again.", (driver.findElement(By.id("error-message")).getText()));
		    //Assert.assertEquals("Key 2",ActualResult.get("key2"));
		}catch(Throwable t){
			//ErrorUtil.addVerificationFailure(t);
			//app.log("Value is incorrect");
			System.out.println("Submit Failed--"+ t );
			app.log("submit Failed" + t);
		}
  }
	/*@Test
	public void setUP
		
		Assert.assertEquals("…that’s not a valid email. Try again.", (driver.findElement(By.id("error-message")).getText()));
		//Assert.assertEquals("Please Enter the valid email address", "text()");
   /*driver.findElement(By.id("newsletter-submit")).submit();
   
    driver.findElement(By.id("EmailAddress")).clear();
    driver.findElement(By.id("EmailAddress")).sendKeys("Kulwant");
    
    driver.findElement(By.id("newsletter-submit")).submit();
    driver.findElement(By.id("EmailAddress")).clear();
    driver.findElement(By.id("EmailAddress")).sendKeys("KulwantAuto@ypcom");
    driver.findElement(By.id("newsletter-submit")).submit();
    driver.findElement(By.id("EmailAddress")).clear();
    driver.findElement(By.id("EmailAddress")).sendKeys("KulwantAuto@yp.com");
    driver.findElement(By.id("newsletter-submit")).submit();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }*/
}

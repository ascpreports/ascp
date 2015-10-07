package SuiteB;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import Util.ErrorUtil;
import Util.Keywords;

public class SuiteB1 {
	Map<String,String> ActualResult = new HashMap<String,String>();
	Keywords app= Keywords.getInstance();//creates single instance of Keywords class 
	

	/** THIS IS FOR JAVA DOC. ADD THE OBJECTIVE & VALUES TO BE ASSERTED
	 * Objective: 
	 * <p>Asserts: </p>
	 * 
	 */

	@Test (priority=1)
	public void TestCase1() {
		app.log("------TESTING TEST CASE B 1-----");
		ActualResult=app.Method1();//execute the method 1 and Result can be accessed using the Map reference
		
		//add every assertion in a try-catch block 
		try{
		Assert.assertEquals("Key 1",ActualResult.get("key1"));//assert(expected, actual value)
		}catch(Throwable t){
			ErrorUtil.addVerificationFailure(t);
			app.log("Value is incorrect");
			}
		}


	/** 
	 * Objective: 
	 * <p>Asserts: </p>
	 * 
	 */

	@Test (priority=2,dependsOnMethods="TestCase1")//other option :dependsOnGroups or remove all the dependencies
	public void TestCase2() throws InterruptedException{
		app.log("------TESTING TEST CASE B 2-----");
		ActualResult=app.Method2();
		try{
		Assert.assertEquals("Key 2",ActualResult.get("key2"));
		}catch(Throwable t){
			ErrorUtil.addVerificationFailure(t);
			app.log("Value is incorrect");
			}
		}


	
}

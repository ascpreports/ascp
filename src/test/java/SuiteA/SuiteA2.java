package SuiteA;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import Util.ErrorUtil;
import Util.Keywords;

public class SuiteA2 {

	Map<String,String> ActualResult = new HashMap<String,String>();
	Keywords app= Keywords.getInstance();//creates single instance of Keywords class 
	
	/** THIS IS FOR JAVA DOC. ADD THE OBJECTIVE & VALUES TO BE ASSERTED
	 * Objective: 
	 * <p>Asserts: </p>
	 * 
	 */

	@Test (priority=1)
	public void TestCaseA11() {
		app.log("------TESTING TEST CASE A11-----");
		ActualResult=app.Method1();//execute the method 1 and Result can be accessed using the Map reference
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

	@Test (priority=2,dependsOnMethods="TestCaseA11")//other option :dependsOnGroups or remove all the dependencies
	public void TestCaseA22() throws InterruptedException{
		app.log("------TESTING TEST CASE A22-----");
		ActualResult=app.Method2();
		try{
		Assert.assertEquals("Key 2",ActualResult.get("key2"));
	}catch(Throwable t){
			ErrorUtil.addVerificationFailure(t);
			app.log("Value is incorrect");
			}
		}



}

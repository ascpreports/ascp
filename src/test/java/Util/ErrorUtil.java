package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.Reporter;
//**CUSTOMLISTENER + ERROR UTIL CLASS IS USED FOR CAPTURING FAILURES DURING THE EXECUTION OF MULTIPLE ASSERTIONS IN A TEST CASE
//ADVANTAGE: THE SCRIPT CONTINUES TO THE REMAINING ASSERTIONS EVEN IF THE PREVIOUS ASSERTION FAILS

public class ErrorUtil {
	@SuppressWarnings("rawtypes")
	private static Map<ITestResult,List> verificationFailuresMap = new HashMap<ITestResult,List>();
	private static Map<ITestResult,List> skipMap = new HashMap<ITestResult,List>();

	
	     @SuppressWarnings("unchecked")
		public static void addVerificationFailure(Throwable e) {
				@SuppressWarnings("rawtypes")
				List verificationFailures = getVerificationFailures();
				verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
				verificationFailures.add(e);
			}
		  
		  public static List<Throwable> getVerificationFailures() {
				List verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
				return verificationFailures == null ? new ArrayList() : verificationFailures;
			}

		 
		  
}
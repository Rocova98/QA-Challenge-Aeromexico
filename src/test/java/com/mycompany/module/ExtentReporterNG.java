package com.mycompany.module;

import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class ExtentReporterNG  implements IReporter{
	
	private ExtentReports extent;

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		// TODO Auto-generated method stub
		extent = new ExtentReports(System.getProperty("user.dir")+"\\reports\\ExtentReportsNG.html", true);
		
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();
			
			for(ISuiteResult res : result.values()) {
				ITestContext context = res.getTestContext();
				
				buildTestNodes(context.getPassedTests(), LogStatus.PASS);
				buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
				buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
			}
		}
		
		extent.flush();
		extent.close();
		
	}
	
	private void buildTestNodes(IResultMap tests, LogStatus status) {
		ExtentTest test;
		
		if(tests.size() > 0) {
			
			for(ITestResult result : tests.getAllResults()) {
				
				test = extent.startTest(result.getMethod().getMethodName());
				
				for(String group : result.getMethod().getGroups()) {
					test.assignCategory(group);
				}
				
				String message = "Test" + status.toString().toLowerCase() + "ed";
				
				if(result.getThrowable() != null) {
					message = result.getThrowable().getMessage();
				}
				
				test.log(status, message);
				
				extent.endTest(test);
			}
		}
	}

}

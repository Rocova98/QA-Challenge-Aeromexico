package com.mycompany.module;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.mycompany.config.Configure;


public class Listeners implements ITestListener{
	
	public static ReportUtil report = new ReportUtil();
	public static String[] testsToRunArray = new String[10];
	
	@Override
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		report.saveReport();	
	}

	@Override
	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		String path = System.getProperty("user.dir")+"\\reports\\ss";
		File directory = new File(path);
		try {
			if (!directory.exists()){
				directory.mkdirs();
			}
			FileUtils.cleanDirectory(directory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		report.startReport();
		//testsToRunArray = Configure.runFeatures();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestStart(ITestResult res) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub
		report.scenario.log(Status.PASS, "Scenario Executed");
	}
	
	
	
}
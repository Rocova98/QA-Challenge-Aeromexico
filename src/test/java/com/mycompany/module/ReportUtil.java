package com.mycompany.module;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.mycompany.config.Configure;

public class ReportUtil {

	public ExtentReports extent;

	public ExtentTest scenario;
	public ExtentTest step;


	public ExtentReports startReport() {
		String path = System.getProperty("user.dir")+"\\reports\\Report.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("QA Challenge");
		reporter.config().setDocumentTitle("Test Results");
		//reporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Application", "Aeromexico Website");
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("User name", System.getProperty("user.name"));
		extent.setSystemInfo("Java", System.getProperty("java.version"));
		extent.setSystemInfo("Browser", Configure.BROWSER);

		return extent;
	}

	public ExtentTest addScenario(String scenarioName) {
		System.out.println(scenarioName);

		scenario = extent.createTest(scenarioName);
		return scenario;
	}

	public ExtentTest addStep(String stepName) {
		step = scenario.createNode(stepName);
		return scenario;
	}

	public void saveReport() {
		extent.flush();
	}

	public void takeSS(WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String fileName = System.currentTimeMillis()+".png";
		String destinationFile = System.getProperty("user.dir") + "\\reports\\ss\\"+fileName;
		FileUtils.copyFile(source, new File(destinationFile));

		step.addScreenCaptureFromPath("ss\\"+fileName);
	}

	public void screenShot(WebDriver driver) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String fileName = System.currentTimeMillis()+".png";
		String destinationFile = System.getProperty("user.dir") + "\\reports\\ss\\"+fileName;
		try {
			FileUtils.copyFile(source, new File(destinationFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			step.addScreenCaptureFromPath("ss\\"+fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addImageFrom(String path) {
		try {
			step.addScreenCaptureFromPath(path); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addCLIfail(String log) {
		step.fail(log);
	}

	public void addCLIlog(String log) {
		step.pass(log);
	}

	public void addStepWithFile(String log, String name) {
		try {
			String fileName = System.currentTimeMillis()+name+".txt";
			String destinationFile = System.getProperty("user.dir") + "\\reports\\ss\\"+fileName;
			File myObj = new File(destinationFile);
			FileWriter myWriter = new FileWriter(destinationFile);
			myWriter.write(log);
			myWriter.close();
			step.info("<a href='ss/"+fileName+"'>click to view the TXT file</a>");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			step.fail(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
	public void addInfoLog(String infoLog) {
		step.info(infoLog);
	}


	public void addStepWithCSVFile(String log, String name) {
		try {
			String fileName = System.currentTimeMillis()+name+".csv";
			String destinationFile = System.getProperty("user.dir") + "\\reports\\ss\\"+fileName;
			File myObj = new File(destinationFile);
			FileWriter myWriter = new FileWriter(destinationFile);
			myWriter.write(log);
			myWriter.close();
			step.info("<a href='ss/"+fileName+"'>click to view the CSV logs</a>");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			step.fail(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	public void testStepHandler(WebDriver driver, String message, String status) throws IOException {

		switch (status) {
		case "FAIL":

			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String fileName = System.currentTimeMillis()+".png";
			String destinationFile = System.getProperty("user.dir") + "\\reports\\ss\\"+fileName;
			FileUtils.copyFile(source, new File(destinationFile));

			scenario.fail("Scenario Failed");
			scenario.addScreenCaptureFromPath("ss\\"+fileName);
			step.fail(message);
			step.addScreenCaptureFromPath("ss\\"+fileName);

			driver.close();
			break;
		case "PASS":
			step.pass(message);
			break;
		default:
			break;
		}
	}

	public void addLog(String log, boolean passFail) {
		if(passFail) {
			step.pass(log);
		}else {
			step.fail(log);
		}
	}
}
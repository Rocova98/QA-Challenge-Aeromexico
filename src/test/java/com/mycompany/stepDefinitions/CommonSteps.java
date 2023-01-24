package com.mycompany.stepDefinitions;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mycompany.config.Configure;
import com.mycompany.module.Listeners;
import com.mycompany.module.TestBase;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class CommonSteps extends TestBase{

	String securityCode;

	@Before
	public void reportScenario(Scenario scenario) {
		Listeners.report.addScenario(scenario.getName());
	}

	@Before(value="@UITest", order=2)
	public void beforeScenario(Scenario scenario) throws IOException{

		getWebDriver(browser);
	}
	
	@Before(order=1)
	public void getConfigurationFromServer(Scenario scenario) throws IOException{
		Configure.getConfig();
	}
	
	
	@After(value="@UITest")
	public void afterScenario() {
		driver.quit();
	}

	@BeforeStep
	public void doSomethingBeforeStep(Scenario scenario) {	
	}

	@AfterStep
	public void doSomethingAfterStep(Scenario scenario) {

	}
	
	public static WebElement waitForElementToBeVisible(WebElement webElement) {
		WebDriverWait wait = new WebDriverWait(driver, Configure.PAGE_LOAD_WAIT);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(webElement));
		return element;
	}
	
	public static WebElement waitForElementToBeClickable(WebElement webElement) {
		WebDriverWait wait = new WebDriverWait(driver, Configure.PAGE_LOAD_WAIT);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(webElement));
		return element;
	}


	


}
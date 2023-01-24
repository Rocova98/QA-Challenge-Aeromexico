package com.mycompany.module;


import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import com.mycompany.config.Configure;
import io.github.bonigarcia.wdm.WebDriverManager;


public class TestBase{
	public static WebDriver driver;
	protected String browser = Configure.BROWSER;

	private String driverPath="src/test/resources/com/mycompany/resources/drivers/";

	public String currentStep;

	public void getWebDriver(String br){
		if(br.equalsIgnoreCase("ff")){
			System.setProperty("webdriver.gecko.driver", driverPath+"geckodriver.exe");
			driver=new FirefoxDriver();
		}else if(br.equalsIgnoreCase("ie")){
			System.setProperty("webdriver.ie.driver", driverPath+"IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}else if(br.equalsIgnoreCase("chrome")){
			//System.setProperty("webdriver.chrome.driver", driverPath+"chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			//WebDriverManager.chromedriver().browserVersion("77.0.3865.40").setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized"); 
			options.addArguments("enable-automation"); 
			options.addArguments("--no-sandbox"); 
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--disable-browser-side-navigation"); 
			options.addArguments("--disable-gpu");
			options.addArguments("--disable-blink-features=AutomationControlled");
			//options.addArguments("window-size=1900,1000");
			options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
			options.addArguments("disable-infobars");

			driver = new ChromeDriver(options);
			
		}

		driver.manage().timeouts().pageLoadTimeout(Configure.PAGE_LOAD_WAIT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Configure.IMPLICIT_WAIT, TimeUnit.SECONDS);

	}

	public interface stepCode{
		void stepMethod();
	}
	


	public void reportStepParallel(stepCode step, String stepName, WebDriver driverV) throws IOException {
		Listeners.report.addStep(stepName);

		try {           
			step.stepMethod();
			//Thread.sleep(1000);

			Listeners.report.testStepHandler(driverV, Configure.PASSTEXT, "PASS");
		}catch(AssertionError | Exception e) {
			Listeners.report.testStepHandler(driverV, e.getLocalizedMessage(), "FAIL");
		}
	}

	public void reportStep(stepCode step, String stepName) throws IOException {
		Listeners.report.addStep(stepName);

		try {			
			step.stepMethod();
			//Thread.sleep(1000);

			Listeners.report.testStepHandler(driver, Configure.PASSTEXT, "PASS");
		}catch(AssertionError | Exception e) {
			Listeners.report.testStepHandler(driver, e.getLocalizedMessage(), "FAIL");
		} 
	}

	public void reportStepCLI(stepCode step, String stepName) throws IOException {
		Listeners.report.addStep(stepName);

		try {			
			step.stepMethod();
			//Thread.sleep(1000);

		}catch(AssertionError | Exception e) {

			Listeners.report.addCLIfail(e.getLocalizedMessage());
		} 
	}

	public void waitFor(int seconds) {
		try {
			for(int i = 0; i<=seconds; i++) {
				Thread.sleep(1000);
				System.out.println("Waiting... "+i+"/"+seconds);
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void navigateToPage(String url){
		driver.get(url);
		//driver.navigate().to(url);
		//driver.manage().window().maximize();
	}

	public void printAndReport(String text) {
		Listeners.report.addLog(text, true);
		System.out.println(text);
	}
	
}

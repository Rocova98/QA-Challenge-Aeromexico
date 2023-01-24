package com.mycompany.stepDefinitions;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import com.mycompany.PageObjects.AeromexicoElements;
import com.mycompany.module.Listeners;
import com.mycompany.module.TestBase;

import org.openqa.selenium.WebElement;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class AeromexicoSearch extends TestBase{

	private AeromexicoElements aeroElements;

	@Before
	public void testSetup() {
		aeroElements = new AeromexicoElements(driver);
	}

	@Given("^I load Volaris website$")
	public void i_load_volaris_website() throws Throwable {
		stepCode step = ()->{
			navigateToPage("https://aeromexico.com/");
			waitFor(5);
			Listeners.report.screenShot(driver);
		};
		reportStep(step, "I load Volaris website");
	}

	@When("^I change the language to English and the currency to USD$")
	public void i_change_the_language_to_english_and_the_currency_to_usd() throws Throwable {

		stepCode step = ()->{
			//Change language and currency
			CommonSteps.waitForElementToBeClickable(aeroElements.languageAndCurrencyMEX).click();
			waitFor(1);
			List<WebElement> EnglishUSD = driver.findElements(By.xpath("//*[@class='country']//following-sibling::span[contains(text(),'English')]/span[contains(text(),'USD')]"));
			EnglishUSD.get(0).click();
			CommonSteps.waitForElementToBeClickable(aeroElements.acceptCookies).click();
			waitFor(1);
			//Validates if the language and the currency have changed (I could use Assertions but I want to add this log to report)
			if(aeroElements.titleSearch.getText().equals("Search for a flight") &&
					aeroElements.currencyUSD.getText().contains("USD"))
			{
				Listeners.report.addCLIlog("Language and currency have been changed to English - USD");
				Listeners.report.screenShot(driver);
			}else {
				Listeners.report.addCLIfail("Language and currency have been not changed to English - USD");
				Listeners.report.screenShot(driver);
			}
		};
		reportStep(step, "I change the language to English and the currency to USD");
	}

	@When("^I look for the cheapest and more expensive flight for the next mont GDL to SEA$")
	public void i_look_for_the_cheapest_and_more_expensive_flight_for_the_next_mont_gdl_to_sea() throws Throwable {
		stepCode step = ()->{
			String origin = "Guadalajara";
			String destination = "Seattle";
			//Select origin, destination and look for flights from the first day of the next month
			aeroElements.oneWayOption.click();
			CommonSteps.waitForElementToBeClickable(aeroElements.originFlight).sendKeys(origin);
			aeroElements.originFlight.sendKeys(Keys.RETURN);
			waitFor(1);
			CommonSteps.waitForElementToBeClickable(aeroElements.destinationFlight).sendKeys(destination);
			aeroElements.destinationFlight.sendKeys(Keys.RETURN);        	
			aeroElements.calendarBooking.click();
			List<WebElement> currentMonths = driver.findElements(By.xpath("//*[@class='DatePickerCalendarMonthRefactored-month']"));
			String month = currentMonths.get(1).getText();
			int daysFutureMonth = getDayNumbersperMonth(month);
			aeroElements.firstDayMonth.get(1).click();
			aeroElements.findFlightsButton.click();
			System.out.println(daysFutureMonth);

			//Look for flights GDL - SEA
			lookForFlights(daysFutureMonth, month, origin + " to "+ destination);
			
			navigateToPage("https://aeromexico.com/en-us");
			waitFor(5);
			
			aeroElements.oneWayOption.click();
			CommonSteps.waitForElementToBeClickable(aeroElements.originFlight).sendKeys("Seattle");
			aeroElements.originFlight.sendKeys(Keys.RETURN);
			waitFor(1);
			CommonSteps.waitForElementToBeClickable(aeroElements.destinationFlight).sendKeys("Guadalajara");
			aeroElements.destinationFlight.sendKeys(Keys.RETURN);        	
			aeroElements.calendarBooking.click();
			currentMonths = driver.findElements(By.xpath("//*[@class='DatePickerCalendarMonthRefactored-month']"));
			daysFutureMonth = getDayNumbersperMonth(month);
			aeroElements.firstDayMonth.get(1).click();
			aeroElements.findFlightsButton.click();
			System.out.println(daysFutureMonth);
			
			//Look for flights SEA - GDL
			lookForFlights(daysFutureMonth, month, destination + " to "+ origin);

		};
		reportStep(step, "I look for the cheapest and more expensive flight for the next mont GDL to SEA");
	}

	public void lookForFlights(int daysFutureMonth, String month, String fromTo) {
		ArrayList<String> cheaperList = new ArrayList<String>();
		ArrayList<String> expensiveList = new ArrayList<String>();
		float cPrice = 0;
		float ePrice = 0;
		
		for(int dayFlight=1; dayFlight<=daysFutureMonth; dayFlight++) {
			try {
				CommonSteps.waitForElementToBeVisible(aeroElements.flightsPrices.get(0));
				if(dayFlight != 1) {
					driver.findElement(By.xpath("//span[contains(text(),'"+month.substring(0, 3)+" "+dayFlight+"')]")).click();
				}

				List<Float> prices = new ArrayList<Float>();
				String price="";
				int nFlights = aeroElements.flightsPrices.size();
				for(int i=0; i<nFlights; i++) {
					price = aeroElements.flightsPrices.get(i).getText().replace(",","");
					prices.add(Float.valueOf(price.substring(1,price.length())));
				}

				float cheaperPrice = Collections.min(prices);
				float expensivePrice =  Collections.max(prices);

				if(dayFlight == 1) {
					cPrice = cheaperPrice;
					ePrice = expensivePrice;
					cheaperList.add(month +" "+ dayFlight + ": $" + cPrice);
					expensiveList.add(month +" "+ dayFlight + ": $" + ePrice);
				}    		
				if (cheaperPrice < cPrice) {
					cheaperList.clear();
					cheaperList.add(month +" "+ dayFlight + ": $" + cheaperPrice);
					cPrice = cheaperPrice;
				}
				if (cheaperPrice == cPrice) {
					cheaperList.add(month +" "+ dayFlight + ": $" + cheaperPrice);
				}        		
				if(expensivePrice > ePrice) {
					expensiveList.clear();
					expensiveList.add(month +" "+ dayFlight + ": $" + expensivePrice);
					ePrice = expensivePrice;
				}
				if (expensivePrice == ePrice) {
					expensiveList.add(month +" "+ dayFlight + ": $" + expensivePrice);
				}

			}catch(Exception e) {
				driver.navigate().refresh();
				System.out.println(e);
			}
		}

		Set<String> hashSetCheap = new HashSet<String>(cheaperList);
		Set<String> hashSetExpensive = new HashSet<String>(expensiveList);
		cheaperList.clear();
		cheaperList.addAll(hashSetCheap);
		expensiveList.clear();
		expensiveList.addAll(hashSetExpensive);
		Collections.sort(cheaperList);
		Collections.sort(expensiveList);		
		System.out.println("Cheap list: " + fromTo);
		Listeners.report.addCLIlog("Cheap list: " + fromTo);
		for (String i : cheaperList) {
			System.out.println(i);
		}
		System.out.println("Expensive list: " + fromTo);
		Listeners.report.addCLIlog("Expensive list" + fromTo);
		for (String i : expensiveList) {			
			System.out.println(i);
		}
		Listeners.report.addCLIlog(cheaperList.toString());
		Listeners.report.addCLIlog(expensiveList.toString());
	}

	public int getDayNumbersperMonth(String month) {
		int days = 0;
		switch(month) {

		case "January": case "March": case "May": case "July": case "August": case "October": case "December":
			days =31;
			break;
		case "April": case "June": case "September": case "November": 
			days =30;
			break;
		case "February":
			Date actualYear=new Date();
			if(isLeapYear(1900 + actualYear.getYear())){
				days=29;
			}else{
				days=28;
			}
			break;
		}

		return days;
	}

	public static boolean isLeapYear(int year) {

		GregorianCalendar calendar = new GregorianCalendar();
		boolean isLeapYear = false;
		if (calendar.isLeapYear(year)) {
			isLeapYear = true;
		}
		return isLeapYear;     
	}
}

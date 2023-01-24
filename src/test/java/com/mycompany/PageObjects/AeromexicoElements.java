package com.mycompany.PageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AeromexicoElements {
		
	@FindBy(xpath="//button[@aria-label='Flag of Mexico']")
	public WebElement languageAndCurrencyMEX;
	
	@FindBy(xpath="//span[contains(text(),'Selecciona tu idioma y ubicación')]")
	public WebElement languageTitleSelectES;
	
	@FindBy(xpath="//*[@class='country']//following-sibling::span[contains(text(),'English')]/span[contains(text(),'USD')]")
	public WebElement englishUSDOption;
	
	@FindBy(xpath="//span[contains(text(),'Accept')]")
	public WebElement acceptCookies;
	
	@FindBy(xpath="//span[text()='One-way']")
	public WebElement oneWayOption;
	
	@FindBy(name="origin")
	public WebElement originFlight;
	
	@FindBy(name="destination")
	public WebElement destinationFlight;
	
	@FindBy(xpath="//*[contains(@class, 'calendarNewHome')]")
	public WebElement calendarBooking;
	
	@FindBy(xpath="//section[@class='DatePickerModal-headerMonth']//following-sibling::section/button/h4[text()='1']")
	public List<WebElement> firstDayMonth;
	
	@FindBy(xpath="//button[@aria-label='Find flights']")
	public WebElement findFlightsButton;
	
	@FindBy(xpath="//section[@class='containerFares-Opt']/div/div/div/p[2]")
	public List<WebElement> flightsPrices;
	
	@FindBy(xpath="//button[contains(@class, 'Icon--arrowRight')]")
	public WebElement arrowRightButton;
	
	@FindBy(xpath="//button[@aria-label='Flag of United States']")
	public WebElement languageAndCurrencyUSA;
	
	@FindBy(xpath="//h2[@class='Booker-search--title']")
	public WebElement titleSearch;
	
	@FindBy(xpath="//ul[@class='Header-profileItems']/li/button/div/span")
	public WebElement currencyUSD;	
	
	public AeromexicoElements(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

}

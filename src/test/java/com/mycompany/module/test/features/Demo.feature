@UITest
Feature: Aeromexico QA Challenge

  @RunThis
  Scenario: Look for cheapest and more expensive flights from GDL to SEA
		Given I load Volaris website
		When I change the language to English and the currency to USD
		Then I look for the cheapest and more expensive flight for the next mont GDL to SEA
		


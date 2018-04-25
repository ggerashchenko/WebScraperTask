##  Document purpose
I will try briefly to explain how i wanted to approach testing, which difficulties I faced and what I actually was able to produce.

## Initial technical task:
Imagine we have a dummy login page here: http://testing-ground.scraping.pro/login. As a test automation engineer,
you are asked to write some automated UI tests to check its login functionality.
You may use any tools/resource you prefer and take as long as you need. At the end, please submit your solution to us either via Email
 or public repository on GitHub. We will:

Compile your project
Run your solution
Evaluate the quality of your tests/solution.

## Original plan was:
1. Write tests in Java
2. Use [Selenide](http://selenide.org/) library for UI test
3. Use TestNG as test runner
4. Write next tests:
    * Success login
    * Login with wrong and empty credentials
    * Delete cookie after login to be able to get missing cookie message
    * Implement test for redirection message
    * Verify Go Back button
5. Use [Allure](http://allure.qatools.ru/) for reporting
 
## What was achieved:
1. Maven project that has a series of UI and API tests (bonus that I decided to add in order to show you how can I work with API testing)
2. UI tests include: 
    - Valid credentials scenario
    - Invalid credentials scenario
    - Missing cookie scenario
3. API tests include: 
    - Valid credentials scenario
    - Invalid credentials scenario
    - Missing cookie scenario
    - Redirect message scenario
4. After tests are finished, allure report is generated

## Difficulties i have faced during testing : 
I didn't manage to reproduce "REDIRECTING" scenario with Selenium tests, but I managed to cover it with API tests. 

In order to run test you can use IDE or you need to call `mvn cleat test` task inside the project folder.
In order to get report you need to call `allure serve allure-results/` task inside the project folder. But to be able to do so
 you need to have allure installed on the machine

## What could be improved : 
- Chromedriver should not be part of resources, but instead docker container with chrome driver should be used. There are two options available : 
    - Either to run tests inside default chrome container taken from [Doker Selenium](https://github.com/SeleniumHQ/docker-selenium)
    - In case when Selenium Grid is needed, then [Selenoid](https://github.com/aerokube/selenoid)can be used
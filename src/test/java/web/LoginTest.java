package web;

import com.codeborne.selenide.Condition;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.ErrorLoginPage;
import pages.LoginPage;
import pages.SuccessLoginPage;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginTest extends BasicTestCase {

	LoginPage loginPage = new LoginPage();

	@Test
	@DisplayName("Test success login to the system.")
	public void successLoginTest() {
		SuccessLoginPage successLoginPage = loginPage.successLogin("admin", "12345");
		verifyMessageDisplayed(successLoginPage.successLoginMessage, "WELCOME :)");
	}

	@Test
	@DisplayName("Test status after cookie been deleted.")
	public void noCookieLoginTest() {
		loginPage.successLogin("admin", "12345");
		WebDriver driver = getWebDriver();
		driver.manage().deleteCookieNamed("tdsess");
		driver.navigate().refresh();
		ErrorLoginPage errorLoginPage = new ErrorLoginPage();
		verifyMessageDisplayed(errorLoginPage.sessionMissingCookieError, "THE SESSION COOKIE IS MISSING OR HAS A WRONG VALUE!");
	}

	@Test
	@DisplayName("Test access denied status without credentials.")
	public void accessDeniedWithoutCredentialsForLoginTest() {
		ErrorLoginPage errorLoginPage = loginPage.failedLogin();
		verifyMessageDisplayed(errorLoginPage.accessDeniedError, "ACCESS DENIED!");
	}

	@Test
	@DisplayName("Test access denied status with wrong credentials.")
	public void accessDeniedWithWrongCredentialsForLoginTest() {
		ErrorLoginPage errorLoginPage = loginPage.failedLogin("incorrectUserName", "wrongPassword");
		verifyMessageDisplayed(errorLoginPage.accessDeniedError, "ACCESS DENIED!");
	}

	@Test
	@DisplayName("Test Back button navigating to login screen.")
	public void goBackAfterSuccessLoginTest() {
		SuccessLoginPage successLoginPage = loginPage.successLogin("admin", "12345");
		loginPage = successLoginPage.goBackToLoginPage();
		loginPage.pageTitle.shouldHave(Condition.exactText("LOGIN"));
	}
}

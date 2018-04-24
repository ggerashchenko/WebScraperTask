package tests;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.ErrorLoginPage;
import pages.LoginPage;
import pages.SuccessLoginPage;
import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginTest extends BasicTestCase {

	LoginPage loginPage = new LoginPage();

	@Test
	@DisplayName("Test success login to the system.")
	public void successLoginTest() {
		loginPage.successLogin("admin", "12345");
		$(By.className("success")).isDisplayed();
		$(By.className("success")).shouldHave(Condition.exactText("WELCOME :)"));
	}

	@Test
	@DisplayName("Test status after cookie been deleted.")
	public void noCookieLoginTest() {
		loginPage.successLogin("admin", "12345");
		WebDriver driver = getWebDriver();
		driver.manage().deleteCookieNamed("tdsess");
		driver.navigate().refresh();
		ErrorLoginPage errorLoginPage = new ErrorLoginPage();
		errorLoginPage.sessionMissingCookieError.isDisplayed();
		errorLoginPage.sessionMissingCookieError.shouldHave(Condition.exactText("THE SESSION COOKIE IS MISSING OR HAS A WRONG VALUE!"));
	}

	@Test
	@DisplayName("Test access denied status without credentials.")
	public void accessDeniedWithoutCredentialsForLoginTest() {
		ErrorLoginPage errorLoginPage = loginPage.failedLogin();
		errorLoginPage.accessDeniedError.isDisplayed();
		errorLoginPage.accessDeniedError.shouldHave(Condition.exactText("ACCESS DENIED!"));
	}

	@Test
	@DisplayName("Test access denied status with wrong credentials.")
	public void accessDeniedWithWrongCredentialsForLoginTest() {
		ErrorLoginPage errorLoginPage = loginPage.failedLogin("incorrectUserName", "wrongPassword");
		errorLoginPage.accessDeniedError.isDisplayed();
		errorLoginPage.accessDeniedError.shouldHave(Condition.exactText("ACCESS DENIED!"));
	}

	@Test
	@DisplayName("Test Back button navigating to login screen.")
	public void goBackAfterSuccessLoginTest() {
		SuccessLoginPage successLoginPage = loginPage.successLogin("admin", "12345");
		loginPage = successLoginPage.goBackToLoginPage();
		loginPage.pageTitle.shouldHave(Condition.exactText("LOGIN"));
	}

//	@Test
	public void redirectionWasNotProcessed() {
		// start the proxy
		BrowserMobProxy proxy = new BrowserMobProxyServer();
		proxy.start();

		// get the Selenium proxy object
		Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

		ChromeDriverService service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File("/Users/grygorii.gerashchenko/Work/wstg/src/main/resources/chromedriver"))
				.usingAnyFreePort()
				.build();
		ChromeOptions options = new ChromeOptions();
		options.merge(capabilities);
		ChromeDriver driver = new ChromeDriver(service, options);



		openPage();

		$(By.id("usr")).setValue("admin");
		$(By.id("pwd")).setValue("12345");
		$(By.cssSelector("input[value=\"Login\"]")).click();


		proxy.stop();

	}
}

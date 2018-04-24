package web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BasicTestCase {

	private static String BASE_URL = "http://testing-ground.scraping.pro/login";
	private static String CHROMEDRIVER_URL = "/Users/grygorii.gerashchenko/Work/wstg/src/main/resources/chromedriver";

	@BeforeClass
	public static void setup() {
		System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_URL);
		System.setProperty("selenide.browser", "Chrome");
	}

	@Before
	public void openPage() {
		Selenide.open(BASE_URL);
	}

	@After
	public void testTearDown() {
		getWebDriver().manage().deleteAllCookies();
		getWebDriver().quit();
	}

	@Step("Verify that message: {message} is displayed")
	void verifyMessageDisplayed(SelenideElement element, String message) {
		element.shouldBe(Condition.visible).shouldHave(Condition.exactText(message));
	}

}

package web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import data.TestPropertiesLoader;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BasicTestCase {

	private static String BASE_URL = TestPropertiesLoader.getBaseUrl();
	private static String chromeDriverPath = TestPropertiesLoader.getChromeDriverPath();
	private static String BROWSER = TestPropertiesLoader.getBrowser();

	@BeforeClass
	public static void setup() {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		System.setProperty("selenide.browser", BROWSER);
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

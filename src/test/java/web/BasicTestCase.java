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

	private static String baseUrl = TestPropertiesLoader.getBaseUrl();
	private static String chromeDriverPath = TestPropertiesLoader.getChromeDriverPath();
	private static String browser = TestPropertiesLoader.getBrowser();

	@BeforeClass
	public static void setup() {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		System.setProperty("selenide.browser", browser);
	}

	@Before
	public void openPage() {
		Selenide.open(baseUrl + "/login");
	}

	@After
	public void testTearDown() {
		getWebDriver().manage().deleteAllCookies();
		getWebDriver().quit();
	}

	@Step("Verify that message: '{message}' is visible and has text")
	void verifyMessageVisibleAndHasText(SelenideElement element, String message) {
		element.shouldBe(Condition.visible).shouldHave(Condition.exactText(message));
	}

}

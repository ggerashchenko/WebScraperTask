package web;

import com.codeborne.selenide.Selenide;
import org.junit.After;
import org.junit.AfterClass;
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

	@AfterClass
	public static void suitTearDown() {
		getWebDriver().quit();
	}

}

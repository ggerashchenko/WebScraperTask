package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ErrorLoginPage {

	public SelenideElement sessionMissingCookieError = $(By.cssSelector("h3.error"));
	public SelenideElement accessDeniedError = $(By.cssSelector(".error"));
}

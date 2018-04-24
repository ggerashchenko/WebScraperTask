package pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class LoginPage {

	private SelenideElement loginField = $(By.id("usr"));
	private SelenideElement passwordField = $(By.id("pwd"));
	private SelenideElement submitButton = $(By.cssSelector("input[value=\"Login\"]"));
	public SelenideElement pageTitle = $(By.cssSelector("#content h1"));

	private LoginPage enterCredentials(String userName, String password) {
		loginField.setValue(userName);
		passwordField.setValue(password);
		return this;
	}

	public SuccessLoginPage successLogin(String userName, String password) {
		enterCredentials(userName, password);
		submitButton.click();
		return new SuccessLoginPage();
	}

	public ErrorLoginPage failedLogin() {
		submitButton.click();
		return new ErrorLoginPage();
	}

	public ErrorLoginPage failedLogin(String userName, String password) {
		enterCredentials(userName, password);
		return failedLogin();
	}
}

package pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginPage {

	private SelenideElement loginField = $(By.id("usr"));
	private SelenideElement passwordField = $(By.id("pwd"));
	private SelenideElement submitButton = $(By.cssSelector("input[value=\"Login\"]"));
	public SelenideElement pageTitle = $(By.cssSelector("#content h1"));

	@Step("Enter username: {userName}")
	private void enterUserName(String userName) {
		loginField.setValue(userName);
	}
	@Step("Enter password: {password}")
	private void enterPassword(String password) {
		passwordField.setValue(password);
	}

	@Step("Click Submit button")
	private void clickSubmitButton() {
		submitButton.click();
	}

	public SuccessLoginPage successLogin(String userName, String password) {
		enterUserName(userName);
		enterPassword(password);
		clickSubmitButton();
		return new SuccessLoginPage();
	}

	public ErrorLoginPage failedLogin() {
		clickSubmitButton();
		return new ErrorLoginPage();
	}

	public ErrorLoginPage failedLogin(String userName, String password) {
		enterUserName(userName);
		enterPassword(password);
		return failedLogin();
	}
}

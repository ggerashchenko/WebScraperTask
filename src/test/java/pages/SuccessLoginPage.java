package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SuccessLoginPage {

	private SelenideElement backButton = $(By.cssSelector("#case_login a"));
	public SelenideElement successLoginMessage = $(By.className("success"));

	@Step("Click Back button")
	public LoginPage goBackToLoginPage() {
		backButton.click();
		return new LoginPage();
	}
}

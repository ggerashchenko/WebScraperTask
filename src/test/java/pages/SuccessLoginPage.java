package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SuccessLoginPage {

	private SelenideElement backButton = $(By.cssSelector("#case_login a"));

	public LoginPage goBackToLoginPage() {
		backButton.click();
		return new LoginPage();
	}
}

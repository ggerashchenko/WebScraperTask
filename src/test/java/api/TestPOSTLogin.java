package api;

import data.TestPropertiesLoader;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class TestPOSTLogin {

	private String baseUrl = TestPropertiesLoader.getBaseUrl();
	private String successLogin = TestPropertiesLoader.getLogin();
	private String successPassword = TestPropertiesLoader.getPassword();

	private static final String REDIRECT_MESSAGE = "REDIRECTING...</h3>";
	private static final String WELCOME_MESSAGE = "WELCOME :)</h3>";
	private static final String ACCESS_DENIED_MESSAGE = "ACCESS DENIED!</h3>";
	private static final String MISSING_COOKIE_MESSAGE = "THE SESSION COOKIE IS MISSING OR HAS A WRONG VALUE!";

	@Test
	@DisplayName("Test redirect status.")
	public void testRedirect() {

		RestAssured.baseURI = baseUrl;

		Response response =  given()
				.formParam("usr", successLogin)
				.formParam("pwd", successPassword)
				.expect()
				.statusCode(302)
				.when()
				.post("/login?mode=login");
		String responseBody = response.getBody().print();
		Assert.assertTrue(responseBody.contains(REDIRECT_MESSAGE));

		RestAssured.reset();
	}

	@Test
	@DisplayName("Test success login status.")
	public void testSuccessLogin() {

		RestAssured.baseURI = baseUrl;

		Response response =  given()
				.formParam("usr", successLogin)
				.formParam("pwd", successPassword)
				.expect()
				.statusCode(302)
				.when()
				.post("/login?mode=login");
		Response getResponse = given()
				.cookies(response.getCookies())
				.expect()
				.statusCode(200)
				.when()
				.get(response.getHeaders().get("Location").getValue());
		String responseBody = getResponse.getBody().print();
		Assert.assertTrue(responseBody.contains(WELCOME_MESSAGE));

		RestAssured.reset();
	}

	@Test
	@DisplayName("Test access denied status.")
	public void testAccessDenied() {

		RestAssured.baseURI = baseUrl;

		Response response =  given()
				.formParam("usr", "")
				.formParam("pwd", "")
				.expect()
				.statusCode(200)
				.when()
				.post("/login?mode=login");
		String responseBody = response.getBody().print();
		Assert.assertTrue(responseBody.contains(ACCESS_DENIED_MESSAGE));

		RestAssured.reset();
	}

	@Test
	@DisplayName("Test no cookies status.")
	public void testNoCookie() {

		RestAssured.baseURI = baseUrl;

		Response response =  given()
				.formParam("usr", successLogin)
				.formParam("pwd", successPassword)
				.expect()
				.statusCode(302)
				.when()
				.post("/login?mode=login");
		Response getResponse = given()
				.expect()
				.statusCode(200)
				.when()
				.get(response.getHeaders().get("Location").getValue());
		String responseBody = getResponse.getBody().print();
		Assert.assertTrue(responseBody.contains(MISSING_COOKIE_MESSAGE));

		RestAssured.reset();
	}

}

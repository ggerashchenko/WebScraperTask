package api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class TestPOST {

	@Test
	@DisplayName("Test redirect status.")
	public void testRedirect() {

		RestAssured.baseURI = "http://testing-ground.scraping.pro";

		Response response =  given()
				.formParam("usr", "admin")
				.formParam("pwd", "12345")
				.expect().log().all()
				.statusCode(302)
				.when()
				.post("/login?mode=login");
		String responseBody = response.getBody().print();
		Assert.assertTrue(responseBody.contains("REDIRECTING...</h3>"));
		RestAssured.reset();
	}

	@Test
	@DisplayName("Test success login status.")
	public void testSuccessLogin() {

		RestAssured.baseURI = "http://testing-ground.scraping.pro";

		Response response =  given()
				.formParam("usr", "admin")
				.formParam("pwd", "12345")
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
		Assert.assertTrue(responseBody.contains("WELCOME :)</h3>"));

		RestAssured.reset();
	}

	@Test
	@DisplayName("Test access denied status.")
	public void testAccessDenied() {

		RestAssured.baseURI = "http://testing-ground.scraping.pro";

		Response response =  given()
				.formParam("usr", "")
				.formParam("pwd", "")
				.expect()
				.statusCode(200)
				.when()
				.post("/login?mode=login");
		String responseBody = response.getBody().print();
		Assert.assertTrue(responseBody.contains("ACCESS DENIED!</h3>"));
		RestAssured.reset();
	}

	@Test
	@DisplayName("Test no cookies status.")
	public void testNoCookie() {

		RestAssured.baseURI = "http://testing-ground.scraping.pro";

		Response response =  given()
				.formParam("usr", "admin")
				.formParam("pwd", "12345")
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
		Assert.assertTrue(responseBody.contains("THE SESSION COOKIE IS MISSING OR HAS A WRONG VALUE!"));

		RestAssured.reset();
	}

}

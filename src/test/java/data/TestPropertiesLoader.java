package data;

import com.codeborne.selenide.webdriver.WebDriverFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestPropertiesLoader {
	private static Properties testProperties;

	public static final String TEST_PROPERTIES = "test.properties";
	public static final String KEY_LOGIN = "username";
	public static final String KEY_PASSWORD = "password";
	public static final String BASE_URL = "URL";
	public static final String KEY_CHROME_DRIVER_PATH = "chromedriver";
	public static final String BROWSER = "browser";


	private TestPropertiesLoader() {
	}

	static {
		testProperties = loadProperties();
		testProperties.putAll(System.getProperties());
		System.setProperties(testProperties);
	}

	private static Properties loadProperties() {
		Properties properties = new Properties();
		properties = loadProperties(TEST_PROPERTIES, properties);
		return properties;
	}

	private static Properties loadProperties(final String propertiesFile, final Properties properties) {
		InputStream is = WebDriverFactory.class.getClassLoader().getResourceAsStream(propertiesFile);
		if (is == null) {
			return properties;
		}
		try {
			properties.load(is);
		} catch (IOException e) {
			System.out.println("[INFO] " + propertiesFile + " wasn't not found in classpath.");
		}
		return properties;
	}

	public static String getChromeDriverPath() {
		return getPropertyAndFailIfMissing(KEY_CHROME_DRIVER_PATH);
	}
	public static String getBrowser() {
		return getPropertyAndFailIfMissing(BROWSER);
	}

	public static String getBaseUrl() {
		return getPropertyAndFailIfMissing(BASE_URL);
	}

	public static String getLogin() {
		return getPropertyAndFailIfMissing(KEY_LOGIN);
	}

	public static String getPassword() {
		return getPropertyAndFailIfMissing(KEY_PASSWORD);
	}

	private static String getPropertyAndFailIfMissing(final String key) {
		String value = testProperties.getProperty(key);
		if (value == null) {
			throw new RuntimeException(key + " system property is not defined");
		}
		return value;
	}
}

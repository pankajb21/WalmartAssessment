package com.walmart.wrapper;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;

import com.walmart.utilities.GlobalConstants;

public class GlobalLibrary {

	static WebDriver driver = null;


	/**
	 * Functionality : Launch Application Description : Takes 2 input parameters
	 * 
	 * @param URL
	 * @param Browser
	 */
	public void launchApplication(String URL, String browser) {
		startSelenium("Invoke Application", browser, URL);
	}

	/**
	 * Functionality : Starts selenium webdriver Description : Takes three
	 * parameters step description, browser name and URL name
	 * 
	 * @param description
	 *            Gather step details
	 * @param Browser
	 *            Gather browser details
	 * @param URL
	 *            Gather URL details
	 * @return Void
	 */
	public void startSelenium(String description, String browser, String URL) {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		try {
				if (browser.equalsIgnoreCase(GlobalConstants.SAFARI)) {
					desiredCapabilities.setBrowserName(DesiredCapabilities
							.safari().getBrowserName());
					desiredCapabilities.setPlatform(Platform.MAC);
					driver = new SafariDriver(desiredCapabilities);
				} else if (browser.equalsIgnoreCase(GlobalConstants.FIREFOX)) {
					desiredCapabilities.setBrowserName(DesiredCapabilities
							.firefox().getBrowserName());
					driver = new FirefoxDriver(desiredCapabilities);
				} else if (browser.equalsIgnoreCase(GlobalConstants.CHROME)) {
					String chromedriver = "lib/chromedriver";
					System.setProperty("webdriver.chrome.driver", chromedriver);
					driver = new ChromeDriver();
				} else if (browser.equalsIgnoreCase(GlobalConstants.IE)) {
					File file = new File("lib/IEDriverServer.exe");
					System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
					driver = new InternetExplorerDriver();
				} else {
					desiredCapabilities.setBrowserName(DesiredCapabilities
							.safari().getBrowserName());
					desiredCapabilities.setPlatform(Platform.MAC);
					driver = new SafariDriver(desiredCapabilities);
				}
			driver.manage().timeouts().implicitlyWait(GlobalConstants.IMPLICIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
			driver.get(URL);
			passStep("Application launched successfully", description,
					"startSelenium", browser, URL);
		} catch (Exception e) {
			if (driver != null) {
				driver.quit();

			}
			//e.printStackTrace();
			System.out.println("Application launch failed. Unable to initialize Selenium driver/ application is not responding");
			failStep("Application launch failed", description, "startSelenium",
					browser, URL);
		}
	}

	/**
	 * Functionality : Returns web element from Application Description : Takes
	 * one parameter , objectName
	 * 
	 * @param ObjectName
	 *            Object name string array
	 * @return WebElement It returns the corresponding web element
	 */
	public WebElement getLocator(String objectName) {
		//Making the implicit timeout to 1 seconds as the polling mechanism is present below.
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		WebElement element = null;
		String application = null;
		String locator = null;
		String[] objectNameContainer = {};
		try {
			locator = objectName; // working with direct locators

			int numofAttemps = 10;
			boolean isSuccess = false;
			System.out.println("Searching for Web Element. Name = " + locator);
			do {
				numofAttemps++;
				try {

					if (driver.findElement(By.xpath(locator)).isDisplayed()
							&& driver.findElement(By.xpath(locator))
							.isEnabled() == true) {
						isSuccess = true;
						System.out.println(locator
								+ " is loaded and visible in the application page.");
						element = driver.findElement(By.xpath(locator));
						break;
					}
					Thread.sleep(1000);
				} catch (Exception nse) {
					System.out.println("Checking for element " + locator);
					Thread.sleep(1000);
				}
			} while (!isSuccess
					|| (numofAttemps < GlobalConstants.TIMEOUTSECONDS));

			if (!isSuccess)
				System.out.println(locator
						+ " not loaded and visible in the application page.");
		} catch (Exception e) {
			System.out.println(objectName + "=" + objectNameContainer[0] + " : "
					+ locator + " is absent in " + "UIObjects/" + application
					+ ".properties file");

		}
		driver.manage().timeouts().implicitlyWait(GlobalConstants.IMPLICIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
		return element;
	}

	/**
	 * Functionality : Passes a testStep Description : Takes 5 parameters
	 * 
	 * @param passStatement
	 *            : Pass statement
	 * @param description
	 *            : Step description
	 * @param objectName
	 *            : Object name
	 * @param testData
	 *            : Test data
	 * @return Void
	 */
	public void passStep(String passStatement, String description,
			String methodName, String objectName, String testData) {
		System.out.println(passStatement);
		Assert.assertTrue(true);
	}

	/**
	 * Functionality : Fails a testStep Description : Takes 5 parameters
	 * 

	 */
	public void failStep(String problem, String description, String methodName,
			String objectName, String testData) {
		Assert.fail(problem);
	}

	/**
	 * Functionality : Close selenium webdriver Description : No parameter
	 * required
	 * 
	 * @return Void
	 */
	public void closeSelenium() {
		System.out.println("Close browser");
		try {
			if (driver != null) {
				driver.close();
				driver.quit();
				driver = null;
			}
		} catch (Exception e) {
			if(driver!=null)
				driver.quit();
			Assert.fail("Unable to close application");
		}
	}

	/**
	 * Functionality : Verifies if an object is present in the UI Description :
	 * Takes two input parameters
	 * 
	 * @param description
	 *            Gather step details
	 * @param objectName
	 *            Object name
	 * @return Void
	 */
	public void verifyObjectPresent(String description, String objectName) {
		WebElement element = getLocator(objectName);
		try {
			if (element != null)
				passStep(objectName + " is present in the UI", description,
						"verifyObjectPresent", objectName, "");
			else
				failStep(objectName + " is not displayed in the UI",
						description, "verifyObjectPresent", objectName, "");
		} catch (Exception e) {
			failStep(objectName + " is not displayed in the UI", description,
					"verifyObjectPresent", objectName, "");
		}
	}

	/**
	 * Functionality : Clicks if an object is present in the UI Description :
	 * Takes 3 input parameters
	 * 
	 * @param description
	 *            : Gather step details
	 * @param objectName
	 *            : Object name
	 * @param isOptional
	 *            : if this step is optional
	 * @return Void
	 */
	public void clickObject(String description, String objectName,
			boolean isOptionalStep) {
		WebElement element = null;

		//Logic to skip polling for element if its an optional element. This will increase the execution speed.
		if(!isOptionalStep)
			element = getLocator(objectName);
		else
		{
			try{
				element = driver.findElement(By.xpath(objectName));

			}catch(Exception e)
			{
				element = null;
			}
		}
		try {
			if (element != null) {
				System.out.println("Doing click event on " + objectName);
				element.click();
				passStep(objectName + " is clicked in the UI", description,
						"clickObject", objectName, "");
			} else {
				if (isOptionalStep == false)
					failStep(objectName + " is not displayed in the UI",
							description, "clickObject", objectName, "");
				else
					passStep(
							objectName
							+ " is not present in the UI. So ignoring the step as it is optional",
							description, "clickObject", objectName, "");
			}
		} catch (Exception e) {
			failStep("Unable to click on " + objectName, description,
					"clickObject", objectName, "");
		}
	}

	/**
	 * Functionality : Feeds input data in a text field Description : Takes
	 * three input parameters
	 * 
	 * @param description
	 *            : Gather step details
	 * @param objectName
	 *            : Object name
	 * @param textToFeed
	 *            : Text to feed
	 * @return Void
	 */
	public void setText(String description, String objectName, String textToFeed) {
		WebElement element = getLocator(objectName);
		try {
			if (element != null) {
				System.out.println("Setting text " + textToFeed + " on " + objectName);
				element.clear();
				element.sendKeys(textToFeed);
				passStep(objectName + " is fed with text " + textToFeed
						+ " in the UI", description, "setText", objectName,
						textToFeed);
			} else
				failStep(objectName + " is not displayed in the UI",
						description, "clickObject", objectName, textToFeed);
		} catch (Exception e) {
			failStep(objectName + " is not fed with text " + textToFeed
					+ " in the UI", description, "setText", objectName,
					textToFeed);
		}
	}

	/**
	 * Functionality : Verify if a text is present anywhere in the UI
	 * Description : Takes two input parameter
	 * 
	 * @param description
	 *            Gather step details
	 * @param testData
	 *            testData
	 * @return Void
	 */
	public void verifyTextPresent(String description, String text) {
		int numofAttemps =10;
		boolean isSuccess = false;
		System.out.println("Searching for Text = " + text);
		do {
			numofAttemps++;
			try {
				if (numofAttemps == GlobalConstants.TIMEOUTSECONDS)
					break;
				if (driver.findElement(By.tagName("body")).getText()
						.contains(text) == true) {
					passStep("Text " + text + " exists in the page...",
							description, "verifyTextPresent", "", text);
					isSuccess = true;
					break;
				} else {
					System.out.println(text + " is not yet loaded ...");
					Thread.sleep(1000);
				}
			} catch (Exception nse) {
				try {
					System.out.println(text + " is not yet loaded ...");
					Thread.sleep(1000);
				} catch (Exception se) {
					if (!isSuccess)
						failStep("Text " + text
								+ " does not exist in the page...",
								description, "searchText", "", text);
				}
			}
		} while (!isSuccess || (numofAttemps < GlobalConstants.TIMEOUTSECONDS));

		if (!isSuccess)
			failStep("Text " + text + " does not exist in the page...",
					description, "searchText", "", text);
	}

	/**
	 * Functionality : Verify if an element is absent Description : Takes Object
	 * Name as input parameter
	 * 
	 * @param description
	 *            Gather step details
	 * @param objectName
	 *            Object name
	 * @return Void
	 */
	public void verifyObjectAbsent(String description, String objectName) {
		WebElement element = getLocator(objectName);
		try {
			if (element == null)
				passStep(objectName + " is not present in the UI", description,
						"verifyObjectAbsent", objectName, "");
			else
				failStep(objectName + " is present in the UI", description,
						"verifyObjectAbsent", objectName, "");
		} catch (Exception e) {
			failStep(objectName + " is present in the UI", description,
					"verifyObjectAbsent", objectName, "");
		}
	}

	/**
	 * Functionality : Verify the displayed text for an object Description :
	 * Takes 4 input parameter VerifyFieldText" is required, when we verify the
	 * text displayed for particular Object. Like link or drop down or text box.
	 * If Object Type is dropdown, always use ID
	 * 
	 * @param description
	 *            Gather step details
	 * @param objectName
	 *            objectName
	 * @param objectType
	 *            objectType of the object
	 * @param testData
	 *            match data
	 * @return Void
	 */
	public void verifyFieldText(String description, String objectName,
			String objectType, String expectedValue) {
		WebElement element = getLocator(objectName);
		String actualValue = null;
		try {
			if (element != null) {
				if (objectType.equals("textbox"))
					actualValue = element.getAttribute("value");

				else
					actualValue = element.getText();

				if (actualValue.equals(expectedValue)
						|| actualValue.contains(expectedValue)) {
					passStep("Expected value is " + expectedValue
							+ " and actual value is " + actualValue,
							description, "VerifyFieldText", objectName,
							expectedValue);
				} else
					failStep("Expected value is " + expectedValue
							+ " and actual value is " + actualValue,
							description, "VerifyFieldText", objectName,
							expectedValue);
			} else
				failStep(objectName + " is not displayed in the UI",
						description, "VerifyFieldText", objectName, "");
		} catch (Exception e) {
			failStep("Unable to fetch field value from " + objectName,
					description, "VerifyFieldText", objectName, "");
		}
	}

	/**
	 * Functionality : Verify the displayed text for an object Description :
	 * Takes 3 input parameter. getFieldText is required, when we need the text
	 * displayed for particular Object. Like link or drop down or text box. If
	 * Object Type is dropdown, always use ID
	 * 
	 * @param description
	 *            Gather step details
	 * @param objectName
	 *            objectName
	 * @param objectType
	 *            objectType of the object
	 * @return String
	 */
	public String getFieldText(String description, String objectName,
			String objectType) {
		WebElement element = getLocator(objectName);
		String actualValue = null;
		try {
			if (element != null) {
				if (objectType.equals("textbox"))
					actualValue = element.getAttribute("value");
				else
					actualValue = element.getText();
				return actualValue;
			} else {
				failStep(objectName + " is not displayed in the UI",
						description, "VerifyFieldText", objectName, "");
				return actualValue;
			}
		} catch (Exception e) {
			failStep("Unable to fetch field value from " + objectName,
					description, "VerifyFieldText", objectName, "");
			return actualValue;
		}
	}

	/**
	 * Functionality : This will open new url
	 * 
	 * @param Description
	 *            : Takes time input parameter
	 * @param url
	 *            : url of the application
	 */
	public void openNewUrl(String description, String url) {
		try {
			System.out.println("Opening url " + url);

			driver.navigate().to(url);
			passStep("New url is launched successfully", description,
					"openNewUrl", "", "");

		} catch (Exception e) {
			//e.printStackTrace();
			failStep("New url failed to launch", description, "openNewUrl", "",
					"");

		}
	}
}

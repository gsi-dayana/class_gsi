package selenium_cucumber.selenium_cucumber.goheavy.login.page;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium_cucumber.selenium_cucumber.general.PageObject;
import selenium_cucumber.selenium_cucumber.general.Setup;
import selenium_cucumber.selenium_cucumber.general.actions;

import static java.lang.Thread.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class LoginPage extends PageObject {
	By emailLocator = By.id("email");
	By passwordLocator = By.id("password");
	String LogingxpathText = "//h1[text()='Login']";
	HashMap<String, WebElement> eles = new HashMap<>();
	By emailErrorSmsLocator = By.xpath("//input[@id='email']/"
			+ "ancestor::div[contains(@class,'ant-form-item-control')]/"
			+ "descendant::div[@role='alert']");
	By passwordErrorSmsLocator = By.xpath("//input[@id='password']/"
			+ "ancestor::div[contains(@class,'ant-form-item-control')]/"
			+ "descendant::div[@role='alert']");
	By emailOrPassIncorrectLocator = By.xpath("//div[@class='ant-notification-notice-message']");
	WebDriverWait wait = new WebDriverWait(Setup.getDriver(),10);


	public LoginPage() {
		super();
		this.urlpath = "login";
	}

	public WebElement getLogingTextElemnt() {
		return getWebElement(By.xpath(LogingxpathText));
	}

	public HashMap<String, WebElement> fillCredentials(String email, String password) {
		WebElement pass_element = getWebElement(passwordLocator);
		pass_element.sendKeys(password);
		WebElement email_element = getWebElement(emailLocator);
		email_element.sendKeys(email);

		eles.put("email", email_element);
		eles.put("password", pass_element);
		return eles;
	}

	public void clickOnButtons() {
		getWebElement(By.xpath("//*[@id=\"admin-form-session\"]/button")).click();

	}

	public void waitForElementDisappear() {
		Setup.getWait().waitUntilElementDisappear(emailLocator, 5000);
		Setup.getWait().waitUntilElementDisappear(passwordLocator, 5000);
	}

	public boolean getErrormessage(String message) {

		try {
			boolean isEmailEmpty = stringIsEmpty(eles.get("email").getAttribute("value"));
			boolean isPassEmpty = stringIsEmpty(eles.get("password").getAttribute("value"));

			if (isEmailEmpty && !isPassEmpty) {
				return checkErrorSms(message,emailErrorSmsLocator,"email");
			} else if (isPassEmpty && !isEmailEmpty) {
				return checkErrorSms(message,passwordErrorSmsLocator,"password");
			} else {
				return checkErrorSms(message, emailErrorSmsLocator, "email") &&
						checkErrorSms(message, passwordErrorSmsLocator, "password");
			}
		} catch (Exception e) {
			print(e.getMessage());
			return false;
		}
	}

	public boolean checkErrorSms(String message,By by, String field) {
		try {
			//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			String sms = getWebElement(by).getText();
			if (!sms.equals(message)) {
				print("The expected message is NOT equals to the corresponding " + field +" field");
				print("\nExpected message is " + message + "\n Actual message is: " + sms);
				return false;
			} else {
				print("The expected message is equals to the corresponding " + field +" field");
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public boolean getInvalidDataError (String message) {
		try {
			Thread.sleep(3000);
			return getWebElement(emailOrPassIncorrectLocator).getText().equals(message);
		} catch (Exception e) {
			return false;
		}
	}
}

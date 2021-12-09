package selenium_cucumber.selenium_cucumber.general;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class PageObject {
	private WebDriver driver;
	protected String urlpath = "";
	private String spinningElement = "//div[contains(@class,'ant-spin-spinning')]";

	public PageObject() {

		this.driver = Setup.getDriver();
		PageFactory.initElements(this.driver, this);
		// https://www.browserstack.com/guide/page-object-model-in-selenium
	}

	public void openURL() {
		String url = ((Properties) Setup.getValueStore("defaultProperties")).getProperty("default.URL");
		Setup.openUrl(url.concat("/").concat(urlpath));
	}

	protected WebElement getWebElement(By by) {
		return this.driver.findElement(by);
	}

	protected List<WebElement> getWebElements(By by) {
		return this.driver.findElements(by);
	}

	protected void cliksOnButton(By by) {
		getWebElement(by).click();
		Setup.getWait().waitForLoading(10000);
	}

	public String getCurrentUrl() {
		return this.driver.getCurrentUrl();
	}

	public String getPagePath() {
		return this.urlpath;
	}

	public void checkSpinningAppears() {
		Setup.getWait().waitUntilElementAppear(By.xpath(spinningElement), 10);
	}

	public void waitForSpinningElementDissapear() {
		try {
			checkSpinningAppears();
			Setup.getWait().waitUntilElementDisappear(By.xpath(spinningElement), 10);
		} catch (Exception e) {

		}

	}

	public void scroll(String scrollElementxpath, By targetElementxpath) {

		WebElement el = this.getWebElement(targetElementxpath);
		int desired_y = el.getSize().height / 2 + el.getLocation().y;

		int current_y = (Integer.parseInt(String.valueOf(Setup.executeScript("return window.innerHeight"))) / 2)
				+ Integer.parseInt(String.valueOf(Setup.executeScript("return window.pageYOffset")));
		int scroll_y_by = desired_y + current_y;

		Setup.executeScript("var el=" + "document.evaluate('" + scrollElementxpath + "',"
				+ " document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;"
				+ " el.scrollTo(0, arguments[0]);", scroll_y_by);

	}

	public HashMap<String, WebElement> getMenu(By by) {
		waitForSpinningElementDissapear();
		Setup.getWait().thread(4000);
		HashMap<String, WebElement> list = new HashMap<>();

		if (!Objects.isNull(by)) {
			WebElement e = this.getWebElement(by);

			WebElement el2 = e.findElement(By.xpath("span[2]"));
			list.put(el2.getText(), e);
		}
//		else {
//			 return an array of all Menu value
//		}
		return list;
	}

	public void sendAction(WebElement element, String value, actions action) {
		Setup.getActions().moveToElement(element).build().perform();

		switch (action) {
			case type:
				Setup.getActions().sendKeys(element, value).build().perform();
				break;
			case click:
				Setup.getActions().click(element).build().perform();
				break;
			default:
				print("There is not need to apply any action");
				break;
		}
	}

	public void print(Object message) {
		System.out.println(message);
	}
	public boolean stringIsEmpty(String value) {
		return value.trim().length() == 0;
	}
}

package com.eBay.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.appium.java_client.android.AndroidDriver;


public abstract class DriverHelper {
	
	private AndroidDriver driver;
	public ExtentReports extent;
	public ExtentTest test;

	public DriverHelper(AndroidDriver androidDriver) {
		driver = androidDriver;
		
	}
	
	public AndroidDriver getDriver(){
		return driver;
	}
	
	
	public void Log(String logMsg){
		System.out.println(logMsg);
	}

	public By ByLocator(String locator) {
		By result = null;

		if (locator.startsWith("//")) {
			result = By.xpath(locator);
		}else if (locator.startsWith("#")) {
			result = By.name(locator.replace("#", ""));
			
		} else if (locator.startsWith("link=")) {
			result = By.linkText(locator.replace("link=", ""));
		}
			
		  else if(locator.startsWith("class=")) {
				result=By.className(locator.replace("class=", ""));
			}
		  else if(locator.startsWith("name=")) {
				result=By.name(locator.replace("name=", ""));
			}
		  
		  else if(locator.startsWith("id=")) {
			  result=By.id(locator.replace("id=", ""));
		  }
		  else if(locator.startsWith("(")) {
			  result = By.xpath(locator);
		  }else {
			result = By.id(locator);
		}

		return result;
	}

	/*
	 * Selenium wrapper methods
	 * 
	*/
	public Boolean isElementPresent(String locator) {
		Boolean result = false;
		try {
			getDriver().findElement(ByLocator(locator));
			result = true;
		} catch (Exception ex) {

		}

		return result;
	}
	

	public Boolean isElementDisplayed(String locator) {

		Boolean result = false;
		try {
			getDriver().findElement(ByLocator(locator)).isDisplayed();
			result = true;
		} catch (Exception ex) {

		}
		return result;
	}
	

	public void WaitForElementPresent(String locator, int timeout) {

		for (int i = 0; i < timeout; i++) {
			if (isElementPresent(locator)) {
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void waitForWebElementPresent(String locator) {
		new WebDriverWait(getDriver(), 60).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ByLocator(locator)));
	}
	
	public void WaitForElementNotPresent(String locator, int timeout) {

		for (int i = 0; i < timeout; i++) {
			if (!isElementPresent(locator)) {
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void WaitForElementEnabled(String locator, int timeout) {

		for (int i = 0; i < timeout; i++) {
			if (isElementPresent(locator)) {
				if (getDriver().findElement(ByLocator(locator)).isEnabled()) {
					break;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void WaitForElementNotEnabled(String locator, int timeout) {

		for (int i = 0; i < timeout; i++) {
			if (isElementPresent(locator)) {
				if (!getDriver().findElement(ByLocator(locator)).isEnabled()) {
					break;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void WaitForElementVisible(String locator, int timeout) {

		for (int i = 0; i < timeout; i++) {
			if (isElementPresent(locator)) {
				if (getDriver().findElement(ByLocator(locator)).isDisplayed()) {
					break;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void WaitForElementNotVisible(String locator, int timeout) {

		for (int i = 0; i < timeout; i++) {
			if (isElementPresent(locator)) {
				if (!getDriver().findElement(ByLocator(locator)).isDisplayed()) {
					break;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void clickOn(String locator)
	{		
		this.WaitForElementPresent(locator,1);
		Assert.assertTrue(isElementPresent(locator),"Element Locator :"+locator+" Not found");
		WebElement el = getDriver().findElement(ByLocator(locator));			
		el.click();
	}
	
	public void sendKeys(String locator, String userName){		
		this.WaitForElementPresent(locator, 1);		
		Assert.assertTrue(isElementPresent(locator), "Element Locator :"+locator+" Not found");
		WebElement el = getDriver().findElement(ByLocator(locator));
		el.clear();
		el.sendKeys(userName);
	}
	
	public void captureScreenshot(String screenshotName) {

		try {
			TakesScreenshot ts = (TakesScreenshot) getDriver();

			File source = ts.getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(source, new File("./Screenshots/" + screenshotName + ".png"));

			System.out.println("Screenshot taken");
		} catch (Exception e) {

			System.out.println("Exception while taking screenshot " + e.getMessage());
		}
	}
	
	
	
	
	
}

package com.eBay.PageHelper;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.eBay.locators.LocatorReader;
import com.eBay.util.DriverHelper;
import com.eBay.util.ExecutionLog;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;



public class eBayPageHelper extends DriverHelper {
	
	private LocatorReader eBayLocators;	
	
	public eBayPageHelper(AndroidDriver driver) {
		super(driver);	
		// Load locator page.
		eBayLocators = new LocatorReader("eBayLocators.xml");
	}
	
	
	
//******************************************************* Home Page*********************************//
	
	public ScreenOrientation getScreenOrientation(){
		ScreenOrientation screenOrientation = null;
		try {
			screenOrientation = getDriver().getOrientation();
			System.out.println(screenOrientation);
			ExecutionLog.Log("Screen orientation: "+screenOrientation);
			
		} catch (Exception e) {
			captureScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName()+".jpg");
			ExecutionLog.Log(Thread.currentThread().getStackTrace()[1].getMethodName()+" method failed to execute due to error: "+e.getMessage());
		}
		return screenOrientation;
	}
	
	public void checkOrientation(){
		try {
			if(getScreenOrientation().toString().equalsIgnoreCase("LANDSCAPE")){
				getDriver().rotate(ScreenOrientation.PORTRAIT);
				ExecutionLog.Log("Change orientation to Portrait view.");
			}
		} catch (Exception e) {
			captureScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName()+".jpg");
			ExecutionLog.Log(Thread.currentThread().getStackTrace()[1].getMethodName()+" method failed to execute due to error: "+e.getMessage());
		}
	}
	
	public void verifyHomePage(){
		
		try {
			WaitForElementNotPresent(eBayLocators.getLocator("homePage.progressIcon"),30);
			String locator= eBayLocators.getLocator("homePage.companyLogo");
			WaitForElementPresent(locator,30);
			if (isElementDisplayed(locator)) {
				ExecutionLog.Log("Successfully land on application home page..");
			} else {
				ExecutionLog.Log("Something went wrong!!! Unable to land on application home page...");
				Assert.assertFalse(true, "Something went wrong!!! Unable to land on application home page...");
			}
		} catch (Exception e) {
			captureScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName()+".jpg");
			ExecutionLog.Log(Thread.currentThread().getStackTrace()[1].getMethodName()+" method failed to execute due to error: "+e.getMessage());
		}
	}
	
	public void searchForProduct_byKeyword(String searchKeyword){
		try {
			String searchBox_beforeClicking_locator = eBayLocators.getLocator("homePage.searchBoxBeforeClicked");
			String searchBox_afterClicking_locator = eBayLocators.getLocator("homePage.searchBoxAfterClicked");
			
			if (isElementPresent(searchBox_beforeClicking_locator)) {
				clickOn(searchBox_beforeClicking_locator);
				ExecutionLog.Log("Clicked on search field.");
			} else {
				ExecutionLog.Log("Search field is not displayed");
				Assert.assertFalse(true, "Search field is not displayed");
			}
			
			if (isElementPresent(searchBox_afterClicking_locator)) {
				sendKeys(searchBox_afterClicking_locator,searchKeyword);
				ExecutionLog.Log(searchKeyword+" search keyword entered");
			} else {
				ExecutionLog.Log("Unable to find search field after cklicking.");
				Assert.assertFalse(true, "Unable to find search field after cklicking.");
			}
		} catch (Exception e) {
			captureScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName()+".jpg");
			ExecutionLog.Log(Thread.currentThread().getStackTrace()[1].getMethodName()+" method failed to execute due to error: "+e.getMessage());		}
	}
	
		public void clickedOnSearchItem_byText(String text){
			try {
				waitForWebElementPresent(eBayLocators.getLocator("homePage.autoSuggestion"));
				List<WebElement> lst_searchedItems = getDriver().findElements(ByLocator(eBayLocators.getLocator("homePage.autoSuggestion")));
				for (WebElement ele_item : lst_searchedItems) {
					//System.err.println(ele_item.getText());
					if (ele_item.getText().trim().contains(text)) {
						ele_item.click();
						ExecutionLog.Log("Clicked on search item from auto-suggestion drop list.");
						break;
					}
				}
			} catch (Exception e) {
				captureScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName()+".jpg");
				ExecutionLog.Log(Thread.currentThread().getStackTrace()[1].getMethodName()+" method failed to execute due to error: "+e.getMessage());
			}
		}
		
	
//*************************************** Search Result Page ************************************************//	
		
		public void verifySearchResultPage(){
			try {
				if(isElementPresent(eBayLocators.getLocator("searchResultPage.popup"))){
					ExecutionLog.Log("Pop-up is appeared.");
					clickOn(eBayLocators.getLocator("searchResultPage.popup"));
					ExecutionLog.Log("Pop-up got handled.");
					}else
					{
						ExecutionLog.Log("Pop-up not appeared.");
					}
				waitForWebElementPresent(eBayLocators.getLocator("searchResultPage.searchedItemImages"));
				if (isElementDisplayed(eBayLocators.getLocator("searchResultPage.sortByButton"))) {
					ExecutionLog.Log("Successfully landed on search result page.");
				} else {
					ExecutionLog.Log("Something went wrong!!! unable to reach search result page..");
				}
			} catch (Exception e) {
				captureScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName()+".jpg");
				ExecutionLog.Log(Thread.currentThread().getStackTrace()[1].getMethodName()+" method failed to execute due to error: "+e.getMessage());
			}
		}
		
		public void sortSearchItem(String str_subFilterText){
			try {
				if (isElementPresent(eBayLocators.getLocator("searchResultPage.sortByButton"))) {
					clickOn(eBayLocators.getLocator("searchResultPage.sortByButton"));
					waitForWebElementPresent(eBayLocators.getLocator("searchResultPage.subFilterButtons"));
					List<WebElement> list_subFilterButtons = getDriver().findElements(ByLocator(eBayLocators.getLocator("searchResultPage.subFilterButtons")));
					for (WebElement ele_subFilter : list_subFilterButtons) {
						if (ele_subFilter.getText().trim().contains(str_subFilterText)) {
							ele_subFilter.click();
							ExecutionLog.Log(str_subFilterText+" subfilter option.");
							break;
						}
					}
				}else{
					ExecutionLog.Log("Element not found");
					
				}
			} catch (Exception e) {
				captureScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName()+".jpg");
				ExecutionLog.Log(Thread.currentThread().getStackTrace()[1].getMethodName()+" method failed to execute due to error: "+e.getMessage());
			}
		}
		
		public void selectItemFromSortedList(String str_itemText){
			try {
				waitForWebElementPresent(eBayLocators.getLocator("searchResultPage.searchedItemImages"));
				List<WebElement> list_sortedItems = getDriver().findElements(ByLocator(eBayLocators.getLocator("searchResultPage.sortedItems")));
				for (WebElement ele_sortedItem : list_sortedItems) {
					if (ele_sortedItem.getText().trim().contains(str_itemText)) {
						String itemName = ele_sortedItem.getText().trim();
						ele_sortedItem.click();
						ExecutionLog.Log(itemName+" item got clicked");
						break;
					}
				}
			} catch (Exception e) {
				captureScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName()+".jpg");
				ExecutionLog.Log(Thread.currentThread().getStackTrace()[1].getMethodName()+" method failed to execute due to error: "+e.getMessage());
			}
		}
		

//********************************* Product Details Page ****************************************************//		
		
		public void verifyPDP(){
			try {
				WaitForElementPresent(eBayLocators.getLocator("ProductDetailPage.variationLayout"),30);
				if (isElementDisplayed(eBayLocators.getLocator("ProductDetailPage.variationLayout"))) {
					ExecutionLog.Log("Successfully landed on PDP");
				} else {
					ExecutionLog.Log("Something went wrong!!! failed to reach PDP");
					Assert.assertFalse(true, "Something went wrong!!! failed to reach PDP");
				}
			} catch (Exception e) {
				captureScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName()+".jpg");
				ExecutionLog.Log(Thread.currentThread().getStackTrace()[1].getMethodName()+" method failed to execute due to error: "+e.getMessage());
			}
		}
		
		public void swipeImages(){
			try {
				WebElement productImage = getDriver().findElement(ByLocator(eBayLocators.getLocator("ProductDetailPage.productImage")));
				int wide  = productImage.getSize().width;
				int hgt = productImage.getSize().height;
				int startx = (int) (wide * (0.9  ));
				int endx = (int)(wide *(0.1));
				int starty =  hgt /2 ;
				int endy = hgt/2;
				TouchAction touchAction = new TouchAction(getDriver());
				touchAction.press(startx, starty).waitAction(Duration.ofSeconds(2)).moveTo(endx, endy).release().perform();
			} catch (Exception e) {
				captureScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName()+".jpg");
				ExecutionLog.Log(Thread.currentThread().getStackTrace()[1].getMethodName()+" method failed to execute due to error: "+e.getMessage());
			}
		}
		
		public void selectColor(String color){
			try {
				if (isElementPresent(eBayLocators.getLocator("ProductDetailPage.itemColorField"))) {
					clickOn(eBayLocators.getLocator("ProductDetailPage.itemColorField"));
					WaitForElementVisible(eBayLocators.getLocator("ProductDetailPage.selectionDropDown"),20);
					List<WebElement> list_colors = getDriver().findElements(ByLocator(eBayLocators.getLocator("ProductDetailPage.itemColorField")));
					for (WebElement ele_color : list_colors) {
						if (ele_color.getText().trim().equalsIgnoreCase(color)) {
							ele_color.click();
							break;
						}
					}
				} else {

				}
			} catch (Exception e) {
				captureScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName()+".jpg");
				ExecutionLog.Log(Thread.currentThread().getStackTrace()[1].getMethodName()+" method failed to execute due to error: "+e.getMessage());
			}
		}
		
		public void addToCart(){
			try {
				if (isElementPresent(eBayLocators.getLocator("ProductDetailPage.ButItNowButton"))) {
					clickOn(eBayLocators.getLocator("ProductDetailPage.ButItNowButton"));
					ExecutionLog.Log("Clicked on BuyIt Now button");
				} else {
					ExecutionLog.Log("Button is not displayed");
					Assert.assertFalse(true, "Element not found.");
				}
			} catch (Exception e) {
				captureScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName()+".jpg");
				ExecutionLog.Log(Thread.currentThread().getStackTrace()[1].getMethodName()+" method failed to execute due to error: "+e.getMessage());
			}
		}

//**************************** Sign in Page ***********************************************//
		
		public void orientationTest(String orientationName){
			try {
				switch (orientationName.toUpperCase()) {
				case "LANDSCAPE":
					getDriver().rotate(ScreenOrientation.LANDSCAPE);
					ExecutionLog.Log("Switch to Landscape view");
					break;
				
				case "PORTRAIT":
					getDriver().rotate(ScreenOrientation.PORTRAIT);
					ExecutionLog.Log("Switch to Portrait view");
					break;	

				default:
					break;
				}
				Thread.sleep(500);
			} catch (InterruptedException e) {
				captureScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName()+".jpg");
				ExecutionLog.Log(Thread.currentThread().getStackTrace()[1].getMethodName()+" method failed to execute due to error: "+e.getMessage());
			}
		}
		
		public void backToPreviousPage(){
			try {
				getDriver().pressKeyCode(AndroidKeyCode.BACK);
				WaitForElementPresent(eBayLocators.getLocator("ProductDetailPage.variationLayout"),30);
			} catch (Exception e) {
				captureScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName()+".jpg");
				ExecutionLog.Log(Thread.currentThread().getStackTrace()[1].getMethodName()+" method failed to execute due to error: "+e.getMessage());
		
			}
		}
		
		
	}
	
	
	
	

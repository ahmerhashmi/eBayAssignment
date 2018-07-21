package com.eBay.util;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.eBay.PageHelper.eBayPageHelper;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public abstract class DriverTestCase {

	//final String URL = "http://google.com";
	public PropertyReader propertyReader;
	public eBayPageHelper ebayPagehelper;
	public AndroidDriver driver;
	public ExtentReports extent;
	public ExtentTest test;
	public static AppiumDriverLocalService appiumService;
	public static String appiumServiceUrl;
	public static AppiumServiceBuilder builder;
	public static DesiredCapabilities capabilities;

	enum DriverType {
		Firefox, IE, Chrome
	}
	
	@Parameters({"deviceUDID","platformVersion","appName_with_apk_extension"})
	@BeforeClass
	public void setUp(String deviceUDID,String platformVersion,String appName_with_apk_extension) {
		
		//Start appium server with set of capabilities
		startServer(deviceUDID, platformVersion, appName_with_apk_extension);
		
		// initialise objects for different classes
		
		ebayPagehelper = new eBayPageHelper(driver);
		propertyReader = new PropertyReader();
		//extent = ExtentManager.Instance();
		
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) {

		if (ITestResult.FAILURE == result.getStatus()) {
			try {
				TakesScreenshot ts = (TakesScreenshot) driver;
				File source = ts.getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(source, new File(getPath()
						+ "//Screenshots//" + result.getName() + ".png"));
				System.out.println("Screenshot taken");
			} catch (Exception e) {

				System.out.println("Exception while taking screenshot "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	
	@AfterClass
	public void aftertest(){
		//extent.endTest(test);
		 // extent.flush();
		 // extent.close();
		  stopServer();
	}
	@Test
	public void test(){}
	
	//@AfterTest
	public void afterClassOperation(){
		//advancedHelper = null;
		//exportHelper = null;
		extent.flush();
		//extent.close();
		driver.quit();
		// close application
		 //driver.quit();
		System.out.println("quite Browser");
		
	}

	public AndroidDriver getDriver() {
		return driver;
	}
	
	public void startServer(String deviceUDID,String platformVersion, String appName_with_apk_extension) {
		//Set Capabilities
		
		appiumService = AppiumDriverLocalService.buildDefaultService();
		appiumService.start();
		appiumServiceUrl = appiumService.getUrl().toString();
        System.out.println("Appium Service Address : - "+ appiumServiceUrl);
		
    	File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "/app/Android");
        File app = new File (appDir, appName_with_apk_extension);
        
        capabilities = new DesiredCapabilities();
        capabilities.setCapability("noReset", "false");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceUDID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        capabilities.setCapability("appWaitActivity", "SplashActivity, SplashActivity,OtherActivity, *, *.SplashActivity");
		try {
			capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
			capabilities.setCapability("adbPort", "5038");
			driver=new AndroidDriver<WebElement>(new URL(appiumServiceUrl),capabilities);
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Start the server with the builder
		/*appiumService = AppiumDriverLocalService.buildDefaultService();
		appiumService.start();
		appiumServiceUrl = appiumService.getUrl().toString();
        System.out.println("Appium Service Address : - "+ appiumServiceUrl);*/
	}
	
	public void stopServer() {
    	appiumService.stop();
	}
	
	public String getPath() {
		String path = "";
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("\\\\+", "/");
		return path;
	}

	// delete all the file under Execution Log
	public void clearAllLogsAtExecutionLogfolder() {
		String path = getPath();
		File directory = new File(path + "//ExecutionLog");
		for (File f : directory.listFiles())
			f.delete();
	}

	// delete all the file under Execution Log
	public void clearAllScreenShots() {
		String path = getPath();
		File directory = new File(path + "//Screenshots");
		for (File f : directory.listFiles())
			f.delete();
	}

}

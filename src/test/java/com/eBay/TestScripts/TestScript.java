package com.eBay.TestScripts;

import org.testng.annotations.Test;
import com.eBay.util.DriverTestCase;
import com.eBay.util.ExecutionLog;

public class TestScript extends DriverTestCase{
	
	@Test
	public void testCaseId_001(){
		
		clearAllLogsAtExecutionLogfolder();
		
		ExecutionLog.LogAddClass(this.getClass().getName()
				+ " and Test method "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());		
		//test = extent.startTest("OpenHA", "Verify testcaseOne");
		
		ebayPagehelper.checkOrientation();
		
		ebayPagehelper.verifyHomePage();
		
		System.err.println(propertyReader.readApplicationFile("searchKeyword"));
		ebayPagehelper.searchForProduct_byKeyword(propertyReader.readApplicationFile("searchKeyword"));
		
		ebayPagehelper.clickedOnSearchItem_byText(propertyReader.readApplicationFile("text"));
		
		ebayPagehelper.verifySearchResultPage();
		
		ebayPagehelper.sortSearchItem(propertyReader.readApplicationFile("subFilterText"));
		
		ebayPagehelper.selectItemFromSortedList(propertyReader.readApplicationFile("price"));
		
		ebayPagehelper.verifyPDP();
		
		ebayPagehelper.swipeImages();
		
		ebayPagehelper.selectColor(propertyReader.readApplicationFile("color"));
		
		ebayPagehelper.addToCart();
		
		ebayPagehelper.orientationTest("Landscape");
		
		ebayPagehelper.orientationTest("PORTRAIT");
		
		ebayPagehelper.backToPreviousPage();
		
		//ebayPagehelper.switchToActivity();
	}
	
}

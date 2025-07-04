package TripPackageAutomation;

import TripPackageAutomation.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class TripPackageTestNG extends Base {
	
	// Declare the WebDriverWait as global
    WebDriverWait wait;
    
    // Declaring Offer Page Title to variable
    String expectedTitle = "Domestic Flights Offers | Deals on Domestic Flight Booking | Yatra.com";
    
    // Declare the bannerTest variable as global
    String bannerText;
    
    // Declare the List of window handles that we will capture while switching windows as global
    ArrayList<String> winHandles;
    
    // We are setting path locations of excel files for testcases validation
    String file_path1 = System.getProperty("user.dir") + "\\testdata\\Testcases1.xlsx";
    String file_path2 = System.getProperty("user.dir") + "\\testdata\\Testcases2.xlsx";
    
    // We are creating object of the Page class
    HomePage homePage;
    OfferPage offersPage;
    HolidayPage holidaysPage;

    @Test(priority = 1)
    public void navigationAndValidation() throws InterruptedException {
    	
    	// Open "www.yatra.com" 
        driver.get("https://www.yatra.com/");
        
        // Initializing the 'homePage','offerPage' & 'holidaysPage' objects with the WebDriver
        homePage = new HomePage(driver);
        offersPage = new OfferPage(driver);
        holidaysPage = new HolidayPage(driver);
        
        // Calling the 'closePopup' method on the 'homePage' object to close any popup displayed on the HomePage
        homePage.closePopup();
        
        // Take a pause for 2 second
        Thread.sleep(2000);
        
        // Creating a WebDriverWait object to wait for a maximum of 15 seconds for given condition
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // It will capture and store window id of Home page of "yatra.com"
        String homeHandle = driver.getWindowHandle();
        
        // Below List will capture unique window id's of Home page and Offer page of "yatra.com"
        ArrayList<String> handles = new ArrayList<>(driver.getWindowHandles());
        
        // Switch the driver's context from Home page to the Offer page window
        driver.switchTo().window(handles.get(1));
        
        // Calling the 'validateTitle' method on the 'offersPage' object, passing 'expectedTitle' to validate the Title
        String actualTitle = offersPage.validateTitle(expectedTitle);
        
        // Now, we will use assertEquals method from Assert Class to compare the current title (currTitle) with the expected title (expectedTitle)
        Assert.assertEquals(actualTitle, expectedTitle);

        bannerText = offersPage.getBannerText();
        
        // We will use assertTrue method from the Assert class to check if the 'banner' element contains 'Great Offers & Amazing Deals'.
        Assert.assertTrue(bannerText.contains("Great Offers & Amazing Deals"));
        
        // Take the Screenshot of browser window
        Screenshot.takeScreenshot(driver, "screenshot.png");
        System.out.println("Screenshot of browser window is captured!");
        System.out.print("\n");
        
        // Take a pause for 2 second 
        Thread.sleep(2000);
    }

    @Test(priority = 2, dependsOnMethods = {"navigationAndValidation"})
    public void listOfHolidayPackageAndPrices() throws InterruptedException {
    	
    	// Calling 'navigateToHolidayTab' method on the 'holidaysPage' object to click on 'HolidayTab'
        holidaysPage.navigateToHolidayTab();
        
        // Take a pause for 2 second
        Thread.sleep(2000);
        
        // Calling 'getHolidayPackageName' method on the 'holidaysPage' object to locate "Holiday Packages" which are available in Offer page of "yatra.com" and store it in List using unique and dynamic Xpath
        List<String> names = holidaysPage.getHolidayPackageNames();
        System.out.println("List of the Holiday Package available on Yatra.com:");
        //names.forEach(System.out::println);
        
        // Iterate all names of "Holiday Package" using 'for each loop' available on Offer page of "yatra.com" and printing it.
     	for(String hpn: names) {
     		System.out.println(hpn);
     	}
     	
     	// Take a pause for 2 second 
        Thread.sleep(2000);
        System.out.print("\n");
        
        // Below we will initialize array having string as datatype assigned with all unique Xpaths of "Holiday Package" available in Offer Page of "Yatra.com"
        String[] holidayPackageXpaths = {
            "//li[@data-trackvalue='View Offer Details - Dubai Summer Special package (Land Only)']",
            "//li[@title='Mauritius Holiday Packages']",
            "//li[@title='Singapore Holiday Packages']",
            "//li[@title='Europe Holiday Packages']",
            "//li[@title='Visit Korea']//img[@class='respnsiv-img']"
        };
        
        // Similarly,we also initialize array having string as datatype assigned with all Xpaths of "Price" of each and every "Holiday Package" available in Offer Page of "Yatra.com" respectively
        String[] priceXpaths = {
            "//span[@class='price ng-binding']",
            "(//div[@class='price'])[1]",
            "(//div[@class='price'])[1]",
            "(//div[@class='price'])[1]",
            "//span[@class='price ng-binding']"
        };
        
        // It will capture and store window id of OfferPage page of "yatra.com"
        String offerPageHandle = driver.getWindowHandle();
        
        // Calling 'getPackagePrice' method on the 'holidaysPage' object to locate "Holiday Price" which are available in 'Holiday Package Page' of "yatra.com" and store it in List using dynamic Xpath
        List<String> packagesPrices = holidaysPage.getPackagePrices(holidayPackageXpaths, priceXpaths, offerPageHandle);
        
        // Printing the prices of all "Holiday Packages" available in Offer page of "yatra.com"
        System.out.println("Holidays Package Prices");
        //prices.forEach(System.out::println);
        
		// Iterate all prices of "Holiday Package" using 'for each loop' available on Offer page of "yatra.com" and printing it.
		for(String price:packagesPrices) {
			System.out.println(price);
		}
    }

    @Test(priority = 3, dependsOnMethods = {"listOfHolidayPackageAndPrices"})
    public void validateWithExcel() throws IOException {
    	
    	// We are getting row count using excelUtils class for excel file1 which is stored in file_path1
        int rows1 = excelUtils.getRowCount(file_path1, "Sheet1");
        
        // Now we are iterating in excel file 1 for Sheet1
        for (int i = 1; i <= rows1; i++) {
        	
        	// We are getting cell data of Title of Offer page from excel file1 in Sheet1
            String title = excelUtils.getCellData(file_path1, "Sheet1", i, 0);
            
            // Now, we are comparing Offer page Title as a data from excel file1 in Sheet1 with the title we retrieved through automation-driver.getTitle()
            if (title.equals(expectedTitle)) {
            	
            	// We are setting the cell data as "Passed" in excel file1 in Sheet1 
                excelUtils.setCellData(file_path1, "Sheet1", i, 2, "Passed");
                
                // We are setting the cell color as "Green" in excel file1 in Sheet1
                excelUtils.fillGreenColor(file_path1, "Sheet1", i, 2);
            } else {
            	// We are setting the cell data as "Failed" in excel file1 in Sheet1
                excelUtils.setCellData(file_path1, "Sheet1", i, 2, "Failed");
                // We are setting the cell color as "Green" in excel file1 in Sheet1
                excelUtils.fillRedColor(file_path1, "Sheet1", i, 2);
            }
        }
        
        // We are getting row count using excelUtils class for excel file2 which is stored in file_path2
        int rows2 = excelUtils.getRowCount(file_path2, "Sheet1");
        
        // Now we are iterating in excel file 2 for Sheet1
        for (int j = 1; j <= rows2; j++) {
        	// We are getting cell data of Banner of Offer page from excel file2 in Sheet1
            String Givenbanner = excelUtils.getCellData(file_path2, "Sheet1", j, 0);
            
            // Now, we are comparing Banner text of Offer page as a data from excel file2 in Sheet1 with the banner text we retrieved through automation
            if (Givenbanner.equals(bannerText)) {
            	// We are setting the cell data as "Passed" in excel file2 in Sheet1 
                excelUtils.setCellData(file_path2, "Sheet1", j, 2, "Passed");
                // We are setting the cell color as "Green" in excel file2 in Sheet1
                excelUtils.fillGreenColor(file_path2, "Sheet1", j, 2);
            } else {
            	// We are setting the cell data as "Failed" in excel file2 in Sheet1
                excelUtils.setCellData(file_path2, "Sheet1", j, 2, "Failed");
                // We are setting the cell color as "Red" in excel file2 in Sheet1
                excelUtils.fillRedColor(file_path2, "Sheet1", j, 2);
            }
        }
    }
}

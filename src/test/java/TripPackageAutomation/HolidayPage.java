package TripPackageAutomation;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;

public class HolidayPage {
	
	// It will declare the WebDriver
    WebDriver driver;
    // It will declare the WebDriverWait
    WebDriverWait wait;
    
    // Now we are declaring By variable 'holidayTab' to locate the tab using an XPath
    By holidayTab = By.xpath("//a[@href='https://www.yatra.com/offer/dom/listing/holiday-deals']");
    // Now we are declaring By variable 'holidayPackages' to locate the holiday packages using an XPath
    By holidayPackages = By.xpath("//span[@class='offerMainTitle']");
    
    // Creating the constructor for the HolidayPage class
    public HolidayPage(WebDriver driver) {
    	// Initializing the WebDriver instance for the HolidayPage 
        this.driver = driver;
        // Initializing the WebDriverWait instance with a 15-second 
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    
    // This method will navigate to Holiday Tab 
    public void navigateToHolidayTab() {
        wait.until(ExpectedConditions.elementToBeClickable(holidayTab)).click();
    }
    
    // This method will retrieves the all names of Holiday Packages names
    public List<String> getHolidayPackageNames() {
        List<WebElement> packages = driver.findElements(holidayPackages);
        List<String> names = new ArrayList<>();
        for (WebElement p : packages) names.add(p.getText());
        return names;
    }
    
    // This method will retrieves the all price of Holiday Packages
    public List<String> getPackagePrices(String[] packageXpaths, String[] priceXpaths, String offerPageHandle) throws InterruptedException {
        List<String> prices = new ArrayList<>();
        for (int i = 0; i < packageXpaths.length; i++) {
        	// Locate the "Holiday Package" link using the XPath stored in the 'holidayPackageXpaths' array at index 'i'.
            driver.findElement(By.xpath(packageXpaths[i])).click();
            // Now we will store all unique id's of all tabs which include Home page, Offer page and all Holiday Package page
            ArrayList<String> handles = new ArrayList<>(driver.getWindowHandles());
            // Here driver's context gets switched from Offer page to Holiday Package page respectively
            driver.switchTo().window(handles.get(2));
            // Locate the price of each "Holiday Package" using the XPath stored in the 'priceXpaths' array at index 'i'.
            WebElement priceElement = driver.findElement(By.xpath(priceXpaths[i]));
            // Stored the all "Holiday Package" price in 'priceElement' List 
            prices.add(priceElement.getText());
            // Take a pause for 2 second
            Thread.sleep(1000);
            // After locating and storing price of "Holiday Package" we will close the current Tab - Holiday Package tab
            driver.close();
            // Now, driver's context gets switched back to Offer page from each and every Holiday Package page
            driver.switchTo().window(offerPageHandle);
            // Take a pause for 2 second
            Thread.sleep(1000);
        }
        return prices;
    }
}

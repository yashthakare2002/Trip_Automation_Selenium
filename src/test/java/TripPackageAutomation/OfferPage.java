package TripPackageAutomation;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class OfferPage {
	
	// It will declare the WebDriver
    WebDriver driver;
    // It will declare the WebDriverWait
    WebDriverWait wait;
    
    // Now we are declaring By variable 'popupClose' to locate the popup close button using an XPath
    By banner = By.xpath("//h2[normalize-space()='Great Offers & Amazing Deals']");
    
    // Creating the constructor for the OfferPage class
    public OfferPage(WebDriver driver) {
    	// Initializes the WebDriver instance for the OfferPage 
        this.driver = driver;
        // Initializing the WebDriverWait instance with a 15-second 
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    
    // We will switch the driver for various offer window
    public void switchToOfferWindow(String mainHandle) {
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }
    // Now we are validating the Title of the Offer page
    public String validateTitle(String expectedTitle) {
        wait.until(ExpectedConditions.titleContains("Domestic Flights Offers"));
        return driver.getTitle();
    }
    // Here, we are retrieving text from banner of the Offer Pages
    public String getBannerText() {
        WebElement bannerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(banner));
        return bannerElement.getText();
    }
}
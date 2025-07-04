package TripPackageAutomation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	
	// It will declare the WebDriver
    WebDriver driver;
    
    // Now we are declaring By variable 'popupClose' to locate the popup close button using an XPath
    By popupClose = By.xpath("//div[@class='MuiBox-root css-19n8dai']/child::div");
    
    // Creating the constructor for the HomePage class
    public HomePage(WebDriver driver) {
    	// Initializes the WebDriver instance for the HomePage 
        this.driver = driver;
    }

    public void closePopup() {
        WebElement ele = driver.findElement(popupClose);
        
        // We are casting the WebDriver instance to JavascriptExecutor to execute JavaScript commands
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        // Executes a JavaScript command to click on the popup close button element
        js.executeScript("arguments[0].click()", ele);
    }
}
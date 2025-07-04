package TripPackageAutomation;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.time.Duration;

public class Base {
	
	public WebDriver driver;
	
	// We will specify the 'browser' parameter that will be passed from the TestNG XML file
	@Parameters("browser")
	
	// Here,'setup' method will be executed before any test methods in the class
	@BeforeClass
	public void setup(@Optional("edge") String browser) {     // Declaring the 'setup' method with an optional 'browser' parameter, defaulting to "edge"
		
		// Step1:Launch Browser
		driver=BrowserFactory.launchBrowser(browser);
		
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@AfterClass
	public void tearDown() {
		if(driver!=null) {
			driver.quit();
		}
	}
}

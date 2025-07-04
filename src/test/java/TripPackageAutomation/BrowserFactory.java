package TripPackageAutomation;
import java.util.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import io.github.bonigarcia.wdm.WebDriverManager; //Imports the WebDriverManager class for managing browser drivers

public class BrowserFactory {
	public static WebDriver launchBrowser(String browserName) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the Browser(edge/chrome): ");
		browserName=sc.nextLine();
		
		// Initializes the WebDriver to null
		WebDriver driver=null;
		switch(browserName.toLowerCase().trim()) {
		
			case "chrome":	
				// Sets up the ChromeDriver using WebDriverManager
				WebDriverManager.chromedriver().setup();
				driver= new ChromeDriver();
				break;
			
				
			case "edge":
				// Sets up the EdgeDriver using WebDriverManager
				WebDriverManager.edgedriver().setup();
				driver=new EdgeDriver();
				break;
				
			default:
				System.out.println("Invalid browser name. Launching Edge Browser by default");
				
				// By default we will setting up the EdgeDriver using WebDriverManager
				WebDriverManager.edgedriver().setup();
				driver=new EdgeDriver();
				break;
		}
		
		//It will maximizes the browser window
		driver.manage().window().maximize();
		return driver;
		
	}

}

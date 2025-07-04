package TripPackageAutomation;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Screenshot {
	public static void  takeScreenshot(WebDriver driver,String filePath) {
		try {
			
			//We will capture the screenshot and stores it as a File object.
			File source=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			
			//Now,We will copy the screenshot file to specific path of file.
			Files.copy(source.toPath(), Paths.get(filePath));
			System.out.println("Screenshot saved at: "+filePath);
		}
		catch(Exception e) {
			System.out.println("Screenshot capture falied: "+e.getMessage());
			
		}
	}
	
}

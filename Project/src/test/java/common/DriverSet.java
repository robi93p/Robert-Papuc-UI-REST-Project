package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverSet {

    private static WebDriver driver;

    public static WebDriver openBrowser(){
        System.setProperty("webdriver.chrome.driver", " set the PATH here ! "); // set the PATH here !
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

}

package com.saucedemo.driverfactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import com.saucedemo.utils.ConfigReader;

public class DriverFactory {
    
    private static WebDriver driver;
    
    public static WebDriver initDriver() {
        String browser = ConfigReader.getProperty("browser").toLowerCase();
        
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
                
            default:
                throw new RuntimeException("Browser not supported: " + browser);
        }
        
        driver.manage().window().maximize();
        return driver;
    }
    
    public static WebDriver getDriver() {
        return driver;
    }
    
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
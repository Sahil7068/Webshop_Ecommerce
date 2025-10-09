package com.webshop.demo.base;

import com.webshop.demo.factory.DriverFactory;
import com.webshop.demo.managers.PageObjectManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected PageObjectManager pages;

    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(30);
    private static final ThreadLocal<WebDriver> THREAD_DRIVER = new ThreadLocal<>();

    private Properties loadProperties() throws IOException {
        Properties prop = new Properties();
        String configPath = System.getProperty("user.dir") + "\\src\\main\\resources\\globaldata.properties";
        try (FileInputStream fis = new FileInputStream(configPath)) {
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + configPath, e);
        }
        return prop;
    }



    public WebDriver initializeDriver() throws IOException {


        Properties prop = loadProperties();

        String browserName = System.getProperty("browser") != null ?
                System.getProperty("browser") : prop.getProperty("browser");


        driver = DriverFactory.getDriver(browserName).createDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT);
        return driver;

    }

    @BeforeMethod(alwaysRun = true)

    public void launchApplication() throws IOException {
        driver = initializeDriver();

        Properties prop = loadProperties();
        String appUrl = prop.getProperty("url");
        if (appUrl == null || appUrl.trim().isEmpty()) {
            throw new IllegalStateException("URL is missing in GlobalData.properties");
        }
        driver.get(appUrl);
        pages = new PageObjectManager(driver);

    }




    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }
}



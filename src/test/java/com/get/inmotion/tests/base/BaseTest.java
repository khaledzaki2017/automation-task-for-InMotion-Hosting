package com.get.inmotion.tests.base;

import com.get.inmotion.driver.DriverFactory;
import com.get.inmotion.helpers.ConfigReader;
import com.get.inmotion.tests.listeners.WebDriverProvider;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest implements WebDriverProvider {

    protected WebDriver driver;
    @Override
    public WebDriver getDriver() {
        return driver;
    }
    @BeforeMethod
    public void setUp() {
        String browser = ConfigReader.get("browser");
        DriverFactory.initDriver(browser);

        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("baseURL"));
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}

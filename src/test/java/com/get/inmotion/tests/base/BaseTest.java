package com.get.inmotion.tests.base;

import com.get.inmotion.driver.DriverFactory;
import com.get.inmotion.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

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

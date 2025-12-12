package com.get.inmotion.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HostingPage {

    private WebDriver driver;

    // Example locators based on the current hosting pricing section
    private By sharedHostingPlanPower = By.xpath("//*[contains(text(),'Power') and ancestor::*[contains(@class,'pricing')]]");
    private By sharedHostingPlanLaunch = By.xpath("//*[contains(text(),'Launch') and ancestor::*[contains(@class,'pricing')]]");

    private By buyNowBtn = By.xpath("//a[contains(text(),'Buy') or contains(text(),'Get Started')]");

    // Cart or checkout link
    private By cartBtn = By.xpath("//a[contains(@href, 'cart') or contains(text(),'Cart')]");

    public HostingPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectPowerPlan() {
        driver.findElement(sharedHostingPlanPower).click();
    }

    public void selectLaunchPlan() {
        driver.findElement(sharedHostingPlanLaunch).click();
    }

    public void addToCart() {
        driver.findElement(buyNowBtn).click();
    }

    public void goToCart() {
        driver.findElement(cartBtn).click();
    }
}

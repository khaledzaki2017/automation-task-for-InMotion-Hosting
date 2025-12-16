package com.get.inmotion.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HostingPage {

    private WebDriver driver;

    //  locators based on the current hosting pricing section
    private By sharedHostingLink = By.xpath("//a[@href='/shared-hosting']");

    private By starterSharedHostingPlans = By.xpath(
            "//button[contains(@class,'imh-term-selector')]" +
                    "[.//span[normalize-space()='Starter']]"

    );
    private By premiumSharedHostingPlans = By.xpath(
            "//button[contains(@class,'imh-term-selector')]" +
                    "[.//span[normalize-space()='Premium']]"
    );


    private By getSharedHostingPlanPower=By.xpath("//h3[text()='Power']/following-sibling::div[contains(@class, 'imh-pricing-container')]//div[contains(@class, 'active')]//a[text()=' Select']");


    private By sharedHostingPlanLaunch = By.xpath("//h3[text()='Launch']/following-sibling::div[contains(@class, 'imh-pricing-container')]//div[contains(@class, 'active')]//a[text()=' Select']");

    public HostingPage(WebDriver driver) {
        this.driver = driver;
    }



    public boolean verifyWebHostingPageUrl() {
        String expectedUrl = "https://www.inmotionhosting.com/web-hosting";
        String currentUrl = driver.getCurrentUrl();

        return currentUrl.equals(expectedUrl) || currentUrl.contains("/web-hosting");
    }
    public void selectSharedHostingPlan() {
        driver.findElement(sharedHostingLink).click();
    }

    public void selectPremiumSwitcher() {
        driver.findElement(premiumSharedHostingPlans).click();
    }
    public void selectStarterSwitcher() {driver.findElement(starterSharedHostingPlans).click();}

    public void selectPowerSharedPlan() {
        WebElement button=driver.findElement(getSharedHostingPlanPower);
         ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);

    }

    public void selectLaunchPlan() {
        WebElement button=driver.findElement(sharedHostingPlanLaunch);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }
}

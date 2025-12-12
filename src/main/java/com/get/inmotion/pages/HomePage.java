package com.get.inmotion.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HomePage {

    private WebDriver driver;

    // Current navigation menu - using visible link text
    private By domainNamesLink = By.linkText("Domain Names");
    private By hostingMenu = By.xpath("//a[contains(text(),'Hosting')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Navigate to the Domain Search / Domain Names section
     */
    public void goToDomainNamesSection() {
        WebElement domainSection = driver.findElement(domainNamesLink);
        domainSection.click();
    }

    /**
     * Navigate to the Hosting section
     */
    public void goToHostingPage() {
        driver.findElement(hostingMenu).click();
    }
}
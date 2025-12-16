package com.get.inmotion.pages;

import com.get.inmotion.helpers.WaitHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;

public class HomePage {

    private WebDriver driver;

    // Locators
    private By domainNamesLink = By.linkText("Domain Names");
    private By allHostingMenu = By.xpath("//a[@title='View All Web Hosting']//span[@class='subnav-title']");
    private By hostingMenu=By.xpath("//a[@id='allHostingDropDown']");
    private By acceptCookiesBtn = By.id("onetrust-accept-btn-handler");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Handle Cookie Banner
     */
    public void acceptCookiesIfVisible() {
        try {
            WebElement btn = WaitHelper.waitForClickable(driver, acceptCookiesBtn);
            btn.click();

            // Wait for cookie banner to disappear
            WaitHelper.waitForInvisible(driver, acceptCookiesBtn);
        } catch (Exception ignored) {
            // Cookie banner not shown
        }
    }


    /**
     * Navigate to "Domain Names" page
     */
    public void goToDomainNamesSection() {
        acceptCookiesIfVisible();

        // Ensure the element is clickable
        WebElement domainLink = WaitHelper.waitForClickable(driver, domainNamesLink);

        // Scroll into view to avoid intercept by overlays
        scrollIntoView(domainLink);

        domainLink.click();
    }

    public void selectHostingMenu(){


// 2. Hover over the menu (common for dropdowns)
        WebElement hostingElement = driver.findElement(hostingMenu);

        Actions actions = new Actions(driver);
        actions.moveToElement(hostingElement).perform();
        hostingElement.click();

// 3. Click "View All Web Hosting" option
        WebElement allHostingLink = WaitHelper.waitForClickable(driver, allHostingMenu);
        allHostingLink.click();
    }

    /**
     * Utility: Scroll into view (prevents click interception)
//     */
    private void scrollIntoView(WebElement el) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        } catch (Exception ignored) {}
    }
}

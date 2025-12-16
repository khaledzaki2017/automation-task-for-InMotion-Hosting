package com.get.inmotion.pages;

import com.get.inmotion.helpers.WaitHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckoutPage {

    private final WebDriver driver;

    private static final String AMP_CHECKOUT_URL =
            "https://central.inmotionhosting.com/amp/checkout#domain";

    /* ===========================
       LOCATORS – AMP CHECKOUT
       =========================== */

    // Domain name shown in checkout summary
    private final By domainNameLabel =
            By.xpath("//sd-app//mat-expansion-panel//tr[1]/td[1]/div[2]");

    private final By domainLink=By.xpath("//span[normalize-space()='DOMAIN']");

    // Domain price row
    private final By domainPriceLabel =
            By.xpath("//tr[contains(., 'Domain Registration')]//span[contains(text(), '$')]");

    // CTA button (Add / Continue)
    private final By addToCartButton =
            By.xpath("//button[.//span[contains(text(),'Add') or contains(text(),'Continue')]]");

    // Domain availability error
    private final By domainTakenMessage =
            By.xpath("//*[contains(text(), 'is taken')]");
    // Purchase new domain radio
    private final By purchaseNewDomainRadio =
            By.cssSelector("mat-radio-button span:contains('Purchase new domain')");

    // Domain input
    private final By domainInput =
            By.xpath("//input[@data-placeholder='Enter your domain']");

    // Search Domains button
    private final By searchDomainsButton =
            By.xpath("//button[.//span[normalize-space()='Search Domains']]");


    /* ===========================
       CONSTRUCTOR
       =========================== */

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    /* ===========================
       AMP STATE WAITS
       =========================== */

    public void waitForDomainUrl() {
        WaitHelper.waitForDomReady(driver);
        WaitHelper.waitForUrlContains(driver, "#domain");

    }

    public void waitForCheckoutCompleteUrl() {
        WaitHelper.waitForDomReady(driver);
        WaitHelper.waitForUrlContains(driver, "#complete");

    }
//    public void waitForSearchActionToComplete() {
//        try {
//            //  Checkout URL reached
//            WaitHelper.waitForUrlContains(driver, "#complete");
//        } catch (Exception e) {
//            throw new TimeoutException(
//                    "Search action did not complete: no checkout or availability result detected."
//            );
//        }
//
//
//
//    }
    public void waitForSearchActionToComplete() {

        try {
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(45))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .until(d -> d.getCurrentUrl().contains("#complete"));

        } catch (TimeoutException e) {
            throw new TimeoutException(
                    "Search action did not complete: checkout page was not reached within 45 seconds.",
                    e
            );
        }
    }



    /* ===========================
       VERIFICATIONS
       =========================== */

    public String getDisplayedDomain() {
        return WaitHelper
                .waitForVisible(driver, domainNameLabel)
                .getText()
                .trim();
    }

    public String getDomainPrice() {
        return WaitHelper
                .waitForVisible(driver, domainPriceLabel)
                .getText()
                .trim();
    }

    /* ===========================
       ACTIONS
       =========================== */
    /**
     * Checks if the given domain is available in AMP checkout results.
     *
     * @param fullDomain ex: myautomationtest123.com
     * @return true if the domain is available, false otherwise
     */
    public boolean isDomainAvailable(String fullDomain) {

        WaitHelper.waitForClickable(driver, domainLink).click();

        By domainRowLocator = By.xpath(
                String.format("//div[contains(@class,'ctw-grid')][.//text()[normalize-space()='%s']]", fullDomain)
        );

        try {
            WebElement domainRow = WaitHelper.waitForVisible(driver, domainRowLocator);
            return domainRow != null;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Gets the price of the domain from AMP results and optionally clicks "Add & Continue".
     *
     * @param fullDomain ex: myautomationtest123.com
     * @param clickAddAndContinue whether to click "Add & Continue" button
     * @return price text (ex: $23.00 /yr)
     */
    public String getDomainPriceAndSelect(String fullDomain, boolean clickAddAndContinue) {
        By domainRowLocator = By.xpath(
                String.format("//div[contains(@class,'ctw-grid')][.//text()[normalize-space()='%s']]", fullDomain)
        );

        WebElement domainRow = WaitHelper.waitForVisible(driver, domainRowLocator);

        // ===== Extract price =====
        WebElement priceElement = domainRow.findElement(By.xpath(".//span[contains(text(),'/yr')]"));
        String priceText = priceElement.getText().trim();

        // ===== Click Add & Continue if requested =====
        if (clickAddAndContinue) {
            WebElement addAndContinueBtn = domainRow.findElement(
                    By.xpath(".//button[.//span[normalize-space()='Add & Continue']]")
            );
            WaitHelper.waitForClickable(driver, addAndContinueBtn).click();
        }

        return priceText;
    }


    public void waitForDomainStepReady() {
        WaitHelper.waitForPresent(driver, domainInput);
    }

    public void enterDomainNameJSHumanLike(String domainName) {

        WebElement input = WaitHelper.waitForClickable(driver, domainInput);

        // Focus + scroll (for Angular Material)
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});" +
                        "arguments[0].focus();" +
                        "arguments[0].value='';",
                input
        );

        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (char c : domainName.toCharArray()) {
            js.executeScript(
                    "arguments[0].value += arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('keydown'));" +
                            "arguments[0].dispatchEvent(new Event('keypress'));" +
                            "arguments[0].dispatchEvent(new Event('input'));" +
                            "arguments[0].dispatchEvent(new Event('keyup'));",
                    input,
                    String.valueOf(c)
            );

            // Small delay to simulate human typing
            try {
                Thread.sleep(120);
            } catch (InterruptedException ignored) {}
        }
    }



    /**
     * Wait until Search Domains becomes clickable then click
     */
    public void clickSearchDomains() {

        WebElement searchBtn =WaitHelper.waitForClickable(driver, searchDomainsButton);
            searchBtn.click();

    }

}

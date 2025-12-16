package com.get.inmotion.pages;

import com.get.inmotion.helpers.WaitHelper;
import org.openqa.selenium.*;

import java.util.List;

public class DomainSearchPage {

    private final WebDriver driver;

    private final By searchInput = By.id("domain_search_domain");
    private final By searchButton =
            By.xpath("//button[contains(text(),'Search')]");
    // ===== UNAVAILABLE DOMAIN LOCATORS =====

    private final By unavailableMessage =
            By.xpath("//span[contains(text(),'is taken')]");

    private final By suggestedDomainRows =
            By.xpath("//div[starts-with(@id,'tld')]");

    private final By suggestedDomainName =
            By.xpath(".//div[contains(@class,'ctw-col-span-3')]");

    private final By suggestedDomainPrice =
            By.xpath(".//span[contains(text(),'$')]");

    private final By unavailableButton =
            By.xpath(".//button[@disabled]//span[text()='Unavailable']");

    public DomainSearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForPageLoaded() {
        WaitHelper.waitForVisible(driver, searchInput);
    }

    public void searchDomain(String domain) {
        WebElement input = WaitHelper.waitForVisible(driver, searchInput);
        input.clear();
        input.sendKeys(domain);
        WaitHelper.waitForClickable(driver, searchButton).click();
    }
    /**
     * Verify unavailable message is displayed
     * Example: "google.com is taken."
     */
    public boolean isUnavailableMessageDisplayedFor(String domain) {
        WebElement message =
                WaitHelper.waitForVisible(driver, unavailableMessage);

        return message.getText().contains(domain)
                && message.getText().contains("taken");
    }

    /**
     * Get count of suggested alternative domains
     */
    public int getSuggestedDomainsCount() {
        List<WebElement> rows =
                WaitHelper.waitForAllVisible(driver, suggestedDomainRows);

        return rows.size();
    }

}

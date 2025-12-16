package com.get.inmotion.pages;

import com.get.inmotion.helpers.WaitHelper;
import org.openqa.selenium.*;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage {

    private final WebDriver driver;

    // ===== STABLE ANCHORS =====
    private final By orderSummaryTitle =
            By.xpath("//span[normalize-space()='Order Summary']");

    private final By cartRows =
            By.cssSelector("table.mat-table tr.mat-row");

    private final By removeIcon =
            By.cssSelector("mat-icon.remove-cart-item");

    private final By totalPrice =
            By.xpath(  "//div[contains(@class,'ctw-font-bold')]" +
                    "[.//text()[normalize-space()='Total']]" +
                    "/span[contains(@class,'ctw-float-right')]");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // ===== PAGE SYNC =====
    public void waitForCartLoaded() {
        WaitHelper.waitForDomReady(driver);
        WaitHelper.waitForUrlContains(driver, "#complete");
        WaitHelper.waitForVisible(driver, orderSummaryTitle);
        WaitHelper.waitForPresent(driver, cartRows);
    }

    // ===== CART DATA =====
    public List<String> getCartItems() {
        waitForCartLoaded();
        return driver.findElements(cartRows)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public boolean isItemPresent(String text) {
        return getCartItems()
                .stream()
                .anyMatch(item -> item.contains(text));
    }

    public int getItemsCount() {
        return driver.findElements(cartRows).size();
    }

    public String getTotalPrice() {
        waitForCartLoaded();
        return WaitHelper.waitForVisible(driver, totalPrice).getText();
    }

    // ===== ACTIONS =====
    public void removeItemContaining(String text) {
        waitForCartLoaded();

        for (WebElement row : driver.findElements(cartRows)) {
            if (row.getText().contains(text)) {
                row.findElement(removeIcon).click();
                WaitHelper.waitForDomReady(driver);
                return;
            }
        }
        throw new AssertionError("Item not found in cart: " + text);
    }

    public void refresh() {
        driver.navigate().refresh();
        waitForCartLoaded();
    }
}

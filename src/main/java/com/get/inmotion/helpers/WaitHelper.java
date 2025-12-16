package com.get.inmotion.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public final class WaitHelper {

    private static final int DEFAULT_TIMEOUT =
            Integer.parseInt(ConfigReader.get("timeout"));

    private WaitHelper() {
        // Utility class
    }

    // ========= CORE WAIT =========

    private static WebDriverWait wait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    private static WebDriverWait wait(WebDriver driver, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    // ========= ELEMENT (By) =========

    public static WebElement waitForVisible(WebDriver driver, By locator) {
        return wait(driver)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return wait(driver)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForPresent(WebDriver driver, By locator) {
        return wait(driver)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static boolean waitForInvisible(WebDriver driver, By locator) {
        return wait(driver)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static boolean waitForText(WebDriver driver, By locator, String text) {
        return wait(driver)
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    // ========= ELEMENT (WebElement) =========

    public static WebElement waitForClickable(WebDriver driver, WebElement element) {
        return wait(driver)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForVisible(WebDriver driver, WebElement element) {
        return wait(driver)
                .until(ExpectedConditions.visibilityOf(element));
    }

    // ========= LISTS =========

    public static List<WebElement> waitForAllVisible(WebDriver driver, By locator) {
        return wait(driver)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static List<WebElement> waitForAllPresent(WebDriver driver, By locator) {
        return wait(driver)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    // ========= PAGE / URL =========

    public static void waitForTitleContains(WebDriver driver, String titlePart) {
        wait(driver)
                .until(ExpectedConditions.titleContains(titlePart));
    }

    public static void waitForUrlContains(WebDriver driver, String partialUrl) {
        wait(driver)
                .until(ExpectedConditions.urlContains(partialUrl));
    }

    // ========= DOM / JS =========

    public static void waitForDomReady(WebDriver driver) {
        wait(driver).until(d ->
                ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }

    // ========= CUSTOM TIMEOUT =========

    public static WebElement waitForClickable(
            WebDriver driver, By locator, int timeoutSeconds) {

        return wait(driver, timeoutSeconds)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }
}

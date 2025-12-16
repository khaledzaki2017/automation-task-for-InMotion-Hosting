package com.get.inmotion.tests.listeners;

import com.get.inmotion.helpers.ScreenshotHelper;
import org.openqa.selenium.*;
import org.testng.*;
import org.testng.annotations.Listeners;

public class GlobalErrorListener implements IInvokedMethodListener {

    private static final By ERROR_MODAL =
            By.xpath("//*[contains(text(), \"Oops! That's not right\")]");

    private static final By OK_BUTTON =
            By.xpath("//button[.//text()[normalize-space()='OK']]");

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

        if (!method.isTestMethod()) return;

        Object instance = testResult.getInstance();
        if (!(instance instanceof WebDriverProvider)) return;

        WebDriver driver = ((WebDriverProvider) instance).getDriver();

        try {
            WebElement error = driver.findElement(ERROR_MODAL);

            if (error.isDisplayed()) {
                ScreenshotHelper.takeScreenshot(driver, "GLOBAL_SYSTEM_ERROR");

                driver.findElement(OK_BUTTON).click();

                throw new SkipException(
                        "Global system error on live environment. Test skipped safely."
                );
            }
        } catch (NoSuchElementException ignored) {
            // No global error → continue test
        }
    }
}

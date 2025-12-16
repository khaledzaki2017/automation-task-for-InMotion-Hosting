package com.get.inmotion.tests.base;


import com.get.inmotion.driver.DriverFactory;
import com.get.inmotion.helpers.ScreenshotHelper;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        ScreenshotHelper.takeScreenshot(DriverFactory.getDriver(),
                "FAIL_" + result.getName());
    }
}
package com.get.inmotion.tests.e2e;

import com.get.inmotion.helpers.CommonHelper;
import com.get.inmotion.helpers.ScreenshotHelper;
import com.get.inmotion.helpers.WaitHelper;
import com.get.inmotion.pages.*;
import com.get.inmotion.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class DomainHostingFlowTest extends BaseTest {

    @Test
    public void testDomainHostingFlow() {

        String domain = "myautomationtest123.com";
        String webHostig = "- Web Hosting";

        HomePage home = new HomePage(driver);
        DomainSearchPage domainPage = new DomainSearchPage(driver);
        CheckoutPage ampPage = new CheckoutPage(driver);
        HostingPage hostingPage = new HostingPage(driver);
        CartPage cart = new CartPage(driver);

        // ===== STEP 1: HOME =====
        WaitHelper.waitForTitleContains(driver, "InMotion Hosting");
        Assert.assertTrue(driver.getTitle().contains("InMotion"));

        // ===== STEP 2: DOMAIN SEARCH =====
        home.goToDomainNamesSection();
        domainPage.waitForPageLoaded();
        domainPage.searchDomain(domain);

        ScreenshotHelper.takeScreenshot(driver, "Domain_Search");

        // ===== STEP 3: AMP DOMAIN =====
        ampPage.waitForCheckoutCompleteUrl();
//        ampPage.waitForDomainStep();
        // Verify domain availability
        Assert.assertTrue(ampPage.isDomainAvailable(domain), "Domain is not available: " + domain);

        // Get the domain price and select it
        String priceOnSearch = ampPage.getDomainPriceAndSelect(domain, true);
        ampPage.waitForCheckoutCompleteUrl();
        ScreenshotHelper.takeScreenshot(driver, "Checkout_Complete");

        Assert.assertEquals(ampPage.getDisplayedDomain(), domain);
        String priceOnCheckout = ampPage.getDomainPrice();
        Assert.assertTrue(ampPage.getDomainPrice().contains("$"));

        // Assert the price matches
        BigDecimal normPriceOnSearch = CommonHelper.normalizePrice(priceOnSearch);
        BigDecimal normPriceOnCheckout = CommonHelper.normalizePrice(priceOnCheckout);
        Assert.assertEquals(normPriceOnSearch, normPriceOnCheckout);

        // ampPage.addDomainToCart();
        driver.navigate().to("https://www.inmotionhosting.com/");

        // ===== STEP 4: HOSTING SELECTION =====
        home.selectHostingMenu();
        hostingPage.verifyWebHostingPageUrl();
        hostingPage.selectSharedHostingPlan();

        hostingPage.selectPremiumSwitcher();
        hostingPage.selectPowerSharedPlan();
        //        hostingPage.selectPowerPlan();
        //        hostingPage.selectPowerSharedPlan();

        ampPage.waitForDomainUrl();
        //        ampPage.ensurePurchaseNewDomainSelected();
        ampPage.waitForDomainStepReady();
        ampPage.enterDomainNameJSHumanLike("myautomationtest123.com");

        ampPage.clickSearchDomains();
        ampPage.waitForSearchActionToComplete();

        ampPage.waitForCheckoutCompleteUrl();

        ScreenshotHelper.takeScreenshot(driver, "Cart_With_Domain_And_Hosting");

        // ===== STEP 5: CART VERIFICATION =====
        //        hostingPage.goToCart();
        Assert.assertTrue(cart.isItemPresent(domain));

        String totalBefore = cart.getTotalPrice();
        System.out.println("Total Price: " + totalBefore);

        // ===== STEP 6: REFRESH =====
        cart.refresh();
        Assert.assertEquals(cart.getTotalPrice(), totalBefore);

        // ===== STEP 7: REMOVE PLAN =====
        cart.removeItemContaining(webHostig);
        ScreenshotHelper.takeScreenshot(driver, "Remove_Plan");

        Assert.assertFalse(cart.isItemPresent(webHostig));

        // ===== STEP 8: CHANGE HOSTING PLAN =====
        driver.navigate().to("https://www.inmotionhosting.com/shared-hosting");

        hostingPage.selectStarterSwitcher();
        hostingPage.selectLaunchPlan();
        ampPage.waitForDomainStepReady();
        ampPage.enterDomainNameJSHumanLike("myautomationtest123.com");

        ampPage.clickSearchDomains();
        ampPage.waitForSearchActionToComplete();

        ampPage.waitForCheckoutCompleteUrl();
//        hostingPage.addToCart();
//        hostingPage.goToCart();
        Assert.assertTrue(cart.getItemsCount() >= 1);

        Assert.assertTrue(cart.isItemPresent("Launch"));
        ScreenshotHelper.takeScreenshot(driver, "Cart_With_Updated_Hosting_Plan");


    }

    @Test
    public void testDomainInvalidDomainSearch() {

        String takenDomain = "google.com";

        DomainSearchPage domainSearchPage = new DomainSearchPage(driver);
        HomePage home = new HomePage(driver);
        // ===== STEP 1: HOME =====
        WaitHelper.waitForTitleContains(driver, "InMotion Hosting");
        Assert.assertTrue(driver.getTitle().contains("InMotion"));

        // ===== STEP 2: DOMAIN SEARCH =====
        home.goToDomainNamesSection();
        domainSearchPage.waitForPageLoaded();
        domainSearchPage.searchDomain(takenDomain);
        // ====== STEP 3: Validate Invalid Domain Search ======
        Assert.assertTrue(
                domainSearchPage.isUnavailableMessageDisplayedFor(takenDomain),
                "Unavailable message not shown for taken domain"
        );

        int suggestionsCount = domainSearchPage.getSuggestedDomainsCount();
        Assert.assertTrue(
                suggestionsCount > 0,
                "No alternative domains were suggested"
        );

    }
}
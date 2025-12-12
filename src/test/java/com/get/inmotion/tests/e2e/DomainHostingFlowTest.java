package com.get.inmotion.tests.e2e;

import com.get.inmotion.pages.CartPage;
import com.get.inmotion.pages.DomainSearchPage;
import com.get.inmotion.pages.HomePage;
import com.get.inmotion.pages.HostingPage;
import com.get.inmotion.tests.base.BaseTest;
import com.get.inmotion.utils.ScreenshotHelper;
import com.get.inmotion.utils.WaitHelper;
import org.testng.Assert;

public class DomainHostingFlowTest  extends BaseTest {
    public void testDomainHostingFlow(){
        WaitHelper wait=new WaitHelper(driver);
        // PAGE OBJECTS
        HomePage home = new HomePage(driver);
        DomainSearchPage domainPage = new DomainSearchPage(driver);
        HostingPage hosting = new HostingPage(driver);
        CartPage cart = new CartPage(driver);

        // STEP 1: Verify title
        wait.waitForPageTitle("InMotion Hosting");
        Assert.assertTrue(driver.getTitle().contains("InMotion"), "Homepage title mismatch!");

        // STEP 2: Navigate to Domain Names
        home.goToDomainNamesSection();

        // STEP 3: Search for domain
        String domainName = "myautomationtest123.com";
        domainPage.searchDomain(domainName);

        // Screenshot 1
        ScreenshotHelper.takeScreenshot(driver, "Domain_Search_Result");

        // Validate domain availability
        Assert.assertTrue(domainPage.isDomainAvailable(), "Domain should be available");

        // Save price
        String domainPrice = domainPage.getDomainPrice();
        Assert.assertNotNull(domainPrice);

        // Add domain to cart
        domainPage.addToCart();

        // STEP 4: Navigate to hosting
        home.goToHostingPage();

        // STEP 5: Select Power Plan
        hosting.selectPowerPlan();

        // Screenshot 2
        ScreenshotHelper.takeScreenshot(driver, "Hosting_PowerPlan_Selected");

        hosting.addToCart();

        // Go to cart
        hosting.goToCart();

        // Verify domain + hosting exist
        Assert.assertTrue(cart.getCartItems().size() >= 2, "Domain + hosting should be in the cart.");

        // Screenshot 3 cart
        ScreenshotHelper.takeScreenshot(driver, "CartView");

        // Save total price before refresh
        String priceBeforeRefresh = cart.getTotalPrice();

        // STEP 6: Refresh cart
        cart.refresh();

        // Validate items persisted
        Assert.assertEquals(cart.getTotalPrice(), priceBeforeRefresh);

        // STEP 7: Remove domain
        cart.removeItem();

        // Screenshot 4
        ScreenshotHelper.takeScreenshot(driver, "Cart_After_Domain_Removal");

        Assert.assertFalse(cart.getCartItems().isEmpty(), "Domain removed; hosting remains.");

        // STEP 8: Update hosting plan
        home.goToHostingPage();
        hosting.selectLaunchPlan();

        // Screenshot 4
        ScreenshotHelper.takeScreenshot(driver, "Hosting_LaunchPlan_Selected");


        hosting.addToCart();
        hosting.goToCart();

        Assert.assertFalse(cart.getCartItems().isEmpty(), "New hosting plan updated.");

        // STEP 9: Negative test - invalid domain
        home.goToDomainNamesSection();
        domainPage.searchDomain("google.com");

        //Screenshot 5
        ScreenshotHelper.takeScreenshot(driver, "Negative_Domain_Search");

        Assert.assertFalse(domainPage.isDomainAvailable(), "google.com should NOT be available");

    }
}

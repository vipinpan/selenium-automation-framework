package org.test.selenium;

import org.main.selenium.SauceDemoPage;
import org.main.selenium.reporting.ReportManager;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Listeners(ReportManager.class)
public class SauceDemoTest extends SauceDemoPage {

    @Test(priority = 1)
    @Parameters({"browser"})
    public void testValidLogin(@Optional("chrome") String browser) {
        navigateToSauceDemo();
        loginToSauceDemo("standard_user", "secret_sauce");
        verifyLoginSuccess();
    }

    @Test(priority = 2)
    @Parameters({"browser"})
    public void testCompletePurchaseFlow(@Optional("chrome") String browser) {
        // Login to Sauce Demo
        navigateToSauceDemo();
        loginToSauceDemo("standard_user", "secret_sauce");
        verifyLoginSuccess();
        
        // Add backpack to cart and complete checkout process
        addToCartCheckout();
        
        // Finish the purchase
        finishCheckout();
    }

    @Test(priority = 3)
    @Parameters({"browser"})
    public void testLoginWithTestData(@Optional("chrome") String browser) {
        loginWithTestData("Valid purchase test");
        verifyLoginSuccess();
        
        // Add backpack to cart and complete checkout
        addToCartCheckout();
        finishCheckout();
    }
}

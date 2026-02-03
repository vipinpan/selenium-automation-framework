package org.main.selenium;

import org.main.selenium.base.BaseTest;
import org.main.selenium.locators.SauceDemoLocators;
import org.main.selenium.reporting.Logger;
import org.main.selenium.reporting.ReportManager;
import org.main.selenium.utils.ConfigReader;
import org.main.selenium.utils.JsonDataReader;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.Map;

public class SauceDemoPage extends BaseTest {

    public void navigateToSauceDemo() {
        Logger.info("Navigating to sauce Demo");
        navigateTo(ConfigReader.getApplicationURL());
        ReportManager.logInfo("Successfully navigated to Sauce Demo");
    }

    public void loginToSauceDemo(String username, String password) {
        try {
            Logger.info("Attempting to login to Sauce Demo with username: " + username);

            sendKeys(SauceDemoLocators.USER_NAME, username);
            Logger.info("Entered username");

            sendKeys(SauceDemoLocators.PASSWORD, password);
            Logger.info("Entered password");

            clickElement(SauceDemoLocators.LOGIN_BUTTON);
            Logger.info("Clicked login button");

            if(isAlertPresent()){
                acceptAlert();
            }

            ReportManager.logPass("Sauce Demo login attempt completed");

        } catch (Exception e) {
            Logger.logError("Sauce Demo login", "Login failed: " + e.getMessage());
            ReportManager.logFailWithScreenshot("Login failed", driver);
            throw e;
        }
    }

    public void loginWithTestData(String testName) {
        Map<String, Object> testData = JsonDataReader.getTestDataByTestName(
                "saucedemo_test_data.json", "purchase_bagpack_test", testName);

        String username = JsonDataReader.getStringValue(testData, "username");
        String password = JsonDataReader.getStringValue(testData, "password");
        String expectedResult = JsonDataReader.getStringValue(testData, "expectedResult");

        navigateToSauceDemo();
        loginToSauceDemo(username, password);

    }

    public void verifyLoginSuccess() {
        try {
            waitForElementToDisappear(SauceDemoLocators.LOGIN_BUTTON);
            ReportManager.logPass("Sauce Demo login verification successful - User logged in");
            Logger.info("Login verification successful");
        } catch (Exception e) {
            ReportManager.logFail("Sauce Demo login verification failed - User not logged in");
            Assert.fail("Login verification failed");
        }
    }

    public void addToCartCheckout() {
        try {
            Logger.info("Starting add to cart and checkout process");

            // Click on the backpack product
            clickElement(SauceDemoLocators.BAGPACK);
            Logger.info("Clicked on backpack product");

            // Add to cart
            clickElement(SauceDemoLocators.ADD_TO_CART);
            Logger.info("Added backpack to cart");

            // Verify cart badge appears
            waitForElementToBeVisible(SauceDemoLocators.CART_ITEM);
            Logger.info("Cart badge displayed - item added successfully");

            if(isAlertPresent()){
                acceptAlert();
            }

            // Click on cart to proceed to checkout
            clickElement(SauceDemoLocators.CART);
            Logger.info("Navigated to cart page");

            // Click checkout button
            clickElement(SauceDemoLocators.CHECKOUT);
            Logger.info("Clicked checkout button");

            // Fill checkout information
            sendKeys(SauceDemoLocators.FIRST_NAME, "John");
            Logger.info("Entered first name");

            sendKeys(SauceDemoLocators.LAST_NAME, "Doe");
            Logger.info("Entered last name");

            sendKeys(SauceDemoLocators.POSTAL_CODE, "12345");
            Logger.info("Entered postal code");

            // Click continue to proceed
            clickElement(SauceDemoLocators.CONTINUE);
            Logger.info("Clicked continue button");

            // Verify checkout overview page
            waitForElementToBeVisible(SauceDemoLocators.CHECKOUT_OVERVIEW);
            Logger.info("Checkout overview page displayed");

            // Verify total price is displayed
            if (isElementDisplayed(SauceDemoLocators.TOTAL_PRICE)) {
                String totalPrice = getText(SauceDemoLocators.TOTAL_PRICE);
                Logger.info("Total price displayed: " + totalPrice);
                ReportManager.logPass("Checkout process completed successfully. Total: " + totalPrice);
            } else {
                ReportManager.logFail("Total price not displayed");
                Assert.fail("Total price not displayed on checkout overview");
            }

        } catch (Exception e) {
            Logger.logError("Add to cart checkout", "Checkout process failed: " + e.getMessage());
            ReportManager.logFailWithScreenshot("Checkout process failed", driver);
            throw e;
        }
    }

    public void finishCheckout() {
        try {
            Logger.info("Finishing checkout process");

            // Click finish button
            clickElement(SauceDemoLocators.FINISH);
            Logger.info("Clicked finish button");

            ReportManager.logPass("Checkout completed successfully");

        } catch (Exception e) {
            Logger.logError("Finish checkout", "Failed to complete checkout: " + e.getMessage());
            ReportManager.logFailWithScreenshot("Failed to complete checkout", driver);
            throw e;
        }
    }
}


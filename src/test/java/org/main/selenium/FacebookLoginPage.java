package org.main.selenium;

import org.main.selenium.base.BaseTest;
import org.main.selenium.locators.FacebookLocators;
import org.main.selenium.reporting.Logger;
import org.main.selenium.reporting.ReportManager;
import org.main.selenium.utils.ConfigReader;
import org.main.selenium.utils.JsonDataReader;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.Map;

public class FacebookLoginPage extends BaseTest {

    public void navigateToFacebook() {
        Logger.info("Navigating to Facebook");
        navigateTo(ConfigReader.getApplicationURL());
        ReportManager.logInfo("Successfully navigated to Facebook");
    }

    public void loginToFacebook(String username, String password) {
        try {
            Logger.info("Attempting to login with username: " + username);
            
            sendKeys(FacebookLocators.EMAIL_FIELD, username);
            Logger.info("Entered username");
            
            sendKeys(FacebookLocators.PASSWORD_FIELD, password);
            Logger.info("Entered password");
            
            clickElement(FacebookLocators.LOGIN_BUTTON);
            Logger.info("Clicked login button");
            
            ReportManager.logPass("Login attempt completed");
            
        } catch (Exception e) {
            Logger.logError("Facebook login", "Login failed: " + e.getMessage());
            ReportManager.logFailWithScreenshot("Login failed", driver);
            throw e;
        }
    }

    public void loginWithTestData(String testName) {
        Map<String, Object> testData = JsonDataReader.getTestDataByTestName(
            "facebook_test_data.json", "loginTests", testName);
        
        String username = JsonDataReader.getStringValue(testData, "username");
        String password = JsonDataReader.getStringValue(testData, "password");
        String expectedResult = JsonDataReader.getStringValue(testData, "expectedResult");
        
        navigateToFacebook();
        loginToFacebook(username, password);
        
        if ("success".equals(expectedResult)) {
            verifyLoginSuccess();
        } else {
            verifyLoginFailure();
        }
    }

    public void verifyLoginSuccess() {
        try {
            waitForElementToDisappear(FacebookLocators.LOGIN_BUTTON);
            ReportManager.logPass("Login verification successful - User logged in");
            Logger.info("Login verification successful");
        } catch (Exception e) {
            ReportManager.logFail("Login verification failed - User not logged in");
            Assert.fail("Login verification failed");
        }
    }

    public void verifyLoginFailure() {
        try {
            if (isElementDisplayed(FacebookLocators.ERROR_MESSAGE)) {
                String errorMessage = getText(FacebookLocators.ERROR_MESSAGE);
                ReportManager.logPass("Login failed as expected: " + errorMessage);
                Logger.info("Login failed as expected: " + errorMessage);
            } else {
                ReportManager.logFail("Expected error message not displayed");
                Assert.fail("Expected error message not displayed");
            }
        } catch (Exception e) {
            ReportManager.logFail("Error message verification failed");
            Assert.fail("Error message verification failed");
        }
    }

    public void createNewAccount(String firstName, String lastName, String email, 
                                String password, String day, String month, String year) {
        try {
            Logger.info("Starting account creation process");
            
            clickElement(FacebookLocators.CREATE_ACCOUNT_LINK);
            waitForElementToBeVisible(FacebookLocators.FIRSTNAME_FIELD);
            
            sendKeys(FacebookLocators.FIRSTNAME_FIELD, firstName);
            sendKeys(FacebookLocators.LASTNAME_FIELD, lastName);
            sendKeys(FacebookLocators.REG_EMAIL_FIELD, email);
            sendKeys(FacebookLocators.REG_PASSWORD_FIELD, password);
            
            selectFromDropdown(FacebookLocators.BIRTHDAY_DAY, day);
            selectFromDropdown(FacebookLocators.BIRTHDAY_MONTH, month);
            selectFromDropdown(FacebookLocators.BIRTHDAY_YEAR, year);
            
            Logger.info("Account form filled successfully");
            ReportManager.logInfo("Account creation form filled");
            
        } catch (Exception e) {
            Logger.logError("Account creation", "Form filling failed: " + e.getMessage());
            ReportManager.logFailWithScreenshot("Account creation failed", driver);
            throw e;
        }
    }

    public void selectGender(String gender) {
        try {
            By genderLocator = By.xpath("//input[@type='radio' and @value='" + gender.toLowerCase() + "']");
            clickElement(genderLocator);
            Logger.info("Selected gender: " + gender);
            ReportManager.logInfo("Gender selected: " + gender);
        } catch (Exception e) {
            Logger.logError("Gender selection", "Failed to select gender: " + e.getMessage());
            ReportManager.logFailWithScreenshot("Gender selection failed", driver);
            throw e;
        }
    }

    public void submitRegistration() {
        try {
            clickElement(FacebookLocators.SIGNUP_BUTTON);
            Logger.info("Submitted registration form");
            ReportManager.logInfo("Registration form submitted");
        } catch (Exception e) {
            Logger.logError("Registration submission", "Failed to submit: " + e.getMessage());
            ReportManager.logFailWithScreenshot("Registration submission failed", driver);
            throw e;
        }
    }

    public void searchForUser(String searchTerm) {
        try {
            Logger.info("Searching for user: " + searchTerm);
            
            if (isElementDisplayed(FacebookLocators.SEARCH_BOX)) {
                sendKeys(FacebookLocators.SEARCH_BOX, searchTerm);
                clickElement(FacebookLocators.SEARCH_BOX);
                Logger.info("Search completed for: " + searchTerm);
                ReportManager.logInfo("Search performed for: " + searchTerm);
            } else {
                Logger.logError("Search", "Search box not found");
                ReportManager.logFail("Search box not available");
            }
        } catch (Exception e) {
            Logger.logError("Search", "Search failed: " + e.getMessage());
            ReportManager.logFailWithScreenshot("Search failed", driver);
            throw e;
        }
    }

    public void logoutFromFacebook() {
        try {
            Logger.info("Attempting to logout");
            
            mouseHover(FacebookLocators.PROFILE_MENU);
            waitForElementToBeVisible(FacebookLocators.SETTINGS_MENU);
            
            mouseHover(FacebookLocators.SETTINGS_MENU);
            waitForElementToBeVisible(FacebookLocators.LOGOUT_BUTTON);
            
            clickElement(FacebookLocators.LOGOUT_BUTTON);
            
            Logger.info("Logout successful");
            ReportManager.logPass("Logout completed successfully");
            
        } catch (Exception e) {
            Logger.logError("Logout", "Logout failed: " + e.getMessage());
            ReportManager.logFailWithScreenshot("Logout failed", driver);
            throw e;
        }
    }
}

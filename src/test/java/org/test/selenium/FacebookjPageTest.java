package org.test.selenium;

import org.main.selenium.FacebookLoginPage;
import org.main.selenium.reporting.ReportManager;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Listeners(ReportManager.class)
public class FacebookjPageTest extends FacebookLoginPage {

    @Test
    @Parameters({"browser"})
    public void testValidLogin(@Optional("chrome") String browser) {
        loginWithTestData("Valid Login Test");
    }

    @Test
    @Parameters({"browser"})
    public void testInvalidPasswordLogin(@Optional("chrome") String browser) {
        loginWithTestData("Invalid Password Test");
    }

    @Test
    @Parameters({"browser"})
    public void testInvalidUsernameLogin(@Optional("chrome") String browser) {
        loginWithTestData("Invalid Username Test");
    }

    @Test
    @Parameters({"browser"})
    public void testEmptyFieldsLogin(@Optional("chrome") String browser) {
        loginWithTestData("Empty Fields Test");
    }

    @Test
    @Parameters({"browser"})
    public void testAccountCreation(@Optional("chrome") String browser) {
        navigateToFacebook();
        createNewAccount("John", "Doe", "john.doe.test@example.com", 
                        "Test@1234", "15", "Jan", "1990");
        selectGender("male");
        submitRegistration();
    }

    @Test
    @Parameters({"browser"})
    public void testSearchFunctionality(@Optional("chrome") String browser) {
        navigateToFacebook();
        loginToFacebook("vipin@gmail.com", "Vipin@1234");
        searchForUser("John Doe");
        logoutFromFacebook();
    }
}

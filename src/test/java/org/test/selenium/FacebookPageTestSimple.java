package org.test.selenium;

import org.main.selenium.FacebookLoginPage;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FacebookPageTestSimple extends FacebookLoginPage {

    @Test
    @Parameters({"browser"})
    public void testValidLogin(@Optional("chrome") String browser) {
        System.out.println("Running testValidLogin with browser: " + browser);
        loginWithTestData("Valid Login Test");
    }

    @Test
    @Parameters({"browser"})
    public void testInvalidPasswordLogin(@Optional("chrome") String browser) {
        System.out.println("Running testInvalidPasswordLogin with browser: " + browser);
        loginWithTestData("Invalid Password Test");
    }

    @Test
    @Parameters({"browser"})
    public void testInvalidUsernameLogin(@Optional("chrome") String browser) {
        System.out.println("Running testInvalidUsernameLogin with browser: " + browser);
        loginWithTestData("Invalid Username Test");
    }

    @Test
    @Parameters({"browser"})
    public void testEmptyFieldsLogin(@Optional("chrome") String browser) {
        System.out.println("Running testEmptyFieldsLogin with browser: " + browser);
        loginWithTestData("Empty Fields Test");
    }

    @Test
    @Parameters({"browser"})
    public void testAccountCreation(@Optional("chrome") String browser) {
        System.out.println("Running testAccountCreation with browser: " + browser);
        navigateToFacebook();
        createNewAccount("John", "Doe", "john.doe.test@example.com", 
                        "Test@1234", "15", "Jan", "1990");
        selectGender("male");
        submitRegistration();
    }

    @Test
    @Parameters({"browser"})
    public void testSearchFunctionality(@Optional("chrome") String browser) {
        System.out.println("Running testSearchFunctionality with browser: " + browser);
        navigateToFacebook();
        loginToFacebook("vipin@gmail.com", "Vipin@1234");
        searchForUser("John Doe");
        logoutFromFacebook();
    }
}

package org.main.selenium.locators;

import org.openqa.selenium.By;

public class FacebookLocators {
    
    public static final By EMAIL_FIELD = By.id("email");
    public static final By PASSWORD_FIELD = By.id("pass");
    public static final By LOGIN_BUTTON = By.name("login");
    public static final By CREATE_ACCOUNT_LINK = By.linkText("Create new account");
    public static final By FORGOT_PASSWORD_LINK = By.linkText("Forgotten password?");
    public static final By SIGNUP_HEADER = By.xpath("//h2[contains(text(),'Create a new account')]");
    public static final By FIRSTNAME_FIELD = By.name("firstname");
    public static final By LASTNAME_FIELD = By.name("lastname");
    public static final By REG_EMAIL_FIELD = By.name("reg_email__");
    public static final By REG_PASSWORD_FIELD = By.name("reg_passwd__");
    public static final By BIRTHDAY_DAY = By.id("day");
    public static final By BIRTHDAY_MONTH = By.id("month");
    public static final By BIRTHDAY_YEAR = By.id("year");
    public static final By GENDER_RADIO = By.xpath("//input[@type='radio']");
    public static final By SIGNUP_BUTTON = By.name("websubmit");
    public static final By ERROR_MESSAGE = By.xpath("//div[contains(@class,'login_error')]");
    public static final By COOKIE_POLICY_LINK = By.linkText("Cookie Policy");
    public static final By PRIVACY_POLICY_LINK = By.linkText("Privacy Policy");
    public static final By TERMS_LINK = By.linkText("Terms");
    public static final By SEARCH_BOX = By.xpath("//input[@placeholder='Search Facebook']");
    public static final By PROFILE_MENU = By.xpath("//div[@aria-label='Menu']");
    public static final By SETTINGS_MENU = By.xpath("//span[text()='Settings & privacy']");
    public static final By LOGOUT_BUTTON = By.xpath("//span[text()='Log Out']");
}

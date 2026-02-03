package org.main.selenium.locators;

import org.openqa.selenium.By;

public class SauceDemoLocators {

    public static final By USER_NAME = By.xpath("//*[@id=\"user-name\"]");
    public static final By PASSWORD = By.id("password");
    public static final By LOGIN_BUTTON = By.id("login-button");
    public static final By BAGPACK = By.xpath("//div[text() = 'Sauce Labs Backpack']");
    public static final By ADD_TO_CART = By.id("add-to-cart");
    public static final By CART = By.xpath("//a[@class='shopping_cart_link']");
    public static final By CART_ITEM = By.xpath("//span[@class = 'shopping_cart_badge']");
    public static final By CHECKOUT = By.id("checkout");
    public static final By FIRST_NAME = By.id("first-name");
    public static final By LAST_NAME = By.id("last-name");
    public static final By POSTAL_CODE = By.id("postal-code");
    public static final By CONTINUE = By.id("continue");
    public static final By CHECKOUT_OVERVIEW = By.xpath("//span[text() = 'Checkout: Overview']");
    public static final By TOTAL_PRICE = By.xpath("//div[contains(text(), 'Total: $')]");
    public static final By FINISH = By.id("finish");
}

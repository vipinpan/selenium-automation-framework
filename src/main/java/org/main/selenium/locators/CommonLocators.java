package org.main.selenium.locators;

import org.openqa.selenium.By;

public class CommonLocators {
    
    public static final By BUTTON = By.tagName("button");
    public static final By INPUT = By.tagName("input");
    public static final By LINK = By.tagName("a");
    public static final By DROPDOWN = By.tagName("select");
    public static final By CHECKBOX = By.cssSelector("input[type='checkbox']");
    public static final By RADIO_BUTTON = By.cssSelector("input[type='radio']");
    public static final By TEXT_AREA = By.tagName("textarea");
    public static final By TABLE = By.tagName("table");
    public static final By TABLE_ROW = By.tagName("tr");
    public static final By TABLE_CELL = By.tagName("td");
    public static final By HEADING = By.tagName("h1");
    public static final By SUBHEADING = By.tagName("h2");
    public static final By PARAGRAPH = By.tagName("p");
    public static final By DIV = By.tagName("div");
    public static final By SPAN = By.tagName("span");
    public static final By IMAGE = By.tagName("img");
    public static final By LIST = By.tagName("li");
    public static final By NAVIGATION = By.tagName("nav");
    public static final By HEADER = By.tagName("header");
    public static final By FOOTER = By.tagName("footer");
    public static final By FORM = By.tagName("form");
    public static final By LABEL = By.tagName("label");
    public static final By ALERT = By.cssSelector(".alert");
    public static final By MODAL = By.cssSelector(".modal");
    public static final By TOOLTIP = By.cssSelector(".tooltip");
    public static final By LOADING_SPINNER = By.cssSelector(".loading");
    public static final By ERROR_MESSAGE = By.cssSelector(".error");
    public static final By SUCCESS_MESSAGE = By.cssSelector(".success");
    public static final By WARNING_MESSAGE = By.cssSelector(".warning");
    public static final By INFO_MESSAGE = By.cssSelector(".info");
}

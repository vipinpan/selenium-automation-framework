package org.main.selenium.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.List;

public class BaseTest {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    
    @Parameters({"browser"})
    @BeforeClass
    public void setUp(@Optional("chrome") String browser) {
        if (browser == null) {
            browser = "chrome";
        }
        
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                driver.manage().window().maximize();
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }
    
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    protected void navigateTo(String url) {
        driver.get(url);
    }
    
    protected WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    protected List<WebElement> findElements(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }
    
    protected void clickElement(By locator) {
        WebElement element = findElement(locator);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    
    protected void clickElement(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
    
    protected void sendKeys(By locator, String text) {
        WebElement element = findElement(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    protected void sendKeys(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }
    
    protected void selectFromDropdown(By locator, String visibleText) {
        Select dropdown = new Select(findElement(locator));
        dropdown.selectByVisibleText(visibleText);
    }
    
    protected void selectFromDropdown(By locator, int index) {
        Select dropdown = new Select(findElement(locator));
        dropdown.selectByIndex(index);
    }
    
    protected void selectFromDropdownByValue(By locator, String value) {
        Select dropdown = new Select(findElement(locator));
        dropdown.selectByValue(value);
    }
    
    protected void mouseHover(By locator) {
        WebElement element = findElement(locator);
        actions.moveToElement(element).perform();
    }
    
    protected void mouseHover(WebElement element) {
        actions.moveToElement(element).perform();
    }
    
    protected void rightClick(By locator) {
        WebElement element = findElement(locator);
        actions.contextClick(element).perform();
    }
    
    protected void doubleClick(By locator) {
        WebElement element = findElement(locator);
        actions.doubleClick(element).perform();
    }
    
    protected void dragAndDrop(By sourceLocator, By targetLocator) {
        WebElement source = findElement(sourceLocator);
        WebElement target = findElement(targetLocator);
        actions.dragAndDrop(source, target).perform();
    }
    
    protected boolean isElementDisplayed(By locator) {
        try {
            return findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    protected boolean isElementEnabled(By locator) {
        try {
            return findElement(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    protected String getText(By locator) {
        return findElement(locator).getText();
    }
    
    protected String getAttributeValue(By locator, String attributeName) {
        return findElement(locator).getAttribute(attributeName);
    }
    
    protected void switchToFrame(By frameLocator) {
        driver.switchTo().frame(findElement(frameLocator));
    }
    
    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
    
    protected void switchToWindow(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }
    
    protected void waitForElementToDisappear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    protected void waitForElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    protected void waitForTextToBePresent(By locator, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
    
    protected void scrollIntoView(By locator) {
        WebElement element = findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    protected void scrollBy(int x, int y) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(" + x + "," + y + ");");
    }
    
    protected void takeScreenshot(String fileName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            java.io.File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            java.io.File destFile = new java.io.File("screenshots/" + fileName + ".png");
            org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Alert/Popup Handling Methods
    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
    
    protected void acceptAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present to accept");
        }
    }
    
    protected void dismissAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present to dismiss");
        }
    }
    
    protected String getAlertText() {
        try {
            Alert alert = driver.switchTo().alert();
            return alert.getText();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present to get text");
            return null;
        }
    }
    
    protected void sendKeysToAlert(String text) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(text);
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present to send keys");
        }
    }
    
    protected void waitForAlert(int timeoutInSeconds) {
        try {
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            alertWait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException e) {
            System.out.println("Alert did not appear within " + timeoutInSeconds + " seconds");
        }
    }
    
    protected void handleAlert(String action, int timeoutInSeconds) {
        try {
            waitForAlert(timeoutInSeconds);
            
            switch (action.toLowerCase()) {
                case "accept":
                    acceptAlert();
                    break;
                case "dismiss":
                    dismissAlert();
                    break;
                default:
                    System.out.println("Invalid alert action: " + action + ". Use 'accept' or 'dismiss'");
            }
        } catch (Exception e) {
            System.out.println("Failed to handle alert: " + e.getMessage());
        }
    }
    
    protected void handleAlert(String action) {
        handleAlert(action, 10); // Default timeout of 10 seconds
    }
    
    // Window Popup Handling (for new windows/tabs)
    protected void switchToNewWindow() {
        String currentWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(currentWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }
    
    protected void closeCurrentWindowAndSwitchToParent() {
        String currentWindow = driver.getWindowHandle();
        driver.close();
        
        // Switch back to the original window
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(currentWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }
    
    protected void switchToWindowByTitle(String windowTitle) {
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
            if (driver.getTitle().equals(windowTitle)) {
                break;
            }
        }
    }
    
    protected void switchToWindowByUrl(String url) {
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
            if (driver.getCurrentUrl().contains(url)) {
                break;
            }
        }
    }
}

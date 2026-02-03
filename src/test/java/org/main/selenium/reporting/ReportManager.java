package org.main.selenium.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.main.selenium.utils.ConfigReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportManager implements ITestListener {
    
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static WebDriver driver;
    
    public static void initReports() {
        String reportPath = ConfigReader.getReportDirectory();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportFileName = "TestReport_" + timestamp + ".html";
        
        File reportDir = new File(reportPath);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath + reportFileName);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Selenium Test Execution Report");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        extent.setSystemInfo("Application", ConfigReader.getProperty("app.name", "Facebook Automation"));
        extent.setSystemInfo("Environment", ConfigReader.getProperty("test.environment", "QA"));
        extent.setSystemInfo("Browser", ConfigReader.getBrowser());
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
    }
    
    public static void createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
    }
    
    public static void logInfo(String message) {
        test.get().log(Status.INFO, message);
    }
    
    public static void logPass(String message) {
        test.get().log(Status.PASS, message);
    }
    
    public static void logFail(String message) {
        test.get().log(Status.FAIL, message);
    }
    
    public static void logSkip(String message) {
        test.get().log(Status.SKIP, message);
    }
    
    public static void logWarning(String message) {
        test.get().log(Status.WARNING, message);
    }
    
    public static void logInfoWithScreenshot(String message, WebDriver driver) {
        String screenshotPath = captureScreenshot(driver, "INFO_" + System.currentTimeMillis());
        test.get().log(Status.INFO, message);
        test.get().addScreenCaptureFromPath(screenshotPath);
    }
    
    public static void logPassWithScreenshot(String message, WebDriver driver) {
        String screenshotPath = captureScreenshot(driver, "PASS_" + System.currentTimeMillis());
        test.get().log(Status.PASS, message);
        test.get().addScreenCaptureFromPath(screenshotPath);
    }
    
    public static void logFailWithScreenshot(String message, WebDriver driver) {
        String screenshotPath = captureScreenshot(driver, "FAIL_" + System.currentTimeMillis());
        test.get().log(Status.FAIL, message);
        test.get().addScreenCaptureFromPath(screenshotPath);
    }
    
    private static String captureScreenshot(WebDriver driver, String fileName) {
        String screenshotPath = "";
        try {
            String screenshotDir = ConfigReader.getScreenshotDirectory();
            File dir = new File(screenshotDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotDir + fileName + ".png");
            
            org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
            screenshotPath = destFile.getAbsolutePath();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }
    
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
    
    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        createTest(result.getMethod().getMethodName());
        logInfo("Test started: " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        logPass("Test passed: " + result.getMethod().getMethodName());
        if (driver != null) {
            logPassWithScreenshot("Test completed successfully", driver);
        }
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        logFail("Test failed: " + result.getMethod().getMethodName());
        logFail("Failure reason: " + result.getThrowable().getMessage());
        if (driver != null) {
            logFailWithScreenshot("Test failed", driver);
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        logSkip("Test skipped: " + result.getMethod().getMethodName());
        logSkip("Skip reason: " + result.getThrowable().getMessage());
    }
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logWarning("Test failed but within success percentage: " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onStart(ITestContext context) {
        initReports();
        // Don't log here as test context is not yet initialized
        // Create a test for the suite startup
        ExtentTest suiteTest = extent.createTest("Test Suite: " + context.getSuite().getName());
        suiteTest.log(Status.INFO, "Test suite started: " + context.getSuite().getName());
    }
    
    @Override
    public void onFinish(ITestContext context) {
        // Create a test for the suite completion
        ExtentTest suiteTest = extent.createTest("Test Suite: " + context.getSuite().getName());
        suiteTest.log(Status.INFO, "Test suite finished: " + context.getSuite().getName());
        flushReports();
    }
}

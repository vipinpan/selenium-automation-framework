package org.main.selenium.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";
    
    static {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE_PATH);
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public static int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
    
    public static int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }
    
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(properties.getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static long getLongProperty(String key) {
        return Long.parseLong(properties.getProperty(key));
    }
    
    public static long getLongProperty(String key, long defaultValue) {
        try {
            return Long.parseLong(properties.getProperty(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    public static String getApplicationURL() {
        return getProperty("app.url");
    }
    
    public static String getBrowser() {
        return getProperty("browser");
    }
    
    public static boolean isHeadless() {
        return getBooleanProperty("browser.headless", false);
    }
    
    public static int getImplicitWaitTimeout() {
        return getIntProperty("timeout.implicit.wait", 10);
    }
    
    public static int getExplicitWaitTimeout() {
        return getIntProperty("timeout.explicit.wait", 10);
    }
    
    public static int getPageLoadTimeout() {
        return getIntProperty("timeout.page.load", 30);
    }
    
    public static String getTestDataPath() {
        return getProperty("test.data.filepath");
    }
    
    public static String getReportDirectory() {
        return getProperty("report.directory");
    }
    
    public static String getScreenshotDirectory() {
        return getProperty("report.screenshot.directory");
    }
    
    public static String getLogLevel() {
        return getProperty("log.level", "INFO");
    }
    
    public static String getLogFilePath() {
        return getProperty("log.file.path");
    }
    
    public static String getLogFileName() {
        return getProperty("log.file.name", "automation.log");
    }
}

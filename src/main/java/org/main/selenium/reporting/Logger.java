package org.main.selenium.reporting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.main.selenium.utils.ConfigReader;

import java.io.File;

public class Logger {
    
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Logger.class);
    
    static {
        configureLogger();
    }
    
    private static void configureLogger() {
        try {
            String logLevel = ConfigReader.getLogLevel();
            String logPath = ConfigReader.getLogFilePath();
            String logFileName = ConfigReader.getLogFileName();
            
            File logDir = new File(logPath);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }
            
            System.setProperty("logFilePath", logPath);
            System.setProperty("logFileName", logFileName);
            
            Level level = Level.toLevel(logLevel, Level.INFO);
            Configurator.setRootLevel(level);
            
        } catch (Exception e) {
            System.err.println("Failed to configure logger: " + e.getMessage());
            Configurator.setRootLevel(Level.INFO);
        }
    }
    
    public static void info(String message) {
        logger.info(message);
    }
    
    public static void debug(String message) {
        logger.debug(message);
    }
    
    public static void warn(String message) {
        logger.warn(message);
    }
    
    public static void error(String message) {
        logger.error(message);
    }
    
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
    
    public static void fatal(String message) {
        logger.fatal(message);
    }
    
    public static void fatal(String message, Throwable throwable) {
        logger.fatal(message, throwable);
    }
    
    public static void trace(String message) {
        logger.trace(message);
    }
    
    public static void logStep(String step) {
        info("STEP: " + step);
    }
    
    public static void logTestStart(String testName) {
        info("TEST STARTED: " + testName);
    }
    
    public static void logTestEnd(String testName, String status) {
        info("TEST COMPLETED: " + testName + " - Status: " + status);
    }
    
    public static void logError(String testName, String error) {
        error("ERROR in " + testName + ": " + error);
    }
    
    public static void logInfo(String testName, String message) {
        info(testName + ": " + message);
    }
}

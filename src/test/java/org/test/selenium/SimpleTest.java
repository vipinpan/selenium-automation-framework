package org.test.selenium;

import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import org.testng.annotations.Optional;

public class SimpleTest {
    
    @Test
    @Parameters({"browser"})
    public void testMethod(@Optional("chrome") String browser) {
        System.out.println("Test executed successfully with browser: " + browser);
        System.out.println("Test compilation and basic setup working!");
    }
}

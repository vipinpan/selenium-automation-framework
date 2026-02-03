# 🚀 Selenium Automation Framework

A comprehensive Selenium-based automation framework with data-driven testing, reporting, logging, and CI/CD pipeline capabilities.

## 🌟 Features

- **🧪 Automated Testing**: Cross-browser Selenium tests with Page Object Model
- **📊 Advanced Reporting**: ExtentReports with screenshots and detailed logs
- **🔄 CI/CD Pipeline**: GitHub Actions with Docker integration
- **🐳 Container Support**: Docker Compose for consistent testing environments
- **🔒 Security Scanning**: Automated vulnerability scanning with Trivy
- **📱 Multi-Browser**: Chrome and Firefox support with headless options
- **📈 Performance Monitoring**: Test execution metrics and optimization

## Framework Structure

```
SeleniumProject/
├── src/
│   ├── main/
│   │   ├── java/org/main/selenium/
│   │   │   ├── base/
│   │   │   │   └── BaseTest.java          # Base class with common Selenium methods
│   │   │   ├── locators/
│   │   │   │   ├── FacebookLocators.java  # Facebook-specific locators
│   │   │   │   └── CommonLocators.java    # Common UI element locators
│   │   │   ├── reporting/
│   │   │   │   ├── ReportManager.java     # ExtentReports integration
│   │   │   │   └── Logger.java            # Log4j2 logging utility
│   │   │   ├── utils/
│   │   │   │   ├── ConfigReader.java      # Configuration file reader
│   │   │   │   └── JsonDataReader.java    # JSON test data reader
│   │   │   └── SeleniumPage.java          # Page object model
│   │   └── resources/
│   │       ├── config.properties          # Application configuration
│   │       └── log4j2.xml                 # Logging configuration
│   └── test/
│       ├── java/org/test/selenium/
│       │   └── SeleniumPageTest.java      # Test classes
│       └── resources/
│           └── testdata/
│               └── facebook_test_data.json # Test data in JSON format
├── .github/
│   └── workflows/
│       └── ci.yml                          # GitHub Actions CI/CD pipeline
├── docker-compose.yml                      # Docker services configuration
├── Dockerfile                             # Container build configuration
├── CI-CD-SETUP.md                         # Detailed CI/CD documentation
├── target/
│   ├── reports/                            # ExtentReports HTML reports
│   ├── screenshots/                        # Test failure screenshots
│   └── logs/                              # Log files
├── pom.xml                                # Maven dependencies
├── testng.xml                             # TestNG configuration
└── README.md                              # This file
```

## 🚀 Quick Start

### Prerequisites
- Java 11+ and Maven 3.6+
- Docker & Docker Compose (for containerized testing)
- Git

### Local Setup
```bash
# Clone the repository
git clone https://github.com/vipinpan/selenium-automation-framework.git
cd selenium-automation-framework

# Run tests locally
mvn clean test

# Run with Docker
docker-compose up -d
docker-compose exec test-runner mvn test
```

### CI/CD Pipeline
The project includes a complete CI/CD pipeline that:
- ✅ Runs tests on every push/PR
- ✅ Tests across Chrome/Firefox and Java 11/17
- ✅ Performs security scanning
- ✅ Generates detailed reports
- ✅ Deploys to staging on main branch

## 🔄 CI/CD Pipeline

### GitHub Actions Workflow
- **Triggers**: Push to main/develop, PRs, daily schedule
- **Matrix Testing**: 4 combinations (Chrome/Firefox × Java 11/17)
- **Security**: Trivy vulnerability scanning
- **Reporting**: Test artifacts, screenshots, PR comments

### Docker Integration
```bash
# Start Selenium Grid
docker-compose up -d selenium-hub chrome-node firefox-node

# Run tests on grid
mvn test -Dremote=true -Dgrid.url=http://localhost:4444/wd/hub

# View Allure reports
open http://localhost:5050
```

For detailed CI/CD setup, see [CI-CD-SETUP.md](CI-CD-SETUP.md).

## 🧪 Test Execution

### Local Testing
```bash
# Run all tests
mvn clean test

# Run specific test class
mvn test -Dtest=SauceDemoTest

# Run with specific browser
mvn test -Dbrowser=firefox

# Run with headless mode
mvn test -Dbrowser.headless=true
```

### SauceDemo Tests
The framework includes working SauceDemo tests:
```bash
# Run complete purchase flow
mvn test -Dtest=SauceDemoTest#testCompletePurchaseFlow

# Run all SauceDemo tests
mvn test -Dtest=SauceDemoTest
```

### ✅ Base Class Methods
- **Element Interactions**: Click, sendKeys, getText, getAttribute
- **Dropdown Handling**: Select by visible text, index, or value
- **Mouse Actions**: Hover, right-click, double-click, drag-and-drop
- **Wait Strategies**: Implicit, explicit, and custom waits
- **Frame/Window Handling**: Switch between frames and windows
- **Scrolling**: Scroll into view and scroll by coordinates
- **Screenshots**: Automatic screenshot capture on failures
- **Browser Support**: Chrome and Firefox with headless option

### ✅ Locator Management
- Centralized locator definitions in separate classes
- Facebook-specific locators for common elements
- Common locators for reusable UI elements
- Easy maintenance and updates

### ✅ Data Configuration
- **Properties File**: `config.properties` for application settings
- **JSON Test Data**: Structured test data for data-driven testing
- **Environment Configuration**: Support for multiple test environments
- **Flexible Data Reading**: Utility classes for easy data access

### ✅ Reporting & Logging
- **ExtentReports**: Beautiful HTML reports with screenshots
- **Log4j2**: Comprehensive logging with multiple appenders
- **TestNG Integration**: Automatic test execution reporting
- **Screenshot Capture**: On-demand and failure screenshots
- **Email Reports**: Optional email notification support

## Configuration

### Application Configuration (`config.properties`)
```properties
# Application Settings
app.url=https://facebook.com
app.name=Facebook Automation

# Browser Configuration
browser=chrome
browser.headless=false

# Timeout Configuration
timeout.implicit.wait=10
timeout.explicit.wait=10

# Reporting Configuration
report.directory=target/reports/
report.screenshot.directory=target/screenshots/
```

### Test Data (`facebook_test_data.json`)
```json
{
  "loginTests": [
    {
      "testName": "Valid Login Test",
      "username": "your-email@gmail.com",
      "password": "your-password",
      "expectedResult": "success"
    }
  ]
}
```

## Usage Examples

### Basic Test Class
```java
@Listeners(ReportManager.class)
public class SeleniumPageTest extends SeleniumPage {

    @Test
    @Parameters({"browser"})
    public void testValidLogin(String browser) {
        loginWithTestData("Valid Login Test");
    }

    @Test
    public void testAccountCreation() {
        navigateToFacebook();
        createNewAccount("John", "Doe", "john.doe@example.com", 
                        "Test@1234", "15", "Jan", "1990");
        selectGender("male");
        submitRegistration();
    }
}
```

### Using Base Class Methods
```java
// Element interactions
clickElement(FacebookLocators.LOGIN_BUTTON);
sendKeys(FacebookLocators.EMAIL_FIELD, "test@example.com");

// Dropdown selection
selectFromDropdown(FacebookLocators.BIRTHDAY_DAY, "15");

// Mouse actions
mouseHover(FacebookLocators.PROFILE_MENU);

// Waits and verification
waitForElementToBeVisible(FacebookLocators.ERROR_MESSAGE);
Assert.assertTrue(isElementDisplayed(FacebookLocators.LOGIN_BUTTON));
```

### Reading Test Data
```java
Map<String, Object> testData = JsonDataReader.getTestDataByTestName(
    "facebook_test_data.json", "loginTests", "Valid Login Test");

String username = JsonDataReader.getStringValue(testData, "username");
String password = JsonDataReader.getStringValue(testData, "password");
```

## Running Tests

### Using TestNG XML
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

### Running Specific Tests
```bash
mvn test -Dtest=SeleniumPageTest#testValidLogin
```

### Parallel Execution
Tests are configured to run in parallel across different browsers as defined in `testng.xml`.

## Dependencies

Key dependencies included in `pom.xml`:
- Selenium WebDriver 4.25.0
- TestNG 7.10.2
- ExtentReports 5.1.2
- Jackson Databind 2.17.0
- Log4j2 2.23.1
- WebDriverManager 5.8.0
- Apache Commons IO 2.16.1
- Apache POI 5.2.5

## Reports

After test execution:
1. **HTML Report**: Check `target/reports/TestReport_[timestamp].html`
2. **Screenshots**: Available in `target/screenshots/`
3. **Logs**: Check `target/logs/automation.log`

## Best Practices

1. **Use Locators**: Always use locator classes instead of hard-coding selectors
2. **Data-Driven Tests**: Store test data in JSON files for maintainability
3. **Error Handling**: Use try-catch blocks with proper logging and reporting
4. **Wait Strategies**: Use explicit waits instead of Thread.sleep()
5. **Screenshots**: Capture screenshots at key points and on failures
6. **Configuration**: Externalize configuration in properties files

## Extending the Framework

### Adding New Pages
1. Create new locator classes in `locators/` package
2. Add page methods in appropriate page classes
3. Update test data JSON files if needed

### Adding New Utilities
1. Create utility classes in `utils/` package
2. Follow existing naming conventions
3. Add proper logging and error handling

### Custom Reporting
1. Extend `ReportManager.java` for custom reporting needs
2. Add new report types in the logging configuration

## Troubleshooting

### Common Issues
1. **Driver Issues**: Ensure WebDriverManager is properly configured
2. **Timeout Issues**: Adjust timeout values in `config.properties`
3. **Locator Issues**: Verify locators are up-to-date with UI changes
4. **Data Issues**: Check JSON syntax and test data format

### Debug Mode
Set log level to DEBUG in `config.properties` for detailed logging:
```properties
log.level=DEBUG
```

## Contributing

1. Follow existing code structure and naming conventions
2. Add proper logging and error handling
3. Update documentation for new features
4. Test changes thoroughly before committing

# 🚀 CI/CD Pipeline Setup

This document explains the CI/CD pipeline setup for the Selenium Automation Framework.

## 📋 Table of Contents

- [Overview](#overview)
- [GitHub Actions Workflow](#github-actions-workflow)
- [Docker Configuration](#docker-configuration)
- [Local Development](#local-development)
- [Environment Variables](#environment-variables)
- [Security](#security)
- [Monitoring & Reporting](#monitoring--reporting)

## 🎯 Overview

The CI/CD pipeline provides:

- **Automated Testing** on every push and PR
- **Cross-browser Testing** (Chrome & Firefox)
- **Multi-Java Version Support** (Java 11 & 17)
- **Security Scanning** with Trivy
- **Test Reporting** with artifacts and screenshots
- **Docker-based Testing** for consistency
- **Scheduled Runs** for regression testing

## 🔧 GitHub Actions Workflow

### Triggers

```yaml
on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]
  schedule:
    - cron: '0 2 * * *'  # Daily at 2 AM UTC
```

### Jobs

#### 1. Test Job
- **Matrix Strategy**: Tests run on Chrome/Firefox with Java 11/17
- **Caching**: Maven dependencies cached for faster builds
- **Test Execution**: Runs full test suite
- **Artifact Upload**: Saves test results, reports, and screenshots
- **PR Comments**: Posts test results in pull requests

#### 2. Security Scan Job
- **Trivy Scanner**: Scans for vulnerabilities
- **SARIF Reports**: Uploads security findings to GitHub

#### 3. Build & Deploy Job
- **Build Application**: Creates deployment package
- **Release Creation**: Auto-creates releases for tags
- **Staging Deployment**: Deploys to staging environment

## 🐳 Docker Configuration

### Docker Compose Services

1. **Selenium Hub**: Central grid coordinator
2. **Chrome Node**: Chrome browser instances
3. **Firefox Node**: Firefox browser instances
4. **Test Runner**: Executes tests in container
5. **Allure Report**: Test reporting service

### Running Tests with Docker

```bash
# Start the complete grid
docker-compose up -d

# Run tests on the grid
docker-compose exec test-runner mvn test -Dbrowser=chrome -Dremote=true

# View logs
docker-compose logs -f test-runner

# Stop all services
docker-compose down
```

## 💻 Local Development

### Prerequisites

- Java 11+ installed
- Maven 3.6+
- Docker & Docker Compose
- Git

### Setup Commands

```bash
# Clone the repository
git clone https://github.com/vipinpan/selenium-automation-framework.git
cd selenium-automation-framework

# Install dependencies
mvn clean install

# Run tests locally
mvn test

# Run with specific browser
mvn test -Dbrowser=firefox

# Run with remote grid
mvn test -Dremote=true -Dgrid.url=http://localhost:4444/wd/hub
```

## 🔐 Environment Variables

### GitHub Secrets

Configure these in GitHub repository settings:

```bash
# Required for deployment
GITHUB_TOKEN=automatically_provided

# Optional: For private dependencies
MAVEN_USERNAME=your_maven_username
MAVEN_PASSWORD=your_maven_password

# Optional: For notification services
SLACK_WEBHOOK_URL=your_slack_webhook
TEAMS_WEBHOOK_URL=your_teams_webhook
```

### Application Properties

```properties
# CI/CD specific settings
ci.headless=true
ci.browser.timeout=30
ci.grid.url=http://selenium-hub:4444/wd/hub
ci.screenshot.on.failure=true
ci.video.recording=true
```

## 🔒 Security Features

### Automated Security Scanning

- **Trivy Scanner**: Container and dependency vulnerability scanning
- **SARIF Reports**: Security findings integrated with GitHub
- **Dependency Check**: Maven dependency vulnerability analysis

### Best Practices

- No hardcoded credentials in code
- Secrets managed through GitHub Secrets
- Minimal container permissions
- Regular security updates

## 📊 Monitoring & Reporting

### Test Reports

1. **JUnit Reports**: Standard test results
2. **ExtentReports**: Detailed HTML reports
3. **Allure Reports**: Interactive test reports
4. **Screenshots**: Failure screenshots
5. **Videos**: Test execution videos (optional)

### Artifacts

Each test run generates:

- `test-results-*.zip`: Complete test results
- `screenshots/`: Failure screenshots
- `reports/`: HTML reports
- `logs/`: Execution logs

### Notifications

- **Pull Request Comments**: Test results summary
- **GitHub Status Checks**: Pass/fail status
- **Slack/Teams**: Custom notifications (configurable)

## 🚀 Advanced Features

### Parallel Execution

```bash
# Run tests in parallel
mvn test -T 4

# Parallel with different browsers
mvn test -Dbrowser=chrome -Dparallel.tests=true
```

### Test Data Management

```bash
# Environment-specific test data
mvn test -Denv=staging

# Custom test data
mvn test -Dtest.data.file=custom-data.json
```

### Performance Testing

```bash
# Run with performance monitoring
mvn test -Dperformance.monitoring=true

# Generate performance reports
mvn test -D.generate.performance.reports=true
```

## 🛠️ Troubleshooting

### Common Issues

1. **Selenium Grid Connection**
   ```bash
   # Check grid status
   curl http://localhost:4444/wd/hub/status
   ```

2. **Docker Container Issues**
   ```bash
   # View container logs
   docker-compose logs selenium-hub
   ```

3. **Maven Build Failures**
   ```bash
   # Clean and rebuild
   mvn clean compile test-compile
   ```

### Debug Mode

```bash
# Run with debug logging
mvn test -Ddebug=true -X

# Enable browser debugging
mvn test -Dbrowser.debug=true
```

## 📈 Performance Optimization

### Build Optimization

- Maven dependency caching
- Docker layer caching
- Parallel test execution
- Incremental builds

### Test Optimization

- Smart test selection
- Test prioritization
- Failure fast strategies
- Resource cleanup

## 🔄 Continuous Improvement

### Metrics to Track

- Test execution time
- Success rate trends
- Flaky test identification
- Resource utilization

### Optimization Strategies

- Regular dependency updates
- Test refactoring
- Infrastructure scaling
- Monitoring alerts

## 📞 Support

For issues with the CI/CD pipeline:

1. Check GitHub Actions logs
2. Review Docker container status
3. Examine test artifacts
4. Consult troubleshooting section
5. Create an issue with detailed logs

---

**Note**: This CI/CD setup is designed to be modular and customizable. Feel free to modify the workflows, Docker configurations, and reporting mechanisms to suit your specific requirements.

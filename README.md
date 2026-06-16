# 🧪 Cucumber UI Automation Framework

> **A complete, production-ready BDD automation framework built with Java 21, Selenium 4, Cucumber 7, TestNG & Extent Reports**

---

## 📋 Table of Contents

1. [Project Overview](#1-project-overview)
2. [Tech Stack](#2-tech-stack)
3. [Framework Architecture](#3-framework-architecture)
4. [Project Structure](#4-project-structure)
5. [Prerequisites & Setup](#5-prerequisites--setup)
6. [Eclipse IDE Setup](#6-eclipse-ide-setup)
7. [Running Tests](#7-running-tests)
8. [Test Reports](#8-test-reports)
9. [Parallel Execution](#9-parallel-execution)
10. [Git & GitHub Integration](#10-git--github-integration)
11. [Jenkins CI/CD Integration](#11-jenkins-cicd-integration)
12. [Test Cases Covered](#12-test-cases-covered)
13. [Configuration Reference](#13-configuration-reference)
14. [Troubleshooting](#14-troubleshooting)

---

## 1. Project Overview

This framework provides complete end-to-end UI test automation using **Behavior Driven Development (BDD)** with Cucumber/Gherkin, Page Object Model (POM), and CI/CD integration.

**Test Website:** [The Internet Herokuapp](https://the-internet.herokuapp.com)  
A purpose-built, publicly available automation practice site — no authentication violations, fully open, always accessible.

### ✅ Key Features

| Feature | Description |
|---|---|
| **BDD with Gherkin** | Human-readable test scenarios using Given/When/Then |
| **Page Object Model** | Clean separation of locators and actions |
| **Parallel Execution** | Run tests simultaneously via TestNG DataProvider |
| **Cross-Browser** | Chrome, Firefox, Edge support |
| **Auto Driver Setup** | WebDriverManager handles driver downloads |
| **Extent Reports** | Rich HTML reports with screenshots |
| **TestNG Reports** | Native TestNG reports with pass/fail details |
| **Screenshot on Failure** | Auto-attached to both Cucumber and Extent reports |
| **Apache POI** | Excel-based test data management |
| **Jenkins CI/CD** | Parameterized pipeline with report publishing |
| **GitHub Actions** | Automatic CI on every push |
| **Java 21 Compatible** | Uses modern Java features (switch expressions, text blocks) |

---

## 2. Tech Stack

```
Java 21              → Programming Language
Selenium 4.21.0      → Browser Automation
Cucumber 7.18.0      → BDD Framework
TestNG 7.10.2        → Test Runner & Parallel Execution
WebDriverManager 5.8 → Auto Driver Management
Extent Reports 5.1.1 → Rich HTML Reporting
Apache POI 5.2.5     → Excel Test Data (Java 21 compatible)
Log4j2 2.23.1        → Logging Framework
Maven 3.9+           → Build & Dependency Management
Jenkins              → CI/CD Automation
Git + GitHub         → Source Control
```

---

## 3. Framework Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                   CUCUMBER UI FRAMEWORK                     │
├─────────────────────────────────────────────────────────────┤
│  Feature Files (.feature)   ←  Gherkin BDD Scenarios        │
│         ↓                                                   │
│  Step Definitions           ←  Java glue code               │
│         ↓                                                   │
│  Page Objects (POM)         ←  Locators + Actions           │
│         ↓                                                   │
│  BasePage                   ←  Shared Selenium methods       │
│         ↓                                                   │
│  DriverManager (ThreadLocal) ← Browser init (parallel safe) │
│         ↓                                                   │
│  WebDriver (Chrome/FF/Edge) ← Actual browser                │
├─────────────────────────────────────────────────────────────┤
│  Hooks (Before/After)       ←  Setup/Teardown + Screenshots │
│  ConfigReader               ←  config.properties reader     │
│  WaitUtils                  ←  Explicit/Fluent waits        │
│  ScreenshotUtils            ←  Screenshot capture           │
│  ExcelUtils (POI)           ←  Test data from Excel         │
│  TestNGListener             ←  Custom reporting listener    │
├─────────────────────────────────────────────────────────────┤
│  REPORTS                                                    │
│  ├── Extent Report (HTML)  → reports/extent/                │
│  ├── TestNG Report         → test-output/                   │
│  ├── Cucumber Report       → reports/cucumber/              │
│  └── Screenshots           → reports/screenshots/           │
└─────────────────────────────────────────────────────────────┘
```

---

## 4. Project Structure

```
CucumberUIFramework/
│
├── src/
│   └── test/
│       ├── java/
│       │   └── com/automation/
│       │       ├── config/
│       │       │   └── ConfigReader.java         # Reads config.properties
│       │       ├── drivers/
│       │       │   └── DriverManager.java        # ThreadLocal WebDriver
│       │       ├── pages/                        # Page Object Model classes
│       │       │   ├── BasePage.java             # Parent page with helpers
│       │       │   ├── HomePage.java
│       │       │   ├── LoginPage.java
│       │       │   ├── SecureAreaPage.java
│       │       │   ├── AlertsPage.java
│       │       │   ├── DropdownPage.java
│       │       │   ├── CheckboxPage.java
│       │       │   ├── FramesPage.java
│       │       │   ├── MultipleWindowsPage.java
│       │       │   ├── DragAndDropPage.java
│       │       │   ├── FileUploadPage.java
│       │       │   ├── WebTablePage.java
│       │       │   ├── MouseHoverPage.java
│       │       │   └── BrokenLinksPage.java
│       │       ├── stepdefinitions/              # Cucumber step implementations
│       │       │   ├── LoginSteps.java
│       │       │   ├── UIElementsSteps.java
│       │       │   ├── AlertsSteps.java
│       │       │   ├── FramesWindowsSteps.java
│       │       │   └── AdvancedInteractionsSteps.java
│       │       ├── hooks/
│       │       │   └── Hooks.java               # Before/After scenario hooks
│       │       ├── runners/
│       │       │   ├── TestRunner.java           # Main runner (Regression)
│       │       │   └── SmokeTestRunner.java      # Smoke-only runner
│       │       ├── listeners/
│       │       │   └── TestNGListener.java       # Custom TestNG listener
│       │       └── utils/
│       │           ├── WaitUtils.java            # Explicit/Fluent waits
│       │           ├── ScreenshotUtils.java      # Screenshot capture
│       │           ├── ExcelUtils.java           # Apache POI Excel reader
│       │           └── JavaScriptUtils.java      # JS helper methods
│       │
│       └── resources/
│           ├── features/
│           │   ├── login/
│           │   │   ├── Login.feature
│           │   │   └── Logout.feature
│           │   ├── ui_elements/
│           │   │   └── UIElements.feature
│           │   └── interactions/
│           │       ├── Alerts.feature
│           │       ├── FramesWindows.feature
│           │       └── AdvancedInteractions.feature
│           ├── config/
│           │   ├── config.properties             # Framework configuration
│           │   └── extent-config.xml             # Extent report styling
│           ├── extent.properties                 # Extent adapter settings
│           └── log4j2.xml                        # Logging configuration
│
├── reports/                                      # Generated at runtime
│   ├── extent/ExtentReport.html
│   ├── cucumber/cucumber-report.html
│   └── screenshots/
├── downloads/                                    # File download directory
├── pom.xml                                       # Maven dependencies
├── testng.xml                                    # TestNG suite (parallel)
├── testng-smoke.xml                              # TestNG smoke suite
├── Jenkinsfile                                   # Jenkins pipeline
├── .github/workflows/ci.yml                     # GitHub Actions CI
├── .gitignore
└── README.md
```

---

## 5. Prerequisites & Setup

### Required Software

| Software | Version | Download |
|---|---|---|
| Java JDK | 21 | [Adoptium](https://adoptium.net/temurin/releases/?version=21) |
| Maven | 3.9+ | [maven.apache.org](https://maven.apache.org/download.cgi) |
| Eclipse IDE | 2024-03+ | [eclipse.org](https://www.eclipse.org/downloads/) |
| Git | Latest | [git-scm.com](https://git-scm.com/) |
| Chrome Browser | Latest | [google.com/chrome](https://www.google.com/chrome/) |

### Verify Installation

Open Command Prompt / Terminal and run:
```bash
java -version        # Should show: openjdk 21...
mvn -version         # Should show: Apache Maven 3.9...
git --version        # Should show: git version 2...
```

---

## 6. Eclipse IDE Setup

### Step 1: Install Eclipse Plugins

1. Open Eclipse → **Help** → **Eclipse Marketplace**
2. Search and install:
   - **TestNG for Eclipse** (by Cedric Beust)
   - **Cucumber Eclipse Plugin** (by Cucumber)
   - **Maven Integration** (usually pre-installed)

### Step 2: Import Project into Eclipse

1. Open Eclipse
2. **File** → **Import** → **Maven** → **Existing Maven Projects**
3. Browse to your `CucumberUIFramework` folder
4. Click **Finish**
5. Wait for Maven to download dependencies (first run takes a few minutes)

### Step 3: Verify Maven Dependencies

1. Right-click `pom.xml` → **Maven** → **Update Project** (Alt+F5)
2. Check **Maven Dependencies** in Package Explorer
3. You should see Selenium, Cucumber, TestNG JARs

### Step 4: Configure Java 21 in Eclipse

1. **Window** → **Preferences** → **Java** → **Installed JREs**
2. Click **Add** → **Standard VM** → Browse to your JDK 21 folder
3. Click **Finish**, check the JDK 21 box

### Step 5: Set Compiler Level

1. **Window** → **Preferences** → **Java** → **Compiler**
2. Set **Compiler compliance level** to **21**

### Step 6: Run from Eclipse

**Method 1 - Right-click runner:**
1. Navigate to `src/test/java/com/automation/runners/TestRunner.java`
2. Right-click → **Run As** → **TestNG Test**

**Method 2 - Maven:**
1. Right-click `pom.xml` → **Run As** → **Maven test**

**Method 3 - Feature file:**
1. Right-click any `.feature` file → **Run As** → **Cucumber Feature**

---

## 7. Running Tests

### Maven Commands

```bash
# ── Run All Regression Tests ─────────────────────────────────────────────────
mvn test

# ── Run by Tag ────────────────────────────────────────────────────────────────
mvn test -Dtags="@Smoke"
mvn test -Dtags="@Login"
mvn test -Dtags="@Regression"
mvn test -Dtags="@Positive"
mvn test -Dtags="@Negative"
mvn test -Dtags="@Alerts"
mvn test -Dtags="@Dropdown"
mvn test -Dtags="@Checkbox"
mvn test -Dtags="@Frames"
mvn test -Dtags="@Windows"
mvn test -Dtags="@DragDrop"
mvn test -Dtags="@Hover"
mvn test -Dtags="@Upload"
mvn test -Dtags="@WebTable"
mvn test -Dtags="@BrokenLinks"
mvn test -Dtags="@Waits"

# ── Run with Specific Browser ─────────────────────────────────────────────────
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge

# ── Combine Tags and Browser ──────────────────────────────────────────────────
mvn test -Dtags="@Smoke" -Dbrowser=firefox
mvn test -Dtags="@Login and @Positive" -Dbrowser=chrome

# ── Run in Headless Mode ──────────────────────────────────────────────────────
mvn test -Dheadless=true -Dtags="@Regression"

# ── Run with Custom Thread Count (Parallel) ───────────────────────────────────
mvn test -DthreadCount=4

# ── Clean + Run ───────────────────────────────────────────────────────────────
mvn clean test

# ── Smoke Suite only ─────────────────────────────────────────────────────────
mvn test -Dsurefire.suiteXmlFiles=testng-smoke.xml

# ── Skip tests (build only) ───────────────────────────────────────────────────
mvn clean install -DskipTests
```

### Available Cucumber Tags

| Tag | Scenarios |
|---|---|
| `@Regression` | All scenarios |
| `@Smoke` | Critical path only |
| `@Login` | Login scenarios |
| `@Logout` | Logout scenarios |
| `@Alerts` | Alert handling |
| `@Dropdown` | Dropdown tests |
| `@Checkbox` | Checkbox tests |
| `@WebTable` | Table tests |
| `@Frames` or `@Iframe` | iframe tests |
| `@Windows` | Multiple windows |
| `@DragDrop` | Drag & drop |
| `@Hover` | Mouse hover |
| `@Upload` | File upload |
| `@BrokenLinks` | Broken images check |
| `@Waits` | Dynamic wait tests |
| `@Positive` | All positive test cases |
| `@Negative` | All negative test cases |
| `@Screenshot` | Screenshot tests |
| `@UIElements` | All UI element tests |
| `@Advanced` | All advanced interactions |

---

## 8. Test Reports

After running tests, reports are auto-generated:

### Extent Report (Rich HTML)
```
reports/extent/ExtentReport.html
```
- Open in browser
- Shows pass/fail pie chart
- Screenshots embedded on failure
- Scenario details with steps

### TestNG Report
```
test-output/index.html
```
- Open in browser via Eclipse: right-click → Open With → Web Browser
- Shows test summary, duration, failures

### Cucumber HTML Report
```
reports/cucumber/cucumber-report.html
```
- Feature-wise breakdown
- Step-by-step results

### Screenshots
```
reports/screenshots/
```
- Auto-captured on test failure
- Named: `TestName_yyyyMMdd_HHmmss.png`

---

## 9. Parallel Execution

The framework supports parallel scenario execution:

### Configuration in `testng.xml`
```xml
<suite name="Parallel Suite"
       parallel="methods"
       thread-count="2">
```

### How it works
- `TestRunner` uses `@DataProvider(parallel = true)`
- Each Cucumber scenario gets its own `WebDriver` instance via **ThreadLocal**
- `DriverManager.initDriver()` creates a new driver per thread
- Threads never share a driver instance — zero race conditions

### Increase Thread Count
```bash
# Via Maven property
mvn test -DthreadCount=4

# Or edit testng.xml
thread-count="4"
```

> ⚠️ **Note:** Set headless=true for more than 2 parallel threads to avoid resource issues.

---

## 10. Git & GitHub Integration

### Step 1: Initialize Git Locally (Command Prompt)

```bash
# Navigate to project folder
cd path/to/CucumberUIFramework

# Initialize Git
git init

# Add all files
git add .

# First commit
git commit -m "🎉 Initial commit - Complete Cucumber UI Framework"
```

### Step 2: Create GitHub Repository

1. Go to [github.com](https://github.com) → **New Repository**
2. Name: `CucumberUIFramework`
3. Visibility: Public or Private
4. **Do NOT** check "Initialize with README" (we have one)
5. Click **Create Repository**

### Step 3: Connect & Push to GitHub

```bash
# Add GitHub remote (replace YOUR_USERNAME)
git remote add origin https://github.com/YOUR_USERNAME/CucumberUIFramework.git

# Rename branch to main
git branch -M main

# Push to GitHub
git push -u origin main
```

### Step 4: Verify on GitHub

Visit `https://github.com/YOUR_USERNAME/CucumberUIFramework` — all files should appear.

### Common Git Commands

```bash
# Check status
git status

# Add specific file
git add src/test/resources/features/login/Login.feature

# Add all changes
git add .

# Commit with message
git commit -m "Add: new test cases for dropdown"

# Push to main
git push origin main

# Create feature branch
git checkout -b feature/add-cart-tests

# Push feature branch
git push origin feature/add-cart-tests

# Pull latest changes
git pull origin main

# View commit history
git log --oneline

# View branches
git branch -a
```

### Branching Strategy (Recommended)

```
main           → Production-ready code
develop        → Integration branch
feature/*      → New features (feature/login-tests)
bugfix/*       → Bug fixes (bugfix/fix-dropdown)
hotfix/*       → Urgent fixes
```

---

## 11. Jenkins CI/CD Integration

### Step 1: Install Jenkins

**Windows:**
1. Download from [jenkins.io/download](https://www.jenkins.io/download/)
2. Run the installer
3. Access: `http://localhost:8080`
4. Install suggested plugins

**Or use Docker:**
```bash
docker run -p 8080:8080 -p 50000:50000 jenkins/jenkins:lts-jdk21
```

### Step 2: Install Required Jenkins Plugins

Go to **Manage Jenkins** → **Manage Plugins** → **Available** and install:
- ✅ **Git Plugin**
- ✅ **GitHub Plugin**
- ✅ **HTML Publisher Plugin** (for Extent Report)
- ✅ **Maven Integration Plugin**
- ✅ **Pipeline Plugin** (usually pre-installed)
- ✅ **Credentials Binding Plugin**

### Step 3: Configure Java 21 & Maven in Jenkins

1. **Manage Jenkins** → **Global Tool Configuration**
2. Under **JDK** → **Add JDK**:
   - Name: `JDK21`
   - JAVA_HOME: path to your JDK 21 (e.g., `C:\Program Files\Eclipse Adoptium\jdk-21...`)
3. Under **Maven** → **Add Maven**:
   - Name: `Maven3`
   - Check "Install automatically" or give path

### Step 4: Add GitHub Credentials

1. **Manage Jenkins** → **Manage Credentials** → **Global** → **Add Credentials**
2. Kind: **Username with password**
3. Username: your GitHub username
4. Password: your GitHub **Personal Access Token** (not password!)
   - GitHub → Settings → Developer Settings → Personal Access Tokens → Generate new token
   - Scopes: `repo`, `workflow`
5. ID: `github-credentials`

### Step 5: Create Jenkins Pipeline Job

1. **New Item** → Enter name: `CucumberUIFramework`
2. Select **Pipeline** → **OK**
3. Under **Pipeline**:
   - Definition: **Pipeline script from SCM**
   - SCM: **Git**
   - Repository URL: `https://github.com/YOUR_USERNAME/CucumberUIFramework.git`
   - Credentials: Select `github-credentials`
   - Branch: `*/main`
   - Script Path: `Jenkinsfile`
4. Click **Save**

### Step 6: Run Jenkins Build

1. Click **Build with Parameters**
2. Select:
   - **BROWSER**: chrome
   - **TAGS**: @Smoke
   - **HEADLESS**: ✅ (recommended for CI)
   - **THREAD_COUNT**: 2
3. Click **Build**

### Step 7: View Reports in Jenkins

After build completes:
- Click build number → **📊 Extent Test Report**
- Click build number → **📋 TestNG Report**
- Click build number → **🥒 Cucumber Report**

### Trigger Build on GitHub Push

1. In Jenkins job → **Configure** → **Build Triggers**
2. Check **GitHub hook trigger for GITScm polling**
3. In GitHub → **Settings** → **Webhooks** → **Add webhook**:
   - URL: `http://YOUR_JENKINS_URL/github-webhook/`
   - Content type: `application/json`
   - Events: **Just the push event**

Now every `git push` to GitHub will trigger Jenkins automatically!

---

## 12. Test Cases Covered

### ✅ Login & Logout
| Test | Type | Tag |
|---|---|---|
| Login with valid credentials | ✅ Positive | @Smoke |
| Login with invalid username | ❌ Negative | @Negative |
| Login with invalid password | ❌ Negative | @Negative |
| Login with empty credentials | ❌ Negative | @Negative |
| Successful logout | ✅ Positive | @Smoke |
| Access secure area after logout | ❌ Negative | @Negative |

### ✅ Dropdowns
| Test | Type |
|---|---|
| Select Option 1 | ✅ Positive |
| Select Option 2 | ✅ Positive |
| Verify all options present | ✅ Positive |
| Verify default placeholder | ❌ Negative |

### ✅ Checkboxes
| Test | Type |
|---|---|
| Check first checkbox | ✅ Positive |
| Uncheck second checkbox | ✅ Positive |
| Check all checkboxes | ✅ Positive |
| Verify checkbox count | ✅ Positive |

### ✅ Alerts
| Test | Type |
|---|---|
| Accept JS Alert | ✅ Positive |
| Accept JS Confirm | ✅ Positive |
| Dismiss JS Confirm | ✅ Positive |
| Enter text in JS Prompt | ✅ Positive |
| Enter empty text in prompt | ❌ Negative |
| Dismiss JS Prompt | ❌ Negative |

### ✅ Frames
| Test | Type |
|---|---|
| Read content inside iFrame | ✅ Positive |
| Type in iFrame editor | ✅ Positive |
| Verify iframe loaded | ✅ Positive |

### ✅ Multiple Windows
| Test | Type |
|---|---|
| Open new window | ✅ Positive |
| Switch to new window | ✅ Positive |
| Close new window, return to main | ✅ Positive |
| Verify single window initially | ❌ Negative |

### ✅ Drag & Drop
| Test | Type |
|---|---|
| Drag A to B (JS) | ✅ Positive |
| Verify columns displayed | ✅ Positive |

### ✅ Mouse Hover
| Test | Type |
|---|---|
| Hover over figure 1 | ✅ Positive |
| Verify 3 figures present | ✅ Positive |

### ✅ File Upload
| Test | Type |
|---|---|
| Upload text file | ✅ Positive |
| Verify upload success | ✅ Positive |
| Verify page elements | ✅ Positive |

### ✅ Web Tables
| Test | Type |
|---|---|
| Table displayed | ✅ Positive |
| Row count ≥ 4 | ✅ Positive |
| Read specific cell | ✅ Positive |
| Verify column headers | ✅ Positive |

### ✅ Broken Links / Images
| Test | Type |
|---|---|
| Count broken images | ✅ Positive |
| Verify image count | ✅ Positive |

### ✅ Screenshots
| Test | Type |
|---|---|
| Capture homepage screenshot | ✅ Positive |

### ✅ Waits
| Test | Type |
|---|---|
| Dynamic loading with explicit wait | ✅ Positive |

---

## 13. Configuration Reference

### config.properties

```properties
base.url=https://the-internet.herokuapp.com
browser=chrome              # chrome | firefox | edge
headless=false              # true for CI/Jenkins
implicit.wait=10            # seconds
explicit.wait=20            # seconds
page.load.timeout=30        # seconds
screenshot.path=reports/screenshots/
download.dir=downloads/
```

### Override via Maven (for Jenkins)

```bash
mvn test -Dbrowser=firefox -Dheadless=true -Dtags="@Smoke"
```

---

## 14. Troubleshooting

### ❓ Driver not found / ChromeDriver error
**Fix:** WebDriverManager auto-downloads. If behind proxy:
```bash
mvn test -Dwdm.proxy=proxy.company.com:8080
```

### ❓ "No Step Definition found"
**Fix:** Ensure step definition package matches `glue` in `TestRunner.java`:
```java
glue = {"com.automation.stepdefinitions", "com.automation.hooks"}
```

### ❓ Port already in use (Jenkins)
**Fix:** Change Jenkins port:
```bash
# Windows: in jenkins.xml, change --httpPort=8080
# Or: java -jar jenkins.war --httpPort=9090
```

### ❓ Extent Report not generated
**Fix:** Check `extent.properties` exists in `src/test/resources/`:
```properties
extent.reporter.spark.start=true
extent.reporter.spark.out=reports/extent/ExtentReport.html
```

### ❓ Eclipse shows red underlines in .feature files
**Fix:** Install **Cucumber Eclipse Plugin** from Marketplace and restart Eclipse.

### ❓ Parallel tests failing with "driver not initialized"
**Fix:** Ensure `@Before` hook runs `DriverManager.initDriver()` before any page access.

### ❓ Tests pass locally but fail in Jenkins (headless)
**Fix:** Add `-Dheadless=true` in Jenkins and ensure Chrome is installed on server:
```bash
# Ubuntu/Jenkins server
sudo apt-get install -y google-chrome-stable
```

---

## 👤 Author

**Designed & Built by:** [Your Name]  
**Tech Stack:** Java 21 | Selenium 4 | Cucumber 7 | TestNG | Maven | Jenkins | Git

---

## 📄 License

This project is for learning and portfolio purposes.

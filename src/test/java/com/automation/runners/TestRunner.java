package com.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestRunner - Main Cucumber TestNG Runner
 *
 * To run:
 *   mvn test -Dbrowser=chrome -Dtags="@Regression"
 *   mvn test -Dtags="@Smoke"
 *   mvn test -Dtags="@Login"
 *
 * Parallel execution is enabled via @DataProvider(parallel=true)
 * Thread count is controlled in testng.xml
 */
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {
        "com.automation.stepdefinitions",
        "com.automation.hooks"
    },
    plugin = {
        "pretty",
        "html:reports/cucumber/cucumber-report.html",
        "json:reports/cucumber/cucumber-report.json",
        "junit:reports/cucumber/cucumber-report.xml",
        // Extent Reports adapter (reads extent.properties automatically)
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    tags = "@Regression",
    monochrome = true,
    publish = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Enable parallel execution of scenarios
     * Thread count is set in testng.xml <suite parallel="methods" thread-count="2">
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

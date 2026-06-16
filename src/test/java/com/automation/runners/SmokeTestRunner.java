package com.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * SmokeTestRunner - Run only @Smoke tagged scenarios
 * Use: mvn test -Dsurefire.suiteXmlFiles=testng-smoke.xml
 */
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {
        "com.automation.stepdefinitions",
        "com.automation.hooks"
    },
    plugin = {
        "pretty",
        "html:reports/cucumber/smoke-report.html",
        "json:reports/cucumber/smoke-report.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    tags = "@Smoke",
    monochrome = true
)
public class SmokeTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false) // Sequential for smoke
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

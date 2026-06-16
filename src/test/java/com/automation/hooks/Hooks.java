package com.automation.hooks;

import com.automation.config.ConfigReader;
import com.automation.drivers.DriverManager;
import com.automation.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hooks - Cucumber lifecycle management
 * - @Before: Initialize driver before each scenario
 * - @After: Take screenshot on failure, quit driver
 * - @AfterStep: Optional step-level logging
 */
public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        log.info("========================================");
        log.info("Starting Scenario: {}", scenario.getName());
        log.info("Tags: {}", scenario.getSourceTagNames());
        log.info("Thread: {}", Thread.currentThread().getName());

        // Initialize WebDriver (thread-safe via ThreadLocal)
        DriverManager.initDriver();

        log.info("Browser initialized. Navigating to base URL...");
        DriverManager.getDriver().get(ConfigReader.getInstance().getBaseUrl());
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        log.info("Scenario: {} | Status: {}", scenario.getName(), scenario.getStatus());

        // Capture screenshot on failure
        if (scenario.isFailed()) {
            log.warn("Scenario FAILED - capturing screenshot");
            try {
                byte[] screenshot = ScreenshotUtils.captureScreenshotAsBytes();
                if (screenshot != null && screenshot.length > 0) {
                    scenario.attach(screenshot, "image/png", "Failure Screenshot: " + scenario.getName());
                    log.info("Screenshot attached to scenario");
                }
            } catch (Exception e) {
                log.error("Failed to capture screenshot: {}", e.getMessage());
            }
        }

        // Quit driver
        DriverManager.quitDriver();
        log.info("Driver quit. Scenario complete.");
        log.info("========================================");
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        // Attach screenshot after EVERY step (optional - remove if reports are too large)
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ScreenshotUtils.captureScreenshotAsBytes();
                if (screenshot != null && screenshot.length > 0) {
                    scenario.attach(screenshot, "image/png", "Step Failure Screenshot");
                }
            } catch (Exception e) {
                log.warn("Could not capture step screenshot: {}", e.getMessage());
            }
        }
    }
}

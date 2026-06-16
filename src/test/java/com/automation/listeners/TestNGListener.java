package com.automation.listeners;

import com.automation.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNGListener - Custom TestNG listener for logging and screenshots
 * Registered in testng.xml <listeners> section
 */
public class TestNGListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestNGListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("▶ TEST STARTED: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✅ TEST PASSED: {} | Duration: {}ms",
            result.getName(),
            result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("❌ TEST FAILED: {} | Reason: {}",
            result.getName(),
            result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown");

        // Capture screenshot on failure
        try {
            String screenshotPath = ScreenshotUtils.captureScreenshot("FAILED_" + result.getName());
            log.info("Screenshot saved: {}", screenshotPath);
        } catch (Exception e) {
            log.warn("Could not capture screenshot: {}", e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⏭ TEST SKIPPED: {}", result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("========================================");
        log.info("SUITE STARTED: {}", context.getName());
        log.info("========================================");
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("========================================");
        log.info("SUITE FINISHED: {}", context.getName());
        log.info("  Passed:  {}", context.getPassedTests().size());
        log.info("  Failed:  {}", context.getFailedTests().size());
        log.info("  Skipped: {}", context.getSkippedTests().size());
        log.info("========================================");
    }
}

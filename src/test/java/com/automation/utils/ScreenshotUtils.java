package com.automation.utils;

import com.automation.config.ConfigReader;
import com.automation.drivers.DriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotUtils - Takes and saves screenshots for reporting
 */
public class ScreenshotUtils {

    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);

    /**
     * Capture screenshot and return file path
     */
    public static String captureScreenshot(String testName) {
        WebDriver driver = DriverManager.getDriver();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotDir = ConfigReader.getInstance().getScreenshotPath();
        String fileName = screenshotDir + testName + "_" + timestamp + ".png";

        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(fileName);
            FileUtils.copyFile(srcFile, destFile);
            log.info("Screenshot captured: {}", fileName);
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            log.error("Failed to capture screenshot: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Capture screenshot and return as Base64 string (for Extent Reports)
     */
    public static String captureScreenshotAsBase64() {
        try {
            WebDriver driver = DriverManager.getDriver();
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            log.error("Failed to capture base64 screenshot: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Capture screenshot as byte array (for TestNG reports)
     */
    public static byte[] captureScreenshotAsBytes() {
        try {
            WebDriver driver = DriverManager.getDriver();
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            log.error("Failed to capture screenshot bytes: {}", e.getMessage());
            return new byte[0];
        }
    }
}

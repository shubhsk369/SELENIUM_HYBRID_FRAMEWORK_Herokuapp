package com.automation.utils;

import com.automation.drivers.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * JavaScriptUtils - JS execution helpers
 */
public class JavaScriptUtils {

    private static final Logger log = LogManager.getLogger(JavaScriptUtils.class);

    public static void highlight(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }

    public static void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public static void click(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    public static Object executeScript(String script, Object... args) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        return js.executeScript(script, args);
    }

    public static void openNewTab() {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("window.open()");
    }

    public static Long getPageLoadTime() {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        return (Long) js.executeScript(
            "return window.performance.timing.loadEventEnd - window.performance.timing.navigationStart;"
        );
    }
}

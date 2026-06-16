package com.automation.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader - Singleton utility to read config.properties
 * Supports system property overrides for Jenkins/CI
 */
public class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;
    private static ConfigReader instance;
    private static final String CONFIG_PATH = "src/test/resources/config/config.properties";

    private ConfigReader() {
        loadProperties();
    }

    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    private void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties.load(fis);
            log.info("Configuration loaded from: {}", CONFIG_PATH);
        } catch (IOException e) {
            log.error("Failed to load config.properties: {}", e.getMessage());
            throw new RuntimeException("Cannot load configuration file: " + CONFIG_PATH, e);
        }
    }

    /**
     * Get property - System property takes precedence (for Jenkins override)
     */
    public String getProperty(String key) {
        // System property overrides file property (useful for Jenkins)
        String sysValue = System.getProperty(key);
        if (sysValue != null && !sysValue.isEmpty()) {
            return sysValue;
        }
        return properties.getProperty(key);
    }

    public String getBrowser() {
        return getProperty("browser") != null ? getProperty("browser") : "chrome";
    }

    public String getBaseUrl() {
        return getProperty("base.url");
    }

    public int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait") != null ? getProperty("implicit.wait") : "10");
    }

    public int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait") != null ? getProperty("explicit.wait") : "20");
    }

    public int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout") != null ? getProperty("page.load.timeout") : "30");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless") != null ? getProperty("headless") : "false");
    }

    public String getScreenshotPath() {
        return getProperty("screenshot.path") != null ? getProperty("screenshot.path") : "reports/screenshots/";
    }

    public String getDownloadDir() {
        return getProperty("download.dir") != null ? getProperty("download.dir") : "downloads/";
    }
}

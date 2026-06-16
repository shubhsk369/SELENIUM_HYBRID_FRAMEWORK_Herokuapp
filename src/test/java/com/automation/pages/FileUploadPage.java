package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * FileUploadPage - https://the-internet.herokuapp.com/upload
 */
public class FileUploadPage extends BasePage {

    @FindBy(id = "file-upload")
    private WebElement fileInput;

    @FindBy(id = "file-submit")
    private WebElement uploadButton;

    @FindBy(id = "uploaded-files")
    private WebElement uploadedFileName;

    @FindBy(css = "h3")
    private WebElement uploadResultHeading;

    private final By fileInputLocator  = By.id("file-upload");
    private final By submitLocator     = By.id("file-submit");
    private final By resultLocator     = By.id("uploaded-files");
    private final By headingLocator    = By.cssSelector("h3");

    public void uploadFile(String filePath) {
        log.info("Uploading file: {}", filePath);
        // Direct sendKeys to file input (no dialog needed)
        fileInput.sendKeys(new File(filePath).getAbsolutePath());
    }

    public void clickUploadButton() {
        log.info("Clicking Upload button");
        click(submitLocator);
    }

    public void uploadFileAndSubmit(String filePath) {
        uploadFile(filePath);
        clickUploadButton();
    }

    public String getUploadedFileName() {
        waitUtils.waitForVisible(resultLocator);
        return getText(resultLocator);
    }

    public boolean isUploadSuccessful() {
        try {
            waitUtils.waitForVisible(headingLocator);
            return getText(headingLocator).contains("File Uploaded");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Creates a temp file for upload testing
     */
    public static String createTestFile(String fileName, String content) {
        try {
            File tempFile = new File(System.getProperty("java.io.tmpdir"), fileName);
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(content);
            }
            return tempFile.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Could not create test file", e);
        }
    }
}

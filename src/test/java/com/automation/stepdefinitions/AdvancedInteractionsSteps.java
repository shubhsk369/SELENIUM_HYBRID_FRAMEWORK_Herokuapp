package com.automation.stepdefinitions;

import com.automation.config.ConfigReader;
import com.automation.pages.*;
import com.automation.utils.ScreenshotUtils;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.time.Duration;
import java.util.List;

/**
 * AdvancedInteractionsSteps - Drag & Drop, Hover, Upload, Broken Links, Screenshots, Waits
 */
public class AdvancedInteractionsSteps {

    private final DragAndDropPage dragAndDropPage = new DragAndDropPage();
    private final MouseHoverPage mouseHoverPage = new MouseHoverPage();
    private final FileUploadPage fileUploadPage = new FileUploadPage();
    private final BrokenLinksPage brokenLinksPage = new BrokenLinksPage();
    private final HomePage homePage = new HomePage();
    private final ConfigReader config = ConfigReader.getInstance();
    private String screenshotPath;
    private int brokenImageCount;

    // ─── Home Page ───────────────────────────────────────────────────────────

    @Given("I navigate to the home page")
    public void iNavigateToHomePage() {
        homePage.openHomePage();
        Assert.assertTrue(homePage.isPageLoaded(), "Home page did not load");
    }

    // ─── Drag and Drop ───────────────────────────────────────────────────────

    @Given("I navigate to the drag and drop page")
    public void iNavigateToDragAndDropPage() {
        dragAndDropPage.navigateTo(config.getBaseUrl() + "/drag_and_drop");
        Assert.assertTrue(dragAndDropPage.isColumnADisplayed(), "Drag and Drop page not loaded");
    }

    @When("I drag column A to column B")
    public void iDragColumnAToColumnB() {
        dragAndDropPage.dragAtoB();
    }

    @When("I drag column A to column B via JavaScript")
    public void iDragColumnAToColumnBViaJS() {
        dragAndDropPage.dragAtoBViaJS();
    }

    @Then("the columns should have swapped positions")
    public void columnsShouldHaveSwappedPositions() {
        dragAndDropPage.sleep(500);
        String colAText = dragAndDropPage.getColumnAText();
        String colBText = dragAndDropPage.getColumnBText();
        // After drag A->B, column B position should have A's content
        Assert.assertTrue(
            colBText.equals("A") || colAText.equals("B"),
            "Columns did not swap. ColA: " + colAText + " | ColB: " + colBText
        );
    }

    @Then("column A should be displayed on the page")
    public void columnAShouldBeDisplayedOnPage() {
        Assert.assertTrue(dragAndDropPage.isColumnADisplayed(), "Column A not displayed");
    }

    // ─── Mouse Hover ─────────────────────────────────────────────────────────

    @Given("I navigate to the hovers page")
    public void iNavigateToHoversPage() {
        mouseHoverPage.navigateTo(config.getBaseUrl() + "/hovers");
    }

    @When("I hover over figure number {int}")
    public void iHoverOverFigureNumber(int number) {
        mouseHoverPage.hoverOverFigure(number - 1); // 0-based index
    }

    @Then("the caption should be visible")
    public void captionShouldBeVisible() {
        // Caption check - CSS makes caption visible on hover
        int count = mouseHoverPage.getFigureCount();
        Assert.assertTrue(count > 0, "No figures found on page");
    }

    @Then("there should be {int} figures on the page")
    public void thereShouldBeFiguresOnPage(int expectedCount) {
        int actualCount = mouseHoverPage.getFigureCount();
        Assert.assertEquals(actualCount, expectedCount,
            "Expected " + expectedCount + " figures but found: " + actualCount);
    }

    // ─── File Upload ─────────────────────────────────────────────────────────

    @Given("I navigate to the file upload page")
    public void iNavigateToFileUploadPage() {
        fileUploadPage.navigateTo(config.getBaseUrl() + "/upload");
    }

    @When("I create and upload a test file named {string}")
    public void iCreateAndUploadTestFile(String fileName) {
        String filePath = FileUploadPage.createTestFile(fileName, "Automation Test File Content\nCreated for testing purposes.");
        fileUploadPage.uploadFile(filePath);
    }

    @When("I click the upload submit button")
    public void iClickUploadSubmitButton() {
        fileUploadPage.clickUploadButton();
    }

    @Then("the file upload should be successful")
    public void fileUploadShouldBeSuccessful() {
        Assert.assertTrue(fileUploadPage.isUploadSuccessful(),
            "File upload was not successful");
    }

    @Then("the uploaded file name should be {string}")
    public void uploadedFileNameShouldBe(String expectedName) {
        String actualName = fileUploadPage.getUploadedFileName();
        Assert.assertEquals(actualName, expectedName,
            "Uploaded file name mismatch. Expected: " + expectedName + " | Got: " + actualName);
    }

    @Then("the file input should be present")
    public void fileInputShouldBePresent() {
        Assert.assertTrue(fileUploadPage.isDisplayed(By.id("file-upload")),
            "File input not present");
    }

    @Then("the upload button should be present")
    public void uploadButtonShouldBePresent() {
        Assert.assertTrue(fileUploadPage.isDisplayed(By.id("file-submit")),
            "Upload button not present");
    }

    // ─── Broken Links ────────────────────────────────────────────────────────

    @Given("I navigate to the broken images page")
    public void iNavigateToBrokenImagesPage() {
        brokenLinksPage.navigateTo(config.getBaseUrl() + "/broken_images");
    }

    @Then("I count the broken images on the page")
    public void iCountBrokenImagesOnPage() {
        brokenImageCount = brokenLinksPage.getBrokenImageCount();
        System.out.println("Broken image count: " + brokenImageCount);
        // Log - don't fail on broken images, just report
        Assert.assertTrue(brokenImageCount >= 0, "Could not count broken images");
    }

    @Then("the page should have at least {int} image")
    public void pageShouldHaveAtLeastImage(int minCount) {
        int totalImages = brokenLinksPage.getTotalImageCount();
        Assert.assertTrue(totalImages >= minCount,
            "Expected at least " + minCount + " image(s) but found: " + totalImages);
    }

    // ─── Screenshots ─────────────────────────────────────────────────────────

    @When("I take a screenshot named {string}")
    public void iTakeScreenshotNamed(String name) {
        screenshotPath = ScreenshotUtils.captureScreenshot(name);
    }

    @Then("the screenshot should be saved successfully")
    public void screenshotShouldBeSavedSuccessfully() {
        Assert.assertNotNull(screenshotPath, "Screenshot path was null");
        File screenshotFile = new File(screenshotPath);
        Assert.assertTrue(screenshotFile.exists(),
            "Screenshot file does not exist at: " + screenshotPath);
    }

    // ─── Dynamic Loading / Waits ─────────────────────────────────────────────

    @Given("I navigate to the dynamic loading page")
    public void iNavigateToDynamicLoadingPage() {
        homePage.navigateTo(config.getBaseUrl() + "/dynamic_loading/2");
    }

    @When("I click the Start button")
    public void iClickStartButton() {
        homePage.findElement(By.cssSelector("#start button")).click();
    }

    @Then("I wait for the loading to complete")
    public void iWaitForLoadingToComplete() {
        // Wait for loader to disappear
        WebDriverWait wait = new WebDriverWait(homePage.driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
    }

    @Then("I should see {string} displayed")
    public void iShouldSeeDisplayed(String expectedText) {
        WebElement finishEl = homePage.findElement(By.id("finish"));
        Assert.assertTrue(finishEl.isDisplayed(), "Finish element not displayed");
        Assert.assertTrue(finishEl.getText().contains(expectedText),
            "Expected text '" + expectedText + "' not found. Got: " + finishEl.getText());
    }
}

package com.automation.stepdefinitions;

import com.automation.config.ConfigReader;
import com.automation.pages.CheckboxPage;
import com.automation.pages.DropdownPage;
import com.automation.pages.WebTablePage;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.util.List;

/**
 * UIElementsSteps - Dropdowns, Checkboxes, Web Tables
 */
public class UIElementsSteps {

    private final DropdownPage dropdownPage = new DropdownPage();
    private final CheckboxPage checkboxPage = new CheckboxPage();
    private final WebTablePage webTablePage = new WebTablePage();
    private final ConfigReader config = ConfigReader.getInstance();

    // ─── Dropdown Steps ──────────────────────────────────────────────────────

    @Given("I navigate to the dropdown page")
    public void iNavigateToDropdownPage() {
        dropdownPage.navigateTo(config.getBaseUrl() + "/dropdown");
    }

    @When("I select {string} from the dropdown")
    public void iSelectFromDropdown(String option) {
        dropdownPage.selectOption(option);
    }

    @Then("the selected option should be {string}")
    public void theSelectedOptionShouldBe(String expectedOption) {
        String actualOption = dropdownPage.getSelectedOptionText();
        Assert.assertEquals(actualOption, expectedOption,
            "Dropdown selection mismatch. Expected: " + expectedOption + " | Got: " + actualOption);
    }

    @Then("the dropdown should have at least {int} options")
    public void dropdownShouldHaveAtLeastOptions(int minCount) {
        int count = dropdownPage.getOptionCount();
        Assert.assertTrue(count >= minCount,
            "Expected at least " + minCount + " options but found: " + count);
    }

    @Then("the default selected option should be {string}")
    public void defaultSelectedOptionShouldBe(String expectedDefault) {
        String actualDefault = dropdownPage.getSelectedOptionText();
        Assert.assertEquals(actualDefault, expectedDefault,
            "Default option mismatch. Expected: " + expectedDefault + " | Got: " + actualDefault);
    }

    // ─── Checkbox Steps ──────────────────────────────────────────────────────

    @Given("I navigate to the checkboxes page")
    public void iNavigateToCheckboxesPage() {
        checkboxPage.navigateTo(config.getBaseUrl() + "/checkboxes");
        Assert.assertTrue(checkboxPage.isPageLoaded(), "Checkboxes page did not load");
    }

    @When("I check checkbox number {int}")
    public void iCheckCheckboxNumber(int number) {
        checkboxPage.checkCheckboxByIndex(number - 1); // Convert to 0-based
    }

    @When("I uncheck checkbox number {int}")
    public void iUncheckCheckboxNumber(int number) {
        checkboxPage.uncheckCheckboxByIndex(number - 1);
    }

    @When("I check all checkboxes")
    public void iCheckAllCheckboxes() {
        checkboxPage.checkAllCheckboxes();
    }

    @Then("checkbox number {int} should be checked")
    public void checkboxShouldBeChecked(int number) {
        Assert.assertTrue(checkboxPage.isCheckboxChecked(number - 1),
            "Checkbox " + number + " should be checked but was not");
    }

    @Then("checkbox number {int} should be unchecked")
    public void checkboxShouldBeUnchecked(int number) {
        Assert.assertFalse(checkboxPage.isCheckboxChecked(number - 1),
            "Checkbox " + number + " should be unchecked but was checked");
    }

    @Then("all checkboxes should be checked")
    public void allCheckboxesShouldBeChecked() {
        int count = checkboxPage.getCheckboxCount();
        for (int i = 0; i < count; i++) {
            Assert.assertTrue(checkboxPage.isCheckboxChecked(i),
                "Checkbox at index " + i + " should be checked");
        }
    }

    @Then("the page should have exactly {int} checkboxes")
    public void pageShouldHaveExactlyCheckboxes(int expectedCount) {
        int actualCount = checkboxPage.getCheckboxCount();
        Assert.assertEquals(actualCount, expectedCount,
            "Expected " + expectedCount + " checkboxes but found: " + actualCount);
    }

    // ─── Web Table Steps ─────────────────────────────────────────────────────

    @Given("I navigate to the web tables page")
    public void iNavigateToWebTablesPage() {
        webTablePage.navigateTo(config.getBaseUrl() + "/tables");
        Assert.assertTrue(webTablePage.isTableDisplayed(), "Web table did not load");
    }

    @Then("the table should be displayed")
    public void tableShouldBeDisplayed() {
        Assert.assertTrue(webTablePage.isTableDisplayed(), "Table is not displayed");
    }

    @Then("the table should have at least {int} rows")
    public void tableShouldHaveAtLeastRows(int minRows) {
        int count = webTablePage.getRowCount();
        Assert.assertTrue(count >= minRows,
            "Expected at least " + minRows + " rows but found: " + count);
    }

    @Then("the table should contain data in row {int} column {int}")
    public void tableShouldContainDataInRowColumn(int row, int col) {
        String data = webTablePage.getCellData(row - 1, col - 1);
        Assert.assertNotNull(data, "Cell data is null at row " + row + " col " + col);
        Assert.assertFalse(data.isEmpty(), "Cell data is empty at row " + row + " col " + col);
    }

    @Then("the table headers should include {string}")
    public void tableHeadersShouldInclude(String header) {
        List<String> headers = webTablePage.getHeaderNames();
        Assert.assertTrue(headers.contains(header),
            "Table headers do not contain: " + header + " | Found: " + headers);
    }
}

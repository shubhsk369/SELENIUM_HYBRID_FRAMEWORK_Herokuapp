# ============================================================
# Feature: UI Elements - Dropdowns, Checkboxes, Radio Buttons
# ============================================================

@Regression @UIElements
Feature: UI Elements Interactions
  As a QA engineer
  I want to test various UI elements
  So that I can verify interactive components work correctly

  # ──────────────────────────────────────────────────────────
  # DROPDOWN TESTS
  # ──────────────────────────────────────────────────────────

  @Dropdown @Positive
  Scenario: Select dropdown option by visible text - Option 1
    Given I navigate to the dropdown page
    When I select "Option 1" from the dropdown
    Then the selected option should be "Option 1"

  @Dropdown @Positive
  Scenario: Select dropdown option by visible text - Option 2
    Given I navigate to the dropdown page
    When I select "Option 2" from the dropdown
    Then the selected option should be "Option 2"

  @Dropdown @Positive
  Scenario: Verify all dropdown options are available
    Given I navigate to the dropdown page
    Then the dropdown should have at least 2 options

  @Dropdown @Negative
  Scenario: Verify placeholder option is not selectable as valid
    Given I navigate to the dropdown page
    Then the default selected option should be "Please select an option"

  # ──────────────────────────────────────────────────────────
  # CHECKBOX TESTS
  # ──────────────────────────────────────────────────────────

  @Checkbox @Positive
  Scenario: Check the first checkbox
    Given I navigate to the checkboxes page
    When I check checkbox number 1
    Then checkbox number 1 should be checked

  @Checkbox @Positive
  Scenario: Uncheck the second checkbox
    Given I navigate to the checkboxes page
    When I uncheck checkbox number 2
    Then checkbox number 2 should be unchecked

  @Checkbox @Positive
  Scenario: Check all checkboxes
    Given I navigate to the checkboxes page
    When I check all checkboxes
    Then all checkboxes should be checked

  @Checkbox @Negative
  Scenario: Verify checkbox state remains after re-navigation
    Given I navigate to the checkboxes page
    Then the page should have exactly 2 checkboxes

  # ──────────────────────────────────────────────────────────
  # WEB TABLE TESTS
  # ──────────────────────────────────────────────────────────

  @WebTable @Positive
  Scenario: Verify web table is displayed
    Given I navigate to the web tables page
    Then the table should be displayed
    And the table should have at least 4 rows

  @WebTable @Positive
  Scenario: Read specific cell data from the web table
    Given I navigate to the web tables page
    Then the table should contain data in row 1 column 1

  @WebTable @Positive
  Scenario: Verify table column headers
    Given I navigate to the web tables page
    Then the table headers should include "Last Name"
    And the table headers should include "First Name"
    And the table headers should include "Email"

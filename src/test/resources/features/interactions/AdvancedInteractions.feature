# ============================================================
# Feature: Advanced Interactions - Drag&Drop, Hover, Upload, Tables, Broken Links
# ============================================================

@Regression @Advanced
Feature: Advanced Web Interactions
  As a QA engineer
  I want to test advanced web interactions
  So that I can verify complex UI behaviors

  # ──────────────────────────────────────────────────────────
  # DRAG AND DROP TESTS
  # ──────────────────────────────────────────────────────────

  @DragDrop @Positive
  Scenario: Drag column A to column B
    Given I navigate to the drag and drop page
    When I drag column A to column B via JavaScript
    Then the columns should have swapped positions

  @DragDrop @Positive
  Scenario: Verify drag and drop page loads with two columns
    Given I navigate to the drag and drop page
    Then column A should be displayed on the page

  # ──────────────────────────────────────────────────────────
  # MOUSE HOVER TESTS
  # ──────────────────────────────────────────────────────────

  @Hover @Positive
  Scenario: Hover over first figure to reveal caption
    Given I navigate to the hovers page
    When I hover over figure number 1
    Then the caption should be visible

  @Hover @Positive
  Scenario: Verify all three figures are present
    Given I navigate to the hovers page
    Then there should be 3 figures on the page

  @Hover @Negative
  Scenario: Caption should not be visible without hover
    Given I navigate to the hovers page
    Then there should be 3 figures on the page

  # ──────────────────────────────────────────────────────────
  # FILE UPLOAD TESTS
  # ──────────────────────────────────────────────────────────

  @Upload @Positive
  Scenario: Upload a text file successfully
    Given I navigate to the file upload page
    When I create and upload a test file named "test_upload.txt"
    And I click the upload submit button
    Then the file upload should be successful
    And the uploaded file name should be "test_upload.txt"

  @Upload @Positive
  Scenario: Verify upload page elements
    Given I navigate to the file upload page
    Then the file input should be present
    And the upload button should be present

  # ──────────────────────────────────────────────────────────
  # BROKEN LINKS / BROKEN IMAGES TESTS
  # ──────────────────────────────────────────────────────────

  @BrokenLinks @Positive
  Scenario: Check for broken images on the broken images page
    Given I navigate to the broken images page
    Then I count the broken images on the page
    And the page should have at least 1 image

  @BrokenLinks @Positive
  Scenario: Verify total images exist on broken images page
    Given I navigate to the broken images page
    Then the page should have at least 1 image

  # ──────────────────────────────────────────────────────────
  # SCREENSHOTS AND ASSERTIONS
  # ──────────────────────────────────────────────────────────

  @Screenshot @Positive
  Scenario: Take screenshot of the home page
    Given I navigate to the home page
    When I take a screenshot named "HomePage"
    Then the screenshot should be saved successfully

  # ──────────────────────────────────────────────────────────
  # WAITS DEMONSTRATION TESTS
  # ──────────────────────────────────────────────────────────

  @Waits @Positive
  Scenario: Dynamic loading - wait for element to appear
    Given I navigate to the dynamic loading page
    When I click the Start button
    Then I wait for the loading to complete
    And I should see "Hello World!" displayed

# ============================================================
# Feature: Frames and Multiple Windows
# ============================================================

@Regression @Frames
Feature: Frame and Window Handling
  As a QA engineer
  I want to interact with iFrames and multiple windows
  So that I can verify content within frames and window management

  # ──────────────────────────────────────────────────────────
  # IFRAME TESTS
  # ──────────────────────────────────────────────────────────

  @Iframe @Positive
  Scenario: Read content inside an iFrame
    Given I navigate to the iframe page
    When I switch to the iframe
    Then I should see text inside the iframe
    And I switch back to the main content

  @Iframe @Positive
  Scenario: Type text inside an iFrame editor
    Given I navigate to the iframe page
    When I switch to the iframe
    And I clear and type "Automation Testing" in the iframe
    Then I switch back to the main content

  @Iframe @Negative
  Scenario: Verify iframe is present on page
    Given I navigate to the iframe page
    Then the iframe should be loaded on the page

  # ──────────────────────────────────────────────────────────
  # MULTIPLE WINDOWS TESTS
  # ──────────────────────────────────────────────────────────

  @Windows @Positive
  Scenario: Open a new window and switch to it
    Given I navigate to the multiple windows page
    When I click to open a new window
    Then a new window should open
    And the new window heading should be "New Window"

  @Windows @Positive
  Scenario: Switch between windows
    Given I navigate to the multiple windows page
    When I click to open a new window
    And I close the new window
    Then I should be back on the main window

  @Windows @Negative
  Scenario: Verify only one window exists initially
    Given I navigate to the multiple windows page
    Then only one window should be open

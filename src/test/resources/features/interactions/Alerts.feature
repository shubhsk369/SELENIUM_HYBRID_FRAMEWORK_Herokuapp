# ============================================================
# Feature: JavaScript Alerts
# URL: https://the-internet.herokuapp.com/javascript_alerts
# ============================================================

@Regression @Alerts
Feature: JavaScript Alert Handling
  As a QA engineer
  I want to handle various types of JavaScript alerts
  So that I can verify alert behavior and dismiss them properly

  Background:
    Given I navigate to the alerts page

  # ──────────────────────────────────────────────────────────
  # POSITIVE SCENARIOS
  # ──────────────────────────────────────────────────────────

  @Positive
  Scenario: Accept a JavaScript alert
    When I click the JS Alert button
    And I accept the alert
    Then the result text should contain "You successfully clicked an alert"

  @Positive
  Scenario: Accept a JavaScript confirm dialog
    When I click the JS Confirm button
    And I accept the alert
    Then the result text should contain "You clicked: Ok"

  @Positive
  Scenario: Dismiss a JavaScript confirm dialog
    When I click the JS Confirm button
    And I dismiss the alert
    Then the result text should contain "You clicked: Cancel"

  @Positive
  Scenario: Enter text in a JavaScript prompt
    When I click the JS Prompt button
    And I enter "Hello Automation!" in the prompt
    Then the result text should contain "You entered: Hello Automation!"

  @Positive
  Scenario: Get text from a JavaScript alert before accepting
    When I click the JS Alert button
    Then the alert message should be "I am a JS Alert"
    When I accept the alert
    Then the result text should contain "You successfully clicked an alert"

  # ──────────────────────────────────────────────────────────
  # NEGATIVE SCENARIOS
  # ──────────────────────────────────────────────────────────

  @Negative
  Scenario: Enter empty text in JavaScript prompt
    When I click the JS Prompt button
    And I enter "" in the prompt
    Then the result text should contain "You entered:"

  @Negative
  Scenario: Dismiss JavaScript prompt
    When I click the JS Prompt button
    And I dismiss the alert
    Then the result text should contain "You entered: null"

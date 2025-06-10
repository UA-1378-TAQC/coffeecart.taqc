Feature: Verification of the functioning oh the "Total" button functionality on "Menu" and "Cart" pages

  Background:
    Given Coffee Cart Menu page is opened

  @issue-13 @owner-viktoriia-fylyk
  Scenario: Test "Total" button on Menu and Cart pages
    When I click on Espresso
    And I hover over the "Total" button
    Then the "Total" button border and text color should be "golden"
    And the pop-up cart should appear

    When I move the cursor away from the "Total" button
    Then the "Total" button border and text color should be "black"
    And the pop-up cart should disappear

    When I click on the "Total" button
    Then the payment modal should appear

    When I click on the close button of the payment modal
    And I click on the "cart" link in the header

    When I hover over the "Total" button
    Then the "Total" button border and text color should be "golden"

    When I move the cursor away from the "Total" button
    Then the "Total" button border and text color should be "black"

    When I click on the "Total" button
    Then the payment modal should appear

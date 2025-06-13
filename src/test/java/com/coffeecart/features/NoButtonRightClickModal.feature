Feature: Add-to-cart modal behaviour

  @issue-14 @owner-roman-fedko
  Scenario: “No” button closes the modal and leaves the cart empty
    Given I open the menu page
    When  I right-click the "Espresso Macchiato" card
    Then  the add-to-cart modal appears
    And   the modal contains 'Yes' and 'No' buttons
    When  I hover over the 'No' button
    Then  the border colour of the 'No' button changes to "#daa520"
    When  I click the 'No' button
    Then  the cart item counter equals 0

  @issue-14 @owner-roman-fedko
  Scenario: Add-to-cart modal reappears after page refresh
    Given I open the menu page
    When  I right-click the "Americano" card
    And   I refresh the page
    When  I right-click the "Americano" card again
    Then  the add-to-cart modal appears
    And   the modal contains 'Yes' and 'No' buttons
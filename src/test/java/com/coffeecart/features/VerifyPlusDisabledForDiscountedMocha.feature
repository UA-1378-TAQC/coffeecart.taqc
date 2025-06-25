Feature: '+' button is disabled for '(Discounted) Mocha' in the Cart Page

  As a user
  I want the '+' button to be disabled for (Discounted) Mocha in the Cart Page
  So that I cannot increase its quantity or total price by clicking '+'

  Background:
    Given I am on the menu page

  @issue-19 @owner-RomanKmet
  Scenario: Verify '+' button is disabled for (Discounted) Mocha in Cart
    When I click on any coffee cup
    And I click on any coffee cup
    And I click on any coffee cup
    Then I verify the cart counter in header is "3"
    And I verify Lucky Day modal appears

    When I click "Yes, of course!" in Lucky Day modal
    Then I verify the cart counter in header is "4"
    And I verify Lucky Day modal disappears

    When I go to the cart page
    Then I should see (Discounted) Mocha and three other coffee cups in the cart

    And I verify the quantity of (Discounted) Mocha is "1"
    And I verify the price of (Discounted) Mocha is "$4.00"

    And I verify the plus button for (Discounted) Mocha is disabled

    When I try to click the plus button for (Discounted) Mocha
    Then I verify the plus button remains disabled
    And I verify the quantity of (Discounted) Mocha is still "1"
    And I verify the total sum in the cart does not increase
    And I verify the cart counter in header does not increase
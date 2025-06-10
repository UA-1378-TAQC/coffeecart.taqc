Feature: Increasing Items Number in Cart
  As a user
  I want to increase the quantity of an item in my cart
  So that I can easily adjust the number of items I want to purchase

  Background:
    Given I am on menu page

  @issue-16 @owner-dmytro-zubenko @regression
  Scenario: Verify increasing item quantity on Cart page
    When I click "Espresso Macchiato"
    Then I verify "Espresso Macchiato" is in the cart
    When I click the add button for "Espresso Macchiato"
    Then I verify the item count is "2"
    And I verify the unit description is "Total: $24.00"
    And I verify the cart count in the header is "2"

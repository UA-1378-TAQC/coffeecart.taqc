Feature: Pop-up Cart Behavior

  As a user
  I want to interact with the cart pop-up
  So that I can modify drink quantities and see correct total prices
  @issue-14 @owner-Khrystyna-Martynova
  Scenario Outline: Verify cart pop-up functionality for a drink from the menu
    Given I am on the menu page
    When I click on the drink "<drinkName>"
    Then the cart pop-up should display the item "<drinkName>" with quantity 1
    When I increase the quantity of "<drinkName>" by clicking "+"
    Then the cart should display quantity 2 for "<drinkName>"
    When I decrease the quantity of "<drinkName>" by clicking "-"
    Then the cart should display quantity 1 for "<drinkName>"
    When I decrease the quantity of "<drinkName>" by clicking "-" again
    Then the total price in the cart should be "Total: $0.00"

    Examples:
      | drinkName     |
      | Espresso      |
      | Latte         |
      | Cappuccino    |

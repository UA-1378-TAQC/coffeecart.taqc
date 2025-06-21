Feature: Verify drink name translation to Chinese on double-click

  As a user of coffee-cart.app
  I want to see coffee drink names translated to Chinese when I double click them

  Background:
    Given I am on coffee menu page

  @issue-7 @owner-RomanKmet
  Scenario: [TC7] Drink name translates to Chinese on double-click

    When I double click on coffee item "Espresso"
    Then the drink title should be translated into Chinese "特浓咖啡"

    When I double click on coffee item "Espresso Macchiato"
    Then the drink title should be translated into Chinese "浓缩玛奇朵"

    When I double click on coffee item "Cappuccino"
    Then the drink title should be translated into Chinese "卡布奇诺"

    When I double click on coffee item "Mocha"
    Then the drink title should be translated into Chinese "摩卡"

    When I double click on coffee item "Flat White"
    Then the drink title should be translated into Chinese "平白咖啡"

    When I double click on coffee item "Americano"
    Then the drink title should be translated into Chinese "美式咖啡"

    When I double click on coffee item "Cafe Latte"
    Then the drink title should be translated into Chinese "拿铁"

    When I double click on coffee item "Espresso Con Panna"
    Then the drink title should be translated into Chinese "浓缩康宝蓝"

    When I double click on coffee item "Cafe Breve"
    Then the drink title should be translated into Chinese "半拿铁"
Feature: Drink name translation to Chinese on double click

  As a user
  I want to see translated drink names in Chinese after double-clicking on their names

  Background:
    Given I am on the menu page

  @issue-7 @owner-RomanKmet
  Scenario Outline: Double-click translates drink name to Chinese
    When I double click drink "<drink>"
    Then the drink name should change to "<chinese>"

    Examples:
      | drink                  | chinese      |
      | Espresso               | 特浓咖啡       |
      | Espresso Macchiato     | 浓缩玛奇朵     |
      | Cappuccino             | 卡布奇诺       |
      | Mocha                  | 摩卡          |
      | Flat White             | 平白咖啡       |
      | Americano              | 美式咖啡       |
      | Cafe Latte             | 拿铁          |
      | Espresso Con Panna     | 浓缩康宝蓝     |
      | Cafe Breve             | 半拿铁        |
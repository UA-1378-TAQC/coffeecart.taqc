Feature: Invalid data is entered in the Name field
    Verify that the payment form correctly handles invalid name inputs with appropriate error messages.

    Background:
        Given I am on the coffee cart menu page

    @issue-9 @owner-dmytro-slobodianiuk
    Scenario: Verify error handling when invalid data is entered in the Name field
        When I select an Espresso drink
        And I proceed to the payment modal
        Then I verify emptiness of 'Name' and 'Password' fields:
            | Name      |  |  #тут йде перевірка чи поверне метод null, тому в колонці значення нічого немає
            | Email     |  |
        When I enter valid email
        Then I check that payment modal is visible
        And I check that validation message appear

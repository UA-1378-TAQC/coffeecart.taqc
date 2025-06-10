# Feature: Payment Form Validation
#   As a user
#   I want to submit payment form with valid data
#   So that I can complete my purchase successfully

#   Background:
#     Given I am on the coffee cart menu page

#   @issue-3 @owner-nataliia-hrusha
#   Scenario: Verify valid name/email submission shows confirmation message
#     When I select an Espresso drink
#     And I proceed to the payment modal
#     Then I verify all payment modal texts match expected:
#       | Field            | Expected Text                          |
#       | Header          | Payment Details                        |
#       | Paragraph       | Please enter your payment details       |
#       | Name Label      | Name                                    |
#       | Email Label     | Email                                   |
#       | Checkbox Label  | I agree to the terms and conditions     |
#       | Submit Button   | Submit                                  |
#     And I verify checkbox is unchecked by default
#     And I verify payment modal has correct background color
#     When I enter valid name and email
#     And I submit the payment form
#     Then I verify successful popup is displayed
#     And I verify success message is "Thanks for your purchase. Please check your email for payment."

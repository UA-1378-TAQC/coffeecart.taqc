package com.coffeecart.cucumber.steps;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.ui.modal.PaymentDetailModal;
import com.coffeecart.ui.modal.SuccessfulPopUp;
import com.coffeecart.ui.page.MenuPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class PaymentModalSteps {
    private final Hooks hooks;
    private MenuPage menuPage;
    private PaymentDetailModal paymentModal;
    private SuccessfulPopUp successPopup;
    private SoftAssert softAssert;

    public PaymentModalSteps(Hooks hooks) {
        this.hooks = hooks;
    }

    @Given("I am on the coffee cart menu page")
    public void iAmOnTheCoffeeCartMenuPage() {
        menuPage = new MenuPage(hooks.getDriver());
        softAssert = hooks.getSoftAssert();
    }

    @When("I select an Espresso drink")
    public void iSelectAnEspressoDrink() {
        menuPage.clickDrink(DrinkEnum.ESPRESSO.getRecipe().getName());
    }

    @When("I proceed to the payment modal")
    public void iProceedToThePaymentModal() {
        paymentModal = menuPage.clickTotalButton();
    }

    @Then("I verify all payment modal texts match expected:")
    public void iVerifyAllPaymentModalTextsMatchExpected(Map<String, String> expectedTexts) {
        softAssert.assertEquals(paymentModal.getHeaderText(), expectedTexts.get("Header"), "Header text mismatch");
        softAssert.assertEquals(paymentModal.getParagraphText(), expectedTexts.get("Paragraph"), "Paragraph text mismatch");
        softAssert.assertEquals(paymentModal.getLabelNameText(), expectedTexts.get("Name Label"), "Name label text mismatch");
        softAssert.assertEquals(paymentModal.getLabelEmailText(), expectedTexts.get("Email Label"), "Email label text mismatch");
        softAssert.assertEquals(paymentModal.getLabelCheckboxText(), expectedTexts.get("Checkbox Label"), "Checkbox label text mismatch");
        softAssert.assertEquals(paymentModal.getSubmitButtonText(), expectedTexts.get("Submit Button"), "Submit button text mismatch");
    }

    @And("I verify checkbox is unchecked by default")
    public void iVerifyCheckboxIsUncheckedByDefault() {
        softAssert.assertFalse(paymentModal.isCheckboxMarked(), "Checkbox should be unchecked by default");
    }

    @And("I verify payment modal has correct background color")
    public void iVerifyPaymentModalHasCorrectBackgroundColor() {
        boolean isMatch = menuPage.isBackgroundColorOfPaymentDetailModalMatch();
        softAssert.assertTrue(isMatch, "Payment modal should have correct background color");
    }

    @When("I enter valid name and email")
    public void iEnterValidNameAndEmail() {
        paymentModal.enterName(hooks.getTestValueProvider().getUserName())
                .enterEmail(hooks.getTestValueProvider().getUserEmail());
    }

    @And("I submit the payment form")
    public void iSubmitThePaymentForm() {
        successPopup = paymentModal.clickSubmitButtonWithValidInput();
    }

    @Then("I verify successful popup is displayed")
    public void iVerifySuccessfulPopupIsDisplayed() {
        softAssert.assertTrue(successPopup.isDisplayed(), "Successful popup should be visible");
    }

    @And("I verify success message is {string}")
    public void iVerifySuccessMessageIs(String expectedMessage) {
        softAssert.assertEquals(successPopup.getSuccessTitleText(), expectedMessage, "Success message text mismatch");
    }

    @Then("I verify emptiness of 'Name' and 'Password' fields:")
    public void iVerifyEmptyFields(Map<String, String> expectedTexts){
        softAssert.assertEquals(paymentModal.getInputNameValue(), expectedTexts.get("Name"), "Name field is filled");
        softAssert.assertEquals(paymentModal.getInputEmailValue(), expectedTexts.get("Email"), "Email field is filled");
    }

    @When("I enter valid email")
    public void iEnterValidEmail(){
        paymentModal.enterEmail(hooks.getTestValueProvider().getUserEmail());
    }

    @Then("I check that validation message appear")
    public void iCheckInvalidName(){
        softAssert.assertEquals(paymentModal.getNameValidationMessage(), "Please fill out this field.");
    }

    @Then("I check that payment modal is visible")
    public void paymentModalIsVisible(){
        softAssert.assertTrue(paymentModal.isModalVisible(), "Modal should still be visible after invalid input");
    }
}

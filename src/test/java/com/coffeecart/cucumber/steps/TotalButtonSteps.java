package com.coffeecart.cucumber.steps;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.ui.data.Colors;
import com.coffeecart.ui.elements.TotalButtonElement;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.modal.PaymentDetailModal;
import com.coffeecart.ui.page.CartPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;


public class TotalButtonSteps {
    private final Hooks hooks;
    private MenuPage menuPage;
    private PaymentDetailModal paymentModal;
    private CartPage cartPage;

    private TotalButtonElement totalButtonElement;

    private SoftAssert softAssert;

    public TotalButtonSteps(Hooks hooks) {
        this.hooks = hooks;
    }

    @Given("Coffee Cart Menu page is opened")
    public void CoffeeCartMenuPageIsOpened() {
        menuPage = new MenuPage(hooks.getDriver());
        softAssert = hooks.getSoftAssert();
        totalButtonElement = menuPage.getButtonElement();

    }

    @When("I click on Espresso")
    public void clickOnCoffeeCup() {
        menuPage.clickDrink(DrinkEnum.ESPRESSO.getRecipe().getName());
    }

    @When("I hover over the \"Total\" button")
    public void hoverOverTotalButton() {
        totalButtonElement.hover();
    }

    @Then("the \"Total\" button border and text color should be {string}")
    public void verifyTotalButtonColorOnMenuPage(String expectedColorName) {
        String actualColor = totalButtonElement.getTotalButton().getCssValue("color");
        Colors expectedColor = Colors.valueOf(expectedColorName.toUpperCase());
        String expectedColorValue = expectedColor.getColor();
        softAssert.assertEquals(actualColor, expectedColorValue,
                String.format("Expected color: %s but got: %s", expectedColorValue, actualColor));
    }


    @Then("the pop-up cart should appear")
    public void verifyCartAppears() {
        softAssert.assertTrue(totalButtonElement.getCartComponentRoot().isDisplayed(),
                "Pop-up cart should be visible");
    }

    @When("I move the cursor away from the \"Total\" button")
    public void moveCursorAwayFromTotal() {
        totalButtonElement.unhover();
    }

    @And("the pop-up cart should disappear")
    public void verifyCartDisappears() {
        softAssert.assertFalse(
                totalButtonElement.getCartComponentRoot().isDisplayed(),
                "Pop-up cart should be hidden"
        );
    }

    @When("I click on the \"Total\" button")
    public void clickTotalButton() {
        paymentModal = totalButtonElement.clickTotalButton();
    }

    @Then("the payment modal should appear")
    public void verifyPaymentModalAppears() {
        softAssert.assertTrue(paymentModal.getPaymentModal().isDisplayed(), "Payment modal should appear");
    }

    @When("I click on the close button of the payment modal")
    public void closePaymentModal() {
        paymentModal.closeModalWindowOnMenuPage();
    }

    @When("I click on the \"cart\" link in the header")
    public void clickCartLinkInHeader() {
        cartPage = menuPage.goToCartPage();
        totalButtonElement = cartPage.getTotalButton();

    }

}

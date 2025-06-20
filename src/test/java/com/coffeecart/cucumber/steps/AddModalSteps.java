package com.coffeecart.cucumber.steps;

import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.ui.component.CardComponent;
import com.coffeecart.ui.modal.AddModal;
import com.coffeecart.ui.page.MenuPage;
import io.cucumber.java.en.*;

import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class AddModalSteps {

    private final Hooks      hooks;
    private final SoftAssert softAssert;

    private MenuPage menuPage;
    private AddModal addModal;
    private String   borderBefore;

    private String lastDrinkName;

    public AddModalSteps(Hooks hooks) {
        this.hooks      = hooks;
        this.softAssert = hooks.getSoftAssert();
    }

    @Given("I open the menu page")
    public void iOpenTheMenuPage() {
        menuPage = new MenuPage(hooks.getDriver());
    }

    @When("I right-click the {string} card")
    public void iRightClickTheDrinkCard(String drinkName) {
        lastDrinkName = drinkName;

        CardComponent card = menuPage.getCardByName(drinkName);
        addModal = card.getCupComponent().rightClickCupBody();
    }

    @When("I hover over the 'No' button")
    public void iHoverOverTheNoButton() {
        borderBefore = addModal.getNoButtonBorderColor();
        addModal.hoverNoButton()
                .waitNoButtonBorderChanged(borderBefore, Duration.ofSeconds(2));
    }

    @When("I click the 'No' button")
    public void iClickTheNoButton() {
        addModal.clickNo();
    }

    @And("I refresh the page")
    public void iRefreshThePage() {
        hooks.getDriver().navigate().refresh();
        menuPage = new MenuPage(hooks.getDriver());

        CardComponent card = menuPage.getCardByName(lastDrinkName);
        addModal = card.getCupComponent().rightClickCupBody();
    }

    @When("I right-click the {string} card again")
    public void iRightClickTheCardAgain(String drinkName) {
        // якщо хочете гарантовано працювати саме з цим drinkName
        CardComponent card = menuPage.getCardByName(drinkName);
        addModal = card.getCupComponent().rightClickCupBody();
    }

    @Then("the add-to-cart modal appears")
    public void theAddToCartModalAppears() {
        softAssert.assertTrue(addModal.isModalDisplayed(),
                "Add-to-cart modal should appear");
    }

    @Then("the modal contains 'Yes' and 'No' buttons")
    public void theModalContainsYesAndNoButtons() {
        softAssert.assertTrue(addModal.getYesButton().isDisplayed(),
                "'Yes' button is missing");
        softAssert.assertTrue(addModal.getNoButton().isDisplayed(),
                "'No' button is missing");
    }

    @Then("the border colour of the 'No' button changes to {string}")
    public void theBorderColourOfNoChanges(String expectedColour) {
        softAssert.assertEquals(addModal.getNoButtonBorderColor(), expectedColour,
                "'No' button border colour did not change as expected");
    }

    @Then("the cart item counter equals {int}")
    public void theCartCounterEquals(int expected) {
        int actual = menuPage.getHeader().getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(actual, expected,
                String.format("Cart counter should be %d but is %d", expected, actual));
    }
}

package com.coffeecart.cucumber.steps;
import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.ui.component.HeaderComponent;
import com.coffeecart.ui.elements.TotalButtonElement;
import com.coffeecart.ui.page.MenuPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;

public class VerificationOfPopUpCartFunctioningSteps {
        private final Hooks hooks;
        private MenuPage menuPage;
        private HeaderComponent header;
        private TotalButtonElement totalButtonElement;
        private SoftAssert softAssert;

        private int initialCounter = 0;
        private int expectedCounter = 0;

        public VerificationOfPopUpCartFunctioningSteps(Hooks hooks) {
            this.hooks = hooks;
            this.softAssert = hooks.getSoftAssert();
        }

        @Given("Coffee cart menu page is opened")
        public void openMenuPage() {
            menuPage = new MenuPage(hooks.getDriver());
            totalButtonElement = menuPage.getButtonElement();
            header = menuPage.getHeader();
            softAssert = hooks.getSoftAssert();
        }

        @And("I note the initial cart counter value")
        public void getInitialCartCounter() {
            initialCounter = header.getTotalNumberItemsFromCartLink();
            expectedCounter = initialCounter;
            softAssert.assertEquals(initialCounter, 0, "Initial counter should be 0");
        }

        @When("I click on the coffee cup icon number {int}")
        public void clickOnCoffeeCup(int index) {
            menuPage.getCards().get(index - 1).clickCup();
            expectedCounter++;
        }

        @Then("the cart counter should be increased by 1")
        public void verifyCounterIncreasedByOne() {
            int actual = header.getTotalNumberItemsFromCartLink();
            softAssert.assertEquals(actual, expectedCounter, "Cart counter not incremented correctly");
        }

        @Then("the cart counter should show {int}")
        public void verifyCartCounter(int expectedValue) {
            int actual = header.getTotalNumberItemsFromCartLink();
            softAssert.assertEquals(actual, expectedValue, "Cart counter mismatch");
            softAssert.assertAll();
        }
    }

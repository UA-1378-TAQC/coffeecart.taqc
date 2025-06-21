package com.coffeecart.cucumber.steps;

import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.page.CartPage;
import com.coffeecart.ui.component.HeaderComponent;
import com.coffeecart.ui.component.FullItemComponent;
import com.coffeecart.ui.component.LuckyDayComponent;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class VerifyPlusButtonIsDisabledForDiscountedMochaSteps {

    private final Hooks hooks;
    private MenuPage menuPage;
    private CartPage cartPage;
    private HeaderComponent header;
    private LuckyDayComponent luckyDayComponent;
    private FullItemComponent mochaItem;
    private int cartCounterBefore;
    private double totalSumBefore;

    private static final String MOCHA_NAME = "(Discounted) Mocha";

    public VerifyPlusButtonIsDisabledForDiscountedMochaSteps(Hooks hooks) {
        this.hooks = hooks;
    }

    @Given("I am on the menu page")
    public void i_am_on_the_menu_page() {
        hooks.getDriver().get("https://example.com/menu"); // Replace with actual URL
        menuPage = new MenuPage(hooks.getDriver());
        header = menuPage.getHeader();
    }

    @When("I click on {string} drink {int} times")
    public void i_click_on_drink_times(String drinkName, int times) {
        for (int i = 0; i < times; i++) {
            menuPage.clickDrink(drinkName);
        }
    }

    @Then("I verify {string} modal appears with extra cup of Mocha")
    public void i_verify_modal_appears_with_extra_cup_of_mocha(String modalName) {
        luckyDayComponent = menuPage.getLuckyDayComponent();
        Assert.assertTrue(
                luckyDayComponent.isDisplayed(),
                modalName + " modal is not visible"
        );
    }

    @When("I click on {string} button")
    public void i_click_on_button(String buttonName) {
        if (buttonName.equals("Yes, of course!")) {
            menuPage = luckyDayComponent.clickYes();
        }
    }

    @And("I go to the cart page")
    public void i_go_to_the_cart_page() {
        cartPage = menuPage.goToCartPage();
    }

    @Then("I verify (Discounted) Mocha and three other coffee cups are in the cart")
    public void i_verify_discounted_mocha_and_three_other_coffee_cups_are_in_the_cart() {
        mochaItem = cartPage.getFullItemByName(MOCHA_NAME);
        Assert.assertNotNull(mochaItem, "(Discounted) Mocha is not in the cart");
        Assert.assertEquals(
                cartPage.getFullItems().size(), 4,
                "There are not 4 items in the cart"
        );
    }

    @And("I verify (Discounted) Mocha quantity is {string}")
    public void i_verify_discounted_mocha_quantity_is(String qty) {
        Assert.assertNotNull(mochaItem, "Mocha not found");
        Assert.assertEquals(
                mochaItem.getCount(), Integer.parseInt(qty),
                "Mocha quantity does not match"
        );
    }

    @And("I verify (Discounted) Mocha price is {string}")
    public void i_verify_discounted_mocha_price_is(String price) {
        Assert.assertNotNull(mochaItem, "Mocha not found");
        String actualPrice = String.format("$%.2f", mochaItem.getPriceOfOneUnit());
        Assert.assertEquals(
                actualPrice, price,
                "Mocha price does not match"
        );
    }

    @And("I verify {string} button should be disabled for (Discounted) Mocha")
    public void i_verify_button_should_be_disabled_for_discounted_mocha(String buttonName) {
        Assert.assertNotNull(mochaItem, "Mocha not found");
        if (buttonName.equals("+")) {
            Assert.assertFalse(
                mochaItem.plusButtonIsEnabled(),
                "'+' button for (Discounted) Mocha is enabled, but it should be disabled."
            );
        }
    }

    @When("I click on {string} button on (Discounted) Mocha")
    public void i_click_on_button_on_discounted_mocha(String buttonName) {
        if (buttonName.equals("+")) {
            mochaItem.clickOnAddButton();
        }
    }

    @And("I verify only one (Discounted) Mocha in the cart")
    public void i_verify_only_one_discounted_mocha_in_the_cart() {
        long count = cartPage.getFullItems().stream().filter(i -> i.getItemLabelString().equals(MOCHA_NAME)).count();
        Assert.assertEquals(
                count, 1,
                "There should be only one (Discounted) Mocha in the cart"
        );
    }

    @And("I verify total sum in the cart is not increased")
    public void i_verify_total_sum_in_the_cart_is_not_increased() {
        double sumAfter = cartPage.getSumOfTotalPricesFromCart();
        Assert.assertEquals(
                sumAfter, totalSumBefore,
                "Total sum increased after clicking '+', but it should not change."
        );
    }

    @And("I verify the cart counter in the header is not increased")
    public void i_verify_the_cart_counter_in_the_header_is_not_increased() {
        int counterAfter = header.getTotalNumberItemsFromCartLink();
        Assert.assertEquals(
                counterAfter, cartCounterBefore,
                "Cart counter increased after clicking '+', but it should not change."
        );
    }
}

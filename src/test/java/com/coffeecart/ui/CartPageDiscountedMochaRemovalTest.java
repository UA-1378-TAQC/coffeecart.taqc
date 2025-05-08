package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.component.FullItemComponent;
import com.coffeecart.ui.component.LuckyDayComponent;
import com.coffeecart.ui.page.CartPage;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CartPageDiscountedMochaRemovalTest extends BaseTestRunner {
    private static final String DISCOUNTED_MOCHA = "(Discounted) Mocha";
    private static final String EMPTY_CART_MESSAGE = "No coffee, go add some.";
    private static final String DRINK = DrinkEnum.ESPRESSO.getRecipe().getName();

    @Test
    @Description("Verify that the Discounted Mocha item in the Cart Page disappears after removing the other coffee cups via the 'x' button")
    @Feature("Discounted Mocha Removal")
    @Issue("31")
    @Owner("Nataliia Hrusha")
    public void verifyDiscountedMochaRemovalViaRemoveButtonOnCartPage() {
        SoftAssert softAssert = new SoftAssert();
        MenuPage menuPage = new MenuPage(driver);

        menuPage.clickDrink(DRINK).clickDrink(DRINK).clickDrink(DRINK);

        LuckyDayComponent luckyDayComponent = menuPage.getLuckyDayComponent();
        softAssert.assertTrue(menuPage.getLuckyDayModalRoot().isDisplayed(),
                "The LuckyDayModal should be displayed.");
        luckyDayComponent.clickYes();

        int numberItemsInCart = menuPage.getHeader().getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(numberItemsInCart, 4,
                String.format("Cart counter should be '4' but was '%d'", numberItemsInCart));

        CartPage cartPage = menuPage.goToCartPage();

        FullItemComponent mochaItem = cartPage.getFullItemByName(DISCOUNTED_MOCHA);
        softAssert.assertNotNull(mochaItem, "Discounted Mocha should be present in cart");
        softAssert.assertEquals(mochaItem.getCount(), 1,
                String.format("Mocha quantity should be 1 but was %d", mochaItem.getCount()));


        for (FullItemComponent item : cartPage.getFullItems()) {
            if (!item.getItemLabelString().equals(DISCOUNTED_MOCHA)) {
                cartPage = item.clickOnDeleteButton();
            }
        }

        mochaItem = cartPage.getFullItemByName(DISCOUNTED_MOCHA);
        softAssert.assertNull(mochaItem, "Discounted Mocha should not be present in cart after removing other items");

        softAssert.assertFalse(cartPage.getTotalButton().isDisplayed(),
                "Total button should not be displayed when cart is empty");

        softAssert.assertEquals(cartPage.getEmptyCartMessage().getText(), EMPTY_CART_MESSAGE, "Empty cart message should be '" + EMPTY_CART_MESSAGE + "' when all items are removed");

        numberItemsInCart = cartPage.getHeader().getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(numberItemsInCart, 0,
                String.format("Cart counter should be '0' but was '%d'", numberItemsInCart));

        softAssert.assertAll();
    }

}

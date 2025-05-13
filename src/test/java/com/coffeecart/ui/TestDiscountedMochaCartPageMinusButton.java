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

public class TestDiscountedMochaCartPageMinusButton extends BaseTestRunner {

    private static final String DISCOUNTED_MOCHA = "(Discounted) Mocha";

    private static final String DRINK1 = DrinkEnum.ESPRESSO.getRecipe().getName();
    private static final String DRINK2 = DrinkEnum.ESPRESSO_MACCHIATO.getRecipe().getName();
    private static final String DRINK3 = DrinkEnum.CAPPUCCINO.getRecipe().getName();

    @Test
    @Description("Verify that the (Discounted) Mocha item in the Cart Page disappears after removing the other coffee cups via the '-' button")
    @Feature("Discounted Mocha Removal")
    @Issue("37")
    @Owner("Roman Fedko")
    public void verifyDiscountedMochaCardMinusButton(){
        SoftAssert softAssert = new SoftAssert();
        MenuPage menuPage = new MenuPage(driver);

        menuPage.clickDrink(DRINK1).clickDrink(DRINK2).clickDrink(DRINK3);

        LuckyDayComponent luckyDayComponent = menuPage.getLuckyDayComponent();
        softAssert.assertTrue(menuPage.getLuckyDayModalRoot().isDisplayed(), "The LuckyDayModal should be displayed.");
        luckyDayComponent.clickYes();

        int numberItemsInCard = menuPage.getHeader().getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(numberItemsInCard, 4,
                String.format("Cart counter should be '4' but was '%d'", numberItemsInCard));

        CartPage cartPage = menuPage.goToCartPage();

        FullItemComponent mochaItem = cartPage.getFullItemByName(DISCOUNTED_MOCHA);
        softAssert.assertNotNull(mochaItem, "Discounted Mocha should be present in cart");
        softAssert.assertEquals(mochaItem.getCount(), 1,
                String.format("Mocha quantity should be 1 but was %d", mochaItem.getCount()));

        for (FullItemComponent item : cartPage.getFullItems()) {
            if (!item.getItemLabelString().equals(DISCOUNTED_MOCHA)) {
                cartPage = item.clickOnRemoveButton();
            }
        }

        mochaItem = cartPage.getFullItemByName(DISCOUNTED_MOCHA);
        softAssert.assertNull(mochaItem, "Discounted Mocha should not be present in cart after removing other items");

        softAssert.assertFalse(cartPage.getTotalButton().isDisplayed(),
                "Total button should not be displayed when cart is empty");

        softAssert.assertTrue(cartPage.emptyCartMessageIsDisplayed(), "Empty cart message should be displayed ");

        numberItemsInCard = cartPage.getHeader().getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(numberItemsInCard, 0,
                String.format("Cart counter should be '0' but was '%d'", numberItemsInCard));

        softAssert.assertAll();
    }

}

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
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Test;

public class TestDiscountedMochaButton extends BaseTestRunner {

    private static final String DISCOUNTED_MOCHA = "(Discounted) Mocha";

    private static final String DRINK1 = DrinkEnum.ESPRESSO.getRecipe().getName();
    private static final String DRINK2 = DrinkEnum.ESPRESSO_MACCHIATO.getRecipe().getName();
    private static final String DRINK3 = DrinkEnum.CAPPUCCINO.getRecipe().getName();

    @Test
    @Description("Verify '+' button is disabled for '(Discounted) Mocha' in the Cart Page")
    @Feature("Is Plus Button Disabled for Discounted Mocha")
    @Issue("19")
    @Owner("Roman Kmet")
    public void testPlusButtonForDiscountedMocha() {
        SoftAssert softAssert = new SoftAssert();
        MenuPage menuPage = new MenuPage(driver);

        menuPage.clickDrink(DRINK1).clickDrink(DRINK2).clickDrink(DRINK3);

        LuckyDayComponent luckyDayComponent = menuPage.getLuckyDayComponent();
        softAssert.assertTrue(menuPage.getLuckyDayModalRoot().isDisplayed(), "The LuckyDayModal should be displayed.");
        luckyDayComponent.clickYes();

        CartPage cartPage = menuPage.goToCartPage();
        
        FullItemComponent mochaItem = cartPage.getFullItemByName(DISCOUNTED_MOCHA);
        softAssert.assertNotNull(mochaItem, "Discounted Mocha should be present in cart");
        softAssert.assertEquals(mochaItem.getCount(), 1,
                String.format("Mocha quantity should be 1 but was %d", mochaItem.getCount()));

        mochaItem.clickOnAddButton();

        int updatedCount = mochaItem.getCount();
        softAssert.assertEquals(updatedCount, 1, 
                String.format("Mocha quantity should remain 1 but was %d after clicking '+' button", updatedCount));

        boolean isPlusButtonEnabled = mochaItem.plusButtonIsEnabled();
        softAssert.assertFalse(isPlusButtonEnabled, "The '+' button should be disabled when Mocha quantity is 1");

        softAssert.assertAll();
    }
}

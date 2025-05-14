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
import static org.testng.Assert.*;

public class TestDiscountedMochaButton extends BaseTestRunner {

    @Test
    @Description("Verify '+' button is disabled for '(Discounted) Mocha' in the Cart Page")
    @Feature("Is Plus Button Disabled for Discounted Mocha")
    @Issue("19")
    @Owner("Roman Kmet")
    public void testPlusButtonForDiscountedMocha() {
        MenuPage menuPage = new MenuPage(driver);

        menuPage.clickDrink(DrinkEnum.ESPRESSO.getRecipe().getName())
                .clickDrink(DrinkEnum.ESPRESSO_MACCHIATO.getRecipe().getName())
                .clickDrink(DrinkEnum.CAPPUCCINO.getRecipe().getName());

        LuckyDayComponent luckyDayComponent = menuPage.getLuckyDayComponent();
        assertTrue(menuPage.getLuckyDayModalRoot().isDisplayed(), "The LuckyDayModal should be displayed.");
        luckyDayComponent.clickYes();

        CartPage cartPage = menuPage.goToCartPage();
        
        FullItemComponent mochaItem = cartPage.getFullItemByName(DrinkEnum.DISCOUNTED_MOCHA.getRecipe().getName());
        assertNotNull(mochaItem, "Discounted Mocha should be present in cart");
        assertEquals(mochaItem.getCount(), 1,
                String.format("Mocha quantity should be 1 but was %d", mochaItem.getCount()));

        mochaItem.clickOnAddButton();

        int updatedCount = mochaItem.getCount();
        assertEquals(updatedCount, 1,
                String.format("Mocha quantity should remain 1 but was %d after clicking '+' button", updatedCount));

        boolean isPlusButtonEnabled = mochaItem.plusButtonIsEnabled();
        assertFalse(isPlusButtonEnabled, "The '+' button should be disabled when Mocha quantity is 1");
    }
}

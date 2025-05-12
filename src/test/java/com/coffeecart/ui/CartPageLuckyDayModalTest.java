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

public class CartPageLuckyDayModalTest extends BaseTestRunner {
    private static final String DISCOUNTED_MOCHA = "(Discounted) Mocha";
    private static final String DRINK = DrinkEnum.ESPRESSO.getRecipe().getName();

    @Test
    @Description("Verify the Lucky Day Modal behavior and verify the functionality of the '+' button for (Discounted) Mocha in the Cart Modal.")
    @Feature("Lucky Day Modal and Cart Modal Functionality")
    @Issue("27")
    @Owner("Dmytro Slobodianiuk")
    public void verifyLuckyDayModalAndCartModalFunctionality() {
        SoftAssert softAssert = new SoftAssert();

        MenuPage menuPage = new MenuPage(driver);

        menuPage.clickDrink(DRINK);
        int cartCountAfterFirstClick = menuPage.getHeader().getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(cartCountAfterFirstClick, 1, "Expected cart counter to be 1 after first click");

        menuPage.clickDrink(DRINK).clickDrink(DRINK);
        
        LuckyDayComponent luckyDayComponent = menuPage.getLuckyDayComponent();
        softAssert.assertTrue(menuPage.getLuckyDayModalRoot().isDisplayed(), "'Lucky Day' modal should appear after 3 drinks");

        luckyDayComponent.clickYes();

        softAssert.assertFalse(menuPage.getLuckyDayModalRoot().isDisplayed(), "'Lucky Day' modal should disappear after clicking 'Yes, of course!'");

        menuPage.getButtonElement.hoverOverTotalButton();

        CartPage cartPage = new CartPage(driver);
        softAssert.assertTrue(cartPage.isCartModalDisplayed(), "Cart modal should appear when hovering over 'Total' button");

        FullItemComponent mochaItem = cartPage.getFullItemByName(DISCOUNTED_MOCHA);
        softAssert.assertNotNull(mochaItem, "(Discounted) Mocha should be present in the Cart Modal");
        softAssert.assertEquals(cartPage.getFullItems().size(), 4, "Expected 4 items in the Cart Modal");

        softAssert.assertEquals(mochaItem.getCount(), 1, "(Discounted) Mocha quantity should be 1 in the Cart Modal");

        softAssert.assertFalse(mochaItem.isPlusButtonEnabled(), "The '+' button should be disabled for (Discounted) Mocha");

        boolean isPlusButtonClickable = mochaItem.isPlusButtonClickable();
        softAssert.assertFalse(isPlusButtonClickable, "The '+' button for (Discounted) Mocha should not be clickable");

        int mochaCountAfterAttempt = mochaItem.getCount();
        softAssert.assertEquals(mochaCountAfterAttempt, 1, "(Discounted) Mocha quantity should not increase after attempting to click '+'");


        softAssert.assertEquals(cartPage.getTotalPrice(), cartPage.getOriginalTotalPrice(),
                "Total price should not increase when attempting to click '+' on (Discounted) Mocha");

        softAssert.assertAll();
    }
}

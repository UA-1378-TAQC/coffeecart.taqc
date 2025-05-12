package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.component.CartComponent;
import com.coffeecart.ui.component.FullItemComponent;
import com.coffeecart.ui.component.LuckyDayComponent;
import com.coffeecart.ui.component.ShortItemComponent;
import com.coffeecart.ui.elements.TotalButtonElement;
import com.coffeecart.ui.modal.PaymentDetailModal;
import com.coffeecart.ui.page.CartPage;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;

import java.util.List;

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

        menuPage.clickDrink(DRINK)
                .clickDrink(DRINK)
                .clickDrink(DRINK);

        LuckyDayComponent luckyDayComponent = menuPage.getLuckyDayComponent();
        softAssert.assertTrue(menuPage.getLuckyDayModalRoot().isDisplayed(), "Lucky Day modal should be displayed");
        luckyDayComponent.clickYes();

        TotalButtonElement totalButton = menuPage.getButtonElement();
        CartComponent cartComponent = totalButton.hoverTotalButton();

        ShortItemComponent mochaItem = cartComponent.getShortItems().stream()
            .filter(item -> item.getName().equals(DISCOUNTED_MOCHA))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Discounted Mocha not found in cart preview"));

        softAssert.assertNotNull(mochaItem, "Discounted Mocha should be present in the cart");
        softAssert.assertEquals(mochaItem.getCount(), 1, "Initial quantity of Mocha should be 1");
        softAssert.assertFalse(mochaItem.plusButtonIsEnabled(), "'+' button should be disabled for Discounted Mocha");

        mochaItem.clickOnAddButton(); 
        softAssert.assertEquals(mochaItem.getCount(), 1, "Mocha quantity should not increase after '+' click");

        double totalBefore = totalButton.getMoneyCounter();
        double totalAfter = totalButton.getMoneyCounter();
        softAssert.assertEquals(totalAfter, totalBefore, "Total sum should not increase");

        softAssert.assertAll();
    }
}

package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.component.CartComponent;
import com.coffeecart.ui.component.FullItemComponent;
import com.coffeecart.ui.component.ShortItemComponent;
import com.coffeecart.ui.page.CartPage;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestSynchronizationBetweenCarts extends BaseTestRunner {
    int expectedCount = 1;
    String expectedTotalPrice = "Total: $18.00";
    String expectedDoubleTotalPrice = "Total: $36.00";
    String drinkName = DrinkEnum.FLAT_WHITE.getRecipe().getName();

    @Test
    public void testSynchronizationBetweenCarts() {
        SoftAssert softAssert = new SoftAssert();
        MenuPage menuPage = new MenuPage(driver);
        CartComponent cartComponent = menuPage
                .clickDrink(drinkName)
                .getButtonElement()
                .hoverTotalButton();
        ShortItemComponent flatWhiteShortItem = cartComponent
                .getShortItems()
                .getFirst();
        String nameOfItemInPopUpCart = flatWhiteShortItem.getName();
        int countOfItemImPopUpCart = flatWhiteShortItem.getCount();
        softAssert.assertEquals(nameOfItemInPopUpCart, drinkName);
        softAssert.assertEquals(countOfItemImPopUpCart, expectedCount);

        String actualTotalPriceInPopUpCart = menuPage
                .getButtonElement()
                .getTotalButton()
                .getText();
        softAssert.assertEquals(actualTotalPriceInPopUpCart, expectedTotalPrice);

        CartPage cartPage = menuPage.goToCartPage();
        FullItemComponent itemOnCartPage = cartPage
                .getFullItems()
                .getFirst();
        String nameOfItemOnCartPage = itemOnCartPage.getItemLabel().getText();
        int countOfItemOnCartPage = itemOnCartPage.getCount();
        softAssert.assertEquals(nameOfItemOnCartPage, nameOfItemInPopUpCart);
        softAssert.assertEquals(countOfItemOnCartPage, countOfItemImPopUpCart);

        String totalPriceOnCartPage = cartPage
                .getTotalButton()
                .getTotalButton()
                .getText();
        softAssert.assertEquals(totalPriceOnCartPage, actualTotalPriceInPopUpCart);

        cartPage = itemOnCartPage
                .clickOnAddButton(1);
        countOfItemOnCartPage = cartPage
                .getFullItemByName(drinkName)
                .getCount();
        softAssert.assertEquals(countOfItemOnCartPage, expectedCount+1);

        totalPriceOnCartPage = cartPage
                .getTotalButton()
                .getTotalButton()
                .getText();
        softAssert.assertEquals(totalPriceOnCartPage,expectedDoubleTotalPrice);

        menuPage = cartPage.goToMenuPage();
        actualTotalPriceInPopUpCart = menuPage
                .getButtonElement()
                .getTotalButton()
                .getText();
        softAssert.assertEquals(actualTotalPriceInPopUpCart, totalPriceOnCartPage);


        flatWhiteShortItem = menuPage
                .getButtonElement()
                .hoverTotalButton()
                .getShortItems()
                .getFirst();

        nameOfItemInPopUpCart = flatWhiteShortItem.getName();
        countOfItemImPopUpCart = flatWhiteShortItem.getCount();
        softAssert.assertEquals(nameOfItemInPopUpCart, drinkName);
        softAssert.assertEquals(countOfItemImPopUpCart, countOfItemOnCartPage);

        flatWhiteShortItem = flatWhiteShortItem.clickMinus();
        nameOfItemInPopUpCart = flatWhiteShortItem.getName();
        countOfItemImPopUpCart = flatWhiteShortItem.getCount();
        softAssert.assertEquals(nameOfItemInPopUpCart, drinkName);
        softAssert.assertEquals(countOfItemImPopUpCart, expectedCount);

        actualTotalPriceInPopUpCart = menuPage
                .getButtonElement()
                .getTotalButton()
                .getText();
        softAssert.assertEquals(actualTotalPriceInPopUpCart, expectedTotalPrice);

        cartPage = menuPage.goToCartPage();
        itemOnCartPage = cartPage
                .getFullItems()
                .getFirst();
        nameOfItemOnCartPage = itemOnCartPage.getItemLabel().getText();
        countOfItemOnCartPage = itemOnCartPage.getCount();
        softAssert.assertEquals(nameOfItemOnCartPage, nameOfItemInPopUpCart);
        softAssert.assertEquals(countOfItemOnCartPage, countOfItemImPopUpCart);

        totalPriceOnCartPage = cartPage
                .getTotalButton()
                .getTotalButton()
                .getText();
        softAssert.assertEquals(totalPriceOnCartPage, actualTotalPriceInPopUpCart);

        softAssert.assertAll();
    }
}

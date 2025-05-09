package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.data.MenuPageDataProviders;
import com.coffeecart.dataprovider.CheckCupPriceCostAndIngredientsDataProvider;
import com.coffeecart.ui.component.HeaderComponent;
import com.coffeecart.ui.page.CartPage;
import com.coffeecart.ui.page.MenuPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;

public class TestSuccessfulPaymentClearCart extends BaseTest{

    @AfterMethod
    public void resetDriver() {
        driver.get(testValueProvider.getBaseUIUrl());
    }

    @Test(dataProvider = "drinkNames", dataProviderClass = MenuPageDataProviders.class)
    @Description("Verify successful payment clear the cart")
    @Feature("Cart Validation")
    @Issue("28")
    @Owner("Nazar Tarasiuk")
    public void testSuccessfulPaymentClearPrice(String drinkName) {

        String text = new MenuPage(driver)
                .clickDrink(drinkName)
                .clickTotalButton()
                .enterName(testValueProvider.getUserName())
                .enterEmail(testValueProvider.getUserEmail())
                .clickSubmitButtonWithValidInput()
                .getSuccessTitleText();
        Assert.assertEquals(text, "Thanks for your purchase. Please check your email for payment.", "Test precondition failed");

        var softAssert = new SoftAssert();

        double mainPageCounter = new MenuPage(driver)
                .getButtonElement()
                .getMoneyCounter();
        double delta = 0.0001f;
        Assert.assertEquals(mainPageCounter,0.0, delta, "TotalButton counter isn't zero (MenuPage)");

        int cartPageCounter = new MenuPage(driver)
                .goToCartPage()
                .getTotalNumberOfItemsFromCart();
        Assert.assertEquals(cartPageCounter,0,"The CartPage cart size isn't zero");

        int headerCounter = new CartPage(driver).getHeader().getTotalNumberItemsFromCartLink();
        Assert.assertEquals(headerCounter,0,"The Header counter isn't zero");

        softAssert.assertAll();
    }
}

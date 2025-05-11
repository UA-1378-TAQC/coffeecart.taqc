package com.coffeecart.ui;

import com.coffeecart.dataprovider.CheckCupPriceCostAndIngredientsDataProvider;
import com.coffeecart.dataprovider.DecreasingButtonOnTheCartPageTestDataProvider;
import com.coffeecart.ui.component.FullItemComponent;
import com.coffeecart.ui.elements.TotalButtonElement;
import com.coffeecart.ui.page.CartPage;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DecreasingButtonOnTheCartPageTest extends BaseTestRunner {
    @Test(dataProvider = "checkDecreaseButtonInTheCartListDataProvider", dataProviderClass = DecreasingButtonOnTheCartPageTestDataProvider.class)
    @Description("Verify the correctness of decreasing the number of {drinkName} on the Cart page")
    @Feature("Cart list decrease button verification")
    @Issue("17")
    @Owner("Anatolii Orynchak")
    public void checkDecreaseButtonInTheCartList(String drinkName, double price){
        SoftAssert softAssert = new SoftAssert();
        int maxExpectedCount = 2;

        CartPage cartPage = new MenuPage(driver)
                .clickDrink(drinkName)
                .clickDrink(drinkName)
                .goToCartPage();
        cartPage.waitForPageToLoad(1);
        softAssert.assertTrue(cartPage.getRootFullItems().getFirst().isDisplayed());

        TotalButtonElement totalButton = cartPage.getTotalButton();
        FullItemComponent currentItem = cartPage.getFullItemByName(drinkName);

        softAssert.assertEquals(currentItem.getPriceOfOneUnit(), price);
        softAssert.assertEquals(currentItem.getCount(), maxExpectedCount);
        softAssert.assertEquals(cartPage.getSumOfTotalPricesFromCart(), maxExpectedCount * price);
        softAssert.assertEquals(totalButton.getMoneyCounter(), maxExpectedCount * price);

        while (maxExpectedCount > 1){
            currentItem.clickOnRemoveButton();
            maxExpectedCount--;
            String expectedUnitValue = String.format("$%.2f x %d", price, maxExpectedCount).replace(",",".");
            softAssert.assertEquals(currentItem.getCount(), maxExpectedCount);
            softAssert.assertEquals(currentItem.getUnitDescString(), expectedUnitValue);
            softAssert.assertEquals(cartPage.getSumOfTotalPricesFromCart(), maxExpectedCount * price);
            softAssert.assertEquals(cartPage.getTotalNumberOfItemsFromCart(), maxExpectedCount);
            softAssert.assertEquals(totalButton.getMoneyCounter(), maxExpectedCount * price);
        }

        currentItem.clickOnRemoveButton();

        softAssert.assertTrue(cartPage.getRootFullItems().isEmpty());
        softAssert.assertTrue(cartPage.getEmptyCartMessage().isDisplayed());

        softAssert.assertAll();
    }
}

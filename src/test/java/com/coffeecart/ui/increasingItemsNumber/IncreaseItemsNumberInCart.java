package com.coffeecart.ui.increasingItemsNumber;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.component.FullItemComponent;
import com.coffeecart.ui.component.HeaderComponent;
import com.coffeecart.ui.page.CartPage;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Optional;

public class IncreaseItemsNumberInCart extends BaseTestRunner {
    @Test
    public void testIncreaseItemsNumberInCart() {
        MenuPage menuPage = new MenuPage(driver);

        CartPage cartPage = menuPage
                .clickDrink(DrinkEnum.getName(DrinkEnum.ESPRESSO_MACCHIATO))
                .goToCartPage();

        Optional<FullItemComponent> optionalItem = cartPage
                .getFullItems().stream()
                .filter(item -> item.getItemLabelString().equals(DrinkEnum.getName(DrinkEnum.ESPRESSO_MACCHIATO)))
                .findFirst();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(optionalItem.isPresent(), "The drink '" + DrinkEnum.getName(DrinkEnum.ESPRESSO_MACCHIATO) + "' should be present in the cart");

        FullItemComponent item = optionalItem.get();
        item.clickOnAddButton();

        softAssert.assertEquals(item.getCount(), 2, "Item count should be 2 after adding");
        softAssert.assertEquals(item.getUnitDescString(), "$12.00 x 2", "Unit description should match expected format");
        softAssert.assertEquals(item.getTotalPriceAsNumber(), 24.00, "Total price should be correct after adding 1 item");

        HeaderComponent header = cartPage.getHeader();
        int actualCartCount = header.getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(actualCartCount, 2, "Header cart count should reflect 2 items");

        softAssert.assertAll();
    }
}

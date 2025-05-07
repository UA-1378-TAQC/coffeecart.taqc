package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.component.CartComponent;
import com.coffeecart.ui.component.ShortItemComponent;
import com.coffeecart.ui.elements.TotalButtonElement;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("Cart pop-up Functionality")
@Feature("Pop-up Cart Behavior")
public class VerificationOfPopUpCartFunctioningTest extends BaseTestRunner {
    @DataProvider(name = "drinksProvider")
    public Object[][] drinksProvider() {
        return new Object[][] {
                { DrinkEnum.ESPRESSO },
                { DrinkEnum.CAPPUCCINO },
                { DrinkEnum.FLAT_WHITE },
                { DrinkEnum.MOCHA },
                { DrinkEnum.AMERICANO },
                { DrinkEnum.ESPRESSO_CON_PANNA },
                { DrinkEnum.ESPRESSO_MACCHIATO },
                { DrinkEnum.CAFE_BREVE},
                { DrinkEnum.CAFFE_LATTE}
        };
    }

    @Test(description = "Verify cart pop-up functionality for any drink from the menu",dataProvider = "drinksProvider")
    @Story("Cart pop-up behavior when interacting with any drink from the menu")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Khrystyna Martynova")
    @Issue("3")
    @Link(name = "Coffee Cart", url = "https://coffee-cart.app/")
    @Description("Test verification of pop-up cart functioning (buttons +/-)")
    public void cartManipulationTest(DrinkEnum drinkEnum) {
        SoftAssert softAssert = new SoftAssert();
        MenuPage menuPage = new MenuPage(driver);

        String drinkName = drinkEnum.getRecipe().getName();
        menuPage.clickDrink(drinkName);

        TotalButtonElement totalButtonElement = menuPage.getButtonElement();
        CartComponent cartComponent = menuPage.getCartPreview(totalButtonElement.getTotalButton());

        ShortItemComponent item = cartComponent.getShortItems().stream()
                .filter(shortItem -> shortItem.getName().contains(drinkName))
                .findFirst()
                .orElse(null);

        softAssert.assertNotNull(item, "Pop-up should contain '" + drinkName + "'");

        if (item != null) {
            softAssert.assertEquals(item.getCount(), 1, "Quantity should be 1 after adding");

            item.clickPlus();
            softAssert.assertEquals(item.getCount(), 2, "Quantity should be 2 after clicking '+'");

            item.clickMinus();
            softAssert.assertEquals(item.getCount(), 1, "Quantity should be 1 after clicking '-'");

            item.clickMinus();
            softAssert.assertEquals(totalButtonElement.getTotalButton().getText(), "Total: $0.00", "Total should be $0 after cart is empty");
        } else {
            softAssert.fail("The item '" + drinkName + "' was not found in the cart preview");
        }
        softAssert.assertAll();
    }
}

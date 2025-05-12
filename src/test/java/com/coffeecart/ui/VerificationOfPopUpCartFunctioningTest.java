package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.data.MenuPageDataProviders;
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

    @Test(description = "Verify cart pop-up functionality for any drink from the menu",dataProvider = "drinkNames",dataProviderClass = MenuPageDataProviders.class)
    @Story("Cart pop-up behavior when interacting with any drink from the menu")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Khrystyna Martynova")
    @Issue("14")
    @Link(name = "Coffee Cart", url = "https://coffee-cart.app/")
    @Description("Test verification of pop-up cart functioning (buttons +/-)")
    public void cartManipulationTest(String drinkName) {
        SoftAssert softAssert = new SoftAssert();
        MenuPage menuPage = new MenuPage(driver)
                .clickDrink(drinkName);

        TotalButtonElement totalButtonElement = menuPage.getButtonElement();
        CartComponent cartComponent = menuPage.getCartPreview(totalButtonElement.getTotalButton());

        ShortItemComponent item = cartComponent.findItemByName(drinkName);

        softAssert.assertNotNull(item, "Pop-up should contain '" + drinkName + "'");

        softAssert.assertEquals(item.getCount(), 1, "Quantity should be 1 after adding");

        item.clickPlus();
        softAssert.assertEquals(item.getCount(), 2, "Quantity should be 2 after clicking '+'");

        item.clickMinus();
        softAssert.assertEquals(item.getCount(), 1, "Quantity should be 1 after clicking '-'");

        item.clickMinus();
        softAssert.assertEquals(totalButtonElement.getTotalButton().getText(), "Total: $0.00", "Total should be $0 after cart is empty");

        softAssert.assertAll();
    }
}

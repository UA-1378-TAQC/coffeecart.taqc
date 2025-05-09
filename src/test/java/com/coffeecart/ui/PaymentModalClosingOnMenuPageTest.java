package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.page.CartPage;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.modal.PaymentDetailModal;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class PaymentModalClosingOnMenuPageTest extends BaseTestRunner {
    private static final String DRINK = DrinkEnum.ESPRESSO.getRecipe().getName();

    @Test
    @Description("Verify that the Payment Details Modal retains pre-filled values after closing via the 'X' icon on the MenuPage")
    @Feature("Closing Payment Details Modal")
    @Issue("171")
    @Owner("Nataliia Hrusha")
    public void verifyPaymentModalClosing() {
        SoftAssert softAssert = new SoftAssert();

        PaymentDetailModal paymentModal = new MenuPage(driver)
                .clickDrink(DRINK)
                .clickTotalButton()
                .enterName(testValueProvider.getUserName())
                .enterEmail(testValueProvider.getUserEmail())
                .markCheckbox();

        MenuPage menuPage = paymentModal.closeModalWindowOnMenuPage();

        paymentModal = menuPage.clickTotalButton();

        softAssert.assertEquals(paymentModal.getInputNameValue(), testValueProvider.getUserName(),
                "Name field should retain value after modal close");
        softAssert.assertEquals(paymentModal.getInputEmailValue(), testValueProvider.getUserEmail(),
                "Email field should retain value after modal close");
        softAssert.assertTrue(paymentModal.isCheckboxMarked(),
                "Checkbox should remain checked after modal close");

        paymentModal.closeModalWindowOnMenuPage();

        CartPage cartPage = menuPage.goToCartPage();
        paymentModal = cartPage.clickOnTotalButton();

        softAssert.assertEquals(paymentModal.getInputNameValue(), "",
                "Name field should be empty after navigation");
        softAssert.assertEquals(paymentModal.getInputEmailValue(), "",
                "Email field should be empty after navigation");
        softAssert.assertFalse(paymentModal.isCheckboxMarked(),
                "Checkbox should be unchecked after navigation");

        softAssert.assertAll();
    }
}

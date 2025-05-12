package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.modal.PaymentDetailModal;
import com.coffeecart.ui.modal.SuccessfulPopUp;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestName extends BaseTestRunner{
    private static final String DRINK = DrinkEnum.ESPRESSO.getRecipe().getName();

    @Test
    @Description("Verify error handling when invalid data is entered in the Name field.")
    @Feature("Error handling")
    @Issue("4")
    @Owner("Dmytro Slobodianiuk")
    public void VerifyName(){
        SoftAssert softAssert = new SoftAssert();

        PaymentDetailModal paymentModal = new MenuPage(driver)
                    .clickDrink(DRINK)
                    .clickTotalButton()
                    .enterName("")
                    .enterEmail(testValueProvider.getUserEmail())
                    .clickSubmitButtonWithInvalidInput();

        softAssert.assertTrue(paymentModal.isModalVisible(), "Modal should still be visible after invalid input");
        softAssert.assertFalse(paymentModal.isNameInputValid(), "Name input should be invalid when empty");
        softAssert.assertEquals(paymentModal.getNameValidationMessage(), "Please fill out this field.");

        softAssert.assertAll();
    
    }
}

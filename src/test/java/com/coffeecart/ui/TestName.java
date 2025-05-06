package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.page.MenuPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TestName extends BaseTest{
    @AfterMethod
    public void resetDriver() {
        driver.get(testValueProvider.getBaseUIUrl());
    }
    @Test
    public void VerifyEmail(){
        new MenuPage(driver).clickDrink(DrinkEnum.getName(DrinkEnum.CAPPUCCINO)).clickTotalButton().enterName("123Viktoriia").enterEmail("viktoriia11@gmail.com").clickSubmitButtonWithValidInput();
    }
    @Test
    public void VerifyEmail2(){
        new MenuPage(driver).clickDrink(DrinkEnum.getName(DrinkEnum.CAPPUCCINO)).clickTotalButton().enterName("@#viktoriia!").enterEmail("viktoriia11@gmail.com").clickSubmitButtonWithInvalidInput().closeModalWindowOnCartPage();
    }
}

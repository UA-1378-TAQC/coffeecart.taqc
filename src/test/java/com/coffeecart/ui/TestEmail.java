package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.page.MenuPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TestEmail extends BaseTest{
    @AfterMethod
    public void resetDriver() {
        driver.get(testValueProvider.getBaseUIUrl());
    }
    @Test
    public void VerifyEmail(){
        new MenuPage(driver).clickDrink(DrinkEnum.getName(DrinkEnum.CAPPUCCINO)).clickTotalButton().enterName("Viktoriia").enterEmail("").clickSubmitButtonWithInvalidInput().closeModalWindowOnCartPage();
    }
    @Test
    public void VerifyEmail2(){
        new MenuPage(driver).clickDrink(DrinkEnum.getName(DrinkEnum.CAPPUCCINO)).clickTotalButton().enterName("Viktoriia").enterEmail("viktoriia11gmail.com").clickSubmitButtonWithInvalidInput().closeModalWindowOnCartPage();
    }
    @Test
    public void VerifyEmail3(){
        new MenuPage(driver).clickDrink(DrinkEnum.getName(DrinkEnum.CAPPUCCINO)).clickTotalButton().enterName("Viktoriia").enterEmail("viktoriia11@").clickSubmitButtonWithInvalidInput().closeModalWindowOnCartPage();
    }
    @Test
    public void VerifyEmail4(){
        new MenuPage(driver).clickDrink(DrinkEnum.getName(DrinkEnum.CAPPUCCINO)).clickTotalButton().enterName("Viktoriia").enterEmail("viktoriia 11@gmail.com").clickSubmitButtonWithInvalidInput().closeModalWindowOnCartPage();
    }
    @Test
    public void VerifyEmail5(){
        new MenuPage(driver).clickDrink(DrinkEnum.getName(DrinkEnum.CAPPUCCINO)).clickTotalButton().enterName("Viktoriia").enterEmail("v!k#toriia11@gmail.com").clickSubmitButtonWithValidInput();
    }
    @Test
    public void VerifyEmail6(){
        new MenuPage(driver).clickDrink(DrinkEnum.getName(DrinkEnum.CAPPUCCINO)).clickTotalButton().enterName("Viktoriia").enterEmail("viktoriia11@gmail.c").clickSubmitButtonWithValidInput();
    }
}

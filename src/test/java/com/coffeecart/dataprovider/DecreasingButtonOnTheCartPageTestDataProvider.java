package com.coffeecart.dataprovider;

import org.testng.annotations.DataProvider;

public class DecreasingButtonOnTheCartPageTestDataProvider {
    @DataProvider(name="checkDecreaseButtonInTheCartListDataProvider")
    public Object[][] checkDecreaseButtonInTheCartListDataProvider(){
        return new Object[][]{
                {"Espresso Macchiato", 12.00}
        };
    }
}

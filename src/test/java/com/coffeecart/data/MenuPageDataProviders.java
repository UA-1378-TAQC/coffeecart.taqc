package com.coffeecart.data;

import org.testng.annotations.DataProvider;

import java.util.Arrays;

public class MenuPageDataProviders {
    @DataProvider(name = "drinkNames")
    public Object[][] provideDrinkNames() {
        return Arrays.stream(DrinkEnum.values())
                .map(drinkEnum -> new Object[]{DrinkEnum.getName(drinkEnum)})
                .toArray(Object[][]::new);
    }
}

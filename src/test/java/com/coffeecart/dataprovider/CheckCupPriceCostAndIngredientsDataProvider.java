package com.coffeecart.dataprovider;
import org.testng.annotations.DataProvider;

public class CheckCupPriceCostAndIngredientsDataProvider {
    @DataProvider(name = "checkCorrectCardTitlesDataProvider")
    public Object[][] checkCorrectCardTitlesDataProvider() {
        return new Object[][]{
                {"Espresso"},
                {"Espresso Macchiato"},
                {"Cappuccino"},
                {"Mocha"},
                {"Flat White"},
                {"Americano"},
                {"Cafe Latte"},
                {"Espresso Con Panna"},
                {"Cafe Breve"}
        };
    }

    @DataProvider(name = "checkCorrectCardPricesDataProvider")
    public Object[][] checkCorrectCardPricesDataProvider() {
        return new Object[][]{
                {"Espresso", 10.00},
                {"Espresso Macchiato", 12.00},
                {"Cappuccino", 19.00},
                {"Mocha", 8.00},
                {"Flat White", 18.00},
                {"Americano", 7.00},
                {"Cafe Latte", 16.00},
                {"Espresso Con Panna", 14.00},
                {"Cafe Breve", 15.00}
        };
    }

    @DataProvider(name = "checkCorrectCardIngredientsDataProvider")
    public Object[][] checkCorrectCardIngredientsDataProvider() {
        return new Object[][]{
                {"Espresso", "espresso"},
                {"Espresso Macchiato", "milk foam", "espresso"},
                {"Cappuccino", "milk foam", "steamed milk", "espresso"},
                {"Mocha", "whipped cream", "steamed milk", "chocolate syrup", "espresso"},
                {"Flat White", "steamed milk", "espresso"},
                {"Americano", "water", "espresso"},
                {"Cafe Latte", "milk foam", "steamed milk", "espresso"},
                {"Espresso Con Panna", "whipped cream", "espresso"},
                {"Cafe Breve", "milk foam", "steamed cream", "steamed milk", "espresso"}
        };
    }


    @DataProvider(name = "checkIncorrectFullComplianceCardIngredientsDataProvider")
    public Object[][] checkIncorrectFullComplianceCardIngredientsDataProvider() {
        return new Object[][]{
                {"Espresso", "espresso", "espresso"},
                {"Espresso Macchiato", "espresso", "espresso"},
                {"Cappuccino", "steamed milk", "steamed milk", "espresso"},
                {"Mocha", "whipped cream", "chocolate syrup", "chocolate syrup", "chocolate syrup"}
        };
    }
}

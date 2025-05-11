package com.coffeecart.ui;

import com.coffeecart.ui.component.CardComponent;
import com.coffeecart.ui.page.MenuPage;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.data.Ingredients;
import com.coffeecart.ui.elements.Drink;

import com.coffeecart.ui.testrunners.BaseTestRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;


public class BaseTest extends BaseTestRunner {
    SoftAssert softAssert = new SoftAssert();
    @Test
    public void firstTest() {


    }
    private DrinkEnum recipes;

    @Test
    public void testRecipeOrder() {
        String recipeNames[] = {
                DrinkEnum.getName(DrinkEnum.ESPRESSO),
                DrinkEnum.getName(DrinkEnum.ESPRESSO_MACCHIATO),
                DrinkEnum.getName(DrinkEnum.CAPPUCCINO),
                DrinkEnum.getName(DrinkEnum.MOCHA),
                DrinkEnum.getName(DrinkEnum.FLAT_WHITE),
                DrinkEnum.getName(DrinkEnum.AMERICANO),
                DrinkEnum.getName(DrinkEnum.CAFFE_LATTE),
                DrinkEnum.getName(DrinkEnum.ESPRESSO_CON_PANNA),
                DrinkEnum.getName(DrinkEnum.CAFE_BREVE)
        };
        int count = 0;
        for (Drink r : recipes.asCollection()) {
            assertEquals(r.getName(), recipeNames[count++]);
        }
    }
    @Test
    public void testQuantities() {
        softAssert.assertEquals(DrinkEnum.AMERICANO.getRecipe().getIngredientQuantity(Ingredients.ESPRESSO), 1, "Кількість Espresso в Americano неправильна");
        softAssert.assertEquals(DrinkEnum.AMERICANO.getRecipe().getIngredientQuantity(Ingredients.WATER), 1, "Кількість Water в Americano неправильна");
        softAssert.assertEquals(DrinkEnum.CAPPUCCINO.getRecipe().getIngredientQuantity(Ingredients.MILK_FOAM), 1, "Кількість Milk Foam в Cappuccino неправильна");
        softAssert.assertEquals(DrinkEnum.CAPPUCCINO.getRecipe().getIngredientQuantity(Ingredients.ESPRESSO), 1, "Кількість Espresso в Cappuccino неправильна");
        softAssert.assertEquals(DrinkEnum.CAPPUCCINO.getRecipe().getIngredientQuantity(Ingredients.STEAMED_MILK), 1, "Кількість Steamed Milk в Cappuccino неправильна");
        softAssert.assertAll();
    }

@Test
public void testRecipeIngredientsOrder() {
    softAssert.assertEquals(getIngredientNames(DrinkEnum.ESPRESSO.getRecipe().getIngredients()),
            Arrays.asList("espresso"), "Неправильні інгредієнти для Espresso");

    softAssert.assertEquals(getIngredientNames(DrinkEnum.ESPRESSO_MACCHIATO.getRecipe().getIngredients()),
            Arrays.asList("espresso", "milk foam"), "Неправильні інгредієнти для Espresso Macchiato");

    softAssert.assertEquals(getIngredientNames(DrinkEnum.CAPPUCCINO.getRecipe().getIngredients()),
            Arrays.asList("espresso", "steamed milk", "milk foam"), "Неправильні інгредієнти для Cappuccino");

    softAssert.assertEquals(getIngredientNames(DrinkEnum.MOCHA.getRecipe().getIngredients()),
            Arrays.asList("espresso", "chocolate syrup", "steamed milk", "whipped cream"), "Неправильні інгредієнти для Mocha");

    softAssert.assertEquals(getIngredientNames(DrinkEnum.FLAT_WHITE.getRecipe().getIngredients()),
            Arrays.asList("espresso", "steamed milk"), "Неправильні інгредієнти для Flat White");
    softAssert.assertEquals(getIngredientNames(DrinkEnum.FLAT_WHITE.getRecipe().getIngredients()),
            Arrays.asList("espresso", "steamed milk"), "Неправильні інгредієнти для Flat White");

    softAssert.assertEquals(getIngredientNames(DrinkEnum.AMERICANO.getRecipe().getIngredients()),
            Arrays.asList("espresso", "water"), "Неправильні інгредієнти для Americano");

    softAssert.assertEquals(getIngredientNames(DrinkEnum.CAFFE_LATTE.getRecipe().getIngredients()),
            Arrays.asList("espresso", "steamed milk", "milk foam"), "Неправильні інгредієнти для Caffe Latte");

    softAssert.assertAll();
    }
private List<String> getIngredientNames(List<Ingredients> ingredientsList) {
        return ingredientsList.stream()
                .map(Ingredients::getName) // Берем только имена ингредиентов
                .collect(Collectors.toList());
    }

}









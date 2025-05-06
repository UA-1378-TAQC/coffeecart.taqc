package com.coffeecart.data;

import com.coffeecart.ui.data.Ingredients;
import com.coffeecart.ui.elements.Drink;
import com.coffeecart.ui.elements.DrinkIngredient;
import lombok.Getter;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public enum DrinkEnum {
    ESPRESSO(new Drink("Espresso",
            new DrinkIngredient(Ingredients.ESPRESSO, 1))),
    ESPRESSO_MACCHIATO(new Drink("Espresso Macchiato",
            new DrinkIngredient(Ingredients.ESPRESSO, 1),
            new DrinkIngredient(Ingredients.MILK_FOAM, 1))),
    CAPPUCCINO(new Drink("Cappuccino",
            new DrinkIngredient(Ingredients.ESPRESSO, 1),
            new DrinkIngredient(Ingredients.STEAMED_MILK, 1),
            new DrinkIngredient(Ingredients.MILK_FOAM, 1))),
    MOCHA(new Drink("Mocha",
            new DrinkIngredient(Ingredients.ESPRESSO, 1),
            new DrinkIngredient(Ingredients.CHOCOLATE_SYRUP, 1),
            new DrinkIngredient(Ingredients.STEAMED_MILK, 1),
            new DrinkIngredient(Ingredients.WHIPPED_CREAM, 1))),
    FLAT_WHITE(new Drink("Flat White",
            new DrinkIngredient(Ingredients.ESPRESSO, 1),
            new DrinkIngredient(Ingredients.STEAMED_MILK, 1))),
    AMERICANO(new Drink("Americano",
            new DrinkIngredient(Ingredients.ESPRESSO, 1),
            new DrinkIngredient(Ingredients.WATER, 1))),
    CAFFE_LATTE(new Drink("Cafe Latte",
            new DrinkIngredient(Ingredients.ESPRESSO, 1),
            new DrinkIngredient(Ingredients.STEAMED_MILK, 1),
            new DrinkIngredient(Ingredients.MILK_FOAM, 1))),
    ESPRESSO_CON_PANNA(new Drink("Espresso Con Panna",
            new DrinkIngredient(Ingredients.ESPRESSO, 1),
            new DrinkIngredient(Ingredients.WHIPPED_CREAM, 1))),
    CAFE_BREVE(new Drink("Cafe Breve",
            new DrinkIngredient(Ingredients.ESPRESSO, 1),
            new DrinkIngredient(Ingredients.STEAMED_MILK, 1),
            new DrinkIngredient(Ingredients.STEAMED_CREAM, 1),
            new DrinkIngredient(Ingredients.MILK_FOAM, 1)));

    @Getter
    private Drink recipe;

    DrinkEnum(Drink recipe) {
        this.recipe = recipe;
    }

    public static String getName(DrinkEnum drink) {
        return drink.recipe.getName();
    }

    public static Collection<Drink> asCollection() {
        return Arrays.stream(values())
                .map(DrinkEnum::getRecipe)
                .collect(Collectors.toList());
    }
    public static Drink getRecipeByOrdinal(int n) {
        if (n < 0 || n >= values().length) return null;
        return values()[n].getRecipe();
    }

    public static Map<String, Drink> asMap() {
        return Arrays.stream(values())
                .collect(Collectors.toMap(e -> e.getRecipe().getName(), DrinkEnum::getRecipe, (a, b) -> a, TreeMap::new));
    }
}

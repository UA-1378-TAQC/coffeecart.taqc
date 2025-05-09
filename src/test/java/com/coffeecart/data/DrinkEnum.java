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
    ESPRESSO(10,new Drink("Espresso", "特浓咖啡",
            new DrinkIngredient(Ingredients.ESPRESSO, 1))),
    ESPRESSO_MACCHIATO(12,new Drink("Espresso Macchiato", "浓缩玛奇朵",
            new DrinkIngredient(Ingredients.MILK_FOAM, 1),
            new DrinkIngredient(Ingredients.ESPRESSO, 1))),
    CAPPUCCINO(19,new Drink("Cappuccino", "卡布奇诺",
            new DrinkIngredient(Ingredients.MILK_FOAM, 2),
            new DrinkIngredient(Ingredients.STEAMED_MILK, 1),
            new DrinkIngredient(Ingredients.ESPRESSO, 1))),
    MOCHA(8,new Drink("Mocha", "摩卡",
            new DrinkIngredient(Ingredients.WHIPPED_CREAM, 1),
            new DrinkIngredient(Ingredients.STEAMED_MILK, 1),
            new DrinkIngredient(Ingredients.CHOCOLATE_SYRUP, 1),
            new DrinkIngredient(Ingredients.ESPRESSO, 1))),
    FLAT_WHITE(18,new Drink("Flat White", "平白咖啡",
            new DrinkIngredient(Ingredients.STEAMED_MILK, 2),
            new DrinkIngredient(Ingredients.ESPRESSO, 1))),
    AMERICANO(7,new Drink("Americano", "美式咖啡",
            new DrinkIngredient(Ingredients.WATER, 3),
            new DrinkIngredient(Ingredients.ESPRESSO, 1))),
    CAFFE_LATTE(16,new Drink("Caffe Latte", "拿铁",
            new DrinkIngredient(Ingredients.MILK_FOAM, 1),
            new DrinkIngredient(Ingredients.STEAMED_MILK, 2),
            new DrinkIngredient(Ingredients.ESPRESSO, 1))),
    ESPRESSO_CON_PANNA(14,new Drink("Espresso Con Panna", "浓缩康宝蓝",
            new DrinkIngredient(Ingredients.WHIPPED_CREAM, 1),
            new DrinkIngredient(Ingredients.ESPRESSO, 1))),
    CAFE_BREVE(15,new Drink("Cafe Breve", "半拿铁",
            new DrinkIngredient(Ingredients.WHIPPED_CREAM, 1),
            new DrinkIngredient(Ingredients.STEAMED_CREAM, 1),
            new DrinkIngredient(Ingredients.STEAMED_MILK, 1),
            new DrinkIngredient(Ingredients.ESPRESSO, 1)));
    @Getter
    private Drink recipe;
    @Getter
    private final int price;

    DrinkEnum(int price,Drink recipe) {
        this.price = price;
        this.recipe = recipe;
    }

    public static String getName(DrinkEnum drink) {
        return drink.recipe.getName();
    }

    public static String getChineseName(DrinkEnum drink) {
        return drink.recipe.getChineseName();
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
                .collect(Collectors.toMap(
                        e -> e.getRecipe().getName(),
                        DrinkEnum::getRecipe, (a, b) -> a,
                        TreeMap::new
                ));
    }
}

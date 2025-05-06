package com.coffeecart.ui.elements;

import com.coffeecart.ui.data.Ingredients;
import lombok.Getter;

public class DrinkIngredient {
    @Getter
    private Ingredients ingredient;

    @Getter
    private int quantity;

    public DrinkIngredient(Ingredients ingredient, int quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public String getName() {
        return ingredient.getName();
    }

    public int getQuantity() {
        return quantity;
    }

    public Ingredients getIngredient() {
        return ingredient;
    }
}

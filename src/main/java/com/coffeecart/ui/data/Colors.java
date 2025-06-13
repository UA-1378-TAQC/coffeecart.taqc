package com.coffeecart.ui.data;

public enum Colors {

    ESPRESSO_COLOR("rgba(222, 98, 38, 1)"),
    STEAMED_MILK_COLOR("rgba(178, 187, 140, 1)"),
    MILK_FOAM_COLOR("rgba(198, 218, 181, 1)"),
    GOLDEN ("rgba(218, 165, 32, 1)"),

    BLACK ("rgba(0, 0, 0, 1)"),

    TURQUOISE("rgba(127, 195, 179, 1)"),
    TERRACOTTA("rgba(222, 98, 38, 1)");


    private final String color;

    Colors(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}

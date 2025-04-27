package com.coffeecart.ui.component;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class CardComponent extends BaseComponent {
    @Getter
    @FindBy(xpath = ".//h4")
    private WebElement nameElement;
    @Getter
    @FindBy(xpath = ".//h4/small")
    private WebElement priceElement;
    @Getter
    @FindBy(xpath = ".//div[contains(@class, 'cup')]")
    private WebElement cupRootElement;

    private CupComponent cupComponent;

    public CardComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
        this.cupComponent = new CupComponent(driver, cupRootElement);
    }

    public String getName() {
        return nameElement.getText().split("\n")[0].trim();
    }

    public double getPrice() {
        String priceText = priceElement.getText().replace("$", "").trim();
        return Double.parseDouble(priceText);
    }

    public String getCardColor() {
        return rootElement.getAttribute("class"); // If color is determined by a CSS class
    }

    public CupComponent getCupComponent() {
        return cupComponent;
    }
}


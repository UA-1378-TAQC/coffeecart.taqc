package com.coffeecart.ui.component;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CartComponent extends BaseComponent {

    @Getter
    @FindBy(xpath = ".//li")
    private List<WebElement> shortItemElements;

    public CartComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public List<ShortItemComponent> getShortItems() {
        return shortItemElements.stream()
                .map(element -> new ShortItemComponent(driver, element))
                .collect(Collectors.toList());
    }

}

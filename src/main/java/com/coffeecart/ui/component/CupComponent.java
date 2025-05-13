package com.coffeecart.ui.component;

import com.coffeecart.ui.modal.AddModal;
import com.coffeecart.ui.page.MenuPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CupComponent extends BaseComponent {

    @Getter
    @FindBy(xpath = ".//div[contains(@class, 'cup-body')]")
    private WebElement cupBody;

    @Getter
    @FindBy(xpath = ".//div[contains(@class, 'cup-handler')]")
    private WebElement cupHandler;

    @Getter
    @FindBy(xpath = ".//div[contains(@class, 'ingredient')]")
    private List<WebElement> ingredientLayers;

    public CupComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public int getIngredientLayersCount() {
        return ingredientLayers.size();
    }

    public List<String> getIngredientNames() {
        return ingredientLayers.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    public Map<String, String> getIngredientsWithHeights() {
        Map<String, String> ingredientsWithHeights = new LinkedHashMap<>();

        for (int i = 0; i < ingredientLayers.size(); i++) {
            WebElement layer = ingredientLayers.get(i);
            String ingredientName = layer.getText().trim();
            String heightStyle = layer.getDomAttribute("style");
            ingredientsWithHeights.put(ingredientName, heightStyle);
        }

        return ingredientsWithHeights;
    }

    public boolean containsIngredient(String ingredientName) {
        return getIngredientNames().contains(ingredientName);
    }

    public String getCupBodyBorderBottomColor() {
        return cupBody.getCssValue("border-bottom-color");
    }

    public String getCupHandlerBorderBottomColor() {
        return cupHandler.getCssValue("border-bottom-color");
    }

    public String getCupBodyBorderLeftColor() {
        return cupBody.getCssValue("border-left-color");
    }

    public String getCupHandlerBorderLeftColor() {
        return cupHandler.getCssValue("border-left-color");
    }

    public List<String> getIngredientColors() {
        return ingredientLayers.stream()
                .map(element -> element.getCssValue("background-color"))
                .map(String::trim)
                .toList();
    }

    @Step("Click on Cup body")
    public MenuPage clickOnCupBody() {
        waitUntilElementClickable(cupBody);
        cupBody.click();
        return new MenuPage(driver);
    }

    @Step("Right‑click cup body → open Add‑to‑Cart modal")
    public AddModal rightClickCupBody() {

        actions.moveToElement(cupBody).contextClick().perform();

        By modalLocator = By.xpath("//dialog[@data-cy='add-to-cart-modal' and @open]");

        WebElement modalRoot = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(modalLocator));

        return new AddModal(driver, modalRoot);
    }

}

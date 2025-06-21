package com.coffeecart.cucumber.steps;

import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.component.CardComponent;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class VerifyTranslationToChineseSteps {

    private final Hooks hooks;
    private MenuPage menuPage;
    private CardComponent lastCard;

    public VerifyTranslationToChineseSteps(Hooks hooks) {
        this.hooks = hooks;
    }

    @Given("I am on coffee menu page")
    public void i_am_on_coffee_menu_page() {
        menuPage = new MenuPage(hooks.getDriver());
    }

    @When("I double click on coffee item {string}")
    public void i_double_click_on_coffee_item(String coffeeName) {
        lastCard = menuPage.getCardByName(coffeeName);
        WebElement nameElement = lastCard.getNameElement();
        Actions actions = new Actions(hooks.getDriver());
        actions.doubleClick(nameElement).perform();
    }

    @Then("the drink title should be translated into Chinese {string}")
    public void the_drink_title_should_be_translated_into_chinese(String chineseName) {
        String actual = lastCard.getNameElement().getText();
        Assert.assertTrue(
                actual.contains(chineseName),
                "Expected translation [" + chineseName + "], but got [" + actual + "]"
        );
    }
}
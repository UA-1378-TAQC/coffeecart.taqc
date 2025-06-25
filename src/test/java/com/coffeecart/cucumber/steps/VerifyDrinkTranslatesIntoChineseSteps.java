package com.coffeecart.cucumber.steps;

import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.ui.page.MenuPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class VerifyDrinkTranslatesIntoChineseSteps {
    private final Hooks hooks;
    private MenuPage menuPage;
    private WebElement selectedDrinkElement;
    private final SoftAssert softAssert;

    public VerifyDrinkTranslatesIntoChineseSteps(Hooks hooks) {
        this.hooks = hooks;
        this.softAssert = hooks.getSoftAssert();
    }

    @When("I double click drink {string}")
    public void iDoubleClickDrink(String drinkName) {
        List<WebElement> productNameElements = hooks.getDriver()
                .findElements(By.xpath("//*[@id='app']/div[2]/ul/li//h4"));

        for (WebElement element : productNameElements) {
            if (element.getText().contains(drinkName)) {
                selectedDrinkElement = element;
                Actions actions = new Actions(hooks.getDriver());
                actions.doubleClick(selectedDrinkElement).perform();
                break;
            }
        }
    }

    @Then("the drink name should change to {string}")
    public void theDrinkNameShouldChangeTo(String chineseName) {
        softAssert.assertNotNull(selectedDrinkElement, "Drink element should be found and double-clicked");
        softAssert.assertTrue(
                selectedDrinkElement.getText().contains(chineseName),
                "After double-click, drink name should be: " + chineseName
        );
        softAssert.assertAll();
    }
}

package com.coffeecart.cucumber.steps;

import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.ui.page.MenuPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentModalSteps {

    private Hooks hooks;
    private MenuPage menuPage;


    public PaymentModalSteps(Hooks hooks) {
        this.hooks = hooks;
    }

    @Given("User is on MenuPage")
    public void userIsOnCreateNewsPage() {
        menuPage = new MenuPage(hooks.getDriver());
        menuPage.getCards();

    }


}

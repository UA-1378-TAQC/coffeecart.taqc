package com.coffeecart.cucumber.steps;
import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.ui.component.HeaderComponent;
import com.coffeecart.ui.elements.TotalButtonElement;
import com.coffeecart.ui.page.MenuPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;

public class VerificationOfPopUpCartFunctioningSteps {
        private final Hooks hooks;
        private MenuPage menuPage;
        private HeaderComponent header;
        private TotalButtonElement totalButtonElement;
        private SoftAssert softAssert;

        private int initialCounter = 0;
        private int expectedCounter = 0;

        public VerificationOfPopUpCartFunctioningSteps(Hooks hooks) {
            this.hooks = hooks;
            this.softAssert = hooks.getSoftAssert();
        }

    }

package com.coffeecart.cucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/com/coffeecart/features",
        glue = {"com.coffeecart.cucumber.steps", "com.coffeecart.cucumber.hooks"},
        plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        monochrome = true
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
}

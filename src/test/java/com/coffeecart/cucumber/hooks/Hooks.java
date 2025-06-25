package com.coffeecart.cucumber.hooks;

import com.coffeecart.utils.TestValueProvider;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import lombok.Getter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;


public class Hooks {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Getter
    private WebDriver driver;

    @Getter
    private TestValueProvider testValueProvider = new TestValueProvider();

    @Getter
    private SoftAssert softAssert;

    @Before
    public void driverSetup() {
        if (driver == null) {
            initDriver();
        }


        softAssert = new SoftAssert();
        driver.get(testValueProvider.getBaseUIUrl());
    }

    public void initDriver() {
        ChromeOptions options = new ChromeOptions();

        String chromeOptionsArg = System.getProperty("chrome.options", "");
        if (!chromeOptionsArg.isEmpty()) {
            for (String option : chromeOptionsArg.split(",")) {
                options.addArguments(option);
            }
        }

        String userDataDir = System.getProperty("user.data.dir");
        if (userDataDir != null && !userDataDir.isEmpty()) {
            options.addArguments("--user-data-dir=" + userDataDir);
        }

        String lang = System.getProperty("chrome.options", "--lang=en");
        if (lang != null && !lang.isEmpty()) {
            options.addArguments(lang);
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(testValueProvider.getImplicitlyWait()));
    }

    @After
    public void tearDown() {
        saveImageAttach("PICTURE Test Name");
        if (driver != null) {
            driver.quit();
        }
        logger.info("Driver closed");

        softAssert.assertAll();
    }

    @Attachment(value = "Image name = {0}", type = "image/png")
    public byte[] saveImageAttach(String attachName){
        byte [] result = null;
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try{
            result = Files.readAllBytes(scrFile.toPath());
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}

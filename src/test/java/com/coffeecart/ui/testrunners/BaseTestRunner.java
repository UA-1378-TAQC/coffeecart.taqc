package com.coffeecart.ui.testrunners;

import com.coffeecart.utils.LocalStorageJS;
import com.coffeecart.utils.TestValueProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;


public class BaseTestRunner {

    protected static LocalStorageJS localStorageJS;
    protected static WebDriver driver;
    protected static TestValueProvider testValueProvider;

    @BeforeSuite
    public void beforeSuite() {
        WebDriverManager.chromedriver().setup();
        testValueProvider = new TestValueProvider();
        initDriver();
    }

    @Step("init ChromeDriver")
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

//        options.addArguments(" --profile-directory=Default");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(testValueProvider.getImplicitlyWait()));

        localStorageJS = new LocalStorageJS(driver);
    }

    @BeforeClass
    public void beforeClass() {
        if (driver == null) {
            initDriver();
        }

        driver.get(testValueProvider.getBaseUIUrl());
    }

    @AfterClass()
    public void afterClass() {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
    }

    @AfterSuite
    public void afterSuite() {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
    }

    @Step("Clear Browser Memory Cookies and LocalStorage.")
    public void clearBrowserMemory() {
        driver.manage().deleteAllCookies();
        localStorageJS.clearLocalStorage();
        driver.navigate().refresh();
    }


}

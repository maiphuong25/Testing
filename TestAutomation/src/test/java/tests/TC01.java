package tests;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.WebDriverFactory;

import static org.testng.Assert.assertTrue;

public class TC01 {
    WebDriver driver;
    HomePage homePage;
    LoginPage loginPage;

    String baseUrl = "http://railwayb2.somee.com";
    String validEmail = "phuongphon251@gmail.com";
    String validPassword = "123456789";

    @BeforeMethod
    public void setup() {
        driver = WebDriverFactory.getDriver();
        driver.get(baseUrl);
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

    @Test
    public void testLoginWithValidCredentials() {
        homePage.goToLoginPage();
        loginPage.login(validEmail, validPassword);
        assertTrue(homePage.isWelcomeMessageDisplayed(), "Welcome message should be displayed.");
    }
}

package tests;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.WebDriverFactory;

import static org.testng.Assert.assertEquals;

public class TC02 {
    WebDriver driver;
    HomePage homePage;
    LoginPage loginPage;

    String baseUrl = "http://railwayb2.somee.com";
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
    public void testLoginWithBlankUsername() {
        homePage.goToLoginPage();
        loginPage.login("", validPassword);
        assertEquals(loginPage.getErrorMessage(), "There was a problem with your login and/or errors exist in your form.");
    }
}

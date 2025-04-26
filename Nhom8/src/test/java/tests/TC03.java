package tests;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.WebDriverFactory;

import static org.testng.Assert.assertEquals;

public class TC03 {
    WebDriver driver;
    HomePage homePage;
    LoginPage loginPage;

    String baseUrl = "http://railwayb2.somee.com";
    String validEmail = "phuongphon251@gmail.com";

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
    public void testLoginWithInvalidPassword() {
        homePage.goToLoginPage();
        loginPage.login(validEmail, "wrongpass");
        assertEquals(loginPage.getErrorMessage(), "There was a problem with your login and/or errors exist in your form.");
    }
}

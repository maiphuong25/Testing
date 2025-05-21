package tests;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.WebDriverFactory;

import static org.testng.Assert.assertTrue;

public class TC05 {
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
    public void testWarningAfterMultipleWrongLogins() {
        homePage.goToLoginPage();
        for (int i = 0; i < 4; i++) {
            loginPage.login(validEmail, "wrongpass");
        }
        assertTrue(loginPage.getErrorMessage().contains("You have used 4 out of 5 login attempts"),
                "Warning message about login attempts should appear.");
    }
}

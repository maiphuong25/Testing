package tests;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.WebDriverFactory;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class TC06 {
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
    public void testAdditionalTabsShownAfterLogin() {
        homePage.goToLoginPage();
        loginPage.login(validEmail, validPassword);

        assertTrue(homePage.areAdditionalTabsDisplayed(),
                "My Ticket, Change Password, and Logout tabs should be visible.");

        homePage.goToMyTicketPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("ManageTicket.cshtml"));
        assertTrue(driver.getCurrentUrl().contains("ManageTicket.cshtml"),
                "Should navigate to My Ticket page.");

        homePage.goToChangePasswordPage();
        wait.until(ExpectedConditions.urlContains("ChangePassword.cshtml"));
        assertTrue(driver.getCurrentUrl().contains("ChangePassword.cshtml"),
                "Should navigate to Change Password page.");
    }
}

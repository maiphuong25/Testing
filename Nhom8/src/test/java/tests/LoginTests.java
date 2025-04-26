package tests;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.WebDriverFactory;

import java.time.Duration;

import static org.testng.Assert.*;

public class LoginTests {
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
    public void TC01_LoginWithValidCredentials() {
        homePage.goToLoginPage();
        loginPage.login(validEmail, validPassword);
        assertTrue(homePage.isWelcomeMessageDisplayed(), "Welcome message should be displayed.");
    }

    @Test
    public void TC02_LoginWithBlankUsername() {
        homePage.goToLoginPage();
        loginPage.login("", validPassword);
        assertEquals(loginPage.getErrorMessage(), "There was a problem with your login and/or errors exist in your form.");
    }

    @Test
    public void TC03_LoginWithInvalidPassword() {
        homePage.goToLoginPage();
        loginPage.login(validEmail, "wrongpass");
        assertEquals(loginPage.getErrorMessage(), "There was a problem with your login and/or errors exist in your form.");
    }

    @Test
    public void TC04_ClickBookTicketWhenNotLoggedIn_ShowsLoginPage() {
        homePage.goToBookTicketPage();
        // Book Ticket khi chưa đăng nhập thì sẽ tự chuyển về trang Login
        assertTrue(loginPage.isAtLoginPage(), "User should be redirected to login page.");
    }

    @Test
    public void TC05_ShowWarningAfterMultipleWrongLoginAttempts() {
        homePage.goToLoginPage();
        for (int i = 0; i < 4; i++) {
            loginPage.login(validEmail, "wrongpass");
        }
        assertTrue(loginPage.getErrorMessage().contains("You have used 4 out of 5 login attempts"),
                "Warning message about login attempts should appear.");
    }

    @Test
    public void TC06_AdditionalTabsShownAfterLogin() {
        homePage.goToLoginPage();
        loginPage.login(validEmail, validPassword);

        // Kiểm tra các tab bổ sung hiển thị sau đăng nhập
        assertTrue(homePage.areAdditionalTabsDisplayed(),
                "My Ticket, Change Password, and Logout tabs should be visible.");

        // Điều hướng đến trang My Ticket
        homePage.goToMyTicketPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("ManageTicket.cshtml"));
        assertTrue(driver.getCurrentUrl().contains("ManageTicket.cshtml"),
                "Should navigate to My Ticket page.");

        // Điều hướng đến trang Change Password
        homePage.goToChangePasswordPage();
        wait.until(ExpectedConditions.urlContains("ChangePassword.cshtml"));
        assertTrue(driver.getCurrentUrl().contains("ChangePassword.cshtml"),
                "Should navigate to Change Password page.");
    }
}

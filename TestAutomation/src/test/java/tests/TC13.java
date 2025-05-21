package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.EmailHelper;
import utils.WebDriverFactory;

public class TC13 {
    private WebDriver driver;
    private HomePage homePage;
    private ForgotPasswordPage forgotPasswordPage;
    private ResetPasswordPage resetPasswordPage;
    private EmailHelper emailHelper;

    @BeforeMethod
    public void setup() {
        driver = WebDriverFactory.getDriver();
        driver.get("http://railwayb2.somee.com");
        homePage = new HomePage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);
        resetPasswordPage = new ResetPasswordPage(driver);
        emailHelper = new EmailHelper();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void TC13_ResetPasswordWithMismatchPasswords() {
        homePage.goToLoginPage();
        homePage.goToForgotPasswordPage();
        String testEmail = "phuongphon251@gmail.com";
        forgotPasswordPage.requestReset(testEmail);
        String resetLink = emailHelper.getResetLink(testEmail);
        driver.get(resetLink);
        resetPasswordPage.fillPassword("Password123!");
        resetPasswordPage.fillConfirmPassword("Different123!");
        resetPasswordPage.clickResetButton();
        Assert.assertFalse(driver.getCurrentUrl().contains("Dashboard"));
        Assert.assertEquals(resetPasswordPage.getFormErrorMessage().trim(),
                "Could not reset password. Please correct the errors and try again.");
        Assert.assertEquals(resetPasswordPage.getConfirmPasswordErrorMessage().trim(),
                "The password confirmation did not match the new password.");
    }
}
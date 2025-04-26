package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.WebDriverFactory;

import java.util.UUID;

public class TC07 {
    private WebDriver driver;
    private HomePage homePage;
    private RegisterPage registerPage;
    private String testEmail;
    private final String testPassword = "12345678";
    private final String testPID = "123456789";

    @BeforeMethod
    public void setup() {
        driver = WebDriverFactory.getDriver();
        driver.get("http://railwayb2.somee.com");
        homePage = new HomePage(driver);
        registerPage = new RegisterPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testUserCanCreateNewAccount() {
        homePage.goToRegisterPage();
        testEmail = "test" + UUID.randomUUID().toString().substring(0, 5) + "@mail.com";
        registerPage.register(testEmail, testPassword, testPassword, testPID);
        Assert.assertTrue(driver.getPageSource().contains("Thank you for registering your account"));
    }
}
package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.WebDriverFactory;

import java.util.UUID;

public class TC14 {
    private WebDriver driver;
    private HomePage homePage;
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private BookTicketPage bookTicketPage;
    private final String testPassword = "12345678";
    private final String testPID = "123456789";

    @BeforeMethod
    public void setup() {
        driver = WebDriverFactory.getDriver();
        driver.get("http://railwayb2.somee.com");
        homePage = new HomePage(driver);
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        bookTicketPage = new BookTicketPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void TC14_UserCanBookOneTicket() {
        String testEmail = registerAndLogin();
        homePage.goToBookTicketPage();
        bookTicketPage.bookTicket("5/5/2025", "Sài Gòn", "Nha Trang", "Soft seat", "1");
        Assert.assertEquals(bookTicketPage.getSuccessMessage(), "Ticket booked successfully!");
    }

    private String registerAndLogin() {
        homePage.goToRegisterPage();
        String email = "user" + UUID.randomUUID().toString().substring(0, 5) + "@mail.com";
        registerPage.register(email, testPassword, testPassword, testPID);
        homePage.goToLoginPage();
        loginPage.login(email, testPassword);
        return email;
    }
}
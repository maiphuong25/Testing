package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.WebDriverFactory;

import java.util.UUID;

public class TC16 {
    private WebDriver driver;
    private HomePage homePage;
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private BookTicketPage bookTicketPage;
    private MyTicketPage myTicketPage;
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
        myTicketPage = new MyTicketPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void TC16_UserCanCancelTicket() {
        String testEmail = registerAndLogin();
        homePage.goToBookTicketPage();
        bookTicketPage.bookTicket("5/5/2025", "Sài Gòn", "Huế", "Soft seat", "1");
        Assert.assertEquals(bookTicketPage.getSuccessMessage(), "Ticket Booked Successfully!");
        homePage.goToMyTicketPage();
        boolean cancelled = myTicketPage.cancelTicket("Sài Gòn", "Huế", "Soft seat");
        Assert.assertTrue(cancelled, "No matching tickets found to cancel.");
        boolean ticketStillVisible = myTicketPage.isTicketStillVisible("Sài Gòn", "Huế", "Soft seat");
        Assert.assertFalse(ticketStillVisible, "Tickets are still available after cancellation!");
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
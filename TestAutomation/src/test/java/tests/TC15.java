package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.WebDriverFactory;

import java.util.UUID;

public class TC15 {
    private WebDriver driver;
    private HomePage homePage;
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private final String testPassword = "12345678";
    private final String testPID = "123456789";

    @BeforeMethod
    public void setup() {
        driver = WebDriverFactory.getDriver();
        driver.get("http://railwayb2.somee.com");
        homePage = new HomePage(driver);
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void TC15_OpenBookTicketFromTimetable() {
        String testEmail = registerAndLogin();
        TimetablePage timetablePage = new TimetablePage(driver);
        timetablePage.openBookTicketFrom("Huế", "Sài Gòn");
        Assert.assertEquals(timetablePage.getSelectedDepartStation(), "Huế");
        Assert.assertEquals(timetablePage.getSelectedArriveStation(), "Sài Gòn");
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
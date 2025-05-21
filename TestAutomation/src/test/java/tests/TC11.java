package tests;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.WebDriverFactory;

import java.util.UUID;

public class TC11 {
    private WebDriver driver;
    private HomePage homePage;
    private RegisterPage registerPage;

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
    public void testCreateAccountWithEmptyPasswordAndPID() {
        homePage.goToRegisterPage();
        String email = "empty" + UUID.randomUUID().toString().substring(0, 5) + "@mail.com";
        registerPage.register(email, "", "", "");

        Assert.assertTrue(registerPage.getFormErrorMessage().contains("There're errors"));

        WebElement passwordError = driver.findElement(By.xpath("//label[@for='password' and contains(@class,'validation-error')]"));
        Assert.assertTrue(passwordError.isDisplayed());
        Assert.assertEquals(passwordError.getText(), "Invalid password length.");

        WebElement pidError = driver.findElement(By.xpath("//label[@for='pid' and contains(@class,'validation-error')]"));
        Assert.assertTrue(pidError.isDisplayed());
        Assert.assertEquals(pidError.getText(), "Invalid ID length.");
    }
}

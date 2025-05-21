package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class TC12 {
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testResetPasswordWithBlankToken() throws InterruptedException {
        driver.get("http://railwayb2.somee.com");
        driver.findElement(By.linkText("Login")).click();

        WebElement forgotLink = driver.findElement(By.linkText("Forgot Password page"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", forgotLink);
        forgotLink.click();

        WebElement emailField = driver.findElement(By.id("email"));
        Thread.sleep(1000);
        emailField.sendKeys("thutest12345@gmail.com");

        driver.findElement(By.xpath("//input[@value='Send Instructions']")).click();

        WebElement newPasswordField = driver.findElement(By.id("newPassword"));
        WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
        WebElement resetTokenField = driver.findElement(By.id("resetToken"));

        newPasswordField.sendKeys("Test12345@New5");
        confirmPasswordField.sendKeys("Test12345@New5");
        resetTokenField.clear();

        WebElement resetButton = driver.findElement(By.xpath("//input[@value='Reset Password']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", resetButton);
        resetButton.click();

        WebElement mainErrorMsg = driver.findElement(By.xpath("//p[@class='message error']"));
        Assert.assertEquals(mainErrorMsg.getText().trim(), "The password reset token is incorrect or may be expired");

        WebElement tokenErrorMsg = driver.findElement(By.xpath("//label[@for='resetToken']/following-sibling::label[@class='validation-error']"));
        Assert.assertEquals(tokenErrorMsg.getText().trim(), "The password reset token is invalid.");
    }
}

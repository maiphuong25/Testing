package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResetPasswordPage {
    private WebDriver driver;

    private By passwordField = By.id("password");
    private By confirmPasswordField = By.id("confirmPassword");
    private By tokenField = By.id("token");
    private By resetButton = By.xpath("//input[@value='Reset Password']");
    private By confirmPasswordError = By.xpath("//label[@for='confirmPassword' and contains(@class,'validation-error')]");

    public ResetPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void fillConfirmPassword(String confirmPassword) {
        driver.findElement(confirmPasswordField).sendKeys(confirmPassword);
    }

    public void clearToken() {
        driver.findElement(tokenField).clear();
    }

    public void clickResetButton() {
        driver.findElement(resetButton).click();
    }

    public String getFormErrorMessage() {
        return driver.findElement(By.cssSelector(".message.error")).getText();
    }

    public String getConfirmPasswordErrorMessage() {
        return driver.findElement(confirmPasswordError).getText().trim();
    }
}

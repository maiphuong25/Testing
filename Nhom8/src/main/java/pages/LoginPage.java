package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    @FindBy(id = "username")
    WebElement usernameInput;

    @FindBy(id = "password")
    WebElement passwordInput;

    @FindBy(css = "input[type='submit']")
    WebElement loginButton;

    @FindBy(css = ".message.error")
    WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOf(usernameInput));
        usernameInput.clear();
        usernameInput.sendKeys(username);

        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.clear();
        passwordInput.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(loginButton));

        // Scroll để đảm bảo không bị che khuất
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", loginButton);

        try {
            loginButton.click();
        } catch (ElementClickInterceptedException e) {
            // Nếu vẫn bị chặn => dùng JS click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        }
    }

    public String getErrorMessage() {
        try {
            return errorMessage.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public boolean isAtLoginPage() {
        try {
            return loginButton.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}

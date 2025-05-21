package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePage {

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPasswordField;

    @FindBy(id = "pid")
    private WebElement pidField;

    @FindBy(xpath = "//input[@value='Register']")
    private WebElement registerButton;

    @FindBy(css = ".message")
    private WebElement registrationMessage;

    @FindBy(xpath = "//p[@class='message error']")
    private WebElement errorMessage;

    @FindBy(xpath = "//label[@for='password' and contains(text(),'Invalid password length.')]")
    private WebElement passwordLengthError;

    @FindBy(xpath = "//label[@for='pid' and contains(text(),'Invalid ID length.')]")
    private WebElement pidLengthError;

    public void register(String email, String password, String confirmPassword, String pid) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        confirmPasswordField.sendKeys(confirmPassword);
        pidField.sendKeys(pid);
        registerButton.click();
    }

    public String getRegistrationMessage() {
        return registrationMessage.getText();
    }

    public String getFormErrorMessage() {
        return errorMessage.getText();
    }

    public boolean isPasswordLengthErrorDisplayed() {
        return passwordLengthError.isDisplayed();
    }

    public boolean isPIDLengthErrorDisplayed() {
        return pidLengthError.isDisplayed();
    }
}

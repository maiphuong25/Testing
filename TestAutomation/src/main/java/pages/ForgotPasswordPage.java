package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ForgotPasswordPage extends BasePage {

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(xpath = "//input[@value='Send Instructions']")
    private WebElement sendInstructionsButton;

    @FindBy(id = "newPassword")
    private WebElement newPasswordField;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPasswordField;

    @FindBy(id = "resetToken")
    private WebElement resetTokenField;

    @FindBy(xpath = "//input[@value='Reset Password']")
    private WebElement resetPasswordButton;

    @FindBy(css = ".message")
    private WebElement messageLabel;

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    public void requestPasswordReset(String email) {
        emailField.sendKeys(email);
        sendInstructionsButton.click();
    }

    public void requestReset(String email) {
        emailField.clear();
        emailField.sendKeys(email);
        sendInstructionsButton.click();
    }

    public void resetPassword(String newPassword, String confirmPassword, String resetToken) {
        newPasswordField.sendKeys(newPassword);
        confirmPasswordField.sendKeys(confirmPassword);
        resetTokenField.clear();
        resetTokenField.sendKeys(resetToken);
        resetPasswordButton.click();
    }

    public String getResetPasswordMessage() {
        return messageLabel.getText();
    }
}

package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChangePasswordPage extends BasePage {
    public ChangePasswordPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "currentPassword")
    private WebElement currentPasswordField;

    @FindBy(id = "newPassword")
    private WebElement newPasswordField;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPasswordField;

    @FindBy(xpath = "//input[@value='Change Password']")
    private WebElement changePasswordButton;

    @FindBy(css = ".message")
    private WebElement messageLabel;

    public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        currentPasswordField.sendKeys(currentPassword);
        newPasswordField.sendKeys(newPassword);
        confirmPasswordField.sendKeys(confirmPassword);
        changePasswordButton.click();
    }

    public String getChangePasswordMessage() {
        return messageLabel.getText();
    }
}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class HomePage extends BasePage {

    @FindBy(linkText = "Login")
    private WebElement loginTab;

    @FindBy(linkText = "Book ticket")
    private WebElement bookTicketTab;

    @FindBy(linkText = "My ticket")
    private WebElement myTicketTab;

    @FindBy(linkText = "Change password")
    private WebElement changePasswordTab;

    @FindBy(linkText = "Log out")
    private WebElement logoutTab;

    @FindBy(css = "div.account strong")
    private WebElement welcomeMessage;

    @FindBy(linkText = "Register")
    private WebElement registerTab;

    @FindBy(linkText = "Timetable")
    private WebElement timetableTab;

    @FindBy(linkText = "Forgot Password page")
    private WebElement forgotPasswordLink;

    @FindBy(css = ".message")
    private WebElement successMessage;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private void clickWhenReady(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        element.click();
    }

    public void goToLoginPage() {
        clickWhenReady(loginTab);
    }

    public void goToBookTicketPage() {
        clickWhenReady(bookTicketTab);
    }

    public void goToMyTicketPage() {
        clickWhenReady(myTicketTab);
    }

    public void goToChangePasswordPage() {
        clickWhenReady(changePasswordTab);
    }

    public void goToRegisterPage() {
        clickWhenReady(registerTab);
    }

    public void goToTimetablePage() {
        clickWhenReady(timetableTab);
    }

    public void goToForgotPasswordPage() {
        clickWhenReady(forgotPasswordLink);
    }

    public boolean isWelcomeMessageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOf(welcomeMessage));
            return welcomeMessage.getText().toLowerCase().contains("welcome");
        } catch (Exception e) {
            System.out.println("Welcome message not found: " + e.getMessage());
            return false;
        }
    }

    public String getSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        return successMessage.getText();
    }

    public void openFromTimetable(String departStation, String arriveStation) {
        String xpath = "//table[@class='MyTable']//td[text()='" + departStation + "']/following-sibling::td[text()='" + arriveStation + "']/following-sibling::td//a[text()='Book ticket']";
        WebElement bookTicketLink = driver.findElement(By.xpath(xpath));
        clickWhenReady(bookTicketLink);
    }

    public boolean areAdditionalTabsDisplayed() {
        return myTicketTab.isDisplayed() && changePasswordTab.isDisplayed() && logoutTab.isDisplayed();
    }
}

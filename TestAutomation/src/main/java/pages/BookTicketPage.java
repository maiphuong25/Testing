package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class BookTicketPage extends BasePage {
    private WebDriverWait wait;

    public BookTicketPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(linkText = "Book ticket")
    private WebElement bookTicketLink;

    @FindBy(name = "Date")
    private WebElement departDateDropdown;

    @FindBy(name = "DepartStation")
    private WebElement departFromDropdown;

    @FindBy(xpath = "//span[@id='ArriveStation']//select")
    private WebElement arriveAtDropdown;

    @FindBy(name = "SeatType")
    private WebElement seatTypeDropdown;

    @FindBy(name = "TicketAmount")
    private WebElement ticketAmountDropdown;

    @FindBy(xpath = "//input[@type='submit' and @value='Book ticket']")
    private WebElement bookTicketButton;

    @FindBy(css = "p.message.success")
    private WebElement bookingMessage;

    @FindBy(tagName = "h1")
    private WebElement bookingSuccessHeading;

    public void navigateToBookTicketPage() {
        if (!driver.getCurrentUrl().contains("BookTicketPage")) {
            bookTicketLink.click();
        }
    }

    public void bookTicket(String date, String departFrom, String arriveAt, String seatType, String amount) {
        navigateToBookTicketPage();

        selectDropdownOption(departDateDropdown, date);
        selectDropdownOption(departFromDropdown, departFrom);
        waitForArriveListToLoad();
        selectDropdownOption(arriveAtDropdown, arriveAt);
        selectDropdownOption(seatTypeDropdown, seatType);
        selectDropdownOption(ticketAmountDropdown, amount);

        bookTicketButton.click();
    }

    private void selectDropdownOption(WebElement dropdown, String option) {
        new Select(dropdown).selectByVisibleText(option);
    }

    private String getSelectedOptionText(WebElement dropdown) {
        return new Select(dropdown).getFirstSelectedOption().getText();
    }

    private void waitForArriveListToLoad() {
        try {
            Thread.sleep(1000); // site không có AJAX ready-state, xài tạm
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isTicketBookedSuccessfully() {
        WebElement success = wait.until(ExpectedConditions.visibilityOf(bookingSuccessHeading));
        return success.isDisplayed();
    }

    public String getSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Tìm phần tử h1 với nội dung "Ticket Booked Successfully!"
        WebElement successMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[text()='Ticket Booked Successfully!' and @align='center']")
        ));
        return successMessageElement.getText();  // Lấy nội dung của phần tử
    }

    public String getArriveAt() {
        return getSelectedOptionText(arriveAtDropdown);
    }

    public String getDepartFrom() {
        return getSelectedOptionText(departFromDropdown);
    }
}

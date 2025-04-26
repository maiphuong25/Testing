package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.EmailHelper;
import utils.WebDriverFactory;
import utils.EmailHelper;



import org.openqa.selenium.WebDriver;

import java.util.UUID;

public class RailwayTest {

    private ForgotPasswordPage forgotPasswordPage;
    private ResetPasswordPage resetPasswordPage;
    private EmailHelper emailHelper;



    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private ChangePasswordPage changePasswordPage;
    private BookTicketPage bookTicketPage;
    private MyTicketPage myTicketPage;

    private String testEmail;
    private String testPassword = "12345678";
    private String testPID = "123456789";

    @BeforeMethod
    public void setup() {
        driver = WebDriverFactory.getDriver();
        driver.get("http://railwayb2.somee.com");

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        changePasswordPage = new ChangePasswordPage(driver);
        bookTicketPage = new BookTicketPage(driver);
        myTicketPage = new MyTicketPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void TC07_UserCanCreateNewAccount() {
        homePage.goToRegisterPage();
        testEmail = "test" + UUID.randomUUID().toString().substring(0, 5) + "@mail.com";
        registerPage.register(testEmail, testPassword, testPassword, testPID);

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Thank you for registering your account"));

    }

    // Đặt câu hỏi cho TC08

    @Test
    public void TC09_UserCanChangePassword() {
        String newPassword = "newPass123";

        // Pre-condition: Create & activate account
        testEmail = registerAndLogin();

        homePage.goToChangePasswordPage();
        changePasswordPage.changePassword(testPassword, newPassword, newPassword);
        String msg = changePasswordPage.getChangePasswordMessage();
        Assert.assertEquals(msg, "Your password has been updated");
    }

    @Test
    public void TC10_ConfirmPasswordMismatch() {
        homePage.goToRegisterPage();
        testEmail = "failConfirm" + UUID.randomUUID().toString().substring(0, 5) + "@mail.com";
        registerPage.register(testEmail, testPassword, "differentPass", testPID);
        String msg = registerPage.getFormErrorMessage();
        Assert.assertEquals(msg, "There're errors in the form. Please correct the errors and try again.");
    }

    @Test
    public void TC11_CreateAccountWithEmptyPasswordAndPID() {
        homePage.goToRegisterPage();
        testEmail = "empty" + UUID.randomUUID().toString().substring(0, 5) + "@mail.com";

        registerPage.register(testEmail, "", "", "");

        Assert.assertTrue(registerPage.getFormErrorMessage().contains("There're errors"));

        // Check hiển thị lỗi "Invalid password length"
        WebElement passwordError = driver.findElement(By.xpath("//label[@for='password' and contains(@class,'validation-error')]"));
        Assert.assertTrue(passwordError.isDisplayed());
        Assert.assertEquals(passwordError.getText(), "Invalid password length.");

        // Check hiển thị lỗi "Invalid ID length"
        WebElement pidError = driver.findElement(By.xpath("//label[@for='pid' and contains(@class,'validation-error')]"));
        Assert.assertTrue(pidError.isDisplayed());
        Assert.assertEquals(pidError.getText(), "Invalid ID length.");
    }

    @Test
    public void TC12_resetPasswordWithBlankToken() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Step 1: Navigate to Login Page
            driver.get("http://railwayb1.somee.com");
            driver.findElement(By.linkText("Login")).click();

            // Step 2: Click on "Forgot Password" link
            WebElement forgotLink = driver.findElement(By.linkText("Forgot Password page"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", forgotLink);
            forgotLink.click();

            // Step 3: Enter the email address
            WebElement emailField = driver.findElement(By.id("email"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", emailField);
            Thread.sleep(1000);
            emailField.sendKeys("thutest12345@gmail.com");

            // Step 4: Click on "Send Instructions"
            WebElement sendInstructionsButton = driver.findElement(By.xpath("//input[@value='Send Instructions']"));
            sendInstructionsButton.click();

            // Step 5: Simulate opening reset password page directly
            WebElement newPasswordField = driver.findElement(By.id("newPassword"));
            WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
            WebElement resetTokenField = driver.findElement(By.id("resetToken"));

            newPasswordField.sendKeys("Test12345@New5");
            confirmPasswordField.sendKeys("Test12345@New5");
            resetTokenField.clear(); // Step 6: Leave reset token blank

            // Step 7: Click Reset button
            WebElement resetButton = driver.findElement(By.xpath("//input[@value='Reset Password']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", resetButton);
            resetButton.click();

            // Step 8: Assert main error message
            WebElement mainErrorMsg = driver.findElement(By.xpath("//p[@class='message error']"));
            String expectedMainMsg = "The password reset token is incorrect or may be expired";
            Assert.assertEquals(mainErrorMsg.getText().trim(), expectedMainMsg, "Main error message not matched");

            // Step 9: Assert token field-level error
            WebElement tokenErrorMsg = driver.findElement(By.xpath("//label[@for='resetToken']/following-sibling::label[@class='validation-error']"));
            String expectedTokenMsg = "The password reset token is invalid.";
            Assert.assertEquals(tokenErrorMsg.getText().trim(), expectedTokenMsg, "Token field error message not matched");

        } finally {
            driver.quit(); // Always close the browser
        }
    }

    @Test
    public void TC13_ResetPasswordWithMismatchPasswords() {
        // Khởi tạo page object
        forgotPasswordPage = new ForgotPasswordPage(driver);
        resetPasswordPage = new ResetPasswordPage(driver);
        emailHelper = new EmailHelper();

        // Step 1: Navigate to Login Page
        homePage.goToLoginPage();

        // Step 2: Click on "Forgot Password page" link
        homePage.goToForgotPasswordPage();

        // Step 3: Nhập email
        String testEmail = "phuongphon251@gmail.com";
        forgotPasswordPage.requestReset(testEmail);

        // Step 4 + 5: Mô phỏng mở email và lấy link reset
        String resetLink = emailHelper.getResetLink(testEmail); // Giả lập: mock link reset
        driver.get(resetLink);

        // Step 6: Nhập mật khẩu không khớp
        resetPasswordPage.fillPassword("Password123!");
        resetPasswordPage.fillConfirmPassword("Different123!");

        // Step 7: Click nút Reset
        resetPasswordPage.clickResetButton();

        // Kiểm tra URL KHÔNG chuyển sang dashboard
        String currentUrl = driver.getCurrentUrl();
        Assert.assertFalse(currentUrl.contains("Dashboard"), "Should not redirect to dashboard");

        // Kiểm tra lỗi chính phía trên form
        Assert.assertEquals(resetPasswordPage.getFormErrorMessage().trim(),
                "Could not reset password. Please correct the errors and try again.");

        // Kiểm tra lỗi cạnh Confirm Password
        Assert.assertEquals(resetPasswordPage.getConfirmPasswordErrorMessage().trim(),
                "The password confirmation did not match the new password.");
    }


    @Test
    public void TC14_UserCanBookOneTicket() {
        testEmail = registerAndLogin();

        homePage.goToBookTicketPage();
        bookTicketPage.bookTicket("5/5/2025", "Sài Gòn", "Nha Trang", "Soft seat", "1");


        Assert.assertEquals(bookTicketPage.getSuccessMessage(), "Ticket booked successfully!");
    }

    @Test
    public void TC15_OpenBookTicketFromTimetable() {
        testEmail = registerAndLogin();

        TimetablePage timetablePage = new TimetablePage(driver);
        timetablePage.openBookTicketFrom("Huế", "Sài Gòn");

        Assert.assertEquals(timetablePage.getSelectedDepartStation(), "Huế");
        Assert.assertEquals(timetablePage.getSelectedArriveStation(), "Sài Gòn");

        System.out.println("Depart Date: " + timetablePage.getSelectedDepartDate());
        // Nếu muốn test chính xác ngày đi thì bật dòng dưới
        // Assert.assertEquals(timetablePage.getSelectedDepartDate(), "4/20/2025");
    }


    @Test
    public void TC16_UserCanCancelTicket() {
        testEmail = registerAndLogin();

        // Step 1: Book vé
        homePage.goToBookTicketPage();
        bookTicketPage.bookTicket("5/5/2025", "Sài Gòn", "Huế", "Soft seat", "1");
        Assert.assertEquals(bookTicketPage.getSuccessMessage(), "Ticket Booked Successfully!");

        // Step 2: Hủy vé
        homePage.goToMyTicketPage();
        boolean cancelled = myTicketPage.cancelTicket("Sài Gòn", "Huế", "Soft seat");
        Assert.assertTrue(cancelled, "No matching tickets found to cancel.");

        // Step 3: Xác minh vé đã biến mất
        boolean ticketStillVisible = myTicketPage.isTicketStillVisible("Sài Gòn", "Huế", "Soft seat");
        Assert.assertFalse(ticketStillVisible, "Tickets are still available after cancellation!");
    }



    // Support function for account registration + login
    private String registerAndLogin() {
        homePage.goToRegisterPage();
        String email = "user" + UUID.randomUUID().toString().substring(0, 5) + "@mail.com";
        registerPage.register(email, testPassword, testPassword, testPID);
        homePage.goToLoginPage();
        loginPage.login(email, testPassword);
        return email;
    }
}

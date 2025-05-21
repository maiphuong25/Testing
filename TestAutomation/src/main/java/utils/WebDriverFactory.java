package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    public static WebDriver getDriver() {
        // Cài đặt đường dẫn đến chromedriver
        System.setProperty("webdriver.chrome.driver", "D:/TaiLieuHocTap/KiemThu/chromedriver-win64/chromedriver.exe");

        // Tuỳ chọn khởi động Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); // Fix lỗi CORS nếu cần
        options.addArguments("--start-maximized");         // Mở trình duyệt toàn màn hình

        return new ChromeDriver(options);
    }
}

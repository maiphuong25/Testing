package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MyTicketPage extends BasePage {

    public MyTicketPage(WebDriver driver) {
        super(driver);
    }

    // Hủy vé
    public boolean cancelTicket(String departStation, String arriveStation, String seatType) {
        List<WebElement> rows = driver.findElements(By.xpath("//table[@class='MyTable']//tr"));
        for (WebElement row : rows) {
            String text = row.getText();
            if (text.contains(departStation) && text.contains(arriveStation) && text.contains(seatType)) {
                WebElement cancelButton = row.findElement(By.xpath(".//input[@value='Cancel']"));
                cancelButton.click();

                // Xác nhận alert
                driver.switchTo().alert().accept();

                // Đợi một chút cho page reload
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return true; // Nếu hủy thành công
            }
        }
        return false; // Nếu không tìm thấy vé cần hủy
    }

    // Kiểm tra xem vé đã bị hủy chưa (không còn trong danh sách)
    public boolean isTicketStillVisible(String departStation, String arriveStation, String seatType) {
        List<WebElement> rows = driver.findElements(By.xpath("//table[@class='MyTable']//tr"));
        for (WebElement row : rows) {
            String text = row.getText();
            if (text.contains(departStation) && text.contains(arriveStation) && text.contains(seatType)) {
                return true; // Nếu vé còn xuất hiện
            }
        }
        return false; // Nếu không tìm thấy vé trong danh sách, có thể đã bị hủy
    }
}

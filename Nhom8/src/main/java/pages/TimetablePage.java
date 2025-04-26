// TimetablePage.java
package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class TimetablePage extends BasePage {

    public TimetablePage(WebDriver driver) {
        super(driver);
    }

    public void openBookTicketFrom(String depart, String arrive) {
        // Click "Timetable" tab (phòng trường hợp test dùng trực tiếp từ HomePage)
        driver.findElement(By.linkText("Timetable")).click();

        // Click "book ticket" link của tuyến cụ thể
        WebElement bookTicketLink = driver.findElement(By.xpath("//tr[td[2][text()='" + depart + "'] and td[3][text()='" + arrive + "']]//a[text()='book ticket']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bookTicketLink);
        bookTicketLink.click();
    }

    public String getSelectedDepartStation() {
        Select select = new Select(driver.findElement(By.name("DepartStation")));
        return select.getFirstSelectedOption().getText().trim();
    }

    public String getSelectedArriveStation() {
        Select select = new Select(driver.findElement(By.name("ArriveStation")));
        return select.getFirstSelectedOption().getText().trim();
    }

    public String getSelectedDepartDate() {
        Select select = new Select(driver.findElement(By.name("Date")));
        return select.getFirstSelectedOption().getText().trim();
    }
}

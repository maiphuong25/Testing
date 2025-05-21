package utils;


public class EmailHelper {

    // Giả lập trả về reset password link cho email đã gửi yêu cầu
    public static String getResetLink(String email) {
        // Trong thực tế, bạn sẽ đọc email trong inbox hoặc API nào đó
        // Ở đây ta giả lập cho test
        return "http://railwayb2.somee.com/Account/ResetPassword?token=fake-token-for-" + email;
    }
}

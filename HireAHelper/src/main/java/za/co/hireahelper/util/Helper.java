package za.co.hireahelper.util;

public class Helper {


    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isValidMobileNumber(String mobileNumber) {
        return mobileNumber != null && mobileNumber.matches("^\\d{10,15}$"); // Basic check for digits, 10-15 length
    }

    public static boolean isValidRate(int rating) {
        return rating >= 1 && rating <= 5;
    }
}


















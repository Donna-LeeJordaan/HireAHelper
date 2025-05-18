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

    public static boolean isNullOrEmpty(String str) {return str == null || str.isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public static boolean isValidRate(double rate) {
        return rate > 0;

    }
}

















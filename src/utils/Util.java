package utils;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    public static int getAgeFromBirth(String birthDate) {
        LocalDate now = LocalDate.now();
        LocalDate parsedDate = LocalDate.parse(birthDate);

        return now.getYear() - parsedDate.getYear();
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}

package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
    private static final Map<String, SimpleDateFormat> dateFormats = new HashMap<>();
    private static final Map<String, DateTimeFormatter> dateTimeFormatters = new HashMap<>();
    private static final DateTimeFormatter sqlDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter sqlDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    static {
        dateFormats.put("dd/MM/yyyy", new SimpleDateFormat("dd/MM/yyyy"));
        dateFormats.put("MM-dd-yyyy", new SimpleDateFormat("MM-dd-yyyy"));
        dateFormats.put("yyyy/MM/dd", new SimpleDateFormat("yyyy/MM/dd"));
        dateFormats.put("yyyy-MM-dd", new SimpleDateFormat("yyyy-MM-dd"));
        dateFormats.put("dd-MM-yyyy", new SimpleDateFormat("dd-MM-yyyy"));
        dateFormats.put("yyyyMMdd", new SimpleDateFormat("yyyyMMdd"));
        dateFormats.put("dd-MM-yyyy HH:mm:ss", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));

        dateTimeFormatters.put("dd/MM/yyyy", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dateTimeFormatters.put("MM-dd-yyyy", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        dateTimeFormatters.put("yyyy/MM/dd", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        dateTimeFormatters.put("yyyy-MM-dd", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dateTimeFormatters.put("dd-MM-yyyy", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        dateTimeFormatters.put("yyyyMMdd", DateTimeFormatter.ofPattern("yyyyMMdd"));
        dateTimeFormatters.put("dd-MM-yyyy HH:mm:ss", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public static String formatLocalDate(LocalDate date, String format) {
        DateTimeFormatter formatter = dateTimeFormatters.get(format);
        if (formatter == null) {
            formatter = DateTimeFormatter.ofPattern(format);
        }
        return date.format(formatter);
    }

    public static LocalDate parseLocalDate(String dateStr, String format) {
        DateTimeFormatter formatter = dateTimeFormatters.get(format);
        if (formatter == null) {
            formatter = DateTimeFormatter.ofPattern(format);
        }
        return LocalDate.parse(ensureLeadingZeros(dateStr), formatter);
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = dateFormats.get(format);
        if (sdf == null) {
            sdf = new SimpleDateFormat(format);
        }
        return sdf.format(date);
    }

    public static Date parseDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat sdf = dateFormats.get(format);
        if (sdf == null) {
            sdf = new SimpleDateFormat(format);
        }
        return sdf.parse(ensureLeadingZeros(dateStr));
    }

    public static String formatSqlDate(java.sql.Date sqlDate) {
        return sqlDateFormatter.format(sqlDate.toLocalDate());
    }

    public static java.sql.Date parseSqlDate(String dateStr) {
        LocalDate localDate = LocalDate.parse(ensureLeadingZeros(dateStr), sqlDateFormatter);
        return java.sql.Date.valueOf(localDate);
    }

    public static String formatSqlDateTime(java.sql.Timestamp sqlTimestamp) {
        return sqlDateTimeFormatter.format(sqlTimestamp.toLocalDateTime());
    }

    public static java.sql.Timestamp parseSqlDateTime(String dateTimeStr) {
        LocalDateTime localDateTime = LocalDateTime.parse(ensureLeadingZeros(dateTimeStr), sqlDateTimeFormatter);
        return java.sql.Timestamp.valueOf(localDateTime);
    }

    public static String formatLocalDateTime(LocalDateTime dateTime, String format) {
        DateTimeFormatter formatter = dateTimeFormatters.get(format);
        if (formatter == null) {
            formatter = DateTimeFormatter.ofPattern(format);
        }
        return dateTime.format(formatter);
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String format) {
        DateTimeFormatter formatter = dateTimeFormatters.get(format);
        if (formatter == null) {
            formatter = DateTimeFormatter.ofPattern(format);
        }
        return LocalDateTime.parse(ensureLeadingZeros(dateTimeStr), formatter);
    }

    public static String ensureLeadingZeros(String dateStr) {
        String[] parts = dateStr.split("[-/ :]");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].length() == 1) {
                parts[i] = "0" + parts[i];
            }
        }
        return String.join("-", parts);
    }

    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        String dateWithZero = DateUtil.ensureLeadingZeros(date.format(DateTimeFormatter.ofPattern("d-M-yyyy")));

        LocalDate parsedDate = DateUtil.parseLocalDate(dateWithZero, "dd-MM-yyyy");
        System.out.println(DateUtil.formatLocalDate(parsedDate, "dd/MM/yyyy"));
    }
}
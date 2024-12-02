package fields;

import utils.DateUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.*;

public final class DateField extends JPanel {
    private final JComboBox<String> dayComboBox = new JComboBox<>();
    private final JComboBox<String> monthComboBox = new JComboBox<>();
    private final JComboBox<String> yearComboBox = new JComboBox<>();

    public DateField() {
        this.initComponents();
    }

    public DateField(LocalDate selectedDate) {
        this.initComponents();

        this.setSelectedDate(DateUtil.formatLocalDate(selectedDate, "yyyy-MM-dd"));
    }

    public void initComponents() {
        int currentYear = LocalDate.now().getYear();

        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(String.valueOf(i));
        }

        for (int i = 1; i <= 12; i++) {
            monthComboBox.addItem(String.valueOf(i));
        }

        for (int i = 1900; i <= currentYear; i++) {
            yearComboBox.addItem(String.valueOf(i));
        }

        yearComboBox.setSelectedItem(String.valueOf(currentYear));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(dayComboBox)
                                .addComponent(monthComboBox)
                                .addComponent(yearComboBox)
                        )
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(dayComboBox)
                                        .addComponent(monthComboBox)
                                        .addComponent(yearComboBox)
                                )
                        )
        );
    }

    public void setSelectedDate(String date) {
        try {
            Date defaultDate = DateUtil.parseDate(date, "yyyy-MM-dd");
            String formatedDate = DateUtil.formatDate(defaultDate, "dd-MM-yyyy");
            String[] dateArray = formatedDate.split("-");

            dayComboBox.setSelectedItem(String.valueOf(Integer.parseInt(dateArray[0])));
            monthComboBox.setSelectedItem(String.valueOf(Integer.parseInt(dateArray[1])));
            yearComboBox.setSelectedItem(dateArray[2]);

        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
        }
    }

    public LocalDate getDate() {
        StringBuilder date = new StringBuilder();

        date.append(dayComboBox.getSelectedItem()).append("-");
        date.append(DateUtil.ensureLeadingZeros((String) monthComboBox.getSelectedItem())).append("-");
        date.append(DateUtil.ensureLeadingZeros((String) yearComboBox.getSelectedItem()));

        return DateUtil.parseLocalDate(date.toString(), "dd-MM-yyyy");
    }

    public String getDateToString() {
        return DateUtil.formatLocalDate(this.getDate(), "dd-MM-yyyy");
    }
}
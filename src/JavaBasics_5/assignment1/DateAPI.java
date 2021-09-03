package JavaBasics_5.assignment1;

import java.util.*;
import java.time.*;
import java.util.stream.*;

public class DateAPI {
    /**
     * @param date
     * @return Date that corresponds to the previous thursday.
     */
    public static LocalDate getPreviousThursday(LocalDate date) {
        return date.minusDays(DayOfWeek.from(date).getValue() + 3);
    }

    /**
     * @param year
     * @return list of the lengths of all the months in a year.
     */
    public static List<Integer> getMonthLengthsFromYear(int year) {
        return List.of(Month.values()).stream()
            .map((month) -> YearMonth.of(year, month).lengthOfMonth())
            .collect(Collectors.toList());
    }

    /**
     * @param month
     * @return Count of all the mondays in a month.
     */
    public static int getMondaysCountFromMonth(int month) {
        YearMonth currentMonth = YearMonth.of(Year.now().getValue(), month);

        LocalDate start = currentMonth.atDay(1);
        LocalDate end = currentMonth.atDay(currentMonth.lengthOfMonth() - 1);

        return start.datesUntil(end)
            .filter(day -> day.getDayOfWeek().equals(DayOfWeek.MONDAY))
            .collect(Collectors.toList())
            .size();
    }

    /**
     * @param date
     * @return true if the date is on Friday the 13th, false otherwise.
     */
    public static boolean isDateFridayThe13th(LocalDate date) {
        return (date.getDayOfWeek() == DayOfWeek.FRIDAY && date.getDayOfMonth() == 13);
    }
}

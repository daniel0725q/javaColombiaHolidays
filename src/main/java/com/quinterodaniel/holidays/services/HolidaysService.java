package com.quinterodaniel.holidays.services;

import com.quinterodaniel.holidays.entities.Holiday;
import com.quinterodaniel.holidays.repositories.HolidayRepository;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HolidaysService {
    private Map<Integer, List<String>> holidaysByYear = new HashMap<>();

    private final HolidayRepository holidayRepository;

    public HolidaysService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    private List<String> getByYear(int year) {
        if (holidaysByYear.containsKey(year)) {
            return holidaysByYear.get(year);
        }
        Iterable<Holiday> holidayDates = holidayRepository.findAll();
        List<String> holidays = new ArrayList<>();
        holidayDates.forEach(holiday -> {
            if (holiday.getType().getId() == 1) {
                holidays.add("" + (holiday.getMonth() - 1) + ":" + holiday.getDay());
            } else if (holiday.getType().getId() == 2) {
                holidays.add(calculateEmiliani(year, holiday.getMonth() - 1, holiday.getDay()));
            } else if (holiday.getType().getId() == 3) {
                holidays.add(calculateOtherHoliday(year, holiday.getEaster(), false));
            }  else if (holiday.getType().getId() == 4) {
                holidays.add(calculateOtherHoliday(year, holiday.getEaster(), true));
            }
        });

        holidaysByYear.put(year, holidays);
        return holidays;
    }

    private int getEasterMonth(int year) {
        int easterMonth;
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int g = (8 * b + 13) / 25;
        int h = (19 * a + b - d - g + 15) % 30;
        int j = c / 4;
        int k = c % 4;
        int m = (a + 11 * h) / 319;
        int r = (2 * e + 2 * j - k - h + m + 32) % 7;
        easterMonth = (h - m + r + 90) / 25;
        easterMonth--;
        return easterMonth;
    }

    private int getEasterDay(int year) {
        int easterMonth;
        int easterDay;
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int g = (8 * b + 13) / 25;
        int h = (19 * a + b - d - g + 15) % 30;
        int j = c / 4;
        int k = c % 4;
        int m = (a + 11 * h) / 319;
        int r = (2 * e + 2 * j - k - h + m + 32) % 7;
        easterMonth = (h - m + r + 90) / 25;
        easterDay = (h - m + r + easterMonth + 19) % 32;
        return easterDay;
    }

    private String calculateOtherHoliday(int year, int days, boolean emiliani) {
        Calendar date = Calendar.getInstance();
        date.set(year, getEasterMonth(year), getEasterDay(year));
        date.add(Calendar.DATE, days);
        if (emiliani) {
            return this.calculateEmiliani(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
        } else {
            return (date.get(Calendar.MONTH) + ":" + date.get(Calendar.DATE));
        }
    }

    private String calculateEmiliani(int year, int month, int day) {
        Calendar date = Calendar.getInstance();
        date.set(year, month, day);
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                date.add(Calendar.DATE, 1);
                break;
            case 3:
                date.add(Calendar.DATE, 6);
                break;
            case 4:
                date.add(Calendar.DATE, 5);
                break;
            case 5:
                date.add(Calendar.DATE, 4);
                break;
            case 6:
                date.add(Calendar.DATE, 3);
                break;
            case 7:
                date.add(Calendar.DATE, 2);
                break;
            default:
                break;
        }
        return (date.get(Calendar.MONTH) + ":" + date.get(Calendar.DATE));
    }

    public boolean isHoliday(int year, int month, int day) {
        List<String> holidays = getByYear(year);
        return holidays.contains(month + ":" + day);
    }

    public boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}

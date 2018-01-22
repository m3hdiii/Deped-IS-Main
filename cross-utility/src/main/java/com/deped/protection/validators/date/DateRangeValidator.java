package com.deped.protection.validators.date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateRangeValidator implements ConstraintValidator<DateRange, Date> {
    private Date dateFromGl;
    private Date dateToGl;
    private boolean isRequired;

    @Override
    public void initialize(DateRange dateRange) {
        this.isRequired = dateRange.required();

        int dayFrom = dateRange.dayFrom();
        int monthFrom = dateRange.monthFrom();
        int yearFrom = dateRange.yearFrom();

        int dayTo = dateRange.dayTo();
        int monthTo = dateRange.monthTo();
        int yearTo = dateRange.yearTo();


        if (dayTo == -1 && monthTo == -1 && yearTo == -1) {
            LocalDateTime now = LocalDateTime.now();
            yearTo = now.getYear();
            monthTo = now.getMonthValue();
            dayTo = now.getDayOfMonth();
        }

        if (dayTo == -1 || monthTo == -1 || yearTo == -1) {
            throw new IllegalArgumentException("All year, month and day must be defined OR ELSE none of them");
        }

        Calendar dateFrom = new GregorianCalendar(yearFrom, monthFrom, dayFrom);
        Calendar dateTo = new GregorianCalendar(yearTo, monthTo, dayTo);
        this.dateFromGl = dateFrom.getTime();
        this.dateToGl = dateTo.getTime();
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            if (isRequired) {
                return false;
            } else {
                return true;
            }
        }

        return (date.after(dateFromGl) && date.before(dateToGl));
    }
}

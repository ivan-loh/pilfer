package com.eriad.app.stuff;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Iterator;

/**
 * Created by ivan on 5/17/14.
 */
public class ICGenerator implements Iterator<String> {

    public static final int[] SELANGOR = {10, 41, 42, 43, 44};

    public static void main(String[] args) {

        ICGenerator Selangor = new ICGenerator(1986, 35);

        while (Selangor.hasNext()) {
            System.out.println(Selangor.next());
        }
    }

    private final DateTimeFormatter FORMAT = DateTimeFormat.forPattern("yyMMdd");

    private final DateTime beginDate;
    private final DateTime endDate;
    private final int stateCode;

    private DateTime activeDate;
    private int activeCount;

    public ICGenerator(int year, int stateCode) {

        this.beginDate   = new DateTime().withTime(0,0,0,0).withDayOfYear(1).withYear(year);
        this.endDate     = new DateTime().withTime(0,0,0,0).withDayOfYear(1).withYear((year + 1));
        this.stateCode   = stateCode;
        this.activeDate  = beginDate;
        this.activeCount = 1;

    }

    public String getYear() {
        return FORMAT.print(beginDate);
    }

    public int getStateCode() {
        return stateCode;
    }

    @Override
    public boolean hasNext() {
        return activeDate.isBefore(endDate);
    }

    @Override
    public String next() {

        String result = FORMAT.print(activeDate) + stateCode + String.format("%04d", activeCount);

        activeCount = activeCount + 1;
        if (activeCount > 9999) {
            activeCount = 1;
            activeDate = activeDate.plusDays(1);
        }

        return result;
    }
}

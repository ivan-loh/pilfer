package com.eriad.app.stuff;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Iterator;

public class ICGenerator implements Iterator<String> {

    private final DateTimeFormatter FORMAT = DateTimeFormat.forPattern("yyMMdd");

    private final DateTime beginDate;
    private final DateTime endDate;
    private final int      stateCode;
    private final String   id;

    private DateTime activeDate;
    private int      activeCount;

    public ICGenerator(int year, int stateCode) {
        this.beginDate   = new DateTime().withTime(0,0,0,0).withDayOfYear(1).withYear(year);
        this.endDate     = new DateTime().withTime(0,0,0,0).withDayOfYear(1).withYear((year + 1));
        this.stateCode   = stateCode;
        this.id          = FORMAT.print(beginDate) + stateCode;

        this.activeDate  = beginDate;
        this.activeCount = 1;
    }

    public String getGeneratorId() { return id; }
    public int    getActiveCount() { return activeCount; }
    public Date   getActiveDate()  { return activeDate.toDate(); }

    public String getYear()        { return FORMAT.print(beginDate); }
    public int    getStateCode()   { return stateCode; }

    @Override
    public boolean hasNext() { return activeDate.isBefore(endDate); }

    public void moveTo(Date activeDate, int activeCount) {
        this.activeDate  = new DateTime(activeDate).withTime(0, 0, 0, 0);
        this.activeCount = activeCount;
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

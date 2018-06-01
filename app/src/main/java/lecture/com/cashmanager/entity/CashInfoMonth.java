package lecture.com.cashmanager.entity;

import java.util.List;

/**
 * Project: CashManager
 * Created by DuongHV.
 * Copyright (c) 2018 - HUST.
 */
public class CashInfoMonth {

    List<String> day;

    public CashInfoMonth(List<String> day) {
        this.day = day;
    }

    public List<String> getDay() {
        return day;
    }

    public void setDay(List<String> day) {
        this.day = day;
    }

}

package lecture.com.cashmanager.entity;

/**
 * Project: CashManager
 * Created by DuongHV.
 * Copyright (c) 2018 - HUST.
 */
public class CashReportBar {
    int month;
    int amount;

    public CashReportBar(int month, int amount) {
        this.month = month;
        this.amount = amount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

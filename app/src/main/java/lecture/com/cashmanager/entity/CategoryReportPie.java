package lecture.com.cashmanager.entity;

/**
 * Project: CashManager
 * Created by DuongHV.
 * Copyright (c) 2018 - HUST.
 */
public class CategoryReportPie {
    private int categoryid;
    private String categoryName;
    private int amount;

    public CategoryReportPie() {
    }

    public CategoryReportPie(int categoryid, String categoryName, int amount) {
        this.categoryid = categoryid;
        this.categoryName = categoryName;
        this.amount = amount;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

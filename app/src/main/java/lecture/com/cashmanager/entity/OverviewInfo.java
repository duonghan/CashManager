package lecture.com.cashmanager.entity;

/**
 * Project: CashManager
 * Created by DuongHV.
 * Copyright (c) 2018 - HUST.
 */
public class OverviewInfo {
    private int inflow;
    private int outflow;
    private int summary;

    public OverviewInfo(int inflow, int outflow, int summary) {
        this.inflow = inflow;
        this.outflow = outflow;
        this.summary = summary;
    }

    public OverviewInfo() {
        this.inflow = 0;
        this.outflow = 0;
        this.summary = 0;
    }

    public int getInflow() {
        return inflow;
    }

    public void setInflow(int inflow) {
        this.inflow = inflow;
    }

    public int getOutflow() {
        return outflow;
    }

    public void setOutflow(int outflow) {
        this.outflow = outflow;
    }

    public int getSummary() {
        return summary;
    }

    public void setSummary(int summary) {
        this.summary = summary;
    }
}

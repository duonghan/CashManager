package lecture.com.cashmanager.entity;

public class CashInfo {
    private int value;
    private String category;
    private String description;
    private String date;

    public CashInfo(int value, String category, String description, String date) {
        this.value = value;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public CashInfo() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

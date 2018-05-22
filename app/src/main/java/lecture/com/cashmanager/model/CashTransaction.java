package lecture.com.cashmanager.model;

import java.io.Serializable;
import java.util.Date;

public class CashTransaction implements Serializable{
    private int id;
    private int userid;
    private int categoryid;
    private int value;
    private String description;
    private String created;
    private String modified;

    public CashTransaction() {
    }

    public CashTransaction(int userid, int categoryid, int value, String description, String created, String modified) {
        this.userid = userid;
        this.categoryid = categoryid;
        this.value = value;
        this.description = description;
        this.created = created;
        this.modified = modified;
    }

    public CashTransaction(int id, int userid, int categoryid, int value, String description, String created, String modified) {
        this.id = id;
        this.userid = userid;
        this.categoryid = categoryid;
        this.value = value;
        this.description = description;
        this.created = created;
        this.modified = modified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}

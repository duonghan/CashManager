package lecture.com.cashmanager.model;

import java.util.Date;

public class CashTransaction {
    private int id;
    private int userid;
    private int categoryid;
    private int value;
    private String description;
    private String type;
    private Date created;
    private Date modified;

    public CashTransaction(int id, int userid, int categoryid, int value, String description, String type, Date created, Date modified) {
        this.id = id;
        this.userid = userid;
        this.categoryid = categoryid;
        this.value = value;
        this.description = description;
        this.type = type;
        this.created = created;
        this.modified = modified;
    }

    public CashTransaction(int id, int userid, int categoryid, int value, String description, String type) {
        this.id = id;
        this.userid = userid;
        this.categoryid = categoryid;
        this.value = value;
        this.description = description;
        this.type = type;
    }

    public CashTransaction(int userid, int categoryid, int value, String description, String type, Date created, Date modified) {
        this.userid = userid;
        this.categoryid = categoryid;
        this.value = value;
        this.description = description;
        this.type = type;
        this.created = created;
        this.modified = modified;
    }

    public CashTransaction() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}

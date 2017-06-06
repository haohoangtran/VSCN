package lachongmedia.vn.nfc.database.models;

import java.util.Date;

/**
 * Created by hao on 6/4/17.
 */

public class PlanWork {
    private String name;
    private Date date;

    @Override
    public String toString() {
        return "PlanWork{" +
                "name='" + name + '\'' +
                ", date=" + date +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PlanWork(String name, Date date) {

        this.name = name;
        this.date = date;
    }
}

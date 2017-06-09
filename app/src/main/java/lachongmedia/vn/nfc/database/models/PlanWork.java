package lachongmedia.vn.nfc.database.models;

import java.util.Date;

import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;

/**
 * Created by hao on 6/4/17.
 */

public class PlanWork {
    private Date date;
    private int isCompleted;
    private Dsdiadiem dsdiadiem;

    public Dsdiadiem getDsdiadiem() {
        return dsdiadiem;
    }

    public void setDsdiadiem(Dsdiadiem dsdiadiem) {
        this.dsdiadiem = dsdiadiem;
    }

    public int isCompleted() {
        return isCompleted;
    }

    public void setCompleted(int completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return "PlanWork{" +
                "date=" + date +
                ", isCompleted=" + isCompleted +
                ", dsdiadiem=" + dsdiadiem +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PlanWork(Dsdiadiem dsdiadiem, Date date) {
        this.isCompleted = 0;
        this.date = date;
        this.dsdiadiem = dsdiadiem;
    }
}

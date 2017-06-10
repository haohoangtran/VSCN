package lachongmedia.vn.nfc.database.realm.realm_models;

import java.util.Date;

import io.realm.RealmObject;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;

/**
 * Created by hao on 6/4/17.
 */

public class PlanWork extends RealmObject {
    private String date;
    private int isCompleted;
    private Dsdiadiem dsdiadiem;

    public PlanWork() {
    }

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
        return Utils.stringToDate(date);
    }

    public void setDate(Date date) {
        this.date = Utils.dateToString(date);
    }

    public PlanWork(Dsdiadiem dsdiadiem, Date date) {
        this.isCompleted = 0;
        this.date = Utils.dateToString(date);
        this.dsdiadiem = dsdiadiem;
    }
}

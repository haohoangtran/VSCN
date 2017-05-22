package lachongmedia.vn.nfc.database.realm.realm_models;

import java.util.Date;

import io.realm.RealmObject;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;
import lachongmedia.vn.nfc.database.respon.login.Nhanvien;

/**
 * Created by tranh on 5/19/2017.
 */

public class DiaDiemSave extends RealmObject {
    private Dsdiadiem dsdiadiem;
    private String time;
    private Nhanvien nhanvien;

    public DiaDiemSave(Dsdiadiem dsdiadiem, Nhanvien nhanvien) {
        this.dsdiadiem = dsdiadiem;
        this.time = Utils.dateToString(new Date());
        this.nhanvien = nhanvien;

    }

    public DiaDiemSave() {

    }

    @Override
    public String toString() {
        return "DiaDiemSave{" +
                "dsdiadiem=" + dsdiadiem +
                ", time='" + time + '\'' +
                ", nhanvien=" + nhanvien +
                '}';
    }

    public Nhanvien getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(Nhanvien nhanvien) {
        this.nhanvien = nhanvien;
    }

    public Dsdiadiem getDsdiadiem() {
        return dsdiadiem;
    }

    public void setDsdiadiem(Dsdiadiem dsdiadiem) {
        this.dsdiadiem = dsdiadiem;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

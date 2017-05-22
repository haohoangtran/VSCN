package lachongmedia.vn.nfc.database.realm.realm_models;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.database.respon.login.Nhanvien;

/**
 * Created by tranh on 5/17/2017.
 */

public class DateString extends RealmObject {
    private String strDate;
    private int idPerson;
    private int type;

    /*
    * 0 là bắt đầu
    * 1 là kết thúc
    * */
    public DateString() {
    }

    public DateString(Date date, int idPerson, int type) {
        if (type != 0 && type != 1) {
            Log.e("Lỗi", "DateString: Lỗi rồi đấy, loại chỉ 0 và 1");
        }
        this.strDate = Utils.dateToString(date);
        this.idPerson = idPerson;
        Log.e("vcsc", String.format("DateString: %s", Utils.stringToDate(strDate)));
    }

    @Override
    public String toString() {
        return "DateString{" +
                "strDate='" + strDate + '\'' +
                ", idPerson='" + idPerson + '\'' +
                ", type=" + type +
                '}';
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

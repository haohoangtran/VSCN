package lachongmedia.vn.nfc.database.realm.realm_models;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;
import lachongmedia.vn.nfc.Utils;

/**
 * Created by tranh on 5/17/2017.
 */

public class DateString extends RealmObject {
    private String strDate;

    public DateString() {
    }

    public DateString(Date date) {
        this.strDate = Utils.dateToString(date);
        Log.e("vcsc", String.format("DateString: %s", Utils.stringToDate(strDate)));
    }
}

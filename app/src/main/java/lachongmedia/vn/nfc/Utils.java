package lachongmedia.vn.nfc;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import lachongmedia.vn.nfc.database.models.Place;
import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by hao on 28/04/2017.
 */

public class Utils {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static String byteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String out = "";

        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

    public static long getTime(Date date1, Date date2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        int days, hours, min;

        long difference = date2.getTime() - date1.getTime();
        days = (int) (difference / (1000 * 60 * 60 * 24));
        hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);
        Log.i("======= Hours", " :: " + hours);
        return days * 1440 + hours * 60 + min;
    }

    public static String getTime(Date date) {
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = localDateFormat.format(date);
        return time;
    }

    public static String dateToString(Date date) {
        String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String stringDate = sdf.format(date);
        return stringDate;
    }

    public static Date stringToDate(String str) {
        String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static RequestBody createRequestBody(@NonNull File file) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), file);
    }

    public static RequestBody createRequestBody(@NonNull String s) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), s);
    }

    //"7,7.5,9,9.5"
    public static String getTimeWorkPlace(Dsdiadiem diadiem) {
        StringBuilder timeReturn = new StringBuilder();
        String time = diadiem.getThoigianlamviec();
        String[] str = time.split(",");
        if (str.length != 0) {
            for (int i = 0; i < str.length; i++) {
                if (str[i] == null || str[i].isEmpty())
                    continue;
                float hour = Float.valueOf(str[i]);
                int minute = (int) (hour * 60);
                int hours = minute / 60;
                int minutes = minute % 60;
                String s;
                if (minutes < 10) {
                    s = "0" + minutes;
                } else {
                    s = minutes + "";
                }
                timeReturn.append(hours).append(':').append(s).append(", ");
            }
        }
        return timeReturn.toString();
    }

    public static List<Place> getPlaceFromDiaDiem(Dsdiadiem diadiem) {
        List<Place> places = new Vector<>();
        String t = diadiem.getThoigianlamviec();
        String[] times = t.split(",");
        for (int i = 0; i < times.length; i++) {
            if (times[i] != null || times[i].isEmpty()) {
                continue;
            }
            float hour = Float.valueOf(times[i]);
            int minute = (int) (hour * 60);
            Place place = new Place(diadiem);
            place.setMinuteInDay(minute);
            places.add(place);
        }
        return places;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}

package lachongmedia.vn.nfc;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hao on 28/04/2017.
 */

public class Utils {
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
    public static long getTime(Date date1,Date date2){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        int days,hours,min;

        long difference = date2.getTime() - date1.getTime();
        days = (int) (difference / (1000*60*60*24));
        hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
        min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
        hours = (hours < 0 ? -hours : hours);
        Log.i("======= Hours"," :: "+hours);
        return days*1440+hours*60+min;
    }
    public static String getTime(Date date){
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = localDateFormat.format(date);
        return time;
    }
}

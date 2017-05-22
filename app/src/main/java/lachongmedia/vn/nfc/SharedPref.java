package lachongmedia.vn.nfc;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hao on 28/04/2017.
 */

public class SharedPref {
    private static final String ID_USER = "iduser";
    private static final String CHECK_ID = "checkida";
    public static SharedPref instance;
    private final String KEY_PREF = "Predasdas";
    private SharedPreferences sharedPreferences;

    private SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(
                KEY_PREF, Context.MODE_PRIVATE
        );
    }

    public static void setInstance(Context context) {
        SharedPref.instance = new SharedPref(context);
    }

    public void logout() {
        sharedPreferences.edit().clear().apply();
    }


    public void putIDUser(int id) {
        sharedPreferences.edit().putInt(ID_USER, id).apply();
    }

    public int getIDUser() {
        return sharedPreferences.getInt(ID_USER, -1);
    }

    public void putCheckID(String id) {
        sharedPreferences.edit().putString(CHECK_ID, id).apply();
    }

    public String getCheckId() {
        return sharedPreferences.getString(CHECK_ID, null);
    }
}

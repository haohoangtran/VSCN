package lachongmedia.vn.nfc.database.realm;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import lachongmedia.vn.nfc.database.respon.login.LoginRespon;
import lachongmedia.vn.nfc.database.respon.login.Site;

/**
 * Created by tranh on 5/15/2017.
 */

public class RealmDatabase {
    public static final RealmDatabase instance = new RealmDatabase();
    private static final String TAG = RealmDatabase.class.getSimpleName();
    private Realm realm;

    public RealmDatabase() {

        this.realm = Realm.getDefaultInstance();
    }

    public void insertOrUpdateLogin(LoginRespon loginRespon) {
        this.realm = Realm.getDefaultInstance();
        final RealmResults<LoginRespon> loginRespons = realm.where(LoginRespon.class).findAll();
        realm.beginTransaction();
        loginRespons.deleteAllFromRealm();
        realm.insertOrUpdate(loginRespon);
        realm.commitTransaction();
    }

    public void inserthi(Site site) {
        realm.beginTransaction();
        realm.insertOrUpdate(site);
        realm.commitTransaction();
    }

    public void gethi() {
        final RealmResults<Site> st = realm.where(Site.class).findAll();
        if (st.size() != 0) {
            Log.e(TAG, String.format("gethi: %s", st.get(0)));
        }
    }

    public LoginRespon getLoginRespon() {
        this.realm = Realm.getDefaultInstance();
        final RealmResults<LoginRespon> loginRespons = realm.where(LoginRespon.class).findAll();
        Log.e("hihi", String.format("getLoginRespon: %s", loginRespons.size()));
        if (loginRespons.size() != 0)
            if (loginRespons.get(0) != null) {
                Log.e("hihi", String.format("getLoginRespon: %s", loginRespons.get(0)));
                return loginRespons.get(0);
            }
        return null;
    }

}

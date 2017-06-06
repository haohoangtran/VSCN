package lachongmedia.vn.nfc.database.realm;

import android.util.Log;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import io.realm.Realm;
import io.realm.RealmResults;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.realm.realm_models.DateString;
import lachongmedia.vn.nfc.database.realm.realm_models.DiaDiemSave;
import lachongmedia.vn.nfc.database.realm.realm_models.RealmString;
import lachongmedia.vn.nfc.database.respon.login.Dschecklist;
import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;
import lachongmedia.vn.nfc.database.respon.login.LoginRespon;
import vn.lachongmedia.ksmartg.chupanhlibrary.activities.ChupAnhActivity;

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

        List<Dsdiadiem> dsdiadiems = new Vector<>();
        for (int i = 0; i < loginRespon.getKehoach().getSite().getDsmatbang().size(); i++) {
            for (int i1 = 0; i1 < loginRespon.getKehoach().getSite().getDsmatbang().get(i).getDsdiadiem().size(); i1++) {
                dsdiadiems.add(loginRespon.getKehoach().getSite().getDsmatbang().get(i).getDsdiadiem().get(i1));
            }
        }
        for (Dsdiadiem dsdiadiem : dsdiadiems) {
            Log.e(TAG, "onResponse: " + dsdiadiem.toString());
        }
        DbContext.instance.setListDiaDiemAll(dsdiadiems);
    }

    public void addToRealmDateString(DateString dateString) {
        this.realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(dateString);
        realm.commitTransaction();
        Log.e(TAG, String.format("addToRealmDateString: thêm %s", dateString));
    }

    public Date getDateStringStartFromRealm(int idnv) {
        RealmResults<DateString> result = realm.where(DateString.class).equalTo("idPerson", idnv).findAll();
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getIdPerson() == idnv && result.get(i).getType() == 0) {
                return Utils.stringToDate(result.get(i).getStrDate());
            }
        }
        return null;
    }

    public Date getDateStringEndFromRealm(int idnv) {
        RealmResults<DateString> result = realm.where(DateString.class).equalTo("idPerson", idnv).findAll();
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getIdPerson() == idnv && result.get(i).getType() == 1) {
                return Utils.stringToDate(result.get(i).getStrDate());
            }
        }
        return null;
    }

    public void removeFromRealm(String dateString) {
        this.realm = Realm.getDefaultInstance();
        RealmResults<DateString> result = realm.where(DateString.class).equalTo("strDate", dateString).findAll();
        realm.beginTransaction();
        result.deleteAllFromRealm();
        realm.commitTransaction();
        Log.e(TAG, "removeFromRealm: thành công");
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

    public List<DiaDiemSave> getDiaDiemSave() {
        this.realm = Realm.getDefaultInstance();
        final RealmResults<DiaDiemSave> diaDiemSaves = realm.where(DiaDiemSave.class).findAll();
        Log.e(TAG, "getDiaDiemSave: " + diaDiemSaves.size());
        return diaDiemSaves;
    }

    public void setPathsImage(Dschecklist dschecklist, List<String> paths) {
        this.realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (int i = 0; i < ChupAnhActivity.pathsList.size(); i++) {
            dschecklist.getPathImages().add(new RealmString(ChupAnhActivity.pathsList.get(i)));
        }
        realm.commitTransaction();
    }


    public void saveDiaDiemSave(DiaDiemSave diaDiemSave) {
        this.realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(diaDiemSave);
        realm.commitTransaction();
        Log.e(TAG, String.format("saveDiaDiemSave: thêm %s", diaDiemSave));
    }

    public void updateDsCheckList(Dschecklist dschecklist, int type) {
        this.realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        dschecklist.setTrangthai(type);
        realm.commitTransaction();
    }
}

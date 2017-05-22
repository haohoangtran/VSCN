package lachongmedia.vn.nfc.database;

import java.util.List;
import java.util.Vector;

import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.respon.login.Diadiem;
import lachongmedia.vn.nfc.database.respon.login.Dshuongdan;
import lachongmedia.vn.nfc.database.respon.login.LoginRespon;
import lachongmedia.vn.nfc.database.respon.login.Matbang;

/**
 * Created by hao on 28/04/2017.
 */

public class DbContext {
    public static final DbContext instance = new DbContext();
    private List<Diadiem> diadiems;
    private List<Dshuongdan> dshuongdanList;
    private List<String> pathImageIssue;
    private Vector<String> paths;

    private DbContext() {
        diadiems = new Vector<>();
        pathImageIssue = new Vector<>();
    }

    public Vector<String> getPaths() {
        return paths;
    }

    public void setPaths(Vector<String> paths) {
        this.paths = paths;
    }

    public Matbang findMbbyId(int id) {
        LoginRespon loginRespon = RealmDatabase.instance.getLoginRespon();
        for (int i = 0; i < loginRespon.getKehoach().getDsmatbang().size(); i++) {
            if (loginRespon.getKehoach().getDsmatbang().get(i).getMatbang().getId() == id) {
                return loginRespon.getKehoach().getDsmatbang().get(i).getMatbang();
            }
        }
        return null;
    }

    public List<Diadiem> getDiadiems() {
        return diadiems;
    }

    public void setDiadiems(List<Diadiem> diadiems) {
        this.diadiems = diadiems;
    }

    public List<Dshuongdan> getDshuongdanList() {
        return dshuongdanList;
    }

    public void setDshuongdanList(List<Dshuongdan> dshuongdanList) {
        this.dshuongdanList = dshuongdanList;
    }

    public List<String> getPathImageIssue() {
        return pathImageIssue;
    }
}

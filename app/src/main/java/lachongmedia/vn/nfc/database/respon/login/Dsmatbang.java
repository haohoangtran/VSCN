package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Dsmatbang extends RealmObject {
    @SerializedName("id")
    private int id;
    @SerializedName("tenmatbang")
    private String tenmatbang;
    @SerializedName("anhmatbang")
    private Anhmatbang anhmatbang;
    @SerializedName("dsdiadiem")
    private RealmList<Dsdiadiem> dsdiadiem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenmatbang() {
        return tenmatbang;
    }

    public void setTenmatbang(String tenmatbang) {
        this.tenmatbang = tenmatbang;
    }

    public Anhmatbang getAnhmatbang() {
        return anhmatbang;
    }

    public void setAnhmatbang(Anhmatbang anhmatbang) {
        this.anhmatbang = anhmatbang;
    }

    public RealmList<Dsdiadiem> getDsdiadiem() {
        return dsdiadiem;
    }

    public void setDsdiadiem(RealmList<Dsdiadiem> dsdiadiem) {
        this.dsdiadiem = dsdiadiem;
    }
}

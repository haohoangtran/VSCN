package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Dsmatbang extends RealmObject {
    @SerializedName("matbang")
    private Matbang matbang;
    @SerializedName("anhmatbang")
    private Anhmatbang anhmatbang;

    public Matbang getMatbang() {
        return matbang;
    }

    public void setMatbang(Matbang matbang) {
        this.matbang = matbang;
    }

    @Override
    public String toString() {
        return "Dsmatbang{" +
                "matbang=" + matbang +
                ", anhmatbang=" + anhmatbang +
                '}';
    }

    public Anhmatbang getAnhmatbang() {
        return anhmatbang;
    }

    public void setAnhmatbang(Anhmatbang anhmatbang) {
        this.anhmatbang = anhmatbang;
    }
}

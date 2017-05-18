package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Matbang extends RealmObject {
    @SerializedName("id")
    private int id;
    @SerializedName("tenmatbang")
    private String tenmatbang;

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

    @Override
    public String toString() {
        return "Matbang{" +
                "id=" + id +
                ", tenmatbang='" + tenmatbang + '\'' +
                '}';
    }
}

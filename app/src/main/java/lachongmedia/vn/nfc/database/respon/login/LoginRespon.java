package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by hao on 6/2/17.
 */

public class LoginRespon extends RealmObject {

    @SerializedName("nhanvien")
    private Nhanvien nhanvien;
    @SerializedName("kehoach")
    private Kehoach kehoach;

    public Nhanvien getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(Nhanvien nhanvien) {
        this.nhanvien = nhanvien;
    }

    public Kehoach getKehoach() {
        return kehoach;
    }

    public void setKehoach(Kehoach kehoach) {
        this.kehoach = kehoach;
    }
}

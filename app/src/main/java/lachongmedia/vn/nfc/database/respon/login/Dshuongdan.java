package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Dshuongdan extends RealmObject {
    @SerializedName("id")
    private int id;
    @SerializedName("id_diadiem")
    private int idDiadiem;
    @SerializedName("path")
    private String path;
    @SerializedName("noidung")
    private String noidung;
    @SerializedName("yeucau")
    private String yeucau;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDiadiem() {
        return idDiadiem;
    }

    public void setIdDiadiem(int idDiadiem) {
        this.idDiadiem = idDiadiem;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getYeucau() {
        return yeucau;
    }

    public void setYeucau(String yeucau) {
        this.yeucau = yeucau;
    }
}

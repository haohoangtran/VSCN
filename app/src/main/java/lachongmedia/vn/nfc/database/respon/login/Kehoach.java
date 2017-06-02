package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Kehoach extends RealmObject {
    @SerializedName("id")
    private int id;
    @SerializedName("id_nhanvien")
    private int idNhanvien;
    @SerializedName("id_site")
    private int idSite;
    @SerializedName("site")
    private Site site;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdNhanvien() {
        return idNhanvien;
    }

    public void setIdNhanvien(int idNhanvien) {
        this.idNhanvien = idNhanvien;
    }

    public int getIdSite() {
        return idSite;
    }

    public void setIdSite(int idSite) {
        this.idSite = idSite;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}

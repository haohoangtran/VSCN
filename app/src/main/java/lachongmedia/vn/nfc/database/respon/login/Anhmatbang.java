package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Anhmatbang extends RealmObject {
    @SerializedName("imageid")
    private int imageid;
    @SerializedName("id_matbang")
    private int idMatbang;
    @SerializedName("id_checklist")
    private int idChecklist;
    @SerializedName("path")
    private String path;
    @SerializedName("ghichu")
    private String ghichu;

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public int getIdMatbang() {
        return idMatbang;
    }

    public void setIdMatbang(int idMatbang) {
        this.idMatbang = idMatbang;
    }

    public int getIdChecklist() {
        return idChecklist;
    }

    public void setIdChecklist(int idChecklist) {
        this.idChecklist = idChecklist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}

package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Site extends RealmObject {
    @SerializedName("id")
    private int id;
    @SerializedName("tensite")
    private String tensite;
    @SerializedName("diachi")
    private String diachi;
    @SerializedName("sodienthoai")
    private String sodienthoai;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensite() {
        return tensite;
    }

    public void setTensite(String tensite) {
        this.tensite = tensite;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }
}

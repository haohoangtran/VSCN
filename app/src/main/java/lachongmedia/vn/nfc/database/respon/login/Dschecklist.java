package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Dschecklist extends RealmObject {
    @SerializedName("id")
    private int id;
    @SerializedName("id_diadiem")
    private int idDiadiem;
    @SerializedName("giobatdau")
    private double giobatdau;
    @SerializedName("thoigianlamviec")
    private double thoigianlamviec;
    @SerializedName("tenchecklist")
    private String tenchecklist;
    @SerializedName("phuongphap")
    private String phuongphap;
    @SerializedName("yeucau")
    private String yeucau;
    @SerializedName("trangthaichupanh")
    private boolean trangthaichupanh;
    @SerializedName("trangthai")
    private int trangthai;

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

    public double getGiobatdau() {
        return giobatdau;
    }

    public void setGiobatdau(double giobatdau) {
        this.giobatdau = giobatdau;
    }

    public double getThoigianlamviec() {
        return thoigianlamviec;
    }

    public void setThoigianlamviec(double thoigianlamviec) {
        this.thoigianlamviec = thoigianlamviec;
    }

    public String getTenchecklist() {
        return tenchecklist;
    }

    public void setTenchecklist(String tenchecklist) {
        this.tenchecklist = tenchecklist;
    }

    public String getPhuongphap() {
        return phuongphap;
    }

    public void setPhuongphap(String phuongphap) {
        this.phuongphap = phuongphap;
    }

    public String getYeucau() {
        return yeucau;
    }

    public void setYeucau(String yeucau) {
        this.yeucau = yeucau;
    }

    public boolean getTrangthaichupanh() {
        return trangthaichupanh;
    }

    public void setTrangthaichupanh(boolean trangthaichupanh) {
        this.trangthaichupanh = trangthaichupanh;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}

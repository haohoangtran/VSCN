package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Dschecklist extends RealmObject {
    @SerializedName("id")
    private int id;
    @SerializedName("id_diadiem")
    private int idDiadiem;
    @SerializedName("giobatdau")
    private float giobatdau;
    @SerializedName("thoigianlamviec")
    private double thoigianlamviec;
    @SerializedName("tenchecklist")
    private String tenchecklist;
    @SerializedName("trangthaichupanh")
    private boolean trangthaichupanh;
    @SerializedName("trangthai")
    private int trangthai;
    @SerializedName("phuongphap")
    private String phuongphap;
    @SerializedName("yeucau")
    private String yeucau;

    public boolean isTrangthaichupanh() {
        return trangthaichupanh;
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

    public float getGiobatdau() {
        return giobatdau;
    }

    public void setGiobatdau(float giobatdau) {
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

    @Override
    public String toString() {
        return "Dschecklist{" +
                "id=" + id +
                ", idDiadiem=" + idDiadiem +
                ", giobatdau=" + giobatdau +
                ", thoigianlamviec=" + thoigianlamviec +
                ", tenchecklist='" + tenchecklist + '\'' +
                ", trangthaichupanh=" + trangthaichupanh +
                ", trangthai=" + trangthai +
                ", phuongphap='" + phuongphap + '\'' +
                ", yeucau='" + yeucau + '\'' +
                '}';
    }
}

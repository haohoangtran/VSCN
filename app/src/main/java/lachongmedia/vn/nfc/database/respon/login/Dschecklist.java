package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import lachongmedia.vn.nfc.database.realm.realm_models.RealmString;

public class Dschecklist extends RealmObject {
    @SerializedName("id")
    private int id;
    @SerializedName("id_diadiem")
    private int idDiadiem;
    @SerializedName("giobatdau")
    private String giobatdau;
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
    private RealmList<RealmString> pathImages;

    public Dschecklist() {
        pathImages = new RealmList<>();
    }

    public boolean isTrangthaichupanh() {
        return trangthaichupanh;
    }

    public RealmList<RealmString> getPathImages() {
        return pathImages;
    }

    public void setPathImages(RealmList<RealmString> pathImages) {
        this.pathImages = pathImages;
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

    public String getGiobatdau() {
        return giobatdau;
    }

    public void setGiobatdau(String giobatdau) {
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

    @Override
    public String toString() {
        return "Dschecklist{" +
                "id=" + id +
                ", idDiadiem=" + idDiadiem +
                ", giobatdau=" + giobatdau +
                ", thoigianlamviec=" + thoigianlamviec +
                ", tenchecklist='" + tenchecklist + '\'' +
                ", phuongphap='" + phuongphap + '\'' +
                ", yeucau='" + yeucau + '\'' +
                ", trangthaichupanh=" + trangthaichupanh +
                ", trangthai=" + trangthai +
                ", pathImages=" + pathImages +
                '}';
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}

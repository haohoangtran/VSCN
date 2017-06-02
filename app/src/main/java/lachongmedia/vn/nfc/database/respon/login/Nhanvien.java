package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Nhanvien extends RealmObject {
    @SerializedName("ID_NhanVien")
    private int idNhanvien;
    @SerializedName("ID_TheNhanVien")
    private String idThenhanvien;
    @SerializedName("tenloainhanvien")
    private String tenloainhanvien;
    @SerializedName("path")
    private String path;
    @SerializedName("TenNhanVien")
    private String tennhanvien;
    @SerializedName("TenDangNhap")
    private String tendangnhap;
    @SerializedName("MatKhau")
    private String matkhau;

    public int getIdNhanvien() {
        return idNhanvien;
    }

    public void setIdNhanvien(int idNhanvien) {
        this.idNhanvien = idNhanvien;
    }

    public String getIdThenhanvien() {
        return idThenhanvien;
    }

    public void setIdThenhanvien(String idThenhanvien) {
        this.idThenhanvien = idThenhanvien;
    }

    public String getTenloainhanvien() {
        return tenloainhanvien;
    }

    public void setTenloainhanvien(String tenloainhanvien) {
        this.tenloainhanvien = tenloainhanvien;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTennhanvien() {
        return tennhanvien;
    }

    public void setTennhanvien(String tennhanvien) {
        this.tennhanvien = tennhanvien;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}

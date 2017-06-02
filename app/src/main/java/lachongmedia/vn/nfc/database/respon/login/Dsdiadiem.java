package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Dsdiadiem extends RealmObject {
    @SerializedName("id")
    private int id;
    @SerializedName("id_thediadiem")
    private String idThediadiem;
    @SerializedName("id_kehoach")
    private int idKehoach;
    @SerializedName("id_site")
    private int idSite;
    @SerializedName("id_matbang")
    private int idMatbang;
    @SerializedName("loaidiadiem")
    private int loaidiadiem;
    @SerializedName("tendiadiem")
    private String tendiadiem;
    @SerializedName("thoigianlamviec")
    private String thoigianlamviec;
    @SerializedName("thoigiantoida")
    private int thoigiantoida;
    @SerializedName("ghichu")
    private String ghichu;
    @SerializedName("dschecklist")
    private RealmList<Dschecklist> dschecklist;
    @SerializedName("dshuongdan")
    private RealmList<Dshuongdan> dshuongdan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdThediadiem() {
        return idThediadiem;
    }

    public void setIdThediadiem(String idThediadiem) {
        this.idThediadiem = idThediadiem;
    }

    public int getIdKehoach() {
        return idKehoach;
    }

    public void setIdKehoach(int idKehoach) {
        this.idKehoach = idKehoach;
    }

    public int getIdSite() {
        return idSite;
    }

    public void setIdSite(int idSite) {
        this.idSite = idSite;
    }

    public int getIdMatbang() {
        return idMatbang;
    }

    public void setIdMatbang(int idMatbang) {
        this.idMatbang = idMatbang;
    }

    public int getLoaidiadiem() {
        return loaidiadiem;
    }

    public void setLoaidiadiem(int loaidiadiem) {
        this.loaidiadiem = loaidiadiem;
    }

    public String getTendiadiem() {
        return tendiadiem;
    }

    public void setTendiadiem(String tendiadiem) {
        this.tendiadiem = tendiadiem;
    }

    public String getThoigianlamviec() {
        return thoigianlamviec;
    }

    public void setThoigianlamviec(String thoigianlamviec) {
        this.thoigianlamviec = thoigianlamviec;
    }

    public int getThoigiantoida() {
        return thoigiantoida;
    }

    public void setThoigiantoida(int thoigiantoida) {
        this.thoigiantoida = thoigiantoida;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public RealmList<Dschecklist> getDschecklist() {
        return dschecklist;
    }

    public void setDschecklist(RealmList<Dschecklist> dschecklist) {
        this.dschecklist = dschecklist;
    }

    public RealmList<Dshuongdan> getDshuongdan() {
        return dshuongdan;
    }

    public void setDshuongdan(RealmList<Dshuongdan> dshuongdan) {
        this.dshuongdan = dshuongdan;
    }
}

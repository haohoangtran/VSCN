package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Kehoach extends RealmObject {
    @SerializedName("site")
    private Site site;
    @SerializedName("dsmatbang")
    private RealmList<Dsmatbang> dsmatbang;
    @SerializedName("dsdiadiem")
    private RealmList<Dsdiadiem> dsdiadiem;

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public RealmList<Dsmatbang> getDsmatbang() {
        return dsmatbang;
    }

    public void setDsmatbang(RealmList<Dsmatbang> dsmatbang) {
        this.dsmatbang = dsmatbang;
    }

    public RealmList<Dsdiadiem> getDsdiadiem() {
        return dsdiadiem;
    }

    public void setDsdiadiem(RealmList<Dsdiadiem> dsdiadiem) {
        this.dsdiadiem = dsdiadiem;
    }
}

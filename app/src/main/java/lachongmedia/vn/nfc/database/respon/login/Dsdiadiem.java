package lachongmedia.vn.nfc.database.respon.login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Dsdiadiem extends RealmObject {
    @SerializedName("diadiem")
    private Diadiem diadiem;
    @SerializedName("dschecklist")
    private RealmList<Dschecklist> dschecklist;
    @SerializedName("dshuongdan")
    private RealmList<Dshuongdan> dshuongdan;

    public Diadiem getDiadiem() {
        return diadiem;
    }

    public void setDiadiem(Diadiem diadiem) {
        this.diadiem = diadiem;
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

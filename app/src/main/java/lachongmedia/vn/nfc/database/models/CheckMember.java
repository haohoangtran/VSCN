package lachongmedia.vn.nfc.database.models;

/**
 * Created by hao on 29/04/2017.
 */

public class CheckMember {
    private String hangMuc;
    private String phuongPhap;
    private String requitement;

    public String getHangMuc() {
        return hangMuc;
    }

    public String getPhuongPhap() {
        return phuongPhap;
    }

    public String getRequitement() {
        return requitement;
    }

    public CheckMember(String hangMuc, String phuongPhap, String requitement) {

        this.hangMuc = hangMuc;
        this.phuongPhap = phuongPhap;
        this.requitement = requitement;
    }
}

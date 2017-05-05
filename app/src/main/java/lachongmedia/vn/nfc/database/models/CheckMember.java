package lachongmedia.vn.nfc.database.models;

/**
 * Created by hao on 29/04/2017.
 */

public class CheckMember {
    private String hangMuc;
    private String phuongPhap;
    private String requitement;
    private int type;

    public void setHangMuc(String hangMuc) {
        this.hangMuc = hangMuc;
    }

    public void setPhuongPhap(String phuongPhap) {
        this.phuongPhap = phuongPhap;
    }

    public void setRequitement(String requitement) {
        this.requitement = requitement;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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
        type = 2;
        this.hangMuc = hangMuc;
        this.phuongPhap = phuongPhap;
        this.requitement = requitement;
    }
}

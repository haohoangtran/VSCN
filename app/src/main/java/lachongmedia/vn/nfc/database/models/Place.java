package lachongmedia.vn.nfc.database.models;

import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;

/**
 * Created by tranh on 5/19/2017.
 */

public class Place {
    private int id;
    private String idThediadiem;
    private int idKehoach;
    private int idSite;
    private int idMatbang;
    private int loaidiadiem;
    private String tendiadiem;
    private int minuteInDay;
    private int thoigiantoida;
    private String ghichu;

    public Place(Dsdiadiem diadiem) {
        this.id = diadiem.getId();
        this.idThediadiem = diadiem.getIdThediadiem();
        idKehoach = diadiem.getIdKehoach();
        idSite = diadiem.getIdSite();
        idMatbang = diadiem.getIdMatbang();
        loaidiadiem = diadiem.getLoaidiadiem();
        tendiadiem = diadiem.getTendiadiem();
        thoigiantoida = diadiem.getThoigiantoida();
        ghichu = diadiem.getGhichu();
    }

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

    public int getMinuteInDay() {
        return minuteInDay;
    }

    public void setMinuteInDay(int minuteInDay) {
        this.minuteInDay = minuteInDay;
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
}

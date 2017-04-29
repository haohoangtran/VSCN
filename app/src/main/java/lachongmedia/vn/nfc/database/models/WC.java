package lachongmedia.vn.nfc.database.models;

import java.util.Date;

/**
 * Created by hao on 28/04/2017.
 */

public class WC {
    private String UID;
    private String name;
    private String location;
    private int minuteRecheck;
    private Date lastCheck;

    public String getUID() {
        return UID;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getMinuteRecheck() {
        return minuteRecheck;
    }

    public Date getLastCheck() {
        return lastCheck;
    }

    public WC(String UID, String name, String location, int minuteRecheck, Date lastCheck) {
        this.UID = UID;
        this.name = name;
        this.location = location;

        this.minuteRecheck = minuteRecheck;
        this.lastCheck = lastCheck;
    }
}

package lachongmedia.vn.nfc.database;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import lachongmedia.vn.nfc.database.models.CheckMember;
import lachongmedia.vn.nfc.database.models.Member;
import lachongmedia.vn.nfc.database.models.WC;

/**
 * Created by hao on 28/04/2017.
 */

public class DbContext {
    public static final DbContext instance = new DbContext();
    private Date dateStart;
    private Date dateStop;

    private DbContext() {
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateStop() {
        return dateStop;
    }

    public void setDateStop(Date dateStop) {
        this.dateStop = dateStop;
    }

}

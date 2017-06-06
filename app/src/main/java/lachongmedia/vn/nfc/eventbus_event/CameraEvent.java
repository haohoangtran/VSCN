package lachongmedia.vn.nfc.eventbus_event;

import lachongmedia.vn.nfc.database.models.CheckMember;
import lachongmedia.vn.nfc.database.respon.login.Dschecklist;

/**
 * Created by tranh on 5/3/2017.
 */

public class CameraEvent {
    private Dschecklist dschecklist;

    public Dschecklist getDschecklist() {
        return dschecklist;
    }

    public void setDschecklist(Dschecklist dschecklist) {
        this.dschecklist = dschecklist;
    }

    public CameraEvent(Dschecklist dschecklist) {

        this.dschecklist = dschecklist;
    }
}

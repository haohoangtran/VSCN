package lachongmedia.vn.nfc.eventbus_event;

import java.util.Vector;

import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;

/**
 * Created by tranh on 5/18/2017.
 */

public class GotoGroundActivityEvent {
    Vector<Dsdiadiem> diadiems;

    public GotoGroundActivityEvent(Vector<Dsdiadiem> diadiems) {

        this.diadiems = diadiems;
    }

    public Vector<Dsdiadiem> getDiadiems() {
        return diadiems;
    }
}

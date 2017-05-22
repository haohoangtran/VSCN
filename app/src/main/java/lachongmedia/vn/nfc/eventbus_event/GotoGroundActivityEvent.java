package lachongmedia.vn.nfc.eventbus_event;

import java.util.Vector;

import lachongmedia.vn.nfc.database.respon.login.Diadiem;
import lachongmedia.vn.nfc.database.respon.login.Dsmatbang;
import lachongmedia.vn.nfc.database.respon.login.Matbang;

/**
 * Created by tranh on 5/18/2017.
 */

public class GotoGroundActivityEvent {
    Vector<Diadiem> diadiems;

    public GotoGroundActivityEvent(Vector<Diadiem> diadiems) {

        this.diadiems = diadiems;
    }

    public Vector<Diadiem> getDiadiems() {
        return diadiems;
    }
}

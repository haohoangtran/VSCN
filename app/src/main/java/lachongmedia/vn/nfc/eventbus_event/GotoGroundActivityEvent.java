package lachongmedia.vn.nfc.eventbus_event;

import java.util.Vector;

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

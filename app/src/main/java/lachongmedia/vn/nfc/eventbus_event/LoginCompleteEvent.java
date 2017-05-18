package lachongmedia.vn.nfc.eventbus_event;

import java.util.Vector;

/**
 * Created by tranh on 5/17/2017.
 */

public class LoginCompleteEvent {
    private Vector<String> path;

    public LoginCompleteEvent(Vector<String> path) {
        this.path = path;
    }

    public Vector<String> getPath() {

        return path;
    }
}

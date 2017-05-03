package lachongmedia.vn.nfc.eventbus_event;

import lachongmedia.vn.nfc.database.models.CheckMember;

/**
 * Created by tranh on 5/3/2017.
 */

public class CameraEvent {
    private CheckMember checkMember;

    public CheckMember getCheckMember() {
        return checkMember;
    }

    public CameraEvent(CheckMember checkMember) {

        this.checkMember = checkMember;
    }
}

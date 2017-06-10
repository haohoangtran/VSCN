package lachongmedia.vn.nfc.eventbus_event;

import lachongmedia.vn.nfc.database.realm.realm_models.PlanWork;

/**
 * Created by Quoc Viet Dang on 6/9/2017.
 */

public class PlanworkEvent {
    PlanWork planWork;

    public PlanworkEvent(PlanWork planWork) {
        this.planWork = planWork;
    }

    public PlanWork getPlanWork() {
        return planWork;
    }
}

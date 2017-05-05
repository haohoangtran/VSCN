package lachongmedia.vn.nfc.eventbus_event;

/**
 * Created by tranh on 5/5/2017.
 */

public class TimeChangeEvent {
    private String time;

    public TimeChangeEvent(String time) {
        this.time = time;
    }

    public void setTime(String time) {

        this.time = time;
    }

    public String getTime() {

        return time;
    }
}

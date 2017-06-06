package lachongmedia.vn.nfc.database.realm.realm_models;

import io.realm.RealmObject;

/**
 * Created by hao on 6/3/17.
 */

public class RealmString extends RealmObject {
    private String value;

    public RealmString() {

    }

    @Override
    public String toString() {
        return "RealmString{" +
                "value='" + value + '\'' +
                '}';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RealmString(String value) {

        this.value = value;
    }
}

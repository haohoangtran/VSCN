package lachongmedia.vn.nfc.database.models;

/**
 * Created by hao on 28/04/2017.
 */

public class Member {
    private String UID;
    private String name;

    public String getUID() {
        return UID;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Member{" +
                "UID='" + UID + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Member(String UID, String name) {

        this.UID = UID;
        this.name = name;
    }
}

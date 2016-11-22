package Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by zemoso on 11/11/16.
 */

public class Data extends RealmObject {
    public String data;
    public void setData(String data) {
        this.data = data;
    }
    public Data() {
    }
    public String getData() {
        return data;
    }
}


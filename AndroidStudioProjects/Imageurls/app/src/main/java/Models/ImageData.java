package Models;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by zemoso on 17/11/16.
 */

public class ImageData extends RealmObject {
    @PrimaryKey
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {

            this.id = id;
    }

    private String url;

    public ImageData(){}

    public ImageData(String url,int id) {
        this.url = url;
        this.setId(id);
    }

    public String getLocalUrl() {
        return url;
    }


}

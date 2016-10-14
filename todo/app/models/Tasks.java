package models;

import com.avaje.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by zemoso on 6/10/16.
 */

@Entity
public class Tasks extends Model{
    public static Finder<String,Tasks> find = new Finder<String,Tasks>(
            String.class, Tasks.class
    );
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;
    public String status;
    public String user;
    public String title;
    public String description;
    public String lastUpdated;
    public String list;
    public String listTF;


    public long getId(){ return id; }
    public String getStatus(){
        return status;
    }
    public String getUser(){ return user; }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getLastUpdated(){
        return lastUpdated;
    }
    public String getList(){ return list; }
    public String getListTF(){ return listTF; }
}

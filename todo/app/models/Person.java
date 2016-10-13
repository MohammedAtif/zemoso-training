package models;

/**
 * Created by zemoso on 4/10/16.
 */
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person extends Model {
    public static Finder<String, Person> find;
    @Id
    public String id;
    public String name;

//    Finder find = new Finder(String.class,Person.class);

}
package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static play.mvc.Http.Context.Implicit.session;

/**
 * Created by zemoso on 5/10/16.
 */

@Entity
public class Registration extends Model{
    /*public static final Finder<Integer, Registration> find = new Model.Finder<String, Registration>();*/
    public static Finder<String,Registration> find = new Finder<String,Registration>(
            String.class, Registration.class
    );
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;
    public String name;
    public String email;
    public String password;

    public static Registration authenticate(String email, String password) {
        return find.where().eq("email", email)
                .eq("password", password).findUnique();
    }
    public static Registration isPresent(String email){
        return find.where().eq("email",email).findUnique();
    }
}

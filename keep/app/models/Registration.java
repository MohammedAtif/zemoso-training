package models;

import com.avaje.ebean.Model;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;

import static play.mvc.Http.Context.Implicit.session;

/**
 * Created by zemoso on 5/10/16.
 */

@Entity
public class Registration extends Model{

    public static Finder<String,Registration> find = new Finder<String,Registration>(
            String.class, Registration.class
    );
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long userId;
    public String userName;
    public String userEmail;
    public String userPassword;
    @Column(name="timestamp",columnDefinition="timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private String accountCreationDate;

    public Registration(String userName,String userEmail,String userPassword){
        this.setUserName(userName);
        this.setUserEmail(userEmail);
        this.setUserPassword(userPassword);
    }

    public static Registration authenticate(String email, String password) {
//        if (BCrypt.checkpw(password, ))
        return find.where().eq("userEmail", email)
                .eq("userPassword", password).findUnique();
    }

    public static Registration isPresent(String email){
        return find.where().eq("userEmail",email).findUnique();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserPassword(){
        return this.userPassword;
    }
}

package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.SqlUpdate;
import com.google.common.io.Files;
import models.Login;
import models.Person;
import models.Registration;
import models.Tasks;
import org.h2.store.fs.FileUtils;
import play.mvc.*;

import views.html.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static play.data.Form.form;
import static play.libs.Json.toJson;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(login.render(""));
    }
    public Result regis(){ return ok(index.render(""));}

    public Result Dash(){
        List<Tasks> tasks=Tasks.find.all();
        return ok(dash.render(session().get("email"),tasks));
    }
    public Result login(){
        return ok(login.render(""));
    }

    public Result loginpost(){
        Login login1=form(Login.class).bindFromRequest().get();
        String email=login1.email;
        String password=login1.password;
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        if(!matcher.find()){
            return ok(login.render("Not a valid email!! Please try again."));
        }
        /*if(Registration.isPresent(email)!=null){
            return ok(login.render("Mail id is already registered.Try logging in!!"));
        }*/
        System.out.println(email+" "+password);
        if(Registration.authenticate(email,password)!=null){
            session().clear();
            session("email",email);
            return redirect(routes.HomeController.Dash());
        }
        else{
            return ok(login.render("Incorrect password or email..Login Failed!"));
        }

    }
    public Result postTask() throws IOException {

        Tasks task=form(Tasks.class).bindFromRequest().get();
        if(task.imageurl!=null) {
            File afile = new File(task.imageurl);
            task.imageurl="public/Uploads/"+ afile.getName();
        }
        try{
        task.save();
        }
        catch (Exception e){
            task.update();
        }

        return redirect(routes.HomeController.Dash());
    }


    public Result register(){
        Registration reg= form(Registration.class).bindFromRequest().get();
        String email=reg.email;
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        if(!matcher.find()){
            return ok(index.render("Not a valid email! Please try again."));
        }
        if(Registration.isPresent(email)!=null){
            return ok(index.render("Mail id is already registered.Try logging in!!"));
        }
        else if(reg.email==null||reg.password==null||reg.name==null||reg.email.equals("")||reg.password.equals("")||reg.name.equals("")){
            return ok(index.render("Fields cannot be empty or null."));
        }
        else {
            reg.save();
            //email = reg.email;
            session().clear();
            session("email", email);
            return redirect(routes.HomeController.Dash());
        }
    }
    public Result logout(){
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.HomeController.login());
    }

    public Result del(Long id){
        Tasks.find.ref(id.toString()).delete();
        return redirect(routes.HomeController.Dash());
    }

}

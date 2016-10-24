package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Card;
import models.Login;
import models.Registration;

import play.mvc.*;

import views.html.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static play.data.Form.form;

import play.libs.Json;


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
        return ok(index.render(""));
    }

    public Result loginPost(){
        Login login1=form(Login.class).bindFromRequest().get();
        String email=login1.email;
        String password=login1.password;
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        if(!matcher.find()){
            return ok(index.render("Not a valid email!! Please try again."));
        }
        if(Registration.authenticate(email,password)!=null){
            session().clear();
            session("email",email);
            //return ok(dash.render(email));
            return redirect(routes.HomeController.Dash());
        }
        else{
            return ok(index.render("Incorrect password or email..Login Failed!"));
        }

    }

    public Result regis(){ return ok(registration.render(""));}

    public Result register(){
        Registration reg= form(Registration.class).bindFromRequest().get();
        String email=reg.email;
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        if(!matcher.find()){
            return ok(registration.render("Not a valid email! Please try again."));
        }
        if(Registration.isPresent(email)!=null){
            return ok(index.render("Mail id is already registered.Try logging in!!"));
        }
        else if(reg.email==null||reg.password==null||reg.name==null||reg.email.equals("")||reg.password.equals("")||reg.name.equals("")){
            return ok(index.render("Fields cannot be empty or null."));
        }
        else {
            reg.save();
            email = reg.email;
            session().clear();
            session("email", email);
            return redirect(routes.HomeController.Dash());
        }
    }

    public Result addCards(){
        JsonNode jsonNode=request().body().asJson();
        String user=jsonNode.path("user").asText();
        String title=jsonNode.path("title").asText();
        String content=jsonNode.path("content").asText();
        String reminder=jsonNode.path("reminder").asText();
        //int isArchive=jsonNode.path("isArchive").asInt();
        int isActive=0;
        String rem=reminder;

        System.out.println(title+"**********************************"+content);
            if (reminder != "") {
                Date now = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy HH:mm");


                //reminder=(simpleDateFormat1.format(simpleDateFormat.parse(reminder)));
                try {
                    reminder = (simpleDateFormat1.format(simpleDateFormat.parse(reminder)));
                    Date date = simpleDateFormat1.parse(reminder);

                    System.out.println("date : " + simpleDateFormat.format(date));

                    if (!now.after(date)) {
                        isActive = 1;
                    }
                    Card posts = new Card(user, title, content, rem, 0, isActive);
                    posts.save();

                } catch (ParseException ex) {
                    System.out.println("Exception " + ex);
                }
            } else {
                Card posts = new Card(user, title, content, "", 0, 0);
                posts.save();
            }
        return ok(HomeController.buildJsonResponse("success", "Post added successfully"));
    }

    public Result updateCards(){
        JsonNode jsonNode=request().body().asJson();
        String id=jsonNode.path("id").asText();
        String user=jsonNode.path("user").asText();
        String title=jsonNode.path("title").asText();
        long Id=Long.parseLong(id);
        String content=jsonNode.path("content").asText();

        String reminder=jsonNode.path("reminder").asText();
        String rem=reminder;
        System.out.println("*************"+reminder+"**************");
        int isArchive=jsonNode.path("isArchive").asInt();
        int isActive=0;

        if (reminder != "") {
            Date now = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy HH:mm");


            //reminder=(simpleDateFormat1.format(simpleDateFormat.parse(reminder)));
            try {
                reminder = (simpleDateFormat1.format(simpleDateFormat.parse(reminder)));
                Date date = simpleDateFormat1.parse(reminder);

                System.out.println("date : " + simpleDateFormat.format(date));

                if (!now.after(date)) {
                    isActive = 1;
                }
                Card posts = new Card(Id, user, title, content, rem, isArchive, isActive);
                posts.update();

            } catch (ParseException ex) {
                System.out.println("Exception " + ex);
            }
        } else {
            Card posts = new Card(Id, user, title, content, "", isArchive, isActive);
            posts.update();
        }

        return ok(HomeController.buildJsonResponse("success", "Post added successfully"));
    }

    public Result copyCards(){
        JsonNode jsonNode=request().body().asJson();
        String user=jsonNode.path("user").asText();
        String title=jsonNode.path("title").asText();
        int isArchive=jsonNode.path("isArchive").asInt();
        int isReminderActive=jsonNode.path("isActive").asInt();
        String reminder=jsonNode.path("remainder").asText();
        String content=jsonNode.path("content").asText();
        Card posts=new Card(user, title, content,reminder,isArchive,isReminderActive);
        posts.save();
        return ok(HomeController.buildJsonResponse("success", "Post added successfully"));
    }

    private static ObjectNode buildJsonResponse(String type, String message) {
        ObjectNode wrapper = Json.newObject();
        ObjectNode msg = Json.newObject();
        msg.put("message", message);
        wrapper.put(type, msg);
        return wrapper;
    }

    public Result Dash(){
        List<Card> card= Card.getData(session().get("email"));
        if(!(session().get("email")==null)){
        return ok(dash.render(session().get("email"),card));
        }
        else{
            return redirect(routes.HomeController.index());
        }
    }
    public Result gArchive(){
        return ok(archive.render(session().get("email")));
    }
    public Result getArchive(){
        List<Card> card= Card.getArchive(session().get("email"));
        return ok(Json.toJson(card));
    }
    public Result gReminder(){
        return ok(reminder.render(session().get("email")));
    }
    public Result getReminder(){
        List<Card> card = Card.getReminder(session().get("email"));
        System.out.print("This is happening");
        return ok(Json.toJson(card));
    }

    public Result getPosts() {
        List<Card> card= Card.getData(session().get("email"));
        return ok(Json.toJson(card));
    }
    public Result del(){
        JsonNode jsonNode=request().body().asJson();
        String id=jsonNode.path("id").asText();
        Card.find.ref(id).delete();
        return ok();
    }
    public Result archive() {
        JsonNode jsonNode = request().body().asJson();
        String id=jsonNode.path("id").asText();
        int isArchive=jsonNode.path("isArchive").asInt();
        long Id=Long.parseLong(id);
        Card c=new Card(Id,isArchive);
        c.update();
        return ok();
    }

    public Result updateReminder(){
        JsonNode jsonNode=request().body().asJson();
        String ID=jsonNode.path("id").asText();
        long id=Long.parseLong(ID);
        Card c=new Card(id);
        c.update();
        return ok();
    }
    public Result logout(){
        session().clear();
        flash("success", "You've been logged out");
        return redirect(routes.HomeController.index());
    }
}

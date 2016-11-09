package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Card;
import models.Registration;

import play.mvc.*;

import views.html.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static play.data.Form.form;

import play.libs.Json;
import org.mindrot.jbcrypt.BCrypt;


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
        JsonNode jsonNode=request().body().asJson();
        String userEmail=jsonNode.path("userEmail").asText().toLowerCase();
        String userPassword=jsonNode.path("userPassword").asText();
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(userEmail);
        if(!matcher.find()){
            return badRequest("Not a valid email!! Please try again.");
        }
        Registration user = Ebean.find(Registration.class).where().eq("userEmail", userEmail).findUnique();
        if(user==null){
            return badRequest("Email is not registered!!");
        }
        else {
            String db_password = user.getUserPassword();
            if (BCrypt.checkpw(userPassword, db_password)) {
                session().clear();
                session("email", userEmail);
                return ok("Login Successs");
            }
            else{
                return badRequest("Password is wrong! Please try again.");
            }

        }

    }

    public Result regis(){ return ok(registration.render(""));}

    public Result register(){
        JsonNode jsonNode=request().body().asJson();
        String userName=jsonNode.path("userName").asText();
        String userEmail=jsonNode.path("userEmail").asText().toLowerCase();
        String userPassword=jsonNode.path("userPassword").asText();
        String hashed = BCrypt.hashpw(userPassword, BCrypt.gensalt(12));
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(userEmail);
        System.out.println(userName+""+userEmail+""+userPassword);

        if(userEmail.equals("")||userPassword.equals("")||userName.equals("")||userEmail.equals("")||userPassword.equals("")||userName.equals("")){
            return badRequest("Fields cannot be empty or null.");
        }
        else if(!matcher.find()){
            return badRequest("Not a valid email! Please try again.");
        }
        else if(Registration.isPresent(userEmail)!=null){
            return badRequest("Mail id is already registered.Try logging in!!");
        }
        else {
            Registration reg=new Registration(userName,userEmail,hashed);
            reg.save();
            session().clear();
            session("email", userEmail);
            return ok("Success");
        }
    }

    public Result addCards(){
        JsonNode jsonNode=request().body().asJson();
        String user=jsonNode.path("user").asText();
        String title=jsonNode.path("title").asText();
        String content=jsonNode.path("content").asText();
        String reminder=jsonNode.path("reminder").asText();
        int isActive=0;
        String rem=reminder;
        //System.out.println(title+"**********************************"+content);
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
        //System.out.println("*************"+reminder+"**************");
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

    public Result updatePositions(){
        JsonNode jsonNode=request().body().asJson();
        String positions=jsonNode.path("positions").asText();
        String ids=jsonNode.path("ids").asText();
        String pos[]=positions.split(" ");
        String iDs[]=ids.split(" ");

        for(int i=0;i<pos.length;i++){
            System.out.println(iDs[i]);
            Card c=new Card(Integer.parseInt(pos[i]),Long.parseLong(iDs[i]));
                c.update();
        }
        //System.out.println("****************************************************************"+ids);
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
    @Security.Authenticated(Secured.class)
    public Result Dash(){
//        List<Card> card= Card.getData(session().get("email"));
//        if(!(session().get("email")==null)){
        return ok(dash.render(session().get("email")));
//        }
//        else{
//            return redirect(routes.HomeController.index());
//        }
    }
    @Security.Authenticated(Secured.class)
    public Result gArchive(){
//        if(!(session().get("email")==null)){
//            return ok(archive.render(session().get("email")));
//        }
//        else{
            return redirect(routes.HomeController.index());
//        }
    }
    public Result getArchive(){
        List<Card> card= Card.getArchive(session().get("email"));
        return ok(Json.toJson(card));
    }
    @Security.Authenticated(Secured.class)
    public Result gReminder(){
//        if(!(session().get("email")==null)){
            return ok(reminder.render(session().get("email")));
//        }
//        else{
//            return redirect(routes.HomeController.index());
//        }
    }
    public Result getReminder(){
        List<Card> card = Card.getReminder(session().get("email"));
        return ok(Json.toJson(card));
    }

    public Result getPosts() {
        List<Card> card= Card.getData(session().get("email"));
        return ok(Json.toJson(card));
    }
    public Result getPostsAll() {
        List<Card> card= Card.getDataAll(session().get("email"));
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
    @Security.Authenticated(Secured.class)
    public Result search(){
//        if(!(session().get("email")==null)){
            return ok(searchData.render(session().get("email")));
//        }
//        else{
//            return redirect(routes.HomeController.index());
//        }
    }
    @Security.Authenticated(Secured.class)
    public Result gSearch(){
//        if(!(session().get("email")==null)){
            return ok(searchData.render(session().get("email")));
//        }
//        else{
//            return redirect(routes.HomeController.index());

//        }
    }
}

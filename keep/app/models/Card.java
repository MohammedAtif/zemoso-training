package models;

import javax.persistence.*;

import com.avaje.ebean.Model;
import org.h2.engine.User;

import java.util.List;

@Entity
public class Card extends Model{
    public static Finder<String, Card> find = new Finder<String, Card>(
            String.class, Card.class
    );

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    public long id;

    @ManyToOne @Column(columnDefinition="TEXT")
    private String user;

    @Column(columnDefinition="TEXT")
    private String content;

    @Column(columnDefinition="TEXT")
    private String title;

    @Column(name="timestamp",columnDefinition="timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private String date;

    @Column(columnDefinition="TEXT")
    private String reminder;

    @Column(columnDefinition="int default 0")
    private Integer isReminderActive;

    @Column(columnDefinition="int default 0")
    private Integer isArchive;

    public Card(String user, String title, String content, String remainder, int isArchive, int isReminderActive) {
        this.setUser(user);
        this.setTitle(title);
        this.setContent(content);
        this.setReminder(remainder);
        this.setIsArchive(isArchive);
        this.setIsReminderActive(isReminderActive);
    }
    public Card(long id,String user, String title, String content, String remainder, int isArchive, int isReminderActive) {
        this.setCardId(id);
        this.setUser(user);
        this.setTitle(title);
        this.setContent(content);
        this.setReminder(remainder);
        this.setIsArchive(isArchive);
        this.setIsReminderActive(isReminderActive);
    }
    public Card(long id,int isArchive){
        this.setCardId(id);
        if(isArchive==0){
            this.setIsArchive(1);
        }
        else{
            this.setIsArchive(0);
        }
    }

    public Card(long id){
        this.setCardId(id);
        this.setIsReminderActive(0);
    }
    public static List<Card> getData(String email) {
        return Card.find.where().eq("user",email).eq("isArchive",0).findList();
    }

    public static List<Card> getArchive(String email) {
        return Card.find.where().eq("user",email).eq("isArchive",1).findList();
    }

    public long getCardIdId() {
        return id;
    }

    public void setCardId(long id){ this.id = id; }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public Integer getIsArchive() {
        return isArchive;
    }

    public void setIsArchive(Integer isArchive) {
        this.isArchive = isArchive;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	public String getUser() {
		return user;
	}

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getIsReminderActive() {
        return isReminderActive;
    }

    public void setIsReminderActive(Integer isReminderActive) {
        this.isReminderActive = isReminderActive;
    }



    //public static Finder<Long, Posts> find=new Finder<Long, Posts>(Posts.class);

}
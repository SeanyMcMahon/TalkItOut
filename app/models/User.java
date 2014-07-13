package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by iloveyou on 7/12/14.
 */
@Entity
public class User extends Model {

    @Id
    public String email;

    @Constraints.MaxLength(value = 30, message = "Max length is 30, Don't be cheeky :)")
    public String username;

    @Constraints.MaxLength(value = 30, message = "Max length is 30, Don't be cheeky :)")
    public String password;


    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

   public static Finder<String, User> find = new Finder(String.class, User.class);

    public static void create(String email, String username, String password){
        User user = new User(email, username, password);
        user.save();
    }

    public static User authenticate(String email, String password){

        return find.where().eq("email" , email).eq("password", password).findUnique();
    }

    public static boolean exists(String email){

        if(find.where().eq("email", email).findUnique() == null){
            return false;
        }else{ return true;}
    }

}

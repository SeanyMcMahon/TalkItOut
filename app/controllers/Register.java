package controllers;

/**
 * Created by iloveyou on 7/3/14.
 */
import models.User;
import play.data.Form;
import play.mvc.*;
import views.html.*;


public  class Register extends Controller{

    public static class RegForm{
    public String email;
    public String username;
    public String password;


    public String validate(){

       if(User.exists(email) == true){

            return "Email already in use";
        }
        return null;
    }
    }

    public static Result register(){

        return ok(register.render(Form.form(RegForm.class)));
    }


    public static Result submit(){
        Form<RegForm> regForm = Form.form(RegForm.class).bindFromRequest();

        if(regForm.hasErrors()){
            return badRequest(register.render(regForm));
        }
        else{
            User.create(regForm.get().email, regForm.get().username, regForm.get().password);
            session().clear();
            session("email",  regForm.get().email);
            return redirect(routes.Application.problems());

        }

    }




}

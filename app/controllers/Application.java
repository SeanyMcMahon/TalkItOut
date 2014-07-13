package controllers;

import models.Problem;
import models.User;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result index() {

        return redirect(routes.Application.problems());
    }

    @Security.Authenticated(Secured.class)
    public static Result problems(){

        return ok(index.render(Problem.findProblemsByOwner(request().username()), User.find.byId(request().username())));

    }


    public static class Login{

        public String email;
        public String password;

        public  String validate(){
            if(User.authenticate(email, password) == null){

                return "invalid email or password";
            }
            return null;
        }

    }

    public static Result login() {
        return ok(
                login.render(Form.form(Login.class))
        );
    }

    public static Result logout(){
        session().clear();
        return redirect(routes.Application.login());
    }


    public static Result authenticate(){
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()){
            return badRequest(login.render(loginForm));
        }
        else{
            session().clear();
            session("email",  loginForm.get().email);
            return redirect(routes.Application.problems());
        }

    }

    @Security.Authenticated(Secured.class)
    public static Result addProblem(){


        Problem.create("new", "new", "new", "new", "new", request().username());
        return redirect(routes.Application.problems());


    }

    @Security.Authenticated(Secured.class)
    public static Result updateProblem(){

        Form<Problem> problemForm = Form.form(Problem.class).bindFromRequest();
        if(problemForm.hasErrors()){

            return redirect(routes.Application.problems());
        }
        else{

            Problem.update(problemForm.get().name, problemForm.get().description,
                    problemForm.get().conversation, problemForm.get().resources, problemForm.get().solution,
                     problemForm.get().id.longValue());

            return ok("all good");

        }


    }

    @Security.Authenticated(Secured.class)
    public static Result deleteProblem(Long id){
        Problem.delete(id);
        return redirect(routes.Application.problems());
    }


    public static Result javascriptRoutes(){

        response().setContentType("text/javascript");
        return ok(
                Routes.javascriptRouter("playRouter",
                        routes.javascript.Application.updateProblem()));

    }


}

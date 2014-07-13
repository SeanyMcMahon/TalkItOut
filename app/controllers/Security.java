package controllers;

/**
 * Created by iloveyou on 7/13/14.
 */
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.*;

public class Secured extends Security.Authenticator{

    @Override
    public String getUsername(Context ctx){
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context ctx){
        return redirect(routes.Application.login());
    }


}

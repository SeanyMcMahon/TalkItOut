package models;

import com.google.common.collect.ImmutableMap;
import controllers.routes;
import org.junit.*;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

/**
 * Created by iloveyou on 7/13/14.
 */
public class ControllerTests extends WithApplication {

    @Before
    public void setUp(){
        start(fakeApplication(inMemoryDatabase(),fakeGlobal())) ;

    }

    @Test
    public void loginTestSuccess(){

        new User ("bob@gmail.com", "Bob", "secret").save();

        Result result = callAction(
                routes.ref.Application.authenticate(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "email", "bob@gmail.com",
                        "password", "secret")));

        assertEquals(303, status(result));
        assertEquals("bob@gmail.com", session(result).get("email"));

    }

    @Test
    public void loginTestFailure(){

        Result result = callAction(
                routes.ref.Application.authenticate(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                                "email", "fred!@hushmail.com",
                                "password", "secret")
        ));

        assertEquals(400, status(result));

    }

    @Test
    public void authenticated(){

        new User ("bob@gmail.com", "Bob", "secret").save();

        Result result = callAction(
                controllers.routes.ref.Application.problems(),
                fakeRequest().withSession("email", "bob@gmail.com")
        );
        assertEquals(200, status(result));
    }

    @Test
    public void notAuthenticated() {
        Result result = callAction(
                controllers.routes.ref.Application.index(),
                fakeRequest()
        );
        assertEquals(303, status(result));
        assertEquals("/login", header("Location", result));
    }



}

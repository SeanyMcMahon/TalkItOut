package models;

import org.junit.*;
import play.test.WithApplication;
import static  org.junit.Assert.*;
import  static play.test.Helpers.*;
/**
 * Created by iloveyou on 7/12/14.
 */
public class ModelTests extends WithApplication{

    @Before
    public void setUp(){
       start(fakeApplication(inMemoryDatabase())) ;

    }


     @Test
      public void createAndRetrieveUser(){

         new User ("bob@gmail.com", "Bob", "secret").save();
         User bob = User.find.where().eq("email","bob@gmail.com").findUnique();
         assertNotNull(bob);
         assertEquals("Bob", bob.name);
     }
}

package models;

import org.junit.*;
import play.test.WithApplication;
import static  org.junit.Assert.*;
import  static play.test.Helpers.*;
import  java.util.*;


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

     @Test
    public void createAndRetrieveProblem(){
         new User ("bob@gmail.com", "Bob", "secret").save();
         User bob = User.find.where().eq("email","bob@gmail.com").findUnique();

         new Problem("learn play", "I don't understand the framework", "what area is difficult? How to route",
                 "www.stackoverflow.com", "create all route types to test..", bob).save();

         List<Problem> problem = Problem.findProblemsByOwner(bob.email);
         assertNotNull(problem);
         assertEquals(problem.get(0).name, "learn play");
     }

    @Test
    public void createAndDeleteProblem(){
        new User ("bob@gmail.com", "Bob", "secret").save();
        User bob = User.find.where().eq("email","bob@gmail.com").findUnique();

        new Problem("learn play", "I don't understand the framework", "what area is difficult? How to route",
                "www.stackoverflow.com", "create all route types to test..", bob).save();

        new Problem("learn guitar", "I don't understand certain chords", "which ones are they? B minor, F",
                "www.learnguitar.com", "print off chord list", bob).save();


        Problem.delete(new Long(1));
        List<Problem> problem = Problem.findProblemsByOwner(bob.email);

        assertNotNull(problem);
        assertEquals(problem.size(), 1);
        assertEquals(problem.get(0).name, "learn guitar");


    }

    @Test
    public void createAndUpdateProblem(){

        new User ("bob@gmail.com", "Bob", "secret").save();
        User bob = User.find.where().eq("email","bob@gmail.com").findUnique();

        Problem problem = new Problem("learn play", "I don't understand the framework", "what area is difficult? How to route",
                "www.stackoverflow.com", "create all route types to test..", bob);

        problem.save();

        Problem.update("learn guitar", "I don't understand certain chords", "which ones are they? B minor, F",
                "www.learnguitar.com", "print off chord list", problem.id);

        List<Problem> problemList = Problem.findProblemsByOwner(bob.email);

        assertNotNull(problemList);
        assertEquals(problemList.size(), 1);
        assertEquals("learn guitar", problemList.get(0).name);
    }



}

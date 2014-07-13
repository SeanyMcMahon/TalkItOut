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
       start(fakeApplication(inMemoryDatabase(),fakeGlobal())) ;

    }


     @Test
      public void createAndRetrieveUser(){

         new User ("bob@gmail.com", "Bob", "secret").save();
         User bob = User.find.where().eq("email","bob@gmail.com").findUnique();
         assertNotNull(bob);
         assertEquals("Bob", bob.username);
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

        new Problem("learn guitar", "I don't understand tabs", "what area is difficult? ",
                "www.guitarmaster.com", "practice from the sheets", bob).save();

        List<Problem>  problem = Problem.find.all();
        Problem.delete(problem.get(0).id);
        List<Problem>  problems = Problem.find.all();

        assertNotNull(problems);
        assertTrue(problems.size() < 3);
        assertEquals(1, problems.size());
        assertEquals(problems.get(0).name, "learn guitar");


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

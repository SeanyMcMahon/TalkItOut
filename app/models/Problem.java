package models;

/**
 * Created by iloveyou on 7/12/14.
 */


import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;


@Entity
public class Problem extends Model {


    @Id
    public Long id;

    public String name;
    public String description;
    public String conversation;
    public String resources;
    public String solution;

    @ManyToOne
    public User user;


    public Problem(String name, String description, String conversation, String resources, String solution, User user) {
        this.name = name;
        this.description = description;
        this.conversation = conversation;
        this.resources = resources;
        this.solution = solution;
        this.user = user;
    }


    public static Finder<Long, Problem> find = new Finder(Long.class, Problem.class);

    public static Problem create(String name, String description, String conversation,
                              String resources, String solution, String owner){

        Problem problem = new Problem(name, description, conversation, resources, solution, User.find.ref(owner));
        problem.save();
        return problem;
    }


    public static List<Problem> findProblemsByOwner(String owner){

        return Problem.find.where().eq("user.email", owner).findList();
    }

    public static void delete(Long id){

        Problem.find.ref(id).delete();
    }



        public static void update(String name, String description, String conversation, String resources, String solution ,Long id){


            Problem  problem = Problem.find.ref(id);
            problem.name = name;
            problem.description = description;
            problem.conversation = conversation;
            problem.resources = resources;
            problem.solution = solution;

            problem.update();

        }



}



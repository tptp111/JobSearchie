import Controllers.*;
import Database.DatabaseManager;
import Entities.*;
import Utilities.UserIO;

import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

/**
 * This is the driver class of the program.
 *
 * @author Team R
 * @version 1.0
 */
public class JobSearchie
{
    private Session session;
    private DatabaseManager db;
    private JFrame frameInit;

    /**
     * This is the main method which begins the program execution.
     *
     * @param args An array of string passed in as command line parameters.
     */
    public static void main(String[] args)
    {
        JobSearchie program = new JobSearchie();
        program.frameInit = new JFrame();
        program.db = new DatabaseManager();
        program.run();
        program.exit();
    }

    /**
     * This method closes the database and terminates the program.
     */
    private void exit()
    {
        db.close();
        UserIO.displayBody("Thank you for using Job Searchie, we hope to see you again soon.");
        System.exit(1);
    }

    /**
     *
     */
    private void insertSession()
    {
        if (session.getUser() != null)
        {
            session.setLogoutTime(new Date(System.currentTimeMillis()));
            try
            {
                db.insertSession(session);
            } catch (Exception e)
            {
                System.out.println("Error inserting session" + e.getMessage());
            }
        }
    }

    /**
     *
     */
    private void resetSession()
    {
        session = new Session();
    }

    /**
     *
     */
    private void run()
    {
        session = new Session();
        LoginHandler loginHandler = new LoginHandler();
        while (true)
        {
            User user = loginHandler.startLogin(db);
            session.setUser(user);
            if (user != null)
            {
                switch (session.getUserType())
                {
                    case ("Admin") -> {
                    }
                    case ("Recruiter") -> runRecruiter();
                    case ("JobSeeker") -> runJobSeeker();
                }
            } else
                break;
            insertSession();
            resetSession();
        }

    }

    /**
     *
     */
    private void runJobSeeker()
    {
        JobSeekerHandler handler = new JobSeekerHandler();
        JobSeeker jobSeeker = (JobSeeker) session.getUser();
        try
        {
            handler.home(jobSeeker, db);
        } catch (Exception e)
        {
            System.out.println("Could not run Job Seeker home method: " + e.getMessage());
        }
    }

    /**
     *
     */
    private void runRecruiter()
    {
        RecruiterHandler handler = new RecruiterHandler();
        Recruiter recruiter = (Recruiter) session.getUser();
        try
        {
            handler.home(recruiter, db);
        } catch (Exception e)
        {
            System.out.println("Could not run Recruiter home method: " + e.getMessage());
        }
    }
}

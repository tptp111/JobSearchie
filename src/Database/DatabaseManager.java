package Database;

import Entities.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Database class which connects to the SQL database.
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version 1.0
 */
public class DatabaseManager
{
    /**
     * The name of the database file stored in the database folder.
     */
    private static final String DATABASE_NAME = "database.db";
    /**
     * Generic SQLite connection string with a relative reference to the database folder. Also uses the database name
     * to get the exact file.
     */
    private static final String CONNECTION_STRING = "jdbc:sqlite:database/" + DATABASE_NAME;

    private UserDB userDB;
    private LocationDB locationDB;
    private KeywordDB keywordDB;
    private CategoryDB categoryDB;
    private JobDB jobDB;
    private UserKeywordDB userKeywordDB;
    private JobKeywordDB jobKeywordDB;
    private JobCategoryDB jobCategoryDB;
    private ApplicationDB applicationDB;
    private InvitationDB invitationDB;
    private SessionDB sessionDB;


    private Connection conn;

    /**
     * TESTED
     * Default and only constructor for the DatabaseManager class. Initialises the SQLite Driver Connection and
     * the SimpleDateFormat.
     */
    public DatabaseManager()
    {
        try
        {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            open();
        } catch (SQLException e)
        {
            System.out.println("Error creating a DriverManager Instance: " + e.getMessage());
        }
    }

    /**
     * TESTED
     * When method is called it closes all open PreparedStatements and finally closes the database connection. This
     * should only be called when the user is ready to close the entire application.
     */
    public void close()
    {
        try
        {
            userDB.close();
            locationDB.close();
            keywordDB.close();
            categoryDB.close();
            jobDB.close();
            userKeywordDB.close();
            jobKeywordDB.close();
            jobCategoryDB.close();
            applicationDB.close();
            invitationDB.close();
            sessionDB.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e)
        {
            System.out.println("Couldn't close database: " + e.getMessage());
        }
    }

    public JobSeeker getJobSeeker(String email)
    {
        return userDB.getJobSeeker(email, userKeywordDB, locationDB);
    }



    /**
     * Gets the admin data from the database by their email.
     *
     * @param email admin email address as a String.
     * @return admin as an Admin object.
     */
    public Admin getAdmin(String email)
    {
        return userDB.getAdmin(email);
    }

    /**
     * Gets invitation from the database by user.
     *
     * @return invitations as an ArrayList<Invitation> object.
     */
    public ArrayList<Invitation> getAllInvitations()
    {
        return invitationDB.getAllInvitations(userDB, locationDB, jobDB, userKeywordDB, jobKeywordDB, jobCategoryDB);
    }

    /**
     * Gets list of jobs from the database.
     *
     * @return list of jobs as an ArrayList<Job>
     */
    public ArrayList<Job> getAllJobs()
    {
        return jobDB.getAllJobs(userDB, locationDB, jobKeywordDB, jobCategoryDB);
    }

    /**
     * Gets the application data from the database by application id.
     *
     * @param applicationId application id as an int.
     * @return application as an Application object.
     */
    public Application getApplication(int applicationId)
    {
        return applicationDB.getApplication(applicationId, userDB, userKeywordDB, locationDB, jobDB, jobKeywordDB, jobCategoryDB);
    }

    /**
     * Gets invitation from the database by invitation id.
     *
     * @param invitationId invitation id as an int.
     * @return invitation as an Invitation object.
     */
    public Invitation getInvitation(int invitationId)
    {
        return invitationDB.getInvitation(invitationId, userDB, locationDB, jobDB, userKeywordDB, jobKeywordDB, jobCategoryDB);
    }

    /**
     * Gets the application data from the database by jobid.
     *
     * @param id jobid as an int.
     * @return job as a Job object.
     */
    public Job getJob(int id)
    {
        return jobDB.getJob(id, userDB, locationDB, jobKeywordDB, jobCategoryDB);
    }

    /**
     * Gets applications from the database by job.
     *
     * @param job job as a Job object.
     * @return ArrayList<Application> of applications.
     */
    public ArrayList<Application> getJobApplications(Job job)
    {
        return applicationDB.getJobApplications(job, userDB, userKeywordDB, locationDB, jobDB, jobKeywordDB, jobCategoryDB);
    }



    /**
     * Gets the recruiter data from the database by email.
     *
     * @param email recruiter email as a String.
     * @return recruiter as a Recruiter object.
     */
    public Recruiter getRecruiter(String email)
    {
        return userDB.getRecruiter(email);
    }

    /**
     * Gets the user account type data from the database by email.
     *
     * @param email user email as a String.
     * @return account type as a String.
     */
    public String getUserType(String email)
    {
        return userDB.getUserAccountType(email);
    }

    /**
     * Inserts admin into the database.
     *
     * @param admin Admin object to insert.
     * @return admin as an Admin object.
     * @throws SQLException handles SQL Exception
     */
    public Admin insertAdmin(Admin admin) throws SQLException
    {
        return userDB.insertAdmin(admin);
    }

    /**
     * Inserts application in the database.
     *
     * @param application Application object to insert.
     * @return application as Application object.
     * @throws SQLException handles SQL Exception.
     */
    public Application insertApplication(Application application) throws SQLException
    {
        return applicationDB.insertApplication(application);
    }

    /**
     * Inserts invitation into the database.
     *
     * @param invitation invitation to insert into the database as an Invitation object.
     * @return invitation as an Invitation object.
     * @throws SQLException Handles SQL Exception.
     */
    public Invitation insertInvitation(Invitation invitation) throws SQLException
    {
        return invitationDB.insertInvitation(invitation, userDB, locationDB, jobDB);
    }

    /**
     * Inserts job into the database.
     *
     * @param job Job object to insert.
     * @return job as a Job object.
     * @throws SQLException Handles SQL Exception
     */
    public Job insertJob(Job job) throws SQLException
    {
        return jobDB.insertJob(job, locationDB, jobKeywordDB, jobCategoryDB);
    }

    /**
     * Inserts job seeker into the database.
     *
     * @param jobSeeker JobSeeker object to insert.
     * @return jobSeeker as a JobSeeker object.
     * @throws SQLException Handles SQL Exception
     */
    public JobSeeker insertJobSeeker(JobSeeker jobSeeker) throws SQLException
    {
        return userDB.insertJobSeeker(jobSeeker, locationDB, userKeywordDB);
    }

    /**
     * Inserts recruiter into the database.
     *
     * @param recruiter Recruiter object to insert.
     * @return recruiter as a Recruiter object.
     * @throws SQLException Handles SQL Exception
     */
    public Recruiter insertRecruiter(Recruiter recruiter) throws SQLException
    {
        return userDB.insertRecruiter(recruiter);
    }

    /**
     * Inserts session into the database.
     *
     * @param session session to insert into the database as a Session object.
     * @return invitation as an Invitation object.
     * @throws SQLException Handles SQL Exception.
     */
    public Session insertSession(Session session) throws SQLException
    {
        return sessionDB.insertSession(session);
    }

    /**
     * TESTED
     * Opens the database and initilises the prepared statements.
     *
     * @return Returns true if the database opened correctly and all prepared statements have correct SQL syntax.
     */
    public boolean open()
    {
        try
        {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            locationDB = new LocationDB(conn);
            keywordDB = new KeywordDB(conn);
            categoryDB = new CategoryDB(conn);
            jobDB = new JobDB(conn);
            userDB = new UserDB(conn);
            userKeywordDB = new UserKeywordDB(conn);
            jobKeywordDB = new JobKeywordDB(conn);
            jobCategoryDB = new JobCategoryDB(conn);
            applicationDB = new ApplicationDB(conn);
            invitationDB = new InvitationDB(conn);
            sessionDB = new SessionDB(conn);
            return true;
        } catch (SQLException e)
        {
            System.out.println("Couldn't open database: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates the session in the database.
     *
     * @param session session to update in the database as a Session object.
     * @return invitation as a Session object.
     * @throws SQLException Handles SQL Exception.
     */
    public Session updateSession(Session session) throws SQLException
    {
        return sessionDB.updateSession(session);
    }
}

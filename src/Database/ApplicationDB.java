package Database;

import Entities.Application;
import Entities.Job;

import java.sql.*;
import java.util.ArrayList;

import static Database.ApplicationDB.Column.*;
import static Database.Parser.parseApplication;

/**
 * Establishes connection to the application table in SQL.
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version 1.0
 */
public class ApplicationDB implements DBHelper
{
    public static final String NAME = "application";
    /**
     * Prepared statement that will insert a Application into the application table.
     *
     * @see ApplicationDB.Insert
     */
    private final PreparedStatement insertApplication;
    private final PreparedStatement queryApplication;
    private final PreparedStatement queryApplicationByJob;

    public ApplicationDB(Connection conn) throws SQLException
    {
        insertApplication = conn.prepareStatement(ApplicationDB.Insert.APPLICATION, Statement.RETURN_GENERATED_KEYS);
        queryApplication = conn.prepareStatement(ApplicationDB.Query.APPLICATION, Statement.RETURN_GENERATED_KEYS);
        queryApplicationByJob = conn.prepareStatement(Query.APPLICATION_BY_JOB);
    }

    @Override
    public void close() throws SQLException
    {
        if (insertApplication != null)
            insertApplication.close();
        if (queryApplication != null)
            queryApplication.close();
        if (queryApplicationByJob != null)
            queryApplicationByJob.close();
    }

    /**
     * Gets application from the SQL database by querying the information to construct the Application object.
     *
     * @param applicationId application id as int
     * @param userDB        userDB as UserDB
     * @param userKeywordDB userKeywordDB as UserKeywordDB
     * @param locationDB    locationDB as LocationDB
     * @param jobDB         jobDB as JobDB
     * @param jobKeywordDB  jobKeywordDB as JobKeywordDB
     * @param jobCategoryDB jobCategoryDB as JobCategoryDB
     * @return application as Application
     */
    public Application getApplication(int applicationId, UserDB userDB, UserKeywordDB userKeywordDB, LocationDB locationDB, JobDB jobDB, JobKeywordDB jobKeywordDB, JobCategoryDB jobCategoryDB)
    {
        try
        {
            queryApplication.setInt(1, applicationId);
            ResultSet result = queryApplication.executeQuery();
            if (result.next())
            {
                return parseApplication(result, userDB, userKeywordDB, locationDB, jobDB, jobKeywordDB, jobCategoryDB);
            }
            return null;
        } catch (SQLException e)
        {
            System.out.println("Error querying applicationId = " + applicationId + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets applications from the SQL database by querying the information to construct the list of Application objects.
     *
     * @param job           job as Job
     * @param userDB        userDB as UserDB
     * @param userKeywordDB userKeywordDB as UserKeywordDB
     * @param locationDB    locationDB as LocationDB
     * @param jobDB         jobDB as JobDB
     * @param jobKeywordDB  jobKeywordDB as JobKeywordDB
     * @param jobCategoryDB jobCategoryDB as JobCategoryDB
     * @return applications as ArrayList<Application>
     */
    public ArrayList<Application> getJobApplications(Job job, UserDB userDB, UserKeywordDB userKeywordDB, LocationDB locationDB, JobDB jobDB, JobKeywordDB jobKeywordDB, JobCategoryDB jobCategoryDB)
    {
        ArrayList<Application> applications = new ArrayList<>();
        try
        {
            queryApplicationByJob.setInt(1, job.getId());
            ResultSet result = queryApplicationByJob.executeQuery();
            while (result.next())
            {
                applications.add(parseApplication(result, userDB, userKeywordDB, locationDB, jobDB, jobKeywordDB, jobCategoryDB));
            }
            return applications;
        } catch (SQLException e)
        {
            System.out.println("Error querying applicationId = " + job.getId() + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Inserts an Application object into the SQL database by deconstructing the information in the Application object.
     *
     * @param application application as Application object.
     * @return application as Application Object.
     * @throws SQLException Handles SQL Exception.
     */
    public Application insertApplication(Application application) throws SQLException
    {
        if (application.getId() != -1)
            return application;
        else
        {
            insertApplication.setInt(1, application.getJob().getId());
            insertApplication.setString(2, application.getJobSeeker().getEmail());
            insertApplication.setString(3, application.getCoverLetterDir());
            insertApplication.setString(4, application.getResumeDir());
            insertApplication.setString(5, application.getStatus());
            insertApplication.setDate(6, application.getApplicationDate());
            int affectedRows = insertApplication.executeUpdate();
            if (affectedRows != 1)
                throw new SQLException("Error inserting Application");
            ResultSet generatedKey = insertApplication.getGeneratedKeys();
            if (generatedKey.next())
            {
                application.setId(generatedKey.getInt(1));
                return application;
            } else
            {
                throw new SQLException("Could not get inserted application Id");
            }
        }
    }

    /**
     * View strings
     */
    public static class View
    {
    }

    /**
     * Column name strings
     */
    public static class Column
    {
        public static final String ID = "id";
        public static final String JOBID = "jobId";
        public static final String USEREMAIL = "userEmail";
        public static final String COVERLETTERDIR = "coverLetterDir";
        public static final String RESUMEDIR = "resumeDir";
        public static final String STATUS = "status";
        public static final String DATE = "date";
    }

    /**
     * Query strings
     */
    public static class Query
    {
        public static final String APPLICATION = "SELECT * FROM " + NAME + " WHERE " + ID + " = ?";
        public static final String APPLICATION_BY_JOB = "SELECT * FROM " + NAME + " WHERE " + JOBID + " = ?";
    }

    /**
     * Insert strings
     */
    public static class Insert
    {
        public static final String APPLICATION = "INSERT INTO " + NAME + " (" + JOBID + ", " + USEREMAIL + ", " + COVERLETTERDIR + ", " + RESUMEDIR + ", " + STATUS + ", " + DATE + ") VALUES (?, ?, ?, ?, ?, ?)";
    }

    /**
     * Update strings
     */
    public static class Update
    {
    }

    /**
     * Delete strings
     */
    public static class Delete
    {
    }
}

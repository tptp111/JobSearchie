package Database;

import Entities.Job;

import java.sql.*;
import java.util.ArrayList;

import static Database.JobKeywordDB.Column.*;
import static Database.KeywordDB.getKeyword;
import static Database.KeywordDB.insertKeyword;

/**
 * Establishes connection to the job_keyword table in SQL.
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version 1.0
 */
public class JobKeywordDB implements DBHelper
{
    public static final String NAME = "job_keyword";

    /**
     * Prepared statement that will return all keywordId's associated with a given jobId. Used to populate
     * a job keywords.
     */
    private final PreparedStatement queryJobKeywords;
    /**
     * Prepared statement that will insert an entry into the job_keyword table given a jobId and KeywordId.
     */
    private final PreparedStatement insertJobKeyword;

    /**
     * Establishes connection to SQL database.
     *
     * @param conn conn as Connection
     * @throws SQLException Handles SQL Exception
     */
    public JobKeywordDB(Connection conn) throws SQLException
    {
        queryJobKeywords = conn.prepareStatement(Query.JOB_KEYWORD, Statement.RETURN_GENERATED_KEYS);
        insertJobKeyword = conn.prepareStatement(Insert.JOB_KEYWORD, Statement.RETURN_GENERATED_KEYS);
    }

    @Override
    public void close() throws SQLException
    {

        if (queryJobKeywords != null)
            queryJobKeywords.close();
        if (insertJobKeyword != null)
            insertJobKeyword.close();
    }

    /**
     * TESTED
     * Returns a list of the keyword Id's associated with a given userId. May return null if no keywords are associated
     * otherwise will retrun an arraylist of integers which represent the keyword ids associated with a given user.
     *
     * @param jobId The user email to be checked against.
     * @return Returns an ArrayList of keyword ids if 1 or more exist or null of none exist.
     */
    private ArrayList<Integer> getJobKeywordIds(int jobId)
    {
        ArrayList<Integer> keywordIds = new ArrayList<>();
        try
        {
            queryJobKeywords.setInt(1, jobId);
            ResultSet results = queryJobKeywords.executeQuery();
            while (results.next())
            {
                keywordIds.add(results.getInt(JobKeywordDB.Column.KEYWORDID));
            }
            return keywordIds.size() == 0 ? null : keywordIds;
        } catch (SQLException e)
        {
            System.out.println("Error querying job_keyword table to get keywordIds: " + e.getMessage());
            return null;
        }
    }

    /**
     * TESTED
     * Gets all the keywords associated with the given user.
     *
     * @param jobId The userEmail of who you would like to get all associated keywords.
     * @return Returns an ArrayList of strings which represent th users keywords. Returns null if no keywords are
     * associated with the userId.
     * given.
     */
    public ArrayList<String> getJobKeywords(int jobId)
    {
        ArrayList<Integer> keywordIds = getJobKeywordIds(jobId);
        if (keywordIds != null)
        {
            ArrayList<String> keywords = new ArrayList<>();
            keywordIds.forEach(id -> keywords.add(getKeyword(id)));
            return keywords;
        } else
            return null;
    }

    /**
     * TESTED
     * Inserts a user keyword into the user_keyword table. Checks to see if the keyword exists, if not will be added.
     * Then checks to see if userKeyword combonation exists, addes it if not.
     *
     * @param jobId   The userEmail to match the keyword against.
     * @param keyword The keywordId to associate with the user.
     * @throws SQLException Throws SQLException if the given combination cannot be inserted. Would be thrown if the
     *                      pair already exists, but this shouldn't happen as its already checked.
     */
    private void insertJobKeyword(int jobId, String keyword) throws SQLException
    {
        int keywordId = insertKeyword(keyword);
        ArrayList<Integer> userKeywordIds = getJobKeywordIds(jobId);
        if (userKeywordIds == null || !userKeywordIds.contains(keywordId))
        {
            insertJobKeyword.setInt(1, jobId);
            insertJobKeyword.setInt(2, keywordId);
            int affectedRows = insertJobKeyword.executeUpdate();
            if (affectedRows != 1)
                throw new SQLException("Couldn't insert job keyword.");
        }
    }

    /**
     * TESTED
     * Inserts all user keywords into the user_keyword table. Uses insertKeyword function.
     *
     * @param job The job seeker who's keywords will be added against.
     */
    public void insertJobKeywords(Job job)
    {
        int jobId = job.getId();
        job.getKeywords().forEach(keyword ->
        {
            try
            {
                insertJobKeyword(jobId, keyword);
            } catch (SQLException e)
            {
                System.out.println("Error inserting job keywords: " + e.getMessage());
            }
        });
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
        public static final String JOBID = "jobId";
        public static final String KEYWORDID = "keywordId";
    }

    /**
     * Query strings
     */
    public static class Query
    {
        public static final String CHECK_USER_KEYWORD = "SELECT * FROM " + NAME + " WHERE " + JOBID + " = ? AND " + KEYWORDID + " = ?";
        public static final String JOB_KEYWORD = "SELECT * FROM " + NAME + " WHERE " + JOBID + " = ?";

    }

    /**
     * Insert strings
     */
    public static class Insert
    {
        public static final String JOB_KEYWORD = "INSERT INTO " + NAME + " (" + JOBID + ", " + KEYWORDID + ") VALUES (?, ?)";

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

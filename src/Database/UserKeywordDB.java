package Database;

import Entities.JobSeeker;
import Entities.User;

import java.sql.*;
import java.util.ArrayList;

import static Database.KeywordDB.getKeyword;
import static Database.KeywordDB.insertKeyword;
import static Database.UserKeywordDB.Column.KEYWORDID;
import static Database.UserKeywordDB.Column.USEREMAIL;

/**
 * Establishes connection to the user_keyword table in SQL.
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version 1.0
 */
public class UserKeywordDB implements DBHelper
{

    public static final String NAME = "user_keyword";

    /**
     * Prepared statement that will return all keyword ID's associated with a given user ID. Used to populate
     * a users keywords.
     *
     * @see UserKeywordDB.Query
     */
    private final PreparedStatement queryUserKeywords;
    /**
     * Prepared statement that will insert an entry into the user_keyword table given a UserId and KeywordId.
     *
     * @see UserKeywordDB.Insert
     */
    private final PreparedStatement insertUserKeyword;

    /**
     * Default constructor which creates the object of the class UserKeywordDB.
     *
     * @param conn Accepts the driver's full name as a string.
     * @throws SQLException If there is an access error with the database.
     */
    public UserKeywordDB(Connection conn) throws SQLException
    {
        queryUserKeywords = conn.prepareStatement(UserKeywordDB.Query.USER_KEYWORD, Statement.RETURN_GENERATED_KEYS);
        insertUserKeyword = conn.prepareStatement(UserKeywordDB.Insert.USER_KEYWORD, Statement.RETURN_GENERATED_KEYS);
    }

    @Override
    public void close() throws SQLException
    {

        if (queryUserKeywords != null)
            queryUserKeywords.close();
        if (insertUserKeyword != null)
            insertUserKeyword.close();
    }

    /**
     * TESTED
     * Returns a list of the keyword Id's associated with a given userId. May return null if no keywords are associated
     * otherwise will retrun an arraylist of integers which represent the keyword ids associated with a given user.
     *
     * @param user The user email to be checked against.
     * @return Returns an ArrayList of keyword ids if 1 or more exist or null of none exist.
     */
    private ArrayList<Integer> getUserKeywordIds(User user)
    {
        ArrayList<Integer> keywordIds = new ArrayList<>();
        try
        {
            queryUserKeywords.setString(1, user.getEmail());
            ResultSet results = queryUserKeywords.executeQuery();
            while (results.next())
            {
                keywordIds.add(results.getInt(UserKeywordDB.Column.KEYWORDID));
            }
            return keywordIds.size() == 0 ? null : keywordIds;
        } catch (SQLException e)
        {
            System.out.println("Error querying user_keyword table to get keywordIds: " + e.getMessage());
            return null;
        }
    }

    /**
     * TESTED
     * Gets all the keywords associated with the given user.
     *
     * @param user The userEmail of who you would like to get all associated keywords.
     * @return Returns an ArrayList of strings which represent th users keywords. Returns null if no keywords are
     * associated with the userId.
     * given.
     */
    public ArrayList<String> getUserKeywords(User user)
    {
        ArrayList<Integer> keywordIds = getUserKeywordIds(user);
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
     * Inserts all user keywords into the user_keyword table. Uses insertKeyword function.
     *
     * @param jobSeeker The job seeker whos keywords will be added against.
     */
    public void insertJobSeekerKeywords(JobSeeker jobSeeker)
    {
        jobSeeker.getKeywords().forEach(keyword ->
        {
            try
            {
                insertUserKeyword(jobSeeker, keyword);
            } catch (SQLException e)
            {
                System.out.println("Error inserting user keywords: " + e.getMessage());
            }
        });
    }

    /**
     * TESTED
     * Inserts a user keyword into the user_keyword table. Checks to see if the keyword exists, if not will be added.
     * Then checks to see if userKeyword combonation exists, addes it if not.
     *
     * @param user    The userEmail to match the keyword against.
     * @param keyword The keywordId to associate with the user.
     * @throws SQLException Throws SQLException if the given combination cannot be inserted. Would be thrown if the
     *                      pair already exists, but this shouldn't happen as its already checked.
     */
    private void insertUserKeyword(User user, String keyword) throws SQLException
    {
        int keywordId = insertKeyword(keyword);
        ArrayList<Integer> userKeywordIds = getUserKeywordIds(user);
        if (userKeywordIds == null || !userKeywordIds.contains(keywordId))
        {
            insertUserKeyword.setString(1, user.getEmail());
            insertUserKeyword.setInt(2, keywordId);
            int affectedRows = insertUserKeyword.executeUpdate();
            if (affectedRows != 1)
                throw new SQLException("Couldn't insert user keyword.");
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
        public static final String USEREMAIL = "userEmail";
        public static final String KEYWORDID = "keywordId";
    }

    /**
     * Query strings
     */
    public static class Query
    {
        public static final String CHECK_USER_KEYWORD = "SELECT * FROM " + NAME + " WHERE " + USEREMAIL + " = ? AND " + KEYWORDID + " = ?";
        public static final String USER_KEYWORD = "SELECT * FROM " + NAME + " WHERE " + USEREMAIL + " = ?";

    }

    /**
     * Insert strings
     */
    public static class Insert
    {
        public static final String USER_KEYWORD = "INSERT INTO " + NAME + " (" + USEREMAIL + ", " + KEYWORDID + ") VALUES (?, ?)";

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

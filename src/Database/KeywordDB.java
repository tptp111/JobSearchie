package Database;

import java.sql.*;

import static Database.KeywordDB.Column.ID;
import static Database.KeywordDB.Column.KEYWORD;

/**
 * Establishes connection to the keyword table in SQL.
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version 1.0
 */
public class KeywordDB implements DBHelper
{

    public static final String NAME = "keyword";
    /**
     * Prepared statement that will search for a keyword given its Id.
     *
     * @see KeywordDB.Query
     */
    private static PreparedStatement queryKeyword;
    /**
     * Prepared statement that will insert a keyword into the keyword table.
     *
     * @see KeywordDB.Insert
     */
    private static PreparedStatement insertKeyword;
    /**
     * Prepared statement that will search for a Keyword ID in the database given a keyword. Will return all
     * keyword ID's with that keyword.
     *
     * @see KeywordDB.Query
     */
    private static PreparedStatement queryKeywordId;

    /**
     * Establishes connection to SQL database.
     *
     * @param conn conn as Connection
     * @throws SQLException Handles SQL Exception
     */
    public KeywordDB(Connection conn) throws SQLException
    {
        insertKeyword = conn.prepareStatement(KeywordDB.Insert.INSERT_KEYWORD, Statement.RETURN_GENERATED_KEYS);
        queryKeyword = conn.prepareStatement(KeywordDB.Query.KEYWORD, Statement.RETURN_GENERATED_KEYS);
        queryKeywordId = conn.prepareStatement(KeywordDB.Query.KEYWORD_ID, Statement.RETURN_GENERATED_KEYS);
    }

    /**
     * TESTED
     * Queries the keyword table to retrun the keyword associated the keyword id.
     *
     * @param id The keyword Id that is to be returned.
     * @return Returns the keyword if it exists and null if it doesn't exist.
     */
    public static String getKeyword(int id)
    {
        try
        {
            queryKeyword.setInt(1, id);
            ResultSet results = queryKeyword.executeQuery();
            if (results.next())
            {
                return results.getString(KeywordDB.Column.KEYWORD);
            } else
                return null;
        } catch (SQLException e)
        {
            System.out.println("Error querying keyword table to get keyword: " + e.getMessage());
            return null;
        }
    }

    /**
     * TESTED
     * Queries the keyword table to see if a keyword exists.
     *
     * @param keyword The keyword to query in the keyword table.
     * @return Return the keywords associated id or -1 if the keyword doesn't exist.
     */
    public static int getKeywordId(String keyword)
    {
        try
        {
            queryKeywordId.setString(1, keyword);
            ResultSet results = queryKeywordId.executeQuery();
            if (results.next())
            {
                return results.getInt(KeywordDB.Column.ID);
            } else
                return -1;
        } catch (SQLException e)
        {
            System.out.println("Error querying keyword table to get keywordId: " + e.getMessage());
            return -1;
        }
    }

    /**
     * TESTED
     * Inserts a keyword into the keyword table. Checks to see if that keyword already exists and inserts if it doesn't.
     *
     * @param keyword The keyword to be inserted.
     * @return The id of the keyword once it has been inserted or the id of the matching keyword.
     * @throws SQLException Throws an SQLException if the method cannot insert the keyword.
     */
    public static int insertKeyword(String keyword) throws SQLException
    {
        int id = getKeywordId(keyword);
        if (id != -1)
            return id;
        else
        {
            insertKeyword.setString(1, keyword);
            int affectedRows = insertKeyword.executeUpdate();
            if (affectedRows != 1)
            {
                throw new SQLException("Couldn't insert keyword, updated more than one row.");
            } else
            {
                ResultSet generatedKeys = insertKeyword.getGeneratedKeys();
                if (generatedKeys.next())
                    return generatedKeys.getInt(1);
                else
                    throw new SQLException("Couldn't get id from keyword after insert.");
            }
        }
    }

    @Override
    public void close() throws SQLException
    {
        if (queryKeyword != null)
            queryKeyword.close();
        if (insertKeyword != null)
            insertKeyword.close();
        if (queryKeywordId != null)
            queryKeywordId.close();
    }

    /**
     * Column name strings
     */
    public static class Column
    {
        public static final String ID = "id";
        public static final String KEYWORD = "keyword";
    }

    /**
     * Query strings
     */
    public static class Query
    {
        public static final String KEYWORD_ID = "SELECT " + ID + " FROM " + NAME + " WHERE " + Column.KEYWORD + " = ?";
        public static final String KEYWORD = "SELECT " + Column.KEYWORD + " FROM " + NAME + " WHERE " + ID + " = ?";
    }

    /**
     * Insert strings
     */
    public static class Insert
    {
        public static final String INSERT_KEYWORD = "INSERT INTO " + NAME + " (" + KEYWORD + ") VALUES (?)";
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

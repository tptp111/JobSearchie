package Database;

import java.sql.*;

import static Database.CategoryDB.Column.*;

/**
 * Establishes connection to the category table in SQL.
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version 1.0
 */
public class CategoryDB implements DBHelper
{

    public static final String NAME = "category";
    /**
     * Prepared statement that will search for a category given its Id.
     */
    private static PreparedStatement queryCategory;
    /**
     * Prepared statement that will insert a category into the category table.
     */
    private static PreparedStatement insertCategory;
    /**
     * Prepared statement that will search for a category ID in the database given a category. Will return all
     * category ID's with that category.
     */
    private static PreparedStatement queryCategoryId;

    /**
     * Establishes connection to SQL database.
     *
     * @param conn conn as Connection
     * @throws SQLException Handles SQL Exception
     */
    public CategoryDB(Connection conn) throws SQLException
    {
        insertCategory = conn.prepareStatement(CategoryDB.Insert.CATEGORY, Statement.RETURN_GENERATED_KEYS);
        queryCategory = conn.prepareStatement(CategoryDB.Query.CATEGORY, Statement.RETURN_GENERATED_KEYS);
        queryCategoryId = conn.prepareStatement(CategoryDB.Query.CATEGORY_ID, Statement.RETURN_GENERATED_KEYS);
    }

    /**
     * TESTED
     * Queries the category table to return the category associated the category id.
     *
     * @param id The category Id that is to be returned.
     * @return Returns the category if it exists and null if it doesn't exist.
     */
    public static String getCategory(int id)
    {
        try
        {
            queryCategory.setInt(1, id);
            ResultSet results = queryCategory.executeQuery();
            if (results.next())
            {
                return results.getString(CategoryDB.Column.CATEGORY);
            } else
                return null;
        } catch (SQLException e)
        {
            System.out.println("Error querying category table to get category: " + e.getMessage());
            return null;
        }
    }

    /**
     * TESTED
     * Queries the category table to see if a category exists.
     *
     * @param category The category to query in the category table.
     * @return Return the category associated id or -1 if the category doesn't exist.
     */
    public static int getCategoryId(String category)
    {
        try
        {
            queryCategoryId.setString(1, category);
            ResultSet results = queryCategoryId.executeQuery();
            if (results.next())
            {
                return results.getInt(CategoryDB.Column.ID);
            } else
                return -1;
        } catch (SQLException e)
        {
            System.out.println("Error querying category table to get categoryId: " + e.getMessage());
            return -1;
        }
    }

    /**
     * TESTED
     * Inserts a category into the category table. Checks to see if that category already exists and inserts if it doesn't.
     *
     * @param category The category to be inserted.
     * @return The id of the category once it has been inserted or the id of the matching category.
     * @throws SQLException Throws an SQLException if the method cannot insert the category.
     */
    public static int insertCategory(String category) throws SQLException
    {
        int id = getCategoryId(category);
        if (id != -1)
            return id;
        else
        {
            insertCategory.setString(1, category);
            int affectedRows = insertCategory.executeUpdate();
            if (affectedRows != 1)
            {
                throw new SQLException("Couldn't insert category, updated more than one row.");
            } else
            {
                ResultSet generatedKeys = insertCategory.getGeneratedKeys();
                if (generatedKeys.next())
                    return generatedKeys.getInt(1);
                else
                    throw new SQLException("Couldn't get id from category after insert.");
            }
        }
    }

    @Override
    public void close() throws SQLException
    {
        if (queryCategory != null)
            queryCategory.close();
        if (insertCategory != null)
            insertCategory.close();
        if (queryCategoryId != null)
            queryCategoryId.close();
    }

    /**
     * Column name strings
     */
    public static class Column
    {
        public static final String ID = "id";
        public static final String CATEGORY = "category";
    }

    /**
     * Query strings
     */
    public static class Query
    {
        public static final String CATEGORY_ID = "SELECT " + ID + " FROM " + NAME + " WHERE " + CategoryDB.Column.CATEGORY + " = ?";
        public static final String CATEGORY = "SELECT " + CategoryDB.Column.CATEGORY + " FROM " + NAME + " WHERE " + ID + " = ?";
    }

    /**
     * Insert strings
     */
    public static class Insert
    {
        public static final String CATEGORY = "INSERT INTO " + NAME + " (" + CategoryDB.Column.CATEGORY + ") VALUES (?)";
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

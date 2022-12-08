package Database;

import Entities.Job;

import java.sql.*;
import java.util.ArrayList;

import static Database.JobCategoryDB.Column.*;
import static Database.CategoryDB.getCategory;
import static Database.CategoryDB.insertCategory;

/**
 * Establishes connection to the job_category table in SQL.
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version 1.0
 */
public class JobCategoryDB implements DBHelper
{

    public static final String NAME = "job_category";

    /**
     * Prepared statement that will return all categoryId's associated with a given jobId. Used to populate
     * a job categories.
     */
    private final PreparedStatement queryJobCategories;
    /**
     * Prepared statement that will insert an entry into the job_category table given a jobId and categoryId.
     */
    private final PreparedStatement insertJobCategory;

    /**
     * Establishes connection to SQL database.
     *
     * @param conn conn as Connection
     * @throws SQLException Handles SQL Exception
     */
    public JobCategoryDB(Connection conn) throws SQLException
    {
        queryJobCategories = conn.prepareStatement(JobCategoryDB.Query.JOB_category, Statement.RETURN_GENERATED_KEYS);
        insertJobCategory = conn.prepareStatement(JobCategoryDB.Insert.JOB_category, Statement.RETURN_GENERATED_KEYS);
    }

    @Override
    public void close() throws SQLException
    {

        if (queryJobCategories != null)
            queryJobCategories.close();
        if (insertJobCategory != null)
            insertJobCategory.close();
    }

    /**
     * TESTED
     * Gets all the categories associated with the given user.
     *
     * @param jobId The userEmail of who you would like to get all associated categories.
     * @return Returns an ArrayList of strings which represent th users categories. Returns null if no categories are
     * associated with the userId.
     * given.
     */
    public ArrayList<String> getJobCategories(int jobId)
    {
        ArrayList<Integer> categoryIds = getJobCategoryIds(jobId);
        if (categoryIds != null)
        {
            ArrayList<String> categories = new ArrayList<>();
            categoryIds.forEach(id -> categories.add(getCategory(id)));
            return categories;
        } else
            return null;
    }

    /**
     * TESTED
     * Returns a list of the category Id's associated with a given userId. May return null if no categories are associated
     * otherwise will retrun an arraylist of integers which represent the category ids associated with a given user.
     *
     * @param jobId The user email to be checked against.
     * @return Returns an ArrayList of category ids if 1 or more exist or null of none exist.
     */
    private ArrayList<Integer> getJobCategoryIds(int jobId)
    {
        ArrayList<Integer> categoryIds = new ArrayList<>();
        try
        {
            queryJobCategories.setInt(1, jobId);
            ResultSet results = queryJobCategories.executeQuery();
            while (results.next())
            {
                categoryIds.add(results.getInt(JobCategoryDB.Column.categoryID));
            }
            return categoryIds.size() == 0 ? null : categoryIds;
        } catch (SQLException e)
        {
            System.out.println("Error querying job_category table to get categoryIds: " + e.getMessage());
            return null;
        }
    }

    /**
     * TESTED
     * Inserts all user categories into the user_category table. Uses insertcategory function.
     *
     * @param job The job seeker who's categories will be added against.
     */
    public void insertJobCategories(Job job)
    {
        int jobId = job.getId();
        job.getCategories().forEach(category ->
        {
            try
            {
                insertJobCategory(jobId, category);
            } catch (SQLException e)
            {
                System.out.println("Error inserting job categories: " + e.getMessage());
            }
        });
    }

    /**
     * TESTED
     * Inserts a user category into the user_category table. Checks to see if the category exists, if not will be added.
     * Then checks to see if userCategory combination exists, adds it if not.
     *
     * @param jobId    The userEmail to match the category against.
     * @param category The categoryId to associate with the user.
     * @throws SQLException Throws SQLException if the given combination cannot be inserted. Would be thrown if the
     *                      pair already exists, but this shouldn't happen as its already checked.
     */
    private void insertJobCategory(int jobId, String category) throws SQLException
    {
        int categoryId = insertCategory(category);
        ArrayList<Integer> userCategoryIds = getJobCategoryIds(jobId);
        if (userCategoryIds == null || !userCategoryIds.contains(categoryId))
        {
            insertJobCategory.setInt(1, jobId);
            insertJobCategory.setInt(2, categoryId);
            int affectedRows = insertJobCategory.executeUpdate();
            if (affectedRows != 1)
                throw new SQLException("Couldn't insert job category.");
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
        public static final String JOBID = "jobId";
        public static final String categoryID = "categoryId";
    }

    /**
     * Query strings
     */
    public static class Query
    {
        public static final String CHECK_USER_category = "SELECT * FROM " + NAME + " WHERE " + JOBID + " = ? AND " + categoryID + " = ?";
        public static final String JOB_category = "SELECT * FROM " + NAME + " WHERE " + JOBID + " = ?";

    }

    /**
     * Insert strings
     */
    public static class Insert
    {
        public static final String JOB_category = "INSERT INTO " + NAME + " (" + JOBID + ", " + categoryID + ") VALUES (?, ?)";

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

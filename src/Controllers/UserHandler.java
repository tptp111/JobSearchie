package Controllers;

import Database.DatabaseManager;
import Entities.User;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller class for the User entity.
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version ver1.0.0
 */
public abstract class UserHandler
{
    public void home(User user, DatabaseManager db) throws SQLException, IOException
    {
    }
}
package Database;

import java.sql.SQLException;

/**
 * Database helper class
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version 1.0
 */
public interface DBHelper
{
    void close() throws SQLException;
}

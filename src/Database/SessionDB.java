package Database;

import Entities.Session;

import java.sql.*;

import static Database.SessionDB.Column.*;

/**
 * Establishes connection to the session table in SQL.
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version 1.0
 */
public class SessionDB implements DBHelper
{

    public static final String NAME = "session";

    /**
     * Prepared statement that will insert a session into the session table.
     *
     * @see SessionDB.Insert
     */
    private static PreparedStatement insertSession;

    private static PreparedStatement updateSession;

    /**
     * Establishes connection to SQL database.
     *
     * @param conn conn as Connection
     * @throws SQLException Handles SQL Exception
     */
    public SessionDB(Connection conn) throws SQLException
    {
        insertSession = conn.prepareStatement(SessionDB.Insert.SESSION, Statement.RETURN_GENERATED_KEYS);
        updateSession = conn.prepareStatement(SessionDB.Update.SESSION, Statement.RETURN_GENERATED_KEYS);
    }

    @Override
    public void close() throws SQLException
    {
        if (insertSession != null)
            insertSession.close();
        if (updateSession != null)
            updateSession.close();
    }

    /**
     * TESTED
     * Inserts a session into the session table. Checks to see if that session already exists and inserts if it doesn't.
     *
     * @param session The session to be inserted.
     * @return The id of the session once it has been inserted or the id of the matching session.
     * @throws SQLException Throws an SQLException if the method cannot insert the session.
     */
    public Session insertSession(Session session) throws SQLException
    {
        if (session.getId() != -1)
            return session;
        else
        {
            insertSession.setString(1, session.getUser().getEmail());
            insertSession.setDate(2, session.getLoginTime());
            insertSession.setDate(3, session.getLogoutTime());
            int affectedRows = insertSession.executeUpdate();
            if (affectedRows != 1)
            {
                throw new SQLException("Couldn't insert session, updated more than one row.");
            } else
            {
                ResultSet generatedKeys = insertSession.getGeneratedKeys();
                if (generatedKeys.next())
                {
                    session.setId(generatedKeys.getInt(1));
                    return session;
                } else
                    throw new SQLException("Couldn't get id from session after insert.");
            }
        }
    }

    /**
     * Updates the session in the database.
     *
     * @param session as Session
     * @return session as Session
     * @throws SQLException Handles SQL Exception
     */
    public Session updateSession(Session session) throws SQLException
    {
        updateSession.setDate(1, session.getLogoutTime());
        updateSession.setInt(2, session.getId());
        int affectedRows = updateSession.executeUpdate();
        if (affectedRows != 1)
        {
            throw new SQLException("Couldn't update session, updated more than one row.");
        } else
        {
            return session;
        }

    }

    /**
     * Column name strings
     */
    public static class Column
    {
        public static final String ID = "id";
        public static final String USERID = "UserID";
        public static final String LOGINTIME = "logInTime";
        public static final String LOGOUTTIME = "logOutTime";
    }

    /**
     * Query strings
     */
    public static class Query
    {
    }

    /**
     * Insert strings
     */
    public static class Insert
    {
        public static final String SESSION = "INSERT INTO " + NAME + " (" + USERID + ", " + LOGINTIME + ", " + LOGOUTTIME + ") VALUES (?, ?, ?)";
    }

    /**
     * Update strings
     */
    public static class Update
    {
        public static final String SESSION = "UPDATE " + NAME + " SET " + LOGOUTTIME + " = ? WHERE " + ID + " = ?";
    }

    /**
     * Delete strings
     */
    public static class Delete
    {
    }
}

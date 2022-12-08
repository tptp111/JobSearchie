package Database;

import Entities.Invitation;

import java.sql.*;
import java.util.ArrayList;

import static Database.InvitationDB.Column.*;
import static Database.Parser.parseInvitation;

/**
 * Establishes connection to the invitation table in SQL.
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version 1.0
 */
public class InvitationDB implements DBHelper
{
    public static final String NAME = "invitation";
    /**
     * Prepared statement that will insert a Invitation into the invitation table.
     */
    private final PreparedStatement insertInvitation;
    /**
     * Prepared statement which will query a invitation and all it's atributes.
     */
    private final PreparedStatement queryInvitationById;
    private final PreparedStatement queryAllInvitations;

    /**
     * Establishes connection to SQL database.
     *
     * @param conn conn as Connection
     * @throws SQLException Handles SQL Exception
     */
    public InvitationDB(Connection conn) throws SQLException
    {
        insertInvitation = conn.prepareStatement(InvitationDB.Insert.INVITATION, Statement.RETURN_GENERATED_KEYS);
        queryInvitationById = conn.prepareStatement(InvitationDB.Query.INVITATION_BY_ID);
        queryAllInvitations = conn.prepareStatement(InvitationDB.Query.ALL_INVITATIONS);
    }

    /**
     * Closes all prepared statements.
     *
     * @throws SQLException Throws an SQLException if a prepared statement is unable to be closed.
     */
    @Override
    public void close() throws SQLException
    {
        if (insertInvitation != null)
            insertInvitation.close();
        if (queryInvitationById != null)
            queryInvitationById.close();
        if (queryAllInvitations != null)
            queryAllInvitations.close();
    }

    /**
     * Gets invitations from the SQL database by querying the information to construct the list of Invitation objects.
     *
     * @param userDB        userDB as UserDB
     * @param userKeywordDB userKeywordDB as UserKeywordDB
     * @param locationDB    locationDB as LocationDB
     * @param jobDB         jobDB as JobDB
     * @param jobKeywordDB  jobKeywordDB as JobKeywordDB
     * @param jobCategoryDB jobCategoryDB as JobCategoryDB
     * @return applications as ArrayList<Application>
     */
    public ArrayList<Invitation> getAllInvitations(UserDB userDB, LocationDB locationDB, JobDB jobDB, UserKeywordDB userKeywordDB, JobKeywordDB jobKeywordDB, JobCategoryDB jobCategoryDB)
    {
        try
        {
            ArrayList<Invitation> invitations = new ArrayList<>();
            ResultSet results = queryAllInvitations.executeQuery();
            while (results.next())
            {
                invitations.add(parseInvitation(results, userDB, locationDB, jobDB, userKeywordDB, jobKeywordDB, jobCategoryDB));
            }
            return invitations;
        } catch (SQLException e)
        {
            System.out.println("Error querying all invitations: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets invitation from the SQL database by querying the information to construct the Invitation object.
     *
     * @param invitationId  invitation id as int
     * @param userDB        userDB as UserDB
     * @param userKeywordDB userKeywordDB as UserKeywordDB
     * @param locationDB    locationDB as LocationDB
     * @param jobDB         jobDB as JobDB
     * @param jobKeywordDB  jobKeywordDB as JobKeywordDB
     * @param jobCategoryDB jobCategoryDB as JobCategoryDB
     * @return invitation as Invitation
     */
    public Invitation getInvitation(int invitationId, UserDB userDB, LocationDB locationDB, JobDB jobDB, UserKeywordDB userKeywordDB, JobKeywordDB jobKeywordDB, JobCategoryDB jobCategoryDB)
    {
        try
        {
            queryInvitationById.setInt(1, invitationId);
            ResultSet result = queryInvitationById.executeQuery();
            if (result.next())
                return parseInvitation(result, userDB, locationDB, jobDB, userKeywordDB, jobKeywordDB, jobCategoryDB);
            return null;
        } catch (SQLException e)
        {
            System.out.println("Error querying invitationId = " + invitationId + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Inserts an invitation into the database. If any of the invitation foreign keys don't exist such as location, them location
     * will be inserted into the database.
     *
     * @param invitation The invitation to be inserted into the database.
     * @param locationDB LocationBD helper class used to insert or get invitation location.
     * @param userDB     InvitationKeywordDB helper class used to insert invitation keywords.
     * @param jobDB      InvitationCategoryDB helper class used to insert invitation categories.
     * @return Returns the invitation that has been inserted into the database.
     * @throws SQLException Throws an SQLException if invitation cannot be inserted.
     */
    public Invitation insertInvitation(Invitation invitation, UserDB userDB, LocationDB locationDB, JobDB jobDB) throws SQLException
    {
        if (invitation.getId() != -1)
        {
            return invitation;
        } else
        {
            invitation.setLocationOfInterview(locationDB.insertLocation(invitation.getLocationOfInterview()));

            insertInvitation.setString(1, invitation.getJobSeeker().getEmail());
            insertInvitation.setString(2, invitation.getRecruiter().getEmail());
            insertInvitation.setInt(3, invitation.getJob().getId());
            insertInvitation.setDate(4, invitation.getDateSent());
            insertInvitation.setDate(5, invitation.getDateOfInterview());
            insertInvitation.setInt(6, invitation.getLocationOfInterview().getId());
            insertInvitation.setString(7, invitation.getAttachedMessage());
            insertInvitation.setString(8, invitation.getTypeOfInterview());
            insertInvitation.setBoolean(9, invitation.isAccepted());
            int affectedRows = insertInvitation.executeUpdate();
            if (affectedRows != 1)
                throw new SQLException("Error inserting Invitation Seeker");
            ResultSet generatedKey = insertInvitation.getGeneratedKeys();
            if (generatedKey.next())
            {
                invitation.setId(generatedKey.getInt(1));
                return invitation;
            } else
                throw new SQLException("Could not get inserted invitation Id");
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
        public static final String JOBSEEKEREMAIL = "jobSeekerEmail";
        public static final String RECRUITEREMAIL = "recruiterEmail";
        public static final String JOBID = "jobId";
        public static final String DATESENT = "dateSent";
        public static final String DATEOFINTERVIEW = "dateOfInterview";
        public static final String LOCATIONID = "locationId";
        public static final String MESSAGE = "message";
        public static final String TYPE = "type";
        public static final String ACCEPTED = "accepted";
    }

    /**
     * Query strings
     */
    public static class Query
    {
        public static final String INVITATION_BY_ID = "SELECT * FROM " + NAME + " WHERE " + ID + " = ?";
        public static final String ALL_INVITATIONS = "SELECT * FROM " + NAME + " ORDER BY " + DATEOFINTERVIEW + " ASC";
    }

    /**
     * Insert strings
     */
    public static class Insert
    {
        public static final String INVITATION = "INSERT INTO " + NAME + " (" + JOBSEEKEREMAIL + ", " + RECRUITEREMAIL + ", " + JOBID + ", " + DATESENT + ", " + DATEOFINTERVIEW + ", " + LOCATIONID + ", " + MESSAGE + ", " + TYPE + ", " + ACCEPTED + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

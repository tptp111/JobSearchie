package Entities;

import java.sql.Date;

/**
 * An entity class (child class of User class) which stores information for the administrator user by extending
 * the user class.
 *
 * @author Team R
 * @version 1.0
 */
public class Admin extends User
{

    /**
     * Default constructor which creates the object of the class Administrator by extending the User class.
     */
    public Admin()
    {
        super("Admin");
    }

    /**
     * Non-default constructor which creates the object of the class Administrator by extending the User class.
     *
     * @param firstName   Accepts the administrator's first name as a string.
     * @param lastName    Accepts the administrator's last name as a string.
     * @param email       Accepts the administrator's email as a string.
     * @param password    Accepts the administrator's password as a string.
     * @param dateCreated Accepts the administrators profile creation date as a Date object datatype.
     */
    public Admin(String firstName, String lastName, String email, String password, Date dateCreated)
    {
        super("Admin", firstName, lastName, email, password, dateCreated);
    }

    /**
     * This method creates an administrator object for testing by calling the admin non-default constructor.
     *
     * @return The state of the object as a string.
     */
    public static Admin generateAdminForTesting()
    {
        return new Admin("James", "Bond", "james@bond.com", "LiveAndLetDie", new Date(System.currentTimeMillis()));
    }
}

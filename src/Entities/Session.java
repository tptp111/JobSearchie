package Entities;

import java.sql.Date;

/**
 * An entity class which stores information for the user session while using Job Searchie.
 *
 * @author Team R
 * @version 1.0
 */
public class Session
{
    private int id;
    private User user;
    private Date loginTime;
    private Date logoutTime;

    /**
     * Default constructor which creates the object of the Session class.
     */
    public Session()
    {
        id = -1;
        user = null;
        loginTime = new Date(System.currentTimeMillis());
        logoutTime = null;
    }

    /**
     * Non-default constructor which creates the object of the Session class
     *
     * @param userLoggedIn Accepts the details of the user that is logged in as an object of the User class.
     */
    public Session(User userLoggedIn)
    {
        id = -1;
        user = userLoggedIn;
        loginTime = new Date(System.currentTimeMillis());
        logoutTime = null;
    }

    /**
     * Non-default constructor which creates the object of the Session class
     *
     * @param userLoggedIn Accepts the details of the user that is logged in as an object of the User class.
     * @param loginTime    Accepts the session's login time as a Date object datatype.
     * @param logoutTime   Accepts the session's logout time as a Date object datatype.
     */
    public Session(User userLoggedIn, Date loginTime, Date logoutTime)
    {
        id = -1;
        this.user = userLoggedIn;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    /**
     * Non-default constructor which creates the object of the Session class
     *
     * @param id           Accepts the identification number of the session as an integer.
     * @param userLoggedIn Accepts the details of the user that is logged in as an object of the User class.
     * @param loginTime    Accepts the session's login time as a Date object datatype.
     * @param logoutTime   Accepts the session's logout time as a Date object datatype.
     */
    public Session(int id, User userLoggedIn, Date loginTime, Date logoutTime)
    {
        this.id = id;
        this.user = userLoggedIn;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    /**
     * Accessor method to get the session's identification number.
     *
     * @return The identification number of the session as an integer.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Accessor method to get the session's login time.
     *
     * @return The session's login time as a Date object datatype.
     */
    public Date getLoginTime()
    {
        return loginTime;
    }

    /**
     * Accessor method to get the session's logout time.
     *
     * @return The session's logout time as a Date object datatype.
     */
    public Date getLogoutTime()
    {
        return logoutTime;
    }

    /**
     * Accessor method to get the session's user details.
     *
     * @return The details of the user that is logged into the session as an object of the User class.
     */
    public User getUser()
    {
        return user;
    }

    /**
     * Accessor method to get the session user's details.
     *
     * @return The details of the user that is logged in as an object of the User class.
     */
    public User getUserLoggedIn()
    {
        return user;
    }

    public String getUserType()
    {
        return user.getClass().toString().split("\\.")[1];
    }

    /**
     * Mutator method to set the session's identification number.
     *
     * @param id The identification number of the session as an integer.
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Mutator method to set the session's login time.
     *
     * @param loginTime The session's login time as a Date object datatype.
     */
    public void setLoginTime(Date loginTime)
    {
        this.loginTime = loginTime;
    }

    /**
     * Mutator method to set the session's logout time.
     *
     * @param logoutTime The session's logout time as a Date object datatype.
     */
    public void setLogoutTime(Date logoutTime)
    {
        this.logoutTime = logoutTime;
    }

    /**
     * Mutator method to set the session's user details.
     *
     * @param user The details of the user that is logged into the session as an object of the User class.
     */
    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * Mutator method to set the session user's details.
     *
     * @param userLoggedIn The details of the user that is logged in as an object of the User class.
     */
    public void setUserLoggedIn(User userLoggedIn)
    {
        this.user = userLoggedIn;
    }
}

package Entities;

import java.sql.Date;

/**
 * An entity class which stores information for the user.
 *
 * @author Team R
 * @version 1.0
 */
public class User
{
    private String accountType;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dateCreated;

    /**
     * Default constructor which creates the object of the User class.
     */
    public User()
    {
        accountType = "User";
        firstName = "";
        lastName = "";
        email = "";
        password = "";
        dateCreated = null;
    }

    /**
     * Non-default constructor which creates the object of the User class
     *
     * @param accountType Accepts the user's account type as a String.
     */
    public User(String accountType)
    {
        this.accountType = accountType;
        firstName = "";
        lastName = "";
        email = "";
        password = "";
        dateCreated = null;
    }

    /**
     * Non-default constructor which creates the object of the User class
     *
     * @param accountType Accepts the user's account type as a String.
     * @param email       Accepts the user's email as a String.
     */
    public User(String accountType, String email)
    {
        this.accountType = accountType;
        firstName = "";
        lastName = "";
        this.email = email;
        password = "";
        dateCreated = null;
    }

    /**
     * Non-default constructor which creates the object of the User class
     *
     * @param email       Accepts the user's email as a String.
     * @param password    Accepts the user's password as a String.
     * @param dateCreated Accepts the user's profile creation date as a Date object datatype.
     */
    public User(String email, String password, Date dateCreated)
    {
        this.accountType = null;
        this.firstName = null;
        this.lastName = null;
        this.email = email;
        this.password = password;
        this.dateCreated = dateCreated;
    }

    /**
     * Non-default constructor which creates the object of the User class
     *
     * @param accountType Accepts the user's account type as a String.
     * @param firstName   Accepts the user's first name as a String.
     * @param lastName    Accepts the user's last name as a String.
     * @param email       Accepts the user's email as a String.
     * @param password    Accepts the user's password as a String.
     * @param dateCreated Accepts the administrators profile creation date as a Date object datatype.
     */
    public User(String accountType, String firstName, String lastName, String email, String password, Date dateCreated)
    {
        this.accountType = accountType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateCreated = dateCreated;
    }

    /**
     * Display method to print the state of the object.
     */
    public void display()
    {
        System.out.println("accountType: " + accountType);
        System.out.println("firstName: " + firstName);
        System.out.println("lastName: " + lastName);
        System.out.println("email: " + email);
        System.out.println("password: " + password);
        System.out.println("dateCreated: " + dateCreated);
    }

    /**
     * Accessor method to get the user's account type.
     *
     * @return The user's account type as a string.
     */
    public String getAccountType()
    {
        return accountType;
    }

    /**
     * Accessor method to get the user's account creation date.
     *
     * @return The account creation date as a string.
     */
    public Date getDateCreated()
    {
        return dateCreated;
    }

    /**
     * Accessor method to get the user's email address.
     *
     * @return The user's email as a string.
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Accessor method to the get user's first name.
     *
     * @return The user's first name as a string.
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Accessor method to the get user's last name.
     *
     * @return The user's last name as a string.
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Accessor method to the get user's password.
     *
     * @return The user's password as a string.
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Mutator method to set the user's account type.
     *
     * @param accountType The user's account type as a String.
     */
    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    /**
     * Mutator method to set the user's account creation date.
     *
     * @param dateCreated The user's account creation date as a string.
     */
    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    /**
     * Mutator method to set the user's email.
     *
     * @param email The user's email as a string.
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Mutator method to set the user's first name.
     *
     * @param firstName The user's first name as a string.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Mutator method to set the user's last name.
     *
     * @param lastName The user's last name as a string.
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Mutator method to set the users password.
     *
     * @param password The user's password as a string.
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
}

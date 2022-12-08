package Entities;

import java.sql.Date;

/**
 * An entity class (child class of User class) which stores information for the recruiter user by extending
 * the user class.
 *
 * @author Team R
 * @version 1.0
 */
public class Recruiter extends User
{
    private String companyName;
    private String recruitingSpecialty;
    private String contactNumber;
    private Date dateOfBirth;

    /**
     * Default constructor which creates the object of the class Recruiter partly by extending the User class.
     */
    public Recruiter()
    {
        super("Recruiter");
        companyName = "";
        recruitingSpecialty = "";
        contactNumber = "";
        dateOfBirth = null;
    }

    /**
     * Non-default constructor which creates the object of the class Recruiter partly by extending the User class.
     *
     * @param email Accepts the recruiter's email as a string.
     */
    public Recruiter(String email)
    {
        super("Recruiter", email);
        companyName = "";
        recruitingSpecialty = "";
        contactNumber = "";
        dateOfBirth = null;
    }

    /**
     * Non-default constructor which creates the object of the class Recruiter partly by extending the User class.
     *
     * @param firstName   Accepts the recruiter's first name as a string.
     * @param lastName    Accepts the recruiter's last name as a string.
     * @param email       Accepts the recruiter's email address as a string.
     * @param password    Accepts the recruiter's password as a string.
     * @param dateCreated Accepts the recruiter's profile creation date as a Date object datatype.
     */
    public Recruiter(String firstName, String lastName, String email, String password, Date dateCreated)
    {
        super("Recruiter", firstName, lastName, email, password, dateCreated);
        companyName = "";
        recruitingSpecialty = "";
        contactNumber = "";
        dateOfBirth = null;
    }

    /**
     * Non-default constructor which creates the object of the class Recruiter partly by extending the User class.
     *
     * @param firstName           Accepts the recruiter's first name as a string.
     * @param lastName            Accepts the recruiter's last name as a string.
     * @param email               Accepts the recruiter's email address as a string.
     * @param password            Accepts the recruiter's password as a string.
     * @param dateCreated         Accepts the recruiter's profile creation date as a Date object datatype.
     * @param companyName         Accepts the recruiter's company name as a string.
     * @param recruitingSpecialty Accepts the recruiter's recruiting specialty as a string.
     * @param contactNumber       Accepts the recruiter's contact number as a string.
     * @param dateOfBirth         Accepts the recruiter's date of birth as a Date object datatype.
     */
    public Recruiter(String firstName, String lastName, String email, String password, Date dateCreated, String companyName, String recruitingSpecialty, String contactNumber, Date dateOfBirth)
    {
        super("Recruiter", firstName, lastName, email, password, dateCreated);
        this.companyName = companyName;
        this.recruitingSpecialty = recruitingSpecialty;
        this.contactNumber = contactNumber;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Display method to print the state of the object.
     */
    public void display()
    {
        super.display();
        System.out.println("companyName: " + companyName);
        System.out.println("recruitingSpecialty: " + recruitingSpecialty);
        System.out.println("contactNumber: " + contactNumber);
        System.out.println("dateOfBirth: " + dateOfBirth);
    }

    /**
     * Accessor method to get the recruiter's company name.
     *
     * @return The recruiter's company name as a string.
     */
    public String getCompanyName()
    {
        return companyName;
    }

    /**
     * Accessor method to get the recruiter's contact number.
     *
     * @return The recruiter's contact number as a string.
     */
    public String getContactNumber()
    {
        return contactNumber;
    }

    /**
     * Accessor method to get the recruiter's date of birth.
     *
     * @return The recruiter's date of birth as a Date object datatype.
     */
    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    /**
     * Accessor method to get the recruiter's recruiting specialty.
     *
     * @return The recruiter's recruiting specialty as a string.
     */
    public String getRecruitingSpecialty()
    {
        return recruitingSpecialty;
    }

    /**
     * Mutator method to set the recruiter's company name.
     *
     * @param companyName The recruiter's company name as a string.
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
     * Mutator method to set the recruiter's contact number.
     *
     * @param contactNumber The recruiter's contact number as a string.
     */
    public void setContactNumber(String contactNumber)
    {
        this.contactNumber = contactNumber;
    }

    /**
     * Mutator method to set the recruiter's date of birth.
     *
     * @param dateOfBirth The recruiter's date of birth as a Date object datatype.
     */
    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Mutator method to set the recruiter's recruiting specialty.
     *
     * @param recruitingSpecialty The recruiter's recruiting specialty as a string.
     */
    public void setRecruitingSpecialty(String recruitingSpecialty)
    {
        this.recruitingSpecialty = recruitingSpecialty;
    }
}

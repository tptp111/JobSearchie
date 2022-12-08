package Entities;

import Utilities.UserIO;

import java.util.ArrayList;
import java.sql.Date;
import java.util.LinkedHashMap;

/**
 * An entity class (child class of User class) which stores information for the job seeker user by extending
 * the user class.
 *
 * @author Team R
 * @version 1.0
 */
public class JobSeeker extends User
{
    private String currentJobName;
    private String currentJobLevel;
    private Location location;
    private String contactNumber;
    private Date dateOfBirth;
    private int expectedCompensation;
    private ArrayList<String> keywords;
    private String resumeContent;

    /**
     * Default constructor which creates the object of the class JobSeeker partly by extending the User class.
     */
    public JobSeeker()
    {
        super("Job Seeker");
        currentJobName = "";
        currentJobLevel = "";
        contactNumber = "";
        resumeContent = "";
        location = null;
        dateOfBirth = null;
        keywords = null;
        expectedCompensation = -1;
    }

    /**
     * Non-default constructor which creates the object of the class JobSeeker partly by extending the User class.
     *
     * @param firstName   Accepts the job seeker's first name as a string.
     * @param lastName    Accepts the job seeker's  last name as a string.
     * @param email       Accepts the job seeker's  email address as a string.
     * @param password    Accepts the job seeker's  password as a string.
     * @param dateCreated Accepts the job seeker's  profile creation date as a Date object datatype.
     */
    public JobSeeker(String firstName, String lastName, String email, String password, Date dateCreated)
    {
        super("Job Seeker", firstName, lastName, email, password, dateCreated);
        currentJobName = "";
        currentJobLevel = "";
        contactNumber = "";
        resumeContent = "";
        location = null;
        dateOfBirth = null;
        keywords = null;
        expectedCompensation = -1;
    }

    /**
     * Non-default constructor which creates the object of the class JobSeeker partly by extending the User class.
     *
     * @param firstName            Accepts the job seeker's first name as a string.
     * @param lastName             Accepts the job seeker's last name as a string.
     * @param email                Accepts the job seeker's email address as a string.
     * @param password             Accepts the job seeker's password as a string.
     * @param dateCreated          Accepts the job seeker's profile creation date as a Date object datatype.
     * @param currentJobName       Accepts the job seeker's job name as a string.
     * @param currentJobLevel      Accepts the job seeker's job level as a string.
     * @param contactNumber        Accepts the job seeker's contact number as a string.
     * @param resumeContent        Accepts the job seeker's resume directory as a string.
     * @param location             Accepts the job seeker's location as an object of the Location class.
     * @param dateOfBirth          Accepts the job seeker's date of birth as a Date object datatype.
     * @param keywords             Accepts the job seeker's descriptive keywords as an array list of strings.
     * @param expectedCompensation Accepts the job seeker's expected compensation as an integer.
     */
    public JobSeeker(String firstName, String lastName, String email, String password, Date dateCreated, String currentJobName, String currentJobLevel, String contactNumber, String resumeContent, Location location, Date dateOfBirth, ArrayList<String> keywords, int expectedCompensation)
    {
        super("Job Seeker", firstName, lastName, email, password, dateCreated);
        this.currentJobName = currentJobName;
        this.currentJobLevel = currentJobLevel;
        this.contactNumber = contactNumber;
        this.resumeContent = resumeContent;
        this.location = location;
        this.dateOfBirth = dateOfBirth;
        this.keywords = keywords;
        this.expectedCompensation = expectedCompensation;
    }

    /**
     * Display method to print the state of the object.
     */
    public void display()
    {
        super.display();
        System.out.println("currentJobName: ");
        System.out.println("currentJobLevel: ");
        System.out.println("contactNumber: ");
        System.out.println("resumeDir: ");
        System.out.println("location: ");
        System.out.println("dateOfBirth: ");
        System.out.println("keywords: ");
        System.out.println("expectedCompensation: ");
    }

    /**
     * Accessor method to get the job seeker's contact number.
     *
     * @return The job seeker's contact number as a string.
     */
    public String getContactNumber()
    {
        return contactNumber;
    }

    /**
     * Accessor method to get the job seeker's current job level.
     *
     * @return The job seeker's current job level as a string.
     */
    public String getCurrentJobLevel()
    {
        return currentJobLevel;
    }

    /**
     * Accessor method to get the job seeker's current job name.
     *
     * @return The job seeker's current job name as a string.
     */
    public String getCurrentJobName()
    {
        return currentJobName;
    }

    /**
     * Accessor method to get the job seeker's date of birth.
     *
     * @return The job seeker's date of birth as a Date object datatype.
     */
    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    /**
     * Accessor method to get the job seeker's expected compensation.
     *
     * @return The job seeker's expected compensation as an integer.
     */
    public int getExpectedCompensation()
    {
        return expectedCompensation;
    }

    /**
     * Accessor method to get the job seeker's descriptive keywords.
     *
     * @return The job seeker's descriptive keywords an array list of strings.
     */
    public ArrayList<String> getKeywords()
    {
        return keywords;
    }

    public String getKeywordsAsString(String separator)
    {
        StringBuilder sb = new StringBuilder();
        if (keywords == null)
        {
            System.out.println(getEmail());
        }
        keywords.forEach(keyword -> sb.append(keyword).append(separator));
        sb.delete(sb.length() - separator.length(), sb.length());
        return sb.toString();
    }

    /**
     * Accessor method to get the job seeker's location
     *
     * @return The job seeker's location as an object of the Location class.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Accessor method to get the job seeker's resume directory.
     *
     * @return The job seeker's resume directory as a string.
     */
    public String getResumeContent()
    {
        return resumeContent;
    }

    public LinkedHashMap<String, String> getUserDetailMap()
    {
        LinkedHashMap<String, String> jobDetails = new LinkedHashMap<>();
        jobDetails.put("Name", getFirstName() + getLastName());
        jobDetails.put("Number", contactNumber);
        jobDetails.put("Email", getEmail());
        jobDetails.put("Location", location.toString());
        jobDetails.put("Current Job", currentJobName);
        jobDetails.put("Keywords", getKeywordsAsString(", "));
        jobDetails.put("Resume Content", resumeContent);
        return jobDetails;
    }

    /**
     * Mutator method to set the job seeker's contact number.
     *
     * @param contactNumber The job seeker's contact number as a string.
     */
    public void setContactNumber(String contactNumber)
    {
        this.contactNumber = contactNumber;
    }

    /**
     * Mutator method to set the job seeker's current job level.
     *
     * @param currentJobLevel The job seeker's current job level as a string.
     */
    public void setCurrentJobLevel(String currentJobLevel)
    {
        this.currentJobLevel = currentJobLevel;
    }

    /**
     * Mutator method to set the job seeker's current job name.
     *
     * @param currentJobName The job seeker's current job name as a string.
     */
    public void setCurrentJobName(String currentJobName)
    {
        this.currentJobName = currentJobName;
    }

    /**
     * Mutator method to set the job seeker's date of birth.
     *
     * @param dateOfBirth The job seeker's date of birth as a Date object datatype.
     */
    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Mutator method to set the job seeker's expected compensation.
     *
     * @param expectedCompensation The job seeker's expected compensation as an integer.
     */
    public void setExpectedCompensation(int expectedCompensation)
    {
        this.expectedCompensation = expectedCompensation;
    }

    /**
     * Mutator method to set the job seeker's descriptive keywords.
     *
     * @param keywords The job seeker's descriptive keywords an array list of strings.
     */
    public void setKeywords(ArrayList<String> keywords)
    {
        this.keywords = keywords;
    }

    /**
     * Mutator method to set the job seeker's location.
     *
     * @param location The job seeker's location as an object of the Location class.
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }

    /**
     * Mutator method to set the job seeker's resume directory.
     *
     * @param resumeContent The job seeker's resume directory as a string.
     */
    public void setResumeContent(String resumeContent)
    {
        this.resumeContent = resumeContent;
    }

    public String stringForCosine()
    {
        StringBuilder sb = new StringBuilder();
        keywords.forEach(keyword -> sb.append(keyword).append(" "));
        sb.append(resumeContent);
        return sb.toString();
    }
}
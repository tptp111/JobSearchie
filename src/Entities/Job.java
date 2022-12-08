package Entities;

import Utilities.RelevanceScorer;
import Utilities.UserIO;

import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * An entity class which stores information for a job.
 *
 * @author Team R
 * @version 1.0
 */
public class Job
{
    private int id;
    private String jobTitle;
    private Recruiter author;
    private Date dateCreated;
    private Date dateListed;
    private Date dateDeListed;
    private String company;
    private ArrayList<String> categories;
    private Location location;
    private String workType;
    private String workingArrangement;
    private int compensation;
    private String jobLevel;
    private String description;
    private boolean isAdvertised;
    private ArrayList<String> keywords;

    /**
     * Default constructor which creates the object of the Job class.
     */
    public Job()
    {
        id = -1;
        jobTitle = "";
        author = null;
        dateCreated = null;
        dateListed = null;
        dateDeListed = null;
        company = "";
        categories = null;
        location = null;
        workType = "";
        workingArrangement = "";
        compensation = -1;
        jobLevel = "";
        description = "";
        isAdvertised = false;
        keywords = null;
    }

    /**
     * Non-default constructor which creates the object of the Job class.
     *
     * @param jobTitle           Accepts the job's title as a string.
     * @param author             Accepts the name of the job's author as a string.
     * @param dateCreated        Accepts the job's creation date as a Date object datatype.
     * @param dateListed         Accepts the job's listed date as a Date object datatype.
     * @param dateDeListed       Accepts the job's de-listing date as a Date object datatype.
     * @param company            Accepts the job's company name as a string.
     * @param categories         Accepts the job's categories as an array list of strings.
     * @param location           Accepts the job's location as an object of the Location class.
     * @param workType           Accepts the job's work type as a string.
     * @param workingArrangement Accepts the job's working arrangement as a string.
     * @param compensation       Accepts the job's compensation level as an integer.
     * @param jobLevel           Accepts the job's level as a string.
     * @param description        Accepts the job's description as a string.
     * @param isAdvertised       Accepts the job's advertised status as a boolean value.
     * @param keywords           Accepts the job's descriptive keywords as an array list of strings.
     */
    public Job(String jobTitle, Recruiter author, Date dateCreated, Date dateListed, Date dateDeListed, String company, ArrayList<String> categories, Location location, String workType, String workingArrangement, int compensation, String jobLevel, String description, boolean isAdvertised, ArrayList<String> keywords)
    {
        id = -1;
        this.jobTitle = jobTitle;
        this.author = author;
        this.dateCreated = dateCreated;
        this.dateListed = dateListed;
        this.dateDeListed = dateDeListed;
        this.company = company;
        this.categories = categories;
        this.location = location;
        this.workType = workType;
        this.workingArrangement = workingArrangement;
        this.compensation = compensation;
        this.jobLevel = jobLevel;
        this.description = description;
        this.isAdvertised = isAdvertised;
        this.keywords = keywords;
    }

    /**
     * Non-default constructor which creates the object of the Job class.
     *
     * @param id                 Accepts the job's identification number as an integer.
     * @param jobTitle           Accepts the job's title as a string.
     * @param author             Accepts the name of the job's author as a string.
     * @param dateCreated        Accepts the job's creation date as a Date object datatype.
     * @param dateListed         Accepts the job's listed date as a Date object datatype.
     * @param dateDeListed       Accepts the job's de-listing date as a Date object datatype.
     * @param company            Accepts the job's company name as a string.
     * @param categories         Accepts the job's categories as an array list of strings.
     * @param location           Accepts the job's location as an object of the Location class.
     * @param workType           Accepts the job's work type as a string.
     * @param workingArrangement Accepts the job's working arrangement as a string.
     * @param compensation       Accepts the job's compensation level as an integer.
     * @param jobLevel           Accepts the job's level as a string.
     * @param description        Accepts the job's description as a string.
     * @param isAdvertised       Accepts the job's advertised status as a boolean value.
     * @param keywords           Accepts the job's descriptive keywords as an array list of strings.
     */
    public Job(int id, String jobTitle, Recruiter author, Date dateCreated, Date dateListed, Date dateDeListed, String company, ArrayList<String> categories, Location location, String workType, String workingArrangement, int compensation, String jobLevel, String description, boolean isAdvertised, ArrayList<String> keywords)
    {
        this.id = id;
        this.jobTitle = jobTitle;
        this.author = author;
        this.dateCreated = dateCreated;
        this.dateListed = dateListed;
        this.dateDeListed = dateDeListed;
        this.company = company;
        this.categories = categories;
        this.location = location;
        this.workType = workType;
        this.workingArrangement = workingArrangement;
        this.compensation = compensation;
        this.jobLevel = jobLevel;
        this.description = description;
        this.isAdvertised = isAdvertised;
        this.keywords = keywords;
    }

    /**
     * Display method to print the state of the object.
     */
    public void display()
    {
        UserIO.displayBody("Job title: " + jobTitle);
        UserIO.displayBody("Date created: " + dateCreated);
        UserIO.displayBody("Date listed: " + dateListed);
        UserIO.displayBody("Date de-listed: " + dateDeListed);
        UserIO.displayBody("Company: " + company);
        UserIO.displayBody("Categories: ");
        UserIO.displayArrayList(categories);
        UserIO.displayBody("Location: " + location.getCountry() + " " + location.getState() + " " + location.getCity() + " " + location.getPostcode());
        UserIO.displayBody("Work type: " + workType);
        UserIO.displayBody("Working arrangement: " + workingArrangement);
        UserIO.displayBody("Compensation: " + compensation);
        UserIO.displayBody("Job level: " + jobLevel);
        UserIO.displayBody("Description: " + description);
        UserIO.displayBody("Currently advertised: " + isAdvertised);
        UserIO.displayBody("Keywords: ");
        UserIO.displayArrayList(keywords);
    }

    /**
     * Accessor method to get the job description's author.
     *
     * @return The job description's author as a string.
     */
    public Recruiter getAuthor()
    {
        return author;
    }

    /**
     * Accessor method to get the job's categories.
     *
     * @return The job's descriptive categories as an array list of strings.
     */
    public ArrayList<String> getCategories()
    {
        return categories;
    }

    /**
     * Accessor method to get the job's company.
     *
     * @return The job description's company name as a string.
     */
    public String getCompany()
    {
        return company;
    }

    /**
     * Accessor method to get the job's compensation level.
     *
     * @return The job's compensation level as an integer.
     */
    public int getCompensation()
    {
        return compensation;
    }

    public int getCosine(String matchingTerm)
    {
        HashMap<String, Integer> matches = new HashMap<>();
        matches.put(jobTitle, 50);
        matches.put(getStringKeywords(" "), 15);
        matches.put(getStringCategories(" "), 15);
        matches.put(description, 20);
        if (RelevanceScorer.getCosine(matches, matchingTerm) == -1)
        {
            System.out.println("Job failed: id = " + id);
        }
        return RelevanceScorer.getCosine(matches, matchingTerm);
    }

    /**
     * Accessor method to get the job's description creation date.
     *
     * @return The job description's creation date as a Date object datatype.
     */
    public Date getDateCreated()
    {
        return dateCreated;
    }

    /**
     * Accessor method to get the job's deListing date.
     *
     * @return The job description's de-listing date as a Date object datatype.
     */
    public Date getDateDeListed()
    {
        return dateDeListed;
    }

    /**
     * Accessor method to get the job's listing date.
     *
     * @return The job description's listing date as a Date object datatype.
     */
    public Date getDateListed()
    {
        return dateListed;
    }

    /**
     * Accessor method to get the job's description.
     *
     * @return The job's description as a string.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Accessor method to get the job's identification number.
     *
     * @return The job's identification number as an integer.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Accessor method to get the job's advertisement status.
     *
     * @return The job's advertisement status as a boolean value.
     */
    public boolean getIsAdvertised()
    {
        return isAdvertised;
    }

    public LinkedHashMap<String, String> getJobDetailMap(JobSeeker jobSeeker)
    {
        LinkedHashMap<String, String> jobDetails = new LinkedHashMap<>();

        jobDetails.put("Title", jobTitle);
        jobDetails.put("Personal Relevancy", String.valueOf(getPersonalRelevancy(jobSeeker)));
        jobDetails.put("Date Posted", dateListed.toString());
        jobDetails.put("Location", location.toString());
        jobDetails.put("Company", company);
        jobDetails.put("Compensation", UserIO.formatCompensation(compensation));
        jobDetails.put("Job Level", jobLevel);
        jobDetails.put("Working Type", workType);
        jobDetails.put("Working Arrangement", workingArrangement);
        jobDetails.put("Description", description);
        jobDetails.put("Categories", getStringCategories(", "));
        jobDetails.put("Keywords", getStringKeywords(", "));
        return jobDetails;
    }

    public LinkedHashMap<String, String> getJobDetailMap()
    {
        LinkedHashMap<String, String> jobDetails = new LinkedHashMap<>();
        jobDetails.put("Title", jobTitle);
        jobDetails.put("Date Posted", dateListed.toString());
        jobDetails.put("Location", location.toString());
        jobDetails.put("Company", company);
        jobDetails.put("Compensation", UserIO.formatCompensation(compensation));
        jobDetails.put("Job Level", jobLevel);
        jobDetails.put("Working Type", workType);
        jobDetails.put("Working Arrangement", workingArrangement);
        jobDetails.put("Description", description);
        jobDetails.put("Categories", getStringCategories(", "));
        jobDetails.put("Keywords", getStringKeywords(", "));
        return jobDetails;
    }

    /**
     * Accessor method to get the job's level.
     *
     * @return The job's level as a string.
     */
    public String getJobLevel()
    {
        return jobLevel;
    }

    public String getJobString()
    {
        return jobTitle + getStringKeywords(" ") + getStringCategories(" ") + description + jobLevel;
    }

    /**
     * Accessor method to get the job's title.
     *
     * @return The job's title as a string.
     */
    public String getJobTitle()
    {
        return jobTitle;
    }

    /**
     * Accessor method to get the job's descriptive keywords.
     *
     * @return The job's descriptive keywords as an array list of strings.
     */
    public ArrayList<String> getKeywords()
    {
        return keywords;
    }

    /**
     * Accessor method to get the job's location.
     *
     * @return The job's location as an object of the Location class.
     */
    public Location getLocation()
    {
        return location;
    }

    public int getPersonalRelevancy(JobSeeker jobSeeker)
    {
        int resumeScore = RelevanceScorer.getCosineScore(jobSeeker.getResumeContent(), getJobString());
        int keywordScore = RelevanceScorer.getCosineScore(jobSeeker.getKeywordsAsString(" "), getJobString());
        int jobNameScore = RelevanceScorer.getCosineScore(jobSeeker.getCurrentJobName(), getJobString());
        int jobLevelScore = RelevanceScorer.getCosineScore(jobSeeker.getCurrentJobLevel(), getJobString());
        int expectedComp = jobSeeker.getExpectedCompensation();
        int compensationScore = (expectedComp > 5000 && expectedComp <= 1000000) ? (Math.abs(expectedComp / compensation - 1) + 1) : 100;

        double resumeWeight = 0.2;
        double keywordWeight = 0.3;
        double jobNameWeight = 0.05;
        double jobLevelWeight = 0.05;
        double compensationWeight = 0.4;

        return (int) ((resumeScore * resumeWeight) + (keywordScore * keywordWeight) + (jobNameScore * jobNameWeight) + (jobLevelScore * jobLevelWeight) + (compensationScore * compensationWeight));
    }

    private String getStringCategories(String separator)
    {
        StringBuilder sb = new StringBuilder();
        categories.forEach(category -> sb.append(category).append(separator));
        sb.delete(sb.length() - separator.length(), sb.length());
        return sb.toString();
    }

    private String getStringKeywords(String separator)
    {
        StringBuilder sb = new StringBuilder();
        keywords.forEach(keyword -> sb.append(keyword).append(separator));
        sb.delete(sb.length() - separator.length(), sb.length());
        return sb.toString();
    }

    /**
     * Accessor method to get the job's work type.
     *
     * @return The job's work type as a string.
     */
    public String getWorkType()
    {
        return workType;
    }

    /**
     * Accessor method to get the job's working arrangement.
     *
     * @return The job's working arrangement as a string.
     */
    public String getWorkingArrangement()
    {
        return workingArrangement;
    }

    /**
     * Mutator method to set the job description's author.
     *
     * @param author The job description's author as a string.
     */
    public void setAuthor(Recruiter author)
    {
        this.author = author;
    }

    /**
     * Mutator method to set the job's descriptive categories.
     *
     * @param categories The job's descriptive categories as an array list of strings.
     */
    public void setCategories(ArrayList<String> categories)
    {
        this.categories = categories;
    }

    /**
     * Mutator method to set the job's company.
     *
     * @param company The job's company name as a string.
     */
    public void setCompany(String company)
    {
        this.company = company;
    }

    /**
     * Mutator method to set the job's proposed compensation level.
     *
     * @param compensation The job's proposed compensation level as an integer.
     */
    public void setCompensation(int compensation)
    {
        this.compensation = compensation;
    }

    /**
     * Mutator method to set the job description's creation date.
     *
     * @param dateCreated The job description's creation date as a Date object datatype.
     */
    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    /**
     * Mutator method to set the job's de-listing date.
     *
     * @param dateDeListed The job description's de-listing date as a Date object datatype.
     */
    public void setDateDeListed(Date dateDeListed)
    {
        this.dateDeListed = dateDeListed;
    }

    /**
     * Mutator method to set the job's listing date.
     *
     * @param dateListed The job description's listing date as a Date object datatype.
     */
    public void setDateListed(Date dateListed)
    {
        this.dateListed = dateListed;
    }

    /**
     * Mutator method to set the job's description.
     *
     * @param description The job's description as a string.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Mutator method to set the job's identification number.
     *
     * @param id The job's identification number as an integer.
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Mutator method to set the job's advertisement status.
     *
     * @param isAdvertised The job's advertisement status as a boolean value.
     */
    public void setIsAdvertised(boolean isAdvertised)
    {
        this.isAdvertised = isAdvertised;
    }

    /**
     * Mutator method to set the job's level.
     *
     * @param jobLevel The job's level as a string.
     */
    public void setJobLevel(String jobLevel)
    {
        this.jobLevel = jobLevel;
    }

    /**
     * Mutator method to set the job's title.
     *
     * @param jobTitle The job's title as a string.
     */
    public void setJobTitle(String jobTitle)
    {
        this.jobTitle = jobTitle;
    }

    /**
     * Mutator method to set the job's descriptive keywords.
     *
     * @param keywords The job's descriptive keywords as an array list of strings.
     */
    public void setKeywords(ArrayList<String> keywords)
    {
        this.keywords = keywords;
    }

    /**
     * Mutator method to set the job's location.
     *
     * @param location The job's location as an object of the Location class.
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }

    /**
     * Mutator method to set the job's work type.
     *
     * @param workType The job's work type as a string.
     */
    public void setWorkType(String workType)
    {
        this.workType = workType;
    }

    /**
     * Mutator method to set the job's working arrangement.
     *
     * @param workingArrangement The job's working arrangement as a string.
     */
    public void setWorkingArrangement(String workingArrangement)
    {
        this.workingArrangement = workingArrangement;
    }

}

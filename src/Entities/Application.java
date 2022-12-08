package Entities;

import java.sql.Date;
import java.util.LinkedHashMap;

/**
 * An entity class which stores information for an application.
 *
 * @author Team R
 * @version 1.0
 */
public class Application
{
    private int id;
    private JobSeeker jobSeeker;
    private Job job;
    private String coverLetterDir;
    private String resumeDir;
    private String status;
    private Date applicationDate;

    /**
     * Default constructor which creates the object of the Application class.
     */
    public Application()
    {
        id = -1;
        jobSeeker = null;
        job = null;
        coverLetterDir = "";
        resumeDir = "";
        status = "";
        applicationDate = null;
    }

    /**
     * Non-default constructor which creates the object of the Application class.
     *
     * @param jobSeeker       Accepts the job seeker details as an object of the JobSeeker class.
     * @param job             Accepts the job details as an object of the Job class.
     * @param coverLetterDir  Accepts a cover letter directory for the application as a string.
     * @param resumeDir       Accepts a resume directory for the application as a string.
     * @param status          Accepts the status of the application as a string.
     * @param applicationDate Accepts the application date as a Date object datatype.
     */
    public Application(JobSeeker jobSeeker, Job job, String coverLetterDir, String resumeDir, String status, Date applicationDate)
    {
        id = -1;
        this.jobSeeker = jobSeeker;
        this.job = job;
        this.coverLetterDir = coverLetterDir;
        this.resumeDir = resumeDir;
        this.status = status;
        this.applicationDate = applicationDate;
    }

    /**
     * Non-default constructor which creates the object of the Application class.
     *
     * @param id              Accepts the application identification number as an integer.
     * @param jobSeeker       Accepts the job seeker details as an object of the JobSeeker class.
     * @param job             Accepts the job details as an object of the Job class.
     * @param coverLetterDir  Accepts the cover letter directory for the application as a string.
     * @param resumeDir       Accepts the resume directory for the application as a string.
     * @param status          Accepts the status of the application as a string.
     * @param applicationDate Accepts the application date as a Date object datatype.
     */
    public Application(int id, JobSeeker jobSeeker, Job job, String coverLetterDir, String resumeDir, String status, Date applicationDate)
    {
        this.id = -1;
        this.jobSeeker = jobSeeker;
        this.job = job;
        this.coverLetterDir = coverLetterDir;
        this.resumeDir = resumeDir;
        this.status = status;
        this.applicationDate = applicationDate;
    }

    /**
     * Display method to print the state of the object.
     */
    public void display()
    {
        System.out.println("id: " + id);
        System.out.println("coverLetterDir: " + coverLetterDir);
        System.out.println("resumeDir: " + resumeDir);
        System.out.println("status: " + status);
        System.out.println("applicationDate: " + applicationDate);
        System.out.println("JOB SEEKER--");
        jobSeeker.display();
        System.out.println("JOB--");
        job.display();
    }

    /**
     * Accessor method to get the application's application date.
     *
     * @return The application date as a Date object datatype.
     */
    public Date getApplicationDate()
    {
        return applicationDate;
    }

    public LinkedHashMap<String, String> getApplicationDetailMap()
    {
        LinkedHashMap<String, String> applicationDetails = new LinkedHashMap<>();
        applicationDetails.put("Application Date", applicationDate.toString());
        applicationDetails.put("Status", status);
        applicationDetails.putAll(jobSeeker.getUserDetailMap());
        return applicationDetails;
    }

    /**
     * Accessor method to get the application's cover letter directory.
     *
     * @return The cover letter directory for the application as a string.
     */
    public String getCoverLetterDir()
    {
        return coverLetterDir;
    }

    /**
     * Accessor method to get the application's identification number.
     *
     * @return The application identification number as an integer.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Accessor method to get the application's job details.
     *
     * @return The job details as an object of the Job class.
     */
    public Job getJob()
    {
        return job;
    }

    /**
     * Accessor method to get the application's job seeker details.
     *
     * @return The job seeker details as an object of the JobSeeker class.
     */
    public JobSeeker getJobSeeker()
    {
        return jobSeeker;
    }

    /**
     * Accessor method to get the application's resume directory.
     *
     * @return The resume directory for the application as a string.
     */
    public String getResumeDir()
    {
        return resumeDir;
    }

    /**
     * Accessor method to get the application's status.
     *
     * @return The status of the application as a string.
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * Mutator method to set the application's application date.
     *
     * @param applicationDate The application date as a Date object datatype.
     */
    public void setApplicationDate(Date applicationDate)
    {
        this.applicationDate = applicationDate;
    }

    /**
     * Mutator method to set the application's cover letter directory.
     *
     * @param coverLetterDir The cover letter directory for the application as a string.
     */
    public void setCoverLetterDir(String coverLetterDir)
    {
        this.coverLetterDir = coverLetterDir;
    }

    /**
     * Mutator method to set the application's identification number.
     *
     * @param id the application identification number as an integer.
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Mutator method to set the application's job details.
     *
     * @param job The job details as an object of the Job class.
     */
    public void setJob(Job job)
    {
        this.job = job;
    }

    /**
     * Mutator method to set the application's job seeker details.
     *
     * @param jobSeeker The job seeker details as an object of the JobSeeker class.
     */
    public void setJobSeeker(JobSeeker jobSeeker)
    {
        this.jobSeeker = jobSeeker;
    }

    /**
     * Mutator method to set the application's resume directory.
     *
     * @param resumeDir The resume directory for the application as a string.
     */
    public void setResumeDir(String resumeDir)
    {
        this.resumeDir = resumeDir;
    }

    /**
     * Mutator method to set the application's status.
     *
     * @param status The status of the application as a string.
     */
    public void setStatus(String status)
    {
        this.status = status;
    }
}

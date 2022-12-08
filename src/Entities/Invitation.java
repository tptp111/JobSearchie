package Entities;

import Utilities.UserIO;

import java.sql.Date;
import java.util.LinkedHashMap;

/**
 * This class contains the details of an interview invitation within the Job Searchie system.
 *
 * @author Team R
 * @version 1.0
 */
public class Invitation
{
    private int id;
    private JobSeeker jobSeeker;
    private Recruiter recruiter;
    private Job job;
    private Date dateSent;
    private Date dateOfInterview;
    private Location locationOfInterview;
    private String attachedMessage;
    private String typeOfInterview;
    private boolean accepted;

    /**
     * Default constructor which creates the object of the Loction class.
     */
    public Invitation()
    {
        id = -1;
        jobSeeker = null;
        recruiter = null;
        job = null;
        dateSent = null;
        dateOfInterview = null;
        locationOfInterview = null;
        attachedMessage = null;
        typeOfInterview = null;
        accepted = false;
    }

    /**
     * Non-default constructor which creates the object of the Invitation class.
     *
     * @param jobSeeker           Accepts the job seeker details as an object of the JobSeeker class.
     * @param recruiter           Accepts the recruiter details as an object of the Recruiter class.
     * @param job                 Accepts the job details as an object of the Job class.
     * @param dateSent            Accepts the date the invitation was sent a Date object datatype.
     * @param dateOfInterview     Accepts the date of the interview as a Date object datatype.
     * @param locationOfInterview Accepts the interview's location as an object of the Location class.
     * @param attachedMessage     Accepts the message attached to the invitation as a string.
     * @param typeOfInterview     Accepts the type of interview of the invitation as a string.
     * @param accepted            Accepts the acceptance status of the invitation as a boolean value.
     */
    public Invitation(JobSeeker jobSeeker, Recruiter recruiter, Job job, Date dateSent, Date dateOfInterview, Location locationOfInterview, String attachedMessage, String typeOfInterview, boolean accepted)
    {
        id = -1;
        this.jobSeeker = jobSeeker;
        this.recruiter = recruiter;
        this.job = job;
        this.dateSent = dateSent;
        this.dateOfInterview = dateOfInterview;
        this.locationOfInterview = locationOfInterview;
        this.attachedMessage = attachedMessage;
        this.typeOfInterview = typeOfInterview;
        this.accepted = accepted;
    }

    /**
     * Non-default constructor which creates the object of the Invitation class.
     *
     * @param id                  Accepts the invitation's identification number as an integer.
     * @param jobSeeker           Accepts the job seeker details as an object of the JobSeeker class.
     * @param recruiter           Accepts the recruiter details as an object of the Recruiter class.
     * @param job                 Accepts the job details as an object of the Job class.
     * @param dateSent            Accepts the date the invitation was sent a Date object datatype.
     * @param dateOfInterview     Accepts the date of the interview as a Date object datatype.
     * @param locationOfInterview Accepts the interview's location as an object of the Location class.
     * @param attachedMessage     Accepts the message attached to the invitation as a string.
     * @param typeOfInterview     Accepts the type of interview of the invitation as a string.
     * @param accepted            Accepts the acceptance status of the invitation as a boolean value.
     */
    public Invitation(int id, JobSeeker jobSeeker, Recruiter recruiter, Job job, Date dateSent, Date dateOfInterview, Location locationOfInterview, String attachedMessage, String typeOfInterview, boolean accepted)
    {
        this.id = id;
        this.jobSeeker = jobSeeker;
        this.recruiter = recruiter;
        this.job = job;
        this.dateSent = dateSent;
        this.dateOfInterview = dateOfInterview;
        this.locationOfInterview = locationOfInterview;
        this.attachedMessage = attachedMessage;
        this.typeOfInterview = typeOfInterview;
        this.accepted = accepted;
    }

    /**
     * Accessor method to get the invitation's attached message.
     *
     * @return The invitation's attached message as a string.
     */
    public String getAttachedMessage()
    {
        return attachedMessage;
    }

    /**
     * Accessor method to get the invitation's interview date.
     *
     * @return The invitation's interview date as a Date object datatype.
     */
    public Date getDateOfInterview()
    {
        return dateOfInterview;
    }

    /**
     * Accessor method to get the set date of the invitation.
     *
     * @return The invitation's sent date as a Date object datatype.
     */
    public Date getDateSent()
    {
        return dateSent;
    }

    /**
     * Accessor method to get the invitation's identification number.
     *
     * @return The invitation's identification number as an integer.
     */
    public int getId()
    {
        return id;
    }

    public LinkedHashMap<String, String> getInvitationDetailMap()
    {
        LinkedHashMap<String, String> invitationDetails = new LinkedHashMap<>();
        invitationDetails.put("Job Title", job.getJobTitle());
        invitationDetails.put("Recruiter Name", recruiter.getFirstName() + " " + recruiter.getLastName());
        invitationDetails.put("Location", locationOfInterview.toString());
        invitationDetails.put("Date of Interview", dateOfInterview.toString());
        invitationDetails.put("Date Received", dateSent.toString());
        invitationDetails.put("Message from Recruiter", attachedMessage);
        invitationDetails.put("Accepted", String.valueOf(accepted));
        return invitationDetails;
    }

    /**
     * Accessor method to get the invitation's job details.
     *
     * @return The invitation's job details as an object of the Job class.
     */
    public Job getJob()
    {
        return job;
    }

    /**
     * Accessor method to get the invitation's job seeker details.
     *
     * @return The invitation's job seeker details as an object of the JobSeeker class.
     */
    public JobSeeker getJobSeeker()
    {
        return jobSeeker;
    }

    /**
     * Accessor method to get the invitation's interview location.
     *
     * @return The invitation's interview location details as an object of the Location class.
     */
    public Location getLocationOfInterview()
    {
        return locationOfInterview;
    }

    /**
     * Accessor method to get the invitation's recruiter details.
     *
     * @return The invitation's recruiter details as an object of the Recruiter class.
     */
    public Recruiter getRecruiter()
    {
        return recruiter;
    }

    /**
     * Accessor method to get the invitation's interview type.
     *
     * @return The invitation's interview type as a string.
     */
    public String getTypeOfInterview()
    {
        return typeOfInterview;
    }

    /**
     * Accessor method to get the invitation's acceptance status.
     *
     * @return The invitation's acceptance status as a boolean value.
     */
    public boolean isAccepted()
    {
        return accepted;
    }

    /**
     * Mutator method to set the invitation's acceptance status.
     *
     * @param accepted The invitation's acceptance status as a boolean value.
     */
    public void setAccepted(boolean accepted)
    {
        this.accepted = accepted;
    }

    /**
     * Mutator method to set the invitation's attached message.
     *
     * @param attachedMessage The invitation's attached message as a string.
     */
    public void setAttachedMessage(String attachedMessage)
    {
        this.attachedMessage = attachedMessage;
    }

    /**
     * Mutator method to set the invitation's interview date.
     *
     * @param dateOfInterview The invitation's interview date as a Date object datatype.
     */
    public void setDateOfInterview(Date dateOfInterview)
    {
        this.dateOfInterview = dateOfInterview;
    }

    /**
     * Mutator method to set the set date of the invitation.
     *
     * @param dateSent The invitation's sent date as a Date object datatype.
     */
    public void setDateSent(Date dateSent)
    {
        this.dateSent = dateSent;
    }

    /**
     * Mutator method to set the invitation's identification number.
     *
     * @param id The invitation's identification number as an integer.
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Mutator method to set the invitation's job details.
     *
     * @param job The invitation's job details as an object of the Job class.
     */
    public void setJob(Job job)
    {
        this.job = job;
    }

    /**
     * Mutator method to set the invitation's job seeker details.
     *
     * @param jobSeeker The invitation's job seeker details as an object of the JobSeeker class.
     */
    public void setJobSeeker(JobSeeker jobSeeker)
    {
        this.jobSeeker = jobSeeker;
    }

    /**
     * Mutator method to set the invitation's interview location.
     *
     * @param locationOfInterview The invitation's interview location details as an object of the Location class.
     */
    public void setLocationOfInterview(Location locationOfInterview)
    {
        this.locationOfInterview = locationOfInterview;
    }

    /**
     * Mutator method to set the invitation's recruiter details.
     *
     * @param recruiter The invitation's recruiter details as an object of the Recruiter class.
     */
    public void setRecruiter(Recruiter recruiter)
    {
        this.recruiter = recruiter;
    }

    /**
     * Mutator method to set the invitation's interview type.
     *
     * @param typeOfInterview The invitation's interview type as a string.
     */
    public void setTypeOfInterview(String typeOfInterview)
    {
        this.typeOfInterview = typeOfInterview;
    }
}

package Entities;

/**
 * An entity class which stores information for the user.
 *
 * @author Team R
 * @version 1.0
 */
public class Complaint
{
    private String complaintID;

    /**
     * Default constructor which creates the object of the Complaint class.
     */
    public Complaint()
    {
        complaintID = "00000";
    }

    /**
     * Non-default constructor which creates the object of the User class.
     *
     * @param complaintID Accepts the identification number of the complaint as an integer.
     */
    public Complaint(String complaintID)
    {
        this.complaintID = complaintID;
    }

    /**
     * Accessor method to get the complaint's identification number.
     *
     * @return The identification number of the complaint as an integer.
     */
    public String getComplaintID()
    {
        return complaintID;
    }

    /**
     * Mutator  method to set the complaint's identification number.
     *
     * @param complaintID The identification number of the complaint as an integer.
     */
    public void setComplaintID(String complaintID)
    {
        this.complaintID = complaintID;
    }
}

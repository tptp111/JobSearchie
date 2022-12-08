package Entities;

/**
 * An entity class which stores information for the user's location.
 *
 * @author Team R
 * @version 1.0
 */
public class Location
{
    private int id;
    private String country;
    private String state;
    private String city;
    private String postcode;

    /**
     * Default constructor which creates the object of the Location class.
     */
    public Location()
    {
        id = -1;
        country = "";
        state = "";
        city = "";
        postcode = "";
    }

    /**
     * Non-default constructor which creates the object of the Location class.
     *
     * @param id Accepts the location's identification number as an integer.
     */
    public Location(int id)
    {
        this.id = id;
        country = "";
        state = "";
        city = "";
        postcode = "";
    }

    /**
     * Non-default constructor which creates the object of the Location class.
     *
     * @param country  Accepts the location's country as a String.
     * @param state    Accepts the location's state as a String.
     * @param city     Accepts the location's city as a String.
     * @param postcode Accepts the location's postcode as a String.
     */
    public Location(String country, String state, String city, String postcode)
    {
        this.id = -1;
        this.country = country;
        this.state = state;
        this.city = city;
        this.postcode = postcode;
    }

    /**
     * Non-default constructor which creates the object of the Location class.
     *
     * @param id       Accepts the location's identification number as an integer.
     * @param country  Accepts the location's country as a String.
     * @param state    Accepts the location's state as a String.
     * @param city     Accepts the location's city as a String.
     * @param postcode Accepts the location's postcode as a String.
     */
    public Location(int id, String country, String state, String city, String postcode)
    {
        this.id = id;
        this.country = country;
        this.state = state;
        this.city = city;
        this.postcode = postcode;
    }

    /**
     * Accessor method to get the location's city.
     *
     * @return The location's city as a string.
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Accessor method to get the location's country.
     *
     * @return The location's country as a string.
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * Accessor method to get the location's identification number.
     *
     * @return The location's identification number as an integer.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Accessor method to get the location's postcode.
     *
     * @return The location's postcode as a string.
     */
    public String getPostcode()
    {
        return postcode;
    }

    /**
     * Accessor method to get the location's state.
     *
     * @return The location's state as a string.
     */
    public String getState()
    {
        return state;
    }

    /**
     * Mutator method to set the location's city.
     *
     * @param city The location's city as a string.
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Mutator method to set the location's country.
     *
     * @param country The location's country as a string.
     */
    public void setCountry(String country)
    {
        this.country = country;
    }

    /**
     * Mutator method to set the location's identification number.
     *
     * @param id The location's identification number as an integer.
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Mutator method to set the location's postcode.
     *
     * @param postcode The location's postcode as a string.
     */
    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }

    /**
     * Mutator method to set the location's state.
     *
     * @param state The location's state as a string.
     */
    public void setState(String state)
    {
        this.state = state;
    }

    public String toString()
    {
        return country + ", " + state + ", " + city + ", " + postcode;
    }
}

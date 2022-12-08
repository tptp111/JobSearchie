package Controllers;

import Entities.Job;
import Entities.Location;
import Entities.Recruiter;
import Utilities.UserIO;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Controller class for Job entity.
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version ver1.0.0
 */
public class JobHandler
{

    /**
     * Default constructor class.
     */
    public JobHandler()
    {
    }

    /**
     * Allows the recruiter class to create a job.
     *
     * @param recruiter The Recruiter object who creates the job.
     * @return job as a Job class.
     */
    public Job createJob(Recruiter recruiter)
    {
        UserIO.displayHeading("Create a New Job");
        UserIO.displayBody("You are now required to enter the following requested information in order to create a job. At any time you wish to go back to the previous screen, please type ‘back’");
        String jobTitle = enterJobTitle();
        String company = enterCompany();
        ArrayList<String> categories = enterCategories();
        Location location = enterLocation();
        String workType = enterWorkType();
        String workingArrangement = enterWorkingArrangement();
        int compensation = enterCompensation();
        String jobLevel = enterJobLevel();
        String description = enterDescription();
        ArrayList<String> keywords = enterKeywords();
        boolean isAdvertised = enterIsAdvertised();
        Date advertiseDate = null;
        if (isAdvertised)
        {
            advertiseDate = new Date(System.currentTimeMillis());
        }
        UserIO.displayBody("You have now finished providing the required information.");

        return new Job(jobTitle, recruiter, new Date(System.currentTimeMillis()), advertiseDate, null, company, categories, location, workType, workingArrangement, compensation, jobLevel, description, isAdvertised, keywords);
    }

    /**
     * Enter method to ask the user to select categories.
     *
     * @return categories as an ArrayList<String>.
     */
    public ArrayList<String> enterCategories()
    {
        ArrayList<String> categories = new ArrayList<>();
        String[] options = {
                "Agricultural Forestry and Fishing",
                "Mining",
                "Manufacturing",
                "Electricity Gas Water and Waste Services",
                "Construction",
                "Wholesale Trade",
                "Retail Trade",
                "Accommodation and Food Services",
                "Transport Postal and Warehousing",
                "Information Media and Telecommunications",
                "Financial and Insurance Services",
                "Rental Hiring and Real Estate Services",
                "Professional Scientific and Technical Services",
                "Administrative and Support Services",
                "Public Administration and Safety",
                "Education and Training",
                "Health Care and Social Assistance",
                "Arts and Recreation Services",
                "Other Services"
        };
        categories.add(UserIO.menuSelectorValue("Please enter the category of the job:", options));

        String[] addAnother = {"Yes", "no"};
        String inputAdd = UserIO.menuSelectorKey("Would you like to add another category", addAnother);
        while (inputAdd.equals("0"))
        {
            categories.add(UserIO.menuSelectorValue("Please enter the category of the job:", options));
            inputAdd = UserIO.menuSelectorKey("Would you like to add another category", addAnother);
        }

        return categories;
    }

    /**
     * Enter method to ask the user to input company name.
     *
     * @return company name as a String.
     */
    public String enterCompany()
    {

        return UserIO.enterAttribute("Company name", 4, 30);
    }

    /**
     * Enter method to ask the user to enter compensation.
     *
     * @return compensation as an int.
     */
    public int enterCompensation()
    {

        UserIO.displayBody("Please enter the compensation level of this job or enter 0 if you don’t wish to include this information:");
        System.out.print("$");
        boolean flag = false;
        int input = -1;
        do
        {
            try
            {
                input = Integer.parseInt(UserIO.getInput().trim());
                if (input >= 0)
                {
                    flag = true;
                } else
                {
                    UserIO.displayBody("Compensation cannot be negative please re-enter");
                }
            } catch (Exception e)
            {
                UserIO.displayBody("Please enter an integer:");
            }
        }
        while (!flag);

        return input;
    }

    /**
     * Enter method to ask the user to enter the job description.
     *
     * @return job description as a String.
     */
    public String enterDescription()
    {

        return UserIO.enterAttribute("job description", 4, 2000);
    }

    /**
     * Enter method to ask the user to select if the user would like to advertise the job.
     *
     * @return advertise status as a boolean.
     */
    public boolean enterIsAdvertised()
    {

        String[] options = {
                "Yes",
                "No"
        };
        String userInput = UserIO.menuSelectorKey("Would you like to advertise this job?:", options);

        boolean isAdvertised;

        switch (userInput)
        {
            case ("0") -> isAdvertised = true;
            case ("1") -> isAdvertised = false;
            default -> isAdvertised = false;
        }
        return isAdvertised;
    }

    /**
     * Enter method to ask the user to select the job level.
     *
     * @return job level as a String.
     */
    public String enterJobLevel()
    {

        String[] options = {
                "Entry level",
                "Mid level",
                "Senior level"
        };

        return UserIO.menuSelectorValue("Please enter the level of this job:", options);
    }

    /**
     * Enter method to ask the user to input job title.
     *
     * @return job title as a String.
     */
    public String enterJobTitle()
    {

        return UserIO.enterAttribute("Job title", 4, 30);
    }

    /**
     * Enter method to ask the user to enter the keywords.
     *
     * @return keywords as an ArrayList<String>.
     */
    public ArrayList<String> enterKeywords()
    {

        ArrayList<String> keywords = new ArrayList<>();
        keywords.add(UserIO.enterAttribute("Keyword", 4, 30));
        String[] addAnother = {"Yes", "no"};
        String inputAdd = UserIO.menuSelectorKey("Would you like to add another keyword", addAnother);
        while (inputAdd.equals("0"))
        {
            keywords.add(UserIO.enterAttribute("Keyword", 4, 30));
            inputAdd = UserIO.menuSelectorKey("Would you like to add another keyword", addAnother);
        }

        return keywords;
    }

    /**
     * Enter method to ask the user to enter location.
     *
     * @return location as a Location object.
     */
    public Location enterLocation()
    {

        String[] countries = {
                "Australia"
        };
        String country = UserIO.menuSelectorValue("Please select the country", countries);
        String[] states = {
                "ACT",
                "NSW",
                "NT",
                "QLD",
                "SA",
                "TAS",
                "VIC",
                "WA"
        };
        String state = UserIO.menuSelectorValue("Please select the state", states);
        String suburb = UserIO.enterAttribute("Suburb", 1, 30);
        String postcode = UserIO.enterAttribute("Postcode", 4, 4);

        return new Location(country, state, suburb, postcode);
    }

    /**
     * Enters method to ask the user to select the work type.
     *
     * @return work type as a String.
     */
    public String enterWorkType()
    {

        String[] options = {
                "Full time",
                "Part time",
                "Contract/ Temporary",
                "Casual/ Vacation"
        };

        return UserIO.menuSelectorValue("Please enter the type of work:", options);
    }

    /**
     * Enter method to ask the user to select the working arrangement.
     *
     * @return working arrangement as a String.
     */
    public String enterWorkingArrangement()
    {

        String[] options = {
                "Office",
                "Remote",
                "Hybrid"
        };

        return UserIO.menuSelectorValue("Please enter the working arrangement:", options);
    }

    /**
     * Gets the key information from a Job as a String.
     *
     * @param job the Job to transform into a String.
     * @return job as a String.
     */
    public String getJobAsString(Job job)
    {
        return job.getJobTitle() + " " + job.getCompany() + " " + String.join(" ", job.getKeywords() + " " + job.getDescription());
    }
}

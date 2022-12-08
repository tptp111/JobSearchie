package Controllers;

import Database.DatabaseManager;
import Entities.JobSeeker;
import Entities.Location;
import Entities.Recruiter;
import Entities.User;
import Utilities.UserIO;
import Utilities.Validate;

import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller class to allow the user to loginHandler into JobSearchie.
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version ver1.0.0
 */
public class LoginHandler
{

    public LoginHandler()
    {
    }

    /**
     * Creates the account set up screen for JobSeeker and Recruiter.
     *
     * @param emailAddress the email address of the user as a String
     * @param db           The database manager object being used.
     * @return a User object
     */
    private User createUser(String emailAddress, DatabaseManager db)
    {
        String sectionTitle = "Registration";
        UserIO.clearScreenAndAddTitle(sectionTitle);
        String password = enterPassword();
        UserIO.clearScreenAndAddTitle(sectionTitle);
        String firstName = UserIO.enterAttribute("your first name", 1, 30);
        UserIO.clearScreenAndAddTitle(sectionTitle);
        String lastName = UserIO.enterAttribute("your last name", 1, 30);
        UserIO.clearScreenAndAddTitle(sectionTitle);
        String accTypeSelection = UserIO.menuSelectorKey("Please select the type of account you'd like to create.", new String[]{"Job Seeker", "Recruiter"});
        switch (accTypeSelection)
        {
            case "0" -> {
                return registerJobSeeker(firstName, lastName, emailAddress, password, db);
            }
            case "1" -> {
                return registerRecruiter(firstName, lastName, emailAddress, password, db);
            }
            default -> throw new IllegalStateException("Unexpected value: " + accTypeSelection);
        }
    }

    /**
     * Enter method to ask the user to input their expected salary.
     *
     * @return their expected compensation as an int.
     */
    private int enterCompensation()
    {
        UserIO.displayBody("Please enter your expected yearly salary:");
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
        } while (!flag);
        return input;
    }

    /**
     * Enter method to ask the user to enter their email address.
     *
     * @return the user's email address as a String.
     */
    private String enterEmail()
    {
        Validate validator = new Validate();
        UserIO.displayBody("Please enter your email address:");
        String email = UserIO.getInput();
        while (!validator.isValidEmail(email))
        {
            UserIO.displayBody("""
                    Invalid email entered.
                    Please try again:""");
            email = UserIO.getInput();
        }
        return email;
    }

    /**
     * Enter method to ask the user to enter a list of keywords.
     *
     * @return keywords as an ArrayList<String>.
     */
    private ArrayList<String> enterKeywords()
    {
        ArrayList<String> keywords = new ArrayList<>();
        UserIO.displayBody("""
                Please enter at least one keyword which you identify with or align to.
                These keywords will help us match you to relevant jobs.
                Multiple keywords should be separated by a single comma.
                                
                E.g: Excel, Finance, Customer Service, Administration
                """);
        keywords.add(UserIO.enterAttribute(" one or more keywords", 0, 120));
        String[] addAnother = {"Yes", "No"};
        String inputAdd;
        do
        {
            UserIO.clearScreenAndAddTitle("Keywords");
            inputAdd = UserIO.menuSelectorKey("Would you like to add another keyword", addAnother);
            if (inputAdd.equals("0"))
            {
                keywords.add(UserIO.enterAttribute("Keyword", 0, 120));
            }
        } while (inputAdd.equals("0"));
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
        String country = UserIO.menuSelectorValue("Please select your country", countries);
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
        String state = UserIO.menuSelectorValue("Please select your state", states);
        String suburb = UserIO.enterAttribute("your suburb", 1, 30);
        String postcode = UserIO.enterAttribute("your postcode", 4, 4);

        return new Location(country, state, suburb, postcode);
    }

    /**
     * Creates the screen for the User to set a password.
     *
     * @return the password as a String.
     */
    public String enterPassword()
    {
        Validate validator = new Validate();
        String inputPassword;
        String confirmedPassword;
        do
        {
            validator.resetAttempts();
            UserIO.displayBody("Please enter a password that matches the following criteria:\n  1. Has at least 8 characters in length.\n  2. Includes at least one capital and one lower case letter.\n  3. Includes at least one number and one special character.");
            inputPassword = UserIO.getInput().strip();
            while (!validator.isValidPassword(inputPassword))
            {
                UserIO.displayBody("The password you entered does not match the criteria.  Please ensure your password:\n  1. Has at least 8 characters in length.\n  2. Includes at least one capital and one lower case letter.\n  3. Includes at least one number and one special character.");
                inputPassword = UserIO.getInput();
            }
            UserIO.displayBody("Please re-enter your password: ");
            confirmedPassword = UserIO.getInput().strip();

            if (!inputPassword.equals(confirmedPassword))
            {
                UserIO.displayBody("Your password does not match. Please try again: ");
            }

        } while (!inputPassword.equals(confirmedPassword));

        return confirmedPassword;
    }

    /**
     * Method that allows the user to loginHandler
     *
     * @param db The database manager object being used.
     * @return User object.
     */
    private User login(DatabaseManager db)
    {
        return login(null, db);
    }

    /**
     * Method that allows the user to loginHandler
     *
     * @param email email address of the user as a String.
     * @param db    The database manager object being used.
     * @return User object.
     */
    private User login(String email, DatabaseManager db)
    {
        UserIO.clearScreenAndAddTitle("Login");
        if (email == null)
            email = enterEmail();

        String accType = db.getUserType(email);
        if (accType == null)
        {
            String sel = UserIO.menuSelectorKey("There is no account linked with that email address.", new String[]{"Try again", "Register"});
            if (sel.equals("0"))
                return login(db);
            else
                return register(email, db);
        }
        User user;
        switch (accType)
        {
            case "Recruiter" -> user = db.getRecruiter(email);
            case "Job Seeker" -> user = db.getJobSeeker(email);
            default -> throw new IllegalStateException("Unexpected value: " + accType);
        }
        UserIO.displayBody("Please enter your password.");
        String password = UserIO.getInput();
        int attempts = 3;
        while (!password.equals(user.getPassword()))
        {
            attempts--;
            if (attempts == 0)
                return null;
            UserIO.displayBody("Incorrect password. You have " + attempts + " attempts remaining. Please try again.");
            password = UserIO.getInput();
        }
        UserIO.displayBody("Login successful. You will now be redirected to the Home page.");
        UserIO.sleep(3);
        return user;
    }

    /**
     * Method to ask the user to select either "Login", "Register" or "Exit".
     *
     * @return the option selected as a String.
     */
    private String loginOrRegisterSelector()
    {
        UserIO.clearScreenAndAddTitle("Login or Register");
        String[] options = {"Login", "Register", "Exit"};
        return UserIO.menuSelectorValue("Please select one of the options", options);
    }

    /**
     * Method to ask the user to register, or loginHandler if the email address already exist.
     *
     * @param db The database manager object being used.
     * @return User object.
     */
    private User register(DatabaseManager db)
    {
        return register(null, db);
    }

    /**
     * Method to ask the user to register, or loginHandler if the email address already exist.
     *
     * @param emailAddress email address of the User as a String.
     * @param db           The database manager object being used.
     * @return User object.
     */
    private User register(String emailAddress, DatabaseManager db)
    {
        if (emailAddress == null)
        {
            UserIO.displayTitle("Registration");
            emailAddress = enterEmail();
        }
        if (db.getUserType(emailAddress) != null)
        {
            UserIO.clearScreenAndAddTitle("Registration");
            String loginChoice = UserIO.menuSelectorKey("""
                    An account with that email address already exists.
                    Would you like to try to log in, or enter a different email address?""", new String[]{"Log In", "Re-enter email address"});
            switch (loginChoice)
            {
                case "0" -> {
                    return login(emailAddress, db);
                }
                case "1" -> {
                    return register(db);
                }
                default -> throw new IllegalStateException("Unexpected value: " + loginChoice);
            }
        } else
        {
            return createUser(emailAddress, db);
        }
    }

    /**
     * Method to create an account for a job seeker.
     *
     * @param fName        first name of the job seeker as a String.
     * @param lName        last name of the job seeker as a String.
     * @param emailAddress email address of the User as a String.
     * @param password     password for the account as a String.
     * @param db           The database manager object being used.
     * @return User object.
     */
    private User registerJobSeeker(String fName, String lName, String emailAddress, String password, DatabaseManager db)
    {
        String sectionTitle = "Registration";
        UserIO.clearScreenAndAddTitle(sectionTitle);
        UserIO.displayBody("Please enter your date of birth in the form of dd/mm/yyyy");
        Date dateOfBirth = UserIO.enterSQLDate();
        UserIO.clearScreenAndAddTitle(sectionTitle);
        String contactNumber = UserIO.enterAttribute(" your preferred contact number", 8, 12);
        UserIO.clearScreenAndAddTitle(sectionTitle);
        String jobLevel = selectJobLevel();
        UserIO.clearScreenAndAddTitle(sectionTitle);
        String jobName = UserIO.enterAttribute(" your current or most recent job name", 3, 30);
        UserIO.clearScreenAndAddTitle(sectionTitle);
        ArrayList<String> keywords = enterKeywords();
        UserIO.clearScreenAndAddTitle(sectionTitle);
        String userChoice = UserIO.menuSelectorKey("Do you wish to upload a resume to help recruiters find you?", new String[]{"Yes", "No"});
        String resume = null;
        if ("0".equals(userChoice))
        {
            try
            {
                resume = Database.FileManager.returnSelectedFileAsString("Please select a resume file");
            } catch (IOException e)
            {
                UserIO.displayBody("There was an error in uploading your resume.");
            }
        }
        UserIO.clearScreenAndAddTitle(sectionTitle);
        int expectedCompensation = enterCompensation();
        UserIO.clearScreenAndAddTitle(sectionTitle);
        Location location = enterLocation();

        JobSeeker newJobSeeker = new JobSeeker(fName, lName, emailAddress, password, new Date(System.currentTimeMillis()), jobName, jobLevel, contactNumber, resume, location, dateOfBirth, keywords, expectedCompensation);
        JobSeeker insertedJobseeker = null;
        try
        {
            insertedJobseeker = db.insertJobSeeker(newJobSeeker);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }


        UserIO.clearScreenAndAddTitle(sectionTitle);
        UserIO.displayBody("Thank you, your account has now been set up. Press \"Enter\" to be redirected to the home page.");
        UserIO.getInput();
        return insertedJobseeker;
    }

    /**
     * Method to create an account for the recruiter.
     *
     * @param fName        first name of the recruiter as a String.
     * @param lName        last name of the recruiter as a String.
     * @param emailAddress email address of the User as a String.
     * @param password     password for the account as a String.
     * @param db           The database manager object being used.
     * @return User object.
     */
    private User registerRecruiter(String fName, String lName, String emailAddress, String password, DatabaseManager db)
    {
        String sectionTitle = "Registration";
        UserIO.clearScreenAndAddTitle(sectionTitle);
        UserIO.displayBody("Please enter your date of birth in the form of dd/mm/yyyy");
        Date dateOfBirth = UserIO.enterSQLDate();
        UserIO.clearScreenAndAddTitle(sectionTitle);
        String contactNumber = UserIO.enterAttribute("your preferred contact number", 8, 12);
        UserIO.clearScreenAndAddTitle(sectionTitle);
        String companyName = UserIO.enterAttribute("the company you work for", 3, 30);
        UserIO.clearScreenAndAddTitle(sectionTitle);
        String recruitingSpecialty = UserIO.enterAttribute("your recruiting specialty", 3, 30);

        Recruiter newRecruiter = new Recruiter(fName, lName, emailAddress, password, new Date(System.currentTimeMillis()), companyName, recruitingSpecialty, contactNumber, dateOfBirth);
        Recruiter insertedRecruiter = null;
        try
        {
            insertedRecruiter = db.insertRecruiter(newRecruiter);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        UserIO.clearScreenAndAddTitle(sectionTitle);
        UserIO.displayBody("Thank you, your account has now been set up. Press \"Enter\" to be redirected to the home page.");
        UserIO.getInput();
        return insertedRecruiter;
    }

    /**
     * Enter method to ask the user to select a job level.
     *
     * @return Job level as a String.
     */
    private String selectJobLevel()
    {
        String[] jobLevel = {
                "Never employed",
                "Student",
                "Entry level",
                "Mid level",
                "High level",
                "Executive",
                "Other",
                "Prefer not to say"
        };
        String userInput = UserIO.menuSelectorKey("Please select your current or most recent job level:", jobLevel);

        switch (userInput)
        {
            case "0" -> userInput = "Never employed";
            case "1" -> userInput = "Student";
            case "2" -> userInput = "Entry level";
            case "3" -> userInput = "Mid level";
            case "4" -> userInput = "High level";
            case "5" -> userInput = "Executive";
            case "6" -> userInput = "Other";
            case "7" -> userInput = "Prefer not to say";
            default -> throw new IllegalStateException("Unexpected value: " + userInput);
        }
        return userInput;
    }

    /**
     * This method will run the process for loginHandler in or registering as a user, and will pass back the user account.
     *
     * @param db The database manager object being used.
     * @return The logged in user as a User derived object.
     */
    public User startLogin(DatabaseManager db)
    {
        JFrame jFrame = new JFrame();
        welcomeScreen();
        String userAction = loginOrRegisterSelector();
        switch (userAction)
        {
            case "Login" -> {
                return login(db);
            }
            case "Register" -> {
                return register(db);
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Welcome screen for JobSearchie.
     */
    private void welcomeScreen()
    {
        UserIO.displayTitleAndBody("Welcome to Job Searchie", """
                Job Searchie is a job listing market place.  We aim to provide a superb experience for both job seekers and recruiters.

                Enjoy searching for your next dream job or finding the perfect employee.""");
        UserIO.sleep(2);
    }
}

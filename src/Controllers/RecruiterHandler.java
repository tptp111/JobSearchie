package Controllers;

import Database.DatabaseManager;
import Entities.*;
import Utilities.UserIO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the Recruiter entity.
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version 1.0
 */
public class RecruiterHandler extends UserHandler
{

    /**
     * The number of jobs to display on each page when viewing their listed jobs.
     */
    public static final int JOBS_PER_PAGE = 10;

    /**
     * User the UserIO class to print a list of jobs in a table format.
     *
     * @param jobs   A list of jobs to be printed in the table.
     * @param pageNo The page number to determine which job to print.
     */
    private static void printJobs(List<Job> jobs, int pageNo)
    {
        UserIO.printJobHeading(false);
        int counter = 1;
        for (Job job : jobs)
        {
            UserIO.printJobSummary(job, (pageNo * 10 + counter));
            counter++;
        }
    }

    /**
     * Builds options for viewing the recruiters jobs screen.
     *
     * @param pageNo     The page number of jobs to display.
     * @param totalPages The total number of pages of jobs.
     * @return Returns a list of options the Recruiter can then select based on the given inputs.
     */
    private static ArrayList<String[]> buildMyJobsOptions(int pageNo, int totalPages)
    {
        ArrayList<String[]> options = new ArrayList<>();
        StringBuilder jobNos = new StringBuilder();
        for (int i = pageNo * JOBS_PER_PAGE + 1; i < pageNo * JOBS_PER_PAGE + 11; i++)
        {
            jobNos.append(i);
            jobNos.append(",");
        }
        options.add(new String[]{"A job number to view its details: {job number}", jobNos.substring(0, jobNos.length() - 1)});
        if (pageNo < totalPages)
            options.add(new String[]{"`next` to got to the next page:", "next"});
        if (pageNo > 0)
            options.add(new String[]{"`previous` to got to the previous page:", "previous"});
        options.add(new String[]{"`home` to return home:", "home"});
        return options;
    }

    /**
     * Uses the UserIO class to print a job in a pretty way.
     *
     * @param job Job to be printed.
     */
    private static void displayJobDetail(Job job)
    {
        UserIO.printBlock(job.getJobDetailMap());
    }

    /**
     * Uses the UserIO class to print an application in a pretty way.
     *
     * @param application The application to be printed.
     */
    private static void displayApplicationDetail(Application application)
    {
        UserIO.printBlock(application.getApplicationDetailMap());
    }

    /**
     * Creates the screen to ask the user if they would like to delete a profile.
     */
    public void deleteProfile()
    {
        UserIO.clearScreenAndAddTitle("Account Deletion");
        String[] options = {
                "Yes (coming soon)",
                "No"
        };
        String userInput = UserIO.menuSelectorKey("Are you sure you would like to delete your profile?:", options);
        switch (userInput)
        {
            case ("0") -> UserIO.comingSoon();
            case ("1") -> UserIO.displayBody("Deletion cancelled. Returning to Profile Management");
        }
    }

    /**
     * Method to not submit the job.
     */
    public void dontSubmitJob()
    {
        UserIO.displayBody("You have chosen not to submit this job");
        UserIO.displayBody("Returning you to the home screen now");
    }

    /**
     * Creates the home screen for the recruiter.
     *
     * @param recruiter the Recruiter user for the home screen.
     * @param db        the DatabaseManager.
     * @throws SQLException handles SQL exception.
     */
    public void home(Recruiter recruiter, DatabaseManager db) throws SQLException
    {
        boolean flag = true;
        while (flag)
        {
            UserIO.displayHeading("Home Page");
            String[] options = {
                    "Create a job listing",
                    "View my jobs",
                    "Messages (coming soon)",
                    "Offer interview (coming soon)",
                    "Search for a job seeker (coming soon)",
                    "Profile management",
                    "Log Out",
            };
            String userInput = UserIO.menuSelectorKey("Please enter one of the following:", options);
            switch (userInput)
            {
                case ("0") -> postJob(recruiter, db);
                case ("1") -> viewMyJobs(recruiter, db);
                case ("2"), ("3"), ("4") -> UserIO.comingSoon();
                case ("5") -> profileManagement();
                case ("6") -> {
                    flag = false;
                    UserIO.displayBody("Logging out.");
                }
                default -> throw new IllegalStateException("Unexpected value: " + userInput);
            }
        }
    }

    /**
     * Creates the screen for the recruiter to select an option on their job.
     *
     * @param db  The DatabaseManager handling the databaseIO.
     * @param job The Job to view menu.
     */
    public void jobDetailMenu(DatabaseManager db, Job job)
    {
        boolean flag = true;
        while (flag)
        {
            displayJobDetail(job);
            UserIO.displayHeading("Options for jobs");
            String[] options = {
                    "View job applicants",
                    "Search for highly ranked job seekers (coming soon)",
                    "Change advertising status of this job (coming soon)",
                    "Update this job (coming soon)",
                    "Delete this job (coming soon)",
                    "Back"
            };
            String userInput = UserIO.menuSelectorKey("Please select an option", options);
            switch (userInput)
            {
                case "0" -> viewJobApplicants(db, job);
                case "1", "2", "3", "4" -> UserIO.comingSoon();
                case "5" -> flag = false;
            }
        }
    }

    /**
     * Method to ask the recruiter to review their job posting and then post their job.
     *
     * @param recruiter The Recruiter using the program.
     * @param db        The DatabaseManager handling the databaseIO.
     * @throws SQLException handles SQL exception.
     */
    public void postJob(Recruiter recruiter, DatabaseManager db) throws SQLException
    {
        Job job = new JobHandler().createJob(recruiter);
        UserIO.displayHeading("Please review the job details:");
        UserIO.printBlock(job.getJobDetailMap());
        String[] options = {
                "Yes",
                "Return home"
        };
        UserIO.displayHeading("Confirm Submission");
        String option = UserIO.menuSelectorKey("Please confirm if you like to submit this job?", options);
        switch (option)
        {
            case ("0") -> submitJob(db, job);
            case ("1") -> dontSubmitJob();
        }
    }

    /**
     * Creates the profile management screen.
     */
    public void profileManagement()
    {
        boolean flag = true;
        do
        {
            UserIO.displayHeading("Profile Management");
            String[] options = {
                    "Update profile (coming soon)",
                    "Delete profile",
                    "Home",
            };
            String userInput = UserIO.menuSelectorKey("Please enter one of the following:", options);
            switch (userInput)
            {
                case ("0") -> UserIO.comingSoon();
                case ("1") -> deleteProfile();
                case ("2") -> {
                    UserIO.displayBody("Returning to Home.");
                    flag = false;
                }
                default -> throw new IllegalStateException("Unexpected value: " + userInput);
            }
        } while (flag);
    }

    /**
     * Method to submit the job.
     *
     * @param db  The DatabaseManager handling the databaseIO.
     * @param job The Job to submit to database.
     * @throws SQLException handles SQL exception.
     */
    public void submitJob(DatabaseManager db, Job job) throws SQLException
    {
        if (db.insertJob(job) != null)
        {
            UserIO.displayBody("You have submitted the job. Thank you for using Job Searchie.");
        } else
        {
            UserIO.displayBody("Job could not be submitted at this time. Please try again later.");
        }
        UserIO.displayBody("Returning to home screen now");
    }

    /**
     * Method to view job applicants for a job.
     *
     * @param db  The DatabaseManager handling the databaseIO.
     * @param job The Job to view applicants.
     */
    public void viewJobApplicants(DatabaseManager db, Job job)
    {
        UserIO.displayHeading("Displaying applicants for " + job.getJobTitle());
        ArrayList<Application> applications = db.getJobApplications(job);
        for (Application application : applications)
        {
            displayApplicationDetail(application);
            System.out.println("\n");
        }
        viewJobApplicantsMenu();
    }

    /**
     * Provides menu options when job applications screen is active.
     */
    private void viewJobApplicantsMenu()
    {
        boolean flag = true;
        while (flag)
        {
            UserIO.displayHeading("Options for Applicants");
            String[] options = {
                    "Offer job (Coming soon)",
                    "Offer interview (Coming soon)",
                    "Reject (coming soon)",
                    "Back"
            };
            String userInput = UserIO.menuSelectorKey("Please select an option", options);
            switch (userInput)
            {
                case ("0"), ("2"), ("1") -> UserIO.comingSoon();
                case ("3") -> flag = false;
            }
        }
    }

    /**
     * Method to view jobs posted.
     *
     * @param recruiter The Recruiter using the program.
     * @param db        The DatabaseManager handling the databaseIO.
     */
    public void viewMyJobs(Recruiter recruiter, DatabaseManager db)
    {
        UserIO.displayTitle("Viewing my Jobs");
        ArrayList<Job> jobs = db.getAllJobs();
        jobs.removeIf(job -> !job.getAuthor().getEmail().equals(recruiter.getEmail()));

        int pageNo = 0;
        int totalPages = jobs.size() / JOBS_PER_PAGE;
        label:
        do
        {
            try
            {
                printJobs(jobs.subList(pageNo * JOBS_PER_PAGE, pageNo * JOBS_PER_PAGE + JOBS_PER_PAGE), pageNo);
            } catch (Exception e)
            {
                printJobs(jobs.subList(pageNo * JOBS_PER_PAGE, jobs.size()), pageNo);
            }
            UserIO.printCenter("Page " + (pageNo + 1) + " of " + (totalPages + 1));
            ArrayList<String[]> options = buildMyJobsOptions(pageNo, totalPages);
            String stringSelection = UserIO.getSelection("Please select one of the above options", options);
            switch (stringSelection)
            {
                case "previous":
                    pageNo--;
                    break;
                case "next":
                    pageNo++;
                    break;
                case "home":
                    break label;
                default:
                    UserIO.clearScreen();
                    Job job = jobs.get(Integer.parseInt(stringSelection) - 1);
                    jobDetailMenu(db, job);
                    break;
            }
        } while (true);
    }
}

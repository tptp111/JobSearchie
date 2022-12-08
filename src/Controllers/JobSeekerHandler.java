package Controllers;

import Database.DatabaseManager;
import Database.FileManager;
import Entities.Application;
import Entities.Invitation;
import Entities.Job;
import Entities.JobSeeker;
import Utilities.UserIO;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Controller class for the JobSeeker entity.
 *
 * @author Charlie Timlock, Levi Quilliam, Tim Perkins, and Merrill Nguyen
 * @version v1.0
 */
public class JobSeekerHandler extends UserHandler
{

    private static final int JOBS_PER_PAGE = 10;

    private static ArrayList<Job> applyFilter(ArrayList<Job> jobs, JobSeeker jobSeeker)
    {
        String[] options = new String[]{"Filter by minimum personal relevancy", "Filter by maximum personal relevancy", "Filter by minimum compensation", "Filter by maximum compensation",};
        String option = UserIO.menuSelectorKey("Please select an option to filter by: ", options);
        switch (option)
        {
            case "0":
                return filterByMinimumPersonalRelevancy(jobs, jobSeeker);
            case "1":
                return filterByMaximumPersonalRelevancy(jobs, jobSeeker);
            case "2":
                return filterByMinimumCompensation(jobs);
            case "3":
                return filterByMaximumCompensation(jobs);
            default:
                System.out.println("Error in JobSearchHandler -> applyFilter.");
                return jobs;
        }
    }

    /**
     * This method will submit an application for a job.
     *
     * @param jobSeeker The applicant.
     * @param db        The database manager.
     * @param job       The job being applied to.
     */
    public static void applyForJob(JobSeeker jobSeeker, DatabaseManager db, Job job)
    {
        String sectionHeading = "Application";
        UserIO.displayTitle(sectionHeading);
        String coverLetterPath = "";
        String resumePath = "";
        String coverLetterExt = "";
        String resumeExt = "";
        String selection = UserIO.menuSelectorValue("Do you wish to add a cover letter to your application?", new String[]{"Yes", "No"});
        if (selection.equals("Yes"))
        {
            coverLetterPath = FileManager.selectFilePath("Select a cover letter file", new String[]{"pdf", "docx", "doc", "txt"});
            if (!coverLetterPath.isEmpty() && coverLetterPath != null)
            {
                coverLetterExt = FileManager.getExtensionFromPath(coverLetterPath);
            }
        }
        UserIO.clearScreenAndAddTitle(sectionHeading);
        selection = UserIO.menuSelectorValue("Do you wish to add a resume to your application?", new String[]{"Yes", "No"});
        if (selection.equals("Yes"))
        {
            resumePath = FileManager.selectFilePath("Select a resume file", new String[]{"pdf", "docx", "doc", "txt"});
            if (!resumePath.isEmpty() && resumePath != null)
            {
                resumeExt = FileManager.getExtensionFromPath(resumePath);
            }
        }
        String status = "Pending";
        try
        {
            Application submitted = db.insertApplication(new Application(jobSeeker, job, coverLetterExt, resumeExt, status, new Date(System.currentTimeMillis())));
            String applicationID = String.valueOf(submitted.getId());
            if (!coverLetterPath.isEmpty() && coverLetterPath != null)
            {
                FileManager.moveFileToJSStorage(FileManager.COVER_LETTER_DIRECTORY, coverLetterPath, applicationID);
            }
            if (!resumePath.isEmpty() && resumePath != null)
            {
                FileManager.moveFileToJSStorage(FileManager.RESUME_DIRECTORY, resumePath, applicationID);
            }
            UserIO.clearScreenAndAddTitle(sectionHeading);
            UserIO.displayBody("Application submitted. Press \"Enter\" to exit.");
            UserIO.getInput();
        } catch (SQLException | IOException e)
        {
            UserIO.clearScreenAndAddTitle(sectionHeading);
            UserIO.displayBody("There was an issue in submitting your application. Please try again.");
            UserIO.displayBody("Press \"Enter\" to exit.");
            UserIO.getInput();
        }
    }

    private static ArrayList<Job> applySort(ArrayList<Job> jobs, JobSeeker jobSeeker)
    {
        String[] options = new String[]{"Sort by personal relevancy descending", "Sort by compensation ascending", "Sort by compensation descending"};
        String option = UserIO.menuSelectorKey("Please select an option to sort by: ", options);
        switch (option)
        {
            case "0":
                return sortByRelevancy(jobs, jobSeeker);
            case "1":
                return sortByCompensationDec(jobs);
            case "2":
                return sortByCompensationAsc(jobs);
            default:
                System.out.println("Error should not be able to reach here");
                return jobs;
        }
    }

    private static ArrayList<String[]> buildInvitationOptions(int pageNo, int totalPages)
    {
        ArrayList<String[]> options = new ArrayList<>();
        StringBuilder invitationNos = new StringBuilder();
        for (int i = pageNo * JOBS_PER_PAGE + 1; i < pageNo * JOBS_PER_PAGE + 11; i++)
        {
            invitationNos.append(i);
            invitationNos.append(",");
        }
        options.add(new String[]{"Select an interview number to view its details: {interview number}", invitationNos.substring(0, invitationNos.length() - 1)});
        if (pageNo < totalPages)
            options.add(new String[]{"`next` to got to the next page:", "next"});
        if (pageNo > 0)
            options.add(new String[]{"`previous` to got to the previous page:", "previous"});
        options.add(new String[]{"`home` to return home:", "home"});
        return options;
    }

    private static ArrayList<String[]> buildJobOptions(int pageNo, int totalPages)
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
        options.add(new String[]{"`back` to return to the search menu:", "back"});
        options.add(new String[]{"`home` to return home:", "home"});
        return options;
    }

    private static ArrayList<Job> defaultFilter(ArrayList<Job> jobs, String searchTerm)
    {
        OptionalInt maxCosine = jobs.stream().mapToInt(job -> job.getCosine(searchTerm)).max();
        OptionalInt minCosine = jobs.stream().mapToInt(job -> job.getCosine(searchTerm)).min();
        return reverseJobs(jobs.stream().filter(job -> job.getCosine(searchTerm) >= ((maxCosine.getAsInt() - minCosine.getAsInt()) / 2)).filter(Job::getIsAdvertised).collect(Collectors.toCollection(ArrayList::new)));
    }

    private static ArrayList<Job> defaultSort(ArrayList<Job> jobs, String searchTerm)
    {
        return reverseJobs(jobs.stream().sorted(Comparator.comparing(job -> job.getCosine(searchTerm))).collect(Collectors.toCollection(ArrayList::new)));
    }

    private static void displayJobDetail(Job job, JobSeeker jobSeeker)
    {
        UserIO.printBlock(job.getJobDetailMap(jobSeeker));
    }

    private static void displayJobs(ArrayList<Job> jobs, JobSeeker jobSeeker, DatabaseManager db)
    {
        int pageNo = 0;
        int totalPages = jobs.size() / JOBS_PER_PAGE;
        label:
        do
        {
            UserIO.displayTitle("Search Results");
            try
            {
                printJobs(jobs.subList(pageNo * JOBS_PER_PAGE, pageNo * JOBS_PER_PAGE + JOBS_PER_PAGE), jobSeeker, pageNo);
            } catch (Exception e)
            {
                printJobs(jobs.subList(pageNo * JOBS_PER_PAGE, jobs.size()), jobSeeker, pageNo);
            }
            UserIO.printCenter("Page " + (pageNo + 1) + " of " + (totalPages + 1));
            ArrayList<String[]> options = buildJobOptions(pageNo, totalPages);

            String stringSelection = UserIO.getSelection("Please select one of the above options", options);
            UserIO.clearScreen(30);
            switch (stringSelection)
            {
                case "previous":
                    pageNo--;
                    break;
                case "next":
                    pageNo++;
                    break;
                case "back":
                    search(jobSeeker, db);
                    break;
                case "home":
                    break label;
                default:
                    Job job = jobs.get(Integer.parseInt(stringSelection) - 1);
                    displayJobDetail(job, jobSeeker);
                    do
                    {
                        String[] jobOptions = new String[]{"Apply for job", "Report job (Coming soon)!", "Go Back"};
                        String selection = UserIO.menuSelectorKey("Please select an option from above: ", jobOptions);
                        if (selection.equals("0"))
                        {
                            applyForJob(jobSeeker, db, job);
                            break;
                        } else if (selection.equals("1"))
                            UserIO.comingSoon();
                        else
                            break;
                    } while (true);
                    break;
            }
        } while (true);
    }

    private static ArrayList<Job> filterByMaximumCompensation(ArrayList<Job> jobs)
    {
        UserIO.displayBody("Please enter the maximum compensation you would like to filter by");
        int maximum = UserIO.getNumericAttribute(0, 1000000);
        return jobs.stream().filter(job -> job.getCompensation() <= maximum).collect(Collectors.toCollection(ArrayList::new));
    }

    private static ArrayList<Job> filterByMaximumPersonalRelevancy(ArrayList<Job> jobs, JobSeeker jobSeeker)
    {
        UserIO.displayBody("Please enter the maximum personal relevancy you would like to filter by");
        int maximum = UserIO.getNumericAttribute(0, 100);
        return jobs.stream().filter(job -> job.getPersonalRelevancy(jobSeeker) <= maximum).collect(Collectors.toCollection(ArrayList::new));
    }

    private static ArrayList<Job> filterByMinimumCompensation(ArrayList<Job> jobs)
    {
        UserIO.displayBody("Please enter the minimum compensation you would like to filter by");
        int minimum = UserIO.getNumericAttribute(0, 1000000);
        return jobs.stream().filter(job -> job.getCompensation() >= minimum).collect(Collectors.toCollection(ArrayList::new));
    }

    private static ArrayList<Job> filterByMinimumPersonalRelevancy(ArrayList<Job> jobs, JobSeeker jobSeeker)
    {
        UserIO.displayBody("Please enter the minimum personal relevancy you would like to filter by");
        int minimum = UserIO.getNumericAttribute(0, 100);
        return jobs.stream().filter(job -> job.getPersonalRelevancy(jobSeeker) >= minimum).collect(Collectors.toCollection(ArrayList::new));
    }

    private static ArrayList<Job> filterJobs(ArrayList<Job> jobs, JobSeeker jobSeeker)
    {
        String[] options = new String[]{"Yes", "No"};
        String selection = UserIO.menuSelectorKey("Would you like to filter the results?", options);
        int counter = 0;
        while (selection.equals("0") && counter < 4)
        {
            jobs = applyFilter(jobs, jobSeeker);
            selection = UserIO.menuSelectorKey("Would you like to apply another filter?", options);
            counter++;
        }
        if (counter != 0)
            UserIO.displayBody("All filters have now been applied");
        return jobs;
    }

    private static String getSearchTerm()
    {
        UserIO.displayBody("Please enter a term you would like to search for:");
        return UserIO.getInput().toLowerCase();
    }

    private static void printInvitations(List<Invitation> invitations, int pageNo)
    {
        UserIO.printInvitationHeading();
        int counter = 1;
        for (Invitation invitation : invitations)
        {
            UserIO.printInvitationSummary(invitation, (pageNo * 10 + counter));
            counter++;
        }
    }

    private static void printJobs(List<Job> jobs, JobSeeker jobSeeker, int pageNo)
    {
        UserIO.printJobHeading(true);
        int counter = 1;
        for (Job job : jobs)
        {
            UserIO.printJobSummary(job, job.getPersonalRelevancy(jobSeeker), (pageNo * 10 + counter));
            counter++;
        }
    }

    private static ArrayList<Job> reverseJobs(ArrayList<Job> jobs)
    {
        if (jobs.size() > 1)
        {
            Job value = jobs.remove(0);
            reverseJobs(jobs);
            jobs.add(value);
        }
        return jobs;
    }

    /**
     * Main search method which is used by a job seeker.
     *
     * @param db        The database instance which is used throughout the program.
     * @param jobSeeker The job seeker who is currently logged in.
     */
    public static void search(JobSeeker jobSeeker, DatabaseManager db)
    {
        UserIO.displayTitleAndBody("Search", "Let us help you find your next dream job!");
        String searchTerm = getSearchTerm();
        ArrayList<Job> jobs;
        do
        {
            jobs = db.getAllJobs();
            jobs = defaultFilter(jobs, searchTerm);
            if (jobs.size() == 0)
            {
                String[] options = new String[]{"Yes", "No"};
                String selection = UserIO.menuSelectorKey("We couldn't find any jobs matching your search term, would you like to try again?", options);
                if (!selection.equals("0"))
                    searchTerm = getSearchTerm();
                else
                    return;
            } else
            {
                jobs = filterJobs(jobs, jobSeeker);
                if (jobs.size() == 0)
                {
                    String[] options = new String[]{"Yes", "No"};
                    String selection = UserIO.menuSelectorKey("Sorry there are no jobs that satisfy the filters applied, would you like to try again?", options);
                    if (!selection.equals("0"))
                    {
                        searchTerm = getSearchTerm();
                    } else
                    {
                        return;
                    }
                } else
                {
                    break;
                }
            }
        } while (true);
        jobs = sortJobs(jobs, jobSeeker, searchTerm);
        displayJobs(jobs, jobSeeker, db);
    }

    private static ArrayList<Job> sortByCompensationAsc(ArrayList<Job> jobs)
    {
        return jobs.stream().sorted(Comparator.comparing(Job::getCompensation)).collect(Collectors.toCollection(ArrayList::new));
    }

    private static ArrayList<Job> sortByCompensationDec(ArrayList<Job> jobs)
    {
        return reverseJobs(jobs.stream().sorted(Comparator.comparing(Job::getCompensation)).collect(Collectors.toCollection(ArrayList::new)));
    }

    private static ArrayList<Job> sortByRelevancy(ArrayList<Job> jobs, JobSeeker jobSeeker)
    {
        return reverseJobs(jobs.stream().sorted(Comparator.comparing(job -> job.getPersonalRelevancy(jobSeeker))).collect(Collectors.toCollection(ArrayList::new)));
    }

    private static ArrayList<Job> sortJobs(ArrayList<Job> jobs, JobSeeker jobSeeker, String searchTerm)
    {
        String[] options = new String[]{"Yes", "No"};
        String selection = UserIO.menuSelectorKey("Would you like to sort the result?", options);
        if (selection.equals("0"))
            return applySort(jobs, jobSeeker);
        else
            return defaultSort(jobs, searchTerm);
    }

    /**
     * Displays the delete profile screen.
     */
    public void deleteProfile()
    {
        boolean flag = true;
        do
        {
            String[] options = {"Yes (coming soon)", "No", "Back"};
            String userInput = UserIO.menuSelectorKey("Are you sure you would like to delete your profile?:", options);

            switch (userInput)
            {
                case "0" -> UserIO.comingSoon();
                case "1", "2" -> {
                    flag = false;
                    UserIO.displayBody("Returning to profile management page.");
                }

            }
        } while (flag);
    }

    private void displayInvitationDetail(Invitation invitation)
    {
        UserIO.printBlock(invitation.getInvitationDetailMap());
    }

    public void displayInvitations(ArrayList<Invitation> invitations, DatabaseManager db)
    {
        int pageNo = 0;
        int totalPages = invitations.size() / JOBS_PER_PAGE;
        label:
        do
        {
            UserIO.displayTitle("Invitations");
            try
            {
                printInvitations(invitations.subList(pageNo * JOBS_PER_PAGE, pageNo * JOBS_PER_PAGE + JOBS_PER_PAGE), pageNo);
            } catch (Exception e)
            {
                printInvitations(invitations.subList(pageNo * JOBS_PER_PAGE, invitations.size()), pageNo);
            }
            UserIO.printCenter("Page " + (pageNo + 1) + " of " + (totalPages + 1));
            ArrayList<String[]> options = buildInvitationOptions(pageNo, totalPages);

            String stringSelection = UserIO.getSelection("Please select one of the above options: ", options);
            UserIO.clearScreen(30);
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
                    Invitation invitation = invitations.get(Integer.parseInt(stringSelection) - 1);
                    boolean flag = true;
                    while (flag)
                    {
                        displayInvitationDetail(invitation);
                        String[] InterviewOptions = new String[]{"View Job Details", "Contact Recruiter (Coming soon)", "Remove application (Coming soon)", "Go Back"};
                        String selection = UserIO.menuSelectorKey("Please select an option from above: ", InterviewOptions);
                        UserIO.clearScreen();
                        switch (selection)
                        {
                            case "0" -> showInterviewJobDetails(invitation.getJob(), invitation.getJobSeeker());
                            case "1", "2" -> UserIO.comingSoon();
                            default -> flag = false;
                        }
                    }
            }
        } while (true);
    }

    /**
     * Creates the home screen for the Job Seeker.
     *
     * @param jobSeeker the JobSeeker user for the home screen.
     * @param db        the DatabaseManager.
     */
    public void home(JobSeeker jobSeeker, DatabaseManager db)
    {
        boolean flag = true;
        do
        {
            UserIO.displayTitle("Home");
            String[] options = {"Search for a job", "View watchlist (coming soon)", "Messages (coming soon)", "View pending job applications (coming soon)", "View job interviews", "Profile management", "Logout"};

            String userInput = UserIO.menuSelectorKey("Please enter one of the above options:", options);

            switch (userInput)
            {
                case ("0") -> search(jobSeeker, db);
                case ("1"), ("2"), ("3") -> UserIO.comingSoon();
                case ("4") -> jobInterviews(jobSeeker, db);
                case ("5") -> profileManagement();
                case ("6") -> {
                    flag = false;
                    UserIO.displayBody("Logging out.");
                }
                default -> throw new IllegalStateException("Unexpected value: " + userInput);
            }
        } while (flag);
    }

    public void jobInterviews(JobSeeker jobSeeker, DatabaseManager db)
    {
        ArrayList<Invitation> invitations;
        invitations = db.getAllInvitations();
        invitations.removeIf(invitation -> invitation.getJobSeeker().getEmail().equals(jobSeeker.getEmail()));
        if (invitations.size() == 0)
        {
            String[] options = new String[]{"Home"};
            UserIO.menuSelectorKey("Unfortunately you do not currently have any invitations, try applying for some more jobs.", options);
            return;
        }
        displayInvitations(invitations, db);
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
            String[] options = {"Update profile (coming soon)", "Delete profile", "Home",};
            String userInput = UserIO.menuSelectorKey("Please enter one of the following:", options);

            switch (userInput)
            {
                case ("0") -> UserIO.comingSoon();
                case ("1") -> deleteProfile();
                case ("2") -> {
                    flag = false;
                    UserIO.displayBody("Returning to Home page.");
                }
                default -> throw new IllegalStateException("Unexpected value: " + userInput);
            }
        } while (flag);

    }

    private void showInterviewJobDetails(Job job, JobSeeker jobSeeker)
    {
        displayJobDetail(job, jobSeeker);
        String[] InterviewOptions = new String[]{"Back",};
        UserIO.menuSelectorKey("Please select an option from below", InterviewOptions);
        UserIO.clearScreen();
    }

    /**
     * Method to view invitation.
     *
     * @param invitation as Invitation.
     */
    private void viewInvitation(Invitation invitation)
    {
    }
}

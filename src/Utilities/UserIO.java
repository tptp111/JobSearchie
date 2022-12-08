package Utilities;

import Entities.Invitation;
import Entities.Job;
import Entities.JobSeeker;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Boundary class used to interact with the user. Prints to the terminal window as well as
 */
public class UserIO
{
    private static final int DEFAULT_SLEEP_TIME = 2;
    private static final int CHAR_WIDTH = 110;
    private static final int ATTRIBUTE_WIDTH = 20;
    private static final int VALUE_WIDTH = CHAR_WIDTH - ATTRIBUTE_WIDTH;

    public static void comingSoon()
    {
        clearScreen(20);
        displayBody("Please sit tight, this functionality is coming soon!");
        sleep(2);
        clearScreen(20);

    }

    /**
     * This is method puts a default delay on moving to the next line of code.
     */
    public static void sleep()
    {
        sleep(DEFAULT_SLEEP_TIME);
    }

    /**
     * This puts a specified delay on moving to the next line of code.
     *
     * @param seconds The delay time, in seconds.
     */
    public static void sleep(int seconds)
    {
        try
        {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception ignored)
        {
        }
    }

    /**
     * This method displays elements from an array list.
     *
     * @param arrayList Accepts an array list
     * @param <E>       The element type of the array list.
     */
    public static <E> void displayArrayList(ArrayList<E> arrayList)
    {
        try
        {
            for (E e : arrayList)
            {
                System.out.println(e);
            }
        } catch (NullPointerException n)
        {
            System.out.print("");
        }
    }

    /**
     * This method displays a body of text as a string.
     *
     * @param body Accepts a body of text as a string.
     */
    public static void displayBody(String body)
    {
        StringBuilder sb = new StringBuilder();
        int currentCount = 0;
        String[] paragraphs = body.split("\n");
        for (String paragraph : paragraphs)
        {
            String[] words = paragraph.split(" ");
            for (String word : words)
            {
                if (currentCount + word.length() > CHAR_WIDTH)
                {
                    sb.append("\n");
                    currentCount = 0;
                }
                sb.append(word).append(" ");
                currentCount += word.length() + 1;
            }
            sb.append("\n");
            currentCount = 0;
        }
        if (sb.length() > 0)
            sb.delete(sb.length() - 1, sb.length());
        System.out.println(sb);
    }

    /**
     * This method displays a heading for a body of text as a string.
     *
     * @param heading Accepts a heading for a body of text as a string.
     */
    public static void displayHeading(String heading)
    {
        displayMain(heading, "-");
    }

    /**
     * This method displays options that a user can select, within a particular functionality.
     * It does not accept or return a response.
     *
     * @param options Accepts possible options to choose from as an array of strings.
     */
    public static void displayOptions(String[] options)
    {
        //Add in the CHAR_WIDTH functionality
        for (int i = 1; i <= options.length; i++)
        {
            System.out.println(i + ". " + options[i - 1]);
        }
    }

    /**
     * This method displays the title of a particular functionality.
     *
     * @param title Accepts the name of a title as a string.
     */
    public static void displayTitle(String title)
    {
        displayMain(title, "=");
    }

    /**
     * This method creates horizontals dividers with a title centered inside them.
     *
     * @param string    The title to be displayed.
     * @param separator The character to use for the dividers.
     */
    private static void displayMain(String string, String separator)
    {
        StringBuilder sb = new StringBuilder();
        int spacesBefore = (CHAR_WIDTH - string.length()) / 2;
        int spacesAfter = (CHAR_WIDTH - string.length() - spacesBefore);
        sb
                .append(" ".repeat(spacesBefore))
                .append(string)
                .append(" ".repeat(spacesAfter))
                .append("\n")
                .append(separator.repeat(CHAR_WIDTH));
        System.out.println(sb);
    }

    /**
     * This method returns the user's input as a String.
     *
     * @return The user's input as a String.
     */
    public static String getInput()
    {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * This methods prints a horizontal divider.
     *
     * @param separator The symbol used to create the divider.
     */
    private static void displayLineBreak(String separator)
    {
        System.out.println(separator.repeat(CHAR_WIDTH));
    }

    /**
     * Dispalys a title and body of text, separated by dividers.
     *
     * @param title The title text.
     * @param body  The body text.
     */
    public static void displayTitleAndBody(String title, String body)
    {
        displayTitle(title);
        displayBody(body);
        displayLineBreak("=");
    }

    /**
     * This method pushes the console with a default number of empty new lines.
     */
    public static void clearScreen()
    {
        clearScreen(5);
    }

    /**
     * This method pushes the console with a specified number of empty new lines.
     *
     * @param lines The specified number of lines.
     */
    public static void clearScreen(int lines)
    {
        for (int i = 0; i < lines; i++)
        {
            System.out.println();
        }
    }

    /**
     * This method pushes the console with a default number of empty new lines, and then displays a title.
     *
     * @param title The title text.
     */
    public static void clearScreenAndAddTitle(String title)
    {
        UserIO.clearScreen();

        UserIO.displayTitle(title);
    }

    /**
     * This method returns the user's input as a string, after asking for an input.
     *
     * @param attributeString The request string, formatter as "Please enter " + attributeString + ":".
     * @param minLength       The minimum allowed length of response.
     * @param maxLength       The maximum allowed length of response.
     * @return The user's response, as a String.
     */
    public static String enterAttribute(String attributeString, int minLength, int maxLength)
    {
        Validate valid = new Validate();
        String attributeValue;
        boolean flag;
        do
        {
            UserIO.displayBody("Please enter " + attributeString + ":");
            attributeValue = UserIO.getInput().trim();
            flag = valid.isValidLength(attributeValue, minLength, maxLength);
            if (!flag)
            {
                UserIO.displayBody("The " + attributeString + " must be between " + minLength + " and " + maxLength + " characters:");
            }
        }
        while (!flag);
        return attributeValue;
    }

    /**
     * Returns an entered string as an SQL Date. Must be formatted as DD-MM-YYYY with "/", ".", or "-" as a delimiter.
     *
     * @return The SQL date.
     */
    public static Date enterSQLDate()
    {
        Scanner scanner = new Scanner(System.in).useDelimiter("/");
        boolean success = false;
        Date date = Date.valueOf("1901-01-01");
        do
        {
            try
            {
                String input = scanner.nextLine();

                String[] chunk = input.split("[./-]");

                String dateString = chunk[2] + "-" + chunk[1] + "-" + chunk[0];

                date = Date.valueOf(dateString);
                if (date.after(new Date(System.currentTimeMillis())) || date.before(Date.valueOf("1901-01-01")))
                {
                    throw new Exception();
                }
                success = true;
            } catch (Exception e)
            {
                UserIO.displayBody("Invalid date entered. Please try again.");
            }

        } while (!success);
        return date;
    }

    /**
     * Returns the user's input as an integer.
     *
     * @return The input, as an int.
     */
    private static int getNumericInput()
    {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * This method returns the user's input as an integer, after asking for an input.
     *
     * @param attributeName The request string, formatter as "Please enter " + attributeString + ":".
     * @param minLength     The minimum allowed length of response.
     * @param maxLength     The maximum allowed length of response.
     * @return The user's response, as an Int.
     */
    public static int getNumericAttribute(String attributeName, int minLength, int maxLength)
    {
        Validate valid = new Validate();
        int numbericValue;
        boolean flag;
        do
        {
            UserIO.displayBody("Please enter " + attributeName + ":");
            numbericValue = UserIO.getNumericInput();
            flag = valid.isValidIntegerLength(numbericValue, minLength, maxLength);
            if (!flag)
            {
                UserIO.displayBody("The " + attributeName + " must be between " + minLength + " and " + maxLength + " characters:");
            }
        }
        while (!flag);
        return numbericValue;
    }

    /**
     * Provides a selection menu and question, and returns the selected option.
     *
     * @param question The question prompting the selection.
     * @param options  The options to be selected from.
     * @return The selected option, as a String.
     */
    public static String menuSelectorValue(String question, String[] options)
    {
        UserIO.displayBody(question);
        UserIO.displayOptions(options);

        boolean flag = false;
        int optionIndex = 0;
        do
        {
            try
            {
                String input = UserIO.getInput().trim();
                optionIndex = Integer.parseInt(input) - 1;
                if (optionIndex <= options.length - 1 && optionIndex >= 0)
                {
                    flag = true;
                } else
                {
                    UserIO.displayBody("An invalid option has been selected");
                    UserIO.displayBody("Please select an option between 1 and " + options.length);
                }
            } catch (NumberFormatException e)
            {
                UserIO.displayBody("You have not entered a number.");
                UserIO.displayBody("Please select an option between 1 and " + options.length);
            } catch (Exception e)
            {
                UserIO.displayBody("An error has occurred.");
                UserIO.displayBody("Please select an option between 1 and " + options.length);
            }
        }
        while (!flag);

        return options[optionIndex];
    }

    /**
     * Provides a selection menu and question, and returns the selected option.
     *
     * @param question The question prompting the selection.
     * @param options  The options to be selected from.
     * @return The selected option's index, as a String. The first option will return "0", etc.
     */
    public static String menuSelectorKey(String question, String[] options)
    {
        UserIO.displayBody("\n" + question);
        UserIO.displayOptions(options);

        boolean flag = false;
        int optionIndex = 0;
        do
        {
            try
            {
                String input = UserIO.getInput().trim();
                optionIndex = Integer.parseInt(input) - 1;
                if (optionIndex <= options.length - 1 && optionIndex >= 0)
                {
                    flag = true;
                } else
                {
                    UserIO.displayBody("An invalid option has been selected");
                    UserIO.displayBody("Please select an option between 1 and " + options.length);
                }
            } catch (NumberFormatException e)
            {
                UserIO.displayBody("You have not entered a number.");
                UserIO.displayBody("Please select an option between 1 and " + options.length);
            } catch (Exception e)
            {
                UserIO.displayBody("An error has occurred.");
                UserIO.displayBody("Please select an option between 1 and " + options.length);
            }
        }
        while (!flag);

        return String.valueOf(optionIndex);
    }

    /**
     * Prints provided text in a block to a specified width, with a supplied vertical separator.
     *
     * @param text      The text to be printed.
     * @param separator The separator character.
     * @param charWidth The overall width.
     */
    private static void printBlock(String text, String separator, int charWidth)
    {
        if (text == null)
        {
            text = "N/A";
        }
        int textLength = text.length();
        int sepLength = separator.length();
        if ((textLength + sepLength) > charWidth)
            text = text.substring(0, charWidth - separator.length() - 3) + "...";
        textLength = text.length();

        int remaining = charWidth - textLength - sepLength;
        text = " ".repeat(remaining / 2) + text;
        if (remaining % 2 == 0)
            text += " ".repeat(remaining / 2);
        else
            text += " ".repeat(remaining / 2 + 1);
        text += separator;
        System.out.print(text);
    }

    /**
     * Prints the Heading of the Job.
     *
     * @param includePersonalRelevancy Determines whether the user's personal relevancy score is displayed in the heading.
     */
    public static void printJobHeading(boolean includePersonalRelevancy)
    {
        printBlock("No.", " | ", 10);
        printBlock("Job Title", " | ", includePersonalRelevancy ? 20 : 25);
        printBlock("Company", " | ", includePersonalRelevancy ? 20 : 25);
        printBlock("State, City", " | ", includePersonalRelevancy ? 20 : 25);
        printBlock("Compensation", " | ", includePersonalRelevancy ? 20 : 25);
        if (includePersonalRelevancy)
            printBlock("Personal Relevancy", " | ", 20);
        System.out.println("\n" + "-".repeat(CHAR_WIDTH));
    }

    /**
     * Formats compensation with commas and cents.
     *
     * @param compensation The compensation to be printed.
     * @return The formatted compensation as a String.
     */
    public static String formatCompensation(double compensation)
    {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return "$" + formatter.format(compensation);
    }

    /**
     * Prints the Job Summary.
     *
     * @param job    The job to be printed,
     * @param number The number as indexed on the job list.
     */
    public static void printJobSummary(Job job, int number)
    {
        String amount = formatCompensation(job.getCompensation());

        printBlock(String.valueOf(number), " - ", 10);
        printBlock(job.getJobTitle(), " - ", 25);
        printBlock(job.getCompany(), " - ", 25);
        printBlock(job.getLocation().getState() + ", " + job.getLocation().getCity(), " - ", 25);
        printBlock(amount, " - ", 25);
        System.out.println("\n");
    }

    /**
     * Prints the Job Summary.
     *
     * @param job       The job to be printed,
     * @param number    The number as indexed on the job list.
     * @param relevancy the personal relevancy of the job to the user.
     */
    public static void printJobSummary(Job job, int relevancy, int number)
    {
        String amount = formatCompensation(job.getCompensation());

        printBlock(String.valueOf(number), " - ", 10);
        printBlock(job.getJobTitle(), " - ", 20);
        printBlock(job.getCompany(), " - ", 20);
        printBlock(job.getLocation().getState() + ", " + job.getLocation().getCity(), " - ", 20);
        printBlock(amount, " - ", 20);
        printBlock(String.valueOf(relevancy), " - ", 20);
        System.out.println("\n");
    }

    /**
     * Accepts user input as an integer.
     *
     * @param minLength The minimum allowable number of digits.
     * @param maxLength The maximum allowable number of digits.
     * @return The response, as an int.
     */
    public static int getNumericAttribute(int minLength, int maxLength)
    {
        Validate valid = new Validate();
        int numberValue;
        boolean flag;
        do
        {
            numberValue = UserIO.getNumericInput();
            flag = valid.isValidIntegerLength(numberValue, minLength, maxLength);
            if (!flag)
            {
                UserIO.displayBody("The entered value must be between " + minLength + " and " + maxLength + " characters:");
            }
        }
        while (!flag);
        return numberValue;
    }

    /**
     * Prints a string in the centre of the display.
     *
     * @param text The text to print.
     */
    public static void printCenter(String text)
    {
        int remaining = CHAR_WIDTH - text.length();
        text = " ".repeat(remaining / 2) + text;
        if (remaining % 2 == 0)
            text += " ".repeat(remaining / 2);
        else
            text += " ".repeat(remaining / 2 + 1);
        System.out.println(text);
    }

    /**
     * Prints the details of a given job.
     *
     * @param job       The job to be printed.
     * @param jobSeeker The jobseeker viewing the job.
     */
    public static void printJobDetail(Job job, JobSeeker jobSeeker)
    {
        System.out.println(job.getId()); //TODO: Delete
        System.out.println("Title: " + job.getJobTitle());
        System.out.println("Company: " + job.getCompany());
        System.out.println("Date posted: " + job.getDateListed());
    }

    /**
     * Gets the user's selection.
     *
     * @param strapLine A horizontal divider to be inserted.
     * @param options   The options to be selected from.
     * @return Returns the user's selection, as a String.
     */
    public static String getSelection(String strapLine, ArrayList<String[]> options)
    {
        int tries = 0;

        options.forEach(element -> displayBody(element[0]));
        System.out.println(strapLine);
        String selection = getInput();
        selection = selection.strip().toLowerCase();
        while (!isValidSelection(selection, options))
        {
            if (tries <= 3)
            {
                displayBody("That was not a valid selection, please enter one of the below options.");
                //TODO: here
                System.out.println("Please try again: ");
                tries++;
                selection = getInput();
            } else
            {
                System.out.println("You have entered to many incorrect attempts.\nShutting program down...");
                System.exit(1);
            }
        }
        return selection;
    }

    /**
     * Checks whether a selection is valid.
     *
     * @param selection The selection.
     * @param options   The options.
     * @return The validity of the selection.
     */
    private static boolean isValidSelection(String selection, ArrayList<String[]> options)
    {
        for (String[] word : options)
        {
            String[] values = word[1].split(",");
            for (String value : values)
            {
                if (selection.equals(value))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param attribute
     * @param value
     */
    public static void printBlock(String attribute, String value)
    {
        System.out.println((attribute) + valueBlock(value));
    }

    /**
     * @param data
     */
    public static void printBlock(LinkedHashMap<String, String> data)
    {
        data.forEach((k, v) -> System.out.println(attributeBlock(k) + valueBlock(v)));
    }

    /**
     * @param text
     * @return
     */
    private static String valueBlock(String text)
    {
        if (text == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int charCount = 0;
        for (String word : text.split(" "))
        {
            if (word.length() + charCount > VALUE_WIDTH)
            {
                sb.append("\n");
                sb.append(" ".repeat(ATTRIBUTE_WIDTH));
                charCount = 0;
            }
            sb.append(word).append(" ");
            charCount += word.length() + 1;
        }
        return sb.toString();
    }

    /**
     * @param text
     * @return
     */
    private static String attributeBlock(String text)
    {
        if (text == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder(text);
        int attLen = sb.length();

        if (attLen + 1 >= ATTRIBUTE_WIDTH)
            sb.delete(ATTRIBUTE_WIDTH - 6, attLen).append("... : ");
        else
            sb.append(" ".repeat(ATTRIBUTE_WIDTH - attLen - 2)).append(": ");
        return sb.toString();
    }

    public static void printInvitationHeading()
    {
        printBlock("No.", " | ", 10);
        printBlock("Job Title", " | ", 25);
        printBlock("Recruiter Name", " | ", 25);
        printBlock("Date Sent", " | ", 25);
        printBlock("Date of Interview", " | ", 25);
        System.out.println("\n" + "-".repeat(CHAR_WIDTH));
    }

    public static void printInvitationSummary(Invitation invitation, int number)
    {
        printBlock(String.valueOf(number), " - ", 10);
        printBlock(invitation.getJob().getJobTitle(), " - ", 25);
        printBlock(invitation.getRecruiter().getFirstName() + " " + invitation.getRecruiter().getLastName(), " - ", 25);
        printBlock(invitation.getDateSent().toString(), " - ", 25);
        printBlock(invitation.getDateOfInterview().toString(), " - ", 25);
        System.out.println("\n");
    }
}

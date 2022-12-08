package Utilities;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the class that validates text based input that is entered by the user.
 *
 * @author Team R
 * @version 1.0
 */
public class Validate
{
    private final int MAX_ATTEMPTS = 3;
    private int incorrectAttempts = 0;

    /**
     * This method checks that the number of attempts by the user to enter a requested input, does
     * not breach the maximum set limit.
     */
    private void checkAttempts()
    {
        //if (incorrectAttempts >= MAX_ATTEMPTS)
        //UserHandler.exit();
    }

    /**
     * This method checks that a numeric value entered by a user falls within a range within a certain number
     * of attempts.
     *
     * @param value     The integer value entered by the user.
     * @param min       The minimum length of the integer input expressed as an integer.
     * @param max       The maximum length of the integer input expressed as an integer.
     * @param inclusive The inclusivity of the value entered by the user expressed as a boolean value.
     * @return The inclusivity of the value entered by the user within a certain number of attempts, expressed as a boolean value.
     */
    public boolean isInRange(int value, int min, int max, boolean inclusive)
    {
        checkAttempts();
        if ((inclusive && isInRangeInclusive(value, min, max)) || (!inclusive && isInRangeExclusive(value, min, max)))
        {
            incorrectAttempts = 0;
            return true;
        } else
        {
            incorrectAttempts++;
            return false;
        }
    }

    /**
     * This method will validate the exclusivity of the length of any integer input from the user.
     *
     * @param value Input from the user as a string.
     * @param min   The minimum length of the integer input expressed as an integer.
     * @param max   The maximum length of the integer input expressed as an integer.
     * @return Whether or not the inputted value falls within the exclusive range, as a boolean.
     */
    private boolean isInRangeExclusive(int value, int min, int max)
    {
        return value > min && value < max;
    }

    /**
     * This method will validate the inclusivity of the length of any integer input from the user.
     *
     * @param value Input from the user as a string.
     * @param min   The minimum length of the integer input expressed as an integer.
     * @param max   The maximum length of the integer input expressed as an integer.
     * @return Whether or not the inputted value falls within the inclusive range, as a boolean.
     */
    private boolean isInRangeInclusive(int value, int min, int max)
    {
        return value >= min && value <= max;
    }

    /**
     * This method will validate that an email address entered by a user meets certain requirement, that
     * are measured against a Regex expression.
     *
     * @param input The email entered by the user as a string.
     * @return Whether the inputted email matches the regex criteria, as a boolean.
     */
    public boolean isValidEmail(String input)
    {
        checkAttempts();
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(input);
        return matcher.find();
        //return false;
    }

    /**
     * The method will validate the length of any integer input from the user.
     *
     * @param integer Input from the user as an integer.
     * @param min     The minimum length of the integer input expressed as an integer.
     * @param max     The maximum length of the integer input expressed as an integer.
     * @return The validity of the integer input, as a boolean value.
     */
    public boolean isValidIntegerLength(int integer, int min, int max)
    {
        boolean valid = false;
        //int length = integer.length();
        if (integer >= min && integer <= max)
        {
            valid = true;
        }
        return valid;
    }

    /**
     * The method will validate the length of any string input from the user.
     *
     * @param string Input from the user as a string.
     * @param min    The minimum length of the string input expressed as an integer.
     * @param max    The maximum length of the string input expressed as an integer.
     * @return The validity of the string input, as a boolean value.
     */
    public boolean isValidLength(String string, int min, int max)
    {
        boolean valid = false;
        int length = string.length();
        if (length >= min && length <= max)
        {
            valid = true;
        }
        return valid;
    }

    /**
     * This method will validate the menu option that is selected by the user.
     *
     * @param array An array of strings representing menu options.
     * @param input The selected menu option as an integer.
     * @return The validity of the menu option as a boolean value.
     */
    public boolean isValidMenuOption(String[] array, int input)
    {
        boolean valid = false;
        int arrayLength = array.length;
        if (input <= arrayLength && input > 0)
        {
            valid = true;
        }
        return valid;
    }

    /**
     * This method checks the validity of a selction inputed by a user with respect to a menu option.
     *
     * @param input   From the user as a string.
     * @param options Menu options expressed as an array of strings.
     * @return If the input entered by the user coresponds to a menu option, returns a boolean value of true.
     */
    public boolean isValidOption(String input, String[] options)
    {
        checkAttempts();
        return Arrays.asList(options).contains(input);
    }

    /**
     * This method will validate a password entered into JobSearchie.
     *
     * @param password Accepts the password entered by the user as a string.
     * @return Whether the inputted password matches the regex criteria, as a boolean value.
     */
    public boolean isValidPassword(String password)
    {
        checkAttempts();
        Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$&+,:;=?@#|'<>.^*()%!~`{}/-])[A-Za-z\\d$&+,:;=?@#|'<>.^*()%!~`{}/-]{8,}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.find();
    }

    /**
     * This method will validated a postcode that is enter by the user.
     *
     * @param postCode Accepts the postcode entered by the user as a string.
     * @return The validity of the inputted postcode as a boolean value
     */
    public boolean isValidPostCode(String postCode)
    {
        return false;
    }

    /**
     * This method resets any incorrect attempts that are entered by the user.
     */
    public void resetAttempts()
    {
        incorrectAttempts = 0;
    }
}

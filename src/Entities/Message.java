package Entities;

/**
 * An entity class which stores information of the Message class.
 *
 * @author Team R
 * @version 1.0
 */
public class Message
{
    private String messageContent;

    /**
     * Default constructor which creates the object of the Message class.
     */
    public Message()
    {
        this.messageContent = "";
    }

    /**
     * Non-default constructor which creates the object of the Message class.
     *
     * @param messageContent The content of the message as a String.
     */
    public Message(String messageContent)
    {
        this.messageContent = messageContent;
    }
}

package it.polimi.ingsw.controller;

import java.io.Serial;
import java.io.Serializable;

/**
 * class which represent the messages that will be exchanged within the network
 * @author Ettori
 */
public class Message implements Serializable {
    private final MessageType type;
    private final String author;
    private final Object content;
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * normal constructor for the Messages that will be sent in the network
     * @param t the type of this message
     * @param p the sender of the message (name)
     * @param cont the content of the message, any type of class (Object)
     */
    public Message(MessageType t, String p, Object cont){ // build the message in a complete way, only one time
        type = t;
        author = p;
        content = cont;
    }
    /**
     * getter for the type of the message
     * @author Ettori
     * @return the type of message sent, which identify his purpose
     */
    public MessageType getType(){return type;}
    /**
     * getter for the author of the message
     * @author Ettori
     * @return the author of message sent, client or server
     */
    public String getAuthor(){return author;}
    /**
     * getter for the content of the message
     * @author Ettori
     * @return the content of message sent, which will be converted to the appropriate class
     */
    public Object getContent(){return content;}
}
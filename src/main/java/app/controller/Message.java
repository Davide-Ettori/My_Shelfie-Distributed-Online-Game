package app.controller;

/**
 * class which represent the messages that will be exchanged within the network
 * @author Ettori
 */
public class Message {
    private final String type;
    private final String author;
    private final Object content;
    public Message(String t, Object cont, String p){ // li costruisci in maniera completa una e una sola volta
        type = t;
        author = p;
        content = cont;
    }

    /**
     * getter for the type of the message
     * @author Ettori
     * @return the type of message sent, which identify his purpose
     */
    public String getType(){return type;}
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
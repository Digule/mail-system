/**
 * A class to model a simple email client. The client is run by a
 * particular user, and sends and retrieves mail via a particular server.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class MailClient
{
    // The server used for sending and receiving.
    private MailServer server;
    // The user running this client.
    private String user;

    private MailItem lastReceived;
    /**
     * Create a mail client run by user and attached to the given server.
     */
    public MailClient(MailServer server, String user)
    {
        this.server = server;
        this.user = user;
        lastReceived = null;
    }

    /**
     * Return the next mail item (if any) for this user.
     */
    public MailItem getNextMailItem()
    {
        MailItem item = server.getNextMailItem(user) ;
        if(item != null){
            lastReceived = item;
        }
        return item;
    }

    /**
     * Print the next mail item (if any) for this user to the text 
     * terminal.
     */
    public void printNextMailItem()
    {
        MailItem item = server.getNextMailItem(user);
        if(item == null) {
            System.out.println("No new mail.");
        }
        else {
            item.print();
        }
    }

    /**
     * Send the given message to the given recipient via
     * the attached mail server.
     * @param to The intended recipient.
     * @param message The text of the message to be sent.
     */
    public void sendMailItem(String to, String subject ,String message)
    {
        MailItem item = new MailItem(user, subject, to, message);
        server.post(item);
    }

    public int getNumberOfMessageInServer(){
        int count = server.howManyMailItems(user);
        return count;
    }

    public MailItem getLastReceivedMail(){
        return lastReceived;
    }

    public void receiveAndAutorespond(){
        MailItem nextMenssage = getNextMailItem();
        if(nextMenssage != null){
            String subject = "RE: " + nextMenssage.getSubject();
            String menssage = "Gracias por su mensaje. Le contestare lo antes posible. " + nextMenssage.getMessage();
            sendMailItem(nextMenssage.getFrom(), subject, menssage);
        }
    }

    public String getStatus(){
        return null;
    }
}

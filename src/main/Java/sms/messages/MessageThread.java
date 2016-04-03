package sms.messages;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by Grandpa on 3/14/2016.
 */
//This statement means that class "Thread.java" is the root-element of our example
//@XmlRootElement(name = "file", namespace = "xml.jaxb")
@XmlRootElement(name = "file")
public class MessageThread {

    // XmLElementWrapper generates a wrapper element around XML representation
    @XmlElementWrapper(name = "thread")
    // XmlElement sets the name of the entities
    @XmlElement(name = "message")
    private ArrayList<Message> list;


    public ArrayList<Message> getMessageList() {
        return this.list;
    }
    public void setMessageList(ArrayList<Message> messageList) {
        this.list = messageList;
    }

}

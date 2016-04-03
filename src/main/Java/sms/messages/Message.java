package sms.messages;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "message")
// Optional - If you want you can define the order in
//   which the fields are written
@XmlType(propOrder = { "address", "body", "date", "read", "type", "locked" })


/**
 * Created by Grandpa on 3/14/2016.
 *
 * Class to hold one SMS text message part.
 */
public class Message {

    private String address;
    private String body;
    private String date;
    private String read;
    private String type;
    private String locked;


    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getRead() {
        return read;
    }
    public void setRead(String read) {
        this.read = read;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getLocked() {
        return locked;
    }
    public void setLocked(String locked) {
        this.locked = locked;
    }

}

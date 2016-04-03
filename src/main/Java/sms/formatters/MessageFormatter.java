package sms.formatters;

import dates.DateFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sms.messages.Message;
import sms.messages.MessageThread;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by Grandpa on 3/14/2016.
 */
public class MessageFormatter {

    private Logger logger = LoggerFactory.getLogger("MessageFormatter");

    /**
     * Main method for testing
     *
     * @param args
     * @throws JAXBException
     * @throws IOException
     */
    public static void main(String[] args) throws JAXBException, IOException {

        final String messageInFile = "C:\\SMS messages\\sms-20160311083540.xml";
        //private static final String messageInFile = "C:\\SMS messages\\sms-20160225140856.xml";
        //private static final String messageInFile = "C:\\SMS messages\\sms-20160305084747.xml";

        final String messageOutFile = "C:\\SMS messages\\JoAnn-Pat.txt";
        //private static final String messageOutFile = "C:\\SMS messages\\700R4-Text.txt";

        MessageFormatter messageMain = new MessageFormatter();
        System.out.println(messageMain.formatSMS(messageInFile, "JoAnn", "Pat"));
    }


    /**
     * Load the xml file, decode the data and print
     * out the important stuff.
     *
     * @throws JAXBException
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public String formatSMS(String inFile, String yourName, String theirName)
            throws JAXBException, FileNotFoundException, UnsupportedEncodingException {

        MessageThread messageThread = new MessageThread();

        // create JAXB context and instantiate marshaller
        JAXBContext context = JAXBContext.newInstance(MessageThread.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // get variables from our xml file.
        System.out.println();
        Unmarshaller um = context.createUnmarshaller();
        messageThread = (MessageThread) um.unmarshal(new FileReader(inFile));
        ArrayList<Message> list = messageThread.getMessageList();

        StringBuilder sb =new StringBuilder();
        for (int i = list.size() -1; i > -1; i--) {
            sb.append(formatDate(list.get(i).getDate()) + "\n");
            sb.append(formatType(list.get(i).getType(), yourName, theirName) + "\n");
            sb.append(decodeText(list.get(i).getBody()) + "\n");
            sb.append("\n");
        }

        //Remove last newline
        sb.setLength(sb.length() -1);
        //Print the formatted file
        logger.info(sb.toString());
        //Return buffer string
        return sb.toString();
    }


    /**
     * Convert the date in milliseconds to actual date.
     *
     * @param milliDate
     * @return human readable date
     */
    public String formatDate(String milliDate) {

        DateFormatter df = new DateFormatter();

        long millis = Long.valueOf(milliDate);

        return df.doInvoke(millis);
    }


    /**
     * Convert URL encoded text to UTF-8 text.
     *
     * @param text
     * @return
     * @throws UnsupportedEncodingException
     */
    public String decodeText(String text) throws UnsupportedEncodingException {

        return URLDecoder.decode(text, "UTF-8");
    }



    public String formatType(String type, String yourName, String theirName) {

        String theirType = "1";
        String yourType = "2";

        String name = "";

        if (type.equals(yourType)) {
            name = yourName;
        }
        if (type.equals(theirType)){
            name = theirName;
        }

        return name;
    }

}

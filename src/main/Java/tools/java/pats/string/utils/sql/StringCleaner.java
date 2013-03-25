package tools.java.pats.string.utils.sql;

import net.jcip.annotations.ThreadSafe;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.IOException;
import java.io.Serializable;

import static java.lang.String.format;

/**
 * This class takes in a String, replaces all white space (\n, \r, etc.) with spaces, 
 * trims excess spaces, removes comments and returns a one line string.
 * <p>
 * Each line of input is concatenated with a single space between the current and 
 * previous lines for word separation. 
 * 
 * @author Pat Keeler
 */
@ThreadSafe
public class StringCleaner implements Serializable {

    private static final long serialVersionUID = 1951L;

	
	/**
	 * Default constructor
	 */
	public StringCleaner() {
		super();
	}

	/**
	 * The driver method. 
	 * <p/>
	 * Appends the input into 1 line.
	 * 
	 * @throws TransformerException
	 * @throws TransformerFactoryConfigurationError
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public String cleanString(String input, StringBuffer comments) {

        /* Return error message. */
        String outPutMessage = "No input data received";

		//Clean the input
		if (null != input) {
			try {
				outPutMessage = removeSpaces(input, comments);
			} 
			catch (ParserConfigurationException e) {
				System.out.println(format("ParserConfigurationException: %s", e.getMessage()));
				e.printStackTrace();
			} 
			catch (SAXException e) {
				System.out.println(format("SAXException: %s", e.getMessage()));
				e.printStackTrace();
			} 
			catch (IOException e) {
				System.out.println(format("IOException: %s", e.getMessage()));
				e.printStackTrace();
			} 
			catch (TransformerFactoryConfigurationError e) {
				System.out.println(format("TransformerFactoryConfigurationError: %s", e.getMessage()));
				e.printStackTrace();
			} 
			catch (TransformerException e) {
				System.out.println(format("TransformerException: %s", e.getMessage()));
				e.printStackTrace();
			}
		}

		return outPutMessage;
	}

	/**
	 * Clean up the text.
	 * 
	 * @param input
     * @param  comments
	 * @return
	 * @throws TransformerException
	 * @throws TransformerFactoryConfigurationError
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private String removeSpaces(String input, StringBuffer comments)
			throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		
		//Remove all comments first.
		RemoveSqlComments remove = new RemoveSqlComments();
		String line = remove.removeComments(input, comments);
		
		return line.replaceAll("(\\s)+", " ");
	}
}

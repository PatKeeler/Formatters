package tools.java.pats.string.utils.xml;

import net.jcip.annotations.ThreadSafe;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class takes in an XML file and returns 1 xml string with all spaces and
 * special characters removed.
 * 
 * It prints out the new one line in the console as well as writes it out to the
 * default output file. You can copy the line from either location.
 * 
 * The program will read most file extensions and output the same, i.e.( .txt
 * .xml .html etc.) depending on how you re-define the default file parms.
 * 
 * @author Pat Keeler - U0027507
 * 
 */
@ThreadSafe
public class XmlCleaner implements Serializable {

    private static final long serialVersionUID = 1951L;


	/**
	 * Default constructor
	 */
	public XmlCleaner() {
		super();
	}

	/**
	 * The driver method. Appends the input into 1 line, removes spaces and
	 * special characters and then writes to the console and to the output file.
	 * 
	 * @param input xml string
     *
	 * @throws TransformerException
	 * @throws TransformerFactoryConfigurationError
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public String doInvoke(String input) throws ParserConfigurationException,
			SAXException, IOException, TransformerFactoryConfigurationError,
			TransformerException {

		if (null == input) {
			throw new InvalidParameterException("Input XML cannot be blank or null!");
		}
		
		//Remove prolog.
		RemovePrologFromXml removeProlog = new RemovePrologFromXml();
		String newString = removeProlog.removeProlog(input);

		//Clean the input
		return removeSpaces(newString);
	}

	/**
	 * Clean up the text.
	 * 
	 * @param message xml string
	 * @return clean xml
	 * @throws TransformerException
	 * @throws TransformerFactoryConfigurationError
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private String removeSpaces(String message)
			throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {

		String transformedLines = message.toString().replaceAll("\n", "");

		Pattern pattern = Pattern.compile(
				">\\s*([\\w-._/,:()';$\"\\s]+?)\\s*</",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(transformedLines);

		// while (matcher.find()) {
		// System.out.println("Found a match: *" + matcher.group(1) +
		// "* Length= " + matcher.group(1).length());
		// }

		String transformedRequestRemovedSpacesInsideTags = matcher
				.replaceAll(">$1</");

		pattern = Pattern.compile(">\\s+<", Pattern.CASE_INSENSITIVE
				| Pattern.MULTILINE);
		matcher = pattern.matcher(transformedRequestRemovedSpacesInsideTags);

		String transformedRequestRemovedSpacesBetweenTags = matcher
				.replaceAll("><");

		return transformedRequestRemovedSpacesBetweenTags;
	}
}

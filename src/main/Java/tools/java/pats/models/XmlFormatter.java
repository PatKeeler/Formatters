package tools.java.pats.models;

import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import tools.java.pats.string.utils.xml.XmlCleaner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * This class converts an xml string to a cleanly formatted xml file that can
 * be used as input to SoapUI.
 *
 * @author Pat Keeler - keelerpl@gmail.com
 * 
 */
@ThreadSafe
public class XmlFormatter implements Serializable {

    private static final long serialVersionUID = 1951L;

    private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * Clean and Format an XML string.
	 * 
	 * @param xml = un-formatted xml
	 * @return formatted xml string
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	public String doInvoke(String xml, String userIndentAmount) throws ParserConfigurationException,
			SAXException, IOException, TransformerFactoryConfigurationError,
			TransformerException {

		if (xml.isEmpty()) {
			System.out.println("Program terminated, input XML string is null.");
			return "";
		}

		if (userIndentAmount.isEmpty()) {
			System.out.println("Program terminated, userIndentAmount string is null.");
			return "";
		}
		
		//Clean the xml.
		XmlCleaner cleaner = new XmlCleaner();
		String cleanedXml = cleaner.doInvoke(xml);

        // Log the one line xml string.
        logger.info("Xml = " + cleanedXml);

		//Format the xml.
		StreamResult result = getFormattedXml(cleanedXml, userIndentAmount);

		return result.getWriter().toString();
	}

	/**
	 * Format a cleaned single string of xml.  
	 * <p>
	 * Clean and return a pretty printed xml string.
	 * <p>
	 * The returned xml is cleaned and ready for use with SoapUI.
	 * 
	 * @param xml un-formatted xml
	 * @return formatted xml
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	private StreamResult getFormattedXml(String xml, String userIndentAmount)
			throws ParserConfigurationException, SAXException, IOException,
            TransformerFactoryConfigurationError, TransformerException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new StringReader(xml)));
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", userIndentAmount);

		// initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
		return result;
	}
}
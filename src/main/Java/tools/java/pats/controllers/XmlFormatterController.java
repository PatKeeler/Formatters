package tools.java.pats.controllers;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import net.jcip.annotations.ThreadSafe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;

import tools.java.pats.models.XmlFormatter;


/**
 * Servlet implementation class XmlFormatterController
 */
@ThreadSafe
@Controller
public class XmlFormatterController extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1951L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public XmlFormatterController() {
		super();
	}

	/**
	 * GET requests handled by doPOST.
	 * 
	 * @param request
	 *            The current request object.
	 * @param response
	 *            The generated response object.
	 * @throws ServletException
	 *             If an exception occurs which cannot be handled.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	@RequestMapping(method=RequestMethod.GET)
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * POST requests.  Cleans and formats the input XML.
	 * 
	 * @param request
	 *            The current request object.
	 * @param response
	 *            The generated response object.
	 * @throws ServletException
	 *             If an exception occurs which cannot be handled.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	@RequestMapping(value = "/ReformatXml", method=RequestMethod.POST)
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//Input xml string.
		String inputXML = request.getParameter("inputXML");
		
		//Indent value requested by user.
		String userIndentAmount = request.getParameter("indentAmount");		
		if (userIndentAmount == null) {
			userIndentAmount = "0";
		}
		
		//Default message if input is empty.
		String results = "Input Xml string is blank!";
		
		XmlFormatter formatter = new XmlFormatter();

		if (inputXML.isEmpty()) {
			response.getWriter().write(results);
		}
		else {
			try {
				
				results = formatter.doInvoke(inputXML, userIndentAmount);
				
			} 
			//catch errors and set message on results
			catch (ParserConfigurationException e) {				
				results = "ParserConfigurationException: " + e.getLocalizedMessage();
			} 
			catch (SAXException e) {				
				results = "SAXException: " + e.getLocalizedMessage();
			} 
			catch (TransformerFactoryConfigurationError e) {				
				results = "TransformerFactoryConfigurationError: " + e.getLocalizedMessage();
			} 
			catch (TransformerException e) {				
				results = "TransformerException: " + e.getLocalizedMessage();
			}
			
			finally {
				//print out the error message
				response.getWriter().write(results);
			}
		}
	}
}

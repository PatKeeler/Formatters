package tools.java.pats.controllers;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tools.java.pats.models.SqlFormatter;
import tools.java.pats.string.utils.AddQuotesForJavaString;
import tools.java.pats.string.utils.RemoveQuotesFromJavaString;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;


/**
 * Servlet implementation class SqlReformatter.
 */
@ThreadSafe
@Controller
public class SqlFormatterController extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1951L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SqlFormatterController() {
		super();
	}

	/**
	 * GET request, executes the POST request.
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
	 * POST request. Format and return the input SQL string.
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
	@RequestMapping(value = "/ReformatSql", method = RequestMethod.POST)
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//Initial indent value for recursion of SqlFormatter.
		String tab = "";
		
		//Indent value requested by user.
		String userIndentAmount = request.getParameter("indentAmount");		
		if (userIndentAmount == null) {
			userIndentAmount = "0";
		}
		
		//Default results value if error.
		String results = "Input Sql string is blank!";

		//Get Input Sql.
		String inputSQL = request.getParameter("inputSQL");

		//If inputSql is blank return error message.
		if (null == inputSQL ||
			inputSQL.isEmpty()) {			
			response.getWriter().write(results);
		} 

        //Get the selected style, block or verbose
        String selectedStyle = request.getParameter("selectedStyle");

		//See if "Reformat and Quotes" request?
		String addQuotesAndReformat = request.getParameter("addQuotesAndReformat");
		
		//See if "Quotes Only" request?
		String addQuotesOnly = request.getParameter("addQuotesOnly");
		
		//See if "remove quotes and reformat"
		String removeQuotesAndReformat = request.getParameter("removeQuotesAndReformat");
		
		//See if "remove quotes only"
		String removeQuotesOnly = request.getParameter("removeQuotesOnly");

		SqlFormatter formatter = new SqlFormatter();

		//if add quotes only.
		if (null != addQuotesOnly && addQuotesOnly.equals("true")) {
			try {
				AddQuotesForJavaString getQuotes = new AddQuotesForJavaString();
				results = getQuotes.addQuotes(inputSQL);
			}
			catch(Exception e) {
				results = "Exception during Add Quotes Only processing: " +
				        e.getLocalizedMessage();
                e.printStackTrace();
			}
            finally {
				//Return results: Formatted Sql or an error message.
    			response.getWriter().write(results);
            }
		}
		//if remove quotes only.
		else if (null != removeQuotesOnly && removeQuotesOnly.equals("true")) {
			try {
                RemoveQuotesFromJavaString removeQuotes = new RemoveQuotesFromJavaString();
				results = removeQuotes.removeQuotes(inputSQL);
			}
			catch(Exception e) {
				results = "Exception during Remove Quotes Only processing: " +
				        e.getLocalizedMessage();
                e.printStackTrace();
			}
            finally {
				//Return results: Quoted input or an error message.
    			response.getWriter().write(results);
            }
		}
		//if remove quotes and format.
		else if (null != removeQuotesAndReformat && removeQuotesAndReformat.equals("true")) {
			try {
                RemoveQuotesFromJavaString removeQuotes = new RemoveQuotesFromJavaString();
				results = removeQuotes.removeQuotes(inputSQL);
                results = formatter.formatSql(results, tab, userIndentAmount, selectedStyle);
			}
			catch(Exception e) {
				results = "Exception during Remove Quotes And Format processing: " +
				        e.getLocalizedMessage();
                e.printStackTrace();
			}
            finally {
				//Return results: Formatted Sql or an error message.
    			response.getWriter().write(results);
            }
		}
		else
        {
			try {				
				//Format the input.
				results = formatter.formatSql(inputSQL, tab, userIndentAmount, selectedStyle);

				//Add quotes to formatted results if requested.
				if (null != addQuotesAndReformat && addQuotesAndReformat.equals("true")) {
					
					AddQuotesForJavaString getQuotes = new AddQuotesForJavaString();
					results = getQuotes.addQuotes(results);
				}
			} 
			catch (Exception e) {
				results = "Exception during format processing: " +
                        e.getLocalizedMessage();
                e.printStackTrace();
			}
				
            finally {
				//Return results: Formatted Sql or an error message.
    			response.getWriter().write(results);
            }
		}
	}
}

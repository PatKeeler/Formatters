package sms.controller;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sms.formatters.MessageFormatter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


/**
 * Servlet implementation class SqlReformatter.
 */
@ThreadSafe
@Controller
public class SmsController extends HttpServlet {

    private static final long serialVersionUID = 1951L;

    private Logger logger = LoggerFactory.getLogger("SmsController");

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SmsController() {
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
     * POST request. Format and return the input SMS Text string.
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
    @RequestMapping(value = "/ReformatSms", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        //Default results value if error.
        String results = "";
        String phoneOwner = null;
        String texter = null;

        try {
            // Configure a repository (to ensure a secure temp location is used)
            ServletContext servletContext = this.getServletConfig().getServletContext();
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

            // Create a factory for disk-based file items
            FileItemFactory factory = newDiskFileItemFactory(servletContext,repository);

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            // Parse the request and process input data
            List<FileItem> items = upload.parseRequest(request);

            for (FileItem item : items) {

                // Is it a field or a file input
                if (item.isFormField()) {
                    //Its a field
                    if (item.getFieldName().equals("yourName")) {
                        phoneOwner = item.getString();
                    }
                    else if (item.getFieldName().equals("theirName")) {
                        texter = item.getString();
                    }
                    results += "Field " + item.getFieldName() + " with value: " +
                            item.getString() + " is successfully read\n";
                } else {
                    //Its a file
                    Path p = Paths.get(item.getName());
                    String fileName = p.getFileName().toString();
//                    String fileName = item.getName();

                    String root = getServletContext().getRealPath("/");
                    File path = new File(root + "/SMSuploads");
                    if (!path.exists()) {
                        boolean status = path.mkdirs();
                    }
                    //Print blank line for run seperation
                    logger.info("");
                    logger.info("fileName: " + fileName);

                    //New file info
                    File uploadedFile = new File(path + "/" + fileName);
                    item.write(uploadedFile);

                    //Print file info
                    logger.info("uploadedFile.getAbsolutePath(): " + uploadedFile.getAbsolutePath());
                    logger.info("uploadedFile.getAbsoluteFile(): " + uploadedFile.getAbsoluteFile());

                    //Set response
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");

                    //Call MessageFormatter
                    MessageFormatter formatter = new MessageFormatter();
                    try {
                        results = formatter.formatSMS(uploadedFile.getAbsolutePath(), phoneOwner, texter);
                    }
                    catch (JAXBException je) {
                        results += "JAXBException: " + je;
                        logger.info("JAXBException: " + je);
                        throw new ServletException("Parsing file upload failed.", je);
                    }
                    catch (Exception e) {
                        results += "Exception: " + e;
                        e.printStackTrace();
                        logger.info("Exception: " + e);
                    }
                    finally {
                        try{

                            String tempFile = uploadedFile.getAbsolutePath();
                            //Delete if tempFile exists
                            File fileTemp = new File(tempFile);
                            if (fileTemp.exists()){
                                fileTemp.delete();
                            }
                        }catch(Exception e){
                            results += "Exception: " + e;
                            logger.info("Exception: " + e);
                            throw new ServletException("Exception: Failed to delete temp file.", e);
                        }
                    }
                }
            }
        } catch (FileUploadException fue) {
            results += "FileUploadException: " + fue;
            logger.info("FileUploadException: " + fue);
            throw new ServletException("Parsing file upload failed.", fue);
        } catch (NullPointerException npe) {
            results += "NullPointerException: " + npe;
            logger.info("NullPointerException: " + npe);
            throw new ServletException("Parsing file upload failed.", npe);
        } catch (Exception e) {
            results += "Exception: " + e;
            logger.info("Exception: " + e);
            throw new ServletException("Parsing file upload failed.", e);
        }

        finally {
            //Return results: Formatted Sql or an error message.
            response.getWriter().write(results);
        }
    }


    public static DiskFileItemFactory newDiskFileItemFactory(ServletContext context,
                                                             File repository) {
        FileCleaningTracker fileCleaningTracker
                = FileCleanerCleanup.getFileCleaningTracker(context);
        DiskFileItemFactory factory
                = new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,
                repository);
        factory.setFileCleaningTracker(fileCleaningTracker);
        return factory;
    }
}

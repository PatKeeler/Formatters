package tools.java.pats.string.utils;

import net.jcip.annotations.ThreadSafe;

import java.io.*;
import java.security.InvalidParameterException;

import static java.lang.String.format;


/**
 * This class will read in a file and return the string value of
 * the file.
 */
@ThreadSafe
public class GetStringFromFile implements Serializable {

    private static final long serialVersionUID = 1951L;

	/** The input file location. */
	private final File inFile;

	/**
	 * Default constructor.
	 */
	public GetStringFromFile(final File inFile) {

		this.inFile = inFile;
	}

	
	/**
	 * Get the string from a file and return it.
     * 
	 * @return String
	 */
	public String getFileString() {

		if (null == inFile) {
			throw new InvalidParameterException(
					"Input string can not be null or blank!");
		}

		StringBuffer sb = new StringBuffer();

		try {
			BufferedReader input = new BufferedReader(new FileReader(inFile));
			try {
				String line; // not declared within while loop

				while ((line = input.readLine()) != null) {
					sb.append(format(" %s ", line));
				}
			} 
			catch (IOException e) {
				System.out.println("IOException reading file: " + e.getMessage());
				e.printStackTrace();
			} 
			finally {
				try {
					input.close();
				} 
				catch (IOException e) {
					System.out.println("IOException closing input file: " + e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: " + e.getMessage());
			e.printStackTrace();
		}

		return sb.toString();
	}

}

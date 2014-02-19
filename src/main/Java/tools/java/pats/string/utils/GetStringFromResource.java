package tools.java.pats.string.utils;

import net.jcip.annotations.ThreadSafe;

import java.io.*;


/**
* Gets a saved resource and returns as a string.
*
* @author Pat Keeler
*/
@ThreadSafe
public class GetStringFromResource implements Serializable {

    private static final long serialVersionUID = 1951L;
	

	/**
	 * Takes a resource and returns as a string.
	 * 
	 * @param resource file
	 * @return String string
	 * @throws IOException
	 */
	public String getString(String resource) throws IOException {

		InputStream in = GetStringFromResource.class.getResourceAsStream(resource);

		if (in == null) {
			throw new FileNotFoundException("The resource cannot be null or blank!");
		}

		Reader reader = new InputStreamReader(in);
		
		char[] buffer = new char[16 * 1024];
		
		StringBuilder builder = new StringBuilder();
		
		int read = reader.read(buffer);
		while (read != -1) {
			builder.append(buffer, 0, read);
			read = reader.read(buffer);
		}

		return builder.toString();

	}

}

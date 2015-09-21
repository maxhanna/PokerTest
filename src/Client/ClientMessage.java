package Client;
import java.net.*;
/* The java.io package contains the basics needed for IO operations. */
import java.io.*;
/** The SocketClient class is a simple example of a TCP/IP Socket Client.
 *
 */


public class ClientMessage {
	public ClientMessage()
	{
		
	}
	public String sendMessage(String args) {
		/** Define a host server */
		String host = "localhost";
		/** Define a port */
		int port = 19999;
		String returnMessage = "";
		StringBuffer instr = new StringBuffer();
		System.out.println("SocketClient initialized");

		try {
			/** Obtain an address object of the server */
			InetAddress address = InetAddress.getByName(host);
			/** Establish a socket connetion */
			Socket connection = new Socket(address, port);
			/** Instantiate a BufferedOutputStream object */
			BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());

			/** Instantiate an OutputStreamWriter object with the optional character
			 * encoding.
			 */
			OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
			String process = args +  (char) 13;

			/** Write across the socket connection and flush the buffer */
			osw.write(process);
			osw.flush();
			/** Instantiate a BufferedInputStream object for reading
		      /** Instantiate a BufferedInputStream object for reading
			 * incoming socket streams.
			 */

			BufferedInputStream bis = new BufferedInputStream(connection.
					getInputStream());
			/**Instantiate an InputStreamReader with the optional
			 * character encoding.
			 */

			InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");

			/**Read the socket's InputStream and append to a StringBuffer */
			int c;
			while ( (c = isr.read()) != 13)
				instr.append( (char) c);

			/** Close the socket connection. */
			connection.close();
			returnMessage = instr.toString();
			return(returnMessage);
		}
		catch (IOException f) {
			System.out.println("IOException: " + f);
		}
		catch (Exception g) {
			System.out.println("Exception: " + g);
		}

		return(returnMessage);
	}

}

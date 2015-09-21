package Network;
import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

	static ServerSocket socket1;
	protected final static int port = 19999;
	static Socket connection;

	static boolean first;
	static StringBuffer process;
	static String TimeStamp;

	public static void main(String[] args) {
		//construct a deck
		Deque<String[]> deck = new ArrayDeque<String[]>();
		for (int suit = 0; suit < 4; suit++) {
			for(int i = 0; i<13; i++)
			{
				if (suit == 0)
				{
					if (i == 11)
						deck.add(new String[] {"Jack","hearts"});
					else if (i == 12)
						deck.add(new String[] {"Queen","hearts"});
					else if (i == 13)
						deck.add(new String[] {"King","hearts"});
					else if (i == 0)
						deck.add(new String[] {"Ace","hearts"});
					else
						deck.add(new String[] {i+"","hearts"});
				}
				else if (suit == 1)
				{

					if (i == 11)
						deck.add(new String[] {"Jack","diamonds"});
					else if (i == 12)
						deck.add(new String[] {"Queen","diamonds"});
					else if (i == 13)
						deck.add(new String[] {"King","diamonds"});
					else if (i == 0)
						deck.add(new String[] {"Ace","diamonds"});
					else
						deck.add(new String[] {i+"","diamonds"});
				}
				else if (suit == 2)
				{
					if (i == 11)
						deck.add(new String[] {"Jack","spades"});
					else if (i == 12)
						deck.add(new String[] {"Queen","spades"});
					else if (i == 13)
						deck.add(new String[] {"King","spades"});
					else if (i == 0)
						deck.add(new String[] {"Ace","spades"});
					else
						deck.add(new String[] {i+"","spades"});
				}
				else
				{
					if (i == 11)
						deck.add(new String[] {"Jack","clubs"});
					else if (i == 12)
						deck.add(new String[] {"Queen","clubs"});
					else if (i == 13)
						deck.add(new String[] {"King","clubs"});
					else if (i == 0)
						deck.add(new String[] {"Ace","clubs"});
					else
						deck.add(new String[] {i+"","clubs"});
				}
			}
		}

		//shuffle entire deck
		ArrayList<String[]> cardList = new ArrayList<String[]>(deck);
		Collections.shuffle(cardList);
		deck = new ArrayDeque<String[]>(cardList);

		try{
			socket1 = new ServerSocket(port);
			System.out.println("SingleSocketServer Initialized");
			int character;
			boolean gameOn = false;
			while (true) {
				connection = socket1.accept();

				BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
				InputStreamReader isr = new InputStreamReader(is);
				process = new StringBuffer();
				while((character = isr.read()) != 13) {
					process.append((char)character);
				}
				System.out.println(process);
				
				if (process.toString().contains("join"))
				{
					System.out.println("person joins");
					if (gameOn == false)
						gameOn = true;
					String returnCode = "take card(s) :" + deck.peek()[0] + " of " + deck.pop()[1] +
										"," + deck.peek()[0] + " of " + deck.pop()[1] +
										"," + deck.peek()[0] + " of " + deck.pop()[1] + 
										"," + deck.peek()[0] + " of " + deck.pop()[1] + 
										"," + deck.peek()[0] + " of " + deck.pop()[1] + (char) 13;
					BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
					OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
					osw.write(returnCode);
					osw.flush();
				}
				else {
				//need to wait 10 seconds for the app to update database
					try {
						Thread.sleep(1);
					}
					catch (Exception e){}
					TimeStamp = new java.util.Date().toString();
					String returnCode = process.toString() + " : Server did not respond. "+ TimeStamp + (char) 13;
					BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
					OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
					osw.write(returnCode);
					osw.flush();
				}
			}
		}
		catch (IOException e) {}
		try {
			connection.close();
		}
		catch (IOException e) {}
	}
}
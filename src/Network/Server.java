package Network;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.HashMap;

import models.ChatMessage;

/*
 * The server that can be run both as a console application or a GUI
 */
public class Server {
	// a unique ID for each connection
	private static int uniqueId;
	// an ArrayList to keep the list of the Client
	private ArrayList<ClientThread> al;
	int finished = 0;
	String scores[] = new String[6];
	ArrayList<String> users = new ArrayList<String>();
	ArrayList<String> deck = new ArrayList<String>();
	int numUsers = 0;
	int inGame = 0;
	int plays = 0;
	ArrayList<String> usersPlayed = new ArrayList<String>();
	HashMap<String, String> userHands = new HashMap<String, String>();



	// to display time
	private SimpleDateFormat sdf;
	// the port number to listen for connection
	private int port;
	// the boolean that will be turned of to stop the server
	private boolean keepGoing;


	/*
	 *  server constructor that receive the port to listen to for connection as parameter
	 *  in console
	 */
	public Server(int port) {
		// GUI or not
		// the port
		this.port = port;
		// to display hh:mm:ss
		sdf = new SimpleDateFormat("HH:mm:ss");
		// ArrayList for the Client list
		al = new ArrayList<ClientThread>();
	}

	public void start() {
		keepGoing = true;
		/* create socket server and wait for connection requests */
		try 
		{
			// the socket used by the server
			ServerSocket serverSocket = new ServerSocket(port);

			// infinite loop to wait for connections
			while(keepGoing) 
			{
				// format message saying we are waiting
				display("Server waiting for Clients on port " + port + ".");

				Socket socket = serverSocket.accept();  	// accept connection
				// if I was asked to stop
				if(!keepGoing)
					break;
				ClientThread t = new ClientThread(socket);  // make a thread of it
				al.add(t);									// save it in the ArrayList
				t.start();
			}
			// I was asked to stop
			try {
				serverSocket.close();
				for(int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
						tc.sInput.close();
						tc.sOutput.close();
						tc.socket.close();
					}
					catch(IOException ioE) {
						// not much I can do
					}
				}
			}
			catch(Exception e) {
				display("Exception closing the server and clients: " + e);
			}
		}
		// something went bad
		catch (IOException e) {
			String mview = " Exception on new ServerSocket: " + e + "\n";
			display(mview);
		}
	}		
	/*
	 * For the GUI to stop the server
	 */
	protected void stop() {
		keepGoing = false;
		// connect to myself as Client to exit statement 
		// Socket socket = serverSocket.accept();
		try {
			new Socket("localhost", port);
		}
		catch(Exception e) {
			// nothing I can really do
		}
	}
	/*
	 * Display an event (not a message) to the console or the GUI
	 */
	private void display(String mview) {
		String time = sdf.format(new Date()) + " " + mview;

		System.out.println(time);


	}
	/*
	 *  to broadcast a message to all Clients
	 */
	synchronized void broadcast(ChatMessage message) {

		// display message on console or GUI


		// we loop in reverse order in case we would have to remove a Client
		// because it has disconnected
		for(int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			// try to write to the Client if it fails remove it from the list
			if(!ct.writeMview(message)) {
				al.remove(i);
				display("Disconnected Client " + ct.username + " removed from list.");
			}
		}
	}


	// for a client who logoff using the LOGOUT message
	synchronized void remove(int id) {
		// scan the array list until we found the Id
		for(int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			// found it
			if(ct.id == id) {
				al.remove(i);
				return;
			}
		}
	}

	/*
	 *  To run as a console application just open a console window and: 
	 * > java Server
	 * > java Server portNumber
	 * If the port number is not specified 28442 is used
	 */ 
	public static void main(String[] args) {
		// start server on port 28442 unless a PortNumber is specified 
		int portNumber = 28442;
		switch(args.length) {
		case 1:
			try {
				portNumber = Integer.parseInt(args[0]);
			}
			catch(Exception e) {
				System.out.println("Invalid port number.");
				System.out.println("Usage is: > java Server [portNumber]");
				return;
			}
		case 0:
			break;
		default:
			System.out.println("Usage is: > java Server [portNumber]");
			return;

		}
		// create a server object and start it
		Server server = new Server(portNumber);
		server.start();
	}

	/** One instance of this thread will run for each client */
	class ClientThread extends Thread {
		// the socket where to listen/talk
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		// my unique id (easier for deconnection)
		int id;
		// the Username of the Client
		String username;
		// the only type of message a will receive
		ChatMessage cm;
		// the date I connect
		String date;

		// Constructor
		ClientThread(Socket socket) {
			// a unique id
			id = ++uniqueId;
			this.socket = socket;
			/* Creating both Data Stream */
			System.out.println("Thread trying to create Object Input/Output Streams");
			try
			{
				// create output first
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput  = new ObjectInputStream(socket.getInputStream());
				// read the username
				username = (String) sInput.readObject();
				display(username + " just connected.\n");
				users.add(username);
				numUsers++;
			}
			catch (IOException e) {
				display("Exception creating new Input/output Streams: " + e);
				return;
			}
			// have to catch ClassNotFoundException
			// but I read a String, I am sure it will work
			catch (ClassNotFoundException e) {
			}
			date = new Date().toString() + "\n";
		}
		public void createDeck(ArrayList<String> deck){
			for (int suit = 0; suit < 4; suit ++){
				for (int num = 1; num < 13; num ++)
				{
					if (num == 1)
					{
						if (suit == 1)
							deck.add("Ace" + " of clubs");
						else if (suit == 2)
							deck.add("Ace" + " of spades");
						else if (suit == 3)
							deck.add("Ace" + " of diamonds");
						else if (suit == 4)
							deck.add("Ace" + " of hearts");
					}
					else if (num == 11)
					{
						if (suit == 1)
							deck.add("Jack" + " of clubs");
						else if (suit == 2)
							deck.add("Jack" + " of spades");
						else if (suit == 3)
							deck.add("Jack" + " of diamonds");
						else if (suit == 4)
							deck.add("Jack" + " of hearts");
					}
					else if (num == 12)
					{
						if (suit == 1)
							deck.add("Queen" + " of clubs");
						else if (suit == 2)
							deck.add("Queen" + " of spades");
						else if (suit == 3)
							deck.add("Queen" + " of diamonds");
						else if (suit == 4)
							deck.add("Queen" + " of hearts");
					}
					else if (num == 13)
					{
						if (suit == 1)
							deck.add("King" + " of clubs");
						else if (suit == 2)
							deck.add("King" + " of spades");
						else if (suit == 3)
							deck.add("King" + " of diamonds");
						else if (suit == 4)
							deck.add("King" + " of hearts");
					}
					else
					{
						if (suit == 1)
							deck.add( num + " of clubs");
						else if (suit == 2)
							deck.add( num + " of spades");
						else if (suit == 3)
							deck.add( num + " of diamonds");
						else if (suit == 4)
							deck.add( num + " of hearts");
					}

				}
			}
		}
		public void shuffleDeck(ArrayList<String> deck){
			Collections.shuffle(deck);
		}
		public boolean returnCards(String cards, ArrayList<String> deck){
			String delims2 = "[,]+";
			String[] returned = cards.split(delims2);
			int deckSize = deck.size();
			for(String s : returned)
			{
				deck.add(s);
			}
			if (deck.size() > deckSize)
				return true;
			else
				return false;
		}	
		public String takeCards(int num, ArrayList<String> deck){
			String cards = "";
			for (int i = 0; i < num; i++)
			{
				if (i==num-1)
					cards = cards + deck.get(0);	
				else
					cards = cards + deck.get(0) + ",";
				deck.remove(0);
			}
			return cards;
		}

		public String serveCards(ArrayList<String> deck){
			if (deck.size() > 4)
			{
				String cards = ("" + deck.get(deck.size()-5) + "," + deck.get(deck.size()-1) + "," 
						+ deck.get(deck.size()-2) + ","+ deck.get(deck.size()-3) + ","
						+ deck.get(deck.size()-4));
				deck.remove(deck.size()-1);
				deck.remove(deck.size()-1);
				deck.remove(deck.size()-1);
				deck.remove(deck.size()-1);
				deck.remove(deck.size()-1);
				return cards;
			}
			else
				return "";
		}
	

		// what will run forever
		public void run() {
			// to loop until LOGOUT
			boolean keepGoing = true;
			while(keepGoing) {
				// read a String (which is an object)
				try {
					cm = (ChatMessage) sInput.readObject();
				}
				catch (IOException e) {
					if (cm.getUserName() != null && !cm.getUserName().equals(""))
					{
						display(cm.getUserName() + " Exception reading Streams: " + e);
						broadcast(new ChatMessage(ChatMessage.LOGOUT, cm.getUserName() + ",logout", cm.getUserName()));

						for(String user : users)
						{
							if (user.contains(cm.getUserName()))
							{
								users.remove(user);
								numUsers--;
								break;
							}
						}

					}
					break;				
				}
				catch(ClassNotFoundException e2) {
					break;
				}
				catch(ClassCastException e2) {
					break;
				}
				// the message part of the ChatMessage


				// Switch on the type of message receive
				switch(cm.getType()) {
				case ChatMessage.LOGOUT:

					broadcast(new ChatMessage(ChatMessage.LOGOUT, cm.getUserName() + ",logout", cm.getUserName()));
					for(String user : users)
					{
						if (user.contains(cm.getUserName()))
						{
							users.remove(user);
							numUsers--;
							break;
						}
					}
					break;
				case ChatMessage.WHOISIN:
					for (int i = 0; i < users.size(); i++) {
						broadcast(new ChatMessage(ChatMessage.MESSAGE, users.get(i) + ", is online\n", users.get(i)));
					}
					break;
				case ChatMessage.MESSAGE:
					if (cm.getMessage().equals("serve"))
					{
						if (inGame == 0){
							inGame = 1;
							createDeck(deck);
							shuffleDeck(deck);
						}
						String cards = serveCards(deck);
						broadcast(new ChatMessage(ChatMessage.MESSAGE, cm.getUserName() +
								" takes " + cards, "Server"));

					}
					else if (cm.getMessage().contains("Current Hand"))
					{
						if (usersPlayed.contains(cm.getUserName())){
							System.out.println("Player has already played.");
						}
						else{
							usersPlayed.add(cm.getUserName());
							System.out.println(cm.getUserName() + "'s Final hand received.");
							broadcast(cm);	
							shuffleDeck(deck);
							plays++;
							if (numUsers == plays)
							{
								inGame = 0;
								plays=0;
								System.out.println("Calculating winner.");
								broadcast(new ChatMessage(ChatMessage.MESSAGE, "Calculate winner", "Server"));
								usersPlayed = new ArrayList<String>();
								
								
							}
						

						}

					}
					else if (cm.getMessage().contains("return"))
					{
						if (!usersPlayed.contains(cm.getUserName()))
						{
							String returned = cm.getMessage().replace("return ", "");
							System.out.println("user is returning: " + returned);
							returned = returned.replace(", ", ",");
							if(returnCards(returned, deck))
								System.out.println("Cards successfully returned to deck.");
							else
								System.out.println("Cards were not successfully returned to deck.");
							shuffleDeck(deck);
							int count = cm.getMessage().substring(cm.getMessage().indexOf("return "), cm.getMessage().length()).split(",", -1).length-1;

							String cards = takeCards(count+1,deck);

							System.out.println("Server delt : " + cards);
							broadcast(new ChatMessage(ChatMessage.MESSAGE, cm.getUserName() +
									" gets " + cards, cm.getUserName()));
						}
						else
						{
							broadcast(new ChatMessage(ChatMessage.MESSAGE, cm.getUserName() +
									" cannot play, waiting for other players to play. ", cm.getUserName()));
						}

						
					}
					display(cm.getMessage());
					break;

				case ChatMessage.VICTORY:
					display(username + ", won\n");
					//broadcast(scores[0]);
					break;
				case ChatMessage.CONNECT:
					broadcast(new ChatMessage(ChatMessage.WHOISIN, cm.getMessage() + ", connected", ""));
					if (cm.getMessage().getClass().equals(String.class))
					{
						if(!(numUsers<0)){
							users.add((String)cm.getMessage());
							numUsers++;
						}else{
							numUsers=0;
							users.add((String)cm.getMessage());
							numUsers++;
						}
					}
					break;

				}
				cm = null;
			}
			// remove myself from the arrayList containing the list of the
			// connected Clients
			remove(id);
			close();
		}

		// try to close everything
		private void close() {
			// try to close the connection
			try {
				if(sOutput != null) sOutput.close();
			}
			catch(Exception e) {}
			try {
				if(sInput != null) sInput.close();
			}
			catch(Exception e) {};
			try {
				if(socket != null) socket.close();
			}
			catch (Exception e) {}
		}

		/*
		 * Write a String to the Client output stream
		 */

		private boolean writeMview(ChatMessage mview) {
			// if Client is still connected send the message to it
			if(!socket.isConnected()) {
				close();
				return false;
			}
			// write the message to the stream
			try {
				sOutput.writeObject(mview);
			}
			// if an error occurs, do not abort just inform the user
			catch(IOException e) {
				//display("Error sending message to " + username);
				display(e.toString());
			}
			return true;
		}

	}
}

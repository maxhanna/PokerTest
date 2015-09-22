package gui;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import models.ChatMessage;
import models.PokerVariables;

public class Client  {

	// for I/O	
	String scores;
	// Variables needed to maintain game
	PokerVariables model;
	private ObjectInputStream sInput;		// to read from the socket
	private ObjectOutputStream sOutput;		// to write on the socket
	private Socket socket;


	// the server, the port and the username
	private String server;
	private int port;
	/*
	 *  Constructor called by console mode
	 *  server: the server address
	 *  port: the port number
	 *  username: the username
	 */

	public static String[] removeElements(String[] input, String deleteMe) {
		List<String> result = new LinkedList<String>();

		for(String item : input)
			if(!deleteMe.equals(item))
				result.add(item);

		return result.toArray(input);
	}
	/*
	 * Constructor call when used from a GUI
	 * in console mode the ClienGUI parameter is null
	 */
	public Client(String server, int port, String username, PokerVariables model) {
		this.server = server;
		this.port = port;
		this.model = model;
		this.model.userNames.set(0, username);
	}

	/*
	 * To start the dialog
	 */
	public boolean start() {
		// try to connect to the server
		try {
			socket = new Socket(server, port);
		} 
		// if it failed not much I can so
		catch(Exception ec) {
			display("Error connectiong to server:" + ec);
			return false;
		}

		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);

		/* Creating both Data Stream */
		try
		{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		// creates the Thread to listen from the server 
		new ListenFromServer().start();
		// Send our username to the server this is the only message that we
		// will send as a String. All other messages will be ChatMessage objects
		try
		{
			sOutput.writeObject(model.userNames.get(0));
		}
		catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		// success we inform the caller that it worked
		return true;
	}

	/*
	 * To send a message to the console or the GUI
	 */
	private void display(String msg) {
		System.out.println(msg);      // println in console
	}

	/*
	 * To send a message to the server
	 */
	void sendMessage(ChatMessage msg) {
		try {
			sOutput.writeObject(msg);
		}
		catch(IOException e) {
			display("Exception writing to server: " + e);
		}
	}

	/*
	 * When something goes wrong
	 * Close the Input/Output streams and disconnect not much to do in the catch clause
	 */
	private void disconnect() {
		try { 
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {} // not much else I can do
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {} // not much else I can do
		try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {} // not much else I can do




	}
	/*
	 * To start the Client in console mode use one of the following command
	 * > java Client
	 * > java Client username
	 * > java Client username portNumber
	 * > java Client username portNumber serverAddress
	 * at the console prompt
	 * If the portNumber is not specified 28442 is used
	 * If the serverAddress is not specified "localHost" is used
	 * If the username is not specified "Anonymous" is used
	 * > java Client 
	 * is equivalent to
	 * > java Client Anonymous 28442 localhost 
	 * are eqquivalent
	 * 
	 * In console mode, if an error occurs the program simply stops
	 * when a GUI id used, the GUI is informed of the disconnection
	 */
	public static void main(String[] args) {
		// default values	
		PokerVariables model  = new PokerVariables();


		int portNumber = 28442;
		String serverAddress = "localhost";
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter a Username");
		

		String userName  = keyboard.nextLine();
		model.userNames.add(userName);

		// depending of the number of arguments provided we fall through
		switch(args.length) {
		// > javac Client username portNumber serverAddr
		case 3:
			serverAddress = args[2];
			// > javac Client username portNumber
		case 2:
			try {
				portNumber = Integer.parseInt(args[1]);
			}
			catch(Exception e) {
				System.out.println("Invalid port number.");
				System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
				keyboard.close();
				return;
			}
			// > javac Client username
		case 1: 
			model.userNames.set(0, args[0]);
			// > java Client
		case 0:
			break;
			// invalid number of arguments
		default:
			System.out.println("Usage is: > java Client [player number] [portNumber] {serverAddress]");
			keyboard.close();
			return;
		}
		// create the Client object
		Client client = new Client(serverAddress, portNumber, model.userNames.get(0), model);
		// test if we can start the connection to the Server
		// if it failed nothing we can do
		if(!client.start()) {
			keyboard.close();
			return;
		}

		// wait for messages from user
		//Scanner scan = new Scanner(System.in);
		String msg = "";
		// loop forever for message from the user
		while(true) {
			// read message from user


			System.out.print(": ");
			msg = keyboard.nextLine( );
			
			// logout if message is LOGOUT
			if(msg.equalsIgnoreCase("LOGOUT")) {
					client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, "" + ",disconnected\n", model.userNames.get(0)));
					// break to do the disconnect
					break;
				}
				// message WhoIsIn
				else if(msg.equalsIgnoreCase("WHOISIN")) {
					client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, "", model.userNames.get(0)));				
				}
	
				else {				// default to ordinary message
	
					client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg, model.userNames.get(0)));				
					System.out.println("sending ordinary message to server");
					
					if (msg.contains("return"))
					{
						String cards = msg.replace("return ", "");
					}
	
				}
			
		}
		// done disconnect
		client.disconnect();
		keyboard.close();
	}

	/*
	 * a class that waits for the message from the server and append them to the JTextArea
	 * if we have a GUI or simply use System.out.println() if in console mode
	 */
	class ListenFromServer extends Thread {

		public void run() {
			while(true) {
				try {
					ChatMessage msg1 = (ChatMessage) sInput.readObject();
					//If you want console to print every message client receives from server, uncomment next line.
					String msg = "";
					if (msg1.getMessage().getClass().equals(String.class))
					{
						msg = (String)msg1.getMessage();
						//System.out.println(msg);

					}
					
					String phrase = msg;
					String delims = "[ ]+";
					String[] tokens = phrase.split(delims);

					for (int i = 0; i<tokens.length-1; i++)
					{
						if (tokens[i].contains(model.userNames.get(0)))
						{
							if (tokens[i+1].contains("takes"))
							{	
								System.out.println("youre taking cards");
								String cards = msg;
								cards = cards.replace(model.userNames.get(0) + " takes ","");

								String delims2 = "[,]+";
								String[] hand = cards.split(delims2);
								for(String s : model.hand)
								{
									model.hand.remove(s);
								}
								System.out.println("your current hand:");
								for(String s : hand)
								{
									model.hand.add(s);
									System.out.println(s);
								}
								
								
							}
							if (tokens[i+1].contains("gets"))
							{	
								String cards = msg;
								cards = cards.replace(model.userNames.get(0) + " gets ", "");

								System.out.println("youre getting cards " + cards);
								String delims2 = "[,]+";
								String[] received = cards.split(delims2);
								
								for(String s : received)
								{
									model.hand.add(s);
									System.out.println("Added " + s + " to hand");
								}

								System.out.println("Current hand: ");
								for(String s : model.hand)
								{
									System.out.print(s + ", ");
								}
								System.out.print("\n");
							}
						}
						//A new player has connected.
						
						else if (tokens[i+1].contains("logout"))
						{


						}

						else if (tokens[i+1].contains("disconnected"))
						{

						}
						else {
							//System.out.println(msg);
						}
					}


				}

				catch(IOException e) {
					display("Server has closed the connection: " + e);
					break;
				}
				// can't happen with a String object but need the catch anyhow
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}

